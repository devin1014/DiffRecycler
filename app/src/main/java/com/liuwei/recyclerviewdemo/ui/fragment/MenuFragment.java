package com.liuwei.recyclerviewdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuwei.android.diffrecycler.DiffRecyclerSimpleAdapter;
import com.liuwei.android.diffrecycler.DiffRecyclerView;
import com.liuwei.android.diffrecycler.listener.OnItemClickListener;
import com.liuwei.recyclerviewdemo.MainActivity;
import com.liuwei.recyclerviewdemo.R;
import com.liuwei.recyclerviewdemo.bean.UIMenu;
import com.liuwei.recyclerviewdemo.provider.DataProvider;

public class MenuFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);
    }

    private void initComponent(View view)
    {
        DiffRecyclerView recyclerView = view.findViewById(R.id.diff_recyclerview);

        DiffRecyclerSimpleAdapter<UIMenu> adapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_menu, mOnItemClickListener);

        adapter.setData(DataProvider.getMenus());

        recyclerView.setAdapter(adapter);
    }

    private OnItemClickListener<UIMenu> mOnItemClickListener = new OnItemClickListener<UIMenu>()
    {
        @Override
        public void onItemClick(View view, UIMenu uiMenu)
        {
            try
            {
                Fragment fragment = (Fragment) uiMenu.getMenuClass().newInstance();

                ((MainActivity) getActivity()).showFragment(fragment);
            }
            catch (java.lang.InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    };
}
