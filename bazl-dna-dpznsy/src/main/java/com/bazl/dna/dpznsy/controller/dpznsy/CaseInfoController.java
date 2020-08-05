package com.bazl.dna.dpznsy.controller.dpznsy;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.dpznsy.controller.base.BaseController;
import com.bazl.dna.dpznsy.controller.base.ResultBean;
import com.bazl.dna.dpznsy.controller.base.error.ErrorCode;
import com.bazl.dna.dpznsy.controller.base.error.ErrorMsgManager;
import com.bazl.dna.dpznsy.model.po.AmCompanyInfo;
import com.bazl.dna.dpznsy.model.po.CaseInfo;
import com.bazl.dna.dpznsy.model.po.MemberInfo;
import com.bazl.dna.dpznsy.model.po.SampleInfo;
import com.bazl.dna.dpznsy.model.po.SysDict;
import com.bazl.dna.dpznsy.model.vo.CaseInfoVo;
import com.bazl.dna.dpznsy.service.DictItemService;
import com.bazl.dna.dpznsy.util.TransferUtil;
import com.bazl.dna.util.DateUtil;

/**
 * Created by Administrator on 2020/5/18.
 */
@RestController
@RequestMapping("/caseInfo")
public class CaseInfoController extends BaseController{

    @Autowired
    DictItemService dictItemService;

    @Value("${japt}")
    private String japtPath;
    
    /**
     * 查询下拉数据以及委托单位
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectDictInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectDictInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = findDictAndOrgInfoList();
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("查询下拉数据以及委托单位失败：" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 调取接案平台字典项数据以及委托单位
     * @return
     * @throws IOException
     */
    private Map<String, Object> findDictAndOrgInfoList() throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        //传递参数
        Map<String, Object> paramMap = new HashMap<String,Object>();
        //调取接案平台接口地址
        String url = japtPath+"/japt/findDictAndOrgInfoList";
        //调取接案平台接口
        logger.info("接收接案平台数据开始：--------------------------------------------");
        String result = TransferUtil.getJaptInfo(paramMap, url);
        logger.info("接收接案平台数据结束：--------------------------------------------");
        logger.info("请求服务返回结果: "+result);
        //解析json
        JSONObject str = JSON.parseObject(result);
        @SuppressWarnings("unchecked")
		Map<String, Object> list = (Map<String, Object>)str.get("data");
        //检材类型
        List<SysDict> sampleTypeDpList = (List<SysDict>) JSON.parseArray(list.get("sampleTypeDpList").toString(), SysDict.class);
        //检材性状
        List<SysDict> sampleCharacterDpList = (List<SysDict>) JSON.parseArray(list.get("sampleCharacterDpList").toString(), SysDict.class);
        //初验结果
        List<SysDict> firstResultDpList = (List<SysDict>) JSON.parseArray(list.get("firstResultDpList").toString(), SysDict.class);
        //检材包装
        List<SysDict> packingDpList = (List<SysDict>) JSON.parseArray(list.get("packingDpList").toString(), SysDict.class);
        //案件性质
        List<SysDict> casePropertyList = (List<SysDict>) JSON.parseArray(list.get("casePropertyList").toString(), SysDict.class);
        //查询委托单位（派出所）
        List<AmCompanyInfo> isRootPcsList = (List<AmCompanyInfo>) JSON.parseArray(list.get("isRootPcsList").toString(), AmCompanyInfo.class);
        //查询委托单位（分局）
        List<AmCompanyInfo> isRootFjList = (List<AmCompanyInfo>) JSON.parseArray(list.get("isRootFjList").toString(), AmCompanyInfo.class);
        resultMap.put("sampleTypeDpList", sampleTypeDpList);
        resultMap.put("sampleCharacterDpList", sampleCharacterDpList);
        resultMap.put("firstResultDpList", firstResultDpList);
        resultMap.put("packingDpList", packingDpList);
        resultMap.put("casePropertyList", casePropertyList);
        resultMap.put("isRootPcsList", isRootPcsList);
        resultMap.put("isRootFjList", isRootFjList);
        return resultMap;
    }

    /**
     * 查询毒品溯源案件列表
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectSampleInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectSampleInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody CaseInfoVo caseInfoVo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> sysDictResultMap = new HashMap<>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sysDictResultMap = findDictAndOrgInfoList();
            //检材类型
            List<SysDict> sampleTypeDpList = (List<SysDict>) sysDictResultMap.get("sampleTypeDpList");
            //检材性状
            List<SysDict> sampleCharacterDpList = (List<SysDict>) sysDictResultMap.get("sampleCharacterDpList");
            //初验结果
            List<SysDict> firstResultDpList = (List<SysDict>) sysDictResultMap.get("firstResultDpList");
            //检材包装
            List<SysDict> packingDpList = (List<SysDict>) sysDictResultMap.get("packingDpList");
            //传递参数
            Map<String, Object> paramMap = new HashMap<String,Object>();
            paramMap.put("caseInfoVo", caseInfoVo);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(caseInfoVo.getPage());
            paramMap.put("pageInfo", pageInfo);
            //调取接案平台接口地址
            String url = japtPath+"/japt/findCaseConsignmentPersonSampleInfo";
            //调取接案平台接口
            logger.info("接收接案平台数据开始：--------------------------------------------");
            String result = TransferUtil.getJaptInfo(paramMap, url);
            logger.info("接收接案平台数据结束：--------------------------------------------");
            logger.info("请求服务返回结果: "+result);
            //解析json
            JSONObject str = JSON.parseObject(result);
            Map<String, Object> list = (Map<String, Object>)str.get("data");
            //案件信息
            List<CaseInfoVo> caseInfoList = (List<CaseInfoVo>) JSON.parseArray(list.get("caseInfoVoList").toString(), CaseInfoVo.class);
            if(null != caseInfoList && caseInfoList.size()>0){
                for(CaseInfoVo caseInfoVo1:caseInfoList){
                    if(null != caseInfoVo1.getEntity().getDelegateAt()){
                        caseInfoVo1.getEntity().setDelegateAt(sim.parse(sim.format(caseInfoVo1.getEntity().getDelegateAt())));
                    }
                    //送检数量
                    String sjCount = "";
                    if(null != caseInfoVo1.getAmount()){
                        sjCount += caseInfoVo1.getAmount();
                    }
                    if(StringUtils.isNotBlank(caseInfoVo1.getMeasurementUnitName())){
                        sjCount += caseInfoVo1.getMeasurementUnitName();
                    }
                    caseInfoVo1.setSjCount(sjCount);
                }
            }
            //分页信息
            PageInfo pageInfos = JSON.parseObject(list.get("pageInfo").toString(), PageInfo.class);
            resultMap.put("caseInfoList", caseInfoList);
            resultMap.put("pageInfo", pageInfos);
            resultMap.put("sampleTypeDpList", sampleTypeDpList);
            resultMap.put("sampleCharacterDpList", sampleCharacterDpList);
            resultMap.put("firstResultDpList", firstResultDpList);
            resultMap.put("packingDpList", packingDpList);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("查询案件列表失败：" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 毒品溯源案件列表查看
     * @param request
     * @param response
     * @param baseId
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectCaseInfoDetailList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectCaseInfoDetailList(HttpServletRequest request, HttpServletResponse response, @RequestBody String baseId) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> sysDictResultMap = new HashMap<>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sysDictResultMap = findDictAndOrgInfoList();
            //案件性质
            List<SysDict> casePropertyList = (List<SysDict>) sysDictResultMap.get("casePropertyList");
            //委托单位
            //查询委托单位（派出所）
            List<AmCompanyInfo> isRootPcsList = (List<AmCompanyInfo>) sysDictResultMap.get("isRootPcsList");
            //查询委托单位（分局）
            List<AmCompanyInfo> isRootFjList = (List<AmCompanyInfo>) sysDictResultMap.get("isRootFjList");
            //传递参数
            Map<String, Object> paramMap = new HashMap<String,Object>();
            CaseInfo caseInfo = new CaseInfo();
            caseInfo.setBaseId(baseId);
            paramMap.put("caseInfo", caseInfo);
            //调取接案平台接口地址
            String url = japtPath+"/japt/findCaseConsignmentPersonSampleInfoByBaseId";
            //调取接案平台接口
            logger.info("接收接案平台数据开始：--------------------------------------------");
            String result = TransferUtil.getJaptInfo(paramMap, url);
            logger.info("接收接案平台数据结束：--------------------------------------------");
            logger.info("请求服务返回结果: "+result);
            //解析json
            JSONObject str = JSON.parseObject(result);
            Map<String, Object> list = (Map<String, Object>)str.get("data");
            //案件委托信息
            CaseInfo caseInfo1 = JSON.parseObject(list.get("caseInfo").toString(), CaseInfo.class);
            if(null != caseInfo1){
                if(null != caseInfo1.getDelegateAt()){
                    caseInfo1.setDelegateAt(sim.parse(sim.format(caseInfo1.getDelegateAt())));
                }
                if(null != caseInfo1.getCrimeAt()){
                    caseInfo1.setCrimeAt(sim.parse(sim.format(caseInfo1.getCrimeAt())));
                }
            }
            //人员信息
            List<MemberInfo> memberInfoList = (List<MemberInfo>) JSON.parseArray(list.get("memberInfoList").toString(), MemberInfo.class);
            //检材信息
            List<SampleInfo> sampleInfoList = (List<SampleInfo>) JSON.parseArray(list.get("sampleInfoList").toString(), SampleInfo.class);

            resultMap.put("caseInfo", caseInfo1);
            resultMap.put("memberInfoList", memberInfoList);
            resultMap.put("sampleInfoList", sampleInfoList);
            resultMap.put("casePropertyList", casePropertyList);
            resultMap.put("isRootPcsList", isRootPcsList);
            resultMap.put("isRootFjList", isRootFjList);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("查询案件委托人员检材详情信息失败：" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }

    }

    /**
     * 毒品溯源导出
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/excelSampleInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void excelSampleInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody CaseInfoVo caseInfoVo) throws IOException {
        try {
            //传递参数
            Map<String, Object> paramMap = new HashMap<String,Object>();
            paramMap.put("caseInfoVo", caseInfoVo);
            //调取接案平台接口地址
            String url = japtPath+"/japt/findExcelSampleInfo";
            //调取接案平台接口
            logger.info("接收接案平台数据开始：--------------------------------------------");
            String result = TransferUtil.getJaptInfo(paramMap, url);
            logger.info("接收接案平台数据结束：--------------------------------------------");
            logger.info("请求服务返回结果: "+result);
            //String result = "{"code":1,"errorCode":0,"data":{"caseInfoVoList":[{"offset":0,"rows":0,"orderByClause":null,"entity":{"baseId":null,"caseId":null,"consignationId":null,"name":null,"tempSn":null,"caseSn":"FYA1404287","consignationMajorSn":"2014DP2143","status":null,"statusName":null,"major":null,"majorName":null,"isAppend":null,"appendSn":null,"property":null,"propertyName":null,"location":"朝阳区苹果社区北区2号楼A座16层","crimeAt":"2014-08-04T16:00:00.000+0000","brief":"2014年8月5日13时许，民警在本市朝阳区苹果社区北区2号楼A座16层抓获吸毒人员王宇，并从其身上起获毒品可疑物一袋。","type":null,"consignationName1":"产海林","cardid1":null,"phone1":"13522752720","consignationName2":"董炜","cardid2":null,"phone2":"13901293284","delegateAt":"2014-08-05T07:12:11.000+0000","createOrgName":"朝阳分局双井派出所","createOrgId":null,"acceptorName":null,"acceptorId":null,"isAccept":null,"refuseReason":null,"acceptAt":null,"acceptComment":null,"overtimeAt":null,"overtimeReason":null,"authorityMajor":null,"criticalDepartment":null,"resultQuerySn":null,"captcha":null,"testerId":null,"testerName":null,"createAt":null,"creatorId":null,"updateAt":null,"updatorId":null,"isDelete":null,"remark":null,"jzSn":null,"xkSn":null,"typeName":null,"isHomicide":null,"isCriminal":null,"createOrgFullname":null,"isTransfer":null,"transferBaseId":null,"overtimeReason2":null,"overtimeReason3":null,"overtimeAt2":null,"overtimeAt3":null,"acceptAtStart":null,"acceptAtEnd":null},"sampleName":null,"sampleNameName":null,"barcode":null,"sampleType":null,"sampleTypeName":null,"measurementUnit":null,"measurementUnitName":null,"color":null,"colorName":null,"count":null,"firstResult":null,"sampleCharacter":null,"sampleCharacterName":"晶体","acceptedAmount":null,"memberName":"王宇","gender":null,"genderName":null,"birthplace":null,"birthdate":null,"age":null,"country":null,"countryName":null,"nation":null,"nationName":null,"identity":null,"identityName":null,"idCard":"身份证号：110224198310171416","company":null,"job":null,"address":null,"noIdReason":null,"declare":null,"remark":null,"packing":null,"packingName":null,"firstResultName":"苯丙胺类","memberRemark":null},{"offset":0,"rows":0,"orderByClause":null,"entity":{"baseId":null,"caseId":null,"consignationId":null,"name":null,"tempSn":null,"caseSn":"FYA1404324","consignationMajorSn":"2014LC0707","status":null,"statusName":null,"major":null,"majorName":null,"isAppend":null,"appendSn":null,"property":null,"propertyName":null,"location":"北京市石景山区首钢水厂铁矿河东干选车间","crimeAt":"2014-07-14T16:00:00.000+0000","brief":"2014年7月15日零时30分许，河北省迁安市大崔庄镇凤凰山村村民郝立良在首钢水厂铁矿河东干选车间6号电铲北侧捡铁矿石时，因捡铁矿石的原因与同在此处捡铁矿石的迁安市大崔庄镇新立庄村的一个小名叫“春生”的男子发生矛盾，被“春生”用拳头打伤。","type":null,"consignationName1":"宋鲁国","cardid1":null,"phone1":"13081130978","consignationName2":"陈立满","cardid2":null,"phone2":"13180426185","delegateAt":"2014-08-05T07:33:05.000+0000","createOrgName":"内保局大企支队","createOrgId":null,"acceptorName":null,"acceptorId":null,"isAccept":null,"refuseReason":null,"acceptAt":null,"acceptComment":null,"overtimeAt":null,"overtimeReason":null,"authorityMajor":null,"criticalDepartment":null,"resultQuerySn":null,"captcha":null,"testerId":null,"testerName":null,"createAt":null,"creatorId":null,"updateAt":null,"updatorId":null,"isDelete":null,"remark":null,"jzSn":null,"xkSn":null,"typeName":null,"isHomicide":null,"isCriminal":null,"createOrgFullname":null,"isTransfer":null,"transferBaseId":null,"overtimeReason2":null,"overtimeReason3":null,"overtimeAt2":null,"overtimeAt3":null,"acceptAtStart":null,"acceptAtEnd":null},"sampleName":null,"sampleNameName":null,"barcode":null,"sampleType":null,"sampleTypeName":null,"measurementUnit":null,"measurementUnitName":null,"color":null,"colorName":null,"count":null,"firstResult":null,"sampleCharacter":null,"sampleCharacterName":null,"acceptedAmount":null,"memberName":"郝立良","gender":null,"genderName":null,"birthplace":null,"birthdate":null,"age":null,"country":null,"countryName":null,"nation":null,"nationName":null,"identity":null,"identityName":null,"idCard":"身份证130283198506074718","company":null,"job":null,"address":null,"noIdReason":null,"declare":null,"remark":null,"packing":null,"packingName":null,"firstResultName":null,"memberRemark":null}]},"errorMessage":"成功"}";
            //解析json
            JSONObject str = JSON.parseObject(result);
            Map<String, Object> list = (Map<String, Object>)str.get("data");
            //案件信息
            List<CaseInfoVo> caseInfoVoList = (List<CaseInfoVo>) JSON.parseArray(list.get("caseInfoVoList").toString(), CaseInfoVo.class);
            //生成excel
            HSSFWorkbook workbook = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            try {
                String filename = null;
                int rowIdx = 1;
                Resource resource = new ClassPathResource("templates/exportCaseMemberSampleInfoList.xls");
                workbook = new HSSFWorkbook(resource.getInputStream());
                HSSFSheet sheet0 = workbook.getSheetAt(0);
                for (CaseInfoVo caseInfoVo1: caseInfoVoList) {
                    row = sheet0.createRow(rowIdx);
                    cell = row.createCell(0);
                    cell.setCellValue(rowIdx);
                    //赋值
                    cell = createCell(cell, row, caseInfoVo1);
                    rowIdx++;
                }

                workbook.setSheetName(0, "毒品");

                filename = "毒品数据统计-" + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + ".xls";

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
                workbook.write(response.getOutputStream());
            } catch (Exception ex) {
                logger.error("导出Excel错误！", ex);
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException ex) {
                    }
                }
            }
        } catch (Exception e) {
            logger.error("导出案件信息失败：" + e.getMessage());
        }
    }

    /**
     * 案件列表导出案件信息
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/excelCaseInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void excelCaseInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody CaseInfoVo caseInfoVo) throws IOException {
        try {
            //传递参数
            Map<String, Object> paramMap = new HashMap<String,Object>();
            paramMap.put("caseInfoVo", caseInfoVo);
            //调取接案平台接口地址
            String url = japtPath+"/japt/findExcelCaseInfo";
            //调取接案平台接口
            logger.info("接收接案平台数据开始：--------------------------------------------");
            String result = TransferUtil.getJaptInfo(paramMap, url);
            logger.info("接收接案平台数据结束：--------------------------------------------");
            logger.info("请求服务返回结果: "+result);
            //String result = "{\"code\":1,\"errorCode\":0,\"data\":{\"caseInfoVoList\":[{\"offset\":0,\"rows\":0,\"orderByClause\":null,\"entity\":{\"baseId\":null,\"caseId\":null,\"consignationId\":null,\"name\":null,\"tempSn\":null,\"caseSn\":\"FYA1306289\",\"consignationMajorSn\":\"2013DP2359\",\"status\":null,\"statusName\":null,\"major\":null,\"majorName\":null,\"isAppend\":null,\"appendSn\":null,\"property\":null,\"propertyName\":null,\"location\":\"海淀区汉庭酒店花园桥店8337房间\",\"crimeAt\":\"2013-12-17T16:00:00.000+0000\",\"brief\":\"2013年12月18日22时许，犯罪嫌疑人丁师锋以非法牟利为目的，在海淀区汉庭酒店花园桥店8337房间内，以人民币3000元的价格向举报人祁蒙蒙贩卖白色可疑晶体1包，后被民警抓获。\",\"type\":null,\"consignationName1\":\"徐振\",\"cardid1\":null,\"phone1\":\"88110680\",\"consignationName2\":\"谢霏\",\"cardid2\":null,\"phone2\":\"88110680\",\"delegateAt\":\"2013-12-18T18:16:16.000+0000\",\"createOrgName\":\"海淀分局恩济庄派出所\",\"createOrgId\":null,\"acceptorName\":null,\"acceptorId\":null,\"isAccept\":null,\"refuseReason\":null,\"acceptAt\":null,\"acceptComment\":null,\"overtimeAt\":null,\"overtimeReason\":null,\"authorityMajor\":null,\"criticalDepartment\":null,\"resultQuerySn\":null,\"captcha\":null,\"testerId\":null,\"testerName\":null,\"createAt\":null,\"creatorId\":null,\"updateAt\":null,\"updatorId\":null,\"isDelete\":null,\"remark\":null,\"jzSn\":null,\"xkSn\":null,\"typeName\":null,\"isHomicide\":null,\"isCriminal\":null,\"createOrgFullname\":null,\"isTransfer\":null,\"transferBaseId\":null,\"overtimeReason2\":null,\"overtimeReason3\":null,\"overtimeAt2\":null,\"overtimeAt3\":null,\"acceptAtStart\":null,\"acceptAtEnd\":null},\"sampleName\":null,\"sampleNameName\":null,\"barcode\":null,\"sampleType\":null,\"sampleTypeName\":null,\"measurementUnit\":null,\"measurementUnitName\":null,\"color\":null,\"colorName\":null,\"count\":null,\"firstResult\":null,\"sampleCharacter\":null,\"sampleCharacterName\":\"晶体\",\"acceptedAmount\":null,\"memberName\":\"祁蒙蒙\",\"gender\":null,\"genderName\":null,\"birthplace\":null,\"birthdate\":null,\"age\":null,\"country\":null,\"countryName\":null,\"nation\":null,\"nationName\":null,\"identity\":null,\"identityName\":null,\"idCard\":\"110224199002141623\",\"company\":null,\"job\":null,\"address\":null,\"noIdReason\":null,\"declare\":null,\"remark\":null,\"packing\":null,\"packingName\":null,\"firstResultName\":\"苯丙胺类\"},{\"offset\":0,\"rows\":0,\"orderByClause\":null,\"entity\":{\"baseId\":null,\"caseId\":null,\"consignationId\":null,\"name\":null,\"tempSn\":null,\"caseSn\":\"FYA1306290\",\"consignationMajorSn\":\"2013DW2721\",\"status\":null,\"statusName\":null,\"major\":null,\"majorName\":null,\"isAppend\":null,\"appendSn\":null,\"property\":null,\"propertyName\":null,\"location\":\"石景山区融景城65号院4号楼下\",\"crimeAt\":\"2013-12-15T16:00:00.000+0000\",\"brief\":\"2013年12月16日，王子豪在上述地点被发现死亡。\",\"type\":null,\"consignationName1\":\"张义军\",\"cardid1\":null,\"phone1\":\"88788178\",\"consignationName2\":\"李明\",\"cardid2\":null,\"phone2\":\"88788178\",\"delegateAt\":\"2013-12-19T01:06:58.000+0000\",\"createOrgName\":\"石景山分局刑侦支队\",\"createOrgId\":null,\"acceptorName\":null,\"acceptorId\":null,\"isAccept\":null,\"refuseReason\":null,\"acceptAt\":null,\"acceptComment\":null,\"overtimeAt\":null,\"overtimeReason\":null,\"authorityMajor\":null,\"criticalDepartment\":null,\"resultQuerySn\":null,\"captcha\":null,\"testerId\":null,\"testerName\":null,\"createAt\":null,\"creatorId\":null,\"updateAt\":null,\"updatorId\":null,\"isDelete\":null,\"remark\":null,\"jzSn\":null,\"xkSn\":null,\"typeName\":null,\"isHomicide\":null,\"isCriminal\":null,\"createOrgFullname\":null,\"isTransfer\":null,\"transferBaseId\":null,\"overtimeReason2\":null,\"overtimeReason3\":null,\"overtimeAt2\":null,\"overtimeAt3\":null,\"acceptAtStart\":null,\"acceptAtEnd\":null},\"sampleName\":null,\"sampleNameName\":null,\"barcode\":null,\"sampleType\":null,\"sampleTypeName\":null,\"measurementUnit\":null,\"measurementUnitName\":null,\"color\":null,\"colorName\":null,\"count\":null,\"firstResult\":null,\"sampleCharacter\":null,\"sampleCharacterName\":null,\"acceptedAmount\":null,\"memberName\":\"王子豪\",\"gender\":null,\"genderName\":null,\"birthplace\":null,\"birthdate\":null,\"age\":null,\"country\":null,\"countryName\":null,\"nation\":null,\"nationName\":null,\"identity\":null,\"identityName\":null,\"idCard\":\"110108199604126011\",\"company\":null,\"job\":null,\"address\":null,\"noIdReason\":null,\"declare\":null,\"remark\":null,\"packing\":null,\"packingName\":null,\"firstResultName\":null}]},\"errorMessage\":\"成功\"}";
            //解析json
            JSONObject str = JSON.parseObject(result);
            Map<String, Object> list = (Map<String, Object>)str.get("data");
            //案件信息
            List<CaseInfoVo> caseInfoVoList = (List<CaseInfoVo>) JSON.parseArray(list.get("caseInfoVoList").toString(), CaseInfoVo.class);
            //生成excel
            HSSFWorkbook workbook = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            try {
                String filename = null;
                int rowIdx = 1;
                Resource resource = new ClassPathResource("templates/exportCaseMemberSampleInfoList.xls");
                workbook = new HSSFWorkbook(resource.getInputStream());
                HSSFSheet sheet0 = workbook.getSheetAt(0);
                for (CaseInfoVo caseInfoVo1: caseInfoVoList) {
                    row = sheet0.createRow(rowIdx);
                    cell = row.createCell(0);
                    cell.setCellValue(rowIdx);
                    //赋值
                    cell = createCell(cell, row, caseInfoVo1);
                    rowIdx++;
                }

                workbook.setSheetName(0, "毒品");

                filename = "毒品数据统计-" + DateUtil.dateToString(new Date(), "yyyyMMddHHmmss") + ".xls";

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
                workbook.write(response.getOutputStream());
            } catch (Exception ex) {
                logger.error("导出Excel错误！", ex);
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException ex) {
                    }
                }
            }
        } catch (Exception e) {
            logger.error("导出案件信息失败：" + e.getMessage());
        }
    }

    /**
     * 给excel赋值
     * @param row
     * @param caseInfoVo1
     * @return
     */
    private HSSFCell createCell(HSSFCell cell, HSSFRow row, CaseInfoVo caseInfoVo1){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //案件编号
        cell = row.createCell(1);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getCaseSn())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getCaseSn());
        }
        //DP号
        cell = row.createCell(2);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getConsignationMajorSn())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getConsignationMajorSn());
        }
        //案件A号
        cell = row.createCell(3);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getJzSn())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getJzSn());
        }
        //送检单位
        cell = row.createCell(4);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getCreateOrgName())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getCreateOrgName());
        }
        //送检人1
        cell = row.createCell(5);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getConsignationName1())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getConsignationName1());
        }
        //送检人1电话
        cell = row.createCell(6);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getPhone1())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getPhone1());
        }
        //送检人2
        cell = row.createCell(7);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getConsignationName2())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getConsignationName2());
        }
        //送检人2电话
        cell = row.createCell(8);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getPhone2())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getPhone2());
        }
        //送检时间
        cell = row.createCell(9);
        if (null == caseInfoVo1.getEntity().getDelegateAt()) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(sim.format(caseInfoVo1.getEntity().getDelegateAt()));
        }
        //被鉴定人
        cell = row.createCell(10);
        if (StringUtils.isBlank(caseInfoVo1.getMemberName())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getMemberName());
        }
        //被鉴定人身份证号
        cell = row.createCell(11);
        if (StringUtils.isBlank(caseInfoVo1.getIdCard())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getIdCard());
        }
        //简要内容
        cell = row.createCell(12);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getBrief())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getBrief());
        }
        //案发地点
        cell = row.createCell(13);
        if (StringUtils.isBlank(caseInfoVo1.getEntity().getLocation())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getEntity().getLocation());
        }
        //案发时间
        cell = row.createCell(14);
        if (null == caseInfoVo1.getEntity().getCrimeAt()) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(sim.format(caseInfoVo1.getEntity().getCrimeAt()));
        }
        //送检物品性状
        cell = row.createCell(15);
        if (StringUtils.isBlank(caseInfoVo1.getSampleCharacterName())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getSampleCharacterName());
        }
        //初检结果
        cell = row.createCell(16);
        if (StringUtils.isBlank(caseInfoVo1.getFirstResultName())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getFirstResultName());
        }
        //送检数量
        cell = row.createCell(17);
        if (StringUtils.isBlank(caseInfoVo1.getSjCount())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getSjCount());
        }
        //检验结果
        cell = row.createCell(18);
        if (StringUtils.isBlank(caseInfoVo1.getTestResult())) {
            cell.setCellValue("");
        } else {
            cell.setCellValue(caseInfoVo1.getTestResult());
        }
        return cell;
    }




    /**
     * 案件查询列表
     * @param request
     * @param response
     * @param caseInfoVo
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectCaseInfoList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectCaseInfoList(HttpServletRequest request, HttpServletResponse response, @RequestBody CaseInfoVo caseInfoVo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //委托单位
            //传递参数
            Map<String, Object> paramMap = new HashMap<String,Object>();
            paramMap.put("caseInfoVo", caseInfoVo);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(caseInfoVo.getPage());
            paramMap.put("pageInfo", pageInfo);
            //调取接案平台接口地址
            String url = japtPath+"/japt/findCaseConsignmentPersonSampleInfo";
            //调取接案平台接口
            logger.info("接收接案平台数据开始：--------------------------------------------");
            String result = TransferUtil.getJaptInfo(paramMap, url);
            logger.info("接收接案平台数据结束：--------------------------------------------");
            logger.info("请求服务返回结果: "+result);
            //解析json
            JSONObject str = JSON.parseObject(result);
            Map<String, Object> list = (Map<String, Object>)str.get("data");
            //案件信息
            List<CaseInfoVo> caseInfoList = (List<CaseInfoVo>) JSON.parseArray(list.get("caseInfoVoList").toString(), CaseInfoVo.class);
            //分页信息
            PageInfo pageInfos = JSON.parseObject(list.get("pageInfo").toString(), PageInfo.class);
            resultMap.put("caseInfoList", caseInfoList);
            resultMap.put("pageInfo", pageInfos);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("查询案件列表失败：" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }
}
