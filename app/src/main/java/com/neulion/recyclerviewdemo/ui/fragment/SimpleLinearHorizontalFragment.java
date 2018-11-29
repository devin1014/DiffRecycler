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

public class SimpleLinearHorizontalFragment extends BaseDiffRecyclerFragment
{
    @Override
    protected int getFragmentLayoutId()
    {
        return R.layout.fragment_recycler_linear_horizontal;
    }

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mSwipeRefreshLayout.setEnabled(false);

        mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_simple_linear_horizontal, mOnItemClickListener);

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    @Override
    public List<UIData> getData()
    {
        return DataProvider.getDataWithSwipeDrag();
    }

    private OnItemClickListener<UIData> mOnItemClickListener = new OnItemClickListener<UIData>()
    {
        @Override
        public void onItemClick(View view, UIData uiData)
        {
            Toast.makeText(getActivity(), "onItemClick:" + mListAdapter.findItemPosition(uiData), Toast.LENGTH_SHORT).show();
        }
    };
}
