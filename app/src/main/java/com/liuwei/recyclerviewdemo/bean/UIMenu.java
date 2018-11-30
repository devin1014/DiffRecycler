package com.liuwei.recyclerviewdemo.bean;

import com.liuwei.android.diffrecycler.diff.DataDiffCompare;

public interface UIMenu extends DataDiffCompare<UIMenu>
{
    String getName();

    Class<?> getMenuClass();
}
