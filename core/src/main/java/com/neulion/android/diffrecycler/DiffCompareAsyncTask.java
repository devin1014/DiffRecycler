package com.neulion.android.diffrecycler;

import android.os.AsyncTask;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.DiffResult;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;
import com.neulion.android.diffrecycler.diff.DiffComparableCallback;
import com.neulion.android.diffrecycler.util.DiffRecyclerLogger;

import java.util.List;

final class DiffCompareAsyncTask<T extends DataDiffCompare<T>> extends AsyncTask<Void, Void, DiffResult>
{
    private List<T> oldList;

    private List<T> newList;

    private Callback<T> callback;

    private boolean detectMoves;

    interface Callback<T extends DataDiffCompare<T>>
    {
        void onCompareResult(List<T> oldList, List<T> newList, DiffResult diffResult);
    }

    DiffCompareAsyncTask(List<T> oldList, List<T> newList, Callback<T> callback, boolean detectMoves)
    {
        this.oldList = oldList;

        this.newList = newList;

        this.callback = callback;

        this.detectMoves = detectMoves;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        DiffRecyclerLogger.info(this, "---------------- DiffCompareTask Start ------------");
    }

    @Override
    protected DiffResult doInBackground(Void... voids)
    {
        DiffRecyclerLogger.set(this);

        // detectMoves should set to false!
        return DiffUtil.calculateDiff(new DiffComparableCallback<>(oldList, newList), detectMoves);
    }

    @Override
    protected void onPostExecute(DiffResult diffResult)
    {
        DiffRecyclerLogger.test(this, "---------------- DiffCompareTask Complete ------------");

        callback.onCompareResult(oldList, newList, diffResult);
    }

    @Override
    protected void onCancelled(DiffResult diffResult)
    {
        super.onCancelled(diffResult);

        DiffRecyclerLogger.warn(this, "---------------- DiffCompareTask Cancelled ------------");
    }
}