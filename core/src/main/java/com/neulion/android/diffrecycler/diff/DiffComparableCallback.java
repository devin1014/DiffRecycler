package com.neulion.android.diffrecycler.diff;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.neulion.android.diffrecycler.util.LogUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion
 */
public final class DiffComparableCallback<T> extends DiffUtil.Callback
{
    private List<DiffComparable> mOldList;

    private List<DiffComparable> mNewList;

    public DiffComparableCallback(List<T> oldList, List<T> newList)
    {
        LogUtil.set(this);

        mOldList = convertList(oldList);

        mNewList = convertList(newList);

        LogUtil.test(this, "convert list item to IDiffComparable object!");
    }

    private List<DiffComparable> convertList(List<T> list)
    {
        ArrayList<DiffComparable> result = null;

        if (list != null)
        {
            result = new ArrayList<>(list.size());

            for (T t : list)
            {
                result.add(newInstance(t));
            }
        }

        return result;
    }

    @Override
    public int getOldListSize()
    {
        int size = mOldList != null ? mOldList.size() : 0;

        LogUtil.warn(this, String.format("getOldListSize=%s", size));

        return size;
    }

    @Override
    public int getNewListSize()
    {
        int size = mNewList != null ? mNewList.size() : 0;

        LogUtil.warn(this, String.format("getNewListSize=%s", size));

        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
    {
        try
        {
            boolean itemSame = mOldList.get(oldItemPosition).compareItem(mNewList.get(newItemPosition));

            if (!itemSame)
            {
                LogUtil.info(this, String.format("compareItem(old=%s,new=%s) [changed]", oldItemPosition, newItemPosition));
            }

            return itemSame;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
    {
        try
        {
            boolean contentSame = mOldList.get(oldItemPosition).compareContent(mNewList.get(newItemPosition));

            if (!contentSame)
            {
                LogUtil.info(this, String.format("compareContent(old=%s,new=%s) [changed]", oldItemPosition, newItemPosition));
            }

            return contentSame;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition)
    {
        try
        {
            Object object = mOldList.get(oldItemPosition).getChangePayload(mNewList.get(newItemPosition));

            LogUtil.info(this, String.format("getChangePayload(old=%s,new=%s,object=%s)", oldItemPosition, newItemPosition, object));

            return object;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private DiffComparable<?> newInstance(T v)
    {
        try
        {
            Class<?> cls = Class.forName(v.getClass().getName() + "DiffComparableImp");

            Constructor<?> constructor = cls.getConstructor(v.getClass());

            return (DiffComparable) constructor.newInstance(v);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }
}

