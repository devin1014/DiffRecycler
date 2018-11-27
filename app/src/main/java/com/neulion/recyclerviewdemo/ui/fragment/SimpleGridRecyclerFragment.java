package com.neulion.recyclerviewdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.DiffRecyclerView.ViewHolderTouchStateCallback;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.provider.DataProvider;

import java.util.List;

public class SimpleGridRecyclerFragment extends BaseDiffRecyclerFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_grid_recycler, container, false);
    }

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_grid_item, mOnItemClickListener);

        mListAdapter.setData(DataProvider.getDataWithDrag());

        recyclerView.setAdapter(mListAdapter);

        recyclerView.setOnViewHolderTouchStateCallback(new ViewHolderTouchStateCallback()
        {
            @Override
            public void onViewHolderTouchStateChanged(ViewHolder viewHolder, int actionState)
            {
                mSwipeRefreshLayout.setEnabled(actionState == ItemTouchHelper.ACTION_STATE_IDLE);
            }
        });
    }

    @Override
    public List<UIData> getData()
    {
        return DataProvider.getDataWithDrag();
    }

    private OnItemClickListener<UIData> mOnItemClickListener = new OnItemClickListener<UIData>()
    {
        @Override
        public void onItemClick(View view, UIData uiData)
        {
            Toast.makeText(getActivity(), "onItemClick:" + mListAdapter.findItemPosition(uiData), Toast.LENGTH_SHORT).show();

            //mListAdapter.removeItem(uiData);
            mListAdapter.moveItem(uiData, 0);
        }
    };
}