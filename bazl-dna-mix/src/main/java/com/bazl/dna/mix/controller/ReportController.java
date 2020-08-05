package com.bazl.dna.mix.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.controller.base.error.ErrorMsgManager;
import com.bazl.dna.mix.model.vo.RapiGeneComparisonVo;
import com.bazl.dna.mix.utils.AnalysisGPMUtils;
import com.bazl.dna.mix.utils.AnalysisPDFUtils;
import com.bazl.dna.mix.utils.GeneMixCompareUtil;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.mix.utils.SampleNumUtil;
import com.bazl.dna.mix.utils.StitchingGeneUtils;
import com.bazl.dna.util.RequestUtils;

/**
 * 上传报告
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

	@Autowired
    private GeneMixCompareUtil geneMixCompareUtil;
	
	@Autowired
	private SampleNumUtil sampleNumUtil;
	
	@Value("${filePath}")
	private String filePath;
	
	/**
     * STRmix拆分报告解析接口
     *
     * @param request
     * @param response
     * @param splitReportFiles //strMix文件
     * @param sampleId         //检材id
     * @param caseId           //案件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/strMixSplitReportFileList")
    public ResponseData strMixSplitReportFileList(HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam("splitReportFiles") MultipartFile splitReportFiles, String geneInfo) throws IOException {
        try {
        		// 解析文件
        		if (splitReportFiles != null) {
        			// strMix
        			RapiGeneComparisonVo comparisonVo = AnalysisPDFUtils.getDataPDFDat(request,splitReportFiles, filePath);
        			
        			Map<String, Object> result = buildGene(comparisonVo, geneInfo);
            		if (result != null) {
            			return new ResponseData(result); 
            		}
        		} 
        		
        		return new ResponseData(ErrorCode.ERR_DATA_EMPTY, ErrorMsgManager.GetErrorMsg(ErrorCode.ERR_DATA_EMPTY));
        } catch (Exception e) {
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 
            		ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
        
    }
    
    /**
     * GPM拆分报告解析接口
     * @param request
     * @param response
     * @param splitReportFiles
     * @param geneInfo
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/gpmMixSplitReportFileList")
    public ResponseData gpmSplitReportFileList(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("splitReportFiles") MultipartFile splitReportFiles, String geneInfo) throws IOException {
        try {
	    		// 解析文件
	    		if (splitReportFiles != null) {
	    			// gpm
	    			RapiGeneComparisonVo comparisonVo = AnalysisGPMUtils.build(request, splitReportFiles, filePath);
	             
	             Map<String, Object> result = buildGene(comparisonVo, geneInfo);
         		if (result != null) {
         			return new ResponseData(result); 
         		}
	    		}
	    		
	    		return new ResponseData(ErrorCode.ERR_DATA_EMPTY, ErrorMsgManager.GetErrorMsg(ErrorCode.ERR_DATA_EMPTY)); 
        } catch (Exception e) {
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 
            		ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }
    
    private Map<String, Object> buildGene(RapiGeneComparisonVo comparisonVo, String geneInfo) {
    		if (geneInfo != null) {
	    		//根据基因id查询基因信息
//	        List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleGeneBySampleId(sampleId);
    			@SuppressWarnings("unchecked")
			Map<String, List<String>> mixedSampleGeneInfo = JSONObject.parseObject(geneInfo, LinkedHashMap.class);
            //判空
            if (mixedSampleGeneInfo != null) {
				// 解析报告比对
                return geneMixCompareUtil.compareResultStrMix(mixedSampleGeneInfo, comparisonVo, null, null);
            }
    		}
    		return null;
    }
    
    /**
     * strmix解析报告
     * @param request
     * @param splitReportFiles
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/strmixReportFile")
    public ResponseData strmixReportFile(HttpServletRequest request, 
    			@RequestParam("splitReportFiles") MultipartFile splitReportFiles) throws Exception {
    		//获取登录信息
        AuthUser user = RequestUtils.getAuthUser();
		if (user == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
    		// 解析文件
		if (splitReportFiles != null) {
			// strmix
			RapiGeneComparisonVo comparisonVo = AnalysisPDFUtils.getDataPDFDat(request,splitReportFiles, filePath);
			Map<String, Object> result = buildReport(comparisonVo, user);
			return new ResponseData(result);
		}
		return new ResponseData(ErrorCode.ERR_DATA_EMPTY, ErrorMsgManager.GetErrorMsg(ErrorCode.ERR_DATA_EMPTY));
    }
    
    /**
     * gpm解析报告
     * @param request
     * @param splitReportFiles
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gpmReportFile")
    public ResponseData gpmReportFile(HttpServletRequest request, 
    			@RequestParam("splitReportFiles") MultipartFile splitReportFiles) throws Exception {
    		//获取登录信息
        AuthUser user = RequestUtils.getAuthUser();
		if (user == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
    		// 解析文件
		if (splitReportFiles != null) {
			// gmp
			RapiGeneComparisonVo comparisonVo = AnalysisGPMUtils.build(request, splitReportFiles, filePath);
			Map<String, Object> result = buildReport(comparisonVo, user);
			return new ResponseData(result);
		}
		return new ResponseData(ErrorCode.ERR_DATA_EMPTY, ErrorMsgManager.GetErrorMsg(ErrorCode.ERR_DATA_EMPTY));
    }
    
    private Map<String, Object> buildReport(RapiGeneComparisonVo comparisonVo, AuthUser user) { 
    	
    		List<Map<String, Object>> resultList = StitchingGeneUtils.geneComparisonVo(comparisonVo);
        //解析比对
        if (ListUtils.isNotNullAndEmptyList(resultList)) {
        		Map<String, Object> result = new HashMap<String, Object>();
            result = geneMixCompareUtil.compareStitchingResultStrMix(resultList, comparisonVo);

            //标识  混合id  区分STR导入
            String mixId = UUID.randomUUID().toString();
            result.put("mixId",mixId);
            //手动导入生成样本编号
            String sampleNo = sampleNumUtil.getNextSampleNoVal(user.getId(), "SAMPLE_NO");
            result.put("sampleNo",sampleNo);
            return result;
        }
        return null;
    }
}
