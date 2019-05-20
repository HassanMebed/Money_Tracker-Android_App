package hm00851.moneytracker.presenters;

import android.content.Context;
import android.graphics.Bitmap;

import hm00851.moneytracker.contracts.ViewItemContract;
import hm00851.moneytracker.models.DBHandler;

/**
 * Presenter class for ViewItemActivity.
 * preforms database functions for edit operations done on some
 * of the item's details
 */
public class ViewItemPresenter implements ViewItemContract.Controller
{
    private ViewItemContract.View view = null;

    public ViewItemPresenter(ViewItemContract.View view)
    {
        this.view = view;
    }

    /**
     * validates new cost value before passing it directly to database handler
     * for updating.
     * @param context
     *          the activity's context.
     * @param newCost
     *          the item's new inputted cost.
     * @param itemID
     *          the item's id in the database.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean editItemCost(Context context, double newCost, int itemID)
    {
        boolean success = true;

        if(newCost < 0)
        {
            success = false;
            this.view.sendAlert("Item's cost cannot be less than 0.");
        }
        else
        {
            success = DBHandler.getInstance(context).updateItemCost(newCost, itemID);

            if(success == false)
            {
                this.view.sendAlert("There was an error in updating the item's cost.");
            }
        }

        return success;
    }

    /**
     * passes new details input directly to database handler for updating.
     * @param context
     *          the activity's context.
     * @param newDetails
     *          the item's new inputted details.
     * @param itemID
     *          the item's id in the database.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean editItemDetails(Context context, String newDetails, int itemID)
    {
        boolean success = DBHandler.getInstance(context).updateItemDetails(newDetails, itemID);

        if(success == false)
        {
            this.view.sendAlert("There was an error in updating the item's details.");
        }

        return success;
    }

    /**
     * passes new captured image for the item directly to database handler for updating.
     * @param context
     *          the activity's context.
     * @param newImage
     *          the item's new captured image.
     * @param itemID
     *          the item's id in the database.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean editItemImage(Context context, Bitmap newImage, int itemID)
    {
        boolean success = DBHandler.getInstance(context).updateItemImage(newImage, itemID);

        if(success == false)
        {
            this.view.sendAlert("There was an error in updating the item's supporting image.");
        }

        return success;
    }

    /**
     * validates new name value before passing it directly to database handler
     * for updating.
     * @param context
     *          the activity's context.
     * @param newName
     *          the item's new inputted name.
     * @param itemID
     *          the item's id in the database.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean editItemName(Context context, String newName, int itemID)
    {
        boolean success = true;

        if(newName.isEmpty())
        {
            success = false;
            this.view.sendAlert("Item's name cannot be empty.");
        }
        else
        {
            success = DBHandler.getInstance(context).updateItemName(newName, itemID);

            if(success == false)
            {
                this.view.sendAlert("There was an error in updating the item's name.");
            }
        }

        return success;
    }

    /**
     * calls database handler to directly remove an item's image.
     * @param context
     *          the activity's context.
     * @param itemID
     *          the item's id in the database.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean removeImage(Context context, int itemID)
    {
        boolean success = DBHandler.getInstance(context).removeItemImage(itemID);

        if(success == false)
        {
            this.view.sendAlert("There was an error in removing the item's supporting image.");
        }

        return success;
    }

    /**
     * calls database handler to directly remove an item record.
     * @param context
     *          the activity's context.
     * @param itemID
     *          the item's id in the database.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean removeItem(Context context, int itemID)
    {
        boolean success = DBHandler.getInstance(context).removeFromItems(itemID);

        if(success == false)
        {
            this.view.sendAlert("There was an error in removing this item.");
        }

        return success;
    }

}