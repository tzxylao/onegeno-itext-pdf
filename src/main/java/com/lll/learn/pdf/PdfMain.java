package com.lll.learn.pdf;

import org.springframework.util.StopWatch;

import java.io.IOException;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 13:35
 **/
public class PdfMain {
    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ReportBuilder reportBuilder = new GenoReportBuilder();
        reportBuilder.initPdf("/Users/laoliangliang/Desktop/report.pdf");

        reportBuilder
                .addIndex()
                .addHello()
                .addExaminee()
                .addDetectionContent()
                .addDirectory()
                .addResultSummary()
                .addContext()
        ;

        reportBuilder.build();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

}
