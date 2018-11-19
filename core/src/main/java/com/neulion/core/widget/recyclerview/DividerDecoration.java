package com.neulion.core.widget.recyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * User: NeuLion
 */
public class DividerDecoration extends RecyclerView.ItemDecoration
{
    private int mHorizontalSize = 0; //px

    private int mVerticalSize = 0; //px

    private Paint mDividerPaint;

    public DividerDecoration(int horizontalSize, int verticalSize)
    {
        //LogUtil.log(this, "DividerDecoration(" + horizontalSize + "," + verticalSize + ")");

        mDividerPaint = new Paint();

        setHorizontalSize(horizontalSize);

        setVerticalSize(verticalSize);
    }

    public void setHorizontalSize(int horizontalSize)
    {
        mHorizontalSize = horizontalSize;

        mDividerPaint.setStrokeWidth(horizontalSize);
    }

    public void setVerticalSize(int verticalSize)
    {
        mVerticalSize = verticalSize;

        mDividerPaint.setStrokeWidth(mVerticalSize);
    }

    public void setDividerColor(@ColorInt int color)
    {
        mDividerPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        //LogUtil.log(this, "getItemOffsets(" + outRect + ")");

        if (mHorizontalSize > 0)
        {
            outRect.right = mHorizontalSize;
        }
        else if (mVerticalSize > 0)
        {
            outRect.bottom = mVerticalSize;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state)
    {
        //LogUtil.log(this, "onDraw");

        if (shouldDrawDivider())
        {
            if (parent.getLayoutManager() instanceof LinearLayoutManager)
            {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL)
                {
                    drawHorizontalDivider(c, parent);
                }
                else
                {
                    drawVerticalDivider(c, parent);
                }
            }
        }
    }

    private boolean shouldDrawDivider()
    {
        return (mHorizontalSize != 0 || mVerticalSize != 0);
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent)
    {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount - 1; i++)
        {
            View view = parent.getChildAt(i);

            int top = view.getTop();

            int bottom = view.getBottom();

            float left = view.getRight();

            float right = left + mHorizontalSize;

            c.drawRect(left, top, right, bottom, mDividerPaint);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent)
    {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount - 1; i++)
        {
            View view = parent.getChildAt(i);

            int left = view.getLeft();

            int right = view.getRight();

            float top = view.getBottom();

            float bottom = top + mVerticalSize;

            c.drawRect(left, top, right, bottom, mDividerPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, State state)
    {
        //LogUtil.log(this, "onDrawOver");
    }

}

