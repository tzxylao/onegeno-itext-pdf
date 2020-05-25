package com.lll.learn.pdf;

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
import com.lll.learn.data.PrintReportBean;

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
    protected PdfFont font;
    protected String outPath;
    protected PrintReportBean reportBean;

    public void setPrintReportBean(PrintReportBean printReportBean) {
        this.reportBean = printReportBean;
    }

    public void initPdf(String outPath) throws IOException {
        this.outPath = outPath;
        writer = new PdfWriter(new File(outPath));
        pdf = new PdfDocument(writer);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new EndPageEventHandler(this));
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, new StartPageEventHandler(this));
        pdf.setDefaultPageSize(PageSize.A4);
        pdf.getDefaultPageSize().applyMargins(0, 0, 0, 0, true);

//        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        font = PdfFontFactory.createFont(ReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);
        doc = new Document(pdf);
        doc.setMargins(50, 60, 60, 60);
        doc.setFont(font);
        doc.setFontSize(10.5f);
    }


    protected class EndPageEventHandler implements IEventHandler {

        private ReportBuilder builder;

        public EndPageEventHandler(ReportBuilder reportBuilder) {
            this.builder = reportBuilder;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();

            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            //Add header and footer
            pdfCanvas.beginText()
                    .setFontAndSize(font, 9)
                    .moveText(pageSize.getWidth() / 2, 30)
                    .showText(String.format("- %d -", pageNumber))
                    .endText();

            pdfCanvas.release();

//            if (pageNumber >= 7 && pageNumber != 9) {
//                String text = headerLineTextMap.get(pageNumber);
//                if (text != null) {
//                    // 头条，进度条
//                    Painting painting = new Painting(pdf, builder);
//                    painting.drawHeader();
//                    painting.close();
//                }
//            }
        }
    }

    protected class StartPageEventHandler implements IEventHandler {

        private ReportBuilder builder;

        public StartPageEventHandler(ReportBuilder reportBuilder) {
            this.builder = reportBuilder;
        }

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
    public abstract GenoReportBuilder addDirectory();

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
}
