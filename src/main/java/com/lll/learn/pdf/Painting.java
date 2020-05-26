package com.lll.learn.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.lll.learn.data.ReportBean;
import lombok.Data;

import java.io.IOException;
import java.net.URL;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/20 8:34
 **/
@Data
public class Painting {
    private PdfCanvas pdfCanvas;
    private PageSize pageSize;
    private PdfDocument pdf;
    private PdfFont font;

    public Painting(PdfDocument pdf) {
        this.pdf = pdf;
        PdfPage page = pdf.getPage(pdf.getNumberOfPages());
        pageSize = pdf.getDefaultPageSize();
        pdfCanvas = new PdfCanvas(page);
        try {
            font = PdfFontFactory.createFont(ReportBuilder.class.getClassLoader().getResource("font/SourceHanSansCN-Regular.ttf").getPath(), PdfEncodings.IDENTITY_H, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawHeader() {
        pdfCanvas.setStrokeColor(GenoColor.getThemeColor());
        pdfCanvas.setLineWidth(0.7f);
        // 画头线（在开头画会使整个页面画不出来）
        float y = pageSize.getTop() - 40;
        pdfCanvas.moveTo(60, y)
                .lineTo(pageSize.getWidth() / 2 - 60, y).stroke();
        pdfCanvas.moveTo(pageSize.getWidth() / 2 + 60, y)
                .lineTo(pageSize.getWidth() - 60, y).stroke();
    }

    public void drawSegment(float score) {
        Color color = null;
        pdfCanvas.saveState();
        pdfCanvas.setStrokeColor(ColorConstants.BLACK);
        pdfCanvas.setLineWidth(1);
        String[] segmenets = Constant.SEGMENETS;
        if (score >= Float.parseFloat(segmenets[0]) && score < Float.parseFloat(segmenets[2])) {
            color = GenoColor.getLightBlueColor();
        } else if (score >= Float.parseFloat(segmenets[2]) && score < Float.parseFloat(segmenets[3])) {
            color = GenoColor.getThemeColor();
        } else if (score >= Float.parseFloat(segmenets[3]) && score < Float.parseFloat(segmenets[4])) {
            color = GenoColor.getOrangeColor();
        } else if (score >= Float.parseFloat(segmenets[4]) && score <= Float.parseFloat(segmenets[5])) {
            color = GenoColor.getRedColor();
        }
        // 位置标示偏移量
        float posXOffset = -100;
        float yOffset = pageSize.getTop() + 20;
        // 画线段
        for (int i = 0; i < 6; i++) {
            pdfCanvas.saveState().moveTo(pageSize.getWidth() / 2 - 100 + i * 40, yOffset - 203)
                    .lineTo(pageSize.getWidth() / 2 - 100 + i * 40, yOffset - 208)
                    .stroke().restoreState();
            try {
                int left = 103;
                if (i > 0) {
                    left = 107;
                }
                pdfCanvas.beginText()
                        .setFontAndSize(PdfFontFactory.createFont(StandardFonts.SYMBOL), 12)
                        .moveText(pageSize.getWidth() / 2 - left + i * 40, yOffset - 218);
                pdfCanvas.newlineShowText(segmenets[i]);
                pdfCanvas.endText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i < segmenets.length - 1) {
                float v1 = Float.parseFloat(segmenets[i]);
                float v2 = Float.parseFloat(segmenets[i + 1]);
                if (score >= v1 && score < v2) {
                    posXOffset = posXOffset + 40 / (v2 - v1) * (score - v1) + 40 * i;
                }
            }
        }
        // 画三角形
        pdfCanvas.setFillColor(color);
        pdfCanvas.moveTo(pageSize.getWidth() / 2 + posXOffset, yOffset - 193)
                .lineTo(pageSize.getWidth() / 2 - 3.4 + posXOffset, yOffset - 187)
                .lineTo(pageSize.getWidth() / 2 + 3.4 + posXOffset, yOffset - 187)
                .fill();
        // 画圆
        pdfCanvas.setLineWidth(2);
        pdfCanvas.setStrokeColor(color);
        pdfCanvas.roundRectangle(pageSize.getWidth() / 2 - 3 + posXOffset, yOffset - 188, 6, 6, 3)
                .stroke();
        // 画进度条
        pdfCanvas
                .roundRectangle(pageSize.getWidth() / 2 - 100, yOffset - 200, 200, 5, 3)
                .stroke();
        pdfCanvas
                .roundRectangle(pageSize.getWidth() / 2 - 100, yOffset - 200, posXOffset + 100, 5, 3)
                .fill()
                .restoreState();
    }

    public void close() {
        pdfCanvas.release();
    }

    public void drawHeaderText(String text) {
        float yOffset = pageSize.getTop();
        pdfCanvas.beginText()
                .setFontAndSize(font, 12)
                .moveText(pageSize.getWidth() / 2 - text.length() * 12 / 2, yOffset - 45);
        pdfCanvas.showText(text);
        pdfCanvas.endText();
    }

    public void drawHello(String imagePath) {
        URL resource = Painting.class.getClassLoader().getResource(imagePath);
        Image backImage = new Image(ImageDataFactory.create(resource));
        PdfPage page = pdf.addNewPage();
        Rectangle pageSize = page.getPageSize();
        int leftDist = 0;
        Rectangle rectangle = new Rectangle(
                pageSize.getX() + leftDist,
                pageSize.getTop(),
                pageSize.getWidth() - leftDist * 2,
                40);

        Canvas canvas = new Canvas(page, rectangle);
        canvas.add(backImage).flush();
        canvas.close();
    }

    public void drawWss(int level, ReportBean.ItemsBean itemsBean) {
        float yOffset = pageSize.getTop() + 20;
        // 画线条
        pdfCanvas.saveState();
        pdfCanvas.setStrokeColor(GenoColor.getThemeColor());
        pdfCanvas.setFillColor(GenoColor.getThemeColor());
        pdfCanvas.roundRectangle(pageSize.getWidth() / 2 - 108, yOffset - 200, 5, 6, 3);
        pdfCanvas.rectangle(pageSize.getWidth() / 2 - 105, yOffset - 200, 70, 6).fill().stroke();
        pdfCanvas.setStrokeColor(GenoColor.getOrangeColor());
        pdfCanvas.setFillColor(GenoColor.getOrangeColor());
        pdfCanvas.rectangle(pageSize.getWidth() / 2 - 105 + 70, yOffset - 200, 70, 6).fill().stroke();
        pdfCanvas.setStrokeColor(GenoColor.getRedColor());
        pdfCanvas.setFillColor(GenoColor.getRedColor());
        pdfCanvas.roundRectangle(pageSize.getWidth() / 2 - 105 + 208, yOffset - 200, 5, 6, 3);
        pdfCanvas.rectangle(pageSize.getWidth() / 2 - 105 + 140, yOffset - 200, 70, 6).fill().stroke();

        Color color = null;
        float posXOffset = 0;
        switch (level) {
            case 2:
                color = GenoColor.getThemeColor();
                posXOffset = -70;
                break;
            case 3:
                color = GenoColor.getOrangeColor();
                posXOffset = 0;
                break;
            case 4:
                color = GenoColor.getRedColor();
                posXOffset = 70;
                break;
            default:
                break;
        }
        // 画三角形
        pdfCanvas.setFillColor(color);
        pdfCanvas.moveTo(pageSize.getWidth() / 2 + posXOffset, yOffset - 193)
                .lineTo(pageSize.getWidth() / 2 - 3.4 + posXOffset, yOffset - 187)
                .lineTo(pageSize.getWidth() / 2 + 3.4 + posXOffset, yOffset - 187)
                .fill();
        // 画圆
        pdfCanvas.setLineWidth(2);
        pdfCanvas.setStrokeColor(color);
        pdfCanvas.roundRectangle(pageSize.getWidth() / 2 - 3 + posXOffset, yOffset - 188, 6, 6, 3)
                .stroke();

        float textHeightOff = -215;
        pdfCanvas.beginText().setFontAndSize(font, 11);
        if (level == 2) {
            pdfCanvas.setFillColor(GenoColor.getThemeColor());
        } else {
            pdfCanvas.setFillColor(new DeviceRgb(159,159,159));
        }
        pdfCanvas.moveText(pageSize.getWidth() / 2 - 70 - 12, yOffset + textHeightOff);
        pdfCanvas.showText("正常").stroke();
        pdfCanvas.endText();

        pdfCanvas.beginText().setFontAndSize(font, 11);
        if (level == 3) {
            pdfCanvas.setFillColor(GenoColor.getOrangeColor());
        } else {
            pdfCanvas.setFillColor(new DeviceRgb(159,159,159));
        }
        pdfCanvas.moveText(pageSize.getWidth() / 2 + 0 - 12, yOffset + textHeightOff);
        pdfCanvas.showText("稍高");
        pdfCanvas.endText();

        pdfCanvas.beginText().setFontAndSize(font, 11);
        if (level == 4) {
            pdfCanvas.setFillColor(GenoColor.getRedColor());
        } else {
            pdfCanvas.setFillColor(new DeviceRgb(159,159,159));
        }
        pdfCanvas.moveText(pageSize.getWidth() / 2 + 70 - 6, yOffset + textHeightOff);
        pdfCanvas.showText("高");
        pdfCanvas.endText().stroke();
        switch (level) {
            case 2:
                pdfCanvas.beginText().setFontAndSize(font, 11);
                pdfCanvas.setFillColor(ColorConstants.BLACK);
                pdfCanvas.moveText(pageSize.getWidth() / 2 - 48, yOffset - 5);
                pdfCanvas.showText(itemsBean.getName() + "需求量").stroke();
                pdfCanvas.setFillColor(GenoColor.getThemeColor());
                pdfCanvas.showText("正常");
                pdfCanvas.endText();
                break;
            case 3:
                pdfCanvas.beginText().setFontAndSize(font, 11);
                pdfCanvas.setFillColor(ColorConstants.BLACK);
                pdfCanvas.moveText(pageSize.getWidth() / 2 - 48, yOffset - 235);
                pdfCanvas.showText(itemsBean.getName() + "需求量").stroke();
                pdfCanvas.setFillColor(GenoColor.getOrangeColor());
                pdfCanvas.showText("稍高");
                pdfCanvas.endText();
                break;
            case 4:
                pdfCanvas.beginText().setFontAndSize(font, 11);
                pdfCanvas.moveText(pageSize.getWidth() / 2 - 48, yOffset - 235);
                pdfCanvas.setFillColor(ColorConstants.BLACK);
                pdfCanvas.showText(itemsBean.getName() + "需求量").stroke();
                pdfCanvas.setFillColor(GenoColor.getRedColor());
                pdfCanvas.showText("高");
                pdfCanvas.endText();
                break;
            default:
                break;
        }
        pdfCanvas.restoreState();
    }
}