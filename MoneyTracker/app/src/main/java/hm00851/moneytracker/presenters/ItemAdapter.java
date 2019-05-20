package hm00851.moneytracker.presenters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hm00851.moneytracker.R;
import hm00851.moneytracker.models.Item;
import hm00851.moneytracker.models.User;

/**
 * Adapter class for RecyclerView that represents and initializes the display format
 * of all the items in the recycler view.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
{
    /** list of all the items in the recycler view */
    private ArrayList<Item> itemsList = null;
    /** item's listener for executing code upon touching the item */
    private ItemListener itemListener = null;

    public ItemAdapter(ArrayList<Item> itemsList, ItemListener itemListener)
    {
        this.itemsList = itemsList;
        this.itemListener = itemListener;
    }

    /**
     * updates the recycler view's item list with a filtered one in the
     * case a search is being performed.
     * @param filteredList
     *          the new list to be shown.
     */
    public void filterList(ArrayList<Item> filteredList)
    {
        this.itemsList = filteredList;
        super.notifyDataSetChanged();
    }

    /**
     * method generated from RecyclerView.Adapter interface;
     * required for initializing layout of each item and adding
     * the above created listener to the item.
     */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view, this.itemListener);

        return viewHolder;
    }

    /**
     * method generated from RecyclerView.Adapter interface;
     * initializes the textviews in the layout of an item with its corresponding
     * text fetched from the arraylist of items.
     * @param itemViewHolder
     *          the object containing the item's layout.
     * @param position
     *          the item's index in the arraylist.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position)
    {
        Item currentItem = this.itemsList.get(position);

        itemViewHolder.name.setText(currentItem.getName());
        itemViewHolder.value.setText(User.getInstance().getCurrency() + currentItem.getValue());
        itemViewHolder.date.setText(currentItem.getDate());
    }

    //generated from RecyclerView.Adapter interface.
    @Override
    public int getItemCount()
    {
        return this.itemsList.size();
    }

    /**
     * Creates the layout of each item from the designated xml layout file for
     * display in the recycler view.
     * Adds onClick listener to the item.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name = null;
        public TextView value = null;
        public TextView date = null;
        public ItemListener itemListener = null;

        public ItemViewHolder(@NonNull View itemView, ItemListener itemListener)
        {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.value = itemView.findViewById(R.id.value);
            this.date = itemView.findViewById(R.id.date);
            this.itemListener = itemListener;

            itemView.setOnClickListener(this);
        }

        /**
         * Calls the ItemListner's implemented onItemClick method.
         * passes to it the position of the item that was clicked in
         * the RecyclerView's list.
         */
        @Override
        public void onClick(View view)
        {
            this.itemListener.onItemClick(super.getAdapterPosition());
        }
    }

    /**
     * Every activity that has the RecyclerView of items can implement this
     * interface to execute code once an item has been clicked.
     */
    public interface ItemListener
    {
        void onItemClick(int position);
    }
}
