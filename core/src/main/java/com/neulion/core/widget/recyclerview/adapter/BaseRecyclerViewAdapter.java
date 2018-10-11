package com.neulion.core.widget.recyclerview.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.neulion.core.widget.recyclerview.holder.BaseViewHolder;
import com.neulion.core.widget.recyclerview.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-14
 * Time: 14:55
 */
public abstract class BaseRecyclerViewAdapter<T, Holder extends BaseViewHolder<T>> extends Adapter<Holder>
{
    private final LayoutInflater mLayoutInflater;

    private List<T> mDataList;

    public BaseRecyclerViewAdapter(LayoutInflater inflater)
    {
        mLayoutInflater = inflater;
    }

    @Override
    public final Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LogUtil.warn(this, "onCreateViewHolder(viewType:" + viewType + ")");

        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    public abstract Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads)
    {
        LogUtil.log(this, "onBindViewHolder(holder:" + Integer.toHexString(holder.hashCode()) + ",position:" + position + ",payloads:" + payloads + ")");

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public final void onBindViewHolder(Holder holder, int position)
    {
        //LogUtil.log(this, "onBindViewHolder(holder:" + Integer.toHexString(holder.hashCode()) + ",position:" + position + ")");

        onBindViewHolder(holder, getItem(position), position);
    }

    public abstract void onBindViewHolder(Holder holder, T t, int position);

    @Override
    public int getItemCount()
    {
        return mDataList != null ? mDataList.size() : 0;
    }

    public void setData(List<T> list)
    {
        LogUtil.log(this, "setData(" + list.getClass().getSimpleName() + ")");

        if (mDataList != list)
        {
            final List<T> temp = mDataList;

            mDataList = list;

            onDataSetChanged(temp, mDataList);
        }
    }

    protected void onDataSetChanged(List<T> oldList, List<T> newList)
    {
        LogUtil.warn(this, "onDataSetChanged(oldList:" + (oldList != null && oldList.size() > 0 ? +oldList.size() : "null") + ")");
        LogUtil.warn(this, "onDataSetChanged(newList:" + (newList != null && newList.size() > 0 ? +newList.size() : "null") + ")");
    }

    public final void appendData(T t, int pos)
    {
        if (mDataList != null)
        {
            mDataList.add(pos, t);

            onDataSetAppended(pos, 1);
        }
        else
        {
            ArrayList<T> list = new ArrayList<>();

            list.add(t);

            setData(list);
        }
    }

    public final void appendData(List<T> list)
    {
        if (list != null && list.size() > 0)
        {
            if (mDataList != null)
            {
                mDataList.addAll(list);

                onDataSetAppended(mDataList.size(), list.size());
            }
            else
            {
                setData(list);
            }
        }
    }

    protected void onDataSetAppended(int position, int count)
    {
        LogUtil.info(this, "onDataSetAppended(position:" + position + ",count:" + count + ")");
    }

    public void onRecyclerViewDetachedFromWindow()
    {
    }

    public final int findItemPosition(T t)
    {
        if (mDataList != null && t != null)
        {
            for (int i = 0; i < mDataList.size(); i++)
            {
                if (t.equals(mDataList.get(i)))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public final T getItem(int position)
    {
        if (position < 0 || position >= getItemCount())
        {
            return null;
        }

        return mDataList != null ? mDataList.get(position) : null;
    }

    public final List<T> getDataList()
    {
        return mDataList;
    }
}