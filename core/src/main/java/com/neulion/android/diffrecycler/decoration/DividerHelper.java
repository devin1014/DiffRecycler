package com.neulion.android.diffrecycler.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

abstract class DividerHelper
{
    static final int MODE_INNER = 0;

    static final int MODE_START = 1;

    static final int MODE_END = 2;

    final Paint mDividerPaint;

    final int mDividerSize;

    final int mDividerMode;

    DividerHelper(DiffRecyclerDivider recyclerDivider)
    {
        mDividerPaint = recyclerDivider.dividerPaint;

        mDividerSize = recyclerDivider.dividerSize;

        mDividerMode = recyclerDivider.dividerMode;
    }

    public abstract void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);

    public abstract void onDraw(Canvas c, RecyclerView parent, State state);

    boolean isLastItem(View view, RecyclerView recyclerView)
    {
        return recyclerView.getAdapter().getItemCount() == recyclerView.getChildViewHolder(view).getAdapterPosition() + 1;
    }

    // ------------------------------------------------------------------------------------------------------------
    // - LinearLayoutManager
    // ------------------------------------------------------------------------------------------------------------
    static class LinearDivider extends DividerHelper
    {
        private LinearLayoutManager mLayoutManager;

        LinearDivider(LayoutManager layoutManager, DiffRecyclerDivider recyclerDivider)
        {
            super(recyclerDivider);

            mLayoutManager = (LinearLayoutManager) layoutManager;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, State state)
        {
            if (mLayoutManager.canScrollHorizontally())
            {
                if (mDividerMode == MODE_START)
                {
                    outRect.left = mDividerSize;
                }
                else if (!isLastItem(view, recyclerView))
                {
                    outRect.right = mDividerSize;
                }
            }
            else
            {
                if (mDividerMode == MODE_START)
                {
                    outRect.top = mDividerSize;
                }
                else if (!isLastItem(view, recyclerView))
                {
                    outRect.bottom = mDividerSize;
                }
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state)
        {
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                if (mDividerMode == MODE_INNER)
                {
                    if (i == parent.getChildCount() - 1)
                    {
                        break;
                    }
                }

                View view = parent.getChildAt(i);

                if (view != null)
                {
                    if (parent.getLayoutManager().canScrollHorizontally())
                    {
                        if (mDividerMode == MODE_INNER || mDividerMode == MODE_END)
                        {
                            drawVerticalLineInViewEnd(c, mDividerPaint, view);
                        }
                        else if (mDividerMode == MODE_START)
                        {
                            drawVerticalLineInViewStart(c, mDividerPaint, view);
                        }
                    }
                    else
                    {
                        if (mDividerMode == MODE_INNER || mDividerMode == MODE_END)
                        {
                            drawHorizontalLineInViewEnd(c, mDividerPaint, view);
                        }
                        else if (mDividerMode == MODE_START)
                        {
                            drawHorizontalLineInViewStart(c, mDividerPaint, view);
                        }
                    }
                }
            }
        }

        private void drawHorizontalLineInViewEnd(Canvas c, Paint paint, View view)
        {
            int left = view.getLeft();
            int right = view.getRight() + mDividerSize;
            int top = view.getBottom();
            int bottom = view.getBottom() + mDividerSize;

            c.drawRect(left, top, right, bottom, paint);
        }

        private void drawHorizontalLineInViewStart(Canvas c, Paint paint, View view)
        {
            int left = view.getLeft();
            int right = view.getRight() + mDividerSize;
            int top = view.getTop() - mDividerSize;
            int bottom = view.getTop();

            c.drawRect(left, top, right, bottom, paint);
        }

        private void drawVerticalLineInViewEnd(Canvas c, Paint paint, View view)
        {
            int top = view.getTop();
            int bottom = view.getBottom() + mDividerSize;
            int left = view.getRight();
            int right = view.getRight() + mDividerSize;

            c.drawRect(left, top, right, bottom, paint);
        }

        private void drawVerticalLineInViewStart(Canvas c, Paint paint, View view)
        {
            int top = view.getTop();
            int bottom = view.getBottom() + mDividerSize;
            int left = view.getLeft() - mDividerSize;
            int right = view.getLeft();

            c.drawRect(left, top, right, bottom, paint);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // - GridLayoutManager
    // ------------------------------------------------------------------------------------------------------------
    static class GridDivider extends DividerHelper
    {
        private GridLayoutManager mLayoutManager;

        private int mSpanCount;

        GridDivider(LayoutManager layoutManager, DiffRecyclerDivider recyclerDivider)
        {
            super(recyclerDivider);

            mLayoutManager = (GridLayoutManager) layoutManager;

            mSpanCount = mLayoutManager.getSpanCount();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, State state)
        {
            ViewHolder viewHolder = recyclerView.getChildViewHolder(view);

            int adapterPos = viewHolder.getAdapterPosition();

            if (mLayoutManager.canScrollHorizontally())
            {
                if ((adapterPos + 1) % mSpanCount != 0)//right side item not draw divider
                {
                    outRect.bottom = mDividerSize;
                }

                int offset = recyclerView.getAdapter().getItemCount() % mSpanCount;

                if (offset == 0)
                {
                    if (adapterPos < recyclerView.getAdapter().getItemCount() - mSpanCount)// last bottom item not draw divider
                    {
                        outRect.right = mDividerSize;
                    }
                }
                else
                {
                    if (adapterPos < recyclerView.getAdapter().getItemCount() - offset)// last bottom item not draw divider
                    {
                        outRect.right = mDividerSize;
                    }
                }
            }
            else
            {
                if ((adapterPos + 1) % mSpanCount != 0)//right side item not draw divider
                {
                    outRect.right = mDividerSize;
                }

                int offset = recyclerView.getAdapter().getItemCount() % mSpanCount;

                if (offset == 0)
                {
                    if (adapterPos < recyclerView.getAdapter().getItemCount() - mSpanCount)// last bottom item not draw divider
                    {
                        outRect.bottom = mDividerSize;
                    }
                }
                else
                {
                    if (adapterPos < recyclerView.getAdapter().getItemCount() - offset)// last bottom item not draw divider
                    {
                        outRect.bottom = mDividerSize;
                    }
                }
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, State state)
        {
            int childMax;

            int offset = recyclerView.getAdapter().getItemCount() % mSpanCount;

            if (offset == 0)
            {
                childMax = recyclerView.getAdapter().getItemCount() - mSpanCount;
            }
            else
            {
                childMax = recyclerView.getAdapter().getItemCount() - offset;
            }

            if (mLayoutManager.canScrollHorizontally())
            {
                //draw column
                for (int i = 0; i < recyclerView.getChildCount(); i += mSpanCount)
                {
                    View childView = recyclerView.getChildAt(i);

                    if (childView != null)
                    {
                        int left = childView.getRight();
                        int right = childView.getRight() + mDividerSize;

                        int top = childView.getTop();
                        int bottom = childView.getBottom();

                        for (int j = 0; j < mSpanCount && i + j < recyclerView.getChildCount(); j++)
                        {
                            bottom = recyclerView.getChildAt(j).getBottom();
                        }

                        c.drawRect(left, top, right, bottom, mDividerPaint);
                    }
                }

                for (int i = 0; i < mSpanCount - 1 && i < recyclerView.getChildCount(); i++)
                {
                    View childView = recyclerView.getChildAt(i);

                    if (childView != null)
                    {
                        int top = childView.getBottom();
                        int bottom = childView.getBottom() + mDividerSize;

                        int left = childView.getLeft();
                        int right = childView.getRight();

                        for (int j = i; j < recyclerView.getChildCount(); j += mSpanCount)
                        {
                            right = recyclerView.getChildAt(j).getRight();
                        }

                        c.drawRect(left, top, right, bottom, mDividerPaint);
                    }
                }
            }
            else
            {
                //draw row
                for (int i = 0; i < childMax; i += mSpanCount)
                {
                    View childView = recyclerView.getChildAt(i);

                    if (childView != null)
                    {
                        int top = childView.getBottom();
                        int bottom = top + mDividerSize;

                        int left = childView.getLeft();
                        int right = childView.getRight();

                        for (int j = 0; j < mSpanCount && i + j < recyclerView.getChildCount(); j++)
                        {
                            right = recyclerView.getChildAt(i + j).getRight();
                        }

                        c.drawRect(left, top, right, bottom, mDividerPaint);
                    }
                }

                for (int i = 0; i < mSpanCount - 1 && i < recyclerView.getChildCount(); i++)
                {
                    View childView = recyclerView.getChildAt(i);

                    if (childView != null)
                    {
                        int left = childView.getRight();
                        int right = childView.getRight() + mDividerSize;

                        int top = childView.getTop();
                        int bottom = childView.getBottom();

                        for (int j = i; j < recyclerView.getChildCount(); j += mSpanCount)
                        {
                            bottom = recyclerView.getChildAt(j).getBottom();
                        }

                        c.drawRect(left, top, right, bottom, mDividerPaint);
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // - StaggeredGridLayoutManager
    // ------------------------------------------------------------------------------------------------------------
    static class StaggeredGridDivider extends DividerHelper
    {
        private StaggeredGridLayoutManager mLayoutManager;

        StaggeredGridDivider(LayoutManager layoutManager, DiffRecyclerDivider recyclerDivider)
        {
            super(recyclerDivider);

            mLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, State state)
        {
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state)
        {
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // - Empty
    // ------------------------------------------------------------------------------------------------------------
    static class EmptyDivider extends DividerHelper
    {
        @SuppressWarnings("unused")
        EmptyDivider(LayoutManager layoutManager, DiffRecyclerDivider recyclerDivider)
        {
            super(recyclerDivider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, State state)
        {
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state)
        {
        }
    }
}
