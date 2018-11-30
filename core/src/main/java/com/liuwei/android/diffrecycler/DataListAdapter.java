package com.liuwei.android.diffrecycler;

import java.util.List;

interface DataListAdapter<T>
{
    void appendData(T t);

    void appendData(T t, int pos);

    void appendDataList(List<T> list);

    void appendDataList(List<T> list, int index);

    void moveItem(T t, int toPosition);

    void moveItem(int fromPosition, int toPosition);

    void swap(int fromPosition, int toPosition);

    void removeItem(T t);

    void removeItem(int pos);

    void updateItem(T t);

    void updateItem(int pos, T t);

    int findItemPosition(T t);

    T getItem(int position);

    class Helper
    {
        static <T> void moveItem(List<T> list, int fromPosition, int toPosition)
        {
            T source = list.get(fromPosition);

            if (fromPosition > toPosition)
            {
                for (int i = fromPosition; i > toPosition; i--)
                {
                    list.set(i, list.get(i - 1));
                }

                list.set(toPosition, source);
            }

            if (fromPosition < toPosition)
            {
                for (int i = fromPosition; i < toPosition; i++)
                {
                    list.set(i, list.get(i + 1));
                }

                list.set(toPosition, source);
            }
        }
    }
}
