package com.bazl.dna.database.nation.converter.task;

import java.util.ArrayList;
import java.util.List;

import com.bazl.dna.database.nation.converter.model.po.SysDict;

/**
 * Created by Administrator on 2020-01-29.
 */
public class StaticPages {

    public static List<SysDict> allDictList = null;

    public static List<String> strLocusNameList = null;

    public static List<String> ystrLocusNameList = null;


    /**
     * 存储已经或正在进行中的页码
     * key: pageNo
     */
    public static List<String> processingPageList = new ArrayList<>();

    /**
     * 正在处理中的案件委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingCaseList = new ArrayList<>();

    /**
     * 正在处理中的身份不明人员委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingUnknownPersonList = new ArrayList<>();

    /**
     * 正在处理中的失踪人口委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingMissingPersonList = new ArrayList<>();

    /**
     * 正在处理中的建库委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingCriminalPersonList = new ArrayList<>();

    /**
     * 正在处理中的灾难委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingDisasterPersonList = new ArrayList<>();


    /**
     * 正在处理中的质控委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingQualityPersonList = new ArrayList<>();


    /**
     * 正在处理中的基础对象委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingBasicPersonList = new ArrayList<>();


    /**
     * 正在处理中的打拐儿童对象委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingAbductionChildList = new ArrayList<>();


    /**
     * 正在处理中的打拐父母对象委托数据转换
     * key: initServerNo前4位
     */
    public static List<String> processingAbductionParentList = new ArrayList<>();

}
