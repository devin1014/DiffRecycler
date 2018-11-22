package com.neulion.android.diffrecycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * User: NeuLion
 */
public class DiffRecyclerView extends android.support.v7.widget.RecyclerView
{
    private int mOrientation;

    private DividerDecoration mDividerDecoration;

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
        mDividerDecoration = new DividerDecoration(0, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, com.neulion.android.diffrecycler.R.styleable.DiffRecyclerView);

        setOrientation(a.getInt(com.neulion.android.diffrecycler.R.styleable.DiffRecyclerView_orientation, LinearLayoutManager.HORIZONTAL));

        setDividerColor(a.getColor(com.neulion.android.diffrecycler.R.styleable.DiffRecyclerView_dividerColor, Color.TRANSPARENT));

        setDividerSize(a.getDimensionPixelSize(com.neulion.android.diffrecycler.R.styleable.DiffRecyclerView_dividerSize, 0));

        a.recycle();

        super.addItemDecoration(mDividerDecoration, -1);
    }

    public void setDividerSize(int size)
    {
        if (mOrientation == LinearLayoutManager.HORIZONTAL)
        {
            mDividerDecoration.setHorizontalSize(size);
        }
        else
        {
            mDividerDecoration.setVerticalSize(size);
        }
    }

    public void setDividerColor(@ColorInt int color)
    {
        mDividerDecoration.setDividerColor(color);
    }

    public void setOrientation(int orientation)
    {
        mOrientation = orientation;

        if (getLayoutManager() != null)
        {
            if (getLayoutManager() instanceof LinearLayoutManager)
            {
                ((LinearLayoutManager) getLayoutManager()).setOrientation(orientation);
            }
        }
        else
        {
            setLayoutManager(new LinearLayoutManager(getContext(), mOrientation, false));
        }
    }

    @Override
    public void addItemDecoration(ItemDecoration decor, int index)
    {
        if (decor instanceof DividerDecoration)
        {
            throw new IllegalArgumentException("Only one DividerDecoration can be added.");
        }

        super.addItemDecoration(decor, index);
    }

}