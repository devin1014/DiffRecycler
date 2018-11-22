package com.neulion.recyclerviewdemo.bean;

import android.support.annotation.DrawableRes;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;

public interface UIDataInterface extends DataDiffCompare<UIDataInterface>
{
    String getId();

    String getName();

    String getDescription();

    @DrawableRes
    int getImageRes();
}
