package com.neulion.recyclerviewdemo.bean;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.neulion.android.diffrecycler.annotation.DiffContent;
import com.neulion.android.diffrecycler.annotation.DiffItem;

/**
 * User: NeuLion
 */
public class UIDataImp implements UIData
{
    @DiffItem
    String id;

    @DiffItem
    String name;

    @DiffContent
    String description;

    @DiffContent
    int imageRes;

    public UIDataImp(int id, String name, String description, @DrawableRes int imageRes)
    {
        this.id = String.valueOf(id);

        this.name = name;

        this.description = description;

        this.imageRes = imageRes;
    }

    @Override
    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getImageRes()
    {
        return imageRes;
    }

    @Override
    public boolean compareData(@NonNull UIData o)
    {
        return getId().equalsIgnoreCase(o.getId()) && getName().equalsIgnoreCase(o.getName());
    }
}
