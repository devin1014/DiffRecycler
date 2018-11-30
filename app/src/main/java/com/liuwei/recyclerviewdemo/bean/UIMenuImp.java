package com.liuwei.recyclerviewdemo.bean;

public class UIMenuImp implements UIMenu
{
    private String name;

    private Class<?> menuClass;

    public UIMenuImp(String name, Class<?> cls)
    {
        this.name = name;
        this.menuClass = cls;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Class<?> getMenuClass()
    {
        return menuClass;
    }

    @Override
    public boolean compareData(UIMenu compareObj)
    {
        return getName().equals(compareObj.getName());
    }
}
