package com.neulion.core.widget.recyclerview.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:34
 */
public class DataBindingHolder<T> extends BaseViewHolder<T>
{
    private final ViewDataBinding mViewDataBinding;

    private OnItemClickListener<T> mOnItemClickListener;

    public DataBindingHolder(View itemView, OnItemClickListener<T> handler)
    {
        super(itemView);

        mOnItemClickListener = handler;

        mViewDataBinding = DataBindingUtil.bind(itemView);
    }

    public DataBindingHolder(LayoutInflater inflater, ViewGroup parent, int layoutId, OnItemClickListener<T> handler)
    {
        this(inflater.inflate(layoutId, parent, false), handler);
    }

    @SuppressWarnings("unused")
    public OnItemClickListener<T> getOnItemClickListener()
    {
        return mOnItemClickListener;
    }

    @SuppressWarnings("unchecked")
    public final <DB extends ViewDataBinding> DB getViewDataBinding()
    {
        return (DB) mViewDataBinding;
    }

}
