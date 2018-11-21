package com.neulion.android.diffrecycler.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.neulion.android.diffrecycler.diff.DataComparable;

public class HeaderViewHolder<T extends DataComparable<T>> extends BaseViewHolder<T>
{
    public final LinearLayout mHeaderLayout;

    public HeaderViewHolder(View itemView)
    {
        super(itemView);

        mHeaderLayout = (LinearLayout) itemView;
    }
}
