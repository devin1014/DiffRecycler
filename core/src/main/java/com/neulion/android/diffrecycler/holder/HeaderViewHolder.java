package com.neulion.android.diffrecycler.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;

@SuppressWarnings("unused")
public class HeaderViewHolder<T extends DataDiffCompare<T>> extends DiffViewHolder
{
    @SuppressWarnings("WeakerAccess")
    public final LinearLayout mHeaderLinearLayout;

    public HeaderViewHolder(View itemView)
    {
        super(itemView);

        mHeaderLinearLayout = (LinearLayout) itemView;
    }
}
