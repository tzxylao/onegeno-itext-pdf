package com.lll.learn.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 17:40
 **/
public abstract class ReportAgainBuilder {

    protected PdfWriter writer;
    protected PdfReader reader;
    protected PdfDocument pdf;
    protected Document doc;
    protected PdfFont font;
    protected String outPath;


    public void initPdf(String inPath, String outPath) throws IOException {
        this.outPath = outPath;
        writer = new PdfWriter(new File(outPath));
        reader = new PdfReader(new File(inPath));
        pdf = new PdfDocument(reader, writer);
        pdf.setDefaultPageSize(PageSize.A4);
        pdf.getDefaultPageSize().applyMargins(0, 0, 0, 0, true);

//        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        font = PdfFontFactory.createFont(ReportAgainBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);
        doc = new Document(pdf);
        doc.setMargins(50, 60, 60, 60);
        doc.setFont(font);
        doc.setFontSize(10f);
    }

}
