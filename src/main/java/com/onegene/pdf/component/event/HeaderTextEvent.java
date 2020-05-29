package com.onegene.pdf.component.event;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.onegene.pdf.component.Painting;

/**
 * @author laoliangliang
 * @since 2020-05-24 13:53
 */
public class HeaderTextEvent implements IEventHandler {

    private String text;
    private PdfFont font;

    public HeaderTextEvent(String text,PdfFont font) {
        this.text = text;
        this.font = font;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        Painting painting = new Painting(pdfDoc, font);
        painting.drawHeader();
        painting.drawHeaderText(text);
        painting.close();
    }
}
