package com.neulion.android.diffrecycler.holder;

import com.neulion.android.diffrecycler.listener.OnItemClickListener;

public interface DataBinding<T>
{
    void setData(T t);

    void setItemClickListener(OnItemClickListener<T> listener);

    void executePendingBindings();
}
