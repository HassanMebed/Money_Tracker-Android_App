package hm00851.moneytracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import hm00851.moneytracker.R;
import hm00851.moneytracker.models.User;

/**
 * base activity that contains the navigation menu of the app. All main
 * level activities i.e. the activities that will be accessible from the
 * navigation menu, should extend from this activity in order to have the menu
 * in place. All activities that extend from this one should not call the
 * setContentView method, but should instead inflate the content frame of this
 * activity with its own layout.
 */
public class NavigationMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    protected FrameLayout contentFrame = null;
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle toggle = null;
    private NavigationView navigationView = null;
    private View headerView = null;
    private TextView name = null;

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        super.setContentView(R.layout.menu_layout);
        this.initialize();
    }

    /**
     * initializes UI elements of the layout.
     */
    private void initialize()
    {
        this.contentFrame = findViewById(R.id.contentFrame);
        this.drawerLayout = findViewById(R.id.drawerLayout);
        this.navigationView = findViewById(R.id.navigationView);

        this.navigationView.setNavigationItemSelectedListener(this);

        this.headerView = this.navigationView.getHeaderView(0);
        this.name = this.headerView.findViewById(R.id.name);

        this.name.setText(User.getInstance().getName());

        this.toggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.open, R.string.close);

        this.drawerLayout.addDrawerListener(this.toggle);
        this.toggle.syncState();
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * if navigation menu is visible, and the back button is pressed the,
     * navigation menu should close.
     */
    @Override
    public void onBackPressed()
    {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    /**
     * called when an item in the navigation menu is selected;
     * gets the selected item and checks which activity it corresponds
     * to by checking its id, then starts the matching activity.
     * @param menuItem
     *          the selected menu item.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch(menuItem.getItemId())
        {
            case R.id.moneySpending:
            {
                Intent startMainScreenActivity = new Intent(this, MainScreenActivity.class);
                super.startActivity(startMainScreenActivity);
                break;
            }
            case R.id.analytics:
            {
                Intent startAnalyticsActivity = new Intent(this, AnalyticsActivity.class);
                super.startActivity(startAnalyticsActivity);
                break;
            }
            case R.id.settings:
            {
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                super.startActivity(startSettingsActivity);
                break;
            }
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * called when the "hamburger" icon, which represents the menu, in the
     * top left corner of the activity is pressed;
     * makes the navigation menu visible and changes the icon to the back icon.
     * @param item
     *          the "hamburger" icon
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (this.toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * called when instance of this activity is returned to;
     * re-initializes the menu view in case variables were lost due to
     * loss of app's focus or if user changes his/her details from the settings.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        this.initialize();
    }
}
