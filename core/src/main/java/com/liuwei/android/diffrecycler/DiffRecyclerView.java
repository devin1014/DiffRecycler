package com.liuwei.android.diffrecycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.liuwei.android.diffrecycler.decoration.DiffRecyclerDivider;
import com.liuwei.android.diffrecycler.util.DiffRecyclerLogger;

/**
 * User: liuwei
 */
public class DiffRecyclerView extends android.support.v7.widget.RecyclerView
{
    private static final int TYPE_LINEARLAYOUT = 0;
    private static final int TYPE_GRIDLAYOUT = 1;
    private static final int TYPE_STAGGERGRIDLAYOUT = 2;

    private ItemTouchHelperCallbackImp mItemTouchHelperCallbackImp;

    public DiffRecyclerView(Context context)
    {
        super(context);
    }

    public DiffRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context, attrs);
    }

    public DiffRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initialize(context, attrs);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, com.liuwei.android.diffrecycler.R.styleable.DiffRecyclerView);

        int layoutType = a.getInt(R.styleable.DiffRecyclerView_layoutType, TYPE_LINEARLAYOUT);

        int orientation = a.getInt(R.styleable.DiffRecyclerView_layoutOrientation, LinearLayoutManager.VERTICAL);

        boolean reverseLayout = a.getBoolean(R.styleable.DiffRecyclerView_reverseLayout, false);

        if (layoutType == TYPE_LINEARLAYOUT)
        {
            setLayoutManager(new LinearLayoutManager(context, orientation, reverseLayout));
        }
        else if (layoutType == TYPE_GRIDLAYOUT)
        {
            int spanCount = a.getInt(R.styleable.DiffRecyclerView_spanCount, 1);

            setLayoutManager(new GridLayoutManager(context, spanCount, orientation, reverseLayout));
        }
        else if (layoutType == TYPE_STAGGERGRIDLAYOUT)
        {
            int spanCount = a.getInt(R.styleable.DiffRecyclerView_spanCount, 1);

            setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
        }

        int dividerSize = a.getDimensionPixelSize(R.styleable.DiffRecyclerView_dividerSize, 0);

        if (dividerSize > 0)
        {
            int color = a.getColor(R.styleable.DiffRecyclerView_dividerColor, DiffRecyclerDivider.DIVIDER_COLOR);

            int mode = a.getInt(R.styleable.DiffRecyclerView_dividerMode, 0); // default mode 0:inner

            addItemDecoration(new DiffRecyclerDivider(dividerSize, color, mode));
        }

        // support 'ItemTouchHelper' feature: touch to resort or swipe to delete
        if (a.getBoolean(R.styleable.DiffRecyclerView_enableItemTouch, false))
        {
            mItemTouchHelperCallbackImp = new ItemTouchHelperCallbackImp(
                    a.getBoolean(R.styleable.DiffRecyclerView_enableLongPressDrag, true),
                    a.getBoolean(R.styleable.DiffRecyclerView_enableItemViewSwipe, true));
        }

        a.recycle();

        super.setAdapter(mAdapterWrapper); // DiffRecyclerView should set 'DiffRecyclerAdapterWrapper'
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        removeOnScrollListener(mOnScrollListener);

        super.onDetachedFromWindow();
    }

    private DiffRecyclerAdapterWrapper mAdapterWrapper = new DiffRecyclerAdapterWrapper();

    @Override
    public void setAdapter(Adapter adapter)
    {
        // should not call super function!!!
        //super.setAdapter(adapter);

        //noinspection unchecked
        mAdapterWrapper.setSourceAdapter(adapter);

        if (mItemTouchHelperCallbackImp != null && adapter instanceof DataListAdapter)
        {
            mItemTouchHelperCallbackImp.attachItemTouchHelperToRecyclerView(this, mAdapterWrapper, (DataListAdapter) adapter);
        }
    }

    public Adapter getAdapter()
    {
        return mAdapterWrapper.getSourceAdapter();
    }

    private int mSpanCount = 1;

    @SuppressWarnings("unused")
    DiffRecyclerAdapterWrapper getAdapterWrapper()
    {
        return mAdapterWrapper;
    }

    @Override
    public void setLayoutManager(LayoutManager layout)
    {
        super.setLayoutManager(layout);

        if (layout instanceof GridLayoutManager)
        {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;

            gridLayoutManager.setSpanSizeLookup(new DiffSpanSizeLookupWrapper(gridLayoutManager));

            mSpanCount = gridLayoutManager.getSpanCount();

            mNearByBottomOffset = mNearByBottomOffset * mSpanCount;
        }
    }

    public interface ViewHolderTouchStateCallback
    {
        void onViewHolderTouchStateChanged(ViewHolder viewHolder, int actionState);
    }

    public void setOnViewHolderTouchStateCallback(ViewHolderTouchStateCallback callback)
    {
        if (mItemTouchHelperCallbackImp != null)
        {
            mItemTouchHelperCallbackImp.setOnViewHolderTouchStateCallback(callback);
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - SpanSizeLookupWrapper
    // ----------------------------------------------------------------------------------------------------------------------------------
    private class DiffSpanSizeLookupWrapper extends SpanSizeLookup
    {
        private SpanSizeLookup mSpanSizeLookup;

        private int mSpanCount;

        DiffSpanSizeLookupWrapper(GridLayoutManager layoutManager)
        {
            mSpanSizeLookup = layoutManager.getSpanSizeLookup();

            mSpanCount = layoutManager.getSpanCount();
        }

        @Override
        public int getSpanSize(int position)
        {
            if (mAdapterWrapper.getItemViewType(position) == DiffRecyclerAdapterWrapper.TYPE_HEADER
                    || mAdapterWrapper.getItemViewType(position) == DiffRecyclerAdapterWrapper.TYPE_FOOTER)
            {
                return mSpanCount;
            }

            final int offsetSize = mAdapterWrapper.getHeaderCount() > 0 ? 1 : 0;

            return mSpanSizeLookup.getSpanSize(position - offsetSize);
        }
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
        return mAdapterWrapper.addHeader(header, index);
    }

    public <V extends View> int removeHeader(V header)
    {
        return mAdapterWrapper.removeHeader(header);
    }

    public final int getHeaderCount()
    {
        return mAdapterWrapper.getHeaderCount();
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
        return mAdapterWrapper.addFooter(footer, index);
    }

    public <V extends View> int removeFooter(V footer)
    {
        return mAdapterWrapper.removeFooter(footer);
    }

    public final int getFooterCount()
    {
        return mAdapterWrapper.getFooterCount();
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - CloseBottom
    // ----------------------------------------------------------------------------------------------------------------------------------
    public interface ScrolledNearByBottomListener
    {
        void onScrolledNearByBottom();
    }

    private ScrolledNearByBottomListener mScrolledNearByBottomListener;

    public void setOnScrolledNearByBottomListener(ScrolledNearByBottomListener listener)
    {
        mScrolledNearByBottomListener = listener;
    }

    private int mNearByBottomOffset = 1;

    public void setNearByBottomOffset(int offset)
    {
        mNearByBottomOffset = offset;

        if (getLayoutManager() instanceof GridLayoutManager)
        {
            mNearByBottomOffset = mNearByBottomOffset * ((GridLayoutManager) getLayoutManager()).getSpanCount();
        }
    }

    private int mCurrentLastChildPosition = -1;

    private OnScrollListener mOnScrollListener = new OnScrollListener()
    {
        private boolean mNearByBottom = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState)
        {
            DiffRecyclerLogger.log(this, "newState=" + newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            DiffRecyclerLogger.log(this, "onScrolled: dx=" + dx + " , dy=" + dy);

            if (getAdapter() != null)
            {
                int adaptPosition = getLastChildViewAdaptPosition();

                if (adaptPosition == -1)
                {
                    return;
                }

                if (dy > 0 || dx > 0)
                {
                    if (adaptPosition + mSpanCount >= getAdapter().getItemCount() - 1 - mNearByBottomOffset)
                    {
                        if (!mNearByBottom)
                        {
                            mNearByBottom = true;

                            if (mScrolledNearByBottomListener != null)
                            {
                                mScrolledNearByBottomListener.onScrolledNearByBottom();
                            }
                        }
                        else
                        {
                            mNearByBottom = false;
                        }
                    }
                }
                else
                {
                    if (adaptPosition + mSpanCount < getAdapter().getItemCount() - 1 - mNearByBottomOffset)
                    {
                        if (mNearByBottom)
                        {
                            mNearByBottom = false;
                        }
                    }
                }
            }
        }

        private int getLastChildViewAdaptPosition()
        {
            if (getChildCount() == 0)
            {
                return -1;
            }

            View lastView = getChildAt(getChildCount() - 1);

            if (lastView == null || lastView.getLayoutParams() == null)
            {
                return -1;
            }

            return ((RecyclerView.LayoutParams) lastView.getLayoutParams()).getViewAdapterPosition();
        }
    };
}
