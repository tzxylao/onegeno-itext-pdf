package com.lll.learn.pdf;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import lombok.Data;

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

    public Painting(PdfDocument pdf) {
        this.pdf = pdf;
        PdfPage page = pdf.getPage(pdf.getNumberOfPages());
        pageSize = pdf.getDefaultPageSize();
        pdfCanvas = new PdfCanvas(page);
        pdfCanvas.concatMatrix(1, 0, 0, 1, 0, pageSize.getHeight());
    }

    public void drawHeader() {
        pdfCanvas.setStrokeColor(GenoColor.getThemeColor());
        pdfCanvas.setLineWidth(0.7f);
        // 画头线（在开头画会使整个页面画不出来）
        int y = -55;
        pdfCanvas.moveTo(60, y)
                .lineTo(pageSize.getWidth() / 2 - 60, y).stroke();
        pdfCanvas.moveTo(pageSize.getWidth() / 2 + 60, y)
                .lineTo(pageSize.getWidth() - 60, y).stroke();
    }

    public void drawSocre() {
        pdfCanvas.setStrokeColor(GenoColor.getRedColor());
        pdfCanvas.
                saveState().moveTo(pageSize.getWidth() / 2 - 50, -200)
                .rectangle(pageSize.getWidth() / 2 - 50, -200, 100, 5)
                .stroke().restoreState();
    }

    public void close() {
        pdfCanvas.release();
    }

}