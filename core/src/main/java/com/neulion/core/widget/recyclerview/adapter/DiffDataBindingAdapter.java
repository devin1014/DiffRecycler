package com.neulion.core.widget.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.ListUpdateCallback;
import android.view.LayoutInflater;

import com.neulion.core.widget.recyclerview.diff.DiffBindingCallback;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;
import com.neulion.core.widget.recyclerview.util.LogUtil;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-13
 * Time: 10:16
 */
public abstract class DiffDataBindingAdapter<T> extends DataBindingAdapter<T>
{
    private DiffCompareTask mCompareTask;

    public DiffDataBindingAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public void setData(List<T> list)
    {
        if (getDataList() == null || list == null)
        {
            super.setData(list);

            notifyDataSetChanged();
        }
        else
        {
            if (mCompareTask != null)
            {
                mCompareTask.cancel(true);
            }

            //FIXME,be careful destroyed!
            (mCompareTask = new DiffCompareTask(getDataList(), list)).execute();
        }
    }

    @Override
    protected void onDataSetAppended(int position, int count)
    {
        mListUpdateCallbackWrapper.onInserted(position, count);
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ListUpdateCallback Wrapper
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private ListUpdateCallbackWrapper mListUpdateCallbackWrapper = new ListUpdateCallbackWrapper();

    private class ListUpdateCallbackWrapper implements ListUpdateCallback
    {
        @Override
        public void onInserted(int position, int count)
        {
            LogUtil.info(this, String.format("onInserted(position=%s,count=%s)", position, count));

            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count)
        {
            LogUtil.info(this, String.format("onRemoved(position=%s,count=%s)", position, count));

            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition)
        {
            LogUtil.info(this, String.format("onMoved(fromPosition=%s,toPosition=%s)", fromPosition, toPosition));

            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload)
        {
            LogUtil.info(this, String.format("onChanged(position=%s,count=%s,payload=%s)", position, count, payload));

            notifyItemRangeChanged(position, count, payload);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DiffCompareTask extends AsyncTask<Void, Void, DiffResult>
    {
        private List<T> oldList;

        private List<T> newList;

        DiffCompareTask(List<T> oldList, List<T> newList)
        {
            this.oldList = oldList;

            this.newList = newList;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            LogUtil.info(this, "---------------- DiffCompareTask Start ------------");
        }

        @Override
        protected DiffResult doInBackground(Void... voids)
        {
            LogUtil.set(this);

            // detectMoves should set to false!
            return DiffUtil.calculateDiff(new DiffBindingCallback<>(oldList, newList), false);
        }

        @Override
        protected void onPostExecute(DiffResult diffResult)
        {
            LogUtil.test(this, "---------------- DiffCompareTask Complete ------------");

            onCompareTaskFinished(newList);

            diffResult.dispatchUpdatesTo(mListUpdateCallbackWrapper);
        }

        @Override
        protected void onCancelled(DiffResult diffResult)
        {
            super.onCancelled(diffResult);

            LogUtil.warn(this, "---------------- DiffCompareTask Cancelled ------------");
        }
    }

    protected void onCompareTaskFinished(List<T> list)
    {
        super.setData(list);
    }
}
