package com.neulion.recyclerviewdemo.bean;

import android.support.annotation.DrawableRes;

public interface UIDataInterface extends Comparable<UIDataInterface>
{
    String getId();

    String getName();

    String getDescription();

    @DrawableRes
    int getImageRes();
}
