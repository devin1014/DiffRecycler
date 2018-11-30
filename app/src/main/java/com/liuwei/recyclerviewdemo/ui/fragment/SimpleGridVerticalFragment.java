package com.liuwei.recyclerviewdemo.ui.fragment;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.liuwei.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.liuwei.android.diffrecycler.DiffRecyclerView;
import com.liuwei.android.diffrecycler.DiffRecyclerView.ViewHolderTouchStateCallback;
import com.liuwei.android.diffrecycler.listener.OnItemClickListener;
import com.liuwei.recyclerviewdemo.R;
import com.liuwei.recyclerviewdemo.bean.UIData;
import com.liuwei.recyclerviewdemo.provider.DataProvider;

import java.util.List;

public class SimpleGridVerticalFragment extends BaseDiffRecyclerFragment
{
    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_recycler_grid_vertical;
    }

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_simple_grid_vertical, mOnItemClickListener);

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