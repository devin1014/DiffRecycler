package com.neulion.android.diffrecycler.diff;

/**
 * User: NeuLion
 */
public interface IDiffComparable<T>
{
    boolean compareObject(T t);

    boolean compareContent(T t);

    Object getChangePayload(T t);
}
