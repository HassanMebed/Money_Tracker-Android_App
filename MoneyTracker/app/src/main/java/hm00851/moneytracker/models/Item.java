package hm00851.moneytracker.models;

import android.graphics.Bitmap;

/**
 * Represents an item.
 * Holds all of the item's attributes and getters to retrieve them.
 */
public class Item
{
    /** item's id */
    private int id = 0;
    /** item's name */
    private String name = null;
    /** item's category */
    private String category = null;
    /** item's cost */
    private double value = 0.0;
    /** item's recorded date */
    private String date = null;
    /** item's supporting details */
    private String details = null;
    /** item's supporting image */
    private Bitmap image = null;


    public Item(int id, String name, String category, double value, String date, String details, Bitmap image)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.value = value;
        this.date = date;
        this.details = details;
        this.image = image;
    }

    public String getCategory()
    {
        return this.category;
    }

    public String getDate()
    {
        return this.date;
    }

    public String getDetails()
    {
        return this.details;
    }

    public int getId()
    {
        return this.id;
    }

    public Bitmap getImage()
    {
        return this.image;
    }

    public String getName()
    {
        return this.name;
    }

    public double getValue()
    {
        return this.value;
    }
}
