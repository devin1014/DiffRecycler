package com.neulion.android.diffrecycler.holder;

public interface DiffViewDataBindingInterface<T>
{
    void executePendingBindings(T t);
}
