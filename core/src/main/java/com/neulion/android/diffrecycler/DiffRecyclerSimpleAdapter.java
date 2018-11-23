package com.neulion.android.diffrecycler;

import android.view.LayoutInflater;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;
import com.neulion.android.diffrecycler.holder.DiffViewHolder;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

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
