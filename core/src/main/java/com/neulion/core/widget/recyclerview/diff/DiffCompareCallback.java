package com.neulion.core.widget.recyclerview.diff;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.neulion.core.widget.recyclerview.util.LogUtil;

import java.util.List;

/**
 * User: NeuLion
 */
public final class DiffCompareCallback<T extends IDiffComparable<T>> extends DiffUtil.Callback
{
    private List<T> mOldList;

    private List<T> mNewList;

    public DiffCompareCallback(List<T> oldList, List<T> newList)
    {
        mOldList = oldList;

        mNewList = newList;
    }

    @Override
    public int getOldListSize()
    {
        int size = mOldList != null ? mOldList.size() : 0;

        LogUtil.info(this, "getOldListSize:" + size);

        return size;
    }

    @Override
    public int getNewListSize()
    {
        int size = mNewList != null ? mNewList.size() : 0;

        LogUtil.info(this, "getNewListSize:" + size);

        return size;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
    {
        LogUtil.info(this, "areItemsTheSame:[oldItemPosition=" + oldItemPosition + ",newItemPosition=" + newItemPosition + "]");

        return mOldList.get(oldItemPosition).compareObject(mNewList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
    {
        LogUtil.info(this, "areContentsTheSame:[oldItemPosition=" + oldItemPosition + ",newItemPosition=" + newItemPosition + "]");

        return mOldList.get(oldItemPosition).compareContent(mNewList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition)
    {
        LogUtil.info(this, "getChangePayload:[oldItemPosition=" + oldItemPosition + ",newItemPosition=" + newItemPosition + "]");

        return mOldList.get(oldItemPosition).getChangePayload(mNewList.get(newItemPosition));
    }

}
