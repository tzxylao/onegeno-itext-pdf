package com.lll.learn.pdf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * @author: wangkangxi
 * @description: 结果
 * @create: 2019/8/22 21:07
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> {

    private static final Integer CODE = 20000;//默认返回码
    private static final String MSG = "";

    private Integer infoCode = CODE;
    private String infoMsg = MSG;
    private String description;
    private Boolean success = true;
    private T data;

    @JsonIgnore
    private HashMap<String, Object> exend;

    public Result() {
        exend = new HashMap<>();
    }

    public static Result failure(Integer code, String msg) {
        Result result = new Result();
        result.setInfoCode(code);
        result.setInfoMsg(msg);
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.setInfoCode(CODE);
        result.setInfoMsg(msg);
        return result;
    }

    public static <E> Result<E> ok(E o) {
        Result<E> result = new Result<>();
        result.setInfoCode(CODE);
        result.setInfoMsg(MSG);
        result.setData(o);
        return result;
    }

    public static Result ok() {
        return ok(new Object());
    }


    public static Result ok(Boolean sucess) {
        Result result = new Result();
        result.setInfoCode(CODE);
        result.setInfoMsg(MSG);
        result.setSuccess(sucess);
        return result;
    }


    public static <E> Result<E> ok(Boolean sucess, E o) {
        Result<E> result = new Result<>();
        result.setInfoCode(CODE);
        result.setInfoMsg(MSG);
        result.setSuccess(sucess);
        result.setData(o);
        return result;
    }

    public Result put(String key, Object value) {
        exend.put(key, value);
        return this;
    }
}
