package hm00851.moneytracker.views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.EditUserDataContract;
import hm00851.moneytracker.models.User;
import hm00851.moneytracker.presenters.EditUserDataPresenter;

/**
 * Activity for displaying user interface for user to edit his/her app details that
 * were inputted on first-time entry.
 */
public class EditUserDataActivity extends AppCompatActivity implements EditUserDataContract.View
{
    private EditText userName = null;
    private EditText userCurrency = null;
    private LinearLayout inputFields = null;
    private EditUserDataPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.edit_user_data_screen_layout);
        this.presenter = new EditUserDataPresenter(this);
        this.initializeView();
    }

    /**
     * initializes UI elements of the layout.
     */
    private void initializeView()
    {
        this.userName = super.findViewById(R.id.userName);
        this.userCurrency = super.findViewById(R.id.userCurrency);
        this.inputFields = super.findViewById(R.id.inputFields);

        this.userName.setText(User.getInstance().getName());
        this.userCurrency.setText(User.getInstance().getCurrency());
    }

    /**
     * onClick method for "SUBMIT CHANGES" button;
     * sends inputted data to the presenter for processing.
     * If changes were made successfully, display message in the bottom
     * to notify user and re-initialize the view to show the changes.
     */
    public void onSubmitChanges(View view)
    {
        boolean success = this.presenter.submitChanges(this, this.userName.getText().toString(), this.userCurrency.getText().toString());

        if(success == true)
        {
            Toast.makeText(this, "Changes were made successfully", Toast.LENGTH_SHORT).show();
            this.initializeView();
        }
    }

    /**
     * adds a textview containing a red colored error under the input
     * fields indicating a validation error.
     */
    @Override
    public void onValidationError(String error)
    {
        if(this.inputFields.getChildCount() == 3)
        {
            this.inputFields.removeViewAt(2);
        }

        TextView errorMessage = new TextView(this);

        errorMessage.setText(error);
        errorMessage.setTextColor(Color.parseColor("#b22222"));
        this.inputFields.addView(errorMessage);
    }

}
