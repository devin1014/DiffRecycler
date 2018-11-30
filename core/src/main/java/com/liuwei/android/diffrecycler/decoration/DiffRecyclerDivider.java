package com.liuwei.android.diffrecycler.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.liuwei.android.diffrecycler.decoration.DividerHelper.EmptyDivider;
import com.liuwei.android.diffrecycler.decoration.DividerHelper.GridDivider;
import com.liuwei.android.diffrecycler.decoration.DividerHelper.LinearDivider;
import com.liuwei.android.diffrecycler.decoration.DividerHelper.StaggeredGridDivider;

/**
 * User: liuwei
 */
public class DiffRecyclerDivider extends RecyclerView.ItemDecoration
{
    public static final int DIVIDER_COLOR = Color.parseColor("#888888");

    final int dividerSize; // px

    final int dividerMode;

    final Paint dividerPaint;

    private DividerHelper mDividerHelper;

    public DiffRecyclerDivider(int dividerSize, @ColorInt int color, int dividerMode)
    {
        this.dividerSize = dividerSize;

        this.dividerMode = dividerMode;

        dividerPaint = new Paint();

        dividerPaint.setColor(color);

        dividerPaint.setStrokeWidth(dividerSize);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        createDividerIfNeeded(parent);

        mDividerHelper.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state)
    {
        createDividerIfNeeded(parent);

        mDividerHelper.onDraw(c, parent, state);
    }

    private void createDividerIfNeeded(RecyclerView recyclerView)
    {
        if (mDividerHelper == null)
        {
            LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                mDividerHelper = new StaggeredGridDivider(layoutManager, this);
            }
            else if (layoutManager instanceof GridLayoutManager)
            {
                mDividerHelper = new GridDivider(layoutManager, this);
            }
            else if (layoutManager instanceof LinearLayoutManager)
            {
                mDividerHelper = new LinearDivider(layoutManager, this);
            }
            else
            {
                mDividerHelper = new EmptyDivider(layoutManager, this);
            }
        }
    }
}

