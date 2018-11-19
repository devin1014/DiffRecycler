package com.neulion.core.widget.recyclerview.util;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

/**
 * User: liuwei
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
