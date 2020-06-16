package com.onegene.pdf.util;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/6/16 15:36
 **/
public class Precondition {
    public static void checkNotNull(Object obj, String msg) {
        if (obj == null) {
            throw new DataException(msg);
        }
    }
}
