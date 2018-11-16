package com.neulion.recyclerviewdemo.bean;

import android.support.annotation.DrawableRes;

import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-17
 * Time: 11:08
 */
public class UIData implements UIDataInterface
{
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

    @DiffItem
    String name;

    @DiffContent
    String description;

    String date;

    @DiffContent
    int imageRes;

    @DiffItem
    int index;

    int requestIndex;

    public UIData(int requestIndex, int index, String name, String description, @DrawableRes int imageRes)
    {
        this.requestIndex = requestIndex;

        this.index = index;

        this.name = name;

        this.description = description;

        this.imageRes = imageRes;

        this.date = format.format(new Date());
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDate()
    {
        return date;
    }

    public int getImageRes()
    {
        return imageRes;
    }

    public String getIndex()
    {
        return String.format("索引：%s - %s", requestIndex, index);
    }

    public void update()
    {
        description = description + " 副本";
    }
}
