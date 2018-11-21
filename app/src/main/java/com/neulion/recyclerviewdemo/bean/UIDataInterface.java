package com.neulion.recyclerviewdemo.bean;

import android.support.annotation.DrawableRes;

import com.neulion.android.diffrecycler.diff.DataComparable;

public interface UIDataInterface extends DataComparable<UIDataInterface>
{
    String getId();

    String getName();

    String getDescription();

    @DrawableRes
    int getImageRes();
}
