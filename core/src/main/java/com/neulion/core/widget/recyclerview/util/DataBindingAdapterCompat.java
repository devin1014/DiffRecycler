package com.neulion.core.widget.recyclerview.util;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-09-13
 * Time: 19:05
 */
public class DataBindingAdapterCompat
{
    // ------------------------------------------------------------------------------------
    // ImageView
    // ------------------------------------------------------------------------------------
    @BindingAdapter({"color"})
    public static void setColor(ImageView imageView, int color)
    {
        imageView.setBackgroundColor(color);
    }

    @BindingAdapter({"backgroundColor"})
    public static void setBackgroundColor(View view, int color)
    {
        view.setBackgroundColor(color);
    }

    @BindingAdapter({"image"})
    public static void setImage(ImageView imageView, int imageRes)
    {
        imageView.setImageResource(imageRes);
    }
}
