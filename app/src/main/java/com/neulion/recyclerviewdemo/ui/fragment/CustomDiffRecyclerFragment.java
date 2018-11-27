package com.neulion.recyclerviewdemo.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.DiffRecyclerView.ViewHolderTouchStateCallback;
import com.neulion.android.diffrecycler.holder.DiffViewHolder;
import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.provider.DataProvider;

import java.util.List;

/**
 * User: NeuLion
 */
public class CustomDiffRecyclerFragment extends BaseDiffRecyclerFragment implements OnRefreshListener
{
    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new CustomRecyclerAdapter(getLayoutInflater());

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);

        recyclerView.setOnViewHolderTouchStateCallback(new ViewHolderTouchStateCallback()
        {
            @Override
            public void onViewHolderTouchStateChanged(ViewHolder viewHolder, int actionState)
            {
                mSwipeRefreshLayout.setEnabled(actionState == ItemTouchHelper.ACTION_STATE_IDLE);
            }
        });
    }

    @Override
    public List<UIData> getData()
    {
        return DataProvider.getData();
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adapter
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class CustomRecyclerAdapter extends DiffRecyclerAdapter<UIData>
    {
        private int[] COLORS = new int[]{Color.parseColor("#aa00aa00"), Color.parseColor("#aa0000aa")};

        CustomRecyclerAdapter(LayoutInflater inflater)
        {
            super(inflater);
        }

        @Override
        public DiffViewHolder<UIData> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
        {
            return new CustomViewHolder(inflater.inflate(getViewHolderLayout(viewType), parent, false));
        }

        @Override
        public void onBindViewHolder(DiffViewHolder<UIData> holder, UIData data, int position)
        {
            holder.itemView.setTag(position);

            holder.itemView.setBackgroundColor(COLORS[position % 2]);

            ((ImageView) holder.findViewById(R.id.image)).setImageResource(data.getImageRes());

            ((TextView) holder.findViewById(R.id.index)).setText(String.valueOf(position));

            ((TextView) holder.findViewById(R.id.name)).setText(data.getName());

            ((TextView) holder.findViewById(R.id.description)).setText(data.getDescription());
        }

        @Override
        protected int getViewHolderLayout(int viewType)
        {
            return R.layout.adapter_list_common;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Holder
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class CustomViewHolder extends DiffViewHolder<UIData> implements OnClickListener
    {
        CustomViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(getActivity(), "单击事件:" + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    }
}
