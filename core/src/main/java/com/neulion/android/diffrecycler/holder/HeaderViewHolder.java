package com.neulion.android.diffrecycler.holder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.LinearLayout;

public class HeaderViewHolder extends ViewHolder
{
    @SuppressWarnings("WeakerAccess")
    public final LinearLayout mHeaderLinearLayout;

    public HeaderViewHolder(View itemView)
    {
        super(itemView);

        mHeaderLinearLayout = (LinearLayout) itemView;
    }
}
