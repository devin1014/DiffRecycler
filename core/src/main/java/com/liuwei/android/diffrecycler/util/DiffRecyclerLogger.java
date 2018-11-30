package com.liuwei.android.diffrecycler.util;

import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;

/**
 * User: liuwei
 */
public class DiffRecyclerLogger
{
    private static boolean DEBUG = false;

    private static final String TAG = "NLLog";

    public static void setDEBUG(boolean debug)
    {
        DEBUG = debug;
    }

    public static void log(Object object, String message)
    {
        if (DEBUG)
        {
            Log.d(TAG + getTagFromObject(object), message);
        }
    }

    public static void info(Object object, String message)
    {
        if (DEBUG)
        {
            Log.i(TAG + getTagFromObject(object), message);
        }
    }

    public static void warn(Object object, String message)
    {
        if (DEBUG)
        {
            Log.w(TAG + getTagFromObject(object), message);
        }
    }

    public static void error(Object object, String message)
    {
        if (DEBUG)
        {
            Log.e(TAG + getTagFromObject(object), message);
        }
    }

    private static String getTagFromObject(Object object)
    {
        if (object == null)
        {
            return "null";
        }

        return object.getClass().getSimpleName() + "@" + Integer.toHexString(object.hashCode());
    }

    // ----------------------------------------------------------------------------------------
    // Test
    // ----------------------------------------------------------------------------------------
    private static SparseArray<Long> mSparseArray = new SparseArray<>();

    public static void set(Object object)
    {
        if (DEBUG)
        {
            mSparseArray.put(object.hashCode(), SystemClock.uptimeMillis());
        }
    }

    public static void test(Object object, String msg)
    {
        if (DEBUG)
        {
            final long duration = SystemClock.uptimeMillis() - mSparseArray.get(object.hashCode());

            if (duration >= 100)
            {
                DiffRecyclerLogger.warn(object, msg + " @(" + duration + "ms)");
            }
            else
            {
                DiffRecyclerLogger.info(object, msg + " @(" + duration + "ms)");
            }
        }

        mSparseArray.remove(object.hashCode());
    }
}
