package hm00851.moneytracker.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.ManageCategoriesContract;
import hm00851.moneytracker.presenters.CategoryItemAdapter;
import hm00851.moneytracker.presenters.ManageCategoriesPresenter;

/**
 * Activity for displaying user interface for user to view all of his current added
 * categories, with options to edit their names, or delete them.
 */
public class ManageCategoriesActivity extends AppCompatActivity implements ManageCategoriesContract.View
{
    private ListView categoriesList = null;
    private ManageCategoriesPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.manage_categories_screen_layout);
        this.presenter = new ManageCategoriesPresenter(this);
        this.initializeView();
    }

    /**
     * initializes UI elements of the layout
     */
    private void initializeView()
    {
        CategoryItemAdapter adapter = new CategoryItemAdapter(this.presenter.getCategories(this), this);
        this.categoriesList = super.findViewById(R.id.categoriesList);
        this.categoriesList.setAdapter(adapter);
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
     * onClick method for "Add Category" button;
     * calls activity for adding categories.
     */
    public void onAddCategory(View view)
    {
        Intent startAddCategoryActivity = new Intent(this, AddCategoryActivity.class);
        super.startActivity(startAddCategoryActivity);
    }
}
