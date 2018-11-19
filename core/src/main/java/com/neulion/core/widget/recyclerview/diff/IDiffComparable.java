package com.neulion.core.widget.recyclerview.diff;

/**
 * User: NeuLion
 */
public interface IDiffComparable<T>
{
    boolean compareObject(T t);

    boolean compareContent(T t);

    Object getChangePayload(T t);
}
