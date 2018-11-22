package com.neulion.android.diffrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;
import com.neulion.android.diffrecycler.holder.DataBindingHolder;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

/**
 * User: NeuLion
 */
public abstract class DataBindingAdapter<T extends DataDiffCompare<T>> extends BaseDiffRecyclerAdapter<T, DataBindingHolder<T>> implements OnItemClickListener<T>
{
    private OnItemClickListener<T> mOnItemClickListener;

    public DataBindingAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        super(layoutInflater);

        mOnItemClickListener = listener;
    }

    @Override
    public DataBindingHolder<T> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new DataBindingHolder<>(inflater, parent, getLayout(viewType), mOnItemClickListener != null ? this : null);
    }

    protected abstract int getLayout(int viewType);

    @Override
    public void onBindViewHolder(DataBindingHolder<T> holder, T t, int position)
    {
        holder.setData(t);
    }

    @Override
    public void onItemClick(View view, T t)
    {
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onItemClick(view, t);
        }
    }

}
