package com.lll.learn.pdf.event;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;

import java.util.Properties;

/**
 * @author laoliangliang
 * @since 2020-05-24 13:53
 */
public class CatalogMoveEvent implements IEventHandler {

    private int startPage = 7;
    private int pageSize = 0;
    private Properties properties;

    public CatalogMoveEvent(Properties properties) {
        this.properties = properties;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdfDoc.getPageNumber(page);
//        pdfDoc.movePage(pageNumber, startPage++);
        pageSize++;
        properties.setProperty("catalogSize", pageSize + "");
    }
}
