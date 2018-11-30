package com.liuwei.android.diffrecycler.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.liuwei.android.diffrecycler.diff.DataDiffCompare;

@SuppressWarnings("unused")
public class FooterViewHolder<T extends DataDiffCompare<T>> extends DiffViewHolder
{
    @SuppressWarnings("WeakerAccess")
    public final LinearLayout mHeaderLinearLayout;

    public FooterViewHolder(View itemView)
    {
        super(itemView);

        mHeaderLinearLayout = (LinearLayout) itemView;
    }
}
