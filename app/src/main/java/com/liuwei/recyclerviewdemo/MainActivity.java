package com.liuwei.recyclerviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.liuwei.android.diffrecycler.util.DiffRecyclerLogger;
import com.liuwei.recyclerviewdemo.ui.fragment.MenuFragment;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // show DiffRecycler logger
        DiffRecyclerLogger.setDEBUG(true);

        setContentView(R.layout.activity_main);

        showFragment(new MenuFragment());
    }

    public void showFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()

                .replace(R.id.fragment_page, fragment)

                .addToBackStack(fragment.getClass().getSimpleName())

                .commit();
    }
}
