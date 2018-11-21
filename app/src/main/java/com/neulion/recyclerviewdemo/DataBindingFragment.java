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
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

/**
 * User: NeuLion
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

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        mListAdapter = new ListAdapter(getLayoutInflater(), mOnItemClickListener);

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);

        view.findViewById(R.id.clear_data).setOnClickListener(mOnClickListener);

        view.findViewById(R.id.add_header).setOnClickListener(mOnClickListener);

        view.findViewById(R.id.add_footer).setOnClickListener(mOnClickListener);
    }

    @Override
    public void onRefresh()
    {
        mSwipeRefreshLayout.setRefreshing(false);

        mListAdapter.setData(DataProvider.getData());
    }

    private void addHeader()
    {
        int headerSize = mListAdapter.getHeaderSize();

        final View headerView = getActivity().getLayoutInflater()
                .inflate(headerSize % 2 == 0 ? R.layout.comp_header1 : R.layout.comp_header2, null, false);

        int index = mListAdapter.addHeader(headerView);

        ((TextView) headerView.findViewById(R.id.header_position)).setText(String.valueOf(index));

        headerView.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                int pos = mListAdapter.removeHeader(v);

                if (pos != -1)
                {
                    Toast.makeText(getActivity(), String.format("删除第%s", pos), Toast.LENGTH_SHORT).show();
                }

                return pos != -1;
            }
        });
    }

    private void addFooter()
    {
        int size = mListAdapter.getFooterSize();

        final View inflaterView = getActivity().getLayoutInflater()
                .inflate(size % 2 == 0 ? R.layout.comp_header1 : R.layout.comp_header2, null, false);

        int index = mListAdapter.addFooter(inflaterView);

        ((TextView) inflaterView.findViewById(R.id.header_position)).setText(String.valueOf(index));

        inflaterView.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                int pos = mListAdapter.removeFooter(v);

                if (pos != -1)
                {
                    Toast.makeText(getActivity(), String.format("删除第%s", pos), Toast.LENGTH_SHORT).show();
                }

                return pos != -1;
            }
        });
    }

    private OnClickListener mOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.clear_data:

                    mListAdapter.setData(null);

                    break;

                case R.id.add_header:

                    addHeader();

                    break;

                case R.id.add_footer:

                    addFooter();

                    break;
            }
        }
    };

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
            return R.layout.item_recycler_view_binding;
        }

        @Override
        public void onBindViewHolder(DataBindingHolder<UIDataInterface> holder, UIDataInterface data, int position)
        {
            super.onBindViewHolder(holder, data, position);

            holder.itemView.setBackgroundColor(COLORS[position % 2]);
        }
    }
}
