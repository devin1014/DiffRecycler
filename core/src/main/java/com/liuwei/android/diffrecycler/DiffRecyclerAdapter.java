package com.liuwei.android.diffrecycler;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuwei.android.diffrecycler.diff.DataDiffCompare;
import com.liuwei.android.diffrecycler.holder.DiffViewHolder;
import com.liuwei.android.diffrecycler.listener.OnItemClickListener;

import java.util.List;

/**
 * User: liuwei
 */
public abstract class DiffRecyclerAdapter<T extends DataDiffCompare<T>> extends DiffRecyclerBaseAdapter<T, DiffViewHolder<T>> implements OnItemClickListener<T>, DiffCompareAsyncTask.Callback<T>
{
    private OnItemClickListener<T> mOnItemClickListener;

    public DiffRecyclerAdapter(LayoutInflater layoutInflater)
    {
        this(layoutInflater, null);
    }

    public DiffRecyclerAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        super(layoutInflater);

        mOnItemClickListener = listener;

        setDiffCompareEnabled(true);

        setDetectMovesEnabled(false);
    }

    @Override
    public DiffViewHolder<T> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return onCreateViewHolder(inflater.inflate(getViewHolderLayout(viewType), parent, false));
    }

    protected DiffViewHolder<T> onCreateViewHolder(View inflaterView)
    {
        return new DiffViewHolder<>(inflaterView, mOnItemClickListener != null ? this : null);
    }

    protected abstract int getViewHolderLayout(int viewType);

    @Override
    public void onItemClick(View view, T t)
    {
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onItemClick(view, t);
        }
    }

    private boolean mDiffCompareEnabled = true; //default enable diff compare

    public void setDiffCompareEnabled(boolean enabled)
    {
        mDiffCompareEnabled = enabled;
    }

    private boolean mDetectMoves = false; //default disable detect moves

    public void setDetectMovesEnabled(boolean enabled)
    {
        mDetectMoves = enabled;
    }

    private DiffCompareAsyncTask<T> mCompareTask;

    @Override
    public void setData(List<T> list)
    {
        if (mDiffCompareEnabled && getDataList() != null && getDataList().size() > 0)
        {
            if (mCompareTask != null)
            {
                mCompareTask.cancel(true);
            }

            (mCompareTask = new DiffCompareAsyncTask<>(getDataList(), list, this, mDetectMoves)).execute();
        }
        else
        {
            super.setData(list);
        }
    }

    @Override
    public void onCompareResult(List<T> oldList, List<T> newList, @Nullable DiffResult diffResult)
    {
        setDataNoNotifyChanged(newList);

        if (diffResult != null)
        {
            diffResult.dispatchUpdatesTo(mUpdateCallback);
        }
        else
        {
            notifyDataSetChanged();
        }
    }

    private ListUpdateCallbackImp mUpdateCallback = new ListUpdateCallbackImp(this);

    // -------------------------------------------------------------------------------------------------------
    // - Callback
    // -------------------------------------------------------------------------------------------------------
    static class ListUpdateCallbackImp implements ListUpdateCallback
    {
        private Adapter mAdapter;

        ListUpdateCallbackImp(Adapter adapter)
        {
            mAdapter = adapter;
        }

        @Override
        public void onInserted(int position, int count)
        {
            mAdapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count)
        {
            mAdapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition)
        {
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload)
        {
            mAdapter.notifyItemRangeChanged(position, count, payload);
        }
    }
}
