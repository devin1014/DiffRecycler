package com.liuwei.android.diffrecycler.holder;

public interface DiffViewDataBindingInterface<T>
{
    void executePendingBindings(T t);
}
