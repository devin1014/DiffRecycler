package com.neulion.recyclerviewdemo.ui.fragment;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.DiffRecyclerView.ViewHolderTouchStateCallback;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.provider.DataProvider;

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