package com.onegene.pdf.component.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: laoliangliang
 * @description: 报告实体类
 * @create: 2019/12/16 15:20
 **/
@NoArgsConstructor
@Data
public class ReportBean {

    private IndexBean index;
    private Map<String, CategoriesBean> categories;
    private Map<String, ItemsBean> items;

    @NoArgsConstructor
    @Data
    public static class IndexBean {
        /**
         * sampleCode :
         * name :
         * gender : gender
         * birthYear : birthYear
         * totalScore : 0
         * peopleRatio : 0
         * analysisItems : 0
         * unlockedItems : 0
         * scores : {}
         * goodTags : []
         * badTags : []
         * normalTags : []
         * recommend : []
         * categories : []
         * reduceWeightTags : []
         * cosmetologyTags : []
         * characterTags : []
         * specialityTags : []
         * nutritionTags : []
         * diseaseTags : []
         * talentTags : []
         * eqiqTags : []
         */
        /**
         * 1 可以解锁 2 不可以
         */
        private Integer unlockState;
        private String sampleCode;
        private Long sampleId;
        private String phone;
        private String csQrCode;
        private String wxQrCode;
        private Long csId;
        private String unencCode;
        private String name;
        private String productName;
        private String packageName;
        private Integer examineeBirthYear;
        private String receivedTime;
        private String reportedTime;
        private String stime;
        private String bloodType;
        private String gender;
        private Integer birthYear;
        private Double totalScore;
        private BigDecimal peopleRatio;
        private BigDecimal peopleRatioBe;
        private BigDecimal peopleRatioLw;
        private int analysisItems;
        private int unlockedItems;
        private Map<String, Double> scores;
        private List<TagBean> avoidUseTags;
        private List<TagBean> carefulUseTags;
        private List<TagBean> goodTags;
        private List<TagBean> badTags;
        private List<TagBean> normalTags;
        private List<Recommend> recommend;
        private List<CategoriesBean> categories;
        private List<ReportBean.CategoriesBean.ItemsBean> reduceWeightTags;
        private List<ReportBean.CategoriesBean.ItemsBean> cosmetologyTags;
        private List<ReportBean.CategoriesBean.ItemsBean> characterTags;
        private List<ReportBean.CategoriesBean.ItemsBean> specialityTags;
        private List<ReportBean.CategoriesBean.ItemsBean> nutritionTags;
        private List<ReportBean.CategoriesBean.ItemsBean> diseaseTags;
        private List<ReportBean.CategoriesBean.ItemsBean> talentTags;
        private List<ReportBean.CategoriesBean.ItemsBean> eqiqTags;
        private List<ReportBean.CategoriesBean.ItemsBean> allUnlockedItems;
        private List<ReportBean.CategoriesBean.ItemsBean> attentionItems;
        private Map<String, Attention> attentionCategories;

        @NoArgsConstructor
        @Data
        public static class Attention {
            private String code;
            private String icon;
            private String name;
            private String image;
            private Integer analysisItems;
            private Integer unlockedItems;
            private Integer attentionItems;
            private List<ReportBean.CategoriesBean.ItemsBean> items;
        }

        @NoArgsConstructor
        @Data
        public static class Recommend {
            private String code;
            private String baseImage;
            private String name;
            private Integer weighting;
            private Integer items;
        }

        @NoArgsConstructor
        @Data
        public static class TagBean {
            private String code;
            private String label;
            private String name;
        }

        @Data
        @NoArgsConstructor
        public static class CategoriesBean {

            /**
             * code : wss
             * name : 维生素
             * icon :
             * image :
             * weighting : null
             * analysisItems : 10
             * unlockedItems : 0
             */
            private Integer categoryId;
            private String code;
            private String name;
            private String icon;
            private String image;
            private Integer rank;
            private String baseImage;
            private Integer weighting;
            private int analysisItems;
            private int unlockedItems;
            private List<ReportBean.CategoriesBean.ItemsBean> items;
        }
    }

    @NoArgsConstructor
    @Data
    public static class CategoriesBean {
        /**
         * sampleCode : 100600304817705
         * gender : FEMALE
         * items : [{"code":"wssA","name":"维生素A","icon":"","image":"","index":2,"level":2,"label":"需求量正常","description":"维生素A需求量正常","score":"0.9999999854315329","tag_score":0,"recommend":true,"locked":true,"direction":"both"}]
         * code : wss
         * name : 维生素
         * icon :
         * image :
         * weighting : null
         * analysisItems : 10
         * unlockedItems : 0
         */
        private Integer categoryId;
        private String sampleCode;
        private String gender;
        private String code;
        private String name;
        private String icon;
        private String image;
        private String baseImage;
        private Integer rank;
        private Integer weighting;
        private int analysisItems;
        private int unlockedItems;
        private List<ItemsBean> items;

        @NoArgsConstructor
        @Data
        public static class ItemsBean {
            /**
             * code : wssA
             * name : 维生素A
             * icon :
             * image :
             * index : 2
             * level : 2
             * label : 需求量正常
             * description : 维生素A需求量正常
             * score : 0.9999999854315329
             * tag_score : 0
             * recommend : true
             * locked : true
             * direction : both
             */
            private String code;
            private String name;
            private String icon;
            private String image;
            private Integer index;
            private String level;
            private String label;
            private String description;
            private float score;
            private float tagScore;
            private Boolean recommend;
            private Boolean locked;
            private String direction;
            private Integer rank;
            private BigDecimal price;
            private Long itemId;
            /**
             * 订单状态 1-代付款 2-待解锁 3-已解锁
             */
            private Integer state;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ItemsBean {
        private ItemsBean nextItem;
        private String sampleCode;
        private String gender;
        private String categoryCode;
        private String categoryName;
        private String code;
        private String name;
        private String icon;
        private String image;
        private int index;
        private String level;
        private String label;
        private String description;
        private float score;
        private Double tagScore;
        private boolean recommend;
        private Boolean locked;
        private String direction;
        private String introduce;
        private Integer rank;
        private String enName;
        private String suitableAge;
        private String csQrCode;
        private String wxQrCode;
        private Long csId;
        private BigDecimal price;
        private Long itemId;
        private List<LevelsBean> levels;
        private List<NormalLocusBean> normalLocus;
        private List<MutationLocusBean> mutationLocus;
        private List<ContentsBean> contents;
        private List<LiteraturesBean> literatures;
        private List<GeneDescBean> geneDesc;

        @NoArgsConstructor
        @Data
        public static class LevelsBean {
            private int percentage;
            private double right;
            private int left;
            private String description;
            private String label;
        }

        @NoArgsConstructor
        @Data
        public static class NormalLocusBean {
            private String og_id;
            private String genotype;
            private String lebel;
            private String ref_genotype;
        }

        @NoArgsConstructor
        @Data
        public static class MutationLocusBean {
            private String og_id;
            private String genotype;
            private String lebel;
            private Integer risk_level;
            private String ref_genotype;
            private Integer relativeValue;
        }

        @NoArgsConstructor
        @Data
        public static class ContentsBean {
            private String label;
            private String value;
        }

        @NoArgsConstructor
        @Data
        public static class LiteraturesBean {
            private String _id;
            private String title;
            private String journal;
            private String serialNumber;
            private String page;
            private String author;
            private String attachment;
            private boolean enabled;
            private int __v;
            private List<?> editLogs;
            private List<String> tags;
        }

        @NoArgsConstructor
        @Data
        public static class GeneDescBean {
            private String og_id;
            private String gene_code;
            private List<String> geneList;
            private String genotype;
            private String ref_genotype;
            private String label;
            private String describe;
            private Integer risk_level;
            private Integer relativeValue;
            private String description;
        }
    }

}
