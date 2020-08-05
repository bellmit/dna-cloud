package com.bazl.dna.mix.connector.nation.controller;



import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSource;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.mix.connector.nation.constants.ErrorCodes;
import com.bazl.dna.mix.connector.nation.constants.ErrorInfo;
import com.bazl.dna.mix.connector.nation.dao.LabServerMapper;
import com.bazl.dna.mix.connector.nation.dao.LocusInfoMapper;
import com.bazl.dna.mix.connector.nation.dao.ReagentKitMapper;
import com.bazl.dna.mix.connector.nation.dao.SysRegionalismMapper;
import com.bazl.dna.mix.connector.nation.model.po.CaseInfo;
import com.bazl.dna.mix.connector.nation.model.po.LabServer;
import com.bazl.dna.mix.connector.nation.model.po.LocusInfo;
import com.bazl.dna.mix.connector.nation.model.po.PersonInfo;
import com.bazl.dna.mix.connector.nation.model.po.ReagentKit;
import com.bazl.dna.mix.connector.nation.model.po.SampleDnaGene;
import com.bazl.dna.mix.connector.nation.model.po.SampleInfo;
import com.bazl.dna.mix.connector.nation.model.po.SysDict;
import com.bazl.dna.mix.connector.nation.model.po.SysRegionalism;
import com.bazl.dna.mix.connector.nation.model.po.newSampleInfo;
import com.bazl.dna.mix.connector.nation.model.vo.SampleDnaGeneVo;
import com.bazl.dna.mix.connector.nation.service.CaseInfoService;
import com.bazl.dna.mix.connector.nation.service.PersonInfoService;
import com.bazl.dna.mix.connector.nation.service.SampleDnaGeneService;
import com.bazl.dna.mix.connector.nation.service.SampleInfoService;
import com.bazl.dna.mix.connector.nation.service.SysDictService;
import com.bazl.dna.mix.connector.nation.utils.ErrorCode;
import com.bazl.dna.mix.connector.nation.utils.ListUtils;
import com.bazl.dna.util.DataUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/nationalTreasury")
public class NationalTreasuryController extends  BaseController{

    @Autowired
    private LocusInfoMapper locusInfoMapper;

    @Autowired
    private ReagentKitMapper reagentKitMapper;

    @Autowired
    private SysRegionalismMapper sysRegionalismMapper;

    @Autowired
    private CaseInfoService caseInfoService;

    @Autowired
    private SampleInfoService sampleInfoService;

    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private SampleDnaGeneService sampleDnaGeneService;

    @Autowired
    LabServerMapper labServerMapper;

    @Value("#{'${REGIONALISM_CODE}'.split(',')}")
    private List<String> REGIONALISM_CODE;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 试剂盒列表  试剂盒id,名称
     * @return
     */
    @GetMapping(value = "/selectKitName")
    public ResponseData selectKitName(){
        try{
            List<Map<String, Object>> list = reagentKitMapper.selectKitName();
            if(ListUtils.isNotNullAndEmptyList(list)){
                return  new ResponseData(list);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e) {
            logger.error("查询试剂盒失败！"+e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询试剂盒列表失败!");
        }
    }

    /**
     * 试剂盒基因座关联表   通过试剂盒id查到关联的基因座id，基因id查询基因座信息
     * @param reagentKitId
     * @return
     */
    @RequestMapping(value = "/selectByKitIdToLocusID/{reagentKitId}")
    public  ResponseData selectByKitIdToLocusID(@PathVariable("reagentKitId") String reagentKitId){
        try {
            List<LocusInfo> list = locusInfoMapper.selectLocusInfoList(reagentKitId);
			if (ListUtils.isNotNullAndEmptyList(list)) {
				return new ResponseData(list);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
        }catch (Exception e) {
            logger.error("查询试剂盒失败！"+e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询试剂盒失败!");
        }
    }

    /**
     * 试剂盒基因座关联表   通过试剂盒名称查到关联的基因座名称，基因id查询基因座信息
     * @param reagentKitName
     * @return
     */
    @GetMapping(value = "/selectByKitNameToLocusName")
    public  ResponseData selectByKitNameToLocusName(@RequestParam("reagentKitName") String reagentKitName){
        try {
            List<Map<String, Object>> list = locusInfoMapper.selectLocusNameList(reagentKitName);
    			if (ListUtils.isNotNullAndEmptyList(list)) {
    				return new ResponseData(list);
    			} else {
    				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    			}

        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询试剂盒失败!");
        }
    }

    /**
     * 根据登录名查询基因座名称
     * @return
     */
    @GetMapping(value = "/selectByLocusName")
    public ResponseData selectByLocusName(){
        try {
            List<Map<String, Object>> list = locusInfoMapper.selectByLocusName();
            if(ListUtils.isNotNullAndEmptyList(list)){
                return new ResponseData(list);
			} else {
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "查询基因座名称失败!");
        }
    }

    /**
     * 根据基因座名称模糊查询基因座信息
     * @param locusName
     * @return
     */
    @RequestMapping(value = "/queryLocusNameVague")
    public ResponseData queryLocusNameVague(@RequestParam("locusName") String locusName){
        try {
            List<LocusInfo> list = locusInfoMapper.selectByNameVague(locusName);
			if (ListUtils.isNotNullAndEmptyList(list)) {
				return new ResponseData(list);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
        }catch (Exception e) {
            logger.error("试剂盒名称查询失败！"+e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "试剂盒名称查询失败！");
        }
    }


    /**
     * 省市二级联动
     * @param code
     * @return
     */
    @RequestMapping(value = "/selectProvinceAndCity")
    public ResponseData selectProvinceAndCity(@RequestParam("code") String code){
        /*Map<String, List<SysRegionalism>> result = new HashMap<>();
        List<SysRegionalism> sysRegionalisms = new ArrayList<>();*/
        try{
            List<Map<String,Object>> list = new ArrayList<>();
            List<SysRegionalism> sysList = sysRegionalismMapper.selectProvinceAndCity(REGIONALISM_CODE);
            if (ListUtils.isNotNullAndEmptyList(sysList)){
                for (SysRegionalism regionalism : sysList){
                    Map<String,Object> result = new HashMap<>();
                    result.put("province",regionalism.getRegionalismName());
                    List<SysRegionalism> cityList = sysRegionalismMapper.selectCitys(regionalism.getRegionalismCode());
                    result.put("city",cityList);
                    SysRegionalism sysRegionalism = sysRegionalismMapper.selectByPrimaryKey(regionalism.getId());
                    result.put("personlism",sysRegionalism);
                    list.add(result);
                }
            }
            return new ResponseData(list);
			/*if (code != "000000" && StringUtils.isNotBlank(code)) {
				list = sysRegionalismMapper.selectCitys(code);
                sysRegionalisms = sysRegionalismMapper.selectProvinceAndCity(REGIONALISM_CODE);
            } else {
				list = sysRegionalismMapper.selectProvinceAndCity(REGIONALISM_CODE);
			}

            if (ListUtils.isNotNullAndEmptyList(list)) {
                result.put("list",list);
                result.put("personlism",sysRegionalisms);
                return new ResponseData(result);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}*/

        }catch (Exception e) {
            logger.error("Exception:", e);
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取省市二级联动失败!");
        }

    }


    /**
     * 人员类别名称
     * @return
     */
    @GetMapping(value = "/selectPersonCategory")
    public ResponseData selectPersonCategory(){
        try {
//           List<SysDict> list = new ArrayList<SysDict>();
            List<Map<String,Object>> list = new ArrayList<>();
            for(int i =0;i <7;i++){
//                SysDict sysDict = new SysDict();
                if(i==0){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("020101");
                    sysDict1.setDictValue1("现场物品");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("020102");
                    sysDict2.setDictValue1("身份不明个体");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("020103");
                    sysDict3.setDictValue1("嫌疑父亲");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("010201");
                    sysDict4.setDictValue1("现场检材");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("010202");
                    sysDict5.setDictValue1("强奸致孕者");
                    mapList.add(sysDict5);
                    map.put("name","未知鉴定对象");
                    map.put("val",mapList);
                    list.add(map);
//                    sysDict.setDictValue1(Constants.PERSON_TYPE_1);
//                    sysDict.setDictKey(Constants.PERSON_TYPE_1_CODE);
//                    sysDict.setDictNationalKey(Constants.PERSON_TYPE_1_DICT);
//                    list.add(sysDict);
                }
                if(i==1){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("020201");
                    sysDict1.setDictValue1("受害人");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("020202");
                    sysDict2.setDictValue1("失踪受害人");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("020203");
                    sysDict3.setDictValue1("嫌疑人");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("020204");
                    sysDict4.setDictValue1("失踪嫌疑人");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("020205");
                    sysDict5.setDictValue1("案件其他人员");
                    mapList.add(sysDict5);
                    SysDict sysDict6 = new SysDict();
                    sysDict6.setDictKey("020206");
                    sysDict6.setDictValue1("强奸致孕受害人");
                    mapList.add(sysDict6);
                    map.put("name","已知鉴定对象");
                    map.put("val",mapList);
                    list.add(map);
                }
                if(i==2){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("010301");
                    sysDict1.setDictValue1("疑似被侵害失踪人员");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("010302");
                    sysDict2.setDictValue1("失踪儿童");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("010303");
                    sysDict3.setDictValue1("疑似灾难失踪人员");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("010304");
                    sysDict4.setDictValue1("负案在逃人员");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("010305");
                    sysDict5.setDictValue1("疑似走失人员");
                    mapList.add(sysDict5);
                    SysDict sysDict6 = new SysDict();
                    sysDict6.setDictKey("010306");
                    sysDict6.setDictValue1("其他失踪人员");
                    mapList.add(sysDict6);
                    SysDict sysDict7 = new SysDict();
                    sysDict7.setDictKey("010307");
                    sysDict7.setDictValue1("失踪儿童父亲");
                    mapList.add(sysDict7);
                    SysDict sysDict8 = new SysDict();
                    sysDict8.setDictKey("010308");
                    sysDict8.setDictValue1("失踪儿童母亲");
                    mapList.add(sysDict8);
                    SysDict sysDict9 = new SysDict();
                    sysDict9.setDictKey("010309");
                    sysDict9.setDictValue1("失踪儿童采血申请人");
                    mapList.add(sysDict9);
                    SysDict sysDict10 = new SysDict();
                    sysDict10.setDictKey("010310");
                    sysDict10.setDictValue1("失踪儿童用品");
                    mapList.add(sysDict10);
                    SysDict sysDict11 = new SysDict();
                    sysDict11.setDictKey("010311");
                    sysDict11.setDictValue1("失踪儿童同胞");
                    mapList.add(sysDict11);
                    map.put("name","失踪人口");
                    map.put("val",mapList);
                    list.add(map);
                }
                if(i==3){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("010401");
                    sysDict1.setDictValue1("未知名尸体");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("010402");
                    sysDict2.setDictValue1("被拐儿童");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("010403");
                    sysDict3.setDictValue1("灾难死者");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("010404");
                    sysDict4.setDictValue1("其他身份不明人员");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("010405");
                    sysDict5.setDictValue1("被拐儿童监护人");
                    map.put("name","身份不明人员");
                    map.put("val",mapList);
                    list.add(map);
                }
                if(i==4){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("010101");
                    sysDict1.setDictValue1("前科人员");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("010102");
                    sysDict2.setDictValue1("违法犯罪人员");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("010103");
                    sysDict3.setDictValue1("个案排查人员");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("010104");
                    sysDict4.setDictValue1("涉毒");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("010106");
                    sysDict5.setDictValue1("卖淫");
                    SysDict sysDict6 = new SysDict();
                    sysDict6.setDictKey("010107");
                    sysDict6.setDictValue1("嫖娼");
                    mapList.add(sysDict6);
                    SysDict sysDict7 = new SysDict();
                    sysDict7.setDictKey("010108");
                    sysDict7.setDictValue1("特殊职业者");
                    mapList.add(sysDict7);
                    SysDict sysDict8 = new SysDict();
                    sysDict8.setDictKey("010109");
                    sysDict8.setDictValue1("其他建库人员");
                    mapList.add(sysDict8);
                    SysDict sysDict9 = new SysDict();
                    sysDict9.setDictKey("010110");
                    sysDict9.setDictValue1("家系人员");
                    mapList.add(sysDict9);
                    map.put("name","建库人员");
                    map.put("val",mapList);
                    list.add(map);
                }
                if(i==5){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("030101");
                    sysDict1.setDictValue1("现场勘查人员");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("030102");
                    sysDict2.setDictValue1("实验室检验人员");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("030103");
                    sysDict3.setDictValue1("刑侦专家");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("030104");
                    sysDict4.setDictValue1("试剂耗材生产人员");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("030105");
                    sysDict5.setDictValue1("其他无关人员");
                    SysDict sysDict6 = new SysDict();
                    sysDict6.setDictKey("030106");
                    sysDict6.setDictValue1("试剂耗材研发人员");
                    mapList.add(sysDict6);
                    SysDict sysDict7 = new SysDict();
                    sysDict7.setDictKey("030107");
                    sysDict7.setDictValue1("试剂耗材质检人员");
                    mapList.add(sysDict7);
                    SysDict sysDict8 = new SysDict();
                    sysDict8.setDictKey("030108");
                    sysDict8.setDictValue1("试剂耗材保洁人员");
                    mapList.add(sysDict8);
                    SysDict sysDict9 = new SysDict();
                    sysDict9.setDictKey("030109");
                    sysDict9.setDictValue1("试剂耗材保卫人员");
                    mapList.add(sysDict9);
                    map.put("name","质控对象");
                    map.put("val",mapList);
                    list.add(map);
                }
                if(i==6){
                    Map<String,Object> map = new HashMap<>();
                    List<SysDict> mapList = new ArrayList<>();
                    SysDict sysDict1 = new SysDict();
                    sysDict1.setDictKey("060101");
                    sysDict1.setDictValue1("人员");
                    mapList.add(sysDict1);
                    SysDict sysDict2 = new SysDict();
                    sysDict2.setDictKey("060102");
                    sysDict2.setDictValue1("物证");
                    mapList.add(sysDict2);
                    SysDict sysDict3 = new SysDict();
                    sysDict3.setDictKey("060103");
                    sysDict3.setDictValue1("失踪人员");
                    mapList.add(sysDict3);
                    SysDict sysDict4 = new SysDict();
                    sysDict4.setDictKey("060105");
                    sysDict4.setDictValue1("身份不明个体");
                    mapList.add(sysDict4);
                    SysDict sysDict5 = new SysDict();
                    sysDict5.setDictKey("060106");
                    sysDict5.setDictValue1("其他");
                    mapList.add(sysDict5);
                    map.put("name","协查对象");
                    map.put("val",mapList);
                    list.add(map);
                }
            }
            if(ListUtils.isNotNullAndEmptyList(list)){
                return new ResponseData(list);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取人员类别名称失败!");
        }
    }


    /**
     * 根据案件编号获取国家库 案件下的基本信息信息
     * @param
     * @return
     * @throws IException
     */
    @RequestMapping(value = "/getCaseInfoByCaseNo")
    public ResponseData getCaseInfoByCaseNo(@RequestParam("caseNo") String caseNo) {
        try {
            CaseInfo caseInfo = new CaseInfo();
            logger.debug("|.获取案件信息已开始|"+"方法名称:"+this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[1].getMethodName()+".|");
            if (StringUtils.isNotBlank(caseNo)) {
                try{
                    Map<String, Object> dataMap = null;
                    caseInfo = caseInfoService.selectByCaseNo(caseNo);
                    if (caseInfo != null) {
                        dataMap = new LinkedHashMap<>();
                        dataMap.put("caseInfo", caseInfo);

                        String caseId = caseInfo.getId();
                        if(StringUtils.isNotBlank(caseId)){
                            //获取案件下审核通过的检材信息
                            List<SampleInfo> sampleInfoList = sampleInfoService.selectAuditSampleByCaseId(caseId);
                            if (ListUtils.isNotNullAndEmptyList(sampleInfoList)) {
                                dataMap.put("sampleInfoList", sampleInfoList);
                                logger.debug("|.获取案件下审核通过的检材信息|结果result="+ sampleInfoList.toString() +".|");
                            }
                            //获取案件下人员信息
                            List<PersonInfo> personInfoList = personInfoService.selectAuditPersonByCaseId(caseId);
                            if (ListUtils.isNotNullAndEmptyList(personInfoList)) {
                                dataMap.put("personInfoList", personInfoList);
                                logger.debug("|.获取案件下审核通过的检材信息|结果result="+ sampleInfoList.toString() +".|");
                            }
                            //获取案件下审核通过的单一检材基因型信息
                            List<SampleDnaGene> singleSampleDnaGeneList =sampleDnaGeneService.selectByCaseToSingleGeneList(caseId);
                            if (ListUtils.isNotNullAndEmptyList(singleSampleDnaGeneList)) {
                                dataMap.put("singleSampleDnaGeneList", singleSampleDnaGeneList);
                                logger.debug("|.获取案件下审核通过的单一检材基因型信息|结果result="+ singleSampleDnaGeneList.toString() +".|");
                            }
                            //获取案件下审核通过的混合检材基因型信息
                            List<SampleDnaGene> mixSampleDnaGeneList =sampleDnaGeneService.selectByCaseToMixSampleDnaGeneList(caseId);
                            if (ListUtils.isNotNullAndEmptyList(mixSampleDnaGeneList)) {
                                dataMap.put("mixSampleDnaGeneList", mixSampleDnaGeneList);
                                logger.debug("|.获取案件下审核通过的混合检材基因型信息|结果result="+ mixSampleDnaGeneList.toString() +".|");
                            }
                        }
                        if (dataMap != null) {
                            logger.debug("|.获取案件信息已结束|结果result="+ dataMap.toString() +".|");
                            return new ResponseData(dataMap);
                        }

                    }else {
                        return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "1该案件不存在，获取信息失败！");
                    }

                }catch (Exception e) {
                    logger.error("获取案件信息错误"+e);
                    return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "获取案件信息错误！"+e.toString());
                }
            }
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "1案件编号为null");
        }catch (Exception e){
            logger.error("获取案件信息错误"+e);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE, "获取案件信息错误！"+e.toString());
        }
    }

    @RequestMapping(value = "/getCaseInfoSampleNo", produces = "application/json; charset=utf-8")
    public ResponseData getCaseInfoSampleNo(@RequestParam("sampleNo") String sampleNo,@RequestParam("datatype") String datatype,
                                            @RequestParam("personCodeType") String personCodeType)throws Exception {
        //先查主数据库
        newSampleInfo singleGeneVosList = sampleDnaGeneService.selectSingleGene(sampleNo,personCodeType);
        if (singleGeneVosList != null){
            //查询服务器地区
            LabServer labServer = labServerMapper.selectByLabServerNo(singleGeneVosList.getLabServerNo());
            if (labServer != null){
                singleGeneVosList.setLabServerName(labServer.getLabServerName());
            }
            ReagentKit reagentKit = reagentKitMapper.selectByPrimaryKey(singleGeneVosList.getReagentKit());
            if (reagentKit != null){
                singleGeneVosList.setKilName(reagentKit.getName());
            }
        }else if (StringUtils.isNotBlank(datatype)){
            DruidDataSource dataSource = (DruidDataSource)ContextHolder.dataSourceMap.get(datatype);
            Connection connection = dataSource.getConnection();

            String sql = " select t.ID,t.LAB_ID ,t.SAMPLE_NO ,t.SAMPLE_LAB_NO ,t.SAMPLE_NAME ,t.SAMPLE_TYPE ,sd.dict_value1 as person_type_name, " +
                    "  ca.id as CASE_ID,ca.CASE_NO as CASE_NO,ca.CASE_NAME,pi.db_category as person_type_code, sdg.REAGENT_KIT as REAGENT_KIT, " +
                    "  pi.ID_CARD_NO as ID_CARD_NO, pi.PERSON_NAME as PERSON_NAME,pi.INIT_SERVER_NO as LAB_SERVER_NO, rk.name as kil_Name, ls.lab_server_name as lab_Server_Name" +
                    "  from SAMPLE_DNA_GENE sdg " +
                    "  left join SAMPLE_INFO t on sdg.sample_id = t.id " +
                    "  left join consignment ct on t.consignment_id = ct.id " +
                    "  left join case_info ca on ct.event_id = ca.id " +
                    "  left join SAMPLE_PERSON_MAP spm on spm.sample_id = t.id " +
                    "  left join PERSON_INFO pi on pi.id = spm.person_object_id " +
                    "  left join SYS_DICT sd on pi.db_category = sd.dict_key and sd.dict_category='PERSON_CATEGORY' " +
                    "  left join REAGENT_KIT rk on rk.id = sdg.reagent_kit " +
                    "  left join LAB_SERVER ls on ls.lab_server_no = sdg.init_server_no " +
                    "  where sdg.id = '"+sampleNo+"'and pi.db_category = '" + personCodeType + "' and sdg.GENE_TYPE = '1'";
            List<Map<String, String>> list = DataSourceUtil.execute(connection, sql);
            connection.close();
            Map<String, String> singmap = new HashMap<>();
            if (ListUtils.isNotNullAndEmptyList(list)){
                Map<String, String> map = list.get(0);
                for (String keys : map.keySet()){
                    String key = DataUtils.coderName(keys);
                    String value = map.get(keys);
                    singmap.put(key,value);
                }
            }
            Gson gson = new Gson();
            singleGeneVosList  = gson.fromJson(gson.toJson(singmap), new TypeToken<newSampleInfo>(){}. getType());
            logger.info("getCaseInfoSampleNo:{}, list:{}", sampleNo, singleGeneVosList);
        }
        return new ResponseData(singleGeneVosList);
    }



    //国家库查询所有混合样本(条查分页)
//    @ResponseBody
//    @RequestMapping(value = "/queryMixedSampleGene", method = RequestMethod.GET)
//    public ResultBean queryMixedSampleGene(HttpServletRequest request, String page,String size , String caseNo,String caseName,String sampleNo, String sampleName){
//        try{
//            //分页参数为空，设置默认值
//            int pg;
//            int sz;
//            if(StringUtils.isNotBlank(page) && !"0".equals(page)){
//                pg = Integer.valueOf(page);
//            }else{
//                pg = 1;
//            }
//            if(StringUtils.isNotBlank(size) && !"0".equals(size)){
//                sz = Integer.valueOf(size);
//            }else{
//                sz = 15;
//            }
//            Map<String, Object> dataMap = new LinkedHashMap<>();
//            SampleDnaGeneVo sampleDnaGeneVo = new SampleDnaGeneVo();
//
//            if(StringUtils.isNotBlank(caseNo)){
//                sampleDnaGeneVo.setCaseNo(caseNo);
//            }
//            if(StringUtils.isNotBlank(caseName)){
//                sampleDnaGeneVo.setCaseName(caseName);
//            }
//            if(StringUtils.isNotBlank(sampleNo)){
//                sampleDnaGeneVo.setSampleNo(sampleNo);
//            }
//            if(StringUtils.isNotBlank(sampleName)){
//                sampleDnaGeneVo.setSampleName(sampleName);
//            }
//
//            List<SampleDnaGeneVo> sampleDnaGeneVoMix = sampleDnaGeneService.queryByMixDnaGene(pg,sz,sampleDnaGeneVo);
//            if(ListUtils.isNotNullAndEmptyList(sampleDnaGeneVoMix)){
//               for (SampleDnaGeneVo dnaGeneVoMix : sampleDnaGeneVoMix) {
//                   if (StringUtils.isNotBlank(dnaGeneVoMix.getEntity().getGenePicture())){
//                       dnaGeneVoMix.getEntity().setGeneImagePath(dnaGeneVoMix.getEntity().getGenePicture());
//                   }else{
//                       dnaGeneVoMix.getEntity().setGeneImagePath("0");
//                   }
//               }
//            }
//            List<SampleDnaGeneVo> sampleDnaGeneVoList = sampleDnaGeneService.countMixDnaGene();
//            dataMap.put("count",sampleDnaGeneVoList.size());
//            dataMap.put("sampleDnaGeneVoMix",sampleDnaGeneVoMix);
//            return new ResultBean(ResultBean.CODE_SUCCESS, 0, dataMap, ResultBean.NAME_SUCCESS);
//        }catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "获取信息错误!");
//        }
//    }


    /**
     * 国家库条查接口
     * @param page
     * @param caseNo
     * @param caseName
     * @param sampleNo
     * @param sampleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMixedSampleGenes")
    public ResponseData queryMixedSampleGenes(@RequestParam("page") String page, @RequestParam("caseNo") String caseNo,
    		@RequestParam("caseName") String caseName, @RequestParam("sampleNo") String sampleNo, @RequestParam("sampleName") String sampleName) {
        try {
            Map<String, Object> dataMap = new LinkedHashMap<>();
            PageInfo pageInfo = new PageInfo();
            SampleDnaGeneVo sampleDnaGeneVo = new SampleDnaGeneVo();
            if(StringUtils.isEmpty(page) || "0".equals(page) || page == null ){
                sampleDnaGeneVo.setOffset(1);
                pageInfo.setPage(1);
            }else{
                sampleDnaGeneVo.setOffset(Integer.parseInt(page));
                pageInfo.setPage(Integer.parseInt(page));
            }
            if (StringUtils.isNotBlank(caseNo)) {
                sampleDnaGeneVo.setCaseNo(caseNo);
            }
            if (StringUtils.isNotBlank(caseName)) {
                sampleDnaGeneVo.setCaseName(caseName);
            }
            if (StringUtils.isNotBlank(sampleNo)) {
                sampleDnaGeneVo.setSampleNo(sampleNo);
            }
            if (StringUtils.isNotBlank(sampleName)) {
                sampleDnaGeneVo.setSampleName(sampleName);
            }


            List<SampleDnaGeneVo> sampleDnaGeneVoMix = sampleDnaGeneService.queryByMixDnaGenes(pageInfo, sampleDnaGeneVo);
            if (ListUtils.isNotNullAndEmptyList(sampleDnaGeneVoMix)) {
                for (SampleDnaGeneVo dnaGeneVoMix : sampleDnaGeneVoMix) {
                    if (StringUtils.isNotBlank(dnaGeneVoMix.getEntity().getGenePicture())) {
                        dnaGeneVoMix.getEntity().setGeneImagePath(dnaGeneVoMix.getEntity().getGenePicture());
                    } else {
                        dnaGeneVoMix.getEntity().setGeneImagePath("0");
                    }
                }
            }
            int count = sampleDnaGeneService.countMixDnaGenes(sampleDnaGeneVo);
            dataMap.put("count", count);
            dataMap.put("sampleDnaGeneVoMix", sampleDnaGeneVoMix);
            return new ResponseData(dataMap);
        }catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 国家库删除混合样本 （假删除）
     * @param sampleGeneId
     * @return
     */
    @GetMapping(value = "/updateSampleDnaGeneDF/{sampleGeneId}")
    public ResponseData updateSampleDnaGeneDF(@PathVariable("sampleGeneId") String sampleGeneId){
        try {
            sampleDnaGeneService.updateSampleDnaGeneDF(sampleGeneId);
            return new ResponseData(0);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "删除信息错误!");
        }
    }


    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 总数
     * @return
     */
    @GetMapping(value = "/selectMixGeneByCwsd")
    public  ResponseData selectMixGeneByCwsd(){
        try{
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("mixGeneCount", sampleDnaGeneService.selectMixGeneByCwsd());
            return new ResponseData(dataMap);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }


    /**
     * 查询单一基因信息(关联案件，地区，样本，字典项) 总数
     * @return
     */
    @RequestMapping(value = "/selectSingleGeneByCwsd")
    public ResponseData selectSingleGeneByCwsd(){
        try{
            Map<String, Object> dataMap = new LinkedHashMap<>();
            int i = sampleDnaGeneService.selectSingleGeneByCwsd( );
            dataMap.put("singleGeneCount",i);
            return new ResponseData(dataMap);

        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     * 查询规定时间内的数据条数
     */
    @RequestMapping(value = "/selectCountByDate")
    public ResponseData selectCountByDate(@RequestParam("date") Date date){
        try{
            Map<String, Object> dataMap = new LinkedHashMap<>();
            int i = sampleDnaGeneService.selectSingleCountByDate(date);
            dataMap.put("singleGeneCount",i);
            return new ResponseData(dataMap);

        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }


    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 分页
     * @param page
     * @return
     */
    @RequestMapping(value = "/selectMixGeneByCwsdPage")
    public  ResponseData selectMixGeneByCwsdPage(@RequestParam("page") String page){
        try{
            int pg = Integer.valueOf(page);
            Map<String, Object> dataMap = new LinkedHashMap<>();
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            List<SampleDnaGeneVo> mixGeneVosList = sampleDnaGeneService.selectMixGeneByCwsdPage(query,pg);
            if(ListUtils.isNotNullAndEmptyList(mixGeneVosList)){
                dataMap.put("mixGeneVosList",mixGeneVosList);
                return new ResponseData(dataMap);
			} else {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }


    /**
     * 查询单一基因信息(关联案件，地区，样本，字典项) 分页
     * @param page
     * @return
     */
    @RequestMapping(value = "/selectSingleGeneByCwsdPage")
    public  ResponseData selectSingleGeneByCwsdPage(@RequestParam("page") String page,@RequestParam("pageSize") int pageSize){
        try{
            PageInfo pageInfo = new PageInfo();
            int pg = Integer.valueOf(page);
            pageInfo.setPage(pg);
            pageInfo.setEvePageRecordCnt(pageSize);
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            List<SampleDnaGeneVo> singleGeneVosList = sampleDnaGeneService.selectSingleGeneByCwsdPage(query,pageInfo);
            if(ListUtils.isNotNullAndEmptyList(singleGeneVosList)){
                return new ResponseData(singleGeneVosList);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 查询规定天数的单一基因信息 分页
     */
    @RequestMapping(value = "/selectByDateSingList")
    public  ResponseData selectByDateSingList(@RequestParam("page") String page,
                                              @RequestParam("pageSize") int pageSize, @RequestParam("date") Date date){
        try{
            PageInfo pageInfo = new PageInfo();
            int pg = Integer.valueOf(page);
            pageInfo.setPage(pg);
            pageInfo.setEvePageRecordCnt(pageSize);
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            query.getEntity().setCreateDatetime(date);
            List<SampleDnaGeneVo> singleGeneVosList = sampleDnaGeneService.selectByDateSing(query,pageInfo);
            if(ListUtils.isNotNullAndEmptyList(singleGeneVosList)){
                return new ResponseData(singleGeneVosList);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error("--------------获取规定天数的单一基因信息失败！--------",e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据样本编号 查询单一基因信息(关联案件，地区，样本，字典项)
     * @param sampleNo
     * @return
     */
    @RequestMapping(value = "/selectSingleGeneBySampleNo")
    public ResponseData selectSingleGeneBySampleNo(@RequestParam("sampleNo") String sampleNo){
        try{
            SampleDnaGeneVo query = new SampleDnaGeneVo();
            query.setSampleNo(sampleNo);
            List<SampleDnaGeneVo> singleGeneVosList = sampleDnaGeneService.selectSingleGeneBySampleNo(query);
            if(ListUtils.isNotNullAndEmptyList(singleGeneVosList)){
                for (SampleDnaGeneVo dnaGeneVo : singleGeneVosList){
                    SysDict bean = new SysDict();
                    bean.setDictCategory("SAMPLE_TYPE");
                    bean.setDictKey(dnaGeneVo.getSampleType());
                    SysDict sysDict = sysDictService.selectByPrimaryKeyBean(bean);
                    if (sysDict != null){
                        dnaGeneVo.setSampleTypeName(sysDict.getDictValue1());
                    }
                    ReagentKit reagentKit = reagentKitMapper.selectByPrimaryKey(dnaGeneVo.getEntity().getReagentKit());
                    if (reagentKit != null){
                        dnaGeneVo.setKitName(reagentKit.getName());
                        dnaGeneVo.setKitId(reagentKit.getId());
                    }
                }
                return new ResponseData(singleGeneVosList);
            }else{
            	return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据案件id查询案件信息
     */
    @GetMapping(value = "/selectByCaseId")
    public ResponseData selectByCaseId(@RequestParam("caseId") String caseId){
        try{
            CaseInfo caseInfo = caseInfoService.selectByCaseId(caseId);
            if(caseInfo != null){
                return new ResponseData(caseInfo);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据案件id查询混合样本库信息
     */
    @GetMapping(value = "/selectMixedSampleGeneList")
    public ResponseData selectMixedSampleGeneList(@RequestParam("caseId") String caseId){
        try{
            List<SampleDnaGene> sampleDnaGeneList = sampleDnaGeneService.selectMixedSampleGeneList(caseId);
            if(ListUtils.isNotNullAndEmptyList(sampleDnaGeneList)){
                return new ResponseData(sampleDnaGeneList);
            }else{
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

    /**
     * 根据案件id查询混合样本库信息
     */
    @GetMapping(value = "/selectBySampleId")
    public ResponseData selectBySampleId(@RequestParam("sampleId") String sampleId){
        try{
            CaseInfo caseInfo = caseInfoService.selectBySampleId(sampleId);
            if(caseInfo != null){
                return new ResponseData(caseInfo);
            }else{
                return new ResponseData(null);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, "获取信息错误!");
        }
    }

}
