package com.neulion.recyclerviewdemo.ui.fragment;

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

import com.neulion.android.diffrecycler.DiffRecyclerAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIData;

import java.util.List;

public abstract class BaseDiffRecyclerFragment extends Fragment implements OnRefreshListener, OnClickListener
{
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected DiffRecyclerView mRecyclerView;

    protected DiffRecyclerAdapter<UIData> mListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_linear_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);

        initRecyclerView(mRecyclerView);
    }

    private void initComponent(View view)
    {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        view.findViewById(R.id.clear_data).setOnClickListener(this);

        view.findViewById(R.id.add_header).setOnClickListener(this);

        view.findViewById(R.id.add_footer).setOnClickListener(this);

        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    protected abstract void initRecyclerView(DiffRecyclerView recyclerView);

    @Override
    public final void onRefresh()
    {
        mSwipeRefreshLayout.setRefreshing(false);

        List<UIData> list = getData();

        resetData(list);

        Toast.makeText(getActivity(), String.format("刷新数据：%s", list.size()), Toast.LENGTH_SHORT).show();
    }

    public abstract List<UIData> getData();

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.clear_data:

                clearData();

                break;

            case R.id.add_header:

                addHeader();

                break;

            case R.id.add_footer:

                addFooter();

                break;
        }
    }

    protected void resetData(List<UIData> list)
    {
        mListAdapter.setData(list);
    }

    protected void clearData()
    {
        mListAdapter.setData(null);
    }

    public void addHeader()
    {
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.comp_header, mRecyclerView, false);

        headerView.setOnLongClickListener(mHeaderLongClickListener);

        ((TextView) headerView.findViewById(R.id.header_position)).setText(String.valueOf(mRecyclerView.addHeader(headerView)));
    }

    public void addFooter()
    {
        View inflaterView = getActivity().getLayoutInflater().inflate(R.layout.comp_footer, mRecyclerView, false);

        inflaterView.setOnLongClickListener(mFooterLongClickListener);

        ((TextView) inflaterView.findViewById(R.id.footer_position)).setText(String.valueOf(mRecyclerView.addFooter(inflaterView)));
    }

    private OnLongClickListener mHeaderLongClickListener = new OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {
            Toast.makeText(getActivity(), String.format("删除 Header , %s", mRecyclerView.removeHeader(v)), Toast.LENGTH_SHORT).show();

            return true;
        }
    };

    private OnLongClickListener mFooterLongClickListener = new OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {
            Toast.makeText(getActivity(), String.format("删除 Footer , %s", mRecyclerView.removeFooter(v)), Toast.LENGTH_SHORT).show();

            return true;
        }
    };
}
