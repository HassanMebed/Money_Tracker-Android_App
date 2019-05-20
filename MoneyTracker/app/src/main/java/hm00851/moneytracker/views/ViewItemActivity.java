package hm00851.moneytracker.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.ViewItemContract;
import hm00851.moneytracker.models.User;
import hm00851.moneytracker.presenters.ViewItemPresenter;

/**
 * Activity for displaying user interface for displaying details of an item.
 * displays corresponding edit options for the some of the item's details.
 */
public class ViewItemActivity extends AppCompatActivity implements ViewItemContract.View
{
    private TextView itemNameView = null;
    private TextView itemCategoryView = null;
    private TextView itemCostView = null;
    private TextView itemDateView = null;
    private TextView itemDetailsView = null;
    private ImageView itemImageView = null;
    private Button takeImage = null;
    private Button deleteImage = null;
    /** the item's id */
    private int itemID = 0;
    /** the item's name */
    private String itemName = null;
    /** the item's category */
    private String itemCategory = null;
    /** the item's cost */
    private double itemCost = 0.0;
    /** the item's recorded date */
    private String itemDate = null;
    /** the item's supporting details */
    private String itemDetails = null;
    /** the item's supporting image */
    private Bitmap itemImage = null;
    private File imageFile = null;
    private ViewItemPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.view_item_screen_layout);

        this.presenter = new ViewItemPresenter(this);
        Intent itemInfo = super.getIntent();
        this.itemID = itemInfo.getIntExtra("id", 0);
        this.itemName = itemInfo.getStringExtra("name");
        this.itemCategory = itemInfo.getStringExtra("category");
        this.itemCost = itemInfo.getDoubleExtra("value", 0.0);
        this.itemDate = itemInfo.getStringExtra("date");
        this.itemDetails = itemInfo.getStringExtra("details");

        if(this.itemDetails.isEmpty())
        {
            this.itemDetails = "Details";
        }

        this.itemImage = itemInfo.getParcelableExtra("image");
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
     * initializes the UI elements of the layout
     */
    private void initializeView()
    {
        this.itemNameView = super.findViewById(R.id.itemName);
        this.itemCategoryView = super.findViewById(R.id.itemCategory);
        this.itemCostView = super.findViewById(R.id.itemCost);
        this.itemDateView = super.findViewById(R.id.itemDate);
        this.itemDetailsView = super.findViewById(R.id.itemDetails);
        this.itemImageView = super.findViewById(R.id.itemImage);
        this.takeImage = super.findViewById(R.id.takeImage);
        this.deleteImage = super.findViewById(R.id.deleteImage);

        this.itemNameView.setText(this.itemName);
        this.itemCategoryView.setText(this.itemCategory);
        this.itemCostView.setText("Cost: " + User.getInstance().getCurrency() + this.itemCost);
        this.itemDateView.setText("Recorded on " + this.itemDate);
        this.itemDetailsView.setText(this.itemDetails);

        if(this.itemImage != null)
        {
            this.itemImage = Bitmap.createScaledBitmap(this.itemImage, 480, 600, false);
            this.itemImageView.setImageBitmap(this.itemImage);
            this.deleteImage.setVisibility(View.VISIBLE);
            this.takeImage.setText("RETAKE");
        }
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
                if (this.imageFile.exists())
                {
                    Bitmap imageResult = BitmapFactory.decodeFile(this.imageFile.getAbsolutePath());
                    boolean success = this.presenter.editItemImage(this, imageResult, this.itemID);

                    if (success == true)
                    {
                        this.itemImage = imageResult;
                        this.itemImage = Bitmap.createScaledBitmap(this.itemImage, 480, 600, false);
                        this.initializeView();
                    }
                }
            }
        }
    }

    /**
     * reloads parent activity when back button is pressed.
     * reloaded activity's tab that represents the category of this item
     * will be automatically selected.
     */
    @Override
    public void onBackPressed()
    {
        Intent reloadParentActivityAndSelectTab = new Intent(this, MainScreenActivity.class);

        reloadParentActivityAndSelectTab.putExtra("tabSection", this.itemCategory);
        super.startActivity(reloadParentActivityAndSelectTab);
    }

    /**
     * onClick method for "DELETE ITEM" button;
     * asks user to confirm that they want to delete this item before sending
     * the item's id to the presenter for the removal process.
     */
    public void onDeleteItem(View view)
    {
        new AlertDialog.Builder(this)
                .setTitle("CONFIRMATION")
                .setMessage("Are you sure you want to delete this item?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        boolean success = presenter.removeItem(ViewItemActivity.this, itemID);

                        if(success == true)
                        {
                            onBackPressed();
                        }
                    }})
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * onClick method for "DELETE" button under the imageview;
     * asks user to confirm that they want to delete the item's saved
     * supporting image before sending the item's id to the presenter for
     * the image removal process.
     */
    public void onDeleteItemImage(View view)
    {
        new AlertDialog.Builder(this)
                .setTitle("CONFIRMATION")
                .setMessage("Are you sure you want to delete this item's supporting image?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        boolean success = presenter.removeImage(ViewItemActivity.this, itemID);

                        if(success == true)
                        {
                            itemImageView.setImageResource(R.drawable.empty_image_icon);
                            deleteImage.setVisibility(View.GONE);
                            itemImage = null;
                        }
                    }})
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * onClick method for the edit button next to the item's cost textview;
     * creates and shows input dialogue for editing item's cost.
     * upon committing the edit, sends the input to the presenter for processing.
     */
    public void onEditItemCost(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Edit Item Cost");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(Double.toString(this.itemCost));
        builder.setView(input);

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                double newCost = Double.parseDouble(input.getText().toString());
                boolean success = presenter.editItemCost(ViewItemActivity.this, newCost, itemID);

                if(success == true)
                {
                    itemCost = newCost;
                    initializeView();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * onClick method for the edit button next to the item's details textview;
     * creates and shows input dialogue for editing item's details.
     * upon committing the edit, sends the input to the presenter for processing.
     */
    public void onEditItemDetails(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Edit Item Details");

        final EditText input = new EditText(this);

        input.setSingleLine(false);
        input.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        input.setText(this.itemDetails);
        builder.setView(input);

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String newDetails = input.getText().toString();
                boolean success = presenter.editItemDetails(ViewItemActivity.this, newDetails, itemID);

                if(success == true)
                {
                    if(input.getText().toString().isEmpty())
                    {
                        itemDetails = "Details";
                    }
                    else
                    {
                        itemDetails = newDetails;
                        initializeView();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * onClick method for the edit button next to the item's name textview;
     * creates and shows input dialogue for editing item's name.
     * upon committing the edit, sends the input to the presenter for processing.
     */
    public void onEditItemName(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Edit Item Name");

        final EditText input = new EditText(this);

        input.setText(this.itemName);
        builder.setView(input);

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String newName = input.getText().toString();
                boolean success = presenter.editItemName(ViewItemActivity.this, newName, itemID);

                if(success == true)
                {
                    itemName = newName;
                    initializeView();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * called when the top back button is pressed;
     * calls the above onBackPressed method.
     * @param item
     *          the top back button.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                this.onBackPressed();
                break;
            }
        }
        return true;
    }

    /**
     * onClick method for "TAKE"/"RETAKE" button under the imageview;
     * sends intent to the system to open its camera sensor interface for this
     * activity and await an image capture event.
     * Creates uri using the image location file variable for the intent to
     * temporarily store captured image in.
     */
    public void onTakeImage(View view)
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
    @Override
    public void sendAlert(String error)
    {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(error)
                .setNegativeButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
