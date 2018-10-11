package com.neulion.core.widget.recyclerview.holder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-14
 * Time: 14:54
 */
public abstract class BaseViewHolder<T> extends ViewHolder
{
    private SparseArray<View> mViewSparseArray;

    public BaseViewHolder(View itemView)
    {
    super(itemView);

    mViewSparseArray = new SparseArray<>();
}

    @SuppressWarnings("unchecked")
    public final <V extends View> V findViewById(int id)
    {
        View cacheView = mViewSparseArray.get(id);

        if (cacheView == null)
        {
            View view = itemView.findViewById(id);

            mViewSparseArray.put(id, view);

            cacheView = view;
        }

        return (V) cacheView;
    }
}