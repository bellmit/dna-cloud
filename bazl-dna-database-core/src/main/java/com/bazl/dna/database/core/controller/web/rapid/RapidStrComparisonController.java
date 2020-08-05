package com.bazl.dna.database.core.controller.web.rapid;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.database.algorithm.result.StrRelativeCompareResult;
import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.algorithm.result.YstrCompareResult;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.rabbitmq.ISendQuickCompareService;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.KinshipCompare;
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.model.po.MatchResultRelative;
import com.bazl.dna.database.service.model.po.MatchResultSame;
import com.bazl.dna.database.service.model.po.MatchResultSameGroup;
import com.bazl.dna.database.service.model.po.MatchResultYstr;
import com.bazl.dna.database.service.model.po.MatchSameResultVo;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.model.po.QuickCompareResultSame;
import com.bazl.dna.database.service.model.po.StrCompare;
import com.bazl.dna.database.service.model.qo.QuickCompareResultSameQuery;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.model.vo.QuickCompareResultSameVo;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.LabServerInfoService;
import com.bazl.dna.database.service.service.MatchResultRelativeService;
import com.bazl.dna.database.service.service.MatchResultSameGroupService;
import com.bazl.dna.database.service.service.MatchResultSameService;
import com.bazl.dna.database.service.service.MatchResultYstrService;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.bazl.dna.database.service.service.QuickCompareResultSameService;
import com.bazl.dna.database.service.service.RapidComparisonService;
import com.bazl.dna.database.utils.ExcelUtil;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 快速比对Controller
 */
@RestController
@RequestMapping("/rapid/str")
public class RapidStrComparisonController extends BaseController {

    @Autowired
    private RapidComparisonService rapidComparisonService;

    @Autowired
    private DnaLocusInfoService dnaLocusInfoServicel;

    @Autowired
    private MatchResultSameService matchResultSameService;

    @Autowired
    private MatchResultYstrService matchResultYstrService;

    @Autowired
    private MatchResultRelativeService matchResultRelativeService;

    @Autowired
    private MatchResultSameGroupService matchResultSameGroupService;

    @Autowired
    private QuickCompareQueueService quickCompareQueueService;
    @Autowired
    private QuickCompareResultSameService quickCompareResultSameService;
    @Autowired
    private DnaStrGeneInfoService dnaStrGeneInfoService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private CaseInfoService caseInfoService;
    @Autowired
    private DnaSampleInfoService dnaSampleInfoService;
    @Autowired
    private LabServerInfoService labServerInfoService;
    @Autowired
    private CasePersonInfoService personInfoService;
    @Autowired
    private ISendQuickCompareService sendQuickCompareService;


    //str同一比对
    @PostMapping(value = "/strCompare")
    public ResponseData strCompare(@RequestBody StrCompare strCompare) {
        try {
            if (strCompare != null) {
                ArrayList<StrSameCompareResult> strSameCompareResults = rapidComparisonService.compareStrGeneInfo(strCompare);
                return new ResponseData(strSameCompareResults);
            }
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.strCompare error.", ex);
            return new ResponseData("同一比对异常！" + ex.getMessage());
        }
    }

    //亲缘三联体比对
    @PostMapping(value = "/kinshipCompare")
    public ResponseData kinshipCompare(KinshipCompare kinshipCompare) {
        try {
            if (kinshipCompare != null) {
                ArrayList<StrRelativeCompareResult> strSameCompareResults = rapidComparisonService.compareKinshipGeneInfo(kinshipCompare);
                return new ResponseData(strSameCompareResults);
            }
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.strCompare error.", ex);
            return new ResponseData("亲缘比对异常！" + ex.getMessage());
        }
    }


    //Ystr同一比对
    @PostMapping(value = "/ystrCompare")
    public ResponseData ystrCompare(StrCompare strCompare) {
        try {
            if (strCompare != null) {
                ArrayList<YstrCompareResult> ystrCompareResults = rapidComparisonService.compareYstrGeneInfo(strCompare);
                return new ResponseData(ystrCompareResults);
            }
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.ystrCompare error.", ex);
            return new ResponseData("Ystr异常！" + ex.getMessage());
        }
    }

    //str基因座管理
    @PostMapping(value = "/strAlleleQuery")
    public ResponseData strAlleleQuery(String name) {
        try {
            int type = 1;
            List<DnaLocusInfo> dnaLocusInfos = dnaLocusInfoServicel.queryAlleleInfos(type,name);
            return new ResponseData(dnaLocusInfos);
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.strAlleleQuery error.", ex);
            return new ResponseData("str基因管理！" + ex.getMessage());
        }
    }

    //str复核标记
    @PostMapping(value = "/updateStrReviewFlag")
    public ResponseData updateStrReviewFlag(String id , String reviewFlag) {
        try {
            MatchResultSame matchResultSame = new MatchResultSame();
            if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(reviewFlag)){
                matchResultSame.setId(Integer.valueOf(id));
                matchResultSame.setReviewFlag(Integer.valueOf(reviewFlag));
            }
            matchResultSameService.updateMatchResultSame(matchResultSame);
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.updateStrReviewFlag error.", ex);
            return new ResponseData("修改异常！" + ex.getMessage());
        }
    }

    //ystr复核标记
    @PostMapping(value = "/updateYStrReviewFlag")
    public ResponseData updateYstrReviewFlag(String id , String reviewFlag) {
        try {
            MatchResultYstr matchResultYstr = new MatchResultYstr();
            if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(reviewFlag)){
                matchResultYstr.setId(Integer.valueOf(id));
                matchResultYstr.setReviewFlag(Integer.valueOf(reviewFlag));
            }
            matchResultYstrService.updateMatchResultYstr(matchResultYstr);
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.updateStrReviewFlag error.", ex);
            return new ResponseData("修改异常！" + ex.getMessage());
        }
    }

    //亲缘复核标记
    @PostMapping(value = "/updateRelativeReviewFlag")
    public ResponseData updateRelativeReviewFlag(String id , String reviewFlag) {
        try {
            MatchResultRelative matchResultRelative = new MatchResultRelative();
            if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(reviewFlag)){
                matchResultRelative.setId(Integer.valueOf(id));
                matchResultRelative.setReviewFlag(Integer.valueOf(reviewFlag));
            }
            matchResultRelativeService.updateMatchResultRelative(matchResultRelative);
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke RapidComparisonController.updateRelativeReviewFlag error.", ex);
            return new ResponseData("修改异常！" + ex.getMessage());
        }
    }

    //数据库检索


    //批量导出excel
    @PostMapping("/exportExcel")
    public ModelAndView exportQyListExcel(HttpServletRequest request, HttpServletResponse response, String groupId) {

        try {


        if (StringUtils.isNotBlank(groupId)) {
            String fileName = "同一比对结果";
            ArrayList<MatchSameResultVo> entryList = new ArrayList<MatchSameResultVo>();

            //查询比对类型
            MatchResultSameGroup resultSameGroup = matchResultSameGroupService.getById(Integer.valueOf(groupId));
            //查询比对结果数据
            List<MatchResultSame> resultSameList = matchResultSameService.selectListByGroupId(Integer.valueOf(groupId));
            for (MatchResultSame matchResultSame : resultSameList) {
                //查询检材数据
                DnaSampleInfo sampleInfo = dnaSampleInfoService.getById(matchResultSame.getSampleaId());
                //查询案件数据
                CaseInfo caseInfo = caseInfoService.getById(sampleInfo.getCaseId());
                MatchSameResultVo matchSameResultVo = new MatchSameResultVo();
                matchSameResultVo.setCompareType(resultSameGroup.getGroupType());
                matchSameResultVo.setCaseName(caseInfo.getCaseName());
                matchSameResultVo.setSampleNo(sampleInfo.getSampleLabNo());
                matchSameResultVo.setSampleName(sampleInfo.getSampleName());
                matchSameResultVo.setInstoredType(sampleInfo.getInstoreDataType());
                entryList.add(matchSameResultVo);
            }
            List<Map<String, Object>> list = createExcelRecord(entryList);
            String columnNames[] = {"案件名称", "案件破获状态", "样本条码号", "检材名称", "入库样本类型", "比对类型", "所属分局"};//列名
            String keys[] = {"caseName", "1", "sampleNo", "sampleName", "instoredType", "2", "delegateOrgName"};//map中的key
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }

        }
        }catch (Exception e){

        }


        return null;
    }

    private List<Map<String, Object>> createExcelRecord(List<MatchSameResultVo> list) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);//caseName","1","sampleNo ","sampleName","instoredType","1","delegateOrgName"
        for (int j = 0; j < list.size(); j++) {
            MatchSameResultVo vote = list.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("caseName", vote.getCaseName());
            mapValue.put("1", "未破获");
            mapValue.put("sampleNo", vote.getSampleNo());
            mapValue.put("sampleName", vote.getSampleName());
            mapValue.put("instoredType", vote.getInstoredType());
            mapValue.put("delegateOrgName", vote.getDelegateOrgName());
            listmap.add(mapValue);
        }
        return listmap;
    }

    /*
    *   快速比对  同型比对---样本编号数据检索
    * */
    @RequestMapping(value = "/selectSTRSampleNo", produces = "application/json;charset=UTF-8")
    public ResponseData selectSTRSampleNoList(String sampleNo, String dataType) throws Exception {
        if (StringUtils.isNotBlank(sampleNo)){
        		DnaSampleInfoVo sampleInfo = null;
        		if (StringUtils.isNotBlank(dataType)) {
        			// lims库
        			DataSource ds = (DataSource)ContextHolder.dataSourceMap.get(DataSourceConstants.DataSourceType.SECOND.name());
        			Connection con = ds.getConnection();
        			String sql = "select s.SAMPLE_ID,s.SAMPLE_NO,s.SAMPLE_NAME,g.GENE_INFO " + 
        					"	from LIMS_SAMPLE_INFO_DNA s, LIMS_SAMPLE_GENE g where s.SAMPLE_ID=g.SAMPLE_ID and s.SAMPLE_NO='"+sampleNo+"'";
        			List<Map<String, String>> list = DataSourceUtil.execute(con, sql);
        			con.close();
        			if (!list.isEmpty()) {
        				sampleInfo = new DnaSampleInfoVo();
        				sampleInfo.setSampleName(list.get(0).get("sample_name"));
        				sampleInfo.setSampleLabNo(list.get(0).get("sample_no"));
        				sampleInfo.setGeneInfo(GeneTransFormUtils.geneForma(list.get(0).get("gene_info")));
        			}
        		} else {
        			sampleInfo = dnaStrGeneInfoService.selectBySampleLabNo(sampleNo);
	            //基因格式转换
	            if (sampleInfo != null){
	                sampleInfo.setGeneInfo(GeneTransFormUtils.geneFormatList(sampleInfo.getGeneInfo()));
	            }
        		}
        		return new ResponseData(sampleInfo);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *      快速比对--同一比对--提交比对
    * */
    @RequestMapping(value = "/submitSameCompareQueue", produces = "application/json;charset=UTF-8")
    public ResponseData submitSameCompareQueue(@CurrentUser AuthUser user, @RequestBody QuickCompareQueue queue) throws Exception {
        if (queue != null && queue.getPendingSampleaGeneInfo() != null){
            //获取用户信息
            queue.setCompareMode(Constants.COMPARE_MODE_STR);
            queue.setQueueStatus(Constants.COMPARE_STATUS_NO);//比对状态
            queue.setCreatePersonId(user.getId());
            queue.setCreatePersonName(user.getUsername());
            queue.setCreateDatetime(LocalDateTime.now());
            quickCompareQueueService.insert(queue);
            Integer id = queue.getId();
            
            // 发送比对
            sendQuickCompareService.sendQuickStrCompare(queue);
            logger.info("Send Compare Queue str:", queue.getId());
            queue.setQueueStatus(Constants.COMPARE_STATUS_WAITING);
            queue.setUpdateDatetime(LocalDateTime.now());
            quickCompareQueueService.updateById(queue);
            return new ResponseData(id);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *   快速比对   同一比对---提交比对后结果列表
    * */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/findResultSame", produces = "application/json;charset=UTF-8")
    public ResponseData findResultSameList(Integer id) throws Exception {
        Map<String, Object> resultObj = new HashMap<>();
            String queueStatus = null;
        if (id != null){
            List<QuickCompareResultSameVo> quickList = quickCompareResultSameService.selectByQuickSameQueueId(id);
            QuickCompareQueue compareQueue = quickCompareQueueService.selectByPrimaryKey(id); //通过比中序列ID，查询比中状态
            if (null !=compareQueue && StringUtils.isNotBlank(compareQueue.getQueueStatus())){
               if (compareQueue.getQueueStatus().equals(Constants.FLAG_TRUE)){ //如果比中状态为1，则已经比中完成
                   queueStatus ="1";
                }else if (compareQueue.getQueueStatus().equals(Constants.FLAG_FALSE)){
                   queueStatus ="0";
               }
            }
            HashSet<String> caseList = new HashSet<>();
            int personCount = 0;//标记比中人员数量
            int evidenceCount = 0; //标记比中物证数量
            if (ListUtils.isNotNullAndEmptyList(quickList)){
                for (QuickCompareResultSameVo mixVo : quickList){
                    //计算比中案件数
                    caseList.add(mixVo.getMatchCaseNo());
                    if (mixVo.getSampleId() != null) {
	                		DnaSampleInfo sampleInfo = dnaSampleInfoService.getById(mixVo.getSampleId());
	                		LabServerInfo labServerInfo = labServerInfoService.getOne(new QueryWrapper<LabServerInfo>().eq("LAB_SERVER_NO", mixVo.getLabServerNo()));
	                		if (labServerInfo != null)
	                			mixVo.setLabServerName(labServerInfo.getLabServerName());
	                		if (sampleInfo != null) {
	                			CasePersonInfo casePersonInfo = personInfoService.getById(sampleInfo.getRefPersonId());
	                    		if (casePersonInfo != null) {
	                    			String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PERSON_TYPE, casePersonInfo.getPersonType());
	                    			mixVo.setPersonTypeName(personType);
	                    		}
	                		}
	                }
                    //区分比中人员和比中样本个数
                    if (StringUtils.isNotBlank(mixVo.getEvidenceFlag())){
                        if (mixVo.getEvidenceFlag().equals(Constants.COMPARE_GROUP_TYPE_PERSON_OBJECT)){
                            mixVo.setPersonTypeName("现场物证");
                            evidenceCount ++;
                        }else {
                            personCount ++;
                        }
                    }
                    //获取基因信息
                    LinkedHashMap<String, Object> sampleGeneInfo = JSON.parseObject(mixVo.getMatchedGeneDetails(), LinkedHashMap.class);
                    mixVo.setGeneMap(sampleGeneInfo);
                }
            }
            //总比中条数
            int count = quickCompareResultSameService.selectBySameQueueIdCount(id);
            resultObj.put("queueStatus",queueStatus); //比对状态结果
            resultObj.put("quickResultList",quickList);
            resultObj.put("personCount",personCount);
            resultObj.put("caseCount",caseList.size());
            resultObj.put("evidenceCount",evidenceCount);
            resultObj.put("count",count);
            return new ResponseData(resultObj);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
    
    /**
	 * 列表
	 * @param query
	 * @return
	 */
	@PostMapping("list")
	public ResponseData list(@RequestBody QuickCompareResultSameQuery query) {
		Map<String, Object> result = quickCompareResultSameService.findList(query);
		return new ResponseData(result);
	}
	
	/**
	 * 比中结果详情列表
	 * @param id
	 * @return
	 */
	@GetMapping("get/{id}")
	public ResponseData get(@PathVariable Integer id) {
		
		QuickCompareResultSame entity = quickCompareResultSameService.getById(id);
		List<QuickCompareResultSameVo> list = quickCompareResultSameService.selectByQuickSameQueueId(entity.getQuickCompareQueueId());
		
		Set<String> caseList = Sets.newHashSet();
		int countPersonType = 0;
		int countSampleType = 0;
		for (QuickCompareResultSameVo vo : list) {
			caseList.add(vo.getMatchCaseNo());
			if (vo.getEvidenceFlag() == "1") {
				countSampleType++;
			} else {
				countPersonType++;
			}
			//获取基因信息
	        @SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> sampleGeneInfo = JSON.parseObject(vo.getMatchedGeneDetails(), LinkedHashMap.class);
	        vo.setGeneMap(sampleGeneInfo);
		};
		
		
		Map<String, Object> result = Maps.newHashMap();
		result.put("info", entity);
		result.put("list", list);
		result.put("countCaseInfo", caseList.size());
		result.put("countPersonType", countPersonType);
		result.put("countSampleType", countSampleType);
		
		return new ResponseData(result);
	}
	
}
