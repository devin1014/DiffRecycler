package com.liuwei.android.diffrecycler.diff;

import java.util.Map;

/**
 * User: liuwei
 */
public interface DiffComparable<T>
{
    boolean compareItem(T t);

    boolean compareContent(T t);

    Object getChangePayload(T t);

    Map<String, Comparable> getItems();

    Map<String, Comparable> getContents();
}
