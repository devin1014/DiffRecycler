package com.neulion.android.diffrecycler.diff;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.neulion.android.diffrecycler.util.DiffRecyclerLogger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion
 */
public final class DiffComparableCallback<T> extends DiffUtil.Callback
{
    @NonNull
    private List<DiffComparable> mOldList;
    @NonNull
    private List<DiffComparable> mNewList;

    private int mOldListSize = -1;

    private int mNewListSize = -1;

    public DiffComparableCallback(List<T> oldList, List<T> newList)
    {
        DiffRecyclerLogger.set(this);

        mOldList = convertList(oldList);

        mNewList = convertList(newList);

        DiffRecyclerLogger.test(this, String.format("convert list item to %s !", DiffComparable.class));

        DiffRecyclerLogger.log(this, "'list as follow:'");

        for (int i = 0; i < Math.max(mOldList.size(), mNewList.size()); i++)
        {
            DiffRecyclerLogger.log(this, String.format("pos = %s", i));

            DiffComparable oldItem = i < mOldList.size() ? mOldList.get(i) : null;

            DiffComparable newItem = i < mNewList.size() ? mNewList.get(i) : null;

            DiffRecyclerLogger.log(this, String.format("    oldItem = %s", oldItem != null ? oldItem.getItems().toString() : "NULL"));

            DiffRecyclerLogger.log(this, String.format("    newItem = %s", newItem != null ? newItem.getItems().toString() : "NULL"));

            DiffRecyclerLogger.log(this, String.format("    oldContent = %s", oldItem != null ? oldItem.getContents().toString() : "NULL"));

            DiffRecyclerLogger.log(this, String.format("    newContent = %s", newItem != null ? newItem.getContents().toString() : "NULL"));
        }
    }

    private List<DiffComparable> convertList(List<T> list)
    {
        ArrayList<DiffComparable> result = new ArrayList<>();

        if (list != null)
        {
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
        if (mOldListSize == -1)
        {
            mOldListSize = mOldList.size();

            DiffRecyclerLogger.warn(this, String.format("getOldListSize = %s", mOldListSize));
        }

        return mOldListSize;
    }

    @Override
    public int getNewListSize()
    {
        if (mNewListSize == -1)
        {
            mNewListSize = mNewList.size();

            DiffRecyclerLogger.warn(this, String.format("getNewListSize = %s", mNewListSize));
        }

        return mNewListSize;
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
                DiffRecyclerLogger.info(this, String.format("compareItem(old=%s,new=%s) [changed]", oldItemPosition, newItemPosition));
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
                DiffRecyclerLogger.info(this, String.format("compareContent(old = %s , new = %s) [changed]", oldItemPosition, newItemPosition));
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
            return mOldList.get(oldItemPosition).getChangePayload(mNewList.get(newItemPosition));

            //DiffRecyclerLogger.info(this, String.format("getChangePayload(old = %s,new = %s , object = %s)", oldItemPosition, newItemPosition, object));
            //
            //return object;
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

