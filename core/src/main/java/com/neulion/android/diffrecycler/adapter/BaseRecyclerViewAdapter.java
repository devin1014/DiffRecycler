package com.neulion.android.diffrecycler.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.neulion.android.diffrecycler.holder.BaseViewHolder;
import com.neulion.android.diffrecycler.holder.HeaderViewHolder;
import com.neulion.android.diffrecycler.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion
 */
public abstract class BaseRecyclerViewAdapter<T extends Comparable<T>, Holder extends BaseViewHolder<T>> extends Adapter<Holder>
{
    private static final int TYPE_HEADER = 10;

    private static final int TYPE_FOOTER = 100;

    private static final int TYPE_ITEM_PREFIX = 1000;

    private final LayoutInflater mLayoutInflater;

    private List<T> mDataList;

    protected LinearLayout mHeaderLayout;

    protected LinearLayout mFooterLayout;

    protected int mHeadPositionOffset = 0;

    protected int mFootPositionOffset = 0;

    public BaseRecyclerViewAdapter(LayoutInflater inflater)
    {
        mLayoutInflater = inflater;

        mDataList = new ArrayList<>();
    }

    @Override
    public final Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            LogUtil.info(this, String.format("onCreateHeaderHolder(viewType=%s)", viewType));

            return onCreateHeaderHolder(mHeaderLayout);
        }
        else if (viewType == TYPE_FOOTER)
        {
            LogUtil.info(this, String.format("onCreateHeaderHolder(viewType=%s)", viewType));

            return onCreateHeaderHolder(mFooterLayout);
        }
        else
        {
            viewType = viewType - TYPE_ITEM_PREFIX;

            LogUtil.info(this, String.format("onCreateViewHolder(viewType=%s)", viewType));

            return onCreateViewHolder(mLayoutInflater, parent, viewType);
        }
    }

    public abstract Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @SuppressWarnings("unchecked")
    public Holder onCreateHeaderHolder(View header)
    {
        return (Holder) (new HeaderViewHolder<>(header));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads)
    {
        LogUtil.log(this, String.format("onBindHolder(holder=@%s,position=%s,payloads=%s", Integer.toHexString(holder.hashCode()), position, payloads));

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public final void onBindViewHolder(Holder holder, int position)
    {
        if (isHeader(position))
        {
            onBindHeaderHolder(holder, position);
        }
        else if (isFooter(position))
        {
            onBindHeaderHolder(holder, position);
        }
        else
        {
            position = position - mHeadPositionOffset;

            onBindViewHolder(holder, getItem(position), position);
        }
    }

    //TODO:should add List<Object> payloads
    public abstract void onBindViewHolder(Holder holder, T t, int position);

    @SuppressWarnings("unused")
    public void onBindHeaderHolder(Holder holder, int position)
    {
    }

    @Override
    public int getItemCount()
    {
        return mDataList.size() + mHeadPositionOffset + mFootPositionOffset;
    }

    @Override
    public final int getItemViewType(int position)
    {
        if (isHeader(position))
        {
            return TYPE_HEADER;
        }

        if (isFooter(position))
        {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM_PREFIX + getItemType(position - mHeadPositionOffset);
    }

    protected int getItemType(@SuppressWarnings("unused") int position)
    {
        return 0;
    }


    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Headers
    // ----------------------------------------------------------------------------------------------------------------------------------
    public <V extends View> int addHeader(V header)
    {
        return addHeader(header, -1);
    }

    public <V extends View> int addHeader(V header, int index)
    {
        return addHeader(header, index, LinearLayout.VERTICAL);
    }

    public <V extends View> int addHeader(V header, int index, int orientation)
    {
        if (mHeaderLayout == null)
        {
            mHeaderLayout = new LinearLayout(header.getContext());

            mHeaderLayout.setOrientation(orientation);

            boolean vertical = orientation == LinearLayout.VERTICAL;

            mHeaderLayout.setLayoutParams(new LayoutParams(vertical ? LayoutParams.MATCH_PARENT : LayoutParams.MATCH_PARENT,
                    vertical ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT));
        }

        final int childCount = mHeaderLayout.getChildCount();

        if (index < 0 || index > childCount)
        {
            index = childCount;
        }

        mHeaderLayout.addView(header, index);

        if (mHeaderLayout.getChildCount() == 1)
        {
            mHeadPositionOffset = 1;

            notifyItemInserted(0);
        }

        return index;
    }

    public <V extends View> int removeHeader(V header)
    {
        int index = -1;

        if (mHeaderLayout != null)
        {
            index = mHeaderLayout.indexOfChild(header);

            if (index != -1)
            {
                mHeaderLayout.removeViewAt(index);
            }

            if (mHeaderLayout.getChildCount() == 0)
            {
                mHeadPositionOffset = 0;

                notifyItemRemoved(0);
            }
        }


        return index;
    }

    protected final boolean isHeader(int position)
    {
        return hasHeaders() && position == 0;
    }

    protected final boolean hasHeaders()
    {
        return mHeadPositionOffset != 0;
    }

    public final int getHeaderSize()
    {
        return mHeaderLayout != null ? mHeaderLayout.getChildCount() : 0;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Footer
    // ----------------------------------------------------------------------------------------------------------------------------------
    public <V extends View> int addFooter(V footer)
    {
        return addFooter(footer, -1);
    }

    public <V extends View> int addFooter(V footer, int index)
    {
        return addFooter(footer, index, LinearLayout.VERTICAL);
    }

    public <V extends View> int addFooter(V footer, int index, int orientation)
    {
        if (mFooterLayout == null)
        {
            mFooterLayout = new LinearLayout(footer.getContext());

            mFooterLayout.setOrientation(orientation);

            boolean vertical = orientation == LinearLayout.VERTICAL;

            mFooterLayout.setLayoutParams(new LayoutParams(vertical ? LayoutParams.MATCH_PARENT : LayoutParams.MATCH_PARENT,
                    vertical ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT));
        }

        final int childCount = mFooterLayout.getChildCount();

        if (index < 0 || index > childCount)
        {
            index = childCount;
        }

        mFooterLayout.addView(footer, index);

        if (mFooterLayout.getChildCount() == 1)
        {
            mFootPositionOffset = 1;

            notifyItemInserted(getItemCount());
        }

        return index;
    }

    public <V extends View> int removeFooter(V footer)
    {
        int index = -1;

        if (mFooterLayout != null)
        {
            index = mFooterLayout.indexOfChild(footer);

            if (index != -1)
            {
                mFooterLayout.removeViewAt(index);
            }

            if (mFooterLayout.getChildCount() == 0)
            {
                mFootPositionOffset = 0;

                notifyItemRemoved(getItemCount());
            }
        }


        return index;
    }

    protected final boolean isFooter(int position)
    {
        return hasFooters() && position == getItemCount() - 1;
    }

    protected final boolean hasFooters()
    {
        return mFootPositionOffset != 0;
    }

    public final int getFooterSize()
    {
        return mFooterLayout != null ? mFooterLayout.getChildCount() : 0;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Data
    // ----------------------------------------------------------------------------------------------------------------------------------
    public void setData(List<T> list)
    {
        if (list == null)
        {
            list = new ArrayList<>();
        }

        if (list.size() == 0)
        {
            LogUtil.warn(this, "setData(list=null/empty)");
        }
        else
        {
            LogUtil.log(this, String.format("setData(list<%s>) , list.getSize()=%s", list.get(0).getClass().getSimpleName(), list.size()));
        }

        if (mDataList != list)
        {
            final List<T> temp = mDataList;

            mDataList = list;

            onDataSetChanged(temp, mDataList);
        }
    }

    protected void onDataSetChanged(List<T> oldList, List<T> newList)
    {
        LogUtil.warn(this, String.format("onDataSetChanged(oldList=%s,newList=%s)", oldList != null ? oldList.size() : 0, newList != null ? newList.size() : 0));
    }

    public final void appendData(T t)
    {
        appendData(t, getDataList().size());
    }

    public final void appendData(T t, int pos)
    {
        mDataList.add(pos, t);

        notifyItemInserted(pos + mHeadPositionOffset);
    }

    public final void appendData(List<T> list)
    {
        appendData(list, mDataList.size());
    }

    public final void appendData(List<T> list, int index)
    {
        if (list != null && list.size() > 0)
        {
            mDataList.addAll(index, list);

            notifyItemRangeInserted(index + mHeadPositionOffset, list.size());
        }
    }

    public final void removeItem(T t)
    {
        removeItem(findItemPosition(t));
    }

    public final void removeItem(int pos)
    {
        if (pos >= 0 && pos < mDataList.size())
        {
            mDataList.remove(pos);

            notifyItemRemoved(pos + mHeadPositionOffset);
        }
    }

    public final void updateItem(T t)
    {
        updateItem(findItemPosition(t), t);
    }

    public final void updateItem(int pos, T t)
    {
        if (pos >= 0 && pos < mDataList.size())
        {
            mDataList.set(pos, t);

            notifyItemChanged(pos + mHeadPositionOffset);
        }
    }

    public final int findItemPosition(T t)
    {
        if (t != null)
        {
            for (int i = 0; i < mDataList.size(); i++)
            {
                if (t.compareTo(mDataList.get(i)) == 0) //TODO
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

        return mDataList.get(position);
    }

    public final List<T> getDataList()
    {
        return mDataList;
    }

}
