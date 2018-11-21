package com.neulion.android.diffrecycler.diff;

/**
 * User: NeuLion
 */
public interface DiffComparable<T>
{
    boolean compareItem(T t);

    boolean compareContent(T t);

    Object getChangePayload(T t);
}
