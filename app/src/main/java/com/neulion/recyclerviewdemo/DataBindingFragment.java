package com.neulion.recyclerviewdemo;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.databinding.ItemRecyclerViewBindingBinding;
import com.neulion.recyclerviewdemo.provider.DataProvider;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-17
 * Time: 11:28
 */
public class DataBindingFragment extends Fragment implements OnRefreshListener
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

        mListAdapter = new ListAdapter(getLayoutInflater(), mOnItemClickListener);

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    private Handler mHandler = new Handler();

    @Override
    public void onRefresh()
    {
        mSwipeRefreshLayout.setRefreshing(false);

        mListAdapter.setData(DataProvider.getData());

        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mListAdapter.setData(DataProvider.getDataCache());
            }
        });
    }

    private OnItemClickListener<UIData> mOnItemClickListener = new OnItemClickListener<UIData>()
    {
        @Override
        public void onItemClick(View view, UIData uiData)
        {
            Toast.makeText(getActivity(), "onItemClick:" + mListAdapter.findItemPosition(uiData), Toast.LENGTH_SHORT).show();
        }
    };

    private static int[] COLORS = new int[]{Color.parseColor("#eeeeee"), Color.parseColor("#bdbdbd")};

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adapter
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class ListAdapter extends DiffDataBindingAdapter<UIData>
    {
        ListAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIData> listener)
        {
            super(layoutInflater, listener);
        }

        @Override
        protected int getLayout(int viewType)
        {
            return R.layout.item_recycler_view_binding;
        }

        @Override
        public void onBindViewHolder(DataBindingHolder<UIData> holder, UIData data, int position)
        {
            holder.itemView.setBackgroundColor(COLORS[position % 2]);

            ViewDataBinding dataBinding = holder.getViewDataBinding();

            ((ItemRecyclerViewBindingBinding) dataBinding).setData(data);

            ((ItemRecyclerViewBindingBinding) dataBinding).setClickListener(this);

            dataBinding.executePendingBindings();
        }
    }
}
