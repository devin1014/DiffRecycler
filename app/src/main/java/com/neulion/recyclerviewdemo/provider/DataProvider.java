package com.neulion.recyclerviewdemo.provider;

import com.neulion.recyclerviewdemo.R;
import com.neulion.recyclerviewdemo.bean.UIData;
import com.neulion.recyclerviewdemo.bean.UIDataInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: NeuLion
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

    public static List<UIDataInterface> getData()
    {
        Random random = new Random(System.currentTimeMillis());

        int count = random.nextInt(20);

        List<UIDataInterface> list = new ArrayList<>(count);

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

            list.add(new UIData(i, "这是一只猫", description, IMAGES[randomIndex]));
        }

        return list;
    }
}
