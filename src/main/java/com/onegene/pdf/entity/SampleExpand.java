package com.onegene.pdf.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sample_expand")
@Data
public class SampleExpand {

    public enum ShowType {
        /**
         * 展示报告类型 1-默认单一类型 2-易感和用药
         */
        ONE(1),GENE_DRUG(2);
        private Integer val;

        ShowType(Integer val) {
            this.val = val;
        }

        public Integer val() {
            return val;
        }
    }

    public enum PdfState {
        /**
         * pdf状态 0-未生成 1-已生成 2-生成失败
         */
        NO(0),YES(1),FAIL(2);
        private Integer val;

        PdfState(Integer val) {
            this.val = val;
        }

        public Integer val() {
            return val;
        }
    }

    @Id
    private Long id;

    /**
     * 样本id
     */
    @Column(name = "sample_id")
    private Long sampleId;


    private String panelName;

    /**
     * 是否可以查看报告字符串 1是 2否
     */
    @Column(name = "report_state")
    private Integer reportState;

    /**
     * 预不合格 0-合格 1-不合格
     */
    @Column(name = "pre_qualified")
    private Integer preQualified;

    /**
     * 是否包邮 1包 2不包
     */
    @Column(name = "mail_state")
    private Integer mailState;

    private String creator;

    private Date created;

    private String modifier;

    private Date modified;

    private String remark;
    private Long channelId;

    /**
     *  实验室编号
     */
    @Column(name = "laboratory_no")
    private String laboratoryNo;

    /**
     * 物流公司 1-顺丰速递 2-京东物流  3-圆通  4-中通 5-EMS 6-天天快递 7-其他
     */
    @Column(name = "logistic_company")
    private Integer logisticCompany;

    /**
     * 物流编号
     */
    @Column(name = "logistic_number")
    private String logisticNumber;


    /**
     * 是否赠送
     */
    private Integer spare2;


    private Integer place;

    private Integer beCollection;

    private Date collectionTime;

    /**
     * 是否生成另一份报告
     */
    private Integer hasOther;

    /**
     * pdf状态 0-未生成 1-已生成 2-生成失败
     */
    private Integer pdfState;
}