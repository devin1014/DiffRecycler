package com.neulion.recyclerviewdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

public class SimpleAdapterDemo2Fragment extends BaseDiffRecyclerFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recyclerview2, container, false);
    }

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_list_databinding2, mOnItemClickListener);

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    private OnItemClickListener<UIDataInterface> mOnItemClickListener = new OnItemClickListener<UIDataInterface>()
    {
        @Override
        public void onItemClick(View view, UIDataInterface uiData)
        {
            Toast.makeText(getActivity(), "onItemClick:" + mListAdapter.findItemPosition(uiData), Toast.LENGTH_SHORT).show();

            mListAdapter.removeItem(uiData);
        }
    };
}