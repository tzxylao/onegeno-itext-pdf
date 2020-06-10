package com.onegene.pdf.component.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 14:23
 **/
@Data
public class PdfRequest {
    private List<String> uuids;
    private String token;
    /**
     * 为null或者1时，当判断样本已生成时跳过
     */
    private Integer skip;
    /**
     * 0-易感 1-用药
     */
    private Integer type;
    private Integer noUpdate;
}
