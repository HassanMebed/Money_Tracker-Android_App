package hm00851.moneytracker.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hm00851.moneytracker.R;
import hm00851.moneytracker.models.DBHandler;

/**
 * Adapter class for ListView that represents and initializes the display format of all
 * the categories in the list.
 */
public class CategoryItemAdapter extends BaseAdapter implements ListAdapter
{
    /** list of all the category names */
    private ArrayList<String> categoryList = null;
    /** the ListView's activity's context */
    private Context context = null;

    public CategoryItemAdapter(ArrayList<String> categoryList, Context context)
    {
        this.categoryList = categoryList;
        this.context = context;
    }

    //the following are methods generated from ListAdapter interface
    //and are needed to identify an element in the list
    @Override
    public int getCount()
    {
        return this.categoryList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.categoryList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    /**
     * set up and initialization of each item in the list.
     * each item will have:
     * category name on the left and on the right two buttons; edit and delete
     * with their onClick listeners
     */
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_item_layout, null);
        }


        TextView categoryItem = view.findViewById(R.id.categoryItem);
        categoryItem.setText(categoryList.get(position));


        Button edit = view.findViewById(R.id.edit);
        Button delete = view.findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener(){
            /**
             * handling of editing category;
             * creates and shows input dialogue for editing category's name.
             */
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Edit Category Name");

                final EditText input = new EditText(context);

                input.setText(categoryList.get(position));
                builder.setView(input);

                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    /**
                     * handling of submitting edited category name;
                     * validates new edit before committing it through
                     * directly passing it to the database handler.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        String newName = input.getText().toString();

                        if(newName.isEmpty())
                        {
                            new android.support.v7.app.AlertDialog.Builder(context)
                                    .setMessage("New category name cannot be empty.")
                                    .setNegativeButton(android.R.string.ok, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        else
                        {
                            newName.toLowerCase();

                            newName = newName.substring(0, 1).toUpperCase() + newName.substring(1);
                            boolean success = true;
                            Cursor categories = DBHandler.getInstance(context).getCategoriesData();

                            while(categories.moveToNext())
                            {
                                if(categories.getString(0).equals(newName))
                                {
                                    success = false;
                                    break;
                                }
                            }

                            if(success == true)
                            {
                                boolean check = DBHandler.getInstance(context).updateCategories(getItem(position).toString(), newName);

                                if(check == true)
                                {
                                    categoryList.set(position, newName);
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    new android.support.v7.app.AlertDialog.Builder(context)
                                            .setMessage("There was an error updating the category's name.")
                                            .setNegativeButton(android.R.string.ok, null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            }
                            else
                            {
                                new android.support.v7.app.AlertDialog.Builder(context)
                                        .setMessage("Entered category already exists")
                                        .setNegativeButton(android.R.string.ok, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
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
        });

        delete.setOnClickListener(new View.OnClickListener(){
            /**
             * handling of deleting category;
             * asks user to confirm category deletion before deleting
             * category directly through database handler.
             */
            @Override
            public void onClick(View view)
            {
                new AlertDialog.Builder(context)
                        .setTitle("CONFIRMATION")
                        .setMessage("Are you sure you want to delete this category?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {

                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                DBHandler.getInstance(context).removeFromCategories(getItem(position).toString());
                                categoryList.remove(position);
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        return view;
    }
}

