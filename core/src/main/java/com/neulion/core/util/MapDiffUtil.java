package com.neulion.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: NeuLion
 */
public class MapDiffUtil
{
    public static boolean compare(Map<String, Comparable> oldMap, Map<String, Comparable> newMap)
    {
        // old & new map is empty
        if ((oldMap == null || oldMap.size() == 0) && (newMap == null || newMap.size() == 0))
        {
            return true;
        }

        int oldMapSize = oldMap != null ? oldMap.size() : 0;

        int newMapSize = newMap != null ? newMap.size() : 0;

        if (oldMapSize != newMapSize)
        {
            return false;
        }

        for (String key : oldMap.keySet())
        {
            if (!newMap.containsKey(key))
            {
                return false;
            }

            if (!compare(oldMap.get(key), newMap.get(key)))
            {
                return false;
            }
        }

        return true;
    }

    public static Map<String, Comparable> diff(Map<String, Comparable> oldMap, Map<String, Comparable> newMap)
    {
        if ((oldMap == null || oldMap.size() == 0) && (newMap == null || newMap.size() == 0))
        {
            return null;
        }

        if (oldMap == null || oldMap.size() == 0)
        {
            return newMap;
        }

        if (newMap == null || newMap.size() == 0)
        {
            return oldMap;
        }

        Map<String, Comparable> result = new HashMap<>();

        for (String key : oldMap.keySet())
        {
            if (!newMap.containsKey(key))
            {
                result.put(key, oldMap.get(key));
            }
            else if (!compare(oldMap.get(key), newMap.get(key)))
            {
                result.put(key, newMap.get(key));
            }
        }

        for (String key : newMap.keySet())
        {
            if (!oldMap.containsKey(key))
            {
                result.put(key, newMap.get(key));
            }
            else if (!compare(newMap.get(key), oldMap.get(key)))
            {
                result.put(key, newMap.get(key));
            }
        }

        return result;
    }

    @SuppressWarnings({"unchecked", "BooleanMethodIsAlwaysInverted"})
    private static boolean compare(Comparable sourceObj, Comparable targetObj)
    {
        try
        {
            return sourceObj.compareTo(targetObj) == 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
    }
}
