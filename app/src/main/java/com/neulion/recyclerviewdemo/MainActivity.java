package com.neulion.recyclerviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.neulion.android.diffrecycler.util.DiffRecyclerLogger;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DiffRecyclerLogger.setDEBUG(true);

        setContentView(R.layout.activity_main);

        initComponent();

        showFragment(new DataBindingFragment());
    }

    private void initComponent()
    {
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radio_native:

                        showFragment(new NativeRecyclerViewFragment());

                        break;

                    case R.id.radio_common:

                        showFragment(new CommonListFragment());

                        break;

                    case R.id.radio_databinding:

                        showFragment(new DataBindingFragment());

                        break;
                }
            }
        });

        radioGroup.check(R.id.radio_databinding);
    }

    private void showFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()

                .replace(R.id.fragment_page, fragment)

                .commit();
    }
}
