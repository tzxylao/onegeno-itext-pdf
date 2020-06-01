package com.onegene.pdf.component.event;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.onegene.pdf.component.Constant;

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
        pageSize++;
        properties.setProperty(Constant.CATALOG_SIZE, pageSize + "");
    }
}
