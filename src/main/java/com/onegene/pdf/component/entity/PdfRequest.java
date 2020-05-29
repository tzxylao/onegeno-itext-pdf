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
}
