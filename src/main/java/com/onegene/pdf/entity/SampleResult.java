package com.onegene.pdf.entity;

import lombok.Data;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 16:13
 **/
@Data
public class SampleResult {
    private Long id;
    private String channelName;
    private String barCode;
    /**
     * 用户姓名
     */
    private String name;

    public boolean vaild() {
        if (channelName == null || barCode == null || name == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return channelName + "-" + name + "-" + barCode;
    }
}
