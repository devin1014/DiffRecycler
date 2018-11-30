package com.liuwei.android.diffrecycler.diff;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.liuwei.android.diffrecycler.util.DiffRecyclerLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: liuwei
 */
public final class DiffComparableCallback<T> extends DiffUtil.Callback
{
    private List<DiffComparable> mOldList;

    private List<DiffComparable> mNewList;

    private final int mOldListSize;

    private final int mNewListSize;

    public DiffComparableCallback(List<T> oldList, List<T> newList)
    {
        DiffRecyclerLogger.set(this);

        mOldList = convertList(oldList);

        mNewList = convertList(newList);

        mOldListSize = Math.max(getListSize(oldList), getListSize(mOldList));

        mNewListSize = Math.max(getListSize(newList), getListSize(mNewList));

        DiffRecyclerLogger.test(this, String.format("convert list item to %s !", DiffComparable.class));

        if (mOldList != null && mNewList != null)
        {
            DiffRecyclerLogger.log(this, "'list as follow:'");

            for (int i = 0; i < Math.max(mOldList != null ? mOldList.size() : 0, mNewList != null ? mNewList.size() : 0); i++)
            {
                DiffRecyclerLogger.log(this, String.format("pos = %s", i));

                DiffComparable oldItem = mOldList != null && i < mOldList.size() ? mOldList.get(i) : null;

                DiffComparable newItem = mNewList != null && i < mNewList.size() ? mNewList.get(i) : null;

                DiffRecyclerLogger.log(this, String.format("    oldItem = %s", oldItem != null ? oldItem.getItems().toString() : "NULL"));

                DiffRecyclerLogger.log(this, String.format("    newItem = %s", newItem != null ? newItem.getItems().toString() : "NULL"));

                DiffRecyclerLogger.log(this, String.format("    oldContent = %s", oldItem != null ? oldItem.getContents().toString() : "NULL"));

                DiffRecyclerLogger.log(this, String.format("    newContent = %s", newItem != null ? newItem.getContents().toString() : "NULL"));
            }
        }
    }

    private List<DiffComparable> convertList(List<T> list)
    {
        if (list == null)
        {
            return null;
        }

        ArrayList<DiffComparable> result = new ArrayList<>(list.size());

        for (T t : list)
        {
            try
            {
                result.add(newInstance(t));
            }
            catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }
        }

        return result;
    }

    @Override
    public int getOldListSize()
    {
        return mOldListSize;
    }

    @Override
    public int getNewListSize()
    {
        return mNewListSize;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
    {
        if (mOldList == null || mNewList == null)
        {
            return false;
        }

        try
        {
            boolean itemSame = mOldList.get(oldItemPosition).compareItem(mNewList.get(newItemPosition));

            if (!itemSame)
            {
                DiffRecyclerLogger.info(this, String.format("compareItem(old=%s,new=%s) [changed]", oldItemPosition, newItemPosition));
            }

            return itemSame;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
    {
        if (mOldList == null || mNewList == null)
        {
            return false;
        }

        try
        {
            boolean contentSame = mOldList.get(oldItemPosition).compareContent(mNewList.get(newItemPosition));

            if (!contentSame)
            {
                DiffRecyclerLogger.info(this, String.format("compareContent(old = %s , new = %s) [changed]", oldItemPosition, newItemPosition));
            }

            return contentSame;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition)
    {
        if (mOldList == null || mNewList == null)
        {
            return null;
        }

        try
        {
            return mOldList.get(oldItemPosition).getChangePayload(mNewList.get(newItemPosition));
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }

    private DiffComparable<?> newInstance(T v) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Class<?> cls = Class.forName(v.getClass().getName() + "DiffComparableImp");

        Constructor<?> constructor = cls.getConstructor(v.getClass());

        return (DiffComparable) constructor.newInstance(v);
    }

    private int getListSize(List<?> list)
    {
        return list != null ? list.size() : 0;
    }
}

