package com.onegene.pdf.component.report.drug;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/21 17:45
 **/
public class DrugColor {
    public static Color getThemeColor() {
        return new DeviceRgb(37, 98, 206);
    }

    public static Color getLightBlueColor() {
        return new DeviceRgb(102, 170, 255);
    }

    public static Color getGreenColor() {
        return new DeviceRgb(29, 190, 190);
    }

    public static Color getOrangeColor() {
        return new DeviceRgb(255, 129, 41);
    }
    public static Color getYellowColor() {
        return new DeviceRgb(250,203,77);
    }
    public static Color getRedColor() {
        return new DeviceRgb(241, 106, 111);
    }
}
