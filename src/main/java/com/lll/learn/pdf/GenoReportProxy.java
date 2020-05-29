package com.lll.learn.pdf;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 11:11
 **/
public class GenoReportProxy implements InvocationHandler{
    private GenoReportBuilder builder;

    public GenoReportProxy(GenoReportBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if (name.startsWith("add")) {
            if (isStop()) {
                return builder;
            }
        }
        return method.invoke(builder, args);
    }

    private boolean isStop() {
        Integer part = builder.getPart();
        if (part > 0) {
            part--;
            builder.setPart(part);
        }else{
            builder.build();
            return true;
        }
        return false;
    }
}
