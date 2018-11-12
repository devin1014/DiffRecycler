package com.neulion.core.widget.recyclerview.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import java.lang.reflect.Method;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:34
 */
public class DataBindingHolder<T> extends BaseViewHolder<T>
{
    private final AbstractViewDataBinding mViewDataBinding;

    private OnItemClickListener<T> mOnItemClickListener;

    public DataBindingHolder(View itemView, OnItemClickListener<T> handler)
    {
        super(itemView);

        mOnItemClickListener = handler;

        mViewDataBinding = new AbstractViewDataBinding(DataBindingUtil.bind(itemView));
    }

    public DataBindingHolder(LayoutInflater inflater, ViewGroup parent, int layoutId, OnItemClickListener<T> handler)
    {
        this(inflater.inflate(layoutId, parent, false), handler);
    }

    @SuppressWarnings("unused")
    public OnItemClickListener<T> getOnItemClickListener()
    {
        return mOnItemClickListener;
    }

    @SuppressWarnings({"unchecked", "unused"})
    public final <DB extends ViewDataBinding> DB getViewDataBinding()
    {
        return (DB) mViewDataBinding.mSourceViewDataBinding;
    }

    public void setData(T t)
    {
        mViewDataBinding.setData(t);

        mViewDataBinding.setItemClickListener(mOnItemClickListener);

        mViewDataBinding.executePendingBindings();
    }

    private class AbstractViewDataBinding implements DataBinding<T>
    {
        final ViewDataBinding mSourceViewDataBinding;

        private Method mSetDataMethod;

        private Method mSetItemClickMethod;

        AbstractViewDataBinding(ViewDataBinding viewDataBinding)
        {
            mSourceViewDataBinding = viewDataBinding;
        }

        private boolean mSetDataMethodField = false;

        @Override
        public void setData(T t)
        {
            if (mSetDataMethod == null && !mSetDataMethodField)
            {
                mSetDataMethodField = true;

                try
                {
                    mSetDataMethod = mSourceViewDataBinding.getClass().getMethod("setData", t.getClass());
                }
                catch (NoSuchMethodException e)
                {
                    e.printStackTrace();
                }
            }

            if (mSetDataMethod != null)
            {
                try
                {
                    mSetDataMethod.invoke(mSourceViewDataBinding, t);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        private boolean mSetItemClickMethodField = false;

        @Override
        public void setItemClickListener(OnItemClickListener<T> listener)
        {
            if (mSetItemClickMethod == null && !mSetItemClickMethodField)
            {
                mSetItemClickMethodField = true;

                try
                {
                    mSetItemClickMethod = mSourceViewDataBinding.getClass().getMethod("setItemClickListener", OnItemClickListener.class);
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
                    mSetItemClickMethod.invoke(mSourceViewDataBinding, listener);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void executePendingBindings()
        {
            if (mSetDataMethod != null || mSetItemClickMethod != null)
            {
                mSourceViewDataBinding.executePendingBindings();
            }
        }
    }
}
