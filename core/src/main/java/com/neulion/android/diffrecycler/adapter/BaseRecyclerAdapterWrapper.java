package com.neulion.android.diffrecycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.neulion.android.diffrecycler.diff.DataComparable;
import com.neulion.android.diffrecycler.holder.HeaderViewHolder;
import com.neulion.android.diffrecycler.util.DiffRecyclerLogger;

import java.util.List;

public class BaseRecyclerAdapterWrapper<T extends DataComparable<T>, Holder extends ViewHolder> extends Adapter<Holder>
{
    private static final int TYPE_HEADER = 1024;

    private static final int TYPE_FOOTER = 2048;

    private LinearLayout mHeaderLayout;

    private LinearLayout mFooterLayout;

    private int mHeadPositionOffset = 0;

    private int mFootPositionOffset = 0;

    private Adapter<Holder> mAdapter;

    private RecyclerView mRecyclerView;

    public BaseRecyclerAdapterWrapper()
    {
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);

        mRecyclerView = null;
    }

    public void setSourceAdapter(Adapter<Holder> adapter)
    {
        if (mAdapter != null)
        {
            mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }

        mAdapter = adapter;

        adapter.registerAdapterDataObserver(mAdapterDataObserver);

        notifyDataSetChanged();
    }

    private InnerAdapterDataObserver mAdapterDataObserver = new InnerAdapterDataObserver(this);

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            DiffRecyclerLogger.info(this, String.format("onCreateHeaderHolder(viewType = %s)", viewType));

            return onCreateHeaderHolder(mHeaderLayout);
        }
        else if (viewType == TYPE_FOOTER)
        {
            DiffRecyclerLogger.info(this, String.format("onCreateHeaderHolder(viewType = %s)", viewType));

            return onCreateHeaderHolder(mFooterLayout);
        }
        else if (mAdapter != null)
        {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "WeakerAccess"})
    protected Holder onCreateHeaderHolder(View header)
    {
        return (Holder) (new HeaderViewHolder<>(header));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads)
    {
        if (isHeader(position))
        {
            //noinspection unchecked
            onBindHeaderHolder((HeaderViewHolder<T>) holder);
        }
        else if (isFooter(position))
        {
            //noinspection unchecked
            onBindHeaderHolder((HeaderViewHolder<T>) holder);
        }
        else if (mAdapter != null)
        {
            mAdapter.onBindViewHolder(holder, position - mHeadPositionOffset, payloads);
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        if (isHeader(position))
        {
            //noinspection unchecked
            onBindHeaderHolder((HeaderViewHolder<T>) holder);
        }
        else if (isFooter(position))
        {
            //noinspection unchecked
            onBindHeaderHolder((HeaderViewHolder<T>) holder);
        }
        else if (mAdapter != null)
        {
            mAdapter.onBindViewHolder(holder, position - mHeadPositionOffset);
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void onBindHeaderHolder(HeaderViewHolder<T> holder)
    {
    }

    @Override
    public int getItemCount()
    {
        return (mAdapter != null ? mAdapter.getItemCount() : 0) + mHeadPositionOffset + mFootPositionOffset;
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

        if (mAdapter != null)
        {
            return mAdapter.getItemViewType(position - mHeadPositionOffset);
        }

        return -1;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Headers
    // ----------------------------------------------------------------------------------------------------------------------------------
    public <V extends View> int addHeader(V header, int index)
    {
        if (mHeaderLayout == null)
        {
            mHeaderLayout = createHeaderLayout();
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

    private boolean isHeader(int position)
    {
        return hasHeaders() && position == 0;
    }

    private boolean hasHeaders()
    {
        return mHeadPositionOffset != 0;
    }

    public final int getHeaderCount()
    {
        return mHeaderLayout != null ? mHeaderLayout.getChildCount() : 0;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Footer
    // ----------------------------------------------------------------------------------------------------------------------------------
    public <V extends View> int addFooter(V footer, int index)
    {
        if (mFooterLayout == null)
        {
            mFooterLayout = createHeaderLayout();
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

    private boolean isFooter(int position)
    {
        return hasFooters() && position == getItemCount() - 1;
    }

    private boolean hasFooters()
    {
        return mFootPositionOffset != 0;
    }

    public final int getFooterCount()
    {
        return mFooterLayout != null ? mFooterLayout.getChildCount() : 0;
    }

    private LinearLayout createHeaderLayout()
    {
        if (mRecyclerView == null)
        {
            throw new IllegalStateException("DiffRecyclerView has not bind!");
        }

        if (mRecyclerView.getLayoutManager() == null)
        {
            throw new IllegalStateException("DiffRecyclerView has not 'LayoutManager'!");
        }

        LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        LinearLayout linearLayout = new LinearLayout(mRecyclerView.getContext());

        if (layoutManager.canScrollVertically())
        {
            linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }
        else
        {
            linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }

        return linearLayout;
    }

    // ----------------------------------------------------------------------------------------------------
    // - InnerDataObserver
    // ----------------------------------------------------------------------------------------------------
    private static class InnerAdapterDataObserver extends AdapterDataObserver
    {
        private BaseRecyclerAdapterWrapper mAdapterWrapper;

        InnerAdapterDataObserver(BaseRecyclerAdapterWrapper adapter)
        {
            mAdapterWrapper = adapter;
        }

        @Override
        public void onChanged()
        {
            mAdapterWrapper.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount)
        {
            mAdapterWrapper.notifyItemRangeChanged(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload)
        {
            mAdapterWrapper.notifyItemRangeChanged(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount)
        {
            mAdapterWrapper.notifyItemRangeInserted(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount)
        {
            mAdapterWrapper.notifyItemRangeRemoved(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount)
        {
            mAdapterWrapper.notifyItemMoved(fromPosition + mAdapterWrapper.mHeadPositionOffset, toPosition + mAdapterWrapper.mHeadPositionOffset);
        }
    }
}
