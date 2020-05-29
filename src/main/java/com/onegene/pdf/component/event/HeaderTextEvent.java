package com.onegene.pdf.component.event;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.onegene.pdf.component.Painting;

/**
 * @author laoliangliang
 * @since 2020-05-24 13:53
 */
public class HeaderTextEvent implements IEventHandler {

    private String text;

    public HeaderTextEvent(String text) {
        this.text = text;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        Painting painting = new Painting(pdfDoc);
        painting.drawHeader();
        painting.drawHeaderText(text);
        painting.close();
    }
}
