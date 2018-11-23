package com.neulion.recyclerviewdemo;

import android.view.View;
import android.widget.Toast;

import com.neulion.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.neulion.android.diffrecycler.DiffRecyclerView;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;
import com.neulion.recyclerviewdemo.provider.DataProvider;

/**
 * User: NeuLion
 */
public class SimpleAdapterDemoFragment extends BaseDiffRecyclerFragment
{
    @Override
    protected void initRecyclerView(DiffRecyclerView recyclerView)
    {
        mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_list_databinding, mOnItemClickListener);

        mListAdapter.setData(DataProvider.getData());

        recyclerView.setAdapter(mListAdapter);
    }

    private OnItemClickListener<UIDataInterface> mOnItemClickListener = new OnItemClickListener<UIDataInterface>()
    {
        @Override
        public void onItemClick(View view, UIDataInterface uiData)
        {
            Toast.makeText(getActivity(), "onItemClick:" + mListAdapter.findItemPosition(uiData), Toast.LENGTH_SHORT).show();

            mListAdapter.removeItem(uiData);
        }
    };
}
