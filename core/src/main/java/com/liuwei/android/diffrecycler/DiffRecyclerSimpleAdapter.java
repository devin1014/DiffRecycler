package com.liuwei.android.diffrecycler;

import android.view.LayoutInflater;

import com.liuwei.android.diffrecycler.diff.DataDiffCompare;
import com.liuwei.android.diffrecycler.holder.DiffViewHolder;
import com.liuwei.android.diffrecycler.listener.OnItemClickListener;

public final class DiffRecyclerSimpleAdapter<T extends DataDiffCompare<T>> extends DiffRecyclerAdapter<T>
{
    private int mViewHolderLayout;

    @SuppressWarnings("unused")
    public DiffRecyclerSimpleAdapter(LayoutInflater layoutInflater, int viewHolderLayout)
    {
        this(layoutInflater, viewHolderLayout, null);
    }

    @SuppressWarnings("WeakerAccess")
    public DiffRecyclerSimpleAdapter(LayoutInflater layoutInflater, int viewHolderLayout, OnItemClickListener<T> listener)
    {
        super(layoutInflater, listener);

        mViewHolderLayout = viewHolderLayout;
    }

    @Override
    protected int getViewHolderLayout(int viewType)
    {
        return mViewHolderLayout;
    }

    @Override
    public void onBindViewHolder(DiffViewHolder<T> holder, T t, int position)
    {
        holder.getViewDataBindingInterface().executePendingBindings(t);
    }
}
