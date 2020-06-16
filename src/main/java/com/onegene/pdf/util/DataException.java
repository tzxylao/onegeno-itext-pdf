package com.onegene.pdf.util;

/**
 * @Author laoliangliang
 * @description
 * @Date 2019/11/27 14:33
 **/
public class DataException extends RuntimeException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
