package com.neulion.recyclerviewdemo.ui.fragment;

import android.view.View;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.provider.DataProvider;

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