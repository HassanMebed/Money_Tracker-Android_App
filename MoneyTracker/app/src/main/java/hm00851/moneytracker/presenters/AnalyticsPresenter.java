package hm00851.moneytracker.presenters;

import android.content.Context;
import android.database.Cursor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import hm00851.moneytracker.contracts.AnalyticsContract;
import hm00851.moneytracker.models.DBHandler;

/**
 * Presenter class for AnalyticsActivity.
 * fetches data from the database in desired format.
 */
public class AnalyticsPresenter implements AnalyticsContract.Controller
{
    private AnalyticsContract.View view = null;

    public AnalyticsPresenter(AnalyticsContract.View view)
    {
        this.view = view;
    }

    /**
     * @return hashmap with category name as key and category's total item cost
     *         as the corresponding value.
     * adds up total cost of items for each category and adds result to hashmap.
     * @param context
     *          the activity's context.
     */
    @Override
    public HashMap<String, Float> getChartData(Context context)
    {
        HashMap<String, Float> chartData = new HashMap<>();
        ArrayList<String> categories = new ArrayList<>();

        Cursor cursor = DBHandler.getInstance(context).getCategoriesData();

        while(cursor.moveToNext())
        {
            categories.add(cursor.getString(0));
        }

        cursor.close();

        Cursor cursorSecondOperation = DBHandler.getInstance(context).getItemsData();

        for(int i = 0; i < categories.size(); i++)
        {
            float totalCost = 0;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

            while(cursorSecondOperation.moveToNext())
            {
                if(cursorSecondOperation.getString(cursorSecondOperation.getColumnIndex("Category")).equals(categories.get(i)))
                {
                    totalCost = totalCost + cursorSecondOperation.getFloat(cursorSecondOperation.getColumnIndex("Value"));
                }
            }

            totalCost = Float.parseFloat(decimalFormat.format(totalCost));

            chartData.put(categories.get(i), totalCost);
            cursorSecondOperation.moveToFirst();
        }

        cursorSecondOperation.close();

        return chartData;
    }
}
