package com.onegene.pdf.mapper;

import com.onegene.pdf.entity.Sample;
import com.onegene.pdf.entity.SampleResult;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author: huzhiyong
 * @description:
 * @create: 2019-11-27 13:54
 **/
public interface SampleMapper extends Mapper<Sample> {
    SampleResult selectSampleResult(Sample sampleQuery);
}
