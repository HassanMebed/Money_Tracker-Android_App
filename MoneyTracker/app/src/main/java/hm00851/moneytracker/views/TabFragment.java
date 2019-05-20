package hm00851.moneytracker.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.TabContract;
import hm00851.moneytracker.models.Item;
import hm00851.moneytracker.presenters.ItemAdapter;
import hm00851.moneytracker.presenters.SpacingItemDecoration;
import hm00851.moneytracker.presenters.TabPresenter;

/**
 * Represents one tab, which is one of the user's added categories, in the tab
 * layout of the main screen.
 * Displays all of the user's recorded items for that category in a grid layout.
 */
public class TabFragment extends Fragment implements TabContract.View, ItemAdapter.ItemListener
{
    private RecyclerView recyclerView = null;
    private RecyclerView.Adapter adapter = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private View view = null;
    private String tabTitle = null;
    /** list of all the items belonging to the tab's category */
    private ArrayList<Item> items = null;
    private TabPresenter presenter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance)
    {
        view = inflater.inflate(R.layout.tab_layout, container, false);
        this.tabTitle = super.getArguments().getString("tabtitle");
        this.presenter = new TabPresenter(this);
        this.items = this.presenter.getItems(super.getActivity());
        this.initializeView();

        return view;
    }

    /**
     * @return name of the tab's category.
     */
    @Override
    public String getTabTitle()
    {
        return this.tabTitle;
    }

    /**
     * initializes UI elements of the layout
     */
    private void initializeView()
    {
        this.recyclerView = view.findViewById(R.id.items);
        this.layoutManager = new GridLayoutManager(super.getActivity(), 2);
        this.adapter = new ItemAdapter(this.items, this);

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.addItemDecoration(new SpacingItemDecoration(35));
        this.recyclerView.setAdapter(this.adapter);
    }

    /**
     * called when an item from the current list of search results is clicked;
     * calls activity to display item's details and passes to it all the details.
     * @param position
     *          the index of the item in the tab's items list.
     */
    @Override
    public void onItemClick(int position)
    {
        Intent viewItem = new Intent(super.getActivity(), ViewItemActivity.class);

        viewItem.putExtra("id", this.items.get(position).getId());
        viewItem.putExtra("name", this.items.get(position).getName());
        viewItem.putExtra("category", this.items.get(position).getCategory());
        viewItem.putExtra("value", this.items.get(position).getValue());
        viewItem.putExtra("date", this.items.get(position).getDate());
        viewItem.putExtra("details", this.items.get(position).getDetails());

        //image needs to be scaled down in order for transaction to happen, otherwise error will occur
        //because image size will be too large.
        if(this.items.get(position).getImage() == null)
        {
            viewItem.putExtra("image", this.items.get(position).getImage());
        }
        else
        {
            viewItem.putExtra("image", Bitmap.createScaledBitmap(this.items.get(position).getImage(), 300, 400, false));
        }

        super.startActivity(viewItem);
    }
}
