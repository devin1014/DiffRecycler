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

import com.neulion.android.diffrecycler.DiffRecyclerAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.holder.DiffViewHolder;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

/**
 * User: NeuLion
 */
public class CustomAdapterDemoFragment extends BaseDiffRecyclerFragment implements OnRefreshListener
{
    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new CustomRecyclerAdapter(getLayoutInflater());

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Adapter
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class CustomRecyclerAdapter extends DiffRecyclerAdapter<UIDataInterface>
    {
        private int[] COLORS = new int[]{Color.parseColor("#aa00aa00"), Color.parseColor("#aa0000aa")};

        CustomRecyclerAdapter(LayoutInflater inflater)
        {
            super(inflater);
        }

        @Override
        public DiffViewHolder<UIDataInterface> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
        {
            return new CustomViewHolder(inflater.inflate(getViewHolderLayout(viewType), parent, false));
        }

        @Override
        public void onBindViewHolder(DiffViewHolder<UIDataInterface> holder, UIDataInterface data, int position)
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
    private class CustomViewHolder extends DiffViewHolder<UIDataInterface> implements OnClickListener
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
