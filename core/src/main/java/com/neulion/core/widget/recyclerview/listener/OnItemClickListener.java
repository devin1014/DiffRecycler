package com.neulion.core.widget.recyclerview.listener;

import android.view.View;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-08-07
 * Time: 15:11
 */
public interface OnItemClickListener<T>
{
    void onItemClick(View view, T t);
}