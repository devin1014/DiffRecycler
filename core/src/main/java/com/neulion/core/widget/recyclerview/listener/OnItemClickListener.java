package com.neulion.core.widget.recyclerview.listener;

import android.view.View;

/**
 * User: NeuLion
 */
public interface OnItemClickListener<T>
{
    void onItemClick(View view, T t);
}