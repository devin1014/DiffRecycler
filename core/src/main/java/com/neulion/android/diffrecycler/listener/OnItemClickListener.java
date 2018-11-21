package com.neulion.android.diffrecycler.listener;

import android.view.View;

/**
 * User: NeuLion
 */
public interface OnItemClickListener<T>
{
    void onItemClick(View view, T t);
}