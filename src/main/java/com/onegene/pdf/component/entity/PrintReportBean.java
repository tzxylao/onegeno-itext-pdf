package com.onegene.pdf.component.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: laoliangliang
 * @description: 线下pdf报告实体类
 * @create: 2019/12/24 16:57
 **/
@Data
@NoArgsConstructor
public class PrintReportBean {

    private ReportBean.IndexBean index;
    private List<ReportBean.CategoriesBean> categories;
    private Map<String, ReportBean.ItemsBean> items;

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

        private String sampleCode;
        private String name;
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
        private List<ReportBean.IndexBean.TagBean> goodTags;
        private List<ReportBean.IndexBean.TagBean> badTags;
        private List<ReportBean.IndexBean.TagBean> normalTags;
        private List<?> recommend;
        private List<ReportBean.IndexBean.CategoriesBean> categories;
        private List<ReportBean.CategoriesBean.ItemsBean> reduceWeightTags;
        private List<ReportBean.CategoriesBean.ItemsBean> cosmetologyTags;
        private List<ReportBean.CategoriesBean.ItemsBean> characterTags;
        private List<ReportBean.CategoriesBean.ItemsBean> specialityTags;
        private List<ReportBean.CategoriesBean.ItemsBean> nutritionTags;
        private List<ReportBean.CategoriesBean.ItemsBean> diseaseTags;
        private List<ReportBean.CategoriesBean.ItemsBean> talentTags;
        private List<ReportBean.CategoriesBean.ItemsBean> eqiqTags;

        @NoArgsConstructor
        @Data
        public static class TagBean {
            private String code;
            private String label;
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

            private String code;
            private String name;
            private String icon;
            private String image;
            private Object weighting;
            private int analysisItems;
            private int unlockedItems;
        }
    }

    @NoArgsConstructor
    @Data
    public static class CategoriesBean {

        @NoArgsConstructor
        @Data
        public static class CategoryItem {

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

            private String sampleCode;
            private String gender;
            private String code;
            private String name;
            private String icon;
            private String image;
            private Object weighting;
            private int analysisItems;
            private int unlockedItems;
            private List<ReportBean.CategoriesBean.ItemsBean> items;

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
                private int index;
                private int level;
                private String label;
                private String description;
                private String score;
                private double tagScore;
                private boolean recommend;
                private boolean locked;
                private String direction;
                private String rank;
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class ItemsBean {
        @NoArgsConstructor
        @Data
        public static class ItemBean {

            private String sampleCode;
            private String gender;
            private String categoryCode;
            private String categoryName;
            private String code;
            private String name;
            private String icon;
            private String image;
            private int index;
            private int level;
            private String label;
            private String description;
            private Double score;
            private double tagScore;
            private boolean recommend;
            private boolean locked;
            private String direction;
            private String introduce;
            private String rank;
            private List<ReportBean.ItemsBean.LevelsBean> levels;
            private List<ReportBean.ItemsBean.NormalLocusBean> normalLocus;
            private List<ReportBean.ItemsBean.MutationLocusBean> mutationLocus;
            private List<ReportBean.ItemsBean.ContentsBean> contents;
            private List<ReportBean.ItemsBean.LiteraturesBean> literatures;
            private List<ReportBean.ItemsBean.GeneDescBean> geneDesc;

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
            }
        }
    }
}
