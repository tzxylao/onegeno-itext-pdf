package com.lll.learn.pdf;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/20 8:34
 **/
public class Component {
    public static Paragraph getCatelogDottedLine(int type) {
        Paragraph element;
        if (type == 1) {
            element = new Paragraph("-----------------------------------------------------------------------------------------------");
        } else {
            element = new Paragraph("------------------------------------------------------------------------");
        }
        element.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.HIDDEN);
        return element;
    }

    public static Cell getDefaultCell() {
        return new Cell().addStyle(GenoStyle.getDefaultCell());
    }

    public static Cell getDefaultCell(int rowspan, int colspan) {
        return new Cell(rowspan, colspan).addStyle(GenoStyle.getDefaultCell());
    }

    public static Cell getCatelogCell() {
        return new Cell().addStyle(GenoStyle.getCatelogCell());
    }

    public static Cell getCatelogCell(int colsapn) {
        return new Cell(1, colsapn).addStyle(GenoStyle.getCatelogCell());
    }

    public static Cell getSignCell() {
        return new Cell().addStyle(GenoStyle.getSignCell());
    }

    public static Paragraph getSignParagraph(String text) {
        return new Paragraph(text).addStyle(GenoStyle.getSignStyle());
    }
}