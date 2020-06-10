package com.onegene.pdf.component.report.drug;

import cn.hutool.core.date.DateUtil;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.onegene.pdf.component.AbstractReportBuilder;
import com.onegene.pdf.component.IReportBuilder;
import com.onegene.pdf.component.Painting;
import com.onegene.pdf.component.entity.ReportBean;
import com.onegene.pdf.component.report.gene.GenoColor;
import com.onegene.pdf.component.report.gene.GenoComponent;
import com.onegene.pdf.component.report.gene.GenoReportBuilder;
import com.onegene.pdf.component.report.gene.GenoStyle;

import java.util.Date;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/6/10 11:03
 **/
public class AdultDrugReportBuilder extends AbstractReportBuilder {
    @Override
    public IReportBuilder addIndex() {
        Image indexImage = new Image(ImageDataFactory.create(AdultDrugReportBuilder.class.getClassLoader().getResource("image/gene/成人纸质报告-54.png")));
        indexImage.setMargins(-50, -60, -60, -60);

        doc.add(indexImage);
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        return this;
    }

    @Override
    public IReportBuilder addHello() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        ReportBean.IndexBean index = reportBean.getIndex();

        Painting painting = new Painting(pdf, font);
        painting.drawHello("image/drug/resultRemarks.png");
        painting.close();

        Div div = new Div();
        div.setWidth(UnitValue.createPercentValue(100));
        div.setHeight(UnitValue.createPercentValue(100));
        div.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph p1 = new Paragraph();
        p1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        p1.setMaxWidth(UnitValue.createPercentValue(75));
        p1.setMarginTop(100f);
        p1.setCharacterSpacing(0.4f);
        Style large = new Style();
        large.setFontSize(22);
        large.setFontColor(GenoColor.getThemeColor());
        p1.add(new Text("尊敬的").addStyle(large));
        p1.add(new Text(index.getName()).addStyle(large).setBorderBottom(new SolidBorder(0.5f)));
        p1.add(new Text(" " + ("FEMALE".equals(index.getGender()) ? "女士" : "MALE".equals(index.getGender()) ? "先生" : "") + "：\n").addStyle(large));
        p1.add(new Text("您好！\n"));
        p1.add(new Text("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0衷心感谢您对我们的信任，选择用药安全基因检测服务!\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0当您收到这份报告书的时候，我们已经根据您的需求，秉持科学、专业、严谨和保密的态度为您完成了本次基因检测，并根据您的个人特有基因检测结果进行了全面深入的分析。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0据统计，我国每年有5000万的住院病人，其中至少250万与药物不良反应有关，20多万人因此而死亡。我国住院病人中有20%是由于不合理用药造成的二次住院。而随着我国经济发展及老龄化趋势，我国三高等慢性病患者已超过3亿人，都面临着长期用药的危险。因此，临床用药安全已经成为我国一个重大难点和关注焦点。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0用药安全基因检测采用美国精准医疗先进理念，通过检测受检者基因信息，结合药物遗传学、药物基因组学等前沿科学研究成果，全面分析受检者对不同药物的代谢能力和敏感性，为受检者生病后的精准用药提供理论依据和实际指导。人体对药物的反应受到基因和环境等因素的共同影响，基因检测的最⼤意义在于挖掘受检者遗传信息中隐藏的用药风险，让受检者在日常用药过程中远离各种雷区，真正做到用对药、用准药。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0用药安全基因检测涵盖了大部分临床常⽤药物体系，综合打造⼀份只属于您个人的个性化用药建议，帮助您安全、高效击败各类疾病，享受健康生活。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0希望本次检测能为您带来舒适满意的体验，针对本次检测，如果您有任何疑问需要解答，敬请拨打我们的健康热线400-163-5588，我们恭候您的来电。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0壹基因衷心祝愿您身体健康，生活快乐！"));
        div.add(p1);

        doc.add(div);
        return this;
    }

    @Override
    public IReportBuilder addExaminee() {
        ReportBean.IndexBean index = reportBean.getIndex();

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Painting painting = new Painting(pdf, font);
        painting.drawHello("image/drug/person_result.png",-40);
        painting.close();

        Div div1 = new Div();
        div1.setMargins(100, 20, 0, 20);
        Paragraph p1 = new Paragraph();
        p1.add(new Paragraph("受检人信息").addStyle(GenoStyle.getSecondTitleStyle()));
        div1.add(p1);
        Paragraph p2 = new Paragraph();
        p2.add(new Text("姓名：" + index.getName() + "\n"));
        p2.add(new Text("性别： " + ("FEMALE".equals(index.getGender()) ? "女" : "MALE".equals(index.getGender()) ? "男" : "") + "\n"));
        p2.add(new Text("年龄：" + (DateUtil.year(new Date()) - index.getBirthYear()) + "\n"));
        p2.add(new Text("样本编号：" + index.getSampleCode() + "\n"));
        p2.add(new Text("检测项目：" + index.getPackageName() + "\n"));
        p2.add(new Text("样本检测结果：合格\n"));
        p2.add(new Text("报告日期：" + (DateUtil.format(new Date(), "yyyy年M月d日")) + "\n"));
        div1.add(p2);

        Paragraph p3 = new Paragraph();
        p3.setMarginTop(50);
        p3.add(new Text("用户须知\n").addStyle(GenoStyle.getSecondTitleStyle()));
        p3.add(new Text("1、检测位点的筛选基于国际权威数据库（pharmGKB）、世界权威组织发布的指南（CPIC、" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0DPWG）及顶级期刊杂志上发表的研究成果；\n" +
                "2、鉴于基因与表型关联的复杂性及科研⽔平局限性，本次检测⽆法覆盖与检测项⽬相关的所" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0有位点；\n" +
                "3、数据库将随着检测⼈数增加和科学研究的深⼊定期升级优化；\n" +
                "4、本报告是以药物基因组学的⻆度推测受检者对各种药物的可能反应，但临床⽤药所需考虑" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0的影响因素复杂，因此本报告不能作为临床⽤药的唯⼀依据；\n" +
                "5、由于药物的选择和调整需具备临床专业知识，所以家⻓切忌依据报告结果擅⾃调整⽤药，\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0需将结果⻛险反馈给专业的医⽣或药剂师，遵医嘱⽤药。"));
        div1.add(p3);
        doc.add(div1);
        return this;
    }

    @Override
    public IReportBuilder addDetectionContent() {
        Div div2 = new Div();
        div2.setMarginTop(30);
        div2.setPaddingRight(18);
        Table signatureTable = new Table(2);
        signatureTable.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        signatureTable.setWidth(UnitValue.createPercentValue(50));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("检测人：舒平")));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("复核人：张萍")));
        signatureTable.startNewRow();
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("日期：" + (DateUtil.format(new Date(), "yyyy年M月d日")))));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("日期：" + (DateUtil.format(new Date(), "yyyy年M月d日")))));
        div2.add(signatureTable);

        Image iconImage01 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/gene/image-01.png")));
        iconImage01.setPaddingRight(18);
        iconImage01.setWidth(108);
        iconImage01.setHeight(108);
        iconImage01.setRelativePosition(292, 0, 60, 90);
        div2.add(iconImage01);
        doc.add(div2);
        return this;
    }

    @Override
    public IReportBuilder addResultSummary() {
        return null;
    }

    @Override
    public IReportBuilder addContext() {
        return null;
    }

    @Override
    public IReportBuilder addThanks() {
        return null;
    }

    @Override
    public IReportBuilder addBackCover() {
        return null;
    }

    @Override
    public IReportBuilder addCatalog() {
        return null;
    }

    @Override
    public IReportBuilder addPageNumber() {
        return null;
    }
}
