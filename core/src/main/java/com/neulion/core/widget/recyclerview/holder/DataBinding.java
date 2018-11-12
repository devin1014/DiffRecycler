package com.neulion.core.widget.recyclerview.holder;

import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

public interface DataBinding<T>
{
    void setData(T t);

    void setItemClickListener(OnItemClickListener<T> listener);

    void executePendingBindings();
}
