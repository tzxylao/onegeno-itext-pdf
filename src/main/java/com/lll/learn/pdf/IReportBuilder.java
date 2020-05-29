package com.lll.learn.pdf;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 11:20
 **/
public interface IReportBuilder {
    /**
     * 添加首页图片
     */
    ReportBuilder addIndex();

    /**
     * say hello
     */
    ReportBuilder addHello();

    /**
     * 添加受检人信息
     */
    ReportBuilder addExaminee();

    /**
     * 添加检测内容
     */
    GenoReportBuilder addDetectionContent();

    /**
     * 检测结果概况
     */
    GenoReportBuilder addResultSummary();

    /**
     * 添加正文
     */
    GenoReportBuilder addContext();

    /**
     * 结束语
     */
    GenoReportBuilder addThanks();

    /**
     * 封底
     */
    GenoReportBuilder addBackCover();

    /**
     * 目录
     */
    GenoReportBuilder addCatalog();

    /**
     * 页码
     */
    GenoReportBuilder addPageNumber();
}
