package com.neulion.android.diffrecycler.holder;

import android.view.View;
import android.widget.LinearLayout;

public class HeaderViewHolder<T extends Comparable<T>> extends BaseViewHolder<T>
{
    public final LinearLayout mHeaderLayout;

    public HeaderViewHolder(View itemView)
    {
        super(itemView);

        mHeaderLayout = (LinearLayout) itemView;
    }
}
