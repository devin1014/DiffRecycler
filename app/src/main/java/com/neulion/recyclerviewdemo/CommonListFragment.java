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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.adapter.BaseRecyclerViewAdapter;
import com.neulion.android.diffrecycler.holder.BaseViewHolder;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

/**
 * User: NeuLion
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

        final DiffRecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mListAdapter = new ListAdapter(getLayoutInflater());

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);

        view.findViewById(R.id.clear_data).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListAdapter.setData(null);
            }
        });

        view.findViewById(R.id.add_header).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int headerSize = mListAdapter.getHeaderSize();

                final View headerView = getActivity().getLayoutInflater()
                        .inflate(headerSize % 2 == 0 ? R.layout.comp_header1 : R.layout.comp_header2, recyclerView, false);

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
        });

        view.findViewById(R.id.add_footer).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int size = mListAdapter.getFooterSize();

                final View inflaterView = getActivity().getLayoutInflater()
                        .inflate(size % 2 == 0 ? R.layout.comp_header1 : R.layout.comp_header2, recyclerView, false);

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
        });
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
    private class ListAdapter extends BaseRecyclerViewAdapter<UIDataInterface, ItemHolder>
    {
        ListAdapter(LayoutInflater inflater)
        {
            super(inflater);
        }

        @Override
        public ItemHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
        {
            return new ItemHolder(inflater.inflate(R.layout.item_recycler_view_2, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, UIDataInterface data, int position)
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
    private class ItemHolder extends BaseViewHolder<UIDataInterface> implements OnClickListener
    {
        ItemHolder(View itemView)
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
