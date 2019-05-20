package hm00851.moneytracker.contracts;

import android.content.Context;

import java.util.HashMap;

public interface AnalyticsContract
{
    interface View
    {

    }

    interface Controller
    {
        HashMap<String, Float> getChartData(Context context);
    }
}
