package com.rkylin.risk.core.dto;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.entity.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by lina on 2016-6-20.
 */
@Setter
@Getter
@ToString
public class LogicRuleBean implements Entity {

    /*
    遍布省份数量
     */
    private Amount provincenum;

    /*
    分支机构数量
     */
    private Amount subagencynum;

    /*
    年培训人次
     */
    private Amount trainingnumyear;

    /*
    年培训收入
     */
    private Amount trainingincomeyear;

    /*
    成立年限
     */
    private Amount foundage;

    /*
    教学总面积
     */
    private Amount areateaching;

    /**
     * 身份证号码位数
     */
    private Amount cerno;
    /**
     * 年龄
     */
    private Amount age;

    /**
     * 学历
     */
    private String degree;

    /**
     * 籍贯是否为大陆人士
     */
    private String isCardNum;

    /**
     * 课程名称是否符合
     */
    private String isCourseName;

    /**
     * 课程类别
     */
    private String courseClass;

    /**
     * 课程单价比例
     */
    private Amount coursePriceRate;
    /**
     * 课程单价/价格区间最大值比率
     */
    private Amount coursePriceMaxRate;
    /**
     * 连续三日日放款额/日培训收入
     */
    private Amount dayPayRate;
    /**
     * 连续三日日放款额/日培训收入1
     */
    private Amount dayPayRate1;
    /**
     * 连续三日日放款额/日培训收入2
     */
    private Amount dayPayRate2;

    /**
     * 月放款额/月培训收入
     */
    private Amount monthPayRate;

    /**
     * 季放款额/季培训收入
     */
    private Amount seasonPayRate;

    /**
     * 年放款额/年培训收入
     */
    private Amount yearPayRate;

    /**
     * 日放款额连续7日日增长率
     */
    private Amount dayPayGrowthRate;
    /**
     * 日放款额连续7日日增长率1
     */
    private Amount dayPayGrowthRate1;
    /**
     * 日放款额连续7日日增长率2
     */
    private Amount dayPayGrowthRate2;
    /**
     * 日放款额连续7日日增长率3
     */
    private Amount dayPayGrowthRate3;
    /**
     * 日放款额连续7日日增长率4
     */
    private Amount dayPayGrowthRate4;
    /**
     * 日放款额连续7日日增长率5
     */
    private Amount dayPayGrowthRate5;
    /**
     * 日放款额连续7日日增长率6
     */
    private Amount dayPayGrowthRate6;

    /**
     * 连续三日日放款人数/日培训人数
     */
    private Amount dayNumRate;
    /**
     * 连续三日日放款人数/日培训人数1
     */
    private Amount dayNumRate1;
    /**
     * 连续三日日放款人数/日培训人数2
     */
    private Amount dayNumRate2;

    /**
     * 月放款人数/月培训人数
     */
    private Amount monthNumRate;

    /**
     * 季放款人数/季培训人数
     */
    private Amount seasonNumRate;

    /**
     * 年放款人数/年培训人数
     */
    private Amount yearNumRate;

    /**
     * 日放款人数连续7日日增长率
     */
    private Amount dayNumGrowthRate;
    /**
     * 日放款人数连续7日日增长率1
     */
    private Amount dayNumGrowthRate1;
    /**
     * 日放款人数连续7日日增长率2
     */
    private Amount dayNumGrowthRate2;
    /**
     * 日放款人数连续7日日增长率3
     */
    private Amount dayNumGrowthRate3;
    /**
     * 日放款人数连续7日日增长率4
     */
    private Amount dayNumGrowthRate4;
    /**
     * 日放款人数连续7日日增长率5
     */
    private Amount dayNumGrowthRate5;
    /**
     * 日放款人数连续7日日增长率6
     */
    private Amount dayNumGrowthRate6;

    /**
     * 逾期率
     */
    private Amount overdueRate;


}
