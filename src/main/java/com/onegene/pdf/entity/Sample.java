package com.onegene.pdf.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Sample {

    public enum Analysed{
        /**
         * 0-未上传或授权重传数据了 1-已上传分析数据 2-自动重新生成报告（用户重新请求生成报告或系统不繁忙时自动生成报告） 3-不存在分析数据
         */
        NO(0),YES(1),AUTO(2),NOT_EXIST(3);

        private Integer val;

        Analysed(Integer val) {
            this.val = val;
        }

        public Integer val() {
            return val;
        }
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long csId;

    /**
     * 是否授权重传数据
     */
    private Integer analysed;

    /**
     * 检测套餐名称
     */
    @Column(name = "package_name")
    private String packageName;

    private String uuid;

    private String goodsName;

    private Integer goodId;

    private Integer productId;

    /**
     * 条形码（样本编号）
     */
    @Column(name = "bar_code")
    private String barCode;

    /**
     * 检测类型id
     */
    @Column(name = "panel_id")
    private Long panelId;

    /**
     * 套餐id
     */
    @Column(name = "package_id")
    private Long packageId;

    /**
     * 订单编号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 受检人id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 受检人姓名
     */
    private String name;

    /**
     * 受检人性别
     */
    private Integer sex;

    /**
     * 1 可以解锁 2不可以
     */
    private Integer unlockState;

    /**
     * 受检人出生年月
     */
    private Date birthDate;
    /**
     * 报告地址
     */
    private String reporting;

    /**
     * 受检人手机号
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 样本订单来源 1-壹号系统 2-有赞
     */
    private Integer source;


    /**
     * 状态 1-待寄回 2-待收样 3-检测中 4-待发送
     */
    private Integer state;

    /**
     * 受检人id
     */
    @Column(name = "examinee_id")
    private Long examineeId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 渠道Id
     */
    private Long channelId;

}