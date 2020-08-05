package com.bazl.dna.database.core.helper.codis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.utils.ListUtils;
import com.google.common.collect.Lists;

/**
 *@author wanghaiyang
 * @date 2019/5/14.
 */
public class CodisFileParser {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CodisFileParser.class);

    public static List<CodisQcModel> getDataCodisFile(InputStream inputStream, String fileName, String reagentName) throws Exception {
        if (fileName.toUpperCase().endsWith("DAT")) {
            return getDataDat(inputStream, reagentName);
        }

        if (fileName.toUpperCase().endsWith("TXT")) {
            return getDataFromTxt(inputStream, reagentName);
        }

        throw new Exception("不支持文件扩展名为：" + fileName.substring(fileName.lastIndexOf('.') + 1) + " 的CODIS文件！");
    }

    /**
     * 解析dat文件
     * @param inputStream
     * @param reagentName
     * @return
     * @throws Exception
     */
    public static List<CodisQcModel> getDataDat(InputStream inputStream, String reagentName) throws Exception {
        List<CodisQcModel> fileStrInfoList = new ArrayList<CodisQcModel>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        //基因座个数
        int locusCount = 0;

        while ((str = br.readLine()) != null) {

            if (StringUtils.isBlank(str)) {
                continue;
            }

            try {
                if ("DNA Analysis Result".equalsIgnoreCase(str)) {
                    List<Object> geneInfolist = new ArrayList<>();
                    List<Object> geneInfoString = new ArrayList<>();
                    List<Object> geneInfoMap = new ArrayList<>();
                    int readCount = 0;
                    //判断是否是混合类型
                    int ifMix = 0;
                    //峰值个数
                    int valCount = 0;

                    //2个无用信息
                    for (int i = 0; i < 2; i++) {
                        br.readLine();
                    }

                    CodisQcModel codisContentModel = new CodisQcModel();

                    //Dna-----------------此处新建map集合--------------------------
                    Map<String, Object> map = new LinkedHashMap<>();

                    //样本实验室编号
                    codisContentModel.setSampleNo(br.readLine());

                    //5个无用信息
                    for (int i = 0; i < 5; i++) {
                        br.readLine();
                    }

                    //20200421基因座和对应的等位基因更 改返回格式

                    locusCount = Integer.parseInt(br.readLine());
                    codisContentModel.setLocusCount(locusCount);
                    while (readCount < locusCount) {


                        String locusName = StringUtils.replace(br.readLine(), " ", "");
                        if (StringUtils.isBlank(locusName)) {
                            break;
                        }
                        //4个无用信息
                        for (int i = 0; i < 4; i++) {
                            br.readLine();
                        }

                        String alleleValue1 = "";
                        String alleleValue2 = "";
                        String alleleValue3 = "";
                        String alleleValue4 = "";
                        String alleleValue5 = "";
                        String alleleValue6 = "";
                        valCount = Integer.parseInt(br.readLine());
                        //判断基因位点是否大于2个
                        if (valCount > 2){
                            ifMix = 1;
                        }
                        if (valCount == 1) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = alleleValue1;
                        } else if (valCount == 2) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                        } else if (valCount == 3) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                        } else if (valCount == 4) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                            alleleValue4 = br.readLine();
                        } else if (valCount == 5) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                            alleleValue4 = br.readLine();
                            alleleValue5 = br.readLine();
                        } else if (valCount == 6) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                            alleleValue4 = br.readLine();
                            alleleValue5 = br.readLine();
                            alleleValue6 = br.readLine();
                        }

                        //Dna-----------------此处装订map--------------------------
                        /*ArrayList<String> list = new ArrayList<String>();
                        if (!"".equals(alleleValue1)){
                            list.add(alleleValue1);
                        }
                        if (!"".equals(alleleValue2)){
                            list.add(alleleValue2);
                        }
                        if (!"".equals(alleleValue3)){
                            list.add(alleleValue3);
                        }
                        if (!"".equals(alleleValue4)){
                            list.add(alleleValue4);
                        }
                        map.put(locusName,list);
                        cqmgid=new CodisQcModel.GeneInfoDetail();
                        cqmgid.setLocusName(locusName);
                        cqmgid.setAlleleOne(list.get(0));
                        cqmgid.setAlleleTwo(list.get(1));
                        cqmgidList.add(cqmgid);*/
                        //Dna-----------------装订map结束--------------------------
                        ArrayList<Object> listMap = new ArrayList<Object>();
                        String allele = "";
                        HashMap<String, Object> Gene = new HashMap<>();
                        HashMap<String, String> map1 = new HashMap<>();
                        HashMap<String, Object> geneList = new HashMap<>();
                        if (!"".equals(alleleValue1)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue1);
                            listMap.add(stringStringHashMap);
                            allele = alleleValue1;
                        }
                        if (!"".equals(alleleValue2)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue2);
                            listMap.add(stringStringHashMap);
                            allele = allele+","+alleleValue2;
                        }
                        if (!"".equals(alleleValue3)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue3);
                            listMap.add(stringStringHashMap);
                            allele = allele+","+alleleValue3;
                        }
                        if (!"".equals(alleleValue4)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue4);
                            listMap.add(stringStringHashMap);
                            allele = allele+","+alleleValue4;
                        }
                        if (!"".equals(alleleValue5)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue5);
                            listMap.add(stringStringHashMap);
                            allele = allele+","+alleleValue5;
                        }
                        if (!"".equals(alleleValue6)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue6);
                            listMap.add(stringStringHashMap);
                            allele = allele+","+alleleValue6;
                        }
                        map.put(locusName,listMap);

                        map1.put("allele",allele);
                        map1.put("markerName",locusName);

                        Gene.put("markerName",locusName);
                        Gene.put("allele",listMap);

                        geneList.put("name",locusName);
                        String[] split = allele.split(",");
                        geneList.put("value",split);

                        geneInfoMap.add(map1);
                        geneInfoString.add(Gene);
                        geneInfolist.add(geneList);
                        readCount++;
                    }

                    //Dna-----------------Map数据插入--------------------------
                    if(map != null){
                        codisContentModel.setUuidString(UUID.randomUUID().toString());
                        codisContentModel.setGeneInfoList(geneInfolist);
//                        codisContentModel.setGeneInfo(cqmgidList);
//                        codisContentModel.setGenenum(Integer.toString(samplenum));
//                        codisContentModel.setGeneInfoList(geneInfolist);
//                        codisContentModel.setGeneInfoBeanList(geneInfobeanlist);
                    }
                    //Dna-----------------Map数据插入结束--------------------------
                    //判断是否混合
                    if (ifMix==0){
                        codisContentModel.setMixFlag("否");
                    }else{
                        codisContentModel.setMixFlag("是");
                    }

                    fileStrInfoList.add(codisContentModel);
                }
            } catch (Exception e) {
                br.close();
                throw new Exception("CODIS文件格式或基因数据有误！");
            }
        }

        br.close();

        return fileStrInfoList;
    }

    /**
     * 解析txt文件
     * @param inputStream
     * @param reagentName
     * @return
     * @throws Exception
     */
    public static List<CodisQcModel> getDataFromTxt(InputStream inputStream, String reagentName) throws Exception {
        List<CodisQcModel> fileStrInfoList = Lists.newArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String lineTxt = null;

        String sampleNo = "";
        CodisQcModel sampleInfo = new CodisQcModel();

        while ((lineTxt = br.readLine()) != null) {


            Map<String, Object> map = new LinkedHashMap<>();

            if (StringUtils.isBlank(lineTxt)) {
                continue;
            }

            try {
                String[] arry = lineTxt.split("\t");

                if (arry[0].equalsIgnoreCase("Sample Info")) {
                    continue;
                }

                if (!arry[0].toLowerCase().equals(sampleNo) && !sampleNo.equals("")) {
                    sampleInfo.setSampleNo(sampleNo);

                    //Dna----------------Map数据装订开始----------------------
                    ArrayList<String> list = new ArrayList<String>();
                    if (!"".equals(arry[2])){
                        list.add(arry[2]);
                    }
                    if (arry.length > 4) {
                        list.add(arry[3]);
                    }
                    if (arry.length > 5) {
                        list.add(arry[4]);
                    }
                    if (arry.length > 6) {
                        list.add(arry[5]);
                    }
                    map.put(arry[1],list);
                    //Dna----------------Map数据装订结束----------------------

                    if(map != null){
                        sampleInfo.setGeneInfo(new ArrayList<CodisQcModel.GeneInfoDetail>());
                    }
                    fileStrInfoList.add(sampleInfo);
                    sampleInfo = new CodisQcModel();
                }

                sampleNo = arry[0].toLowerCase();

                //Dna----------------Map数据装订开始----------------------
                ArrayList<String> list = new ArrayList<String>();
                if (!"".equals(arry[2])){
                    list.add(arry[2]);
                }
                if (arry.length > 4) {
                    list.add(arry[3]);
                }
                if (arry.length > 5) {
                    list.add(arry[4]);
                }
                if (arry.length > 6) {
                    list.add(arry[5]);
                }
                map.put(arry[1],list);
                //Dna----------------Map数据装订结束----------------------
            } catch (Exception e) {
                br.close();
                throw new Exception("CODIS文件格式或基因数据有误！");
            }
        }
        br.close();

        return fileStrInfoList;
    }

    //判断基因型是否属于混合或者常染色体或者Y基因型
    @SuppressWarnings({ "unchecked" })
	public static String isGeneType(String geneInfo,Integer types) {
        String geneType = null;

        Map<String, List<String>> srcResult = null;
        try {
            srcResult =(Map<String, List<String>>) JSON.parse(geneInfo);
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误!", ex);
        }

        if (srcResult != null) {
            for (Map.Entry<String, List<String>> srcEntry : srcResult.entrySet()) {
                List<String> srcAlleleList = srcEntry.getValue();
                if (1 == types){
                    geneType = Constants.GENE_YSTR;
                    break;
                }else{
                    if (srcAlleleList.size() > 2) {
                        geneType = Constants.GENE_MIXED;
                        break;
                    }else if(srcAlleleList.size() <= 2) {
                        geneType = Constants.GENE_NORMAL;
                    }
                }
            }
        }

        return geneType;
    }

    /**
     * 判断基因型是否重复
     * @param srcGeneInfo	     待比对基因型
     * @param tarGeneInfo	     被比样本基因型
     * @return
     */
	@SuppressWarnings("unchecked")
	public static boolean compareGeneInfo(String srcGeneInfo, String tarGeneInfo) {
        boolean flag = true;

        Map<String, List<String>> srcResult = null;
        try {
            srcResult =(Map<String, List<String>>) JSON.parse(srcGeneInfo);
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误!", ex);
            ex.getStackTrace();
            throw ex;
        }
        Map<String, List<String>> tarResult = null;
        try {
            tarResult =(Map<String, List<String>>) JSON.parse(tarGeneInfo);
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误！ ", ex);
            ex.getStackTrace();
            throw ex;
        }

        //判断基因型是否为空值
        if(srcResult .size()==0 || tarResult.size() == 0){
            return false;
        }
//        if(srcResult == null || tarResult == null){
//            return false;
//        }

        for (Map.Entry<String, List<String>> srcEntry : srcResult.entrySet()) {
            String markerName = srcEntry.getKey();
            List<String> srcAlleleList = srcEntry.getValue();
            List<String> tarAlleleList = tarResult.get(markerName);
            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {
                if (srcAlleleList.size() != tarAlleleList.size() || !srcAlleleList.containsAll(tarAlleleList)) {
                    flag = false;
                    break;
                }
            }else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 校验基因座是否有效
     * @param geneInfo
     * @return
     */
    @SuppressWarnings("unchecked")
	public static boolean checkValidGene(String geneInfo) {
        boolean flag = false;

        Map<String, List<String>> srcResult = null;
        try {
            srcResult =(Map<String, List<String>>) JSON.parse(geneInfo);
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误!", ex);
        }
        if (srcResult != null) {
            for (Map.Entry<String, List<String>> srcEntry : srcResult.entrySet()) {
                List<String> srcAlleleList = srcEntry.getValue();
                if (srcAlleleList.size() > 0) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

}
