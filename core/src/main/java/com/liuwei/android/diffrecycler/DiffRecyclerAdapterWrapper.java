package com.liuwei.android.diffrecycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.liuwei.android.diffrecycler.holder.FooterViewHolder;
import com.liuwei.android.diffrecycler.holder.HeaderViewHolder;
import com.liuwei.android.diffrecycler.util.DiffRecyclerLogger;

import java.util.List;

final class DiffRecyclerAdapterWrapper<Holder extends ViewHolder> extends Adapter<Holder> implements HeaderFooterAdapter
{
    static final int TYPE_HEADER = 1024;

    static final int TYPE_FOOTER = 2048;

    private LinearLayout mHeaderLayout;

    private LinearLayout mFooterLayout;

    private int mHeadPositionOffset = 0;

    private int mFootPositionOffset = 0;

    private Adapter<Holder> mAdapter;

    private RecyclerView mRecyclerView;

    DiffRecyclerAdapterWrapper()
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

    void setSourceAdapter(Adapter<Holder> adapter)
    {
        if (mAdapter != null)
        {
            mAdapter.unregisterAdapterDataObserver(mChildAdapterDataObserver);
        }

        mAdapter = adapter;

        adapter.registerAdapterDataObserver(mChildAdapterDataObserver);

        notifyDataSetChanged();
    }

    private ChildAdapterDataObserver mChildAdapterDataObserver = new ChildAdapterDataObserver(this);

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
            DiffRecyclerLogger.info(this, String.format("onCreateFooterHolder(viewType = %s)", viewType));

            return onCreateFooterHolder(mFooterLayout);
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
        return (Holder) (new HeaderViewHolder(header));
    }

    @SuppressWarnings({"unchecked", "WeakerAccess"})
    protected Holder onCreateFooterHolder(View footer)
    {
        return (Holder) (new FooterViewHolder<>(footer));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads)
    {
        if (isHeader(position))
        {
            //noinspection unchecked
            onBindHeaderHolder((HeaderViewHolder) holder);
        }
        else if (isFooter(position))
        {
            //noinspection unchecked
            onBindFooterHolder((FooterViewHolder) holder);
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
            onBindHeaderHolder((HeaderViewHolder) holder);
        }
        else if (isFooter(position))
        {
            //noinspection unchecked
            onBindFooterHolder((FooterViewHolder) holder);
        }
        else if (mAdapter != null)
        {
            mAdapter.onBindViewHolder(holder, position - mHeadPositionOffset);
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void onBindHeaderHolder(HeaderViewHolder holder)
    {
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public void onBindFooterHolder(FooterViewHolder holder)
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

    @SuppressWarnings("WeakerAccess")
    public Adapter<Holder> getSourceAdapter()
    {
        return mAdapter;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Headers
    // ----------------------------------------------------------------------------------------------------------------------------------
    @Override
    public int addHeader(View header)
    {
        return addHeader(header, -1);
    }

    public int addHeader(View header, int index)
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

    public int removeHeader(View header)
    {
        int index = -1;

        if (mHeaderLayout != null)
        {
            index = mHeaderLayout.indexOfChild(header);

            if (index >= 0)
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

    @Override
    public View removeHeader(int index)
    {
        View removedView = null;

        if (mHeaderLayout != null && index >= 0 && index < mHeaderLayout.getChildCount())
        {
            removedView = mHeaderLayout.getChildAt(index);

            mHeaderLayout.removeViewAt(index);

            if (mHeaderLayout.getChildCount() == 0)
            {
                mHeadPositionOffset = 0;

                notifyItemRemoved(0);
            }
        }

        return removedView;
    }

    public void removeAllHeader()
    {
        if (mHeaderLayout != null && mHeaderLayout.getChildCount() > 0)
        {
            mHeaderLayout.removeAllViews();

            mHeadPositionOffset = 0;

            notifyItemRemoved(0);
        }
    }

    public boolean isHeader(int position)
    {
        return hasHeaders() && position == 0;
    }

    public boolean hasHeaders()
    {
        return mHeadPositionOffset != 0;
    }

    public final int getHeaderCount()
    {
        return mHeaderLayout != null ? mHeaderLayout.getChildCount() : 0;
    }

    @Override
    public int addFooter(View footer)
    {
        return 0;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Footer
    // ----------------------------------------------------------------------------------------------------------------------------------
    public int addFooter(View footer, int index)
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

    public int removeFooter(View footer)
    {
        int index = -1;

        if (mFooterLayout != null)
        {
            index = mFooterLayout.indexOfChild(footer);

            if (index >= 0 && index < mFooterLayout.getChildCount())
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

    @Override
    public View removeFooter(int index)
    {
        View removedView = null;

        if (mFooterLayout != null && index >= 0 && index < mFooterLayout.getChildCount())
        {
            removedView = mFooterLayout.getChildAt(index);

            mFooterLayout.removeViewAt(index);

            if (mFooterLayout.getChildCount() == 0)
            {
                mFootPositionOffset = 0;

                notifyItemRemoved(getItemCount());
            }
        }

        return removedView;
    }

    public void removeAllFooter()
    {
        if (mFooterLayout != null && mFooterLayout.getChildCount() > 0)
        {
            mFooterLayout.removeAllViews();

            mFootPositionOffset = 0;

            notifyItemRemoved(getItemCount());
        }
    }

    public boolean isFooter(int position)
    {
        return hasFooters() && position == getItemCount() - 1;
    }

    public boolean hasFooters()
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
    // - DataObserver
    // ----------------------------------------------------------------------------------------------------
    private static class ChildAdapterDataObserver extends InnerAdapterDataObserver
    {
        private DiffRecyclerAdapterWrapper mAdapterWrapper;

        ChildAdapterDataObserver(DiffRecyclerAdapterWrapper adapter)
        {
            mAdapterWrapper = adapter;
        }

        @Override
        public void onChanged()
        {
            super.onChanged();

            mAdapterWrapper.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount)
        {
            super.onItemRangeChanged(positionStart, itemCount);

            mAdapterWrapper.notifyItemRangeChanged(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload)
        {
            super.onItemRangeChanged(positionStart, itemCount, payload);

            mAdapterWrapper.notifyItemRangeChanged(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount)
        {
            super.onItemRangeInserted(positionStart, itemCount);

            mAdapterWrapper.notifyItemRangeInserted(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount)
        {
            super.onItemRangeRemoved(positionStart, itemCount);

            mAdapterWrapper.notifyItemRangeRemoved(positionStart + mAdapterWrapper.mHeadPositionOffset, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount)
        {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);

            mAdapterWrapper.notifyItemMoved(fromPosition + mAdapterWrapper.mHeadPositionOffset, toPosition + mAdapterWrapper.mHeadPositionOffset);
        }
    }

    private static class InnerAdapterDataObserver extends AdapterDataObserver
    {
        @Override
        public void onChanged()
        {
            DiffRecyclerLogger.warn(this, "onChanged");
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload)
        {
            DiffRecyclerLogger.warn(this, String.format("onItemRangeChanged(positionStart = %s , itemCount = %s , payload = %s)", positionStart, itemCount, payload));
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount)
        {
            DiffRecyclerLogger.warn(this, String.format("onItemRangeInserted(positionStart = %s , itemCount = %s)", positionStart, itemCount));
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount)
        {
            DiffRecyclerLogger.warn(this, String.format("onItemRangeRemoved(positionStart = %s , itemCount = %s)", positionStart, itemCount));
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount)
        {
            DiffRecyclerLogger.warn(this, String.format("onItemRangeMoved(fromPosition = %s , toPosition = %s , itemCount = %s)", fromPosition, toPosition, itemCount));
        }
    }
}
