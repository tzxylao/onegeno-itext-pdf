package com.lll.learn.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.lll.learn.pdf.entity.PrintReportBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 17:40
 **/
public abstract class ReportBuilder implements IReportBuilder{

    protected PdfWriter writer;
    protected PdfDocument pdf;
    protected Document doc;
    protected PdfFont font;
    protected String outPath;
    @Getter
    @Setter
    protected Integer part = 0;
    protected PrintReportBean reportBean;
    protected ConverterProperties proper;
    protected Map<GenoReportBuilder.ExtraParam.CatalogType, java.util.List<GenoReportBuilder.CataLog>> cataLogsMap = new LinkedHashMap<>();
    protected Properties properties = new Properties();

    public void setPrintReportBean(PrintReportBean printReportBean) {
        this.reportBean = printReportBean;
    }

    public void initPdf(String outPath) throws IOException {
        this.outPath = outPath;
        String inPath = outPath;
        if (part <= 0) {
            inPath = getInPath();
        }
        writer = new PdfWriter(new File(inPath));
        pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        pdf.getDefaultPageSize().applyMargins(0, 0, 0, 0, true);

//        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        font = PdfFontFactory.createFont(ReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);
        doc = new Document(pdf);
        doc.setMargins(50, 60, 50, 60);
        doc.setFont(font);
        doc.setFontSize(10.5f);
        doc.setCharacterSpacing(0.1f);

        proper = new ConverterProperties();
        //字体设置，解决中文不显示问题
        FontSet fontSet = new FontSet();
        fontSet.addFont(GenoReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H);
        FontProvider fontProvider = new FontProvider(fontSet);
        proper.setFontProvider(fontProvider);
    }

    protected String getInPath() {
        int index = outPath.lastIndexOf("/");
        String fileName = outPath.substring(index);
        String prefix = outPath.substring(0, index);
        String[] split = fileName.split("\\.");
        String name = split[0];
        return prefix + name + "_temp.pdf";
    }

    @Data
    @AllArgsConstructor
    protected class CataLog {
        private Integer index;
        private String categoryName;
        private String name;
        private String label;
        private Integer page;
        private GenoReportBuilder.ExtraParam extraParam;
    }

    /**
     * 一键构建
     *
     * @return
     */
    public ReportBuilder buildAll(PrintReportBean data) {
        this.reportBean = data;
        addIndex();
        addHello();
        addExaminee();
        addDetectionContent();
        addResultSummary();
        addContext();
        addThanks();
        addBackCover();
        addCatalog();
        addPageNumber();
        build();
        return this;
    }

    /**
     * 添加首页图片
     */
    @Override
    public abstract ReportBuilder addIndex();

    /**
     * say hello
     */
    @Override
    public abstract ReportBuilder addHello();

    /**
     * 添加受检人信息
     */
    @Override
    public abstract ReportBuilder addExaminee();

    public void build() {
        try {
            doc.close();
            pdf.close();
            pdf.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加检测内容
     */
    @Override
    public abstract GenoReportBuilder addDetectionContent();

    /**
     * 检测结果概况
     */
    @Override
    public abstract GenoReportBuilder addResultSummary();

    /**
     * 添加正文
     */
    @Override
    public abstract GenoReportBuilder addContext();

    /**
     * 结束语
     */
    @Override
    public abstract GenoReportBuilder addThanks();

    /**
     * 封底
     */
    @Override
    public abstract GenoReportBuilder addBackCover();

    /**
     * 目录
     */
    @Override
    public abstract GenoReportBuilder addCatalog();

    /**
     * 页码
     */
    @Override
    public abstract GenoReportBuilder addPageNumber();

}
