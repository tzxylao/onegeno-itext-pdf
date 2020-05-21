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

    protected void initPdf(String filePath) throws IOException {
        writer = new PdfWriter(new File(filePath));
        pdf = new PdfDocument(writer);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());
        pdf.setDefaultPageSize(PageSize.A4);
        pdf.getDefaultPageSize().applyMargins(0, 0, 0, 0, true);

//        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        font = PdfFontFactory.createFont(ReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);
        doc = new Document(pdf);
        doc.setMargins(40, 60, 40, 60);
        doc.setFont(font);
        doc.setFontSize(10f);
    }

    /**
     * 添加正文
     */
    public abstract void addContext();

    protected class MyEventHandler implements IEventHandler {

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
        }
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
    public abstract GenoReportBuilder addDirectory();

    /**
     * 检测结果概况
     */
    public abstract GenoReportBuilder addResultSummary();
}
