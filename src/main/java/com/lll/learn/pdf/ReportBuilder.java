package com.lll.learn.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 17:40
 **/
public abstract class ReportBuilder {

    protected PdfWriter writer;
    protected PdfDocument pdf;
    protected Document doc;

    protected void initPdf(String filePath) throws IOException {
        writer = new PdfWriter(new File(filePath));
        pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        pdf.getDefaultPageSize().applyMargins(0, 0, 0, 0, true);

//        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        PdfFont font = PdfFontFactory.createFont(ReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H,true);
        doc = new Document(pdf);
        doc.setMargins(40, 60, 40, 60);
        doc.setFont(font);
        doc.setFontSize(10f);
    }

    /**
     * 添加首页图片
     */
    abstract ReportBuilder addIndex();

    /**
     * say hello
     */
    abstract ReportBuilder addHello();

    /**
     * 添加受检人信息
     */
    abstract ReportBuilder addExaminee();

    public void build() {
        try {
            doc.close();
            pdf.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加检测内容
     */
    public abstract GenoReportBuilder addDetectionContent();

    /**
     * 添加目录
     */
    public abstract void addDirectory();
}
