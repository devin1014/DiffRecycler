package com.neulion.android.diffrecycler.holder;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.neulion.android.diffrecycler.diff.DataDiffCompare;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

public class DiffViewHolder<T extends DataDiffCompare<T>> extends ViewHolder
{
    private SparseArray<View> mViewSparseArray;

    private OnItemClickListener<T> mOnItemClickListener;

    private DiffViewDataBindingInterface<T> mViewDataBindingInterface;

    public DiffViewHolder(View itemView)
    {
        this(itemView, null);
    }

    public DiffViewHolder(View itemView, OnItemClickListener<T> listener)
    {
        super(itemView);

        mOnItemClickListener = listener;

        mViewSparseArray = new SparseArray<>();
    }

    public DiffViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutId, OnItemClickListener<T> listener)
    {
        this(inflater.inflate(layoutId, parent, false), listener);
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setText(@IdRes int viewId, CharSequence value)
    {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public DiffViewHolder setText(@IdRes int viewId, @StringRes int strId)
    {
        TextView view = findViewById(viewId);
        view.setText(strId);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId)
    {
        ImageView view = findViewById(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color)
    {
        View view = findViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     *
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes)
    {
        View view = findViewById(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor)
    {
        TextView view = findViewById(viewId);
        view.setTextColor(textColor);
        return this;
    }


    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable)
    {
        ImageView view = findViewById(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    public DiffViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap)
    {
        ImageView view = findViewById(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    public DiffViewHolder setAlpha(@IdRes int viewId, float value)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            findViewById(viewId).setAlpha(value);
        }
        else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            findViewById(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setGone(@IdRes int viewId, boolean visible)
    {
        View view = findViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for INVISIBLE.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setVisible(@IdRes int viewId, boolean visible)
    {
        View view = findViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId The id of the TextView to linkify.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder linkify(@IdRes int viewId)
    {
        TextView view = findViewById(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     */
    public DiffViewHolder setTypeface(@IdRes int viewId, Typeface typeface)
    {
        TextView view = findViewById(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * Apply the typeface to all the given viewIds, and enable subpixel rendering.
     */
    public DiffViewHolder setTypeface(Typeface typeface, int... viewIds)
    {
        for (int viewId : viewIds)
        {
            TextView view = findViewById(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setProgress(@IdRes int viewId, int progress)
    {
        ProgressBar view = findViewById(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setProgress(@IdRes int viewId, int progress, int max)
    {
        ProgressBar view = findViewById(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setMax(@IdRes int viewId, int max)
    {
        ProgressBar view = findViewById(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setRating(@IdRes int viewId, float rating)
    {
        RatingBar view = findViewById(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The DiffViewHolder for chaining.
     */
    public DiffViewHolder setRating(@IdRes int viewId, float rating, int max)
    {
        RatingBar view = findViewById(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <V extends View> V findViewById(int id)
    {
        View cacheView = mViewSparseArray.get(id);

        if (cacheView == null)
        {
            View view = itemView.findViewById(id);

            mViewSparseArray.put(id, view);

            cacheView = view;
        }

        return (V) cacheView;
    }

    @SuppressWarnings("unchecked")
    public DiffViewDataBindingInterface<T> getViewDataBindingInterface()
    {
        if (mViewDataBindingInterface == null)
        {
            mViewDataBindingInterface = new DiffViewDataBindingInterfaceImp(itemView, mOnItemClickListener);
        }

        return mViewDataBindingInterface;
    }

}
