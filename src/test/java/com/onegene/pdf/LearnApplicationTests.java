package com.onegene.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.onegene.pdf.component.AbstractReportBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;

class LearnApplicationTests {

    @Test
    void contextLoads() throws IOException {
        PdfFont font = PdfFontFactory.createFont(AbstractReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);

        String outPath = "/Users/laoliangliang/Desktop/report2.pdf";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        PdfReader pdfReader = new PdfReader(new File("/Users/laoliangliang/Desktop/report.pdf"));
        PdfWriter writer = new PdfWriter(new File(outPath));
        PdfDocument pdf = new PdfDocument(pdfReader, writer);
        pdf.setDefaultPageSize(PageSize.A4);
        PageSize pageSize = pdf.getDefaultPageSize();


        Document document = new Document(pdf, pageSize);

        document.setFont(font);
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 0; i < numberOfPages; i++) {
            document.showTextAligned(new Paragraph("内容xxx"), pageSize.getWidth() / 2, pageSize.getTop()-30, i + 1, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0);
            PdfPage page = pdf.getPage(i+1);
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            pdfCanvas.saveState()
                .moveTo(pageSize.getWidth()/2-200,pageSize.getTop()-30)
            .lineTo(pageSize.getWidth()/2-100,pageSize.getTop()-30)
            .stroke()
            .restoreState();
        }
        pdf.close();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }


    @Test
    public void test2() throws IOException {
        PdfFont font = PdfFontFactory.createFont(AbstractReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);

        String outPath = "/Users/laoliangliang/Desktop/report3.pdf";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        PdfWriter writer = new PdfWriter(new File(outPath));
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        PageSize pageSize = pdf.getDefaultPageSize();
        pdf.addNewPage();


        Document document = new Document(pdf, pageSize);

        document.setFont(font);
        PdfCanvas pdfCanvas = new PdfCanvas(pdf.getPage(pdf.getNumberOfPages()));
        pdfCanvas.saveState()
                .moveTo(pageSize.getWidth()/2-200,pageSize.getTop()-30)
                .lineTo(pageSize.getWidth()/2-100,pageSize.getTop()-30)
                .stroke()
                .restoreState();

        document.add(new Paragraph("hello 你好"));

        pdf.close();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

}
