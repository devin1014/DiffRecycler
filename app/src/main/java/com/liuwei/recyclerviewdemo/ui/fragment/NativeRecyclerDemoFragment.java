package com.liuwei.recyclerviewdemo.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liuwei.recyclerviewdemo.R;
import com.liuwei.recyclerviewdemo.bean.UIData;
import com.liuwei.recyclerviewdemo.provider.DataProvider;

import java.util.List;

/**
 * User: liuwei
 */
public class NativeRecyclerDemoFragment extends Fragment implements OnRefreshListener
{
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListAdapter mListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_native_recycler, container, false);
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);

        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if (position % 4 == 0)
                {
                    return 4;
                }

                return position % 4;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        mListAdapter = new ListAdapter();

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

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
    private class ListAdapter extends Adapter<Holder>
    {
        private int[] COLORS = new int[]{Color.parseColor("#eeeeee"), Color.parseColor("#bdbdbd")};

        private LayoutInflater mLayoutInflater;

        private List<UIData> mData;

        ListAdapter()
        {
            mLayoutInflater = getActivity().getLayoutInflater();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            return new Holder(mLayoutInflater.inflate(viewType % 2 == 0 ?
                    R.layout.adapter_list_common : R.layout.adapter_list_common2, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position)
        {
            holder.setData(getItem(position), position);

            holder.itemView.setBackgroundColor(COLORS[position % 2]);
        }

        @Override
        public int getItemCount()
        {
            return mData.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return position;
        }

        UIData getItem(int position)
        {
            return mData.get(position);
        }

        public void setData(List<UIData> data)
        {
            mData = data;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Holder
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class Holder extends ViewHolder implements OnClickListener
    {
        private TextView mIndex;

        private TextView mName;

        private TextView mDescription;

        private ImageView mImage;

        Holder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            mIndex = itemView.findViewById(R.id.index);

            mImage = itemView.findViewById(R.id.image);

            mName = itemView.findViewById(R.id.name);

            mDescription = itemView.findViewById(R.id.description);
        }

        public void setData(UIData data, int pos)
        {
            itemView.setTag(pos);

            mIndex.setText(data.getId());

            mImage.setImageResource(data.getImageRes());

            mName.setText(data.getName());

            mDescription.setText(data.getDescription());
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(getActivity(), "onClick:" + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    }
}
