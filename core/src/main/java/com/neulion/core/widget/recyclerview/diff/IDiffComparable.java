package com.neulion.core.widget.recyclerview.diff;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-10
 * Time: 15:50
 */
public interface IDiffComparable<T>
{
    boolean compareObject(T t);

    boolean compareContent(T t);

    Object getChangePayload(T t);
}
