package com.bazl.dna.caseinfo.reg.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.reg.common.Constants;
import com.bazl.dna.lims.model.po.DelegateCenterConfig;
import com.bazl.dna.lims.service.DelegateCenterConfigService;
import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.FugitivesInfoVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.service.OrgInfoService;
import com.bazl.dna.lims.service.FugitivesInfoService;
import com.bazl.dna.lims.utils.DateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Created by Sun on 2018/12/20.
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {

    @Autowired
    LimsCaseInfoService caseInfoService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    FugitivesInfoService fugitivesInfoService;//在逃人员信息接口
    @Autowired
    DelegateCenterConfigService delegateCenterConfigService;
    @Autowired
    OrgInfoService  orgInfoService;//机构信息
    /**
     * 类说明：首页数据展示接口
     * Date:2020-07-10
     * Auth:Liuchang
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView view = new ModelAndView();
        view.setViewName("home");

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            view.addObject("date", "redirect:/login.html?timeoutFlag=1");
            return view;
        }

        //查询实验室名称
        String dnaLabName = orgInfoService.selectLabNameById(operateUser.getOrgId());
        view.addObject("dnaLabName",dnaLabName);
        //登录用户的机构信息
        String loginOrgId = operateUser.getOrgId();
        //查询案件总数(根据登录机构ID)
        int caseCnt = caseInfoService.selectCountByOrgCode(loginOrgId);
        view.addObject("caseCnt", caseCnt);
        //查询物证检材总数（根据登录机构ID）
        int evidenceCnt = limsSampleInfoDnaService.selectCountByOrgId(loginOrgId);
        view.addObject("evidenceCnt", evidenceCnt);
        //查询待领取状态案件数(待领取，登录机构ID)
        int cnt3 = caseInfoService.selectCountByCaseStatus(Constants.CASE_STATUS_03,loginOrgId);
        view.addObject("cnt3", cnt3);
        //查询在逃人员总数 (登录机构ID)
        int fugitivesCnt = fugitivesInfoService.selectCountByOrgId(loginOrgId);
        view.addObject("fugitivesCnt",fugitivesCnt);
        //在逃人员查询亲缘人员总数
        int relationPersonCnt = fugitivesInfoService.selectRelationPersonCnt(loginOrgId);
        view.addObject("relationPersonCnt",relationPersonCnt);

        //根据年份获取各个月份的案件数
        //HashMap<String, String> monthMap = caseInfoService.selectMonthCountByYear(DateUtils.getCurrentYear(), operateUser.getOrgId());
        //view.addObject("monthMap", monthMap);

        /*
         * 汇总统计
         * 案件鉴定委托信息各项明细
         * 已送检委托数量，已送检补送委托数量，待送检委托数量,待送检补送委托数量，超时未送检委托数量，已出鉴定书委托数量
         */

        //*已送检委托案件数量（未补送）
        int submissionCaseCnt = caseInfoService.selectCountByCaseStatus(Constants.STATUS_02, loginOrgId);
        view.addObject("submissionCaseCnt",submissionCaseCnt);
        //*已送检委托案件数量（已补送）
        String appendFlag = String.valueOf(Constants.APPEND_FLAG_1);
        int bsSubmissionCaseCnt = caseInfoService.selectCntByCaseStatus(Constants.STATUS_02,appendFlag,loginOrgId);
        view.addObject("bsSubmissionCaseCnt",bsSubmissionCaseCnt);
        //*待送检委托数量(未补送)
        int waitSubmissionCaseCnt = caseInfoService.selectCountByCaseStatus(Constants.STATUS_01, loginOrgId);
        view.addObject("waitSubmissionCaseCnt",waitSubmissionCaseCnt);
        //*待送检补送委托数量(已补送)
        int waitBsCaseCnt = caseInfoService.selectCntByCaseStatus(Constants.STATUS_01,appendFlag,loginOrgId);
        view.addObject("waitBsCaseCnt",waitBsCaseCnt);
        //*查询超时未送检委托案件数量
        //系统当前时间，72小时之前的时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -3);
        date = calendar.getTime();
        //设置系统当前时间 ,如果选择时间大于超时时间，则更改为超时时间
        LimsCaseInfoVo  query = new LimsCaseInfoVo();
        query.setDelegateOrgCode(loginOrgId); //委托机构编码
        query.setDelegateEndDatetime(date);   //结束日期
        query.setStatus(Constants.STATUS_01); //委托状态（未受理）
        //排序条件设置
        query.setOrderByClause("lci.DELEGATE_DATETIME desc");
        int timeOutcaseInfoCnt = caseInfoService.selectVOCount(query);
        view.addObject("timeOutcaseInfoCnt",timeOutcaseInfoCnt);
        //已出鉴定书委托数量（案件信息）
        int outAppraisalBookCnt = caseInfoService.selectCntByAppraisalBook(loginOrgId);
        view.addObject("outAppraisalBookCnt",outAppraisalBookCnt);

        /*
         * 汇总统计
         * 在逃人员鉴定委托各项明细
         * 已送检委托数量，已送检补送委托数量，待送检委托数量,待送检补送委托数量，超时未送检委托数量，已出鉴定书委托数量
         */
         //已送检委托数量（未补送）
         int fugitivesCaseCnt = fugitivesInfoService.selectCountByCaseStatus(Constants.STATUS_02,Constants.APPEND_FLAG_0,loginOrgId);
         view.addObject("fugitivesCaseCnt",fugitivesCaseCnt);
         //已送检委托数量（已补送）
         int bsFugitivesCaseCnt = fugitivesInfoService.selectCountByCaseStatus(Constants.STATUS_02,Constants.APPEND_FLAG_1,loginOrgId);
         view.addObject("bsFugitivesCaseCnt",bsFugitivesCaseCnt);
         //待送检委托数量（未补送）
         int waitFugitivesCaseCnt = fugitivesInfoService.selectCountByCaseStatus(Constants.STATUS_01,Constants.APPEND_FLAG_0,loginOrgId);
         view.addObject("waitFugitivesCaseCnt",waitFugitivesCaseCnt);
         //待送检委托数量(已补送)
         int waitBsFugitivesCnt = fugitivesInfoService.selectCountByCaseStatus(Constants.STATUS_01,Constants.APPEND_FLAG_1,loginOrgId);
         view.addObject("waitBsFugitivesCnt",waitBsFugitivesCnt);
         //超时未送检委托数量（在逃人员）
         FugitivesInfoVo queryVo =  new FugitivesInfoVo();
         queryVo.setDelegateOrgCode(loginOrgId);
         queryVo.setDelegateEndDatetime(date);
         int timeOutFugitivesCnt = fugitivesInfoService.selectVOCnt(queryVo);
         view.addObject("timeOutFugitivesCnt",timeOutFugitivesCnt);
         //已出鉴定书委托数量(在逃人员)
         int selectCntByAppraisalBook = fugitivesInfoService.selectCntByAppraisalBook(loginOrgId);
         view.addObject("selectCntByAppraisalBook",selectCntByAppraisalBook);

        String orgId = operateUser.getOrgId();
        String substring = orgId.substring(0, 6);
        List<DelegateCenterConfig> delegateCenterConfigs = delegateCenterConfigService.selectQualification(substring);
        view.addObject("orgInfos",delegateCenterConfigs);

        //获取所有在逃人员
        List<FugitivesInfo> fugitivesInfoList = fugitivesInfoService.selectAll();
        view.addObject("fugitivesInfoList", fugitivesInfoList);
        return view;
    }

    /**
     * 根据年份查询统计数据
     *
     * @param year
     * @return
     */
    @RequestMapping("/getCount")
    @ResponseBody
    public Map<String, Object> getCount(String year) {
        Map<String, Object> map = new HashMap<>();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            map.put("date", "redirect:/login.html?timeoutFlag=1");
            return map;
        }

        try {
            //根据年份获取各个月份的案件数
            Map<String, Object> monthMap = caseInfoService.selectMonthCountByYear(year, operateUser.getOrgId());
            map.put("monthMap", monthMap);
            map.put("code", 0);
            map.put("message", "获取成功！");
        } catch (Exception ex) {
            logger.info("获取失败" + ex);
            map.put("code", 1);
            map.put("message", "获取失败！");
        }
        return map;
    }

    @RequestMapping("/receiveWord")
    public void circulationRecord(HttpServletRequest request, HttpServletResponse response,
                                  String caseNo, String caseName, String orgName, String delegatorName, String delegatorNo) throws ParseException {

        Map<String, Object> params = new HashMap<String, Object>();
        /*if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                int length = caseNo.split("-").length;
                if (length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }*/

        params.put("caseNo", StringUtils.isBlank(caseNo) ? "" : caseNo);
        params.put("caseName", StringUtils.isBlank(caseName) ? "" : caseName);
        params.put("orgName", StringUtils.isBlank(orgName) ? "" : orgName);
        params.put("delegatorName", StringUtils.isBlank(delegatorName) ? "" : delegatorName);
        params.put("delegatorNo", StringUtils.isBlank(delegatorNo) ? "" : delegatorNo);


        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("鉴定书领取单.ftl", "UTF-8");

            String filename = "-鉴定书领取单" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + caseNo + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            //类型
            response.setContentType("application/x-msdownload");
            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("下载错误", ex);
        } finally {
            try {
                response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
