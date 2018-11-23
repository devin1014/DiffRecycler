package com.neulion.android.diffrecycler;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView.Adapter;

import com.neulion.android.diffrecycler.util.DiffRecyclerLogger;

final class DiffCompareListUpdateCallbackImp implements ListUpdateCallback
{
    private Adapter mAdapter;

    DiffCompareListUpdateCallbackImp(Adapter adapter)
    {
        mAdapter = adapter;
    }

    @Override
    public void onInserted(int position, int count)
    {
        DiffRecyclerLogger.info(this, String.format("onInserted(position = %s , count = %s)", position, count));

        mAdapter.notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count)
    {
        DiffRecyclerLogger.info(this, String.format("onRemoved(position = %s , count = %s)", position, count));

        mAdapter.notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition)
    {
        DiffRecyclerLogger.info(this, String.format("onMoved(fromPosition = %s , toPosition = %s)", fromPosition, toPosition));

        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload)
    {
        DiffRecyclerLogger.info(this, String.format("onChanged(position = %s , count = %s , payload = %s)", position, count, payload));

        mAdapter.notifyItemRangeChanged(position, count, payload);
    }
}
