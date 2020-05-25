package com.lll.learn.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.lll.learn.pdf.event.HeaderTextEvent;

import java.io.IOException;
import java.net.URL;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/20 8:34
 **/
public class GenoReportBuilder extends ReportBuilder {

    @Override
    public void initPdf(String outPath) throws IOException {
        super.initPdf(outPath);
    }

    @Override
    public GenoReportBuilder addIndex() {
        Image indexImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/成人纸质报告-54.png")));
        Image introductionImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/gene.png")));
        Image honorImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/honor.png")));

        doc.add(indexImage);
        pdf.addNewPage(2).flush();
        doc.add(introductionImage);
        doc.add(honorImage);
        return this;
    }

    @Override
    public GenoReportBuilder addHello() {
        Div div = new Div();
        div.setWidth(UnitValue.createPercentValue(100));
        div.setHeight(UnitValue.createPercentValue(100));
        div.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph p1 = new Paragraph();
        p1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        p1.setMaxWidth(UnitValue.createPercentValue(75));
        p1.setMarginTop(180f);
        p1.setCharacterSpacing(0.4f);
        Style large = new Style();
        large.setFontSize(22);
        large.setFontColor(GenoColor.getThemeColor());
        p1.add(new Text("尊敬的 ").addStyle(large));
        p1.add(new Text("郑成功").addStyle(large).setBorderBottom(new SolidBorder(0.5f)));
        p1.add(new Text(" 女士：\n").addStyle(large));
        p1.add(new Text("您好！\n"));
        p1.add(new Text("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0衷心感谢您对我们的信任，选择壹基因疾病遗传风险基因检测服务!\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0当您收到这份报告书的时候，我们已经根据您的需求，秉持科学、专业、严谨和保密的态度为您完成了本次基因检测，并根据您的个人特有基因检测结果进行了全面深入的分析。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0现代医学研究表明，所有疾病（除外伤）的发生均与基因有关，而易感基因与疾病的发生有着非常密切的关系，患病是基因（内因）与外部环境（外因）共同作用的结果。我们只有了解自身的基因奥秘、预测出疾病的发生风险，才能更好的利用现代医学手段，做到早预防、早诊断、早治疗，实现“上医治未病”的目的。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0本报告是通过对受检者提供的生物样本（一般为口腔黏膜细胞）进行基因检测，依据国际有关疾病易感基因的公开研究成果和数据，参考国际权威23andme，对检测结果进行分析，计算出受检者患某些疾病的风险指数。医学专家顾问团队依据患病风险等级给出针对性的饮食、运动、常规体检等方面的健康建议，旨在帮助受检者根据个人基因信息，科学合理地加强自身健康管理，预防疾病的发生。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0希望本次检测能为您带来舒适满意的体验，针对本次检测，如果您有任何疑问需要解答，敬请拨打我们的健康热线400-163-5588。我们恭候您的来电，并保证给予及时、专业、贴心的回复。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0壹基因衷心祝愿您身体健康、享受品质生活！"));
        div.add(p1);



        Painting painting = new Painting(pdf);
        painting.drawHello("image/纸质报告-03.png");
        painting.close();

        doc.add(div);
        return this;
    }

    @Override
    public ReportBuilder addExaminee() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Div div1 = new Div();
        Image iconImage27 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/icon-27.png")));
        Paragraph p1 = new Paragraph();
        p1.add(iconImage27.addStyle(GenoStyle.getIconStyle()));
        p1.add(new Text("受检人信息").addStyle(GenoStyle.getTitleStyle()));
        div1.add(p1);
        doc.add(div1);

        Div div2 = new Div();
        div2.addStyle(GenoStyle.getDefaultTableOuter());
        Table table = new Table(3).useAllAvailableWidth();
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("姓 名：郑成功")));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("性 别： 女")));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("年 龄：23")));
        table.startNewRow();
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("样本编号：ITGE1625")));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("样本类型：基因组DNA")));
        table.startNewRow();
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("样本检测结果：合格")));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("报告⽇期：2020年5⽉20⽇")));
        div2.add(table);
        doc.add(div2);
        return this;
    }


    @Override
    public GenoReportBuilder addDetectionContent() {
        Div div1 = new Div();
        div1.setMargins(30, 0, 20, 0);
        Image iconImage27 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/icon-29.png")));
        Paragraph p1 = new Paragraph();
        p1.add(iconImage27.addStyle(GenoStyle.getIconStyle()));
        p1.add(new Text("检测内容").addStyle(GenoStyle.getTitleStyle()));
        div1.add(p1);

        Paragraph p2 = new Paragraph();
        p2.setFontSize(11);
        p2.add("检测套餐：防癌爆款【男士三项】-仅测试\n");
        p2.add("检测⽅法：⾼通量基因分型技术\n");
        p2.add("检测形式：对受检者基因组中与各项检测项⽬密切相关的基因信息进⾏DNA分型检测。\n");
        div1.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add(new Text("用户须知\n").addStyle(GenoStyle.getSecondTitleStyle()));
        p3.add(new Text("1、检测基因和位点的筛选均基于国际权威数据库（HGMD、OMIM等）和权威学术研究报道。\n" +
                "2、检测基因和位点以及用于解读报告的数据库，会随着基因库的增大和医学研究的更新而升级优化。\n" +
                "3、检测结果可用于指导生活习惯、健康管理和疾病预防⽅案的制定，不作为临床诊断和治疗的依据。\n" +
                "4、疾病的发生、发展与基因变异密切相关，是基因（内因）与外部环境（外因）共同作用的结果。\n" +
                "5、基因检测的目的不是去忽视低风险疾病，而是找出高风险疾病，并进行针对性的预防和健康管理，\n" +
                "以延缓或规避相应疾病的发生。"));
        Div div2 = new Div();
        div2.setMarginTop(30);
        Table signatureTable = new Table(2);
        signatureTable.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        signatureTable.setWidth(UnitValue.createPercentValue(50));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("检测人：舒平")));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("复核人：张萍")));
        signatureTable.startNewRow();
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("日期：2020年5月20日")));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("日期：2020年5月20日")));
        div2.add(signatureTable);

        Image iconImage01 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/image-01.png")));
        iconImage01.setWidth(105);
        iconImage01.setHeight(105);
        iconImage01.setRelativePosition(295, 0, 60, 90);
        div2.add(iconImage01);
        doc.add(div1);
        doc.add(p3);
        doc.add(div2);

        return this;
    }

    @Override
    public GenoReportBuilder addDirectory() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Div div1 = new Div();
        Paragraph p1 = new Paragraph();
        p1.add(new Text("目录").addStyle(GenoStyle.getTitleStyle()).setFontSize(32));

        Table tableCatalog = new Table(4).useAllAvailableWidth();
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("检测结果概况").addStyle(GenoStyle.getSecondTitleStyle())));
        tableCatalog.addCell(GenoComponent.getCatelogCell(2).add(GenoComponent.getCatelogDottedLine(1)));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("8")));
        tableCatalog.startNewRow();
        // 需要注意 动态变化
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("需要注意").addStyle(GenoStyle.getSecondTitleStyle())));
        tableCatalog.startNewRow();

        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("宫颈癌")));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(GenoComponent.getCatelogDottedLine(2)));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new List().add(new ListItem("风险高").setListSymbol(new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/red-point.png"))).addStyle(GenoStyle.getDefaultPoint())))));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("10")));
        tableCatalog.startNewRow();

        // 正常项目 动态变化
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("正常项目").addStyle(GenoStyle.getSecondTitleStyle())));
        tableCatalog.startNewRow();
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("常见肿瘤类").addStyle(GenoStyle.getSecondTitleStyle().setFontSize(13))));
        tableCatalog.startNewRow();

        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("胃癌")));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(GenoComponent.getCatelogDottedLine(2)));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new List().add(new ListItem("风险正常").setListSymbol(new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/blue-point.png"))).addStyle(GenoStyle.getDefaultPoint())))));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("23")));
        tableCatalog.startNewRow();

        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("结束语").addStyle(GenoStyle.getSecondTitleStyle())));

        div1.add(p1);
        div1.add(tableCatalog);
        doc.add(div1);
        return this;
    }

    @Override
    public GenoReportBuilder addResultSummary() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Paragraph header = GenoComponent.getHeaderLineText("检测结果概况");

        Paragraph p1 = new Paragraph();
        p1.add(new Text("检测结果概况\n").addStyle(GenoStyle.getTitleStyle()));
        p1.add(new Text("本次检测包括"));
        p1.add(new Text("常见肿瘤类").addStyle(GenoStyle.getThirdTitleStyle()));
        p1.add(new Text("1部分内容\n"));
        p1.add(new Text("共计"));
        p1.add(new Text("5").addStyle(GenoStyle.getThirdTitleStyle()));
        p1.add(new Text("项检测项目"));

        Table t1 = new Table(2).useAllAvailableWidth();
        t1.setMargins(30, 0, 20, 0);
        Image goodTipImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/goodtip.png")));
        t1.addCell(GenoComponent.getDefaultCell(2, 1).setWidth(80).setPaddingBottom(30).add(goodTipImage.addStyle(GenoStyle.getLargeIconStyle())));
        t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("优势标签").addStyle(GenoStyle.getThirdTitleStyle())));
        // 优势标签
        t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("暂无优势项目")));
        t1.startNewRow();

        // 需要注意
        Image badTipImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/badtip.png")));
        t1.addCell(GenoComponent.getDefaultCell(2, 1).setWidth(80).setPaddingRight(30).add(badTipImage.addStyle(GenoStyle.getLargeIconStyle())));
        t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("需要注意").addStyle(GenoStyle.getThreeTitleOrangeStyle())));

        Div tabDiv = new Div();
        Paragraph element = new Paragraph();


        for (int i = 0; i < 10; i++) {
            Text text = new Text("宫颈癌风险高" + ((i + 1) % 3 == 0 ? "\n" : ""));
            Style style = new Style();
            style.setPaddings(2, 10, 2, 10);
            style.setBackgroundColor(GenoColor.getOrangeColor());
            style.setBorderRadius(new BorderRadius(10));
            style.setFontColor(ColorConstants.WHITE);
            style.setMargins(0, 3, 0, 3);
            text.addStyle(style);
            element.add(text);
        }

        tabDiv.add(element);
        t1.addCell(GenoComponent.getDefaultCell().add(tabDiv));

        Paragraph p2 = new Paragraph();
        p2.setFixedPosition(60, 90, UnitValue.createPercentValue(100));
        p2.add(GenoComponent.getSecondTitle("温馨提示\n"));
        p2.add(new Text("1、本检测报告的每项内容主要由检测结果、基因位点信息、简介、健康建议等组成。\n" +
                "2、我们用基因风险指数来解释和划分您的疾病遗传风险等级,共分为五级,五级及分级" +
                "标准为:低风险<0.5、0.5≤稍低风险<0.8、0.8≤中等或正常水平风险<1.2、1.2≤稍高风险<1.8、高风险≥1.8。\n" +
                "3、基因位点信息由四列内容组成,分别为本项目所检测的位点名称、参考基因型(参考型)、检测出" +
                "的基因型(检出型)及该基因型的作用。\n" +
                "4、因篇幅限制,本页仅展示了部分\"需要注意\"的项目,请在目录或详细检测结果查看全部该类项目。"));

        doc.add(header);
        doc.add(p1);
        doc.add(t1);
        doc.add(p2);

        Painting painting = new Painting(pdf);
        painting.drawHeader();
        painting.close();

        // 加个详细检测结果页
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Image image88 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/成人纸质报告8.8-10.png")));
        doc.add(image88);
        return this;
    }

    @Override
    public GenoReportBuilder addContext() {
        float score = 1.9f;
        String title = "结直肠癌";
        HeaderTextEvent headerTextEvent = new HeaderTextEvent(title);
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, headerTextEvent);

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        // 头条，进度条
        Painting painting = new Painting(pdf);
        painting.drawSegment(score);
        painting.close();

        Paragraph p1 = new Paragraph();
        p1.add(new Text("结直肠癌\n").addStyle(GenoStyle.getTitleStyle()));
        p1.add(GenoComponent.getSecondTitle("检测结果"));
        doc.add(p1);

        // 等级
        Div progressBlock = new Div();
        Paragraph level = new Paragraph();
        level.setWidth(200);
        Style style = new Style();
        style.setFontSize(11);
        style.setMargins(2, -4, 2, 0);
        style.setPaddings(3, 12, 3, 12);
        Style attentionStyle = new Style();
        attentionStyle.setBorderRadius(new BorderRadius(5));
        attentionStyle.setFontColor(ColorConstants.WHITE);

        Text level1 = new Text("低").addStyle(style);
        Text level2 = new Text("稍低").addStyle(style);
        Text level3 = new Text("正常").addStyle(style);
        Text level4 = new Text("稍高").addStyle(style);
        Text level5 = new Text("高").addStyle(style);
        String[] segmenets = Constant.SEGMENETS;
        Color color = null;
        if (score >= Float.parseFloat(segmenets[0]) && score < Float.parseFloat(segmenets[1])) {
            color = GenoColor.getLightBlueColor();
            attentionStyle.setBackgroundColor(color);
            level1.addStyle(attentionStyle);
        } else if (score >= Float.parseFloat(segmenets[1]) && score < Float.parseFloat(segmenets[2])) {
            color = GenoColor.getLightBlueColor();
            attentionStyle.setBackgroundColor(color);
            level2.addStyle(attentionStyle);
        } else if (score >= Float.parseFloat(segmenets[2]) && score < Float.parseFloat(segmenets[3])) {
            color = GenoColor.getThemeColor();
            attentionStyle.setBackgroundColor(color);
            level3.addStyle(attentionStyle);
        } else if (score >= Float.parseFloat(segmenets[3]) && score < Float.parseFloat(segmenets[4])) {
            color = GenoColor.getOrangeColor();
            attentionStyle.setBackgroundColor(color);
            level4.addStyle(attentionStyle);
        } else if (score >= Float.parseFloat(segmenets[4]) && score <= Float.parseFloat(segmenets[5])) {
            color = GenoColor.getRedColor();
            attentionStyle.setBackgroundColor(color);
            level5.addStyle(attentionStyle);
        }
        level.add(level1);
        level.add(level2);
        level.add(level3);
        level.add(level4);
        level.add(level5);
        level.setHorizontalAlignment(HorizontalAlignment.CENTER);
        progressBlock.add(level);
        doc.add(progressBlock);

        // 正文
        int index = 0;
        Paragraph p2 = new Paragraph();
        p2.setMarginTop(60);
        p2.add(new Tab());
        p2.addTabStops(new TabStop(20, TabAlignment.LEFT));
        p2.add("您的基因风险指数为");
        p2.add(new Text(score + "").setFontColor(color));
        switch (index) {
            case 0:
            case 1:
                p2.add("。在相同外界条件下，与大多数人相比，您具有较好的基因优势。但也不要忽视了预防，疾病是由基因和外界环境共同作用而导致的，因此仍建议您注意规避外界风险因素，保持良好的生活习惯，加强锻炼，定期进行体检和相关筛查。");
                break;
            case 2:
                p2.add("。在相同外界条件下，您与大多数人的患病风险相同。基因风险相同情况下，最终患病与否，与外界环境、生活习惯和医疗条件的差异密切相关。因此，仍建议您重视预防{{itemval.name}}，注意规避外界风险因素，保持良好的生活习惯，加强锻炼，定期进行体检和相关筛查。");
            case 3:
                p2.add("。在相同外界条件下，您患{{itemval.name}}的风险比正常人群稍高。建议您尽可能规避相关高危因素、调整生活习惯，改变不利的生活方式，定期进行体检和相关筛查，认真对待相关急慢性疾病（如发生）的治疗和医疗干预。");
            case 4:
                p2.add("。在相同外界条件下，您患{{itemval.name}}的风险高于正常人群。建议您尽可能规避{{itemval.name}}相关高危因素、调整生活习惯，改变不利的生活方式，定期进行体检和相关筛查，认真对待相关急慢性疾病（如发生）的治疗和医疗干预。");
            default:
                break;
        }
        doc.add(p2);
        // 基因位点信息
        doc.add(new Paragraph("基因位点信息").addStyle(GenoStyle.getSecondTitleStyle()));
        Table geneLocusTable = new Table(4).useAllAvailableWidth();
        geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("基因位点")).addStyle(GenoStyle.getTableHeader()));
        geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("参考型")).addStyle(GenoStyle.getTableHeader()));
        geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("检出型")).addStyle(GenoStyle.getTableHeader()));
        geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("基因型解释")).addStyle(GenoStyle.getTableHeader()));
        geneLocusTable.startNewRow();
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("HLA-DPA1-103617")));
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("G/G")));
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("A/A")));
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("风险增加")));
        geneLocusTable.startNewRow();
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("ERCC6/ERCC6-PGBD3-633931")));
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("C/C")));
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("T/T")));
        geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph("风险稍微增加")));
        geneLocusTable.startNewRow();
        doc.add(geneLocusTable);

        // 简介
        doc.add(GenoComponent.getTitleParagraph(new Text("简介").addStyle(GenoStyle.getSecondTitleStyle())));
        Paragraph p3 = new Paragraph();
        p3.add("肺癌（Lung cancer）是原发性⽀⽓管肺癌（Primary bronchogenic carcinoma）的简称，肿瘤细\n" +
                "胞源于⽀⽓管黏膜上⽪或腺体，常有区域性淋巴结转移和⾎⾏播散。早期常有刺激性咳嗽、痰中带⾎\n" +
                "等呼吸道症状，病情的进展速度与细胞⽣物特性有关。肺癌为当前世界各地最常⻅的恶性肿瘤之⼀，\n" +
                "肺癌发病率在男性恶性肿瘤中已居⾸位");
        doc.add(p3);


        // 新的一页，症状，健康建议
//        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(GenoComponent.getTitleParagraph(GenoComponent.getSecondTitle("症状")));
        Paragraph p4 = new Paragraph();
        p4.add("1.阴道流⾎：是宫颈癌的主要症状。出⾎量根据病灶⼤⼩、侵及间质内⾎管情况⽽定，若侵袭⼤⾎管\n" +
                "可引起⼤出⾎。早期多为接触性出⾎。年轻患者也可表现为经期延⻓、经量增多；⽼年患者常为绝经\n" +
                "后不规则阴道流⾎。⼀般外⽣型较早出现该症状，出⾎量多；内⽣型较晚出现该症状。\n" +
                "2.阴道排液：阴道排液增多，液体为⽩⾊或⾎性，稀薄如⽔样或⽶汤样，有腥臭味。晚期患者因癌组\n" +
                "织坏死、继发感染，可有⼤量⽶汤样或脓性恶臭⽩带排出。\n" +
                "3.晚期症状：根据癌灶累及范围出现不同的继发性症状。如尿频、尿急、便秘、下肢肿痛等；癌肿压\n" +
                "迫或累及输尿管时，可引起输尿管梗阻、肾盂积⽔及尿毒症；晚期可有贫⾎、消瘦、发热、恶病质等\n" +
                "全⾝衰竭症状。");
        doc.add(p4);
        doc.add(GenoComponent.getTitleParagraph(GenoComponent.getSecondTitle("健康建议")));
        doc.add(GenoComponent.getTitleParagraph(GenoComponent.getThirdTitle("二级预防建议")));
        doc.add(new Paragraph("1.⾃检预警信号\n" +
                "早期宫颈癌⽆明显症状，疾病发展到⼀定程度，可出现不同程度的⾮典型症状，如阴道不规则出⾎，\n" +
                "阴道分泌物异常增多，味道腥臭等症状。出现这些症状或相关慢性疾病此症状加重时，要尽快到医院\n" +
                "诊治。\n" +
                "2.定期筛查和检查\n" +
                "积极进⾏宫颈癌筛查：?根据美国妇产科医师协会（ACOG）2016年推荐的筛查指南建议：\n" +
                "①21~29岁的⼥性，推荐每3年筛查⼀次宫颈细胞学检查（巴⽒涂⽚或TCT检测）；\n" +
                "②30~65岁的⼥性，每5年筛查⼀次⼈类乳头状瘤病毒(HPV)检测与宫颈细胞学检查，也可以每3年筛\n" +
                "查⼀次宫颈细胞学检查。"));
        doc.add(GenoComponent.getTitleParagraph(GenoComponent.getThirdTitle("一级预防建议")));
        doc.add(new Paragraph("1.做好慢病管理\n" +
                "积极预防和治疗相关疾病：积极预防并治疗慢性⼦宫颈炎等疾病，分娩时注意避免宫颈裂伤，如有裂\n" +
                "伤应及时修补。\n" +
                "2.预防和感染控制\n" +
                "①保持外阴清洁：要保持良好卫⽣习惯，勤换内裤，严禁乱⽤各种冲洗液，以免破坏阴道天然防护屏\n" +
                "障。\n" +
                "②注意监测⾼危型HPV，运⽤安全有效HPV疫苗：HPV持续感染是引起宫颈癌的主要危险因素，HPV\n" +
                "的感染引起超过90%的宫颈癌病例，HPV-16/18亚型是最常⻅的HPV致癌亚型。⽬前HPV疫苗已⽐较\n" +
                "成熟，国内上市的⼆价、四价、九价疫苗均可涵盖⾼危型HPV，建议⼥性可根据⾃⾝年龄和经济状\n" +
                "况，选择接种不同价型的HPV疫苗。\n" +
                "③避免其它病原体的感染：沙眼⾐原体、⼈巨细胞病毒、⽀原体等病原体的感染在HPV感染导致宫颈\n" +
                "癌的发病过程中有协同作⽤，要洁⾝⾃好、注意卫⽣，积极避免这些病原体的感染。\n" +
                "3.改变不良⽣活⽅式\n" +
                "①选择安全可靠的避孕措施：⼝服避孕药也会增加宫颈癌的患病⻛险，应尽量选择安全套等⾮药物避\n" +
                "孕措施，还能预防HPV感染。\n" +
                "②保持有节律的性⽣活：性⽣活紊乱、过早性⽣活（＜16岁）都会增加宫颈癌患病⻛险。\n" +
                "4.养成健康饮⻝习惯\n" +
                "①少吃腌制、烟熏、烧烤及⾟辣⻝品：这些⻝品含有的亚硝酸盐、多环芳烃类致癌物质，可增加宫颈\n" +
                "癌患病⻛险，另外，⼤量⻝⽤⾟辣⻝品也可增加患癌的⻛险。\n" +
                "②保证每⽇蔬菜和⽔果的摄⼊，限制⾼脂、⾼糖饮⻝，均衡膳⻝：特别是脂肪摄⼊量较⾼的⼥性患病\n" +
                "⻛险较⾼。建议每⽇⾄少⻝⽤300~500g蔬菜及200~350g⽔果，健康⼈群每⽇平均膳⻝⽐例应为：⾕\n" +
                "薯类30%、蔬菜35%、⽔果20%、动物性⻝品加⼤⾖15%。\n" +
                "5.规避外界环境⻛险\n" +
                "避免致癌物接触：铀、镭及其衍化物等放射性物质，砷、铬、镍等重⾦属，煤焦油、⽯棉、福尔⻢林\n" +
                "等化⼯原料具有较强致癌作⽤。要避免接触此类化学物质，相关⾏业⼯作者应按照职业防护标准做好\n" +
                "防护。"));

        // 基因解读
        doc.add(GenoComponent.getTitleParagraph(new Text("基因解读").addStyle(GenoStyle.getSecondTitleStyle())));
        doc.add(new Paragraph("研究表明，EXOC1、RAB51B、ZNRD1等基因的多态性与宫颈癌的发病⻛险密切相关。\n" +
                "EXOC1基因编码的蛋⽩属于胞泌⼩泡蛋⽩复合体的组分之⼀，其功能主要负责将胞泌⼩泡运输并定位\n" +
                "到细胞膜的特定部位。当该基因发⽣突变时会减弱⼈体对感染病原微⽣物的免疫抵抗能⼒，导致感染\n" +
                "⼈乳头状瘤病毒（HPV）的机率增加，进⽽增加宫颈癌的发病⻛险。研究表明，EXOC1基因的多态性\n" +
                "和宫颈癌的发病⻛险密切相关，该基因254173位点的基因型为TC以及TT的⼈群患宫颈癌的⻛险要⾼\n" +
                "于基因型为CC的⼈群。\n" +
                "RAD51B基因编码的蛋⽩属于DNA损伤修复蛋⽩RAD51蛋⽩超家族，该蛋⽩是在DNA损伤修复过程中\n" +
                "⾮常重要的成员之⼀，能够和该家族另外⼀个成员RAD51C形成稳定的异源⼆聚体共同参与DNA修复\n" +
                "过程。研究表明，RAD51B基因的多态性和宫颈癌的发病⻛险密切相关，该基因183885位点的基因型\n" +
                "为AG以及GG的⼈群患宫颈癌的⻛险要低于基因型为AA的⼈群。\n" +
                "ZNRD1基因编码的产物属于ZNRD1基因的反义RNA分⼦，该RNA分⼦可以和ZNRD1基因编码的信使R\n" +
                "NA结合从⽽调节ZNRD1基因的表达⽔平。ZNRD1蛋⽩属于RNA聚合酶的组成成分之⼀，可以调控细\n" +
                "胞的增殖。研究表明，ZNRD1基因的多态性和宫颈癌的发病⻛险密切相关，该基因126819位点的基\n" +
                "因型为AG以及AA的⼈群患宫颈癌的⻛险要低于基因型为GG的⼈群。"));

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(GenoComponent.getTitleParagraph(new Text("参考文献（部分）").addStyle(GenoStyle.getSecondTitleStyle())));
        doc.add(new Paragraph(new Text("[1]Jin?T,?Wu?X,?Yang?H.?Association?of?the?miR-17-5p?variants?with?susceptibility?to?cervical?cancer\n" +
                "in?a?Chinese?population.??Oncotarget.??2016?7(47).\n" +
                "[2]Miura?K,?Mishima?H,?Kinoshita?A.?Genome-wide?association?study?of?HPV-associated?cervical?ca\n" +
                "ncer?in?Japanese?women.??Journal?of?Medical?Virology.??2014?86(7).\n" +
                "[3]Shi?Y,?Li?L,?Hu?Z.?A?genome-wide?association?study?identifies?two?new?cervical?cancer?susceptibi\n" +
                "lity?loci?at?4q12?and?17q12.??Nature?Genetics.??2013?45(8).\n" +
                "[4]Jia?M,?Han?J,?Hang?D.?HLA-DP?is?the?cervical?cancer?susceptibility?loci?among?women?infected?b\n" +
                "y?high-risk?human?papillomavirus:?potential?implication?for?triage?of?human?papillomavirus-positi\n" +
                "ve?women.??Tumor?Biology.??2015?1-7.\n" +
                "[5]Han?J,?Zhou?W,?Jia?M.?Expression?quantitative?trait?loci?in?long?non-coding?RNA?PAX8-AS1?are?as\n" +
                "sociated?with?decreased?risk?of?cervical?cancer.??Molecular?Genetics?and?Genomics.??2016?1-6.\n" +
                "[6]Chen?D,?Enroth?S,?Liu?H.?Pooled?analysis?of?genome-wide?association?studies?of?cervical?intraep\n" +
                "ithelial?neoplasia?3?(CIN3)?identifies?a?new?susceptibility?locu.?Oncotarget.?2016.\n" +
                "[7]Guo?L,?Wen?J,?Han?J.?Expression?quantitative?trait?loci?in?long?non-coding?RNA?ZNRD1-AS1?influ\n" +
                "ence?cervical?cancer?development.??American?journal?of?cancer?research.??2015?5(7).\n" +
                "[8]Hang?D,?Zhou?W,?Jia?M.?Genetic?variants?within?microRNA‐binding?site?of?RAD51B?are?associate\n" +
                "d?with?risk?of?cervical?cancer?in?Chinese?women.??Cancer?Medicine.??2016?5(9).\n" +
                "[9]Xie?B,?Zhang?Z,?Wang?H.?Genetic?polymorphisms?in?MMP?2,?3,?7,?and?9?genes?and?the?susceptibili\n" +
                "ty?a").addStyle(new Style().setPaddings(-1,0,-1,0).setCharacterSpacing(1))).setFontSize(9f).setFontColor(new DeviceRgb(85,85,85)));

        // 移除监听器
        pdf.removeEventHandler(PdfDocumentEvent.START_PAGE, headerTextEvent);

        return this;
    }

    @Override
    public GenoReportBuilder addThanks() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Paragraph p1 = new Paragraph();
        p1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        p1.setMaxWidth(UnitValue.createPercentValue(75));
        p1.setMarginTop(200f);
        p1.setCharacterSpacing(0.4f);
        p1.add(new Text("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0再次感谢您选用壹基因提供的基因检测服务。\n" +
                "\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0疾病的发生受到先天和后天两个因素的共同影响，某疾病先天遗传风险低并不代表患病概率是零，如果后天生活习惯很差，生存环境不好，都会提升疾病的发生风险，促进疾病的最终发生；做基因检测的目的，不是去忽视低风险的疾病，而是要找出高风险疾病进行重点预防，采取措施进行针对性干预，以便阻断或者延缓疾病的发生。\n" +
                "\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0您可以通过我们提供的健康建议开展健康管理，对风险较高项目积极预防，如改变饮食习惯，定期进行针对性的临床体检等。若您携带某种疾病易感基因型，该基因型很可能也存在于您亲属的基因中并遗传给他们的后代。因此，壹基因建议您的亲属也针对这些高风险项目进行检测，以了解自身健康风险，及早采取干预措施，拥有健康品质生活。\n" +
                "\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0如果您对我们检测服务和体验有任何意见或建议，敬请拨打我们的健康热线400-163-5588，或者手机扫描下部二维码，联系您的专属健康顾问。"));
        doc.add(p1);
        URL resource = Painting.class.getClassLoader().getResource("image/结束语.png");
        Image backImage = new Image(ImageDataFactory.create(resource));
        int pageNum = pdf.getNumberOfPages();
        backImage.setFixedPosition(pageNum, 0, 0, UnitValue.createPercentValue(125));
        backImage.scale(1, 1.05f);
        doc.add(backImage);

        return this;
    }

    @Override
    public GenoReportBuilder addBackCover() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Image backCoverImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/封底-02.png")));
        backCoverImage.setWidth(UnitValue.createPercentValue(100));
        backCoverImage.scale(1.3f, 1.3f);
        backCoverImage.setMarginLeft(-70);
        backCoverImage.setMarginTop(-80);
        doc.add(backCoverImage);
        return this;
    }

}