package com.lll.learn.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/21 17:45
 **/
public class GenoColor {
    public static Color getThemeColor() {
        return new DeviceRgb(37, 98, 206);
    }

    public static Color getLightBlueColor() {
        return new DeviceRgb(102, 170, 255);
    }

    public static Color getOrangeColor() {
        return new DeviceRgb(247, 181, 45);
    }

    public static Color getRedColor() {
        return new DeviceRgb(241, 106, 111);
    }
}
