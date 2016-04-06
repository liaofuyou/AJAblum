package me.aj.ablum;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SelectImageDecoration extends RecyclerView.ItemDecoration {
    private final int mSpacingHorizontal;
    private final int mSpacingVertical;

    public SelectImageDecoration(int spacingHorizontal, int spacingVertical) {
        super();
        mSpacingHorizontal = spacingHorizontal;
        mSpacingVertical = spacingVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mSpacingHorizontal, // left
                mSpacingVertical,       // top
                mSpacingHorizontal, // right
                mSpacingVertical);      // bottom
    }
}