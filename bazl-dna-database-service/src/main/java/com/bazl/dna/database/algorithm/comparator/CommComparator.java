package com.bazl.dna.database.algorithm.comparator;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 通用比对类
 * Created by lizhihua on 2020-03-10.
 */
public abstract class CommComparator {

    protected static Logger logger = LoggerFactory.getLogger(CommComparator.class);


    /**
     * 从指定基因信息集合中查找相同基因座名称的对象
     * @param markerName
     * @param geneInfoDetailList
     * @return
     */
    protected static DnaGeneInfoDetail findSameNameLocus(String markerName, List<DnaGeneInfoDetail> geneInfoDetailList){
        try {
            return geneInfoDetailList.stream().filter(tar -> tar.getName().equalsIgnoreCase(markerName)).findFirst().get();
        }catch(Exception ex){
            return null;
        }
    }


    /**
     * 将基因字符串转换为Map类型
     * @return
     */
    protected static JSONObject convertGeneInfoStrToMap(String geneInfoStr) throws Exception{
        JSONObject obj = JSONObject.parseObject(geneInfoStr, Feature.OrderedField);
        /*
        Map<String, List<String>> geneInfoMap = null;
        try {
            geneInfoMap =(Map) JSON.parse(geneInfoStr);
        } catch (Exception ex) {
            throw new Exception("解析样本基因信息错误!");
        }
        */
        return obj;
    }
}
