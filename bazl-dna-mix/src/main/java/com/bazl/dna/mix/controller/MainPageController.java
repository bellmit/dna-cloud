package com.bazl.dna.mix.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.client.CaseInfoServerClient;
import com.bazl.dna.mix.client.FilesServerClient;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.SampleDataServicr;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.GlobalConstants;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.controller.base.error.ErrorMsgManager;
import com.bazl.dna.mix.dao.MobileNewsMapper;
import com.bazl.dna.mix.model.po.AutoanalysisMix;
import com.bazl.dna.mix.model.po.CaseInfo;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.GeneInfoBean;
import com.bazl.dna.mix.model.po.MainpageBean;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.PersonInfo;
import com.bazl.dna.mix.model.po.QualtyPersonnel;
import com.bazl.dna.mix.model.po.SampleInfo;
import com.bazl.dna.mix.model.po.SingleSampleGene;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.model.vo.CaseMixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MixMatchedSupectVo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SampleInfoVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.service.CaseInfoService;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.DictInfoService;
import com.bazl.dna.mix.service.DictItemService;
import com.bazl.dna.mix.service.MatchResultMixSingleService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.service.PersonInfoService;
import com.bazl.dna.mix.service.QualtyPersonnelService;
import com.bazl.dna.mix.service.SampleInfoService;
import com.bazl.dna.mix.service.SingleSampleGeneService;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import com.bazl.dna.mix.utils.ExportExcelKit;
import com.bazl.dna.mix.utils.FileUtils;
import com.bazl.dna.mix.utils.GeneCompareUtil;
import com.bazl.dna.mix.utils.GeneMixCompareUtil;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.InitializationData;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.mix.utils.RedisUtils;
import com.bazl.dna.mix.utils.ResultPageListUtils;
import com.bazl.dna.mix.utils.SampleNumUtil;
import com.bazl.dna.util.RequestUtils;

/**
 * Created by lizhihua on 2019-07-04.
 * 主页面Controller
 */
@RestController
@RequestMapping("/main")
public class MainPageController extends BaseController {

    @Autowired
    MixedSampleGeneService mixedSampleGeneService;
    @Autowired
    SingleSampleGeneService singleSampleGeneService;
    @Autowired
    SplitedSampleGeneService splitedSampleGeneService;
    @Autowired
    QualtyPersonnelService qualtyPersonnelService;
    @Autowired
    CaseInfoService caseInfoService;
    @Autowired
    SampleInfoService sampleInfoService;
    @Autowired
    PersonInfoService personInfoService;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Autowired
    DictItemService dictItemService;
    @Autowired
    DictInfoService dictInfoService;
    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    MobileNewsMapper mobileNewsMapper;
    @Autowired
    GeneCompareUtil geneCompareUtil;
    @Autowired
    GeneformatUtils geneformatUtils;
    @Autowired
    SampleNumUtil sampleNumUtil;

    @Autowired
    MatchResultMixSingleService matchResultMixSingleService;
    
    @Autowired
    GeneListServerClient geneListServerClient;
    //混合匹配下限
    @Value("${minSameMixCount}")
    private int minSameMixCount;
    //导出文档名称
    @Value("${excelName}")
    private String excelName;
    //导出数据后在excel表格中左下角显示的工作簿名称（注意：不是导出后的文件名）
    @Value("${sheetName}")
    private String sheetName;
    //基因信息转换条件
    @Value("${Gen}")
    private int gen;

    @Autowired
    CaseInfoServerClient caseInfoServerClient;
    @Autowired
    SampleDataServicr sampleDataServicr;
    @Autowired
    FilesServerClient filesServerClient;


    /**
     * 首页查询最新的混合样本比中嫌疑人的比中列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/latestMatchedSuspectList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean latestMatchedSuspectList(HttpServletRequest request, HttpServletResponse response, Integer lastestCount) throws IOException {
        try {
        		AuthUser user = RequestUtils.getAuthUser();
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            //从缓存里查数据
            List<MixMatchedSupectVo> caseList = (List<MixMatchedSupectVo>) redisUtils.get("latestMatchedSuspectList_" + userId);
            if (ListUtils.isNotNullAndEmptyList(caseList) ){
                return new ResultBean(ResultBean.CODE_SUCCESS, 0, caseList, ResultBean.NAME_SUCCESS);
            }else {
                //TODO 2.1查询最新的混合样本比中嫌疑人样本的列表
                List<MixMatchedSupectVo> caseInfoList = mixedSampleGeneService.selectMixMatchedSupectList(lastestCount,userId);
                //将List放入缓存中 缓存时间设置为1小时
                redisUtils.set("latestMatchedSuspectList_" + userId, caseInfoList,3600);

                // 首页查询混合样本比中嫌疑人的总数
//            int suspectsNumber = mixedSampleGeneService.selectMatchedSuspectCount();

//            resultMap.put("caseInfoList", caseInfoList);

//            resultMap.put("suspectsNumber", suspectsNumber);
                return new ResultBean(ResultBean.CODE_SUCCESS, 0, caseInfoList, ResultBean.NAME_SUCCESS);
            }

        } catch (Exception e) {

            logger.error("查询最新的混合样本比中嫌疑人的比中列表失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 首页查询最新的混合样本比中嫌疑人的比中总数
     */
    @RequestMapping(value = "/latestMatchedSuspectCount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean latestMatchedSuspectCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //混合样本比中嫌疑人的总数
        try {
            //TODO 2.2查询混合样本比中嫌疑人的总数
//            suspectsNumber = mixedSampleGeneService.selectMatchedSuspectCount();
            //获取登录人信息
        		AuthUser user = RequestUtils.getAuthUser();
            //首页查询混合样本比中嫌疑人的比中条数
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            MixedSampleGeneVo query = new MixedSampleGeneVo();
            query.setUserId(userId);
            int suspectsNumber = mixedSampleGeneService.selectMatchedSuspectCountByQuery(query);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, suspectsNumber, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {

            logger.error("2.2查询混合样本比中嫌疑人的总数" + e.getMessage());

            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }


    /**
     * 首页查询更多混合样本比中嫌疑人的比中列表
     *
     * @param request
     * @param response
     * @param pageInfo
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/moreMatchedSuspectList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean moreMatchedSuspectList(HttpServletRequest request,
                                             HttpServletResponse response,
                                             MixedSampleGeneVo query,
                                             PageInfo pageInfo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        try {
        		AuthUser user = RequestUtils.getAuthUser();
            //初始化查询条件
            query = InitializationData.mixedSampleGeneVoVoQuery(query);
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            query.setUserId(userId);

            //TODO 2.3查询更多混合样本比中嫌疑人的比中列表
            List<MixMatchedSupectVo> caseInfoList = mixedSampleGeneService.selectMoreMatchedSuspectList(query, pageInfo);
            //TODO 首页查询更多混合样本比中嫌疑人的比中条数
            int moreMatchedSuspectCount = mixedSampleGeneService.selectMatchedSuspectCountByQuery(query);

            resultMap.put("caseInfoList", caseInfoList);
            resultMap.put("pageInfo", pageInfo.updatePageInfo(moreMatchedSuspectCount));

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {
            logger.error("首页查询更多混合样本比中嫌疑人的比中列表失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 首页查询混合基因串并案比中嫌疑人列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/latestSerialCaseMatchedSuspectList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean latestSerialCaseMatchedSuspectList(HttpServletRequest request, HttpServletResponse response,
                                                         Integer lastestCount) throws IOException {
        try {
        		AuthUser user = RequestUtils.getAuthUser();
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            List<MixMatchedSupectVo> caseList = (List<MixMatchedSupectVo>) redisUtils.get("latestSerialCaseMatchedSuspectList_" + userId);
            if (ListUtils.isNotNullAndEmptyList(caseList)){
                return new ResultBean(ResultBean.CODE_SUCCESS, 0, caseList, ResultBean.NAME_SUCCESS);
            }else {
                //从数据库里查
                List<MixMatchedSupectVo> caseInfoList = mixedSampleGeneService.selectDifferentCaseMatchedSuspectList(lastestCount,userId);
                //将数据放入缓存，并且设置时间为一小时
                redisUtils.set("latestSerialCaseMatchedSuspectList_" + userId,caseInfoList,3600);
                //首页查询混合基因串并案比中嫌疑人总数
//            int serialCaseMixedGeneCount = mixedSampleGeneService.selectSerialCaseMixedSampleCount();
//            resultMap.put("caseInfoList", caseInfoList);
//
//            resultMap.put("serialCaseMixedGeneCount", serialCaseMixedGeneCount);
                return new ResultBean(ResultBean.CODE_SUCCESS, 0, caseInfoList, ResultBean.NAME_SUCCESS);
            }

        } catch (Exception e) {
            logger.error("首页查询混合基因串并案比中嫌疑人列表失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }
    /**
     * 首页查询混合基因串并案比中嫌疑人比中的总数
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/serialCaseMixedSampleCount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean serialCaseMixedSampleCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            //TODO 2.5查询串并案比中的混合样本总数
//            int serialCaseMixedGeneCount = mixedSampleGeneService.selectSerialCaseMixedSampleCount();
            //获取登录人信息
        		AuthUser user = RequestUtils.getAuthUser();
            //首页查询串并案比中的混合样本条数
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            MixedSampleGeneVo query = new MixedSampleGeneVo();
            query.setUserId(userId);
            int serialCaseMixedGeneCount = mixedSampleGeneService.selectDifferentSuspectCountByQuery(query);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, serialCaseMixedGeneCount, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("查询串并案比中的混合样本总数失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 首页查询更多混合基因串并案比中嫌疑人列表
     *
     * @param request
     * @param response
     * @param pageInfo
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/moreSerialCaseMixedSampleList", produces = "application/json;charset=UTF-8")
    public ResultBean moreDifferentCaseMixedSampleList(HttpServletRequest request, HttpServletResponse response,
                                                       MixedSampleGeneVo query,
                                                       PageInfo pageInfo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        try {
        		AuthUser user = RequestUtils.getAuthUser();
            //初始化查询条件
            query = InitializationData.mixedSampleGeneVoVoQuery(query);
            //设置当前单位
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            query.setUserId(userId);
            //TODO 首页查询更多混合基因串并案比中嫌疑人列表
            List<MixMatchedSupectVo> caseInfoList = mixedSampleGeneService.selectMoreSerialCaseMixedSampleList(query, pageInfo);

            //首页查询更多串并案比中的混合样本条数
            int moreMatchedSuspectCount = mixedSampleGeneService.selectDifferentSuspectCountByQuery(query);

            resultMap.put("caseInfoList", caseInfoList);
            resultMap.put("pageInfo", pageInfo.updatePageInfo(moreMatchedSuspectCount));
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("首页查询更多混合基因串并案比中嫌疑人列表失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 首页查询混合样本比中质控人员样本列表
     *
     * @param request
     * @param response
     * @param lastestCount
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/latestQualityMixedSampleList", produces = "application/json;charset=UTF-8")
    public ResultBean latestQualityMixedSampleList(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   Integer lastestCount) throws IOException {

        try {
        		AuthUser user = RequestUtils.getAuthUser();
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            List<MixMatchedSupectVo> caseIList = (List<MixMatchedSupectVo>) redisUtils.get("latestQualityMixedSampleList_" + userId);
            if (ListUtils.isNotNullAndEmptyList(caseIList)){
                return new ResultBean(ResultBean.CODE_SUCCESS, 0, caseIList, ResultBean.NAME_SUCCESS);
            }else {
                //TODO 2.7查询最新的混合样本比中质控人员样本列表
                List<MixMatchedSupectVo> caseInfoList = mixedSampleGeneService.selectLatestQualityMixedSampleList(lastestCount,userId);
                //将数据放入缓存
                redisUtils.set("latestQualityMixedSampleList_" + userId,caseInfoList,3600);
                //TODO 	查询混合样本比中质控人员的总数
//            int qualityPersonnelCount = mixedSampleGeneService.selectMixedSampleQualityPersonnelCount();
//            resultMap.put("caseInfoList", caseInfoList);

//            resultMap.put("qualityPersonnelCount", qualityPersonnelCount);

                return new ResultBean(ResultBean.CODE_SUCCESS, 0, caseInfoList, ResultBean.NAME_SUCCESS);
            }
        } catch (Exception e) {
            logger.error("首页查询最新的混合样本比中质控人员样本列表失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 查询混合样本比中质控人员的总数
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMixedSampleQualityPersonnelCount", produces = "application/json;charset=UTF-8")
    public ResultBean selectMixedSampleQualityPersonnelCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //TODO 	查询混合样本比中质控人员的总数
//            int mixedSampleQualityPersonnelCount = mixedSampleGeneService.selectMixedSampleQualityPersonnelCount();
            //获取当前登录人
        		AuthUser user = RequestUtils.getAuthUser();
            //查询更多混合样本比中质控人员样本条数
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            MixedSampleGeneVo query = new MixedSampleGeneVo();
            query.setUserId(userId);
            int mixedSampleQualityPersonnelCount = mixedSampleGeneService.selectMixedQualityPersonnelCount(query);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, mixedSampleQualityPersonnelCount, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {

            logger.error("查询混合样本比中质控人员的总数失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }
    /**
     * 首页查询更多混合样本比中质控人员样本列表
     *
     * @param request
     * @param response
     * @param pageInfo
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/moreQualityMixedSampleList", produces = "application/json;charset=UTF-8")
    public ResultBean moreQualityMixedSampleList(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 MixedSampleGeneVo query,
                                                 PageInfo pageInfo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        try {
        		AuthUser user = RequestUtils.getAuthUser();
            //初始化查询条件
            query = InitializationData.mixedSampleGeneVoVoQuery(query);
            //设置单位ID
            String userId = null;
            if (null != user.getId()){
                userId = user.getId();
            }
            query.setUserId(userId);
            //TODO 2.9 查询更多混合样本比中质控人员样本列表
            List<MixMatchedSupectVo> caseInfoList = mixedSampleGeneService.selectMoreQualityMixedSampleList(query, pageInfo);

            //查询更多混合样本比中质控人员样本条数
            int moreQualityCount = mixedSampleGeneService.selectMixedQualityPersonnelCount(query);

            resultMap.put("caseInfoList", caseInfoList);
            resultMap.put("pageInfo", pageInfo.updatePageInfo(moreQualityCount));

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("首页查询更多混合样本比中质控人员样本列表失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 查询混合样本总数
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMixedSampleGeneCount", produces = "application/json;charset=UTF-8")
    public ResultBean selectMixedSampleGeneCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //TODO 查询混合样本总数
            int mixedSampleGeneCount = mixedSampleGeneService.selectMixedSampleGeneCount();
            //TODO 查询单一样本总数
            int singleSampleGeneCount = singleSampleGeneService.selectSingleSampleGeneCount();
            //TODO 查询已拆分样本总数
            int splitedSampleGeneCount = splitedSampleGeneService.selectSplitedSampleGeneCount();
            //TODO 查询混合样本比中嫌疑人总数
            int mixedSampleMatchPersonCount = mixedSampleGeneService.selectMatchedSuspectCount();

            resultMap.put("mixedSampleGeneCount", mixedSampleGeneCount);
            resultMap.put("singleSampleGeneCount", singleSampleGeneCount);
            resultMap.put("splitedSampleGeneCount", splitedSampleGeneCount);
            resultMap.put("mixedSampleMatchPersonCount", mixedSampleMatchPersonCount);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("查询混合样本总数失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }


    /**
     * 根据案件编号调取lims数据并添加
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
    @RequestMapping(value = "/selectByCaseNo", produces = "application/json;charset=UTF-8")
    public ResultBean selectByCaseNo(HttpServletRequest request, HttpServletResponse response, String caseNo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        logger.info("调用lims案件编号--------------"+caseNo+"------开始");
        //FYB1901772-2019WZ1772   FYB1901770-2019WZ1770  CY2019WZ0723  FYB1901734-2019WZ1734
//        caseNo = "CY2019WZ0723";
        //小写转大写
        String no = caseNo.toUpperCase();
        URL realUrl = null;
        PrintWriter out = null;
        try {
            String result = "";
            //获取地址
            String url = FileUtils.getProperties("/application.yml", "caseAnalystUrl");

        /*    Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("caseNo", caseNo);*/
            realUrl = new URL(url);
            // 打开与URL之间的链接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("conn", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

            // 设置请求属性
          /*JSONObject resultJson = new JSONObject();
            Iterator it = paramMap.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                resultJson.put(key, paramMap.get(key));
            }*/
            // 发送请求参数
            out.print(no);
            // flush输出流缓冲
            out.flush();
            logger.info("调用lims-------------------开始");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            logger.info("调用lims-------------------结束返回内容"+reader);
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            //解析json

            JSONObject jsonObject = JSONObject.parseObject(result);
            String data = jsonObject.getString("data");
            if (data.equals("1")){
                logger.info("该案件不存在，获取信息失败！");
                return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_SEARCH_ISNULL_EXCEPTION, 1,"该案件不存在，获取信息失败！");
            }else {
                JSONObject str = JSON.parseObject(data);
                CaseInfo caseInfo = new CaseInfo();
                List<SampleInfo> sampleInfoList = new ArrayList<>();
                List<PersonInfo> personInfoList = new ArrayList<>();
                List<SingleSampleGene> singleSampleGenesList = new ArrayList<>();
                List<MixedSampleGene> mixedSampleGeneList = new ArrayList<>();
                //案件信息
                if (str.containsKey("caseInfo")) {

                    try{
                        Map map = (Map) JSONObject.parse(str.get("caseInfo").toString());
                        String  createDatetime = (String) map.get("createDatetime");
                        String  caseDatetime2 = (String) map.get("caseDatetime");
                        //给定模式(这里给定的模式须与给定日期字符串格式匹配)
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        Date createDatetime2 = sdf2.parse(createDatetime);


                        String caseProperty = (String) map.get("caseProperty");
                        String caseStatus = (String) map.get("caseStatus");
                        String createPerson = (String) map.get("createPerson");
                        String caseBrief = (String) map.get("caseBrief");
//                    String majorNo = (String) map.get("majorNo");
                        String caseNoes = (String) map.get("caseNo");
//                    String hasAppendFlag = (String) map.get("hasAppendFlag");
//                    String entrustStatus = (String) map.get("entrustStatus");
                        String caseType = (String) map.get("caseType");
//                    String deleteFlag = (String) map.get("deleteFlag");
//                    String majorType = (String) map.get("majorType");
                        String caseId = (String) map.get("caseId");
                        String caseName = (String) map.get("caseName");

                        if(caseDatetime2 != null){
                            Date caseDatetime = sdf2.parse(caseDatetime2);
                            caseInfo.setCaseDatetime(caseDatetime);
                        }

                        caseInfo.setCreateDatetime(createDatetime2);

                        caseInfo.setCaseProperty(caseProperty);
                        caseInfo.setCaseStatus(caseStatus);
                        caseInfo.setCreatePerson(createPerson);
                        caseInfo.setCaseBrief(caseBrief);
                        caseInfo.setCaseNo(caseNoes);
                        caseInfo.setCaseType(caseType);
                        caseInfo.setId(caseId);
                        caseInfo.setCaseId(caseId);
                        caseInfo.setCaseName(caseName);

//                    caseInfo = JSON.parseObject(str.get("caseInfo").toString(), CaseInfo.class);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                //样本信息
                if (str.containsKey("sampleInfoList")) {
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<SampleInfo> list2 = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("sampleInfoList"), SampleInfo.class);
                        if(list2.size() >0){
                            for(int j=0;j< list2.size();j++){
                                SampleInfo sampleInfoes = list2.get(j);
                                sampleInfoList.add(sampleInfoes);
                            }
                        }

//                    sampleInfoList = (List<SampleInfo>) JSON.parseArray(str.get("sampleInfoList").toString(), SampleInfo.class);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                //人员信息
                if (str.containsKey("personInfoList")) {
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<PersonInfo> personList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("personInfoList"), PersonInfo.class);
                        if(personList.size() >0){
                            for(int k=0;k<personList.size();k++){
                                PersonInfo personInfo = personList.get(k);
                                personInfoList.add(personInfo);
                            }

                        }
//                    JSONObject str2 = JSONObject.parseObject(str.toString());
//                    personInfoList = (List<PersonInfo>) JSON.parseArray(str2.get("personInfoList").toString(), PersonInfo.class);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                //单一审核通过基因型信息
                if (str.containsKey("auditedGeneList")) {
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<SingleSampleGene> singleSampleGeneList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("auditedGeneList"), SingleSampleGene.class);
                        if(ListUtils.isNotNullAndEmptyList(singleSampleGeneList)){
                            for(SingleSampleGene sampleGene : singleSampleGeneList){
                                singleSampleGenesList.add(sampleGene);
                            }
                        }
//                        singleSampleGenesList = (List<SingleSampleGene>) JSON.parseArray(str.get("auditedGeneList").toString(), SingleSampleGene.class);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                //混合审核通过基因型信息
                if (str.containsKey("mixAuditedGeneList")) {
                    try{
                        JSONObject str2 = JSONObject.parseObject(str.toString());
                        List<MixedSampleGene> mixAuditedGeneList = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("mixAuditedGeneList"), MixedSampleGene.class);
                        if(ListUtils.isNotNullAndEmptyList(mixAuditedGeneList)){
                            for(MixedSampleGene sampleGene : mixAuditedGeneList){
                                mixedSampleGeneList.add(sampleGene);
                            }

                        }
//                        mixedSampleGeneList = (List<MixedSampleGene>) JSON.parseArray(str.get("mixAuditedGeneList").toString(), MixedSampleGene.class);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                AuthUser user = RequestUtils.getAuthUser();

                //添加或修改案件信息 如果数据库存在改案件则修改 不存在添加
                CaseInfo caseInfo1 = caseInfoService.selectByCaseId(caseInfo.getCaseId());
                if (null != caseInfo1) {
                    caseInfo1.setId(caseInfo.getId());
                    caseInfo1.setCaseId(caseInfo.getCaseId());
                    caseInfo1.setCaseName(caseInfo.getCaseName());
                    caseInfo1.setCaseBrief(caseInfo.getCaseBrief());
                    caseInfo1.setCaseLocation(caseInfo.getCaseLocation());
                    caseInfo1.setCaseDatetime(caseInfo.getCaseDatetime());
                    caseInfo1.setCaseType(caseInfo.getCaseType());
                    caseInfo1.setCaseProperty(caseInfo.getCaseProperty());
                    caseInfo1.setCaseLevel(caseInfo.getCaseLevel());
                    caseInfo1.setCaseStatus(caseInfo.getCaseStatus());
                    caseInfo1.setUpdateDatetime(new Date());
                    if (null != user.getOrgId()){
                        caseInfo1.setOrgId(user.getOrgId());
                    }
                    if (null != user.getId()){
                        caseInfo1.setCreatePerson(user.getId());
                        caseInfo1.setUpdatePereson(user.getId());
                    }
                    //caseInfo1.setUpdatePereson();
                    caseInfoService.updateCaseInfo(caseInfo1);
                    resultMap.put("caseInfo", caseInfo1);
                } else {
                    caseInfo.setId(caseInfo.getCaseId());
                    caseInfo.setCreateDatetime(new Date());
                    if (null != user.getOrgId()){
                        caseInfo.setOrgId(user.getOrgId());
                    }
                    if (null != user.getId()){
                        caseInfo.setCreatePerson(user.getId());
                    }
                    //caseInfo.setCreatePerson();
                    caseInfoService.insertCaseInfo(caseInfo);
                    resultMap.put("caseInfo", caseInfo);

                /*//添加消息信息
                MobileNews mobileNews = new MobileNews();
                mobileNews.setId(UUID.randomUUID().toString());
                mobileNews.setTitle("添加案件:"+caseInfo.getCaseName());//标题
                mobileNews.setContent(caseInfo.getCaseBrief());//内容
                mobileNews.setState(0);//状态  未读
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createdate = sdf.format(date);
                mobileNews.setCreateDatetime(createdate);
                mobileNews.setType(12);
                if (null != user.getUserId()){
                    mobileNews.setUserid(user.getUserId());
                }
                mobileNews.setCaseId(caseInfo.getCaseId());//案件id
                if (null != user.getOrgId()){
                    mobileNews.setUserOrg(user.getOrgId());
                }
                mobileNews.setMessageType(3);//消息类型
                mobileNewsMapper.insert(mobileNews);*/
                }

                //添加或修改样本信息
                for (SampleInfo sampleInfo : sampleInfoList) {
                    //根据样本编号和案件id查询该样本是否存在
                    SampleInfo sampleInfo1 = sampleInfoService.selectBySampleId(sampleInfo.getSampleId());
                    if (null != sampleInfo1) {
                        sampleInfo1.setSampleName(sampleInfo.getSampleName());
                        sampleInfo1.setSampleType(sampleInfo.getSampleType());
                        sampleInfo1.setSampleFlag(sampleInfo.getSampleFlag());
                        sampleInfo1.setRemark(sampleInfo.getSampleRemark());
                        sampleInfo1.setRefPersonId(sampleInfo.getLinkId());
                        //sampleInfo1.setUpdatePereson();
                        sampleInfo1.setUpdateDatetime(new Date());
                        sampleInfoService.updateSampleInfo(sampleInfo1);
                    } else {
                        sampleInfo.setCreateDatetime(new Date());
                        //sampleInfo.setCreatePerson();
                        sampleInfo.setId(sampleInfo.getSampleId());
                        sampleInfo.setRefPersonId(sampleInfo.getLinkId());
                        sampleInfo.setRemark(sampleInfo.getSampleRemark());
                        sampleInfo.setInstoredFlag(Constants.INSTORED_FLAG_STATUS_0);
                        sampleInfoService.insertSampleInfo(sampleInfo);
                    }
                    //判断是否是人员样本还是物证检材
                    if (("1").equals(sampleInfo.getSampleFlag())) {
                        //添加或修改人员信息
                        for (PersonInfo personInfo : personInfoList) {
                            PersonInfo personInfo1 = personInfoService.selectByPersonId(personInfo.getPersonId());
                            if (null != personInfo1) {
                                personInfo1.setPersonName(personInfo.getPersonName());
                                personInfo1.setPersonType(personInfo.getPersonType());
                                personInfo1.setSex(personInfo.getPersonGender());
                                personInfo1.setIdCard(personInfo.getPersonIdCard());
                                personInfo1.setUpdateDatetime(new Date());
                                //personInfo1.setUpdatePereson();
                                personInfoService.updatePersonInfo(personInfo1);
                            } else {
                                personInfo.setId(personInfo.getPersonId());
                                personInfo.setSex(personInfo.getPersonGender());
                                personInfo.setIdCard(personInfo.getPersonIdCard());
                                personInfo.setCreateDatetime(new Date());
                                //personInfo.setCreatePerson();
                                personInfoService.insert(personInfo);
                            }
                        }
                    }
                }


                List< Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
                if (gen == 1){
                    logger.info("查询基因座信息开始----------------------");
                    ResponseData responseData = geneListServerClient.selectByLocusName();
                    String dataResultStr = JSONObject.toJSONString(responseData.getResult());
                    List<HashMap> llocus = JSON.parseArray(dataResultStr, HashMap.class);
                    for (Object object : llocus ) {
                        listLocusName.add((Map<String, String>) object);
                    }
                    logger.info("查询基因座信息结束----------------------");
                }
                //添加或修改单一审核通过基因型信息
                List<SingleSampleGeneVo> singleSampleGeneList = new ArrayList<>();
                for (SingleSampleGene singleSampleGene : singleSampleGenesList) {
                    SingleSampleGene singleSampleGene1 = singleSampleGeneService.selectBySampleId(singleSampleGene.getSampleId());
                    if (null != singleSampleGene1) {
                        String gene = null;
                        if (gen == 1){
                            logger.info("基因信息转换开始----------------------");
                            gene = geneformatUtils.geneFormatConversionNew(singleSampleGene.getGeneInfo(),listLocusName);
                            logger.info("基因信息转换结束----------------------");
                        }else {
                            gene = singleSampleGene.getGeneInfo();
                        }
                        singleSampleGene1.setGeneInfo(gene);
                        singleSampleGene1.setReagentName(singleSampleGene.getPanelId());
                        singleSampleGene1.setGenePicture(singleSampleGene.getImagePath());
                        singleSampleGene1.setUpdateDatetime(new Date());
                        //singleSampleGene1.setUpdatePereson();
                        singleSampleGene1.setBoardBarcode(singleSampleGene.getBoardNo());
                        singleSampleGeneService.updateSingleSampleGene(singleSampleGene1);
                        //查询单一样本信息
                        List<SingleSampleGeneVo> singleSampleGeneVoList = singleSampleGeneService.selectSingleSampleGeneVoList(singleSampleGene1.getId());
                        singleSampleGeneList.add(singleSampleGeneVoList.get(0));
                    } else {
                        singleSampleGene.setId(UUID.randomUUID().toString());
                        singleSampleGene.setCreateDatetime(new Date());
                        //singleSampleGene1.setCreatePerson();
                        singleSampleGene.setReagentName(singleSampleGene.getPanelId());
                        singleSampleGene.setGenePicture(singleSampleGene.getImagePath());
                        singleSampleGene.setBoardBarcode(singleSampleGene.getBoardNo());
                        String gene = null;
                        if (gen == 1){
                            logger.info("基因信息转换开始----------------------");
                            gene = geneformatUtils.geneFormatConversionNew(singleSampleGene.getGeneInfo(),listLocusName);
                            logger.info("基因信息转换结束----------------------");
                        }else {
                            gene = singleSampleGene.getGeneInfo();
                        }
                        singleSampleGene.setGeneInfo(gene);
                        singleSampleGeneService.insert(singleSampleGene);
                        //查询单一样本信息
                        List<SingleSampleGeneVo> singleSampleGeneVoList = singleSampleGeneService.selectSingleSampleGeneVoList(singleSampleGene.getId());
                        singleSampleGeneList.add(singleSampleGeneVoList.get(0));
                    }
                }
                resultMap.put("singleSampleGenesList", singleSampleGeneList);
                //添加或修改混合审核通过基因型信息
                List<MixedSampleGeneVo> mixedSampleGeneList1 = new ArrayList<>();
                for (MixedSampleGene mixedSampleGene : mixedSampleGeneList) {
                    MixedSampleGene mixedSampleGene1 = mixedSampleGeneService.selectBySampleId(mixedSampleGene.getSampleId());
                    if (null != mixedSampleGene1) {
                        mixedSampleGene1.setReagentName(mixedSampleGene.getPanelId());
                        String gene = null;
                        if (gen == 1){
                            logger.info("基因信息转换开始----------------------");
                            gene = geneformatUtils.geneFormatConversionNew(mixedSampleGene.getGeneInfo(),listLocusName);
                            logger.info("基因信息转换结束----------------------");
                        }else {
                            gene = mixedSampleGene.getGeneInfo();
                        }
                        mixedSampleGene1.setGeneInfo(gene);
//                        mixedSampleGene1.setGeneInfo(mixedSampleGene.getGeneInfo());
                        mixedSampleGene1.setGenePicture(mixedSampleGene.getImagePath());
                        mixedSampleGene1.setUpdateDatetime(new Date());
                        //mixedSampleGene1.setUpdatePereson();
                        mixedSampleGene1.setBoardBarcode(mixedSampleGene.getBoardNo());
                        mixedSampleGeneService.updateMixedSampleGene(mixedSampleGene1);
                        //查询混合单一样本信息
                        List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneService.selectMixedSampleGeneVoList(mixedSampleGene1.getId());
                        mixedSampleGeneList1.add(mixedSampleGeneVoList.get(0));
                    } else {
                        mixedSampleGene.setId(UUID.randomUUID().toString());
                        mixedSampleGene.setCreateDatetime(new Date());
                        //mixedSampleGene.setCreatePerson();
                        mixedSampleGene.setReagentName(mixedSampleGene.getPanelId());
                        mixedSampleGene.setGenePicture(mixedSampleGene.getImagePath());
                        mixedSampleGene.setBoardBarcode(mixedSampleGene.getBoardNo());
                        mixedSampleGene.setUpdateDatetime(new Date());
                        String gene = null;
                        if (gen == 1){
                            logger.info("基因信息转换开始----------------------");
                            gene = geneformatUtils.geneFormatConversionNew(mixedSampleGene.getGeneInfo(),listLocusName);
                            logger.info("基因信息转换结束----------------------");
                        }else {
                            gene = mixedSampleGene.getGeneInfo();
                        }
                        mixedSampleGene.setGeneInfo(gene);
//                        mixedSampleGene1.setGeneInfo(mixedSampleGene.getGeneInfo());
                        mixedSampleGeneService.insert(mixedSampleGene);
                        //查询混合单一样本信息
                        List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneService.selectMixedSampleGeneVoList(mixedSampleGene.getId());
                        mixedSampleGeneList1.add(mixedSampleGeneVoList.get(0));
                    }
                }
                resultMap.put("mixedSampleGeneList", mixedSampleGeneList1);

                logger.info("根据案件编号调取lims:" + JSON.toJSON(new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS)));
            }
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {
            logger.error("根据案件编号调取lims数据并添加失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 首页查看比中详情信息
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMixedSampleSuspectsDetails", produces = "application/json;charset=UTF-8")
    public ResultBean selectMixedSampleSuspectsDetails(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestBody GeneInfoBean bean) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> resultDetails = new HashMap<>();
        int mixSameCount = 0;
        try {
            //判断混合基因是否包含此基因
            if (mixSameCount == 0) {
                mixSameCount = minSameMixCount;
            }
            result = mixedSingleSampleGene(bean.getMixSampleGeneInfo(), bean.getSampleGeneInfo(), mixSameCount);

            resultDetails.put("viewRatioGeneDetails", result);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultDetails, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("首页查看比中详情信息" + e.getMessage());

            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 查看单一样本基因分型及图谱  and 查看混合样本基因分型及图谱
     *
     * @param request
     * @param response
     * @param geneID   //基因id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectSingleMixedSampleGeneDetails", produces = "application/json;charset=UTF-8")
    public ResultBean selectSingleSampleGeneDetails(HttpServletRequest request, HttpServletResponse response, String geneID) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> resultDetails = new HashMap<>();

        try {
            //TODO 2.13 查看单一样本基因分型及图谱
            List<SingleSampleGene> singleSampleGeneList = singleSampleGeneService.selectSingleSampleGeneDetails(geneID);

            //TODO 2.14 查看混合样本基因分型及图谱
            List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleSuspectsDetails(geneID);

            //单一样本基因分型信息
            if (ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {

                SingleSampleGene singleSampleGene = singleSampleGeneList.get(0);
                Map<String, List<String>> singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(singleSampleGene.getGeneInfo());

                //判空
                if (singleSampleGeneInfo != null) {

                    result = geneSameCompareUtil.analysisGene(singleSampleGeneInfo);
                }
                //单一图谱
                if (StringUtils.isNotBlank(singleSampleGene.getGenePicture())) {
                    result.put("geneImagePath", singleSampleGene.getGenePicture());
                } else {
                    result.put("geneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

                }
            }
            //混合基因分型信息
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList)) {

                MixedSampleGene mixedSampleGene = mixedSampleGeneList.get(0);

                Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene.getGeneInfo());
                //判空
                if (mixedSampleGeneInfo != null) {
                    //解析基因型信息
                    result = geneSameCompareUtil.analysisGene(mixedSampleGeneInfo);
                }
                //混合图谱
                if (StringUtils.isNotBlank(mixedSampleGene.getGenePicture())) {

                    result.put("geneImagePath", mixedSampleGene.getGenePicture());
                } else {
                    result.put("geneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

                }
            }

            resultDetails.put("viewRatioGeneDetails", result);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultDetails, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("查看基因分型及图谱" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }


    /**
     * 混合样本 比中 单一样本的详情
     *
     * @param mixedSampleGeneList
     * @param singleSampleGeneList
     * @param mixSameCount
     * @return
     */
    private Map<String, Object> mixedSingleSampleGene(List<MixedSampleGene> mixedSampleGeneList,
                                                      List<SingleSampleGene> singleSampleGeneList, int mixSameCount) {
        Map<String, Object> result = new HashMap<>();

        //混合基因
        MixedSampleGene mixedSampleGene = mixedSampleGeneList.get(0);
        //比对基因
        SingleSampleGene singleSampleGene = singleSampleGeneList.get(0);
        //判空
        if (StringUtils.isNotBlank(mixedSampleGene.getGeneInfo()) && StringUtils.isNotBlank(singleSampleGene.getGeneInfo())) {
            //混合基因
            Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene.getGeneInfo());

            //单一基因
            Map<String, List<String>> singleSampleInfo = GeneformatUtils.mixedSampleGeneformat(singleSampleGene.getGeneInfo());

            if (mixSameCount == 0) {
                mixSameCount = minSameMixCount;
            }
            //进入比对
            if (mixedSampleGeneInfo != null && singleSampleInfo != null) {
                result = geneMixCompareUtil.compareResult(mixedSampleGeneInfo, singleSampleInfo, mixSameCount);
            }
        }

        //混合图谱
        if (StringUtils.isNotBlank(mixedSampleGene.getGenePicture())) {
            result.put("splitdSampleGeneImagePath", mixedSampleGene.getGenePicture());
        } else {
            result.put("splitdSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

        }
        //比对图谱
        if (StringUtils.isNotBlank(singleSampleGene.getGenePicture())) {
            result.put("ratioSampleGeneImagePath", singleSampleGene.getGenePicture());
        } else {
            result.put("ratioSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

        }
        return result;
    }

    /**
     * 混合样本 比中 单一样本的详情
     *
     * @param mixSameCount
     * @return
     */
    private Map<String, Object> mixedSingleSampleGene(String mixedSampleGene, String singleSampleGene, int mixSameCount) {
        Map<String, Object> result = new HashMap<>();
        //判空
        if (StringUtils.isNotBlank(mixedSampleGene) && StringUtils.isNotBlank(singleSampleGene)) {
            //混合基因
            Map<String, List<String>> mixedSampleGeneInfoa =GeneSameCompareUtil.NewmarkerNameGene(mixedSampleGene);
            Map<String, List<String>> geneInfoString = GeneSameCompareUtil.NewgetGeneInfoString(singleSampleGene);
            //Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene);
            //Map<String, List<String>> singleSampleInfo = GeneformatUtils.mixedSampleGeneformat(singleSampleGene);

            if (mixSameCount == 0) {
                mixSameCount = minSameMixCount;
            }
            //进入比对
            if (mixedSampleGeneInfoa != null && geneInfoString != null) {
                result = geneMixCompareUtil.compareResult(mixedSampleGeneInfoa, geneInfoString, mixSameCount);
            }
        }

        //混合图谱
        if (StringUtils.isNotBlank(/*mixedSampleGene.getGenePicture()*/"")) {
            result.put("splitdSampleGeneImagePath", /*mixedSampleGene.getGenePicture()*/"");
        } else {
            result.put("splitdSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

        }
        //比对图谱
        if (StringUtils.isNotBlank(/*singleSampleGene.getGenePicture()*/"")) {
            result.put("ratioSampleGeneImagePath", /*singleSampleGene.getGenePicture()*/"");
        } else {
            result.put("ratioSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

        }
        return result;
    }


    /**
     * 自动分析混合样本基因分型和单一样本分型进行比对
     */
    @RequestMapping(value = "/autoanalysisMixedAndSingleCompare", produces = "application/json;charset=UTF-8")
    public ResponseData autoanalysisMixedAndSingleCompare(@RequestBody  MainpageBean MainpageBean) throws IOException {
            int mixSameCount = 13;
            //根据案件id查询案件信息
            CaseInfo caseInfo = MainpageBean.getCaseInfo();
            //查询混合样本
            List<MixedSampleGeneVo> mixedSampleGeneList = MainpageBean.getMixedSampleGeneList();
            //查询单一样本
            List<SingleSampleGeneVo> singleSampleGeneList = MainpageBean.getSingleSampleGenesList();
            //自动分析混合样本基因分型和单一样本分型进行比对
            Map<String, Object> returnMap = new HashMap<>();
            //判断混合是否为空
            if (null != mixedSampleGeneList && mixedSampleGeneList.size() > 0) {
                //returnMap = matchResultMixSingleService.autoanalysisMixedAndSingleCompare(mixedSampleGeneList, singleSampleGeneList, mixSameCount,caseInfo);
                List<AutoanalysisMix> list = matchResultMixSingleService.autoanalysisMixedAndSingle(mixedSampleGeneList, singleSampleGeneList, mixSameCount,caseInfo);
                returnMap.put("autoanalysis", list);
                returnMap.put("status", Constants.AUTOMATIC_STATUS_0);
            } else {
                returnMap.put("status", Constants.AUTOMATIC_STATUS_1);
            }
        return new ResponseData(returnMap);
    }

    /**
     * 根据混合基因id查询混合单一比中详情表
     *
     * @param request
     * @param response
     * @param sampleId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMatchResultMixSingleByMixedSampleGeneId", produces = "application/json;charset=UTF-8")
    public ResultBean selectMatchResultMixSingleByMixedSampleGeneId(HttpServletRequest request, HttpServletResponse response, String sampleId) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        if (sampleId != null ){
            try {
                //根据混合样本id查询混合样本信息
                List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleGeneBySampleId(sampleId);

                //根据样本id查询所对应的案件id
//                SampleInfo sampleInfos = sampleInfoService.selectBySampleId(sampleId);
//                List<MatchResultMixSingle> matchResultMixSingleList2 = matchResultMixSingleService.selectMatchResultMixSingleByMixedSampleGeneId(mixedSampleGeneList.get(0).getId());
//                List<MatchResultMixSingle> mixSingleList = new ArrayList<>();
                //删除非本案件的单一样本
                /*for(int i = matchResultMixSingleList2.size()-1;i>=0;i--){
                    List<SampleInfoVo> sampleInfoList2 = sampleInfoService.selectSampleInfoListBySingleGeneId(matchResultMixSingleList2.get(i).getSinglegeneId());
                    if(sampleInfoList2.get(0).getEntity().getCaseId().equals(sampleInfos.getCaseId())){
                        matchResultMixSingleList2.remove(i);
                    }
                }*/
//                List<MatchResultMixSingle> mixSingles = new ArrayList<>();
//                if (ListUtils.isNotNullAndEmptyList(matchResultMixSingleList2)){
//                    for(MatchResultMixSingle matchResultMixSinglew:matchResultMixSingleList2){
//                        List<SampleInfoVo> sampleInfoList2 = sampleInfoService.selectSampleInfoListBySingleGeneId(matchResultMixSinglew.getSinglegeneId());
//                        if(sampleInfoList2.get(0).getEntity().getCaseId().equals(sampleInfos.getCaseId())){
//                            mixSingleList.add(matchResultMixSinglew);
//                        }
//                    }
//                    if (ListUtils.isNotNullAndEmptyList(mixSingleList)){
//                        int x = 1;//记录分组
//                        //对同型比中情况进行分组
//                        Map<String, String> groupMap = new HashMap<String, String>();
//                        Map<String, List<MatchResultMixSingle>> groupMapList = new HashMap<String, List<MatchResultMixSingle>>();
//                        int len = mixSingleList.size();
//                        String groupName = null;
//                        List<MatchResultMixSingle> matchedSampleList = null;
//                        for(int i = 0; i < len; i++){
//                            MatchResultMixSingle si = mixSingleList.get(i);
//                            if(groupMap.containsKey(si.getSampleNo())){
////                        groupName = groupMap.get(si.getSampleNo());
//                                continue;
//                            }else{
//                                groupName = "Group-" + x;
//                                groupMap.put(si.getSampleNo(), groupName);
//                            }
//                            matchedSampleList = groupMapList.get(groupName);
//                            if(matchedSampleList == null || matchedSampleList.size() == 0){
//                                matchedSampleList = new ArrayList<>();
//                            }
//                            for(int j = i+1; j < len; j++){
//                                MatchResultMixSingle sj = mixSingleList.get(j);
//                                //获取基因型
//                                SingleSampleGene sampleGene1 = singleSampleGeneService.selectById(si.getSinglegeneId());
//                                SingleSampleGene sampleGene2 = singleSampleGeneService.selectById(sj.getSinglegeneId());
//                                String geneInfo1 = sampleGene1.getGeneInfo();
//                                String geneInfo2 = sampleGene2.getGeneInfo();
//                                boolean bool = geneCompareUtil.repeatingUtils(geneInfo1, geneInfo2);
//                                if(bool){//si与sj同型比中
//                                    groupName = "Group-" + x;
//                                    groupMap.put(sj.getSampleNo(), groupName);
//                                    matchedSampleList.add(sj);
//                                }
//                            }
//                            matchedSampleList.add(0, si);
//                            groupMapList.put(groupName, matchedSampleList);
//                            x++;
//                        }
//                        //遍历map
//                        for (String key : groupMapList.keySet()){
//                            List<MatchResultMixSingle> singleList = groupMapList.get(key);
//                            for (MatchResultMixSingle single : singleList){
//                                single.setGroupName(key);
//                                mixSingles.add(single);
//                            }
//                        }
//                        //位置排序
//                        Collections.sort(mixSingles);
//                        for(MatchResultMixSingle matchResultMixSinglew:mixSingles){
//                            SingleSampleGene singleSampleGene = singleSampleGeneService.selectById(matchResultMixSinglew.getSinglegeneId());
//                            SampleInfo sampleInfo = sampleInfoService.selectBySampleId(singleSampleGene.getSampleId());
//                            matchResultMixSinglew.setSampleNo(sampleInfo.getSampleNo());
//                            matchResultMixSinglew.setSampleName(sampleInfo.getSampleName());
//                            //比中类型
//                            if (StringUtils.isNotBlank(sampleInfo.getSampleFlag())){
//                                if (sampleInfo.getSampleFlag().equals("0")){
//                                    matchResultMixSinglew.setSampleFlag("0");//物证
//                                }else {
//                                    matchResultMixSinglew.setSampleFlag("1");//人员
//                                }
//                            }
//                            //查询检材类型名称
//                            List<DictItem> dictItemList = dictItemService.selectListByDictTypeCode(Constants.SAMPLE_TYPE);
//                            for(DictItem dictItem:dictItemList){
//                                if((dictItem.getDictCode()).equals(sampleInfo.getSampleType())){
//                                    matchResultMixSinglew.setSampleType(dictItem.getDictName());
//                                }
//                            }
//                            //基因数量
//                            int ratio = matchResultMixSinglew.getRatio();
//                            int splitDigit = matchResultMixSinglew.getSplitDigit();
//                            int sumCount = ratio + splitDigit;
//                            //基因数量
//                            matchResultMixSinglew.setSumCount(sumCount);
//                        }
//                    }

//                }

                //根据混合基因id查询混合基因信息
                MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(mixedSampleGeneList.get(0).getId());
                //获取基因信息
                Map<String, Object> resultGeneInfo = new HashMap<>();

                //基因格式转换
                Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene.getGeneInfo());
                //判空
                if (mixedSampleGeneInfo != null) {
                    //解析基因型信息
                    resultGeneInfo = geneSameCompareUtil.analysisGene1(mixedSampleGeneInfo);
                }
                mixedSampleGene.setResultGeneInfo(resultGeneInfo);

//                resultMap.put("matchResultMixSingleList", mixSingles);
//                resultMap.put("singleSampleCount", mixSingles.size());//混合单一比中总数
                resultMap.put("mixedSampleGene", mixedSampleGene);

                return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
            } catch (Exception e) {

                logger.error("根据混合基因id查询混合单一比中详情表" + e.getMessage());
                return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                        ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
            }
        }else {
            logger.error("传入参数错误！");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入的参数有误！");
        }
    }

    /**
     * 2.15 混合样本数据库管理
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMixedDateBaseGeneList", produces = "application/json;charset=UTF-8")
    public ResultBean selectMixedDateBaseGeneList(HttpServletRequest request, HttpServletResponse response,
                                                  CaseMixedSampleGeneVo query, PageInfo pageInfo) throws IOException {
        Map<String, Object> result = new HashMap<>();
        try {
            //TODO 2.15 混合样本数据库管理
            //初始化查询条件
            query = InitializationData.caseMixedSampleGeneVoQuery(query);
            //查询混合样本数据库管理
            List<CaseMixedSampleGeneVo> caseMixedSampleGeneVoList = mixedSampleGeneService.selectMixedDateBaseGeneList(query, pageInfo);
            //显示条数
            int totalCnt = mixedSampleGeneService.selectMixedDateBaseGeneCount(query);

            result.put("pageInfo", pageInfo.updatePageInfo(totalCnt));
            result.put("caseMixedSampleGeneVoList", caseMixedSampleGeneVoList);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, result, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("2.15混合样本数据库管理 " + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 混合样本数据库管理：根据基因id删除案件混合样本
     *
     * @param request
     * @param response
     * @param caseID
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/deleteMixedDateBaseGene", produces = "application/json;charset=UTF-8")
    public ResultBean deleteMixedDateBaseGene(HttpServletRequest request, HttpServletResponse response,
                                              String caseID) throws IOException {
        int deleteMixedDatestatus = 0;
        try {
            deleteMixedDatestatus = mixedSampleGeneService.deleteMixedDateBaseGene(caseID);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, deleteMixedDatestatus, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("混合样本数据库管理：根据基因id删除案件混合样本" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 混合样本数据库管理：批量删除
     *
     * @param request
     * @param response
     * @param geneIds
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/batchDeleteMixedDateGene", produces = "application/json;charset=UTF-8")
    public ResultBean batchDeleteMixedDateGene(HttpServletRequest request, HttpServletResponse response,
                                               String geneIds) throws IOException {
        int batchDeleteStatus = 0;
        try {
            //基因id
            batchDeleteStatus = mixedSampleGeneService.batchDeleteMixedDateGene(geneIds);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, batchDeleteStatus, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("批量删除混合样本" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }


    /**
     * 自动拆分
     *
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/automaticSplit", produces = "application/json;charset=UTF-8")
    public ResponseData automaticSplit(@RequestBody GeneInfoBean geneInfoBean) throws IOException {
            //根据混合基因id查询混合基因信息
            String mixedSampleGene = geneInfoBean.getMixSampleGeneInfo();
            //根据单一基因id查询单一基因信息
             String singleSampleGene = geneInfoBean.getSampleGeneInfo();
            //基因判空
            Map<String, List<String>> srcResult = null;
            Map<String, List<String>> tarResult = null;
            if (StringUtils.isNotBlank(mixedSampleGene) && StringUtils.isNotBlank(singleSampleGene)) {
                try {
                    srcResult=  GeneformatUtils.mixedSampleGeneformat(mixedSampleGene);
                    //srcResult = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene.getGeneInfo());

                } catch (Exception ex) {
                    logger.error("解析比对基因分型信息错误！", ex);
                    return null;
                }
                try {
                    tarResult=  GeneformatUtils.mixedSampleGeneformat(singleSampleGene);
                   // tarResult = GeneformatUtils.mixedSampleGeneformat(singleSampleGene.getGeneInfo());
                } catch (Exception ex) {
                    logger.error("解析比对基因分型信息错误！", ex);
                    return null;
                }
            }
            if (srcResult == null || tarResult == null) {
                return null;
            }
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //进行循环比对
            List<Map<String, Object>> list = new ArrayList<>();
            for (Map.Entry<String, List<String>> srcEntry : srcResult.entrySet()) {
                Map<String, Object> resMap = new LinkedHashMap<>();
                String markerName = srcEntry.getKey();
                List<String> srcAlleleList = srcEntry.getValue();
                List<String> tarAlleleList = tarResult.get(markerName);
                List<Map<String, Object>> alleleList = new ArrayList<>();
                if (ListUtils.isNotNullAndEmptyList(srcAlleleList)){
                    if (ListUtils.isNotNullAndEmptyList(tarAlleleList)){
                        for (String src : srcAlleleList){
                            Map<String, Object> alleleMap1 = new LinkedHashMap<>();
                            if (tarAlleleList.contains(src)){
                                alleleMap1.put("name", src);
                                alleleMap1.put("identification", Constants.IDENTIFICATION_0);
                                alleleList.add(alleleMap1);
                            }else {
                                alleleMap1.put("name", src);
                                alleleMap1.put("identification", Constants.IDENTIFICATION_1);
                                alleleList.add(alleleMap1);
                            }
                            if (alleleList.size()>=3){
                                for (int i=0; i< alleleList.size();i++){
                                    int sum = 0;
                                    for (int s=0; s< alleleList.size();s++){
                                        if (alleleList.get(i).get("name").equals(alleleList.get(s).get("name"))){
                                            if (sum == 0){
                                                //第一次有相同
                                                ++sum;
                                            }else {
                                                //已经有过相同
                                                //修改当前map1的当前map吧数值制成1
                                                if (alleleList.get(i).get("identification").equals(alleleList.get(s).get("identification"))){
                                                    alleleList.get(s).put("identification", Constants.IDENTIFICATION_1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //拼接单一基因位点
                        String tarAllele1 = "";
                        for (String tarAllele : tarAlleleList) {
                            tarAllele1 += tarAllele + ",";
                        }
                        String tarAllele2 = tarAllele1.substring(0, tarAllele1.length() - 1);
                        @SuppressWarnings("unused")
						String[] tarstrings = tarAllele2.split(",");
                    }else{
                        for (String srcAlleles : srcAlleleList) {
                            Map<String, Object> alleleMap2 = new LinkedHashMap<>();
                            alleleMap2.put("name", srcAlleles);
                            alleleMap2.put("identification", Constants.IDENTIFICATION_1);
                            alleleList.add(alleleMap2);
                        }
                    }
                    resMap.put("markerName", markerName);
                    resMap.put("allele", alleleList);
                    list.add(resMap);
                }
            }
            resultMap.put("list", list);
            return new ResponseData(list);
    }

    /**
     * 入库拆分单一样本信息
     *
     * @param request
     * @param response
     * @param mixedSampleGeneId
     * @param geneInfo
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
    @RequestMapping(value = "/splitSampleGeneWarehousing", produces = "application/json;charset=UTF-8")
    public ResultBean splitSampleGeneWarehousing(HttpServletRequest request, HttpServletResponse response,
                                                 String mixedSampleGeneId, String geneInfo, String mixSameCount) throws IOException {
//        geneInfo="[{\"markerName\":\"D7S820\",\"allele\":[{\"name\":\"18\"},{\"name\":\"16\"},{\"name\":\"17\"}]},{\"markerName\":\"vWA\",\"allele\":[{\"name\":\"14\"},{\"name\":\"14\"},{\"name\":\"16\"}]},{\"markerName\":\"FGA\",\"allele\":[{\"name\":\"25\"},{\"name\":\"20\"},{\"name\":\"21\"}]},{\"markerName\":\"TPOX\",\"allele\":[{\"name\":\"11\"},{\"name\":\"10\"},{\"name\":\"14\"}]},{\"markerName\":\"D19S433\",\"allele\":[{\"name\":\"13\"},{\"name\":\"10\"},{\"name\":\"12\"}]},{\"markerName\":\"D5S818\",\"allele\":[{\"name\":\"11\"},{\"name\":\"10\"},{\"name\":\"12\"}]},{\"markerName\":\"Am\",\"allele\":[{\"name\":\"X\"},{\"name\":\"Y\"}]},{\"markerName\":\"D2S1338\",\"allele\":[{\"name\":\"21\"},{\"name\":\"23\"},{\"name\":\"25\"}]},{\"markerName\":\"D21S11\",\"allele\":[{\"name\":\"29\"},{\"name\":\"31\"},{\"name\":\"32\"}]},{\"markerName\":\"TH01\",\"allele\":[{\"name\":\"9\"},{\"name\":\"9\"},{\"name\":\"10\"}]},{\"markerName\":\"D18S51\",\"allele\":[{\"name\":\"15\"},{\"name\":\"20\"},{\"name\":\"21\"}]},{\"markerName\":\"D16S539\",\"allele\":[{\"name\":\"10\"},{\"name\":\"11\"},{\"name\":\"12\"}]},{\"markerName\":\"D13S317\",\"allele\":[{\"name\":\"8\"},{\"name\":\"12\"},{\"name\":\"16\"}]},{\"markerName\":\"CSF1PO\",\"allele\":[{\"name\":\"12\"},{\"name\":\"13\"},{\"name\":\"15\"}]},{\"markerName\":\"D8S1179\",\"allele\":[{\"name\":\"10\"},{\"name\":\"12\"},{\"name\":\"11\"}]},{\"markerName\":\"D3S1358\",\"allele\":[{\"name\":\"16\"},{\"name\":\"17\"},{\"name\":\"18\"}]}]";
//        geneInfo="[{\"markerName\":\"D7S820\",\"allele\":[{\"name\":\"18\"}]},{\"markerName\":\"vWA\",\"allele\":[{\"name\":\"14\"}]},{\"markerName\":\"FGA\",\"allele\":[{\"name\":\"25\"},{\"name\":\"20\"},{\"name\":\"21\"}]}]";
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            Map<String, Object> geneMap1 = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                //用来存放位点
                List<String> wdlist = new ArrayList<String>();
                String markerName = jsonArray.get(i).get("markerName").toString();
                List<Map<String, String>> list = (List) jsonArray.get(i).get("allele");
                for (int j = 0; j < list.size(); j++) {
                    String allele = list.get(j).get("name");
                    wdlist.add(allele);
                }
                //对list进行排序
                Collections.sort(wdlist);
                geneMap1.put(markerName, wdlist);
            }

            //数据库查询出来的基因信息
            List<SplitedSampleGene> geneDBList = splitedSampleGeneService.selectSplitedSampleGeneByMixedSampleGeneId(mixedSampleGeneId);
            //将拆分基因型插入到比对队列表中
            //TODO 根据混合基因id查询样本信息
            List<SampleInfoVo> sampleInfoList = sampleInfoService.selectSampleInfoList(mixedSampleGeneId);
            SampleInfoVo sampleInfoVo = null;
            if (ListUtils.isNotNullAndEmptyList(sampleInfoList)){
                sampleInfoVo = sampleInfoList.get(0);
            }

            //用来统计循环对比的次数
            int count = countUtils(geneDBList, geneMap1,mixSameCount,sampleInfoVo);

            //去判断是否添加入库还是返回重复
            result = resultCountUtils(count, geneDBList, geneMap1, mixedSampleGeneId,Integer.parseInt(mixSameCount),sampleInfoVo);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, result, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {
            logger.error("入库拆分单一样本信息" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 不考虑字符串顺序，判断字符串是否相同  公共方法
     *
     * @param o
     * @param o1
     * @return
     */
    private static boolean match(String o, String o1) {
        boolean flag = false;
        String[] split = o.split(",");
        String[] split1 = o1.split(",");
        Arrays.sort(split);
        Arrays.sort(split1);
        if (split.length == split1.length) {
            if (Arrays.equals(split, split1)) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * strMix拆分
     *
     * @param request
     * @param response
     * @param sampleId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/strMixSplit", produces = "application/json;charset=UTF-8")
    public ResultBean strMixSplit(HttpServletRequest request, HttpServletResponse response, String sampleId) throws IOException {
        try {
            //根据混合样本id查询混合样本表
            SampleInfo sampleInfo = sampleInfoService.selectBySampleId(sampleId);

            System.out.print(JSON.toJSON(new ResultBean(ResultBean.CODE_SUCCESS, 0, sampleInfo, ResultBean.NAME_SUCCESS)));

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, sampleInfo, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {
            logger.error("strMix拆分" + e);
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 混合分型入比对队列表
     *
     * @param request
     * @param response
     * @param mixedSampleGeneId
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/mixedSampleGeneCompareQueue", produces = "application/json;charset=UTF-8")
    public ResultBean mixedSampleGeneCompareQueue(HttpServletRequest request,
                                                  HttpServletResponse response, String mixedSampleGeneId) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        //修改是否入库状态
        SampleInfo sampleInfo = null;
        CompareQueue compareQueue = null;
        try {
            //获取用户信息
        		AuthUser user = RequestUtils.getAuthUser();
            //根据混合id查询样本信息
            List<SampleInfoVo> sampleInfoVoList = sampleInfoService.selectSampleInfoList(mixedSampleGeneId);
            if (null != sampleInfoVoList && sampleInfoVoList.size() > 0) {
                if (sampleInfoVoList.get(0).getEntity().getInstoredFlag().equals("1")){
                    resultMap.put("code", 1);
                }else {
                    //将混合样本id查询对比队列表是否已经存在
                    compareQueue = new CompareQueue();
//                    compareQueue.setSampleId(sampleInfoVoList.get(0).getEntity().getId());
                    compareQueue.setQueueType("1");
                    List<CompareQueue> compareQueueList = compareQueueService.selectBySampleId(compareQueue);
                    if (null == compareQueueList || compareQueueList.size() == 0) {
                        //将混合样本id插入比对队列
                        compareQueue = new CompareQueue();
//                        compareQueue.setSampleId(sampleInfoVoList.get(0).getEntity().getId());
                        compareQueue.setQueueType(Constants.QUEUE_TYPE_01);
                        compareQueue.setCreatePerson(user.getId());
                        compareQueueService.insertCompareQueue(compareQueue);
                    }else {
                        resultMap.put("code", 1);
                        return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, "比对队列已存在!");
                    }
                    //修改入库状态
                    sampleInfo = new SampleInfo();
                    sampleInfo.setId(sampleInfoVoList.get(0).getEntity().getId());
                    sampleInfo.setInstoredFlag(Constants.INSTORED_FLAG_STATUS_1);
                    sampleInfoService.updateSampleInfo(sampleInfo);
                    resultMap.put("code", 0);
                }
            } else {
                return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "混合样本不存在!");
            }

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("混合分型入比对队列表" + e);
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }


    /**
     * 快速比对 本案比对
     *
     * @param request
     * @param response
     * @param geneInfo   //混合基因信息
     * @param targetType //比对类型
     * @param condition  //比对条件
     * @param caseId     //案件id
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/theCaseFastComparison", produces = "application/json;charset=UTF-8")
    public ResultBean theCaseFastComparison(HttpServletRequest request, HttpServletResponse response, String geneInfo,
                                            String targetType, String condition,
                                            String caseId, PageInfo pageInfo) throws IOException {

//        geneInfo="[{\"geneName\":\"TPOX\",\"allele\":\"8,8\"},{\"geneName\":\"D2S1338\",\"allele\":\"19,22\"},{\"geneName\":\"D13S317\",\"allele\":\"12,12\"},{\"geneName\":\"D3S1358\",\"allele\":\"15,15\"}]";
//        targetType ="[\"01\",\"3\"]";
//        condition="[\"3\"]";
//        caseId="98f49848-2492-4f0f-8481-35d7c33b4166";
        Map<String, Object> resultMap = new HashMap<>();
        List<SingleSampleGeneVo> singleSampleGeneList = null;
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        List<SingleSampleGeneVo> singleSampleGeneLists = new ArrayList<>();
        try {

            //判断比对类型
            String[] strings = stringsUtils(targetType);
            //快速比对 容差
            int conditionId = conditionIdUtils(condition);

            if (StringUtils.isNotBlank(geneInfo)) {
                if (strings != null) {
                    //判断物证、人员、质控
                    for (String sampleflag : strings) {
                        look:
                        //0 .物证
                        if (sampleflag.equals("0")) {
                            int num = 0;
                            boolean flag = true;
                            while (flag) {
                                //查询物证信息list
                                singleSampleGeneList = singleSampleGeneService.selectSampleTypeCaseIdById(sampleflag, caseId, num, 100);
                                num++;
                                if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
                                    flag = false;
                                    break look;//直接跳出while语句
                                }
                                //判断strMix解析报告比对
                                if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                                    singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
                                }
                                singleSampleGeneLists.addAll(singleSampleGeneVoList);
                            }
                        } else if (sampleflag.equals("3")) {
                            int num = 0;
                            boolean flag = true;
                            while (flag) {
                                //查询质控人员
                                singleSampleGeneList = qualtyPersonnelService.selecAllSampleQualityList(num, 100);
                                num++;
                                if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
                                    flag = false;
                                    break look;//直接跳出while语句
                                }
                                //判断strMix解析报告比对
                                if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                                    singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
                                }
                                singleSampleGeneLists.addAll(singleSampleGeneVoList);
                            }
                        } else {
                            int num = 0;
                            boolean flag = true;
                            while (flag) {
                                //1:人员查询物证信息list
                                singleSampleGeneList = singleSampleGeneService.selectPersonTypeCaseIdById(sampleflag, caseId, num, 100);
                                num++;
                                if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
                                    flag = false;
                                    break look;//直接跳出while语句
                                }
                                //判断strMix解析报告比对
                                if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                                    singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
                                }
                                singleSampleGeneLists.addAll(singleSampleGeneVoList);
                            }
                        }
                    }
                }

            }else {
                return new ResultBean(ResultBean.CODE_ERROR,
                        ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                        ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
            }
            //进行分页
            resultMap = ResultPageListUtils.resultPageList(pageInfo, singleSampleGeneLists);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("快速比对提交本案比对失败" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }

    }

    /**
     * 快速比对 全库比对
     *
     * @param request
     * @param response
     * @param geneInfo   //混合基因信息
     * @param targetType //比对类型
     * @param condition  //比对条件
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/allFastComparison", produces = "application/json;charset=UTF-8")
    public ResultBean allFastComparison(HttpServletRequest request, HttpServletResponse response, String geneInfo,
                                        String targetType, String condition, PageInfo pageInfo) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        List<SingleSampleGeneVo> singleSampleGeneLists = new ArrayList<>();
        try {

            //判断比对类型
            String[] strings = stringsUtils(targetType);
            //快速比对 容差
            int conditionId = conditionIdUtils(condition);
            //根据条件去提交全库比对
            singleSampleGeneLists = sampleGeneListsUtils(strings, conditionId, geneInfo);
            //进行分页
            resultMap = ResultPageListUtils.resultPageList(pageInfo, singleSampleGeneLists);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("快速比对提交全库接口" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 快速比对 比对条件 公共方法
     *
     * @param targetType
     * @return
     */
    private String[] stringsUtils(String targetType) {
        String[] strings = null;
        //替换传过来的
        if (StringUtils.isNotBlank(targetType)) {
            targetType = targetType.replace("'", "");
            targetType = targetType.replace("\"", "");
            targetType = targetType.replace("]", "");
            targetType = targetType.replace("[", "");
            //判断比对类型
            strings = targetType.split(",");
        }
        return strings;
    }

    /**
     * 快速比对 容差 公共方法
     *
     * @param condition
     * @return
     */
    private int conditionIdUtils(String condition) {
        int conditionId = 0;
        if (condition != null && !condition.equals("")) {
            condition = condition.replace("\"", "");
            condition = condition.replace("]", "");
            condition = condition.replace("[", "");
            conditionId = Integer.parseInt(condition);
        }
        return conditionId;
    }

    /**
     * 快速比对 ：提交全库比对后  导出报表
     */
    @RequestMapping(value = "/exportComparison", method = RequestMethod.GET)
    public void exportWorkSchedules(HttpServletResponse response, String geneInfo,
                                    String targetType, String condition) throws IOException {

//        geneInfo = "[{\"geneName\":\"TPOX\",\"allele\":\"8,8\"},{\"geneName\":\"D2S1338\",\"allele\":\"19,22\"},{\"geneName\":\"D13S317\",\"allele\":\"12,12\"},{\"geneName\":\"D3S1358\",\"allele\":\"15,15\"}]";
//        targetType = "[\"01\",\"3\"]";
//        condition = "[\"3\"]";

        // 表头
        Map<String, String> title = new HashMap<>();
        // 需要导出的数据
        List<Map<String, Object>> data = new ArrayList<>();
        // 表头字段对应的位置（自定义位置）
        Map<String, Integer> position = new HashMap<>();

        List<SingleSampleGeneVo> singleSampleGeneLists = new ArrayList<>();
        try {

            //判断比对类型
            String[] strings = stringsUtils(targetType);
            //快速比对 容差
            int conditionId = conditionIdUtils(condition);
            //根据条件去提交全库比对
            singleSampleGeneLists = sampleGeneListsUtils(strings, conditionId, geneInfo);

            //表头字段对应的位置（自定义位置）
            position = positionUtils();
            // 设置表头信息
            title = titleUtils();
            //需要导出的数据
            data = singleSampleGeneListsUtils(singleSampleGeneLists);

            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            //获取文件后缀
            String xlsxName = FileUtils.getProperties("/application.yml", "xlsxName");
            //获取文件名称
            String excelNameS = excelName + date + xlsxName;

            excelNameS = URLEncoder.encode(excelNameS, "UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + excelNameS);
            response.setContentType("application/x-download");
            ExportExcelKit.exportDataToExcel(title, position, data, sheetName, response.getOutputStream());

        } catch (Exception e) {
            logger.error("导出全库列表接口失败" + e);
        }

    }

    /**
     * 根据条件去提交全库比对 公共方法
     *
     * @param strings
     * @param conditionId
     * @param geneInfo
     * @return
     * @throws Exception
     */
    private List<SingleSampleGeneVo> sampleGeneListsUtils(String[] strings, int conditionId, String geneInfo) throws Exception {
        List<SingleSampleGeneVo> singleSampleGeneList = null;
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        List<SingleSampleGeneVo> singleSampleGeneLists = new ArrayList<>();

        if (StringUtils.isNotBlank(geneInfo)) {
            if (strings != null) {
                //判断物证、人员、质控
                for (String sampleflag : strings) {
                    look:
                    //0 .物证
                    if (sampleflag.equals("0")) {
                        int num = 0;
                        boolean flag = true;
                        while (flag) {
                            //提交全库 0：物证信息list
                            singleSampleGeneList = singleSampleGeneService.selectSampleTypeSampleflag(sampleflag, num, 1000);
                            num++;
                            if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
                                flag = false;
                                break look;//直接跳出while语句
                            }
                            //判断strMix解析报告比对
                            if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                                singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
                                singleSampleGeneLists.addAll(singleSampleGeneVoList);

                            }
                        }
                    } else if (sampleflag.equals("3")) {
                        int num = 0;
                        boolean flag = true;
                        while (flag) {
                            //查询质控人员
                            singleSampleGeneList = qualtyPersonnelService.selecAllSampleQualityList(num, 1000);
                            num++;
                            if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
                                flag = false;
                                break look;//直接跳出while语句
                            }
                            //判断strMix解析报告比对
                            if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                                singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
                                singleSampleGeneLists.addAll(singleSampleGeneVoList);
                            }
                        }
                    } else {
                        int num = 0;
                        boolean flag = true;
                        while (flag) {
                            //1:提交本库人员查询物证信息list
                            singleSampleGeneList = singleSampleGeneService.selectPersonSampleflag(sampleflag, num, 1000);
                            num++;
                            if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
                                flag = false;
                                break look;//直接跳出while语句
                            }
                            //判断strMix解析报告比对
                            if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                                singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
                                singleSampleGeneLists.addAll(singleSampleGeneVoList);

                            }
                        }
                    }
                }
            }

        }
        return singleSampleGeneLists;
    }

    /**
     * 需要导出的数据  公共方法
     *
     * @param singleSampleGeneLists
     * @return
     */
    private List<Map<String, Object>> singleSampleGeneListsUtils(List<SingleSampleGeneVo> singleSampleGeneLists) {
        // 需要导出的数据
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> headersMap = null;

        // 遍历模拟的数据填充到headersMap集合
        for (SingleSampleGeneVo singleSampleGeneVo : singleSampleGeneLists) {
            headersMap = new HashMap<>();
            headersMap.put("sampleFlag", singleSampleGeneVo.getSampleFlag());
            headersMap.put("sampleNo", singleSampleGeneVo.getSampleNo());
            headersMap.put("sampleName", singleSampleGeneVo.getSampleName());
            headersMap.put("caseNo", singleSampleGeneVo.getCaseNo());
            headersMap.put("caseName", singleSampleGeneVo.getCaseName());
            headersMap.put("sameCount", singleSampleGeneVo.getSameCount());
            // 将headersMap添加到List集合中
            data.add(headersMap);
        }
        return data;
    }

    /**
     * 设置表头信息  公共方法
     *
     * @return
     */
    private Map<String, String> titleUtils() {
        // 表头
        Map<String, String> title = new HashMap<>();
        // 设置表头信息
        title.put("sampleFlag", "目标库别");
        title.put("sampleNo", "样本编号");
        title.put("sampleName", "样本名称");
        title.put("caseNo", "案件编号");
        title.put("caseName", "案件名称");
        title.put("sameCount", "匹配位点数");
        return title;

    }

    /**
     * 表头字段对应的位置  公共方法
     *
     * @return
     */
    private Map<String, Integer> positionUtils() {
        // 表头字段对应的位置（自定义位置）
        Map<String, Integer> position = new HashMap<>();
        // 设置表头字段位置
        position.put("sampleFlag", 0);
        position.put("sampleNo", 1);
        position.put("sampleName", 2);
        position.put("caseNo", 3);
        position.put("caseName", 4);
        position.put("sameCount", 5);
        return position;
    }

    /**
     * 已拆分分型比中单一分型列表
     *
     * @param request
     * @param response
     * @param caseId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectSplitSingleCompareResult", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectSplitSingleCompareResult(HttpServletRequest request, HttpServletResponse response,
                                                     String caseId, PageInfo pageInfo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //查询已拆分分型比中单一分型列表
//            MatchResultSplitSingleVo matchResultSplitSingleVo = new MatchResultSplitSingleVo();
//            matchResultSplitSingleVo.setCaseId(caseId);
//            List<MatchResultSplitSingleVo> matchResultSplitSingleVoList = matchResultSplitSingleService.selectSplitSingleCompareResult(matchResultSplitSingleVo, pageInfo);
            //查询已拆分分型比中单一分型总数
//            int matchResultSplitSingleVoCount = matchResultSplitSingleService.selectSplitSingleCompareResultCount(matchResultSplitSingleVo);
            //查询混合分型比中单一分型总数
//            MatchResultMixSingleVo matchResultMixSingleVo = new MatchResultMixSingleVo();
//            matchResultMixSingleVo.setCaseId(caseId);
//            int mixedSingleCompareResultCount = matchResultMixSingleService.selectMixedSingleCompareResultCount(matchResultMixSingleVo);
            //查询混合分型比中拆分分型总数
//            int mixedSplitedCompareResult = matchResultMixSingleService.selectMixedSplitedCompareResultCount(matchResultMixSingleVo);

//            resultMap.put("matchResultSplitSingleVoList", matchResultSplitSingleVoList);
//            resultMap.put("matchResultSplitSingleVoCount", matchResultSplitSingleVoCount);
//            resultMap.put("mixedSingleCompareResultCount", mixedSingleCompareResultCount);
//            resultMap.put("mixedSplitedCompareResult", mixedSplitedCompareResult);
//            resultMap.put("pageInfo", pageInfo.updatePageInfo(matchResultSplitSingleVoCount));

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("已拆分分型比中单一分型列表" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 混合分型比中单一分型列表
     *
     * @param request
     * @param response
     * @param caseId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMixedSingleCompareResult", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectMixedSingleCompareResult(HttpServletRequest request, HttpServletResponse response,
                                                     String caseId, PageInfo pageInfo) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        try {

            //混合分型比中单一分型列表
//            MatchResultMixSingleVo matchResultMixSingleVo = new MatchResultMixSingleVo();
//            matchResultMixSingleVo.setCaseId(caseId);
//            List<MatchResultMixSingleVo> matchResultSplitSingleVoList = matchResultMixSingleService.selectMixedSingleCompareResult(matchResultMixSingleVo, pageInfo);

            //查询混合分型比中单一分型总数
//            int mixedSingleCompareResultCount = matchResultMixSingleService.selectMixedSingleCompareResultCount(matchResultMixSingleVo);

//            resultMap.put("matchResultSplitSingleVoList", matchResultSplitSingleVoList);
//            resultMap.put("mixedSingleCompareResultCount", mixedSingleCompareResultCount);
//            resultMap.put("pageInfo", pageInfo.updatePageInfo(mixedSingleCompareResultCount));

            System.out.print(JSON.toJSON(new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS)));

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {

            logger.error("混合分型比中单一分型列表" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 混合分型比中拆分分型列表
     *
     * @param request
     * @param response
     * @param caseId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectMixedSplitedCompareResult", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultBean selectMixedSplitedCompareResult(HttpServletRequest request, HttpServletResponse response,
                                                      String caseId, PageInfo pageInfo) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        try {
            //混合分型比中拆分分型列表
//            MatchResultMixSingleVo matchResultMixSingleVo = new MatchResultMixSingleVo();
//            matchResultMixSingleVo.setCaseId(caseId);
//            List<MatchResultMixSingleVo> matchResultSplitSingleVoList = matchResultMixSingleService.selectMixedSplitedCompareResult(matchResultMixSingleVo, pageInfo);

            //查询混合分型比中拆分分型总数
//            int mixedSplitedCompareResultCount = matchResultMixSingleService.selectMixedSplitedCompareResultCount(matchResultMixSingleVo);

//            resultMap.put("matchResultSplitSingleVoList", matchResultSplitSingleVoList);
//            resultMap.put("mixedSplitedCompareResultCount", mixedSplitedCompareResultCount);
//            resultMap.put("pageInfo", pageInfo.updatePageInfo(mixedSplitedCompareResultCount));

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultMap, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {

            logger.error("混合分型比中拆分分型列表" + e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     * 快速比对 查看比中详情
     *
     * @param request
     * @param response
     * @param geneInfo //混合基因信息
     * @param genneId  //基因id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/examineComparison", produces = "application/json;charset=UTF-8")
    public ResultBean examineComparison(HttpServletRequest request, HttpServletResponse response, String geneInfo,
                                        String genneId) throws IOException {

        Map<String, Object> resultGenne = new HashMap<>();

/*        geneInfo="[{\"geneName\":\"TPOX\",\"allele\":\"8,11\"},{\"geneName\":\"D2S1338\",\"allele\":\"18,21\"},{\"geneName\":\"D13S317\",\"allele\":\"8,8\"},{\"geneName\":\"D3S1358\",\"allele\":\"15,15\"}]";*/
        try {
            if (StringUtils.isNotBlank(genneId) && StringUtils.isNotBlank(geneInfo)){
                List<SingleSampleGene> singleSampleGeneList = singleSampleGeneService.selectSingleSampleGeneDetails(genneId);
                //判空
                if (ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {

                    SingleSampleGene singleSampleGene = singleSampleGeneList.get(0);
                    //基因判空
                    Map<String, List<String>> singleSampleGeneInfo = null;
                    if (StringUtils.isNotBlank(singleSampleGene.getGeneInfo())) {
                        singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(singleSampleGene.getGeneInfo());
                    }

                    //判空
                    if (singleSampleGeneInfo != null && StringUtils.isNotBlank(geneInfo)) {
                        resultGenne = geneMixCompareUtil.comparisonResult(singleSampleGeneInfo, geneInfo);
                    }
                    resultGenne.put("ratioSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);
                    //比对图谱
                    if (StringUtils.isNotBlank(singleSampleGene.getGenePicture())) {
                        resultGenne.put("splitdSampleGeneImagePath", singleSampleGene.getGenePicture());
                    } else {
                        resultGenne.put("splitdSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

                    }
                }else {
                    List<QualtyPersonnel> qualtyPersonnelList = qualtyPersonnelService.selectMixedSampleQuality(genneId);
                    if(ListUtils.isNotNullAndEmptyList(qualtyPersonnelList)){
                        QualtyPersonnel personnel = qualtyPersonnelList.get(0);
                        //基因判空
                        Map<String, List<String>> singleSampleGeneInfo = null;
                        if (StringUtils.isNotBlank(personnel.getGeneInfo())) {
                            singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(personnel.getGeneInfo());
                        }

                        //判空
                        if (singleSampleGeneInfo != null && StringUtils.isNotBlank(geneInfo)) {
                            resultGenne = geneMixCompareUtil.comparisonResult(singleSampleGeneInfo, geneInfo);
                        }
                        resultGenne.put("ratioSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);
                        //比对图谱
                        if (StringUtils.isNotBlank(personnel.getGenePicture())) {
                            resultGenne.put("splitdSampleGeneImagePath", personnel.getGenePicture());
                        } else {
                            resultGenne.put("splitdSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

                        }
                    }
                }

                return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultGenne, ResultBean.NAME_SUCCESS);
            }else {
                return new ResultBean(ResultBean.CODE_ERROR,
                        ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                        ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
            }
        } catch (Exception e) {
            logger.error("快速比对查看比中详情全库接口" + e);
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }

    }

    /**
     * 快速比对 拆分入库
     *
     * @param request
     * @param response
     * @param geneInfo //拆分基因信息
     * @param genneId  //混合id
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertSplitComparison", produces = "application/json;charset=UTF-8")
    public ResultBean insertSplitComparison(HttpServletRequest request, HttpServletResponse response,
                                            String geneInfo, String genneId) throws IOException {

        Map<String, Object> result = new HashMap<>();
//        geneInfo = "[{\"geneName\":\"TPOX\",\"allele\":\"8,11\"},{\"geneName\":\"D2S1338\",\"allele\":\"18,21\"},{\"geneName\":\"D13S317\",\"allele\":\"8,8\"},{\"geneName\":\"D3S1358\",\"allele\":\"15,15\"}]";

        try {
            //解析前端传过来的基因信息
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            Map<String, Object> geneMap1 = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String[] allele1 = allele.split(",");
                geneMap1.put(markerName, allele1);
            }
            //数据库查询出来的基因信息
            List<SplitedSampleGene> geneDBList = splitedSampleGeneService.selectSplitedSampleGeneByMixedSampleGeneId(genneId);
            //用来统计循环对比的次数
            int count = countUtils1(geneDBList, geneMap1);

            //去判断是否添加入库还是返回重复
            result = comparisonCountUtil(count, geneDBList, geneMap1, genneId);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, result, ResultBean.NAME_SUCCESS);

        } catch (Exception e) {
            logger.error("快速比对拆分入库接口" + e);
            return new ResultBean(ResultBean.CODE_ERROR,
                    ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }

    }

    /**
     * 判断拆分入库是否重复后  是否添加  的公共方法
     *
     * @param count
     * @param geneDBList
     * @param geneMap1
     * @param genneId
     * @return
     * @throws Exception 
     */
    private Map<String, Object> resultCountUtils(int count, List<SplitedSampleGene> geneDBList,
                                                 Map<String, Object> geneMap1, String genneId,
                                                 int mixSameCount,SampleInfoVo infoVo) throws Exception {
        Map<String, Object> result = new HashMap<>();

        //如果遍历一遍 没有相同的就添加
        if (count == geneDBList.size()) {
            //获取用户信息
        		AuthUser user = RequestUtils.getAuthUser();
            //执行添加方法
            //入库拆分单一样本信息
            SplitedSampleGene splitedSampleGene1 = new SplitedSampleGene();
            splitedSampleGene1.setId(UUID.randomUUID().toString());
            splitedSampleGene1.setCreateDatetime(new Date());
            //splitedSampleGene1.setCreatePerson();
            splitedSampleGene1.setGeneInfo(JSON.toJSONString(geneMap1));
            splitedSampleGene1.setMixedSampleGeneId(genneId);
            splitedSampleGeneService.insert(splitedSampleGene1);
            result.put("code", 0);

            if (infoVo != null) {
                CompareQueue compareQueue = new CompareQueue();
//                compareQueue.setSampleId(infoVo.getEntity().getId());
                compareQueue.setQueueType(Constants.QUEUE_TYPE_02);
//                compareQueue.setMixSameCount(mixSameCount);
                compareQueue.setCreatePerson(user.getId());
                compareQueueService.insertCompareQueue(compareQueue);
            }
        } else {
            //有重复的基因信息
            result.put("code", 1);
        }
        return result;
    }

    private Map<String, Object> comparisonCountUtil(int count, List<SplitedSampleGene> geneDBList,
                                                 Map<String, Object> geneMap1, String genneId) throws Exception {
        Map<String, Object> result = new HashMap<>();

        //如果遍历一遍 没有相同的就添加
        if (count == geneDBList.size()) {
            //获取用户信息
        		AuthUser user = RequestUtils.getAuthUser();
            //执行添加方法
            //入库拆分单一样本信息
            SplitedSampleGene splitedSampleGene1 = new SplitedSampleGene();
            splitedSampleGene1.setId(UUID.randomUUID().toString());
            splitedSampleGene1.setCreateDatetime(new Date());
            //splitedSampleGene1.setCreatePerson();
            splitedSampleGene1.setGeneInfo(JSON.toJSONString(geneMap1));
            splitedSampleGene1.setMixedSampleGeneId(genneId);
            splitedSampleGeneService.insert(splitedSampleGene1);
            result.put("code", 0);
            //将拆分基因型插入到比对队列表中
            //TODO 根据混合基因id查询样本信息
            List<SampleInfoVo> sampleInfoList = sampleInfoService.selectSampleInfoList(genneId);
            if (ListUtils.isNotNullAndEmptyList(sampleInfoList)) {
                CompareQueue compareQueue = new CompareQueue();
//                compareQueue.setSampleId(sampleInfoList.get(0).getEntity().getId());
                compareQueue.setQueueType(Constants.QUEUE_TYPE_02);
                compareQueue.setCreatePerson(user.getId());
                compareQueueService.insertCompareQueue(compareQueue);
            }
        } else {
            //有重复的基因信息
            result.put("code", 1);
        }
        return result;
    }

    /**
     * 判断拆分入库是否是重复数据  公共方法
     *
     * @param geneDBList
     * @param geneMap1
     * @return
     */
    private int countUtils(List<SplitedSampleGene> geneDBList, Map<String, Object> geneMap1,
                           String mixSameCount, SampleInfoVo sampleInfoVo) {

        int count = 0;
        //遍历数据库的基因信息
        for (SplitedSampleGene splitedSampleGene : geneDBList) {
            //查看匹配下线是否相同
            CompareQueue compareQueue = new CompareQueue();
//            compareQueue.setSampleId(sampleInfoVo.getEntity().getId());
//            compareQueue.setMixSameCount(Integer.parseInt(mixSameCount));
            List<CompareQueue> compareQueueList = compareQueueService.selectByMixSameCount(compareQueue);
            //没有相同的匹配下限就数量增加后跳过,否则判断基因信息是否相同
            if (ListUtils.isNotNullAndEmptyList(compareQueueList)){
                //对数据库基因信息转为Map对象
                Map<String, Object> geneDBMap = JSON.parseObject(splitedSampleGene.getGeneInfo());
                Map<String, Object> geneMap = JSON.parseObject(JSON.toJSONString(geneMap1));
                //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
                if (geneDBMap.keySet().size() != geneMap.keySet().size()) {
                    count++;
                    continue;
                } else {
                    //如果相同那么循环比较各个value值
                    for (String key : geneMap.keySet()) {
                        //如果前台的key在数据库的基因信息不存在则跳出循环
                        if (geneDBMap.get(key) == null) {
                            count++;
                            break;
                        } else {
                            //判断基因value值
                            if (match(geneDBMap.get(key).toString(), geneMap.get(key).toString())) {
                                continue;
                            } else {
                                count++;
                                break;
                            }
                        }
                    }

                }
            }else {
                count++;
                continue;
            }
        }
        return count;
    }

    /**
     * 判断拆分入库是否是重复数据
     *
     * @param geneDBList
     * @param geneMap1
     * @return
     */
    private int countUtils1(List<SplitedSampleGene> geneDBList, Map<String, Object> geneMap1) {

        int count = 0;
        //遍历数据库的基因信息
        for (SplitedSampleGene splitedSampleGene : geneDBList) {
            //对数据库基因信息转为Map对象
            Map<String, Object> geneDBMap = JSON.parseObject(splitedSampleGene.getGeneInfo());
            Map<String, Object> geneMap = JSON.parseObject(JSON.toJSONString(geneMap1));
            //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
            if (geneDBMap.keySet().size() != geneMap.keySet().size()) {
                count++;
                continue;
            } else {
                //如果相同那么循环比较各个value值
                for (String key : geneMap.keySet()) {
                    //如果前台的key在数据库的基因信息不存在则跳出循环
                    if (geneDBMap.get(key) == null) {
                        count++;
                        break;
                    } else {
                        //判断基因value值
                        if (match(geneDBMap.get(key).toString(), geneMap.get(key).toString())) {

                            continue;
                        } else {
                            count++;
                            break;
                        }
                    }
                }

            }
        }
        return count;
    }

     /**
     * 数据库的 比中详情信息
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectDatabaseRatioDetails", produces = "application/json;charset=UTF-8")
    public ResultBean selectDatabaseRatioDetails(String mixedSampleGendId, String splieId,String singleId) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> resultDetails = new HashMap<>();
        int mixSameCount = 0;
        try {

            //根据id查询拆分样本
            List<SplitedSampleGene> splitedSampleGeneList = splitedSampleGeneService.selectSplitedSampleGeneList(splieId);

            //根据id查询单一样本
            List<SingleSampleGene> singleSampleGeneList = singleSampleGeneService.selectSingleSampleGene(singleId);

            //根据id查询混合样本
            List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleSuspect(mixedSampleGendId);

            //拆分 和 单一 基因信息比中
            if (ListUtils.isNotNullAndEmptyList(splitedSampleGeneList) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {

                result = splitSingleSampleGene(splitedSampleGeneList, singleSampleGeneList, mixSameCount);

            }
            //混合 和 单一  基因信息比中
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
                result = mixedSingleSampleGene(mixedSampleGeneList, singleSampleGeneList, mixSameCount);
            }
            //混合 和 拆分  基因信息比中
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList) && ListUtils.isNotNullAndEmptyList(splitedSampleGeneList)) {
                result = mixedSplitSampleGene(mixedSampleGeneList, splitedSampleGeneList);
            }

            resultDetails.put("viewRatioGeneDetails", result);

            return new ResultBean(ResultBean.CODE_SUCCESS, 0, resultDetails, ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("数据库的查看比中详情信息" + e);

            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }


    /**
     * 拆分 比中 单一样本的详情
     *
     * @param splitedSampleGeneList
     * @param singleSampleGeneList
     * @return
     */
    private Map<String, Object> splitSingleSampleGene(List<SplitedSampleGene> splitedSampleGeneList,
                                                      List<SingleSampleGene> singleSampleGeneList, int mixSameCount) {
        Map<String, Object> result = new HashMap<>();

        //混合基因
        SplitedSampleGene splitedSampleGene = splitedSampleGeneList.get(0);
        //比对基因
        SingleSampleGene singleSampleGene = singleSampleGeneList.get(0);


        Map<String, List<String>> splitedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(splitedSampleGene.getGeneInfo());

        Map<String, List<String>> singleSampleInfos = GeneformatUtils.mixedSampleGeneformat(singleSampleGene.getGeneInfo());

        //进入比对
        if (splitedSampleGeneInfo != null && singleSampleInfos != null) {
            result = geneMixCompareUtil.compareResult(splitedSampleGeneInfo, singleSampleInfos, mixSameCount);
        }

        result.put("splitdSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);


        //比对图谱
        if (StringUtils.isNotBlank(singleSampleGene.getGenePicture())) {
            result.put("ratioSampleGeneImagePath", singleSampleGene.getGenePicture());
        } else {
            result.put("ratioSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

        }
        return result;
    }

    /**
     * 混合 比中 拆分样本的详情
     *
     * @param splitedSampleGeneList
     * @param mixedSampleGeneList
     * @return
     */
    private Map<String, Object> mixedSplitSampleGene(List<MixedSampleGene> mixedSampleGeneList,
                                                     List<SplitedSampleGene> splitedSampleGeneList
    ) {
        Map<String, Object> result = new HashMap<>();
        int mixSameCount = 0;
        //混合基因
        MixedSampleGene mixedSampleGene = mixedSampleGeneList.get(0);
        //比对基因
        SplitedSampleGene splitedSampleGene = splitedSampleGeneList.get(0);

        Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGene.getGeneInfo());

        Map<String, List<String>> splitSampleInfo = GeneformatUtils.mixedSampleGeneformat(splitedSampleGene.getGeneInfo());


        //进入比对
        if (mixedSampleGeneInfo != null && splitSampleInfo != null) {
            result = geneMixCompareUtil.compareResult(mixedSampleGeneInfo, splitSampleInfo, mixSameCount);
        }
        //比对图谱
        if (StringUtils.isNotBlank(mixedSampleGene.getGenePicture())) {

            result.put("splitdSampleGeneImagePath", mixedSampleGene.getGenePicture());
        } else {

            result.put("splitdSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);

        }
        result.put("ratioSampleGeneImagePath", GlobalConstants.SAMPLE_GANE_IMAGEPATH);


        return result;
    }


    /**
     *  6.1  全库比对
     * @throws Exception 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
    @RequestMapping(value = "/allCompare", produces = "application/json;charset=UTF-8")
    public ResultBean allCompare(@CurrentUser AuthUser authUser,
    		String geneInfo,String targetType,String condition,String fileName,String weight,String contributorName) throws Exception {
        if (geneInfo != null){
            //获取登录信息
            //基因型转化
            List<Map<String, Object>> jsonArray = null;
            //前端传过来的基因信息转成list
            jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            Map<String, Object> geneMap = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String[] allele1 = allele.split(",");
                geneMap.put(markerName, allele1);
            }
//            String geneInfos = JSON.toJSONString(geneMap);
            //快速比对 容差
//            int conditionId = conditionIdUtils(condition);
            //添加比对队列
//            CompareContribution compareContribution = new CompareContribution();
//            compareContribution.setId(UUID.randomUUID().toString());
//            compareContribution.setQueueType("1");//队列类型
//            compareContribution.setGeneInfo(geneInfos);//基因信息
//            compareContribution.setCondition(String.valueOf(conditionId));//容差
//            compareContribution.setFileName(fileName);//文件名称
//            compareContribution.setWeight(weight);//权重
//            compareContribution.setTargetType(targetType);//比对类型
//            compareContribution.setContributorName(contributorName);//贡献者
//            compareContribution.setStatus(Constants.MATCH_STATUS_02);
//            compareContribution.setCreatePerson(user.getId());
//            compareContribution.setCreateDatetime(new Date());
//            compareContributionService.insert(compareContribution);
            logger.info("添加贡献者比对信息 : " + LocalDateTime.now().toLocalTime());
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, 0, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空!");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /**
     * 混合样本峰图上传 或者 修改
     */
    @PostMapping(value = "/uploadGenePicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData uploadGenePicture(HttpServletRequest request,@RequestParam("id") String id, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        try {
			if (StringUtils.isBlank(id)) {
				ResponseData response = filesServerClient.upload(multipartFile);
				return response;
            }else {
            		ResponseData response = filesServerClient.upload(multipartFile);
            		String url = response.getResult().toString();

                MixedSampleGene mixedSampleGene = new MixedSampleGene();
                mixedSampleGene.setId(id);
                mixedSampleGene.setGenePicture(url);
                mixedSampleGeneService.updateGenePictureById(mixedSampleGene);
                return new ResponseData(url);
            }
        }catch(Exception ex){
            logger.error("invoke MainPageController.uploadGenePicture error.", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }
    }

    /**
     * 混合样本峰图删除
     */
    @GetMapping(value = "/deleteGenePictureById" , produces = "application/json; charset=utf-8")
    public ResponseData deleteGenePictureById(HttpServletRequest request, @RequestParam("id") String id) throws IOException {
        try {
            Boolean bool = true;
            if (StringUtils.isNotBlank(id)){
                MixedSampleGene sampleGene = mixedSampleGeneService.selectById(id);
                if (sampleGene != null){
                    bool = mixedSampleGeneService.deleteGenePictureById(id);
                }
            }
            return new ResponseData(bool);
        }catch(Exception ex){
            logger.error("invoke MainPageController.updateGenePictureById error.", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }
    }

}
