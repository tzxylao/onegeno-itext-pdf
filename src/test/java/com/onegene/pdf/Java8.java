package com.onegene.pdf;

import com.onegene.pdf.entity.Sample;
import org.junit.Test;

import java.sql.Date;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/6/1 8:27
 **/
public class Java8 {

    @Test
    public void reduceTest() {
        List<Sample> samples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Sample sample = new Sample();
            sample.setBarCode("bar-" + i);
        }
        samples.stream().reduce((s1, s2) -> {
            String s = s1.getBarCode() + s2.getBarCode();
            s2.setBarCode(s);
            return s2;
        });
//        ZoneId of = ZoneId.of("Asia/Shanghai");
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(Date.from(clock.instant()));
    }
}
