package com.liuwei.android.diffrecycler.listener;

import android.view.View;

/**
 * User: liuwei
 */
public interface OnItemClickListener<T>
{
    void onItemClick(View view, T t);
}