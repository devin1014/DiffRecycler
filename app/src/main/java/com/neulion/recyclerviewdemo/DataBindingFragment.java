package com.neulion.recyclerviewdemo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffDataBindingAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.holder.DataBindingHolder;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

import java.util.List;

/**
 * User: NeuLion
 */
public class DataBindingFragment extends BaseDiffRecyclerFragment
{
    private ListAdapter mListAdapter;

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new ListAdapter(getLayoutInflater(), mOnItemClickListener);

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void resetData(List<UIDataInterface> list)
    {
        mListAdapter.setData(list);
    }

    @Override
    protected void clearData()
    {
        mListAdapter.setData(null);
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

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adapter
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class ListAdapter extends DiffDataBindingAdapter<UIDataInterface>
    {
        private int[] COLORS = new int[]{Color.parseColor("#eeeeee"), Color.parseColor("#bdbdbd")};

        ListAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIDataInterface> listener)
        {
            super(layoutInflater, listener);
        }

        @Override
        protected int getLayout(int viewType)
        {
            return R.layout.adapter_list_common;
        }

        @Override
        public void onBindViewHolder(DataBindingHolder<UIDataInterface> holder, UIDataInterface data, int position)
        {
            super.onBindViewHolder(holder, data, position);

            holder.itemView.setBackgroundColor(COLORS[position % 2]);
        }
    }
}
