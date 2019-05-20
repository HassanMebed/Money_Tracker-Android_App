package hm00851.moneytracker.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * singleton class that integrates the app's sqlite database with the rest of
 * the app's modules.
 * creates the sqlite database on first-time launch.
 */
public class DBHandler extends SQLiteOpenHelper
{
    /** accessible instance of the database helper */
    private static DBHandler dbHandlerInstance = null;
    //the following variables are variables used in sql statements
    //to create tables, insert, delete, update etc.
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "moneytracker_db.db";
    private static final String OWNER_TABLE = "Owner";
    private static final String OWNER_COL1 = "Name";
    private static final String OWNER_COL2 = "Currency";
    private static final String CATEGORIES_TABLE = "Categories";
    private static final String CATEGORIES_COL1 = "Name";
    private static final String ITEMS_TABLE = "Items";
    private static final String ITEMS_COL1 = "ID";
    private static final String ITEMS_COL2 = "Name";
    private static final String ITEMS_COL3 = "Date";
    private static final String ITEMS_COL4 = "Details";
    private static final String ITEMS_COL5 = "Value";
    private static final String ITEMS_COL6 = "Category";
    private static final String ITEMS_COL7 = "Image";

    private DBHandler(Context context)
    {
        // call to SQLiteOpenHelper constructor
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * @return data set with all records from Categories table.
     */
    public Cursor getCategoriesData()
    {
        SQLiteDatabase database = super.getWritableDatabase();
        Cursor data = database.rawQuery("select * from " + CATEGORIES_TABLE, null);

        return data;
    }

    /**
     * @param context
     *          calling activity's context
     * @return instance of this class
     */
    public static synchronized DBHandler getInstance(Context context)
    {
        if(dbHandlerInstance == null)
        {
            dbHandlerInstance = new DBHandler(context.getApplicationContext());
        }

        return dbHandlerInstance;
    }

    /**
     * @return data set with all records from Items table.
     */
    public Cursor getItemsData()
    {
        SQLiteDatabase database = super.getWritableDatabase();
        Cursor data = database.rawQuery("select * from " + ITEMS_TABLE, null);

        return data;
    }

    /**
     * @return data set with user record in Owner table.
     */
    public Cursor getOwnerData()
    {
        SQLiteDatabase database = super.getWritableDatabase();
        Cursor data = database.rawQuery("select * from " + OWNER_TABLE, null);

        return data;
    }

    /**
     * inserts a new category into the Categories table.
     * @param name
     *          name of the new category.
     * @return true if operation was successful, false if not.
     */
    public boolean insertIntoCategoriesTable(String name)
    {
        boolean success = true;
        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues tableValues = new ContentValues();

        tableValues.put(CATEGORIES_COL1, name);

        long result = database.insert(CATEGORIES_TABLE, null, tableValues);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * inserts a new item record in the Items table
     * @param name
     *          item's name.
     * @param date
     *          item's recorded date.
     * @param details
     *          item's supporting details.
     * @param value
     *          item's cost.
     * @param category
     *          item's category.
     * @param image
     *          item's supporting image.
     * @return true if operation was successful, false if not.
     */
    public boolean insertIntoItemsTable(String name, String date, String details, double value, String category, Bitmap image)
    {
        boolean success = true;
        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues tableValues = new ContentValues();
        // since image column has data type of BLOB, bitmap image has
        // to be converted to a byte array in order to be stored.
        byte[] imageData = null;

        if(image != null)
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            imageData = outputStream.toByteArray();

            try
            {
                outputStream.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        tableValues.put(ITEMS_COL2, name);
        tableValues.put(ITEMS_COL3, date);
        tableValues.put(ITEMS_COL4, details);
        tableValues.put(ITEMS_COL5, value);
        tableValues.put(ITEMS_COL6, category);
        tableValues.put(ITEMS_COL7, imageData);

        long result = database.insert(ITEMS_TABLE, null, tableValues);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * inserts record of the app's user.
     * @param name
     *          user's name.
     * @param currency
     *          user's preferred currency.
     * @return true if operation was successful, false if not.
     */
    public boolean insertIntoOwnerTable(String name, String currency)
    {
        boolean success = true;
        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues tableValues = new ContentValues();

        tableValues.put(OWNER_COL1, name);
        tableValues.put(OWNER_COL2, currency);

        long result = database.insert(OWNER_TABLE, null, tableValues);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * SQLiteOpenHelper onCreate method.
     * when database id created for the first time.
     * @param database
     *             database passed by the SQLiteOpenHelper as a result
     *             of the database name passed to it in the constructor.
     *
     */
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String createOwnerTable = "create table " + OWNER_TABLE +
                "("
                + OWNER_COL1 + " text not null primary key,"
                + OWNER_COL2 + " text" +
                ")";

        String createCategoriesTable = "create table " + CATEGORIES_TABLE +
                "("
                + CATEGORIES_COL1 + " text not null primary key unique" +
                ")";

        String createItemsTable = "create table " + ITEMS_TABLE +
                "("
                + ITEMS_COL1 + " integer unique primary key autoincrement not null,"
                + ITEMS_COL2 + " text not null,"
                + ITEMS_COL3 + " text not null,"
                + ITEMS_COL4 + " text,"
                + ITEMS_COL5 + " real not null,"
                + ITEMS_COL6 + " text not null,"
                + ITEMS_COL7 + " blob,"
                + "foreign key(" + ITEMS_COL6 + ") references " + CATEGORIES_TABLE + "(" + CATEGORIES_COL1 + ")" + " on update cascade" +
                ")";

        ContentValues defaultCategory = new ContentValues();
        defaultCategory.put(CATEGORIES_COL1, "Food & Drink");

        database.execSQL(createOwnerTable);
        database.execSQL(createCategoriesTable);
        database.execSQL(createItemsTable);
        database.insert(CATEGORIES_TABLE, null, defaultCategory);
    }

    // recreates database if needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + OWNER_TABLE);
        db.execSQL("drop table if exists " + CATEGORIES_TABLE);
        db.execSQL("drop table if exists " + ITEMS_TABLE);
        this.onCreate(db);
    }

    /**
     * removes all records from the Items table.
     */
    public void removeAllFromItems()
    {
        SQLiteDatabase database = super.getWritableDatabase();
        database.execSQL("delete from " + ITEMS_TABLE);
    }

    /**
     * removes a designated category from the Categories table.
     * @param name
     *          name of the category to be removed.
     * @return true if operation was successful, false if not.
     */
    public boolean removeFromCategories(String name)
    {
        boolean success = true;
        SQLiteDatabase database = super.getWritableDatabase();
        long resultCategoryDelete = database.delete(CATEGORIES_TABLE, CATEGORIES_COL1 + "='" + name + "'", null);
        long resultChildItemDelete = database.delete(ITEMS_TABLE, ITEMS_COL6 + "='" + name + "'", null);

        if(resultCategoryDelete == -1)
        {
            success = false;
        }

        if(resultChildItemDelete == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * removes a designated item from the Items table
     * @param id
     *          item's id
     * @return true if operation was successful, false if not.
     */
    public boolean removeFromItems(int id)
    {
        boolean success = true;
        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.delete(ITEMS_TABLE, ITEMS_COL1 + " = " + id, null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * removes an image from an item's record by setting its field to null.
     * @param id
     *          item's id
     * @return true if operation was successful, false if not.
     */
    public boolean removeItemImage(int id)
    {
        boolean success = true;
        ContentValues newValue = new ContentValues();

        newValue.putNull(ITEMS_COL7);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(ITEMS_TABLE, newValue, ITEMS_COL1 + " = " + id, null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * updates a designated category's name
     * @param oldName
     *          the current name of the category.
     * @param newName
     *          the categories new name to be changed to.
     * @return true if operation was successful, false if not.
     */
    public boolean updateCategories(String oldName, String newName)
    {
        boolean success = true;
        ContentValues newValue = new ContentValues();

        newValue.put(CATEGORIES_COL1, newName);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(CATEGORIES_TABLE, newValue, CATEGORIES_COL1 + " = '" + oldName + "'", null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * updates a designated item's cost/value field
     * @param newCost
     *          item's new cost.
     * @param id
     *          item's id
     * @return true if operation was successful, false if not.
     */
    public boolean updateItemCost(double newCost, int id)
    {
        boolean success = true;
        ContentValues newValue = new ContentValues();

        newValue.put(ITEMS_COL5, newCost);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(ITEMS_TABLE, newValue, ITEMS_COL1 + " = " + id, null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * updates a designated item's supporting details.
     * @param newDetails
     *          item's new supporting details.
     * @param id
     *          item's id.
     * @return true if operation was successful, false if not.
     */
    public boolean updateItemDetails(String newDetails, int id)
    {
        boolean success = true;
        ContentValues newValue = new ContentValues();

        newValue.put(ITEMS_COL4, newDetails);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(ITEMS_TABLE, newValue, ITEMS_COL1 + " = " + id, null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * updates a designated item's supporting image.
     * @param newImage
     *          item's new supporting image.
     * @param id
     *          item's id.
     * @return true if operation was successful, false if not.
     */
    public boolean updateItemImage(Bitmap newImage, int id)
    {
        boolean success = true;
        ContentValues newValue = new ContentValues();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        newImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        byte[] newImageData = outputStream.toByteArray();

        try
        {
            outputStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        newValue.put(ITEMS_COL7, newImageData);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(ITEMS_TABLE, newValue, ITEMS_COL1 + " = " + id, null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * updates a designated item's name.
     * @param newName
     *          item's new name.
     * @param id
     *          item's id.
     * @return true if operation was successful, false if not.
     */
    public boolean updateItemName(String newName, int id)
    {
        boolean success = true;
        ContentValues newValue = new ContentValues();

        newValue.put(ITEMS_COL2, newName);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(ITEMS_TABLE, newValue, ITEMS_COL1 + " = " + id, null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }

    /**
     * updates user's name and preferred currency.
     * @param oldName
     *          user's current name.
     * @param oldCurrency
     *          user's current preferred currency.
     * @param newName
     *          user's new name.
     * @param newCurrency
     *          user's new preferred currency
     * @return true if operation was successful, false if not.
     */
    public boolean updateOwnerData(String oldName, String oldCurrency, String newName, String newCurrency)
    {
        boolean success = true;
        ContentValues newValues = new ContentValues();

        newValues.put(OWNER_COL1, newName);
        newValues.put(OWNER_COL2, newCurrency);

        SQLiteDatabase database = super.getWritableDatabase();
        long result = database.update(OWNER_TABLE, newValues, OWNER_COL1 + " = '" + oldName + "' AND " + OWNER_COL2 + " = '" + oldCurrency + "'", null);

        if(result == -1)
        {
            success = false;
        }

        return success;
    }
}