package com.bazl.dna.mix.compare.algorithm.comparator;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.bazl.dna.mix.compare.algorithm.model.DnaGeneInfoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * 通用比对类
 * @author lizhihua on 2020-03-10.
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
            Optional<DnaGeneInfoDetail> optional = geneInfoDetailList.stream().filter(tar -> tar.getName().equalsIgnoreCase(markerName)).findFirst();
            if(optional.isPresent()){
                return optional.get();
            }
        }catch(Exception ex){
            logger.error("inovke CommComparator.findSameNameLocus error.", ex);
        }

        return null;
    }


    /**
     * 将基因字符串转换为Map类型
     * @return
     */
    protected static JSONObject convertGeneInfoStrToMap(String geneInfoStr) throws Exception{
        //JSONObject obj = JSONObject.parseObject(geneInfoStr, Feature.OrderedField);
        return JSONObject.parseObject(geneInfoStr, Feature.OrderedField);
    }
}
