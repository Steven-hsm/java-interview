package com.hsm.common;

import java.util.Collection;

/**
 * @Classname CollectionUtil
 * @Description TODO
 * @Date 2021/9/13 10:51
 * @Created by huangsm
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }


}
