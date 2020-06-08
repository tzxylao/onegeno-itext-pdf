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
import com.onegene.pdf.entity.SampleExpand;
import com.onegene.pdf.entity.SampleResult;
import com.onegene.pdf.mapper.SampleExpandMapper;
import com.onegene.pdf.mapper.SampleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Autowired
    private SampleExpandMapper sampleExpandMapper;

    @Value("${onegene.font.path}")
    private String fontPath;

    @Value("${onegene.biology.print.url:http://infoapi.1genehealth.com/biology/bio/report/print}")
    private String url;

    @Value("${onegene.pdf.prefix:/Users/laoliangliang/Desktop/}")
    private String prefixPath;

    @PostConstruct
    public void init() {
        HostInfo hostInfo = SystemUtil.getHostInfo();
        String name = hostInfo.getName();
        if ("laoliangliangdeMacBook-Pro.local".equals(name)) {
            fontPath = "/Users/laoliangliang" + fontPath;
            prefixPath = "/Users/laoliangliang" + prefixPath;
        }

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "10");
        // 目标目录加个日期
        prefixPath = prefixPath + DateUtil.format(new Date(), "yyyyMMdd") + "/";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downLoad(@RequestParam("access_token") String token,
                         @RequestParam("uuid") String uuid,
                         @RequestParam(value = "part", defaultValue = "0") Integer part,
                         @RequestParam(value = "force", defaultValue = "0") Integer force,
                         HttpServletResponse response) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        PrintReportBean data = getPrintReportBean(token, uuid);
        if (data == null) {
            log.info("报告数据不存在：{}", uuid);
            return;
        }

        // 若没有目录则创建
        this.createDirectory();

        // 若生成则直接返回
        String outPath = prefixPath + uuid + ".pdf";
        if (Files.exists(Paths.get(outPath)) && (force == null || force != 1)) {
            writeOutputStream(uuid, response, outPath);
            return;
        }

        // 构建PDF
        GenoReportBuilder builder = new GenoReportBuilder();
        builder.setFontPath(fontPath);
        builder.initPdf(outPath, part);

        if (part == 0) {
            builder.buildAll(data);
        } else {
            builder.invokePartProxy(data);
        }

        writeOutputStream(uuid, response, outPath);


        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }

    private void writeOutputStream(String uuid, HttpServletResponse response, String outPath) throws IOException {
        // 输出
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + uuid + ".component");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + uuid + ".pdf");
        FileUtil.writeToStream(new File(outPath), response.getOutputStream());
    }

    private void createDirectory() {
        File prefixPathFile = new File(prefixPath);
        if (!prefixPathFile.exists()) {
            prefixPathFile.mkdirs();
        }
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
    public Result saveToDisk(@RequestBody PdfRequest pdfRequest) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int size = pdfRequest.getUuids().size();
        Integer noUpdate = pdfRequest.getNoUpdate();
        CountDownLatch countDownLatch = new CountDownLatch(size);
        pdfRequest.getUuids().parallelStream().forEach(uuid -> {
            StopWatch watch = new StopWatch();
            watch.start();
            try {
                SampleResult sampleResult = getSampleResult(uuid);
                if (!sampleResult.vaild()) {
                    log.error("uuid:{},参数存在问题：{}", uuid, JSON.toJSONString(sampleResult));
                    return;
                }

                //判断不需要再生成则下一条
                if (isOver(pdfRequest, sampleResult)) {
                    log.info("跳过uuid:{}", uuid);
                    return;
                }

                PrintReportBean data = getPrintReportBean(pdfRequest.getToken(), uuid);
                if (data == null) {
                    log.error("uuid:{} pdf数据不存在", uuid);
                    return;
                }

                GenoReportBuilder builder = new GenoReportBuilder();
                builder.setFontPath(fontPath);
                builder.initPdf(prefixPath + sampleResult.toString() + ".pdf");
                builder.buildAll(data);
                if (noUpdate == null || noUpdate != 1) {
                    // pdf状态成功
                    updatePdfStateById(sampleResult.getId(), SampleExpand.PdfState.YES);
                }

            } catch (Exception e) {
                log.info("未知异常：", e);
                for (int i = 0; i < size; i++) {
                    countDownLatch.countDown();
                }

                SampleResult sampleResult = getSampleResult(uuid);
                if (noUpdate == null || noUpdate != 1) {
                    // pdf状态失败
                    updatePdfStateById(sampleResult.getId(), SampleExpand.PdfState.FAIL);
                }
            } finally {
                watch.stop();
                log.info(uuid + ",耗时：" + watch.getTotalTimeMillis() + "ms");
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
        return Result.ok("正在生成中，请耐心等待！");
    }

    private boolean isOver(@RequestBody PdfRequest pdfRequest, SampleResult sampleResult) {
        SampleExpand sampleExpandQuery = new SampleExpand();
        sampleExpandQuery.setSampleId(sampleResult.getId());
        SampleExpand sampleExpand = sampleExpandMapper.selectOne(sampleExpandQuery);
        return SampleExpand.PdfState.YES.val().equals(sampleExpand.getPdfState()) && (pdfRequest.getSkip() == null || pdfRequest.getSkip() == 1);
    }

    /**
     * 修改pdf状态
     */
    private void updatePdfStateById(Long id, SampleExpand.PdfState pdfState) {
        SampleExpand sampleExpandUpdate = new SampleExpand();
        sampleExpandUpdate.setPdfState(pdfState.val());
        Example example = new Example(SampleExpand.class);
        example.createCriteria().andEqualTo("sampleId", id);
        sampleExpandMapper.updateByExampleSelective(sampleExpandUpdate, example);
    }

    private SampleResult getSampleResult(String uuid) {
        Sample sampleQuery = new Sample();
        sampleQuery.setUuid(uuid);
        return sampleMapper.selectSampleResult(sampleQuery);
    }

}
