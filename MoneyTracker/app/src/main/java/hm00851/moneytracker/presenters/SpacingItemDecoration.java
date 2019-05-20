package hm00851.moneytracker.presenters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * class needed to manage the spacing between items in the grid layout
 * of the recycler view.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration
{
    /** the spacing value in pixels */
    private int spacing;

    public SpacingItemDecoration(int spacing)
    {
        this.spacing = spacing;
    }

    /**
     * method overrided from RecyclerView.ItemDecoration class;
     * outRect is what manages spacing of each side of each item in
     * the recycler view.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.left = this.spacing;
        outRect.right = this.spacing;
        outRect.bottom = this.spacing;

        // if element in recycler view is first or second i.e. in the first row
        // of the grid layout, apply spacing above it in order for it not to look attached
        // to the tab.
        if(parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1)
        {
            outRect.top = spacing;
        }
        else
        {
            outRect.top = 0;
        }
    }
}