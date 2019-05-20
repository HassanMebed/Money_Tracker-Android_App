package hm00851.moneytracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.MainScreenContract;
import hm00851.moneytracker.presenters.MainScreenPresenter;
import hm00851.moneytracker.presenters.SectionsPagerAdapter;

/**
 * Activity that is loaded after start-up finishes (or when user enters
 * application for the first time, after entering his/her app details).
 * Shows user all of his/her added categories in a tab layout, with each tab
 * fragment containing the items of that category. Gives user the options to add a
 * new item to a category, manage his/her categories and search for a specific item.
 */
public class MainScreenActivity extends NavigationMenu implements MainScreenContract.View
{
    private ViewPager viewPager = null;
    private TabLayout tabs = null;
    private FloatingActionButton fab = null;
    private SectionsPagerAdapter adapter = null;
    private MainScreenPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.getLayoutInflater().inflate(R.layout.main_screen_layout, super.contentFrame);
        this.presenter = new MainScreenPresenter(this);
        this.initializeView();
    }

    /**
     * initializes UI elements of the layout.
     */
    private void initializeView()
    {
        Intent tabSelection = super.getIntent();
        this.adapter = new SectionsPagerAdapter(super.getSupportFragmentManager());
        this.adapter = this.presenter.initializeSectionsPagerAdapter(this, adapter);
        this.viewPager = findViewById(R.id.view_pager);
        this.viewPager.setAdapter(this.adapter);
        this.tabs = findViewById(R.id.tabs);

        this.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        this.tabs.setupWithViewPager(this.viewPager);
        this.fab = findViewById(R.id.fab);

        if(tabSelection.hasExtra("tabSection"))
        {
            for(int i = 0; i < this.adapter.getCount(); i++)
            {
                if(this.adapter.getPageTitle(i).equals(tabSelection.getStringExtra("tabSection")))
                {
                    this.tabs.getTabAt(i).select();
                }
            }
        }
    }

    /**
     * onClick method for the floating action button;
     * calls activity for adding a new item to a category and passes to it
     * the title of the current selected tab so that the in the categories
     * selection field, the category representing the tab is loaded as the first
     * option.
     */
    public void onAddItem(View view)
    {
        Intent startAddItemActivity = new Intent(this, AddItemActivity.class);

        startAddItemActivity.putExtra("selectedTab", this.adapter.getPageTitle(this.tabs.getSelectedTabPosition()));
        super.startActivity(startAddItemActivity);
    }

    /**
     * onClick method for "Manage Categories" button;
     * calls activity for managing categories.
     */
    public void onManageCategories(View view)
    {
        Intent startManageCategoriesActivity = new Intent(this, ManageCategoriesActivity.class);
        super.startActivity(startManageCategoriesActivity);
    }

    /**
     * Since this is a singleTop Activity, every time user returns to it or
     * tries to access it again, it will return to the same instance, in which
     * case it should re-initialize it in case categories or items data changed.
     */
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        this.initializeView();
    }

    /**
     * onClick method for "Find a specific item" button;
     * calls activity to preform the search.
     */
    public void onSearchForItem(View view)
    {
        Intent startSearchActivity = new Intent(this, SearchActivity.class);
        super.startActivity(startSearchActivity);
    }

}