package com.neulion.core.widget.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: NeuLion
 */
public abstract class DataBindingAdapter<T extends Comparable<T>> extends BaseRecyclerViewAdapter<T, DataBindingHolder<T>> implements OnItemClickListener<T>
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
