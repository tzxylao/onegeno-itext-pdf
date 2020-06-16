package com.onegene.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.onegene.pdf.component.AbstractReportBuilder;
import com.onegene.pdf.component.report.drug.AdultDrugReportBuilder;
import com.onegene.pdf.component.report.gene.GenoReportBuilder;
import com.onegene.pdf.component.entity.PrintReportBean;
import com.onegene.pdf.component.entity.Result;
import org.springframework.util.StopWatch;

import java.io.File;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 13:35
 **/
public class PdfMain {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String prefix = "/Users/laoliangliang/Desktop/";
        String fileName = "report.pdf";

        createDirectory(prefix);

        AbstractReportBuilder abstractReportBuilder = null;

        // 获取真实数据
        int selected = 1;
        if (selected < 2) {
            abstractReportBuilder = new GenoReportBuilder();
        } else if (selected == 2) {
            abstractReportBuilder = new AdultDrugReportBuilder();
        }
        abstractReportBuilder.initPdf(prefix + fileName);
        Result<PrintReportBean> reportBeanResult = null;
        if (selected == 1) {
            HttpRequest request = HttpUtil.createPost(
                    "http://result.1genehealth.com/api/v1/biology/bio/report/print?access_token=72d892f3-96ea-4ddf-b2bb-9490a051f3a5&uuid=ce74f1476c324bf0a063e7f4c3ec7df2&mold=&type=1");
//            HttpRequest request = HttpUtil.createPost(
//                    "http://result.dev.1genehealth.com/api/v1/biology/bio/report/medical/print?access_token=15444897-ea08-4df2-a416-98d9a947d11e&uuid=6cf678f342e54c8aae0fc0bd7896e3a1&mold=adult&type=1");
            HttpResponse execute = request.execute();
            String body = execute.body();
            reportBeanResult = JSON.parseObject(body, new TypeReference<Result<PrintReportBean>>() {
            });
        } else if (selected == 0) {
            String requestStr = FileUtil.readString(new File(PdfMain.class.getClassLoader().getResource("test/request.json").getPath()), "UTF-8");
            reportBeanResult = JSON.parseObject(requestStr, new TypeReference<Result<PrintReportBean>>() {
            });
        } else if (selected == 2) {
            String requestStr = FileUtil.readString(new File(PdfMain.class.getClassLoader().getResource("test/adult.json").getPath()), "UTF-8");
            reportBeanResult = JSON.parseObject(requestStr, new TypeReference<Result<PrintReportBean>>() {
            });
        }

        PrintReportBean data = reportBeanResult.getData();
        abstractReportBuilder.setPrintReportBean(data);
        abstractReportBuilder.buildAll(data);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

    private static void createDirectory(String prefixPath) {
        File prefixPathFile = new File(prefixPath + "temp/");
        if (!prefixPathFile.exists()) {
            prefixPathFile.mkdirs();
        }
    }

}
