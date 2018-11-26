package com.neulion.android.diffrecycler;

import java.util.List;

interface DataListAdapter<T>
{
    void appendData(T t);

    void appendData(T t, int pos);

    void appendDataList(List<T> list);

    void appendDataList(List<T> list, int index);

    void moveItem(int fromPosition, int toPosition);

    void removeItem(T t);

    void removeItem(int pos);

    void updateItem(T t);

    void updateItem(int pos, T t);

    int findItemPosition(T t);

    T getItem(int position);
}
