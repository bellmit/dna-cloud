package com.bazl.dna.mix.controller.codis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *@author wanghaiyang
 * @date 2019/5/14.
 */
public class CodisFileParser {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CodisFileParser.class);

    public static List<CodisContentModel> getDataCodisFile(InputStream inputStream, String fileName, String reagentName, Integer types) throws Exception {
        if (fileName.toUpperCase().endsWith("DAT")) {
            return getDataDat(inputStream, reagentName, types);
        }

       /* if (fileName.toUpperCase().endsWith("TXT")) {
            return getDataFromTxt(inputStream, reagentName);
        }*/

        throw new Exception("不支持文件扩展名为：" + fileName.substring(fileName.lastIndexOf('.') + 1) + " 的CODIS文件！");
    }

    /**
     * 解析dat文件
     * @param inputStream
     * @param reagentName
     * @return
     * @throws Exception
     */
    public static List<CodisContentModel> getDataDat(InputStream inputStream, String reagentName, Integer types) throws Exception {
        List<CodisContentModel> fileStrInfoList = new ArrayList<CodisContentModel>();
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
                    List<Object> geneInfobeanlist = new ArrayList<>();
                    int samplenum = 0;
                    //判断是否是混合类型
                    int ifMix = 0;
                    int readCount = 0;
                    //峰值个数
                    int valCount = 0;
                    //2个无用信息
                    for (int i = 0; i < 2; i++) {
                        br.readLine();
                    }
                    CodisContentModel codisContentModel = new CodisContentModel();
                    //Dna-----------------此处新建map集合--------------------------
                    Map<String, Object> map = new LinkedHashMap<>();
                    //样本实验室编号
                    codisContentModel.setMixedSampleId(UUID.randomUUID().toString());
                    codisContentModel.setMixId(UUID.randomUUID().toString());
                    codisContentModel.setSampleNo(br.readLine());
                    //5个无用信息
                    for (int i = 0; i < 5; i++) {
                        br.readLine();
                    }
                    locusCount = Integer.parseInt(br.readLine());
                    codisContentModel.setGeneCount(locusCount);
                    while (readCount < locusCount) {
                        HashMap<String, Object> Gene = new HashMap<>();
                        String locusName = br.readLine();
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
                        ArrayList<Object> list = new ArrayList<Object>();
                        String allele = "";
                        HashMap<String, String> map1 = new HashMap<>();
                        if (!"".equals(alleleValue1)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue1);
                            list.add(stringStringHashMap);
                            allele = alleleValue1;
                        }
                        if (!"".equals(alleleValue2)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue2);
                            list.add(stringStringHashMap);
                            allele = allele+","+alleleValue2;
                        }
                        if (!"".equals(alleleValue3)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue3);
                            list.add(stringStringHashMap);
                            allele = allele+","+alleleValue3;
                        }
                        if (!"".equals(alleleValue4)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue4);
                            list.add(stringStringHashMap);
                            allele = allele+","+alleleValue4;
                        }
                        if (!"".equals(alleleValue5)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue5);
                            list.add(stringStringHashMap);
                            allele = allele+","+alleleValue5;
                        }
                        if (!"".equals(alleleValue6)){
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            stringStringHashMap.put("name",alleleValue6);
                            list.add(stringStringHashMap);
                            allele = allele+","+alleleValue6;
                        }
                        map.put(locusName,list);
                        map1.put("allele",allele);
                        map1.put("markerName",locusName);
                        Gene.put("markerName",locusName);
                        Gene.put("allele",list);

                        geneInfobeanlist.add(map1);
                        geneInfolist.add(Gene);
                        samplenum++;
                        readCount++;
                    }
                    if(map != null){
                  /*      JSONObject json = new JSONObject(map);
                        codisContentModel.setGeneInfo(json.toJSONString());*/
                        codisContentModel.setGenenum(Integer.toString(samplenum));
                        codisContentModel.setGeneInfoList(geneInfolist);
                        codisContentModel.setGeneInfoBeanList(geneInfobeanlist);
                    }

                    //types为1 表示只获取单一样本基因信息  0：混合样本
                    if(types==1){
                        if (ifMix == 0){
                            //获取单一样本
                            fileStrInfoList.add(codisContentModel);
                        }
                    }else{
                        if (ifMix == 1){
                            //获取混合样本
                            fileStrInfoList.add(codisContentModel);
                        }
                    }
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
    public static List<CodisContentModel> getDataFromTxt(InputStream inputStream, String reagentName) throws Exception {
        List<CodisContentModel> fileStrInfoList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String lineTxt = null;

        String sampleNo = "";
        CodisContentModel sampleInfo = new CodisContentModel();

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
                        JSONObject json = new JSONObject(map);
                        sampleInfo.setGeneInfo(json.toJSONString());
                    }
                    fileStrInfoList.add(sampleInfo);
                    sampleInfo = new CodisContentModel();
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



    /**
     * 校验基因座是否有效
     * @param geneInfo
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean checkValidGene(String geneInfo) {
        boolean flag = false;

        Map<String, List<String>> srcResult = null;
        try {
            srcResult =(Map) JSON.parse(geneInfo);
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
