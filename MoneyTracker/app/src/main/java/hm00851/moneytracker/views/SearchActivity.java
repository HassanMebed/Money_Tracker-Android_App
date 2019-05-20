package hm00851.moneytracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

import hm00851.moneytracker.R;
import hm00851.moneytracker.models.Item;
import hm00851.moneytracker.presenters.ItemAdapter;
import hm00851.moneytracker.presenters.SearchPresenter;

/**
 * Activity for displaying user interface for user to do a live-result
 * search on one of his/her added items.
 */
public class SearchActivity extends AppCompatActivity implements ItemAdapter.ItemListener
{
    private EditText searchText = null;
    private RecyclerView itemsList = null;
    private RecyclerView.Adapter adapter = null;
    private RecyclerView.LayoutManager layoutManager = null;
    /** list of all of the users items */
    private ArrayList<Item> items = null;
    /** list of the current filtered items */
    private ArrayList<Item> filteredItems = null;
    private SearchPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.search_screen_layout);
        this.presenter = new SearchPresenter();
        this.items = this.presenter.getAllItems(this);
        this.filteredItems = this.items;
        this.initializeView();
    }

    /**
     * initializes the UI elements of the layout.
     */
    private void initializeView()
    {
        this.searchText = super.findViewById(R.id.searchText);
        this.itemsList = super.findViewById(R.id.itemsList);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new ItemAdapter(this.items, this);
        this.itemsList.setLayoutManager(this.layoutManager);
        this.itemsList.setAdapter(this.adapter);
        this.searchText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            /**
             * calls method to filter search results after each text change
             * event
             * @param s
             *          the new text
             */
            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }
        });
    }

    /**
     * adds items from the list of user's added items that contain a specific string
     * of text in their item names, into the filtered items list, then updates the search
     * results view with the new filtered list.
     * @param text
     *          the string of text to be checked for.
     */
    private void filter(String text)
    {
        this.filteredItems = new ArrayList<>();

        for(Item item : this.items)
        {
            if(item.getName().toLowerCase().contains(text.toLowerCase()))
            {
                this.filteredItems.add(item);
            }
        }

        ((ItemAdapter) this.adapter).filterList(this.filteredItems);
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
     * called when an item from the current list of search results is clicked;
     * calls activity to display item's details and passes to it all the details.
     * @param position
     *          the index of the item in the filtered list.
     */
    @Override
    public void onItemClick(int position)
    {
        Intent viewItem = new Intent(this, ViewItemActivity.class);

        viewItem.putExtra("id", this.filteredItems.get(position).getId());
        viewItem.putExtra("name", this.filteredItems.get(position).getName());
        viewItem.putExtra("category", this.filteredItems.get(position).getCategory());
        viewItem.putExtra("value", this.filteredItems.get(position).getValue());
        viewItem.putExtra("date", this.filteredItems.get(position).getDate());
        viewItem.putExtra("details", this.filteredItems.get(position).getDetails());
        viewItem.putExtra("image", this.filteredItems.get(position).getImage());
        super.startActivity(viewItem);
    }
}