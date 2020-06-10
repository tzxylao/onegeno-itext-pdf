package com.onegene.pdf.component;

import com.onegene.pdf.component.entity.PrintReportBean;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 11:20
 **/
public interface IReportBuilder {

    /**
     * 通过代理调用
     */
    void invokePartProxy(PrintReportBean data);

    /**
     * 添加首页图片
     */
    IReportBuilder addIndex();

    /**
     * say hello
     */
    IReportBuilder addHello();

    /**
     * 添加受检人信息
     */
    IReportBuilder addExaminee();

    /**
     * 添加检测内容
     */
    IReportBuilder addDetectionContent();

    /**
     * 检测结果概况
     */
    IReportBuilder addResultSummary();

    /**
     * 添加正文
     */
    IReportBuilder addContext();

    /**
     * 结束语
     */
    IReportBuilder addThanks();

    /**
     * 封底
     */
    IReportBuilder addBackCover();

    /**
     * 目录
     */
    IReportBuilder addCatalog();

    /**
     * 页码
     */
    IReportBuilder addPageNumber();
}
