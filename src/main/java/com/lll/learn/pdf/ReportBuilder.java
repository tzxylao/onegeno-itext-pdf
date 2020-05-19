package com.lll.learn.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 17:40
 **/
public class ReportBuilder {

    private PdfWriter writer;
    private PdfDocument pdf;
    private Document doc;

    public void initPdf(String filePath) throws IOException {
        writer = new PdfWriter(new File(filePath));
        pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        pdf.getDefaultPageSize().applyMargins(20, 20, 20, 20, true);

        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
//        PdfFont f3 = PdfFontFactory.createFont("C:/Windows/Fonts/simhei.ttf", PdfEncodings.IDENTITY_H,true);
        doc = new Document(pdf);
        doc.setFont(font);
        doc.setFontSize(12f);
    }

    /**
     * 添加首页图片
     */
    public ReportBuilder addIndex() throws MalformedURLException {
        Image indexImage = new Image(ImageDataFactory.create(ReportBuilder.class.getClassLoader().getResource("image/成人纸质报告-54.png")));
        Image introductionImage = new Image(ImageDataFactory.create(ReportBuilder.class.getClassLoader().getResource("image/gene.png")));
        Image honorImage = new Image(ImageDataFactory.create(ReportBuilder.class.getClassLoader().getResource("image/honor.png")));

        doc.add(indexImage);
        doc.add(introductionImage);
        doc.add(honorImage);
        return this;
    }

    public ReportBuilder addHello() throws MalformedURLException {
        Div div = new Div();
        div.setWidth(UnitValue.createPercentValue(70));
        div.setHeight(UnitValue.createPercentValue(100));
        div.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph p = new Paragraph();
        p.setMarginTop(150f);
        Style large = new Style();
        large.setFontSize(20);
        large.setFontColor(new DeviceRgb(37, 98, 206));
        p.add(new Text("尊敬的 ").addStyle(large));
        p.add(new Text("郑成功").addStyle(large).setBorderBottom(new SolidBorder(0.5f)));
        p.add(new Text(" 女士：\n").addStyle(large));
        p.add(new Text("您好！\n"));
        p.add(new Text("衷心感谢您对我们的信任，选择壹基因疾病遗传风险基因检测服务!\n"));
        div.add(p);

        URL resource = ReportBuilder.class.getClassLoader().getResource("image/纸质报告-03.png");
        Image backImage = new Image(ImageDataFactory.create(resource));
        PdfPage page = pdf.addNewPage();
        Rectangle pageSize = page.getPageSize();
        Rectangle rectangle = new Rectangle(
                pageSize.getX() + 36,
                pageSize.getTop(),
                pageSize.getWidth() - 72,
                50);

        Canvas canvas = new Canvas(page, rectangle);
        canvas.add(backImage).flush();
        canvas.close();

        doc.add(div);
        return this;
    }

    public void build() {
        doc.close();
    }
}
