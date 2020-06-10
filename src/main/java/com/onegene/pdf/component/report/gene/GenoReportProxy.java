package com.onegene.pdf.component.report.gene;

import com.onegene.pdf.component.AbstractReportBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 11:11
 **/
@Slf4j
public class GenoReportProxy implements InvocationHandler {
    private AbstractReportBuilder builder;

    public GenoReportProxy(AbstractReportBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String name = method.getName();
        if (name.startsWith("add")) {
            if (isStop()) {
                return builder;
            }
        }
        Object invoke = method.invoke(builder, args);
        stopWatch.stop();
        log.info(method.getName() + "耗时：" + stopWatch.getTotalTimeMillis() + "ms");
        return invoke;
    }

    private boolean isStop() {
        Integer part = builder.getPart();
        if (part > 0) {
            part--;
            builder.setPart(part);
        } else {
            builder.build();
            return true;
        }
        return false;
    }
}
