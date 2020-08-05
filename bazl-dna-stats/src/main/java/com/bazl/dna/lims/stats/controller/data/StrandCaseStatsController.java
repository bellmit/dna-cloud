package com.bazl.dna.lims.stats.controller.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.stats.controller.BaseController;

/**
 * Created by zhangbo on 2019/11/1.
 */
@Controller
@RequestMapping("/strandCase")
public class StrandCaseStatsController extends BaseController {

//    @Value("${comparison_url}")
//    private String comparisonUrl;

    @Autowired
    OrgInfoService orgInfoService;

    /**
     * tonyi
     *
     * @param request
     * @param matchResult
     * @param pageInfo
     * @return
     * @throws JsonProcessingException
     *//*
    @RequestMapping("/strandCaseStatsList")
    public ModelAndView caseAndBring(HttpServletRequest request, MatchSameResultVo matchResult, PageInfo pageInfo) throws JsonProcessingException {
        ModelAndView view = new ModelAndView();
        String result = null;
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
        }

        JSONObject obj = new JSONObject();
        obj.put("page", pageInfo.getPage());
        obj.put("orgId", operateUser.getOrgId());
        obj.put("matchResult", matchResult);
        String afferentJson = obj.toString();
        HttpClient client = HttpClients.createDefault();
        String url = comparisonUrl + "/queryCompareResult/querySameAreaSameCompareResultList";

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(afferentJson);
            se.setContentType("text/json");
            post.setEntity(se);
            HttpResponse res = client.execute(post);
            result = EntityUtils.toString(res.getEntity());
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }
        //查询受理单位
        List<OrgInfo> acceptOrgList = orgInfoService.selectAcceptOrgList();
        view.addObject("acceptOrgList", acceptOrgList);
        //查询委托单位（分局）
        String orgParentsId = "110000000000";
        List<OrgInfo> orgInfoList = orgInfoService.selectDelegateByParentsId(orgParentsId);
        view.addObject("orgInfoList", orgInfoList);

        //入库类型
        DictItem dictItem = new DictItem();
        dictItem.setDictTypeCode(Constants.INSTORED_TYPE);
        List<DictItem> dictSampleEntryTypeList = DictUtil.getDictList(dictItem);
        view.addObject("dictSampleEntryTypeList", dictSampleEntryTypeList);

        //比对类型
        dictItem.setDictTypeCode(Constants.COMPARISON_TYPE);
        List<DictItem> comparisonTypeList = DictUtil.getDictList(dictItem);
        view.addObject("comparisonTypeList", comparisonTypeList);

        //比对类别
        List<DictItem> comparisonCategoryList1 = new LinkedList<>();
        dictItem.setDictTypeCode(Constants.COMPARISON_CATEGORY);
        List<DictItem> comparisonCategoryList = DictUtil.getDictList(dictItem);
        for (DictItem dictItem1 : comparisonCategoryList) {
            if (("05").equals(dictItem1.getDictCode())) {
                comparisonCategoryList1.add(dictItem1);
            } else if (("03").equals(dictItem1.getDictCode())) {
                comparisonCategoryList1.add(dictItem1);
            } else if (("04").equals(dictItem1.getDictCode())) {
                comparisonCategoryList1.add(dictItem1);
            } else if (("02").equals(dictItem1.getDictCode())) {
                comparisonCategoryList1.add(dictItem1);
            }
        }
        view.addObject("comparisonCategoryList", comparisonCategoryList1);

        //状态
        List<DictItem> compareStatusList1 = new LinkedList<>();
        dictItem.setDictTypeCode(Constants.COMPARE_STATUS);
        List<DictItem> compareStatusList = DictUtil.getDictList(dictItem);
        for (DictItem dictItem1 : compareStatusList) {
            if (("0").equals(dictItem1.getDictCode())) {
                compareStatusList1.add(dictItem1);
            } else if (("2").equals(dictItem1.getDictCode())) {
                compareStatusList1.add(dictItem1);
            } else if (("3").equals(dictItem1.getDictCode())) {
                compareStatusList1.add(dictItem1);
            }
        }
        view.addObject("compareStatusList", compareStatusList1);

        //jeixi
        JSONObject jsonObject = JSON.parseObject(result);

        String userOrgId = jsonObject.get("userOrgId").toString();
        List<LibMatchResultGroupDomain> matchResultListNew = (List<LibMatchResultGroupDomain>) jsonObject.get("matchResultGroupList");
        pageInfo = JSON.parseObject(jsonObject.get("pageInfo").toString(), PageInfo.class);
        String comparisonCategory = jsonObject.get("comparisonCategory").toString();
        String matchResultJsonStr = jsonObject.get("matchResult").toString();
        matchResult = JSON.parseObject(matchResultJsonStr, MatchSameResultVo.class);

        view.addObject("userOrgId", userOrgId);
        view.addObject("matchResultGroupList", matchResultListNew);
        view.addObject("pageInfo", pageInfo);
        view.addObject("comparisonCategory", comparisonCategory);
        view.addObject("matchResult", matchResult);

        view.setViewName("strandCase/areaSameCompareResultList");
        return view;
    }

    *//**
     * 查看跨区同一比对结果详情
     *
     * @param matchResult
     * @param pageInfo
     * @return
     *//*
    @RequestMapping("/querySameAreaSameCompareResultDetail")
    public ModelAndView queryAreaSameCompareResultDetail(MatchSameResultVo matchResult, PageInfo pageInfo) {
        ModelAndView view = new ModelAndView();
        String result = null;
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo loaUserInfo = (LoaUserInfo) subject.getPrincipal();

        JSONObject obj = new JSONObject();
        obj.put("page", pageInfo.getPage());
        obj.put("orgId", loaUserInfo.getOrgId());
        obj.put("matchResult", matchResult);
        String afferentJson = obj.toString();
        HttpClient client = HttpClients.createDefault();
        String url = comparisonUrl + "/queryCompareResult/querySameAreaSameCompareResultDetail";

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(afferentJson);
            se.setContentType("text/json");
            post.setEntity(se);
            HttpResponse res = client.execute(post);
            result = EntityUtils.toString(res.getEntity());
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }


        try {
            //入库类型
            DictItem dictItem = new DictItem();
            dictItem.setDictTypeCode(Constants.INSTORED_TYPE);
            List<DictItem> dictSampleEntryTypeList = DictUtil.getDictList(dictItem);
            view.addObject("dictSampleEntryTypeList", dictSampleEntryTypeList);

            //状态
            List<DictItem> compareStatusList1 = new LinkedList<>();
            dictItem.setDictTypeCode(Constants.COMPARE_STATUS);
            List<DictItem> compareStatusList = DictUtil.getDictList(dictItem);
            for (DictItem dictItem1 : compareStatusList) {
                if (("0").equals(dictItem1.getDictCode())) {
                    compareStatusList1.add(dictItem1);
                } else if (("2").equals(dictItem1.getDictCode())) {
                    compareStatusList1.add(dictItem1);
                } else if (("3").equals(dictItem1.getDictCode())) {
                    compareStatusList1.add(dictItem1);
                }
            }
            view.addObject("compareStatusList", compareStatusList1);

            JSONObject jsonObject = JSON.parseObject(result);
            if (null != jsonObject) {
                List<AmPersonalInfo> amPersonalInfoList = (List<AmPersonalInfo>) jsonObject.get("amPersonalInfoList");
                List<Map<String, Object>> matchResultList = (List<Map<String, Object>>) jsonObject.get("matchResultList");
                pageInfo = JSON.parseObject(jsonObject.get("pageInfo").toString(), PageInfo.class);
                String matchResultJsonStr = jsonObject.get("matchResult").toString();
                matchResult = JSON.parseObject(matchResultJsonStr, MatchSameResultVo.class);

                view.addObject("amPersonalInfoList", amPersonalInfoList);
                view.addObject("matchResultList", matchResultList);
                view.addObject("matchResult", matchResult);
                view.addObject("pageInfo", pageInfo);
            } else {
                logger.info("同一比对结果=" + result);
            }
            view.setViewName("strandCase/viewAreaSameCompareDetail");
        } catch (Exception e) {
            logger.info("查看同一比对结果详细报错:" + e);
        }

        return view;
    }

    *//**
     * 查看跨区同一本地库比对结果详细单
     *
     * @param request
     * @param matchResultVo
     * @param pageInfo
     * @return
     *//*
    @RequestMapping("/querySameAreaSameCompareCondition")
    public ModelAndView queryAreaSameCompareCondition(HttpServletRequest request, MatchSameResultVo matchResultVo, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        String result = null;
        JSONObject obj = new JSONObject();
        obj.put("page", pageInfo.getPage());
        obj.put("matchResultVo", matchResultVo);
        String afferentJson = obj.toString();
        HttpClient client = HttpClients.createDefault();
        String url = comparisonUrl + "/queryCompareResult/querySameAreaSameCompareCondition";

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(afferentJson);
            se.setContentType("text/json");
            post.setEntity(se);
            HttpResponse res = client.execute(post);
            result = EntityUtils.toString(res.getEntity());
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        JSONObject jsonObject = JSON.parseObject(result);
        matchResultVo = JSON.parseObject(jsonObject.get("matchResultVo").toString(), MatchSameResultVo.class);
        modelAndView = JSON.parseObject(jsonObject.get("modelAndView").toString(), ModelAndView.class);

        modelAndView.addObject("matchResultVo", matchResultVo);
        modelAndView.setViewName("strandCase/viewAreaCompareCondition");
        return modelAndView;
    }


    *//**
     * 查询跨区亲缘比对结果
     *
     * @param request
     * @param matchResult
     * @param pageInfo
     * @return
     *//*
    @RequestMapping("/queryAreaRelationCompareResultList")
    public ModelAndView queryAreaRelationCompareResult(HttpServletRequest request, MatchRelativeResultVo matchResult, PageInfo pageInfo,
                                                       String sameCount, String compareStatus) {
        ModelAndView view = new ModelAndView();
        String result = null;
        JSONObject obj = new JSONObject();
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");

        }
        if (StringUtils.isNoneBlank(sameCount)) {
            matchResult.getEntity().setSameCount(Integer.parseInt(sameCount));
        }
        if (StringUtils.isNoneBlank(compareStatus)) {
            matchResult.getEntity().setCompareStatus(compareStatus);
        }
        obj.put("sameCount", sameCount);
        obj.put("compareStatus", compareStatus);
        obj.put("page", pageInfo.getPage());
        obj.put("orgId", operateUser.getOrgId());
        obj.put("matchResult", matchResult);
        String afferentJson = obj.toString();
        HttpClient client = HttpClients.createDefault();
        String url = comparisonUrl + "/queryCompareResult/querySameAreaRelationCompareResultList";

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(afferentJson);
            se.setContentType("text/json");
            post.setEntity(se);
            HttpResponse res = client.execute(post);
            result = EntityUtils.toString(res.getEntity());
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        //查询受理单位
        List<OrgInfo> acceptOrgList = orgInfoService.selectAcceptOrgList();
        //获取当前用户的id
        String userOrgId = operateUser.getOrgId();
        //将当前用户org_id设置进query
        if (StringUtils.isNotBlank(userOrgId)
                && (userOrgId.contains(Constants.FORENSIC_CENTER_SHORT_ORG_ID)
                || userOrgId.contains(Constants.FORENSIC_CENTER_SHORT_ORG_ID_BAK))) {
            userOrgId = Constants.FORENSIC_CENTER_ORG_ID;
        }

        if (StringUtils.isNoneBlank(userOrgId)) {
            matchResult.setUserOrdId(userOrgId);
            matchResult.setAcceptOrgId(userOrgId);
        }

        //查询委托单位（分局）
        String orgParentsId = Constants.TOP_ORG_ID;
        List<OrgInfo> orgInfoList = orgInfoService.selectDelegateByParentsId(orgParentsId);
        view.addObject("orgInfoList", orgInfoList);

        //入库类型
        DictItem dictItem = new DictItem();
        dictItem.setDictTypeCode(Constants.INSTORED_TYPE);
        List<DictItem> dictSampleEntryTypeList = DictUtil.getDictList(dictItem);
        view.addObject("dictSampleEntryTypeList", dictSampleEntryTypeList);

        //比对类型
        dictItem.setDictTypeCode(Constants.COMPARISON_TYPE);
        List<DictItem> comparisonTypeList = DictUtil.getDictList(dictItem);
        view.addObject("comparisonTypeList", comparisonTypeList);

        //比对类别
        dictItem.setDictTypeCode(Constants.COMPARISON_CATEGORY);
        List<DictItem> comparisonCategoryList = DictUtil.getDictList(dictItem);
        view.addObject("comparisonCategoryList", comparisonCategoryList);

        //状态
        List<DictItem> compareStatusList1 = new LinkedList<>();
        dictItem.setDictTypeCode(Constants.COMPARE_STATUS);
        List<DictItem> compareStatusList = DictUtil.getDictList(dictItem);
        for (DictItem dictItem1 : compareStatusList) {
            if (("0").equals(dictItem1.getDictCode())) {
                compareStatusList1.add(dictItem1);
            } else if (("2").equals(dictItem1.getDictCode())) {
                compareStatusList1.add(dictItem1);
            } else if (("3").equals(dictItem1.getDictCode())) {
                compareStatusList1.add(dictItem1);
            }
        }
        view.addObject("compareStatusList", compareStatusList1);

        JSONObject jsonObject = JSON.parseObject(result);

        acceptOrgList = (List<OrgInfo>) jsonObject.get("acceptOrgList");
        userOrgId = jsonObject.get("userOrgId").toString();
        List<LibMatchResultGroupDomain> matchResultGroupList = (List<LibMatchResultGroupDomain>) jsonObject.get("matchResultGroupList");
        pageInfo = JSON.parseObject(jsonObject.get("pageInfo").toString(), PageInfo.class);
        String comparisonCategory = null;
        if (null != jsonObject.get("comparisonCategory")) {
            comparisonCategory = jsonObject.get("comparisonCategory").toString();
        }
        String matchResultJsonStr = jsonObject.get("matchResult").toString();
        matchResult = JSON.parseObject(matchResultJsonStr, MatchRelativeResultVo.class);

        view.addObject("acceptOrgList", acceptOrgList);
        view.addObject("userOrgId", userOrgId);
        view.addObject("matchResultGroupList", matchResultGroupList);
        view.addObject("pageInfo", pageInfo);
        view.addObject("comparisonCategory", comparisonCategory);
        view.addObject("matchResult", matchResult);
        view.setViewName("strandCase/areaRelationCompareResultList");
        return view;
    }

    *//**
     * 查看跨区亲缘结果详情
     *
     * @param matchResult
     * @param pageInfo
     * @return
     *//*
    @RequestMapping("/querySameAreaRelationCompareResultDetail")
    public ModelAndView queryAreaRelationCompareResultDetail(MatchRelativeResultVo matchResult, PageInfo pageInfo) {
        ModelAndView view = new ModelAndView();
        String result = null;
        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo loaUserInfo = (LoaUserInfo) subject.getPrincipal();
        if (loaUserInfo == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        JSONObject obj = new JSONObject();
        obj.put("page", pageInfo.getPage());
        obj.put("orgId", loaUserInfo.getOrgId());
        obj.put("matchResult", matchResult);
        String afferentJson = obj.toString();
        HttpClient client = HttpClients.createDefault();
        String url = comparisonUrl + "/queryCompareResult/querySameAreaRelationCompareResultDetail";

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(afferentJson);
            se.setContentType("text/json");
            post.setEntity(se);
            HttpResponse res = client.execute(post);
            result = EntityUtils.toString(res.getEntity());

            logger.info("查询亲缘结果详情:" + result);
        } catch (Exception ex) {
            logger.error("查询失败:", ex);
        }

        try {
            //入库类型
            DictItem dictItem = new DictItem();
            dictItem.setDictTypeCode(Constants.INSTORED_TYPE);
            List<DictItem> dictSampleEntryTypeList = DictUtil.getDictList(dictItem);
            view.addObject("dictSampleEntryTypeList", dictSampleEntryTypeList);

            //状态
            List<DictItem> compareStatusList1 = new LinkedList<>();
            dictItem.setDictTypeCode(Constants.COMPARE_STATUS);
            List<DictItem> compareStatusList = DictUtil.getDictList(dictItem);
            for (DictItem dictItem1 : compareStatusList) {
                if (("0").equals(dictItem1.getDictCode())) {
                    compareStatusList1.add(dictItem1);
                } else if (("2").equals(dictItem1.getDictCode())) {
                    compareStatusList1.add(dictItem1);
                } else if (("3").equals(dictItem1.getDictCode())) {
                    compareStatusList1.add(dictItem1);
                }
            }
            view.addObject("compareStatusList", compareStatusList1);

            JSONObject jsonObject = JSON.parseObject(result);
            if (null != jsonObject) {
                logger.info("result=" + result);
                List<AmPersonalInfo> amPersonalInfoList = (List<AmPersonalInfo>) jsonObject.get("amPersonalInfoList");
                List<Map<String, Object>> matchResultList = (List<Map<String, Object>>) jsonObject.get("matchResultList");
                pageInfo = JSON.parseObject(jsonObject.get("pageInfo").toString(), PageInfo.class);
                String matchResultJsonStr = jsonObject.get("matchResult").toString();
                matchResult = JSON.parseObject(matchResultJsonStr, MatchRelativeResultVo.class);
                view.addObject("amPersonalInfoList", amPersonalInfoList);
                view.addObject("matchResultList", matchResultList);
                view.addObject("matchResult", matchResult);
                view.addObject("pageInfo", pageInfo);
            } else {
                logger.info("查看跨区亲缘结果详情 = " + result);
            }
            view.setViewName("strandCase/viewAreaRelationCompareDetail");
        } catch (Exception e) {
            logger.error("查看跨区亲缘比对结果详细报错.", e);
        }

        return view;
    }

    *//**
     * 查看跨区亲缘本地库比对结果详细单
     *
     * @param request
     * @param matchResultVo
     * @param pageInfo
     * @return
     *//*
    @RequestMapping("/querySameAreaRelationCompareCondition")
    public ModelAndView queryAreaRelationCompareCondition(HttpServletRequest request, MatchRelativeResultVo matchResultVo, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        String result = null;
        JSONObject obj = new JSONObject();
        obj.put("page", pageInfo.getPage());
        obj.put("matchResultVo", matchResultVo);
        String afferentJson = obj.toString();
        HttpClient client = HttpClients.createDefault();
        String url = comparisonUrl + "/queryCompareResult/querySameAreaRelationCompareCondition";

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(afferentJson);
            se.setContentType("text/json");
            post.setEntity(se);
            HttpResponse res = client.execute(post);
            result = EntityUtils.toString(res.getEntity());
        } catch (Exception ex) {
            logger.info("查询失败:" + ex);
        }

        JSONObject jsonObject = JSON.parseObject(result);
        matchResultVo = JSON.parseObject(jsonObject.get("matchResultVo").toString(), MatchRelativeResultVo.class);
        modelAndView = JSON.parseObject(jsonObject.get("modelAndView").toString(), ModelAndView.class);

        modelAndView.addObject("matchResultVo", matchResultVo);
        modelAndView.addObject("matchType", "relative");
        modelAndView.setViewName("strandCase/viewAreaCompareCondition");
        return modelAndView;
    }
    */


}
