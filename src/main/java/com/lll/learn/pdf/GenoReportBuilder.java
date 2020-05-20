package com.lll.learn.pdf;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;

import java.io.IOException;
import java.net.URL;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/20 8:34
 **/
public class GenoReportBuilder extends ReportBuilder {

    @Override
    public void initPdf(String filePath) throws IOException {
        super.initPdf(filePath);
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
        p1.setMarginTop(200f);
        p1.setCharacterSpacing(0.4f);
        Style large = new Style();
        large.setFontSize(22);
        large.setFontColor(GenoReportStyle.getThemeColor());
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

        URL resource = GenoReportBuilder.class.getClassLoader().getResource("image/纸质报告-03.png");
        Image backImage = new Image(ImageDataFactory.create(resource));
        PdfPage page = pdf.addNewPage();
        Rectangle pageSize = page.getPageSize();
        int leftDist = 0;
        Rectangle rectangle = new Rectangle(
                pageSize.getX() + leftDist,
                pageSize.getTop(),
                pageSize.getWidth() - leftDist * 2,
                40);

        Canvas canvas = new Canvas(page, rectangle);
        canvas.add(backImage).flush();
        canvas.close();

        doc.add(div);
        return this;
    }

    @Override
    ReportBuilder addExaminee() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Div div1 = new Div();
        Image iconImage27 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/icon-27.png")));
        Paragraph p1 = new Paragraph();
        p1.add(iconImage27.addStyle(GenoReportStyle.getIconStyle()));
        p1.add(new Text("受检人信息").addStyle(GenoReportStyle.getTitleStyle()));
        div1.add(p1);
        doc.add(div1);

        Div div2 = new Div();
        div2.addStyle(GenoReportStyle.getDefaultTableOuter());
        Table table = new Table(3).useAllAvailableWidth();
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("姓 名：郑成功")));
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("性 别： 女")));
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("年 龄：23")));
        table.startNewRow();
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("样本编号：ITGE1625")));
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("样本类型：基因组DNA")));
        table.startNewRow();
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("样本检测结果：合格")));
        table.addCell(Component.getDefaultCell().add(Component.getSignParagraph("报告⽇期：2020年5⽉20⽇")));
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
        p1.add(iconImage27.addStyle(GenoReportStyle.getIconStyle()));
        p1.add(new Text("检测内容").addStyle(GenoReportStyle.getTitleStyle()));
        div1.add(p1);

        Paragraph p2 = new Paragraph();
        p2.setFontSize(11);
        p2.add("检测套餐：防癌爆款【男士三项】-仅测试\n");
        p2.add("检测⽅法：⾼通量基因分型技术\n");
        p2.add("检测形式：对受检者基因组中与各项检测项⽬密切相关的基因信息进⾏DNA分型检测。\n");
        div1.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add(new Text("用户须知").addStyle(GenoReportStyle.getSecondTitleStyle()));
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
        signatureTable.addCell(Component.getSignCell().add(Component.getSignParagraph("检测人：舒平")));
        signatureTable.addCell(Component.getSignCell().add(Component.getSignParagraph("复核人：张萍")));
        signatureTable.startNewRow();
        signatureTable.addCell(Component.getSignCell().add(Component.getSignParagraph("日期：2020年5月20日")));
        signatureTable.addCell(Component.getSignCell().add(Component.getSignParagraph("日期：2020年5月20日")));
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
    public void addDirectory() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Div div1 = new Div();
        Paragraph p1 = new Paragraph();
        p1.add(new Text("目录").addStyle(GenoReportStyle.getTitleStyle()).setFontSize(32));

        Table tableCatalog = new Table(4).useAllAvailableWidth();
        tableCatalog.addCell(Component.getCatelogCell().add(new Paragraph("检测结果概况").addStyle(GenoReportStyle.getSecondTitleStyle())));
        tableCatalog.addCell(Component.getCatelogCell(2).add(Component.getCatelogDottedLine(1)));
        tableCatalog.addCell(Component.getCatelogCell().add(new Paragraph("8")));
        tableCatalog.startNewRow();
        // 需要注意 动态变化
        tableCatalog.addCell(Component.getCatelogCell().add(new Paragraph("需要注意").addStyle(GenoReportStyle.getSecondTitleStyle())));
        tableCatalog.startNewRow();
        tableCatalog.addCell(Component.getCatelogCell().add(new Paragraph("宫颈癌")));
        tableCatalog.addCell(Component.getCatelogCell().add(Component.getCatelogDottedLine(2)));
        tableCatalog.addCell(Component.getCatelogCell().add(new List().add(new ListItem("风险高").setListSymbol(ListNumberingType.ZAPF_DINGBATS_4))));
        tableCatalog.addCell(Component.getCatelogCell().add(new Paragraph("10")));
        div1.add(p1);
        div1.add(tableCatalog);
        doc.add(div1);
    }


    static class Component {
        public static Paragraph getCatelogDottedLine(int type) {
            Paragraph element;
            if (type == 1) {
                element = new Paragraph("-----------------------------------------------------------------------------------------------");
            }else{
                element = new Paragraph("------------------------------------------------------------------------");
            }
            element.setProperty(Property.OVERFLOW_X, OverflowPropertyValue.HIDDEN);
            return element;
        }

        public static Cell getDefaultCell() {
            return new Cell().addStyle(GenoReportStyle.getDefaultCell());
        }

        public static Cell getCatelogCell() {
            return new Cell().addStyle(GenoReportStyle.getCatelogCell());
        }

        public static Cell getCatelogCell(int colsapn) {
            return new Cell(1, colsapn).addStyle(GenoReportStyle.getCatelogCell());
        }

        public static Cell getSignCell() {
            return new Cell().addStyle(GenoReportStyle.getSignCell());
        }

        public static Paragraph getSignParagraph(String text) {
            return new Paragraph(text).addStyle(GenoReportStyle.getSignStyle());
        }
    }

    static class GenoReportStyle {

        public static Style getTitleStyle() {
            Style style = new Style();
            style.setFontSize(16);
            style.setBold();
            style.setMarginLeft(3);
            style.setFontColor(GenoReportStyle.getThemeColor());
            return style;
        }

        public static Style getSecondTitleStyle() {
            Style style = new Style();
            style.setFontSize(14);
            style.setFontColor(GenoReportStyle.getThemeColor());
            return style;
        }

        public static Style getIconStyle() {
            Style style = new Style();
            style.setHeight(18);
            style.setWidth(15);
            style.setMarginBottom(-2);
            return style;
        }

        public static Color getThemeColor() {
            return new DeviceRgb(37, 98, 206);
        }

        public static Style getDefaultCell() {
            Style style = new Style();
            style.setPadding(10);
            style.setBorder(Border.NO_BORDER);
            return style;
        }

        public static Style getCatelogCell() {
            Style style = new Style();
            style.setBorder(Border.NO_BORDER);
            style.setVerticalAlignment(VerticalAlignment.MIDDLE);
            return style;
        }

        public static Style getDefaultTableOuter() {
            Style style = new Style();
            style.setBorder(new SolidBorder(GenoReportStyle.getThemeColor(), 1));
            style.setPaddings(10, 20, 10, 20);
            return style;
        }

        public static Style getSignCell() {
            Style style = new Style();
            style.setBorder(Border.NO_BORDER);
            return style;
        }

        public static Style getSignStyle() {
            Style style = new Style();
            style.setFontSize(11);
            return style;
        }
    }

}
