package com.bazl.dna.database.core.controller.web.rapid;


import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.rabbitmq.ISendQuickCompareService;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.model.po.QuickCompareResultYstr;
import com.bazl.dna.database.service.model.qo.QuickCompareResultYstrQuery;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.model.vo.QuickCompareResultYstrVo;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;
import com.bazl.dna.database.service.service.LabServerInfoService;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.bazl.dna.database.service.service.QuickCompareResultYstrService;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * YSTR快速比对Controller
 */
@RestController
@RequestMapping("/rapid/ystr")
public class RapidYstrComparisonController extends BaseController {

    @Autowired
    private DnaYstrGeneInfoService dnaYstrGeneInfoService;
    @Autowired
    private DnaLocusInfoService dnaLocusInfoService;
    @Autowired
    private QuickCompareQueueService quickCompareQueueService;
    @Autowired
    private QuickCompareResultYstrService quickCompareResultYstrService;
    @Autowired
    private DnaSampleInfoService dnaSampleInfoService;
    @Autowired
    private LabServerInfoService labServerInfoService;
    @Autowired
    private CasePersonInfoService personInfoService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private ISendQuickCompareService sendQuickCompareService;

    /**
     * 根据样本编号查询YSTR分型数据--liuchang
     * @param sampleNo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectYstrSampleNoList" ,produces = "application/json;charset=UTF-8")
    public ResponseData selectYstrSampleNoList(String sampleNo, String dataType) throws Exception {
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
	    			sampleInfo = dnaYstrGeneInfoService.selectBySampleLabNo(sampleNo);
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

    /**
     * YSTR提交快速比对--liuchang
     * @param queue
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submitYstrCompareQueue", produces = "application/json;charset=UTF-8")
    public ResponseData submitYstrCompareQueue(@CurrentUser AuthUser user, @RequestBody QuickCompareQueue queue) throws Exception {
        if (queue != null){
            //获取用户信息
            queue.setCompareMode(Constants.COMPARE_MODE_YSTR);//YSTR比对模式
            queue.setQueueStatus(Constants.COMPARE_STATUS_NO);//比对状态
            queue.setCreatePersonId(user.getId());//创建人
            queue.setCreatePersonName(user.getUsername());
            queue.setCreateDatetime(LocalDateTime.now());//创建时间
            quickCompareQueueService.insert(queue);
            Integer id = queue.getId();
            
            // 发送比对
            sendQuickCompareService.sendQuickYstrCompare(queue);
            logger.info("Send Compare Queue ystr:", queue.getId());
            queue.setQueueStatus(Constants.COMPARE_STATUS_WAITING);
            queue.setUpdateDatetime(LocalDateTime.now());
            quickCompareQueueService.updateById(queue);
            return new ResponseData(id);
        }else{
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     * 快速比对  添加基因座--liuchang
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addYstrLocus", produces = "application/json;charset=UTF-8")
    @SuppressWarnings("all")
    public ResponseData addYstrLocus(String type) throws Exception {
        //type 1:STR 2:Y-STR 3:混合STR
        if (type!=null){
            List<DnaLocusInfo> dnaLocusInfos = null;
            try {
                if (type.equals(Constants.GENE_TYPE_STR)){
                    dnaLocusInfos = dnaLocusInfoService.listByLocusType(Constants.GENE_TYPE_STR);//查询STR基因座
                }else if (type.equals(Constants.GENE_TYPE_YSTR)){
                    dnaLocusInfos = dnaLocusInfoService.listByLocusType(Constants.GENE_TYPE_YSTR);//查询YSTR基因座
                }else if (type.equals(Constants.GENE_TYPE_MIX)){
                    dnaLocusInfos = dnaLocusInfoService.listByLocusType(Constants.GENE_TYPE_MIX);//查询混合STR基因座
                }
                return new ResponseData(dnaLocusInfos);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseData("YSTR基因座列表出现异常！" + e.getMessage());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     * 快速比对   YSTR比对-提交比对后结果列表--liuchang
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/findResultYstr", produces = "application/json;charset=UTF-8")
    public ResponseData findResultYstr(Integer id) throws Exception {
        Map<String, Object> resultObj = new HashMap<>();
        if (id != 0 && id !=null){
            HashSet<String> caseList = new HashSet<>();
            int personCount = 0;//标记比中人员数量
            int evidenceCount = 0; //标记比中物证数量
            String  queueStatus = null;//比中状态
            List<QuickCompareResultYstrVo> quickList = quickCompareResultYstrService.selectByQuickCompareQueueId(id);//根据序列ID 查询YSTR比中结果详情
            QuickCompareQueue compareQueue = quickCompareQueueService.selectByPrimaryKey(id);
            if (null!=compareQueue &&StringUtils.isNotBlank(compareQueue.getQueueStatus())){
                if (compareQueue.getQueueStatus().equals(Constants.FLAG_TRUE)){
                       queueStatus ="1";
                }else if (compareQueue.getQueueStatus().equals(Constants.FLAG_FALSE)){
                       queueStatus= "0";
                }
            }
            if (ListUtils.isNotNullAndEmptyList(quickList)){
                for (QuickCompareResultYstrVo ystrVo : quickList){
                    //计算比中案件数
                    caseList.add(ystrVo.getMatchCaseNo());
                    if (ystrVo.getSampleId() != null) {
                    		DnaSampleInfo sampleInfo = dnaSampleInfoService.getById(ystrVo.getSampleId());
                    		LabServerInfo labServerInfo = labServerInfoService.getOne(new QueryWrapper<LabServerInfo>().eq("LAB_SERVER_NO", sampleInfo.getLabServerNo()));
                    		if (labServerInfo != null) {
                                ystrVo.setLabServerName(labServerInfo.getLabServerName());
                            }
                    		if (sampleInfo != null) {
                    			CasePersonInfo casePersonInfo = personInfoService.getById(sampleInfo.getRefPersonId());
	                    		if (casePersonInfo != null) {
	                    			String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PERSON_TYPE, casePersonInfo.getPersonType());
	                    			ystrVo.setPersonTypeName(personType);
	                    		}
                            }
                    }
                    
                    //区分比中人员和比中样本个数
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(ystrVo.getEvidenceFlag())){
                        if (ystrVo.getEvidenceFlag().equals(Constants.COMPARE_GROUP_TYPE_PERSON_OBJECT)){
                            ystrVo.setPersonTypeName("现场物证");
                            evidenceCount ++;
                        }else {
                            personCount ++;
                        }
                    }
                    //获取基因信息
                    LinkedHashMap<String, Object> sampleGeneInfo = JSON.parseObject(ystrVo.getMatchedGeneDetails(), LinkedHashMap.class);
                    ystrVo.setGeneMap(sampleGeneInfo);

                }
            }
            //总比中条数
            int count = quickCompareResultYstrService.selectByQueueIdCount(id);


            resultObj.put("queueStatus",queueStatus); //比中完成状态 0 未比中完成 1已比中完成
            resultObj.put("quickResultList",quickList);//快速比对结果集合
            resultObj.put("personCount",personCount);//人员数
            resultObj.put("caseCount",caseList.size());//案件数
            resultObj.put("evidenceCount",evidenceCount);//物证数
            resultObj.put("count",count);//比中条数
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
	public ResponseData list(@RequestBody QuickCompareResultYstrQuery query) {
		Map<String, Object> result = quickCompareResultYstrService.findList(query);
		return new ResponseData(result);
	}
	
	/**
	 * 比中结果详情列表
	 * @param id
	 * @return
	 */
	@GetMapping("get/{id}")
	public ResponseData get(@PathVariable Integer id) {
		
		QuickCompareResultYstr entity = quickCompareResultYstrService.getById(id);
		List<QuickCompareResultYstrVo> list = quickCompareResultYstrService.selectByQuickCompareQueueId(entity.getQuickCompareQueueId());
		
		Set<String> caseList = Sets.newHashSet();
		int countPersonType = 0;
		int countSampleType = 0;
		for (QuickCompareResultYstrVo vo : list) {
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
