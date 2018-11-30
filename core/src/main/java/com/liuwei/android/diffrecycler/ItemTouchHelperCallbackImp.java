package com.liuwei.android.diffrecycler;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.liuwei.android.diffrecycler.DiffRecyclerView.ViewHolderTouchStateCallback;
import com.liuwei.android.diffrecycler.holder.FooterViewHolder;
import com.liuwei.android.diffrecycler.holder.HeaderViewHolder;
import com.liuwei.android.diffrecycler.util.DiffRecyclerLogger;

final class ItemTouchHelperCallbackImp extends ItemTouchHelper.Callback
{
    private final float ALPHA_FULL = 1.0f;

    private HeaderFooterAdapter mHeaderFooterAdapter;

    private DataListAdapter mAdapter;

    private boolean mLongPressDragEnabled;

    private boolean mItemViewSwipeEnabled;

    private ViewHolderTouchStateCallback mViewHolderTouchStateCallback;

    ItemTouchHelperCallbackImp(boolean longPressDragEnabled, boolean itemViewSwipeEnabled)
    {
        mLongPressDragEnabled = longPressDragEnabled;

        mItemViewSwipeEnabled = itemViewSwipeEnabled;
    }

    void attachItemTouchHelperToRecyclerView(DiffRecyclerView recyclerView, HeaderFooterAdapter headerFooterAdapter, DataListAdapter dataListAdapter)
    {
        if (recyclerView != null)
        {
            mHeaderFooterAdapter = headerFooterAdapter;

            mAdapter = dataListAdapter;

            new ItemTouchHelper(this).attachToRecyclerView(recyclerView);
        }
    }

    void setOnViewHolderTouchStateCallback(ViewHolderTouchStateCallback callback)
    {
        mViewHolderTouchStateCallback = callback;
    }

    @Override
    public boolean isLongPressDragEnabled()
    {
        return mLongPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled()
    {
        return mItemViewSwipeEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder)
    {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager || recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager)
        {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        else
        {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public void onSelectedChanged(ViewHolder viewHolder, int actionState)
    {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
        {
            DiffRecyclerLogger.info(this, String.format("onSelectedChanged(viewHolder = %s , actionState = %s)", getViewHolderInfo(viewHolder), getState(actionState)));
        }

        super.onSelectedChanged(viewHolder, actionState);

        if (mViewHolderTouchStateCallback != null)
        {
            mViewHolderTouchStateCallback.onViewHolderTouchStateChanged(viewHolder, actionState);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target)
    {
        DiffRecyclerLogger.warn(this, String.format("onMove(viewHolder = %s , target = %s)", getViewHolderInfo(viewHolder), getViewHolderInfo(target)));

        if (viewHolder.getItemViewType() != target.getItemViewType())
        {
            return false;
        }

        if (viewHolder instanceof HeaderViewHolder || target instanceof HeaderViewHolder)
        {
            return false;
        }

        mAdapter.moveItem(getViewHolderPosition(viewHolder), getViewHolderPosition(target));

        return true;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction)
    {
        DiffRecyclerLogger.warn(this, String.format("onSwiped(viewHolder = %s , direction = %s)", getViewHolderInfo(viewHolder), getDirection(direction)));

        if (viewHolder instanceof HeaderViewHolder)
        {
            mHeaderFooterAdapter.removeAllHeader();
        }
        else if (viewHolder instanceof FooterViewHolder)
        {
            mHeaderFooterAdapter.removeAllFooter();
        }
        else
        {
            mAdapter.removeItem(getViewHolderPosition(viewHolder));
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
        {
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
        else
        {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder)
    {
        DiffRecyclerLogger.log(this, String.format("clearView(viewHolder = %s)", getViewHolderInfo(viewHolder)));

        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);
    }

    private String getViewHolderInfo(ViewHolder viewHolder)
    {
        if (viewHolder == null)
        {
            return "null";
        }

        return String.format("@%s , pos = %s , adapterPos = %s", Integer.toHexString(viewHolder.hashCode()), getViewHolderPosition(viewHolder), viewHolder.getAdapterPosition());
    }

    private int getViewHolderPosition(ViewHolder viewHolder)
    {
        if (viewHolder == null)
        {
            return -1;
        }

        final int viewHolderPos = viewHolder.getAdapterPosition();

        if (mHeaderFooterAdapter.isHeader(viewHolderPos) || mHeaderFooterAdapter.isFooter(viewHolderPos))
        {
            return viewHolderPos;
        }

        return viewHolderPos - (mHeaderFooterAdapter.hasHeaders() ? 1 : 0);
    }

    private String getState(int state)
    {
        switch (state)
        {
            case ItemTouchHelper.ACTION_STATE_DRAG:
                return "drag";
            case ItemTouchHelper.ACTION_STATE_SWIPE:
                return "swipe";
            case ItemTouchHelper.ACTION_STATE_IDLE:
                return "idle";
            default:
                return String.valueOf(state);
        }
    }

    private String getDirection(int direction)
    {
        switch (direction)
        {
            case ItemTouchHelper.LEFT:
                return "left";
            case ItemTouchHelper.RIGHT:
                return "right";
            case ItemTouchHelper.UP:
                return "up";
            case ItemTouchHelper.DOWN:
                return "down";
            case ItemTouchHelper.START:
                return "start";
            case ItemTouchHelper.END:
                return "end";
            default:
                return "direct:" + direction;
        }
    }
}
