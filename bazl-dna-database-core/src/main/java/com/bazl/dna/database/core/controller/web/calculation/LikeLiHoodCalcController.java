package com.bazl.dna.database.core.controller.web.calculation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.algorithm.calculation.MarkerLRCalculation;
import com.bazl.dna.database.algorithm.calculation.ParentageCalculation;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.constants.GeneConstants;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.bo.LikeLiHoodCalcModel;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.*;
import com.bazl.dna.database.utils.ListStringUtils;
import com.bazl.dna.util.DataFormat;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Liuchang on 2020/6/1
 * 似然比计算方式控制层
 */
@RestController
@RequestMapping("/likeLiHood")
public class LikeLiHoodCalcController extends BaseController {

    @Autowired
    DnaSampleInfoService dnaSampleInfoService;

    @Autowired
    DnaYstrGeneInfoService dnaYstrGeneInfoService;

    @Autowired
    DnaStrGeneInfoService dnaStrGeneInfoService;

    @Autowired
    DnaMixGeneInfoService dnaMixGeneInfoService;

    @Autowired
    AlleleFrequencyInfoService frequencyInfoService;

    @Autowired
    DnaLocusInfoService dnaLocusInfoService;

    /**
     * 查看全部STR基因座信息--liuchang
     *
     * @return
     */
    @GetMapping(value = "/queryStrLocusInfo")
    public ResponseData queryStrLocusInfo() {
        try {
            List<DnaLocusInfo> strDnaLocusInfo = dnaLocusInfoService.listByLocusType("1");//查询STR基因座
            return new ResponseData(strDnaLocusInfo);
        } catch (Exception ex) {
            logger.error("invoke LikeLiHoodCalcController.queryStrLocusInfo error.", ex);
            return new ResponseData("查询str基因座信息异常！" + ex.getMessage());
        }
    }

    /**
     * 根据样本编号，基因类型查询基因分型信息数据--liuchang
     *
     * @param sampleNo
     * @param geneType
     * @return
     */
    @GetMapping(value = "/queryGeneInfo")
    public ResponseData queryGeneInfo(String sampleNo, String geneType) {
        try {
            if (sampleNo != null && geneType != null) {
                if (geneType.equals(Constants.GENE_TYPE_STR)) {                                  //STR基因分型
                    DnaSampleInfoVo geneInfo = dnaStrGeneInfoService.selectBySampleLabNo(sampleNo);
                    if (geneInfo != null && geneInfo.getGeneInfo() != null) {
                        String gene = GeneTransFormUtils.geneFormatList(geneInfo.getGeneInfo());//基因分型数据转换
                        geneInfo.setGeneInfo(gene);
                        return new ResponseData(geneInfo);
                    } else {
                        return new ResponseData("系统中没有该检材对应分型！");
                    }

                } else if (geneType.equals(Constants.GENE_TYPE_YSTR)) {
                    DnaSampleInfoVo geneInfo = dnaYstrGeneInfoService.selectBySampleLabNo(sampleNo); //YSTR基因分型
                    if (geneInfo.getGeneInfo() != null) {
                        String gene = GeneTransFormUtils.geneFormatList(geneInfo.getGeneInfo());//基因分型数据转换
                        geneInfo.setGeneInfo(gene);
                        return new ResponseData(geneInfo);
                    } else {
                        return new ResponseData("系统中没有该检材对应分型！");
                    }

                } else if (geneType.equals(Constants.GENE_TYPE_MIX)) {
                    DnaSampleInfoVo geneInfo = dnaMixGeneInfoService.selectBySampleLabNo(sampleNo);      //混合基因分型
                    if (geneInfo.getGeneInfo() != null) {
                        String gene = GeneTransFormUtils.geneFormatList(geneInfo.getGeneInfo());//基因分型数据转换
                        geneInfo.setGeneInfo(gene);
                        return new ResponseData(geneInfo);
                    } else {
                        return new ResponseData("系统中没有该检材对应分型!");
                    }
                }
                return new ResponseData();
            } else {
                return new ResponseData("系统中没有该检材对应分型!");
            }
        } catch (Exception ex) {
            logger.error("invole LikeLiHoodCalcController.queryGene error!", ex);
            return new ResponseData("查询基因分型数据异常！" + ex.getMessage());
        }
    }

    /**
     * 似然率管理-计算似然比率--liuchang
     *
     * @param likeLiHoodCalcModel
     * @return
     */
    @PostMapping(value = "/calcLikeLiHood")
    @SuppressWarnings("all")
    public ResponseData calcLikeLiHood(@RequestBody LikeLiHoodCalcModel likeLiHoodCalcModel) {
        //查询种群信息
        Map<String, Object> frequencyMap = Maps.newHashMap();
        if (likeLiHoodCalcModel.getPopulationFrequencyId() != 0) {
            frequencyMap.put("POPULATION_FREQUENCY_ID", likeLiHoodCalcModel.getPopulationFrequencyId());
        } else {
            frequencyMap.put("POPULATION_FREQUENCY_ID", 1);
        }
        List<AlleleFrequencyInfo> frequencyList = frequencyInfoService.listByMap(frequencyMap);
        try {
            //计算样本类型都是STR分型 sampleA和sampleB的似然比率
            if (likeLiHoodCalcModel.getStrSampleAGeneInfo() != null && likeLiHoodCalcModel.getStrSampleBGeneInfo() != null) {
                JSONObject json = new JSONObject();
                String sampleA = likeLiHoodCalcModel.getStrSampleAGeneInfo().getGeneInfo();//获取样本A分型数据
                String sampleB = likeLiHoodCalcModel.getStrSampleBGeneInfo().getGeneInfo();//获取样本B分型数据
                //转换去除空格,去除斜杠
                JSONObject sampleAJsonObject = JSONObject.parseObject(sampleA); //转对象样本A的分型为对象
                Set<String> sampleAKeys = sampleAJsonObject.keySet();           //获取sampleAKeys里面的所有key值
                JSONObject sampleBJsonObject = JSONObject.parseObject(sampleB);  //转对象样本B的分型为对象
                Set<String> sampleBKeys = sampleBJsonObject.keySet();             //获取sampleBKeys里面的所有Key值
                Sets.SetView<String> unionKeySet = Sets.union(sampleAKeys, sampleBKeys); //合集
                JSONArray array = new JSONArray(unionKeySet.size());
                //等位基因集合
                double totalLR = 1;
                json.put("alleleList", array);
                for (String key : sampleAKeys) {
                    JSONObject alleleJson = new JSONObject();
                    alleleJson.put("alleleKey", key);//等位基因key值：等位基因名称
                    alleleJson.put("alleleValueSampleA", sampleAJsonObject.getString(key));//等位基因value值:等位基因位点值
                    alleleJson.put("alleleValueSampleB", sampleBJsonObject.getString(key));//比中等位基因value值
                    alleleJson.put("LR", "");//似然率计算
                    if (sampleAJsonObject.getString(key) != null && sampleAJsonObject.getString(key).equals(sampleBJsonObject.getString(key))) {
                        String alleleValue = sampleAJsonObject.getString(key);
                        if (!"".equals(alleleValue)) {
                            String[] allele = StringUtils.split(alleleValue, "/");
                            if (allele.length > 1) {
                                Double lr = new MarkerLRCalculation(frequencyList).calculateSingleMarkerLR(key, allele[0], allele[1]);
                                if (Double.isNaN(lr)) {
                                        alleleJson.put("LR", "");
                                    }else{
                                    if (!key.equals("AMELOGENIN")) { //性别基因座不计算似然比
                                        String str = DataFormat.formatDecimal(lr == null ? 0.0 : lr, 3, 1, true);//转换科学计数法
                                        alleleJson.put("LR", str);  //似然比科学计数法转换
                                        totalLR = totalLR * lr;
                                      }
                                    }
				        		}
                        }else{
                            alleleJson.put("LR", "");
                        }
                    }
                    array.add(alleleJson);
                }
                if (Double.NaN == totalLR) {
                		totalLR = 0;
                }
                if (totalLR!=1) {
                    String totalStr = (DataFormat.formatDecimal(totalLR == 0 ? 0.0 : totalLR, 3, 1, true));//总计似然比科学计数法转换
                    json.put("totalLR",totalStr);
                }else {
                    json.put("totalLR","");
                }
                return new ResponseData(json.toJSONString());
            } else {
                return new ResponseData("fail");
            }
        } catch (Exception ex) {
            logger.error("invoke LikeLiHoodCalcController.calcLikeLiHood error", ex);
            return new ResponseData("计算似然比率异常！" + ex.getMessage());
        }
    }

    /**
     * 亲权指数管理-计算亲权指数  三联体计算，父或子，母或子--liuchang
     *
     * @param likeLiHoodCalcModel
     * @return
     */
    @PostMapping(value = "/parentalIndex")
    @SuppressWarnings("all")
    public ResponseData parentalIndex(@RequestBody LikeLiHoodCalcModel likeLiHoodCalcModel) {
        Map<String, Object> frequencyMap = Maps.newHashMap(); //查询种群信息
        if (likeLiHoodCalcModel.getPopulationFrequencyId() != 0) {
            frequencyMap.put("POPULATION_FREQUENCY_ID", likeLiHoodCalcModel.getPopulationFrequencyId());
        } else {
            frequencyMap.put("POPULATION_FREQUENCY_ID", 1);
        }
        List<AlleleFrequencyInfo> frequencyList = frequencyInfoService.listByMap(frequencyMap);
        if (likeLiHoodCalcModel.getSampleRelation() == 1) {   //样本关系为父亲 母亲 子三联体样本关系
            try {
                if (likeLiHoodCalcModel.getStrChildGeneInfo() != null && likeLiHoodCalcModel.getStrFatherGeneInfo() != null && likeLiHoodCalcModel.getStrMotherGeneInfo() != null) {
                    JSONObject json = new JSONObject();
                    //父亲基因分型信息
                    String sampleFather = likeLiHoodCalcModel.getStrFatherGeneInfo().getGeneInfo();
                    List<DnaGeneInfoDetail> fatherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatList(sampleFather), DnaGeneInfoDetail.class);
                    JSONObject sampleFatherJsonObject = JSONObject.parseObject(sampleFather); //转父亲的分型为对象
                    Set<String> sampleFatherKeys = sampleFatherJsonObject.keySet();

                    //母亲基因分型信息
                    String sampleMother = likeLiHoodCalcModel.getStrFatherGeneInfo().getGeneInfo();
                    List<DnaGeneInfoDetail> motherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatList(sampleMother), DnaGeneInfoDetail.class);
                    JSONObject sampleMotherJsonObject = JSONObject.parseObject(sampleMother); //转母亲的分型为对象
                    Set<String> sampleMotherKeys = sampleMotherJsonObject.keySet();

                    //孩子基因分型信息
                    String sampleChird = likeLiHoodCalcModel.getStrChildGeneInfo().getGeneInfo();
                    List<DnaGeneInfoDetail> chirdGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatList(sampleChird), DnaGeneInfoDetail.class);
                    JSONObject sampleChirdJsonObject = JSONObject.parseObject(sampleChird); //转孩子的分型为对象
                    Set<String> sampleChirdKeys = sampleChirdJsonObject.keySet();

                    Sets.SetView<String> unionKeySet = Sets.union(sampleChirdKeys, sampleFatherKeys);//(父亲和孩子)合集
                    Sets.SetView<String> unionKeySet2 = Sets.union(sampleChirdKeys, sampleMotherKeys);//(母亲和孩子)合集
                    Sets.SetView<String> unionKeySet3 = Sets.union(unionKeySet2, unionKeySet); //(父亲和母亲和孩子)合集
                    JSONArray array = new JSONArray(unionKeySet3.size());
                    //等位基因集合
                    json.put("alleleList", array);
                    double totalPI = 1;
                    for (String key : sampleFatherKeys) {
                        JSONObject alleleJson = new JSONObject();
                        alleleJson.put("alleleKey", key);//等位基因key值：等位基因名称
                        alleleJson.put("alleleValueSampleFather", sampleFatherJsonObject.getString(key));//等位基因value值:等位基因位点值
                        alleleJson.put("alleleValueSampleMother", sampleMotherJsonObject.getString(key));//等位基因value值:等位基因位点值
                        alleleJson.put("alleleValueSampleChird", sampleChirdJsonObject.getString(key));//等位基因value值:等位基因位点值
                        alleleJson.put("PI", "");//亲权指数计算
                        if (sampleFatherJsonObject.getString(key).equals(sampleChirdJsonObject.getString(key))) {
                            String alleleValue = sampleFatherJsonObject.getString(key);
                            List<String> fatherAlleleList = ListStringUtils.string2List(sampleFatherJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//父亲等位基因位点值
                            List<String> motherAlleleList = ListStringUtils.string2List(sampleMotherJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//母亲等位基因位点值
                            List<String> childAlleleList = ListStringUtils.string2List(sampleChirdJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//孩子等位基因位点值
                            if (!"".equals(alleleValue)) {
                                String[] allele = StringUtils.split(alleleValue, "/");
                                if (allele.length > 1) {
                                    if (!key.equals("AMELOGENIN")) { //性别基因座不计算似然比
                                        Double PI = (double) new ParentageCalculation(frequencyList).calculateFmz(key, fatherAlleleList, motherAlleleList, childAlleleList);
                                        if (Double.isNaN(PI)) {
                                            alleleJson.put("PI", "");
                                        } else {
                                            String str = DataFormat.formatDecimal(PI == null ? 0.0 : PI, 3, 1, true);//转换科学计数法
                                            alleleJson.put("PI", str);
                                            totalPI = totalPI * PI;//PI all
                                        }
                                    }
                                }
                            } else {
                                alleleJson.put("PI", "");
                            }
                        }
                        array.add(alleleJson);
                    }
                    if (Double.NaN == totalPI) {
                        totalPI = 0;
                    }
                    if (totalPI !=1) {
                        String totalStr = (DataFormat.formatDecimal(totalPI == 0 ? 0.0 : totalPI, 3, 1, true));//总计似然比科学计数法转换
                        json.put("totalPI",totalStr);//总计似然比科学计数法转换
                    }else{
                        json.put("totalPI","");//总计似然比科学计数法转换
                    }
                    return new ResponseData(json.toJSONString());
                }
            } catch (Exception ex) {
                logger.error("invoke LikeLiHoodCalcController. parentalIndex error", ex);
                return new ResponseData("计算三联体样本关系亲权指数异常！" + ex.getMessage());
            }

        } else if (likeLiHoodCalcModel.getSampleRelation() == 2) { //父或子计算亲权指数
            try {
                if (likeLiHoodCalcModel.getStrFatherGeneInfo() != null && likeLiHoodCalcModel.getStrChildGeneInfo() != null) {
                    JSONObject json = new JSONObject();
                    //父亲基因分型信息
                    String sampleFather = likeLiHoodCalcModel.getStrFatherGeneInfo().getGeneInfo();
                    JSONObject sampleFatherJsonObject = JSONObject.parseObject(sampleFather); //转父亲的分型为对象
                    Set<String> sampleFatherKeys = sampleFatherJsonObject.keySet();
                    //孩子基因分型信息
                    String sampleChird = likeLiHoodCalcModel.getStrChildGeneInfo().getGeneInfo();
                    JSONObject sampleChirdJsonObject = JSONObject.parseObject(sampleChird); //转孩子的分型为对象
                    Set<String> sampleChirdKeys = sampleChirdJsonObject.keySet();
                    Sets.SetView<String> unionKeySet = Sets.union(sampleChirdKeys, sampleFatherKeys); //(父亲和孩子)合集
                    JSONArray array = new JSONArray(unionKeySet.size());
                    json.put("alleleList", array);
                    //等位基因集合
                    double totalPI = 1;
                    for (String key : sampleFatherKeys) {
                        JSONObject alleleJson = new JSONObject();
                        alleleJson.put("alleleKey", key);//等位基因key值：等位基因名称
                        alleleJson.put("alleleValueSampleFather", sampleFatherJsonObject.getString(key));//父亲等位基因value值:等位基因位点值
                        alleleJson.put("alleleValueSampleChird", sampleChirdJsonObject.getString(key)); //孩子等位基因value值:等位基因位点值
                        alleleJson.put("PI", "");//亲权指数计算
                        if (sampleFatherJsonObject.getString(key).equals(sampleChirdJsonObject.getString(key))) {
                            String alleleValue = sampleFatherJsonObject.getString(key);
                            List<String> fatherAlleleList = ListStringUtils.string2List(sampleFatherJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//父亲等位基因位点值
                            List<String> childAlleleList = ListStringUtils.string2List(sampleChirdJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//孩子等位基因位点值
                            if (!"".equals(alleleValue)) {
                                String[] allele = StringUtils.split(alleleValue, "/");

                                if (allele.length > 1) {
                                    if (!key.equals("AMELOGENIN")) { //性别基因座不计算似然比
                                        Double PI = (Double) new ParentageCalculation(frequencyList).calculateFz(key, fatherAlleleList, childAlleleList);
                                        if (Double.isNaN(PI)) {
                                            alleleJson.put("PI", "");
                                        } else {
                                            String str = DataFormat.formatDecimal(PI == null ? 0.0 : PI, 3, 1, true);//转换科学计数法
                                            alleleJson.put("PI", str);
                                            totalPI = totalPI * PI;
                                        }
                                    }
                                }
                            } else {
                                alleleJson.put("PI", "");
                            }
                        }
                        array.add(alleleJson);
                    }
                    if (Double.NaN == totalPI) {
                        totalPI = 0;
                    }
                    if (totalPI !=1) {
                        String totalStr = (DataFormat.formatDecimal(totalPI == 0 ? 0.0 : totalPI, 3, 1, true));//总计似然比科学计数法转换
                        json.put("totalPI",totalStr);
                    }else{
                        json.put("totalPI","");
                    }
                    return new ResponseData(json.toJSONString());
                }
            } catch (Exception ex) {
                logger.error("invoke LikeLiHoodCalcController.parentalIndex error", ex);
                return new ResponseData("计算父子样本关系亲权指数异常！" + ex.getMessage());
            }
        } else if (likeLiHoodCalcModel.getSampleRelation() == 3) { //母或子计算亲权指数
            try {
                if (likeLiHoodCalcModel.getStrMotherGeneInfo() != null && likeLiHoodCalcModel.getStrChildGeneInfo() != null) {
                    JSONObject json = new JSONObject();

                    String sampleMother = likeLiHoodCalcModel.getStrMotherGeneInfo().getGeneInfo();//母亲基因分型信息
                    JSONObject sampleMotherJsonObject = JSONObject.parseObject(sampleMother); //转母亲的分型为对象
                    Set<String> sampleMotherKeys = sampleMotherJsonObject.keySet();

                    String sampleChird = likeLiHoodCalcModel.getStrChildGeneInfo().getGeneInfo(); //孩子基因分型信息
                    JSONObject sampleChirdJsonObject = JSONObject.parseObject(sampleChird); //转孩子的分型为对象
                    Set<String> sampleChirdKeys = sampleChirdJsonObject.keySet();

                    Sets.SetView<String> unionKeySet = Sets.union(sampleChirdKeys, sampleMotherKeys); //(母亲和孩子)合集
                    JSONArray array = new JSONArray(unionKeySet.size());
                    //等位基因集合
                    double totalPI = 1;
                    json.put("alleleList", array);
                    for (String key : sampleMotherKeys) {
                        JSONObject alleleJson = new JSONObject();
                        alleleJson.put("alleleKey", key);//等位基因key值：等位基因名称
                        alleleJson.put("alleleValueSampleChird", sampleChirdJsonObject.getString(key));//等位基因value值:等位基因位点值
                        alleleJson.put("PI", "");//亲权指数计算
                        if (sampleMotherJsonObject.getString(key).equals(sampleChirdJsonObject.getString(key))) {
                            String alleleValue = sampleMotherJsonObject.getString(key);
                            List<String> childAlleleList = ListStringUtils.string2List(sampleChirdJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//孩子等位基因位点值
                            List<String> motherAlleleList = ListStringUtils.string2List(sampleMotherJsonObject.getString(key), GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);//母亲等位基因位点值
                            if (!"".equals(alleleValue)) {
                                String[] allele = StringUtils.split(alleleValue, "/");
                                if (allele.length > 1) {
                                    if (!key.equals("AMELOGENIN")) { //性别基因座不计算似然比
                                        Double PI = (Double) new ParentageCalculation(frequencyList).calculateMz(key, motherAlleleList, childAlleleList);
                                        if (Double.isNaN(PI)) {
                                            alleleJson.put("PI", "");
                                        } else {
                                            String str = DataFormat.formatDecimal(PI == null ? 0.0 : PI, 3, 1, true);//转换科学计数法
                                            alleleJson.put("PI", str);
                                            totalPI = totalPI * PI;
                                        }
                                    }
                                }
                            } else {
                                alleleJson.put("PI", "");
                            }
                        }
                        array.add(alleleJson);
                    }
                    if (Double.NaN == totalPI) {
                        totalPI = 0;
                    }
                    if (totalPI!=1) {
                        String totalStr = (DataFormat.formatDecimal(totalPI == 0 ? 0.0 : totalPI, 3, 1, true));//总计似然比科学计数法转换
                        json.put("totalPI", totalStr);//总计似然比
                    }else {
                        json.put("totalPI", "");//总计似然比
                    }
                    return new ResponseData(json.toJSONString());
                }
            } catch (Exception ex) {
                logger.error("invoke LikeLiHoodCalcController.parentalIndex error", ex);
                return new ResponseData("计算母子样本关系亲权指数异常！" + ex.getMessage());
            }
        }
        return new ResponseData();
    }
}
