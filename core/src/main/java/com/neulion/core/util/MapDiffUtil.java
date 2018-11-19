package com.neulion.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: NeuLion
 */
public class MapDiffUtil
{
    public static boolean compare(Map<String, Object> oldMap, Map<String, Object> newMap)
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

            if (!isEquals(oldMap.get(key), newMap.get(key)))
            {
                return false;
            }
        }

        return true;
    }

    public static Map<String, Object> diff(Map<String, Object> oldMap, Map<String, Object> newMap)
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

        Map<String, Object> result = new HashMap<>();

        for (String key : oldMap.keySet())
        {
            if (!newMap.containsKey(key))
            {
                result.put(key, oldMap.get(key));
            }
            else if (!isEquals(oldMap.get(key), newMap.get(key)))
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
            else if (!isEquals(newMap.get(key), oldMap.get(key)))
            {
                result.put(key, newMap.get(key));
            }
        }

        return result;
    }

    private static boolean isEquals(Object sourceObj, Object targetObj)
    {
        return sourceObj != null && sourceObj.equals(targetObj);
    }
}
