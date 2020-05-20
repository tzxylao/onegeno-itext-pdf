package com.lll.learn.pdf;

import java.io.IOException;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 13:35
 **/
public class PdfLearn {
    public static void main(String[] args) throws IOException {
        ReportBuilder reportBuilder = new GenoReportBuilder();
        reportBuilder.initPdf("E:\\Users\\fgm\\Desktop\\pdf\\report.pdf");

        reportBuilder
                .addIndex()
                .addHello()
                .addExaminee()
                .addDetectionContent()
                .addDirectory()
        ;

        reportBuilder.build();
    }

}
