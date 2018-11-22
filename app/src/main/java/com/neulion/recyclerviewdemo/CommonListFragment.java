package com.neulion.recyclerviewdemo;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.adapter.BaseRecyclerViewAdapter;
import com.neulion.android.diffrecycler.holder.BaseViewHolder;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

import java.util.List;

/**
 * User: NeuLion
 */
public class CommonListFragment extends BaseDiffRecyclerFragment implements OnRefreshListener
{
    private ListAdapter mListAdapter;

    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new ListAdapter(getLayoutInflater());

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    @Override
    protected void resetData(List<UIDataInterface> list)
    {
        mListAdapter.setData(list);

        mListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void clearData()
    {
        mListAdapter.setData(null);
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adapter
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class ListAdapter extends BaseRecyclerViewAdapter<UIDataInterface, CustomViewHolder>
    {
        private int[] COLORS = new int[]{Color.parseColor("#eeeeee"), Color.parseColor("#bdbdbd")};

        ListAdapter(LayoutInflater inflater)
        {
            super(inflater);
        }

        @Override
        public CustomViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
        {
            return new CustomViewHolder(inflater.inflate(R.layout.item_recycler_view_2, parent, false));
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, UIDataInterface data, int position)
        {
            holder.itemView.setTag(position);

            holder.itemView.setBackgroundColor(COLORS[position % 2]);

            ((ImageView) holder.findViewById(R.id.image)).setImageResource(data.getImageRes());

            ((TextView) holder.findViewById(R.id.name)).setText(data.getName());

            ((TextView) holder.findViewById(R.id.description)).setText(data.getDescription());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Holder
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class CustomViewHolder extends BaseViewHolder<UIDataInterface> implements OnClickListener
    {
        CustomViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(getActivity(), "onClick:" + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    }
}
