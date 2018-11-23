package com.neulion.android.diffrecycler.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

final class DiffViewDataBindingInterfaceImp<T extends DataDiffCompare<T>> implements DiffViewDataBindingInterface<T>
{
    private ViewDataBinding mViewDataBinding;

    private Method mSetDataMethod;

    private Method mSetItemClickMethod;

    DiffViewDataBindingInterfaceImp(View bindingView, OnItemClickListener<T> listener)
    {
        mViewDataBinding = DataBindingUtil.bind(bindingView);

        setItemClickListener(listener);
    }

    public ViewDataBinding getViewDataBinding()
    {
        return mViewDataBinding;
    }

    @Override
    public void executePendingBindings(T t)
    {
        setData(t);

        if (mSetDataMethod != null || mSetItemClickMethod != null)
        {
            mViewDataBinding.executePendingBindings();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------
    // - Inner
    // -------------------------------------------------------------------------------------------------------------------
    private boolean mSetDataMethodField = false;

    private void setData(T t)
    {
        if (mSetDataMethod == null && !mSetDataMethodField)
        {
            mSetDataMethodField = true;

            try
            {
                Field field = mViewDataBinding.getClass().getSuperclass().getDeclaredField("mData");

                Type type = field.getGenericType();

                mSetDataMethod = mViewDataBinding.getClass().getMethod("setData", (Class) type);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (mSetDataMethod != null)
        {
            try
            {
                mSetDataMethod.invoke(mViewDataBinding, t);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean mSetItemClickMethodField = false;

    private void setItemClickListener(OnItemClickListener<T> listener)
    {
        if (listener != null && mSetItemClickMethod == null && !mSetItemClickMethodField)
        {
            mSetItemClickMethodField = true;

            try
            {
                mSetItemClickMethod = mViewDataBinding.getClass().getMethod("setItemClickListener", OnItemClickListener.class);
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
        }

        if (mSetItemClickMethod != null)
        {
            try
            {
                mSetItemClickMethod.invoke(mViewDataBinding, listener);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}