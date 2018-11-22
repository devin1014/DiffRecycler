package com.neulion.android.diffrecycler.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.neulion.android.diffrecycler.diff.DataComparable;
import com.neulion.android.diffrecycler.holder.BaseViewHolder;
import com.neulion.android.diffrecycler.util.DiffRecyclerLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion
 */
public abstract class BaseRecyclerViewAdapter<T extends DataComparable<T>, Holder extends BaseViewHolder<T>> extends Adapter<Holder>
{
    private final LayoutInflater mLayoutInflater;

    private List<T> mDataList;

    public BaseRecyclerViewAdapter(LayoutInflater inflater)
    {
        mLayoutInflater = inflater;

        mDataList = new ArrayList<>();
    }

    @Override
    public final Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        DiffRecyclerLogger.info(this, String.format("onCreateViewHolder(viewType = %s)", viewType));

        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    public abstract Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads)
    {
        DiffRecyclerLogger.log(this, String.format("onBindHolder(holder = @%s , position = %s , payloads = %s", Integer.toHexString(holder.hashCode()), position, payloads));

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public final void onBindViewHolder(Holder holder, int position)
    {
        onBindViewHolder(holder, getItem(position), position);
    }

    //TODO:should add List<Object> payloads
    public abstract void onBindViewHolder(Holder holder, T t, int position);

    @Override
    public int getItemCount()
    {
        return mDataList != null ? mDataList.size() : 0;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Data
    // ----------------------------------------------------------------------------------------------------------------------------------
    public void setData(List<T> list)
    {
        if (mDataList != list)
        {
            final List<T> oldList = mDataList;

            mDataList = list;

            onDataSetChanged(oldList, mDataList);
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected void onDataSetChanged(List<T> oldList, List<T> newList)
    {
        DiffRecyclerLogger.warn(this, "onDataSetChanged()");

        if (oldList == null)
        {
            DiffRecyclerLogger.warn(this, String.format("    oldList = %s , size = %s", "NULL", 0));
        }
        else
        {
            DiffRecyclerLogger.warn(this, String.format("    oldList = @%s , size = %s", Integer.toHexString(oldList.hashCode()), oldList.size()));
        }

        if (newList == null)
        {
            DiffRecyclerLogger.warn(this, String.format("    newList = %s , size = %s", "NULL", 0));
        }
        else
        {
            DiffRecyclerLogger.warn(this, String.format("    newList = @%s , size = %s", Integer.toHexString(newList.hashCode()), newList.size()));
        }
    }

    @SuppressWarnings("unused")
    public final void appendData(T t)
    {
        appendData(t, getDataList().size());
    }

    @SuppressWarnings("WeakerAccess")
    public final void appendData(T t, int pos)
    {
        mDataList.add(pos, t);

        notifyItemInserted(pos);
    }

    @SuppressWarnings("unused")
    public final void appendDataList(List<T> list)
    {
        appendDataList(list, mDataList.size());
    }

    @SuppressWarnings("WeakerAccess")
    public final void appendDataList(List<T> list, int index)
    {
        if (list != null && list.size() > 0)
        {
            mDataList.addAll(index, list);

            notifyItemRangeInserted(index, list.size());
        }
    }

    public final void removeItem(T t)
    {
        removeItem(findItemPosition(t));
    }

    @SuppressWarnings("WeakerAccess")
    public final void removeItem(int pos)
    {
        if (pos >= 0 && pos < mDataList.size())
        {
            mDataList.remove(pos);

            notifyItemRemoved(pos);
        }
    }

    @SuppressWarnings("unused")
    public final void updateItem(T t)
    {
        updateItem(findItemPosition(t), t);
    }

    @SuppressWarnings("WeakerAccess")
    public final void updateItem(int pos, T t)
    {
        if (pos >= 0 && pos < mDataList.size())
        {
            mDataList.set(pos, t);

            notifyItemChanged(pos);
        }
    }

    public final int findItemPosition(T t)
    {
        if (t != null)
        {
            for (int i = 0; i < mDataList.size(); i++)
            {
                if (t.compareTo(mDataList.get(i)))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    @SuppressWarnings("WeakerAccess")
    public final T getItem(int position)
    {
        if (position < 0 || position >= getItemCount())
        {
            return null;
        }

        return mDataList.get(position);
    }

    @SuppressWarnings("WeakerAccess")
    public final List<T> getDataList()
    {
        return mDataList;
    }

}
