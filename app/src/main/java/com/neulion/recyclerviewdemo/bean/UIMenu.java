package com.neulion.recyclerviewdemo.bean;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;

public interface UIMenu extends DataDiffCompare<UIMenu>
{
    String getName();

    Class<?> getMenuClass();
}
