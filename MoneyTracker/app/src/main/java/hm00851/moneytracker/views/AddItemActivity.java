package hm00851.moneytracker.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.AddItemContract;
import hm00851.moneytracker.presenters.AddItemPresenter;

/**
 * Activity for displaying user interface for user to add an item to one of
 * his/her created categories.
 */
public class AddItemActivity extends AppCompatActivity implements AddItemContract.View
{
    private LinearLayout fieldsContainer = null;
    private LinearLayout itemInputField = null;
    private LinearLayout costInputField = null;
    private Spinner selection = null;
    private EditText itemName = null;
    private EditText itemDetails = null;
    private EditText itemCost = null;
    private ImageView itemImage = null;
    /** stores current captured image */
    private Bitmap image = null;
    /** stores temporary file location for a captured image */
    private File imageFile = null;
    private AddItemPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.add_item_screen_layout);
        this.presenter = new AddItemPresenter(this);
        //for naming the file appropriately
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDirectory = super.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try
        {
            this.imageFile = File.createTempFile(timeStamp, ".jpg", storageDirectory);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.initializeView();
    }

    /**
     * adds a textview containing a red colored error under the item cost input
     * field indicating a validation error.
     */
    @Override
    public void displayValidationErrorForItemCost(String error)
    {
        TextView errorMessage = new TextView(this);

        errorMessage.setText(error);
        errorMessage.setTextColor(Color.parseColor("#b22222"));
        this.costInputField.addView(errorMessage);
    }

    /**
     * adds a textview containing a red colored error under the item name input
     * field indicating a validation error.
     */
    @Override
    public void displayValidationErrorForItemName(String error)
    {
        TextView errorMessage = new TextView(this);

        errorMessage.setText(error);
        errorMessage.setTextColor(Color.parseColor("#b22222"));
        this.itemInputField.addView(errorMessage);
    }

    /**
     * initializes UI elements of the layout
     */
    private void initializeView()
    {
        Intent selectedTab = super.getIntent();
        this.fieldsContainer = super.findViewById(R.id.fieldsContainer);
        this.itemInputField = super.findViewById(R.id.itemInputField);
        this.costInputField = super.findViewById(R.id.costInputField);
        this.itemName = super.findViewById(R.id.itemName);
        this.itemDetails = super.findViewById(R.id.itemDetails);
        this.itemCost = super.findViewById(R.id.itemCost);
        this.itemImage = super.findViewById(R.id.itemImage);
        this.selection = super.findViewById(R.id.selection);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, this.presenter.getCategoriesArray(this, selectedTab.getStringExtra("selectedTab")));

        this.selection.setAdapter(adapter);
    }

    /**
     * handling of the result of the image capture;
     * gets resulted image output from the file location variable used in the
     * original intent,
     * sets it to the global image variable of the activity,
     * modifies view accordingly.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(this.imageFile.exists())
                {
                    Bitmap imageResult = BitmapFactory.decodeFile(this.imageFile.getAbsolutePath());
                    this.image = imageResult;
                    Button remove = new Button(this);

                    this.itemImage.setImageBitmap(this.image);
                    remove.setWidth(150);
                    remove.setText("DELETE");
                    remove.setBackgroundColor(Color.parseColor("#ff0000"));
                    remove.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            image = null;
                            itemImage.setImageResource(R.drawable.add_photo_icon);
                            fieldsContainer.removeViewAt(fieldsContainer.getChildCount() - 1);
                        }
                    });

                    this.fieldsContainer.addView(remove);
                }
            }
        }
    }

    /**
     * reloads parent activity when back button is pressed.
     */
    @Override
    public void onBackPressed()
    {
        Intent reloadParentActivity = new Intent(this, MainScreenActivity.class);
        super.startActivity(reloadParentActivity);
    }

    /**
     * onClick method for "SUBMIT" button;
     * clears any textview validation errors, if any,
     * sends inputted data to presenter for processing,
     * on item being added successfully, parent activity will be reloaded and
     * the tab that represents the category of this item will be automatically
     * selected.
     */
    public void onSubmit(View view)
    {
        if(this.costInputField.getChildCount() == 2)
        {
            this.costInputField.removeViewAt(1);
        }

        if(this.itemInputField.getChildCount() == 2)
        {
            this.itemInputField.removeViewAt(1);
        }

        boolean success = this.presenter.submit(this, this.itemName.getText().toString(), this.itemDetails.getText().toString(), this.itemCost.getText().toString(), this.selection.getSelectedItem().toString(), this.image);

        if(success == true)
        {
            Intent reloadParentActivityAndSelectTab = new Intent(this, MainScreenActivity.class);

            reloadParentActivityAndSelectTab.putExtra("tabSection", this.selection.getSelectedItem().toString());
            super.startActivity(reloadParentActivityAndSelectTab);
        }
    }

    /**
     * onClick method for the imageview;
     * sends intent to the system to open its camera sensor interface for this
     * activity and await an image capture event.
     * Creates uri using the image location file variable for the intent to
     * temporarily store captured image in.
     */
    public void openCamera(View view)
    {
        Intent accessCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(this.imageFile != null)
        {
            Uri imageURI = FileProvider.getUriForFile(this, "hm00851.moneytracker.android.fileprovider", this.imageFile);
            accessCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            super.startActivityForResult(accessCamera, 1);
        }
    }

    /**
     * displays a pop-up alert box showing an input error.
     * @param error
     *          the error to be displayed
     */
    public void sendAlert(String error)
    {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(error)
                .setNegativeButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}