package com.bazl.dna.database.core.controller.web.rapid;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.rabbitmq.ISendQuickCompareService;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.model.vo.QuickCompareResultMixVo;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.DnaMixGeneInfoService;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.bazl.dna.database.service.service.QuickCompareResultMixService;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceConstants;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.util.GeneTransFormUtils;

/**
 * 快速比对Controller
 */
@RestController
@RequestMapping("/rapid/mix")
public class RapidMixComparisonController extends BaseController {

    @Autowired
    private DnaMixGeneInfoService dnaMixGeneInfoService;
    @Autowired
    private DnaLocusInfoService dnaLocusInfoService;
    @Autowired
    private QuickCompareQueueService quickCompareQueueService;
    @Autowired
    private QuickCompareResultMixService quickCompareResultMixService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private ISendQuickCompareService sendQuickCompareService;

    /*
    *   快速比对  混合STR比对---样本编号数据检索
    * */
    @GetMapping(value = "/selectMixedSampleNoList")
    public ResponseData selectMixedSampleNoList(String sampleNo, String dataType) throws Exception {
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
	    			sampleInfo = dnaMixGeneInfoService.selectBySampleLabNo(sampleNo);
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
    *   快速比对  添加基因座
    * */
    @RequestMapping(value = "/addMixedSTRLocus", produces = "application/json;charset=UTF-8")
    public ResponseData addMixedSTRLocus(String type) throws Exception {
        //type 1:STR 2:Y-STR 3:混合STR
        if (StringUtils.isNotBlank(type)){
            List<DnaLocusInfo> dnaLocusInfos = null;
            try {
                if (type.equals(Constants.GENE_TYPE_STR)){
                    dnaLocusInfos = dnaLocusInfoService.listByLocusType(Constants.GENE_TYPE_STR);
                }else if (type.equals(Constants.GENE_TYPE_YSTR)){
                    dnaLocusInfos = dnaLocusInfoService.listByLocusType(Constants.GENE_TYPE_YSTR);
                }else if (type.equals(Constants.GENE_TYPE_MIX)){
                    dnaLocusInfos = dnaLocusInfoService.listByLocusType(Constants.GENE_TYPE_STR);
                }
                return new ResponseData(dnaLocusInfos);
            } catch (Exception e) {
                return new ResponseData("混合STR基因座列表出现异常！" + e.getMessage());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *   快速比对   混合STR比对---提交比对
    * */
    @RequestMapping(value = "/submitMixSTRCompareQueue", produces = "application/json;charset=UTF-8")
    public ResponseData submitMixSTRCompareQueue(@CurrentUser AuthUser user, @RequestBody QuickCompareQueue queue) throws Exception {
        if (queue != null && queue.getPendingSampleaGeneInfo() != null){
            //获取用户信息
            queue.setCompareMode(Constants.COMPARE_MODE_MIX_STR);//比对模式
            queue.setQueueStatus(Constants.COMPARE_STATUS_NO);//比对状态
            queue.setCreatePersonId(user.getId());//创建人
            queue.setCreatePersonName(user.getUsername());
            queue.setCreateDatetime(LocalDateTime.now());//创建时间
            quickCompareQueueService.insert(queue);
            Integer id = queue.getId();
            
            // 发送比对
            sendQuickCompareService.sendQuickMixCompare(queue);
            logger.info("Send Compare Queue mix:", queue.getId());
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
    *   快速比对   混合STR比对---提交比对后结果列表
    * */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/findResultMixed", produces = "application/json;charset=UTF-8")
    public ResponseData findResultMixedList(Integer id) throws Exception {
        Map<String, Object> resultObj = new HashMap<>();
        if (id != null){
            List<QuickCompareResultMixVo> quickList = quickCompareResultMixService.selectByQuickCompareQueueId(id);
             HashSet<String> caseList = new HashSet<>();
            int personCount = 0;//标记比中人员数量
            int evidenceCount = 0; //标记比中物证数量
            String queueStatus = null;//比对完成状态
            QuickCompareQueue compareQueue = quickCompareQueueService.selectByPrimaryKey(id);
            if (null !=compareQueue&&StringUtils.isNotBlank(compareQueue.getQueueStatus())){
                if (compareQueue.getQueueStatus().equals(Constants.FLAG_TRUE)){
                    queueStatus ="1";
                }else if (compareQueue.getQueueStatus().equals(Constants.FLAG_FALSE)){
                    queueStatus ="0";
                }
            }
            if (ListUtils.isNotNullAndEmptyList(quickList)){
                for (QuickCompareResultMixVo mixVo : quickList){
                    //计算比中案件数
                    caseList.add(mixVo.getMatchCaseNo());
                    //区分比中人员和比中样本个数
                    if (StringUtils.isNotBlank(mixVo.getEvidenceFlag())){
                        if (mixVo.getEvidenceFlag().equals(Constants.COMPARE_GROUP_TYPE_PERSON_OBJECT)){
                            mixVo.setPersonTypeName("现场物证");
                            evidenceCount ++;
                        }else {
                            personCount ++;
                        }
                    }
                    //案件人员类型
                    String personType = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PERSON_TYPE, mixVo.getPersonType());
                    mixVo.setPersonTypeName(personType);
                    //获取基因信息
                    LinkedHashMap<String, Object> sampleGeneInfo = JSON.parseObject(mixVo.getMatchedGeneDetails(), LinkedHashMap.class);
                    mixVo.setGeneMap(sampleGeneInfo);
                    /*QuickCompareQueue quickCompareQueue = quickCompareQueueService.selectByPrimaryKey(mixVo.getQuickCompareQueueId());
                    String matchedGeneDetails = mixVo.getMatchedGeneDetails();
                    if (quickCompareQueue != null && StringUtils.isNotBlank(matchedGeneDetails)){
                        String srcGene = GeneTransFormUtils.geneExchange(quickCompareQueue.getPendingSampleaGeneInfo());
                        String tarGene = GeneTransFormUtils.geneExchange(matchedGeneDetails);
                        Map<String, Object> map = GeneTransFormUtils.compareResultFlag(srcGene, tarGene, 0);
                        mixVo.setGeneMap(map);
                    }*/
                }
            }
            //总比中条数
            int count = quickCompareResultMixService.selectByQueueIdCount(id);
            resultObj.put("queueStatus",queueStatus);//比对完成状态
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

}
