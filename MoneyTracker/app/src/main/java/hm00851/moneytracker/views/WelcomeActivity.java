package hm00851.moneytracker.views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.WelcomeContract;
import hm00851.moneytracker.presenters.WelcomePresenter;

/**
 * activity that displays user interface for first time user registration.
 */
public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View
{
    private EditText name = null;
    private EditText currency = null;
    private LinearLayout inputFields = null;
    private WelcomePresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.welcome_screen_layout);
        this.presenter = new WelcomePresenter(this);
        this.initializeView();
    }

    /**
     * adds a textview containing a red colored error under the input
     * fields indicating a validation error.
     */
    @Override
    public void failedToRegister(String error)
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

    /**
     * initializes UI elements of the layout.
     */
    private void initializeView()
    {
        this.name = super.findViewById(R.id.name);
        this.currency = super.findViewById(R.id.currency);
        this.inputFields = super.findViewById(R.id.inputFields);
    }

    /**
     * onClick method for "CONTINUE" button;
     * sends inputted data to the presenter for processing.
     * upon successful user data registration, transitions to
     * main screen activity of the app.
     */
    public void onContinue(View view)
    {
        boolean success = this.presenter.onRegister(this, this.name.getText().toString(), this.currency.getText().toString());

        if(success == true)
        {
            Intent startMainScreenActivity = new Intent(this, MainScreenActivity.class);
            super.startActivity(startMainScreenActivity);
            super.finish();
        }
    }
}

