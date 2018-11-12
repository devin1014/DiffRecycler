package com.neulion.core.widget.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:33
 */
public abstract class DataBindingAdapter<T> extends BaseRecyclerViewAdapter<T, DataBindingHolder<T>> implements OnItemClickListener<T>
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
        return new DataBindingHolder<>(inflater, parent, getLayout(viewType), this);
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
