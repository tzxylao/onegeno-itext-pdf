package com.lll.learn.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.lll.learn.pdf.entity.PrintReportBean;
import lombok.AllArgsConstructor;
import lombok.Data;

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
public abstract class ReportBuilder {

    protected PdfWriter writer;
    protected PdfDocument pdf;
    protected Document doc;
    protected PdfFont font;
    protected String outPath;
    protected PrintReportBean reportBean;
    protected ConverterProperties proper;
    protected Map<GenoReportBuilder.ExtraParam.CatalogType, java.util.List<GenoReportBuilder.CataLog>> cataLogsMap = new LinkedHashMap<>();
    protected Properties properties = new Properties();

    public void setPrintReportBean(PrintReportBean printReportBean) {
        this.reportBean = printReportBean;
    }

    public void initPdf(String outPath) throws IOException {
        this.outPath = outPath;
        String inPath = getInPath();
        writer = new PdfWriter(new File(inPath));
        pdf = new PdfDocument(writer);
//        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new EndPageEventHandler());
//        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, new StartPageEventHandler());
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

    protected class EndPageEventHandler implements IEventHandler {

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            String forbidPage = properties.getProperty("forbidPage");

            if (pageNumber > 7 && pageNumber != 9) {
                if (forbidPage != null && pageNumber>=Integer.parseInt(forbidPage)) {
                    return;
                }
                PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

                //Add header and footer
                pdfCanvas.beginText()
                        .setFontAndSize(font, 9)
                        .moveText(pageSize.getWidth() / 2, 30)
                        .showText(String.format("- %d -", (pageNumber+1)))
                        .endText();

                pdfCanvas.release();
            }
        }
    }

    protected class StartPageEventHandler implements IEventHandler {

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();

//            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), pdfDoc);

//            if (pageNumber >= 7 && pageNumber != 9) {
//                String text = headerLineTextMap.get(pageNumber);
//                if (text != null) {
                    // 头条，进度条
//                    Painting painting = new Painting(pdf, pdfCanvas,builder);
//                    painting.drawHeader();
//                    painting.close();
//                }
//            }
        }
    }

    /**
     * 一键构建
     * @return
     */
    public abstract ReportBuilder buildAll(PrintReportBean data);
    /**
     * 添加首页图片
     */
    public abstract ReportBuilder addIndex();

    /**
     * say hello
     */
    public abstract ReportBuilder addHello();

    /**
     * 添加受检人信息
     */
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
    public abstract GenoReportBuilder addDetectionContent();

    /**
     * 检测结果概况
     */
    public abstract GenoReportBuilder addResultSummary();

    /**
     * 添加正文
     */
    public abstract GenoReportBuilder addContext();

    /**
     * 结束语
     */
    public abstract GenoReportBuilder addThanks();

    /**
     * 封底
     */
    public abstract GenoReportBuilder addBackCover();

    /**
     * 目录
     */
    public abstract GenoReportBuilder addCatalog();

    /**
     * 页码
     */
    public abstract GenoReportBuilder addPageNumber();

}
