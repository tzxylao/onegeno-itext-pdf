package com.onegene.pdf.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.system.HostInfo;
import cn.hutool.system.SystemUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.onegene.pdf.component.GenoReportBuilder;
import com.onegene.pdf.component.entity.PdfRequest;
import com.onegene.pdf.component.entity.PrintReportBean;
import com.onegene.pdf.component.entity.Result;
import com.onegene.pdf.entity.Sample;
import com.onegene.pdf.entity.SampleResult;
import com.onegene.pdf.mapper.SampleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 8:31
 **/
@RestController
@RequestMapping("/pdf")
@Slf4j
public class DownloadPdfController {

    @Autowired
    private SampleMapper sampleMapper;

    @Value("${onegene.font.path}")
    private String fontPath;

    @Value("${onegene.biology.print.url:http://infoapi.1genehealth.com/biology/bio/report/print}")
    private String url;

    @Value("${onegene.pdf.prefix:/Users/laoliangliang/Desktop/}")
    private String prefixPath;

    @PostConstruct
    public void init(){
        HostInfo hostInfo = SystemUtil.getHostInfo();
        String name = hostInfo.getName();
        if ("laoliangliangdeMacBook-Pro.local".equals(name)) {
            fontPath = "/Users/laoliangliang" + fontPath;
            prefixPath = "/Users/laoliangliang" + prefixPath;
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downLoad(@RequestParam("access_token") String token, @RequestParam("uuid") String uuid, @RequestParam(value = "part", defaultValue = "0") Integer part, HttpServletResponse response) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        PrintReportBean data = getPrintReportBean(token, uuid);
        if (data == null) {
            return;
        }
        File prefixPathFile = new File(prefixPath);
        if (!prefixPathFile.exists()) {
            prefixPathFile.mkdirs();
        }
        // 构建PDF
        String outPath = prefixPath + uuid + ".pdf";
        GenoReportBuilder builder = new GenoReportBuilder();
        builder.setFontPath(fontPath);
        builder.initPdf(outPath, part);

        if (part == 0) {
            builder.buildAll(data);
        } else {
            builder.invokePartProxy(data);
        }

        // 输出
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + uuid + ".component");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + uuid + ".pdf");
        FileUtil.writeToStream(new File(outPath), response.getOutputStream());

        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }

    private PrintReportBean getPrintReportBean(@RequestParam("access_token") String token, @RequestParam("uuid") String uuid) {
        Map<String, Object> params = new HashMap<>();
        String body = HttpUtil.post(url + "?access_token=" + token + "&uuid=" + uuid, params);
        Result<PrintReportBean> reportBeanResult = JSON.parseObject(body, new TypeReference<Result<PrintReportBean>>() {
        });
        if (reportBeanResult == null) {
            return null;
        }
        PrintReportBean data = reportBeanResult.getData();
        if (data == null) {
            return null;
        }
        return data;
    }

    @RequestMapping(value = "/disk", method = RequestMethod.POST)
    @Async
    public void saveToDisk(@RequestBody PdfRequest pdfRequest) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CountDownLatch countDownLatch = new CountDownLatch(pdfRequest.getUuids().size());
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "10");
        pdfRequest.getUuids().parallelStream().forEach(uuid -> {
            try {
                PrintReportBean data = getPrintReportBean(pdfRequest.getToken(), uuid);
                if (data == null) {
                    log.error("uuid:{} pdf数据不存在", uuid);
                    return;
                }
                StopWatch watch = new StopWatch();
                watch.start();

                Sample sampleQuery = new Sample();
                sampleQuery.setUuid(uuid);
                SampleResult sampleResult = sampleMapper.selectSampleResult(sampleQuery);
                if (!sampleResult.vaild()) {
                    log.error("uuid:{},参数存在问题：{}", uuid, JSON.toJSONString(sampleResult));
                    return;
                }

                GenoReportBuilder builder = new GenoReportBuilder();
                builder.setFontPath(fontPath);
                builder.initPdf(prefixPath + DateUtil.format(new Date(), "yyyyMMdd") + "/" + sampleResult.toString() + ".pdf");
                builder.buildAll(data);
                watch.stop();
                log.info(uuid + ",耗时：" + watch.getTotalTimeMillis() + "ms");
            } finally {
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        log.info("总耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }

}
