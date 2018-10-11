package com.neulion.recyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.neulion.core.widget.recyclerview.holder.BaseViewHolder;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.provider.DataProvider;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-14
 * Time: 16:09
 */
public class CommonListFragment extends Fragment implements OnRefreshListener
{
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListAdapter mListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);
    }

    private void initComponent(View view)
    {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mListAdapter = new ListAdapter(getLayoutInflater());

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    private static int[] COLORS = new int[]{Color.parseColor("#eeeeee"), Color.parseColor("#bdbdbd")};

    @Override
    public void onRefresh()
    {
        mSwipeRefreshLayout.setRefreshing(false);

        mListAdapter.setData(DataProvider.getData());

        mListAdapter.notifyDataSetChanged();
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adapter
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class ListAdapter extends BaseRecyclerViewAdapter<UIData, Holder>
    {
        ListAdapter(LayoutInflater inflater)
        {
            super(inflater);
        }

        @Override
        public Holder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
        {
            return new Holder(inflater.inflate(R.layout.item_recycler_view_2, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, UIData data, int position)
        {
            holder.itemView.setTag(position);

            holder.itemView.setBackgroundColor(COLORS[position % 2]);

            ((ImageView) holder.findViewById(R.id.image)).setImageResource(data.getImageRes());

            ((TextView) holder.findViewById(R.id.name)).setText(data.getName());

            ((TextView) holder.findViewById(R.id.description)).setText(data.getDescription());

            ((TextView) holder.findViewById(R.id.date)).setText(data.getDate());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Holder
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class Holder extends BaseViewHolder<UIData> implements OnClickListener
    {
        Holder(View itemView)
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
