package com.neulion.core.widget.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.neulion.core.R;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-27
 * Time: 14:38
 */
public class RecyclerView extends android.support.v7.widget.RecyclerView
{
    private int mOrientation;

    private DividerDecoration mDividerDecoration;

    public RecyclerView(Context context)
    {
        super(context);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        initialize(context, attrs);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initialize(context, attrs);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs)
    {
        mDividerDecoration = new DividerDecoration(0, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerView);

        setOrientation(a.getInt(R.styleable.RecyclerView_orientation, LinearLayoutManager.HORIZONTAL));

        setDividerColor(a.getColor(R.styleable.RecyclerView_dividerColor, Color.TRANSPARENT));

        setDividerSize(a.getDimensionPixelSize(R.styleable.RecyclerView_dividerSize, 0));

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
