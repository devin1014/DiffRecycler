package com.liuwei.recyclerviewdemo.ui.fragment;

import android.view.View;
import android.widget.Toast;

import com.liuwei.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.liuwei.android.diffrecycler.DiffRecyclerView;
import com.liuwei.android.diffrecycler.listener.OnItemClickListener;
import com.liuwei.recyclerviewdemo.R;
import com.liuwei.recyclerviewdemo.bean.UIData;
import com.liuwei.recyclerviewdemo.provider.DataProvider;

import java.util.List;

public class SimpleGridHorizontalFragment extends BaseDiffRecyclerFragment
{
    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_recycler_grid_horizontal;
    }

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mSwipeRefreshLayout.setEnabled(false);

        mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_simple_grid_horizontal, mOnItemClickListener);

        mListAdapter.setData(DataProvider.getDataWithDrag());

        recyclerView.setAdapter(mListAdapter);
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