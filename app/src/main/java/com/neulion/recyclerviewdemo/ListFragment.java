package com.neulion.recyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.provider.DataProvider;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-14
 * Time: 15:19
 */
public class ListFragment extends Fragment implements OnRefreshListener
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

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));

        //recyclerView.addItemDecoration(new DividerDecoration(50, 50));

        mListAdapter = new ListAdapter();

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
    private class ListAdapter extends Adapter<Holder>
    {
        private LayoutInflater mLayoutInflater;

        private List<UIData> mData;

        ListAdapter()
        {
            mLayoutInflater = getActivity().getLayoutInflater();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            return new Holder(mLayoutInflater.inflate(R.layout.item_recycler_view_2, parent, false));
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
        private TextView mName;

        private TextView mDescription;

        private TextView mDate;

        private ImageView mImage;

        Holder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            mImage = itemView.findViewById(R.id.image);

            mName = itemView.findViewById(R.id.name);

            mDescription = itemView.findViewById(R.id.description);

            mDate = itemView.findViewById(R.id.date);
        }

        public void setData(UIData data, int pos)
        {
            itemView.setTag(pos);

            mImage.setImageResource(data.getImageRes());

            mName.setText(data.getName());

            mDescription.setText(data.getDescription());

            mDate.setText(data.getDate());
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(getActivity(), "onClick:" + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    }
}