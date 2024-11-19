package com.coolers.cloud.cloudGateway.util;

import com.coolers.cloud.cloudGateway.constant.BaseConst;

import java.util.Collection;

public class BaseUtil {
    public static boolean isEmptyObject(Object object) {
        return object == null;
    }

    public static boolean isNotEmptyObject(Object object) {
        return object != null;
    }

    public static boolean isEmptyMathObject(Object object) {
        return object == null || (Integer) object == 0;
    }

    public static boolean isNotEmptyMathObject(Object object) {
        return object != null && (Integer) object == 0;
    }

    public static boolean isEmptyCollection(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmptyCollection(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 通过上送的req_body截取出userId
     */
    public static String getUserIdByBody(String params) {
        String[] ps = params.split("&");
        for (String p : ps) {
            String[] sArr = p.split("=");
            if (sArr.length > 1 && BaseConst.USER_ID_KEY.equals(sArr[0])) {
                return sArr[1];
            }
        }
        return "";
    }
}
