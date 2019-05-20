package hm00851.moneytracker.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.AnalyticsContract;
import hm00851.moneytracker.models.User;
import hm00851.moneytracker.presenters.AnalyticsPresenter;

/**
 * activity for displaying analytics page that contains visual data related to user's
 * inputted categories and items.
 */
public class AnalyticsActivity extends NavigationMenu implements AnalyticsContract.View
{
    private PieChart chart = null;
    private LinearLayout totalCosts = null;
    private AnalyticsPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.getLayoutInflater().inflate(R.layout.analytics_screen_layout, super.contentFrame);

        this.presenter = new AnalyticsPresenter(this);

        this.initializeView();
    }

    /**
     * initializes UI elements of the layout
     */
    private void initializeView()
    {
        this.chart = super.findViewById(R.id.chart);
        this.totalCosts = super.findViewById(R.id.totalCosts);
        ArrayList<PieEntry> dataEntries = new ArrayList<>();
        HashMap<String, Float> chartData = this.presenter.getChartData(this);
        Iterator chartDataIterator = chartData.entrySet().iterator();

        while(chartDataIterator.hasNext())
        {
            Map.Entry dataPoint = (Map.Entry)chartDataIterator.next();
            TextView totalCostsForCategory = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(15,0,0,15);
            totalCostsForCategory.setLayoutParams(params);
            totalCostsForCategory.setText(dataPoint.getKey() + " TOTAL: " + User.getInstance().getCurrency() + dataPoint.getValue());
            totalCostsForCategory.setTextSize(12f);
            totalCostsForCategory.setTypeface(Typeface.DEFAULT_BOLD);
            totalCostsForCategory.setTextColor(Color.parseColor("#fffff0"));

            this.totalCosts.addView(totalCostsForCategory);

            if((Float)dataPoint.getValue() != 0)
            {
                dataEntries.add(new PieEntry((Float)dataPoint.getValue(), dataPoint.getKey().toString()));
            }
        }

        PieDataSet dataSet = new PieDataSet(dataEntries, "Your Money-Spending Categories");

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);

        data.setValueTextSize(12f);
        data.setValueTextColor(Color.YELLOW);

        this.chart.setUsePercentValues(true);
        this.chart.getDescription().setText("Percentages of Each Category's Costs");
        this.chart.setDragDecelerationFrictionCoef(0.95f);
        this.chart.setDrawHoleEnabled(true);
        this.chart.setTransparentCircleRadius(55f);
        this.chart.setData(data);
        this.chart.animateY(1000);
        this.chart.invalidate();
    }

    /**
     * Since this is a singleTop Activity, every time user returns to it or
     * tries to access it again, it will return to the same instance, in which
     * case it should re-initialize it in case categories or items data changed.
     */
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        this.initializeView();
    }
}
