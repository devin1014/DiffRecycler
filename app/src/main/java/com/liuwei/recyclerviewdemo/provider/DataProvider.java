package com.liuwei.recyclerviewdemo.provider;

import com.liuwei.recyclerviewdemo.R;
import com.liuwei.recyclerviewdemo.bean.UIData;
import com.liuwei.recyclerviewdemo.bean.UIDataImp;
import com.liuwei.recyclerviewdemo.bean.UIMenu;
import com.liuwei.recyclerviewdemo.bean.UIMenuImp;
import com.liuwei.recyclerviewdemo.ui.fragment.CustomDiffRecyclerFragment;
import com.liuwei.recyclerviewdemo.ui.fragment.NativeRecyclerDemoFragment;
import com.liuwei.recyclerviewdemo.ui.fragment.SimpleGridHorizontalFragment;
import com.liuwei.recyclerviewdemo.ui.fragment.SimpleGridVerticalFragment;
import com.liuwei.recyclerviewdemo.ui.fragment.SimpleLinearHorizontalFragment;
import com.liuwei.recyclerviewdemo.ui.fragment.SimpleLinearVerticalFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: liuwei
 */
public class DataProvider
{
    private static final int[] IMAGES = new int[10];

    static
    {
        IMAGES[0] = R.drawable.placekitten_1;
        IMAGES[1] = R.drawable.placekitten_1;
        IMAGES[2] = R.drawable.placekitten_2;
        IMAGES[3] = R.drawable.placekitten_2;
        IMAGES[4] = R.drawable.placekitten_3;
        IMAGES[5] = R.drawable.placekitten_3;
        IMAGES[6] = R.drawable.placekitten_4;
        IMAGES[7] = R.drawable.placekitten_4;
        IMAGES[8] = R.drawable.placekitten_4;
        IMAGES[9] = R.drawable.placekitten_4;
    }

    private static final int DATA_COUNT = 20;

    public static List<UIData> getData()
    {
        Random random = new Random(System.currentTimeMillis());

        int count = random.nextInt(DATA_COUNT);

        List<UIData> list = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
        {
            int randomIndex = random.nextInt(10) % 10;

            String description;

            if (randomIndex < 2)
            {
                description = "第2张图片";
            }
            else if (randomIndex < 4)
            {
                description = "第2张图片";
            }
            else if (randomIndex < 7)
            {
                description = "第3张图片";
            }
            else
            {
                description = "第4张图片";
            }

            list.add(new UIDataImp(i, description, "", IMAGES[randomIndex]));
        }

        return list;
    }

    public static List<UIData> getDataWithSwipeDrag()
    {
        Random random = new Random(System.currentTimeMillis());

        int count = random.nextInt(DATA_COUNT);

        List<UIData> list = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
        {
            int randomIndex = random.nextInt(10) % 10;

            String description;

            if (randomIndex < 2)
            {
                description = "第2张图片";
            }
            else if (randomIndex < 4)
            {
                description = "第2张图片";
            }
            else if (randomIndex < 7)
            {
                description = "第3张图片";
            }
            else
            {
                description = "第4张图片";
            }

            list.add(new UIDataImp(i, description, "单机置顶，左右滑动删除，长按拖拽", IMAGES[randomIndex]));
        }

        return list;
    }

    public static List<UIData> getDataWithDrag()
    {
        Random random = new Random(System.currentTimeMillis());

        int count = random.nextInt(DATA_COUNT);

        List<UIData> list = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
        {
            int randomIndex = random.nextInt(10) % 10;

            String description;

            if (randomIndex < 2)
            {
                description = "第2张图片";
            }
            else if (randomIndex < 4)
            {
                description = "第2张图片";
            }
            else if (randomIndex < 7)
            {
                description = "第3张图片";
            }
            else
            {
                description = "第4张图片";
            }

            list.add(new UIDataImp(i, description, "单机置顶，长按拖拽", IMAGES[randomIndex]));
        }

        return list;
    }

    public static List<UIMenu> getMenus()
    {
        List<UIMenu> list = new ArrayList<>();

        list.add(new UIMenuImp("SimpleLinearVertical", SimpleLinearVerticalFragment.class));
        list.add(new UIMenuImp("SimpleLinearHorizontal", SimpleLinearHorizontalFragment.class));
        list.add(new UIMenuImp("SimpleGridVertical", SimpleGridVerticalFragment.class));
        list.add(new UIMenuImp("SimpleGridHorizontal", SimpleGridHorizontalFragment.class));
        list.add(new UIMenuImp("CustomDiffRecycler", CustomDiffRecyclerFragment.class));
        list.add(new UIMenuImp("NativeRecycler", NativeRecyclerDemoFragment.class));

        return list;
    }
}
