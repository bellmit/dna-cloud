package com.bazl.dna.lims.core.controller;

import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.po.LimsCaseInfo;
import com.bazl.dna.lims.core.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.core.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.core.service.*;
import com.bazl.dna.lims.core.utils.DateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliu on 2020/4/1.
 */
@Component
public class NewDownloadFileUtils extends BaseController {

    @Autowired
    LimsConfigure limsConfigure;

    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    OrgInfoService orgInfoService;


    /**
     * 在检验案件管理 下载预实验表
     * @param request
     * @param response
     * @param consignmentId
     * @throws ParseException
     */
    public void inspectionpreAceptDoc(HttpServletRequest request, HttpServletResponse response, List<LimsSampleInfoDna> limsSampleInfoDnaList,
                                      String consignmentId) throws ParseException {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignmentInfo consignment = limsConsignmentInfoService.selectByConsignmentId(consignmentId);

        //List<LimsSampleInfoDna> limsSampleInfoDnaList = delegateDataModel.getSampleInfoDnaList();
        AmPersonalInfo personInfo = new AmPersonalInfo();
        if (consignment.getAcceptorId() != null) {
            personInfo = amPersonalInfoService.selectByPersonalId(consignment.getAcceptorId());
        }

        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
        if (caseNo == null || caseNo == "") {
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
        }
        if (personInfo != null) {
            params.put("lims.ame", StringUtils.isBlank(personInfo.getFullName()) ? "" : personInfo.getFullName());
        }
        if (consignment.getAcceptDatetime() != null) {
            params.put("lims.ate", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy-MM-dd"));
        }
        //页眉
        if (consignment != null) {
            if (StringUtils.isNotBlank(consignment.getAcceptOrgId())) {
                if (limsConfigure.getYushiyanControlNo().equals(consignment.getAcceptOrgId())){
                    params.put("orgQNo", limsConfigure.getYushiyanControlNo());
                    params.put("orgQualification", limsConfigure.getLabName());
                }else{
                    params.put("orgQNo", Constants.selectOrgQNo(consignment.getAcceptOrgId()));
                    String orgQualification = orgInfoService.selectLabNameById(consignment.getAcceptOrgId());
                    params.put("orgQualification", StringUtils.isBlank(orgQualification) ? "" : orgQualification);
                }
                //通用版本启用以下代码
                /*params.put("orgQNo", Constants.selectOrgQNo(consignment.getAcceptOrgId()));
                String orgQualification = orgInfoService.selectLabNameById(consignment.getAcceptOrgId());
                params.put("orgQualification", StringUtils.isBlank(orgQualification) ? "" : orgQualification);*/
                //顺义正式版启用以下代码
                /*params.put("orgQNo", this.yushiyanControlNo);
                params.put("orgQualification", this.labName);*/
            }
        }

        boolean bloodFlag = false;
        boolean seminalFlag = false;
        //送检样本
        for (LimsSampleInfoDna sampleInfo : limsSampleInfoDnaList) {
            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
            sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
            sampleInfo.setPreMethod1Result(StringUtils.isBlank(sampleInfo.getPreMethod1Result()) ? "" : sampleInfo.getPreMethod1Result());
            sampleInfo.setPreMethod2Result(StringUtils.isBlank(sampleInfo.getPreMethod2Result()) ? "" : sampleInfo.getPreMethod2Result());
            sampleInfo.setPreMethod3Result(StringUtils.isBlank(sampleInfo.getPreMethod3Result()) ? "" : sampleInfo.getPreMethod3Result());
            sampleInfo.setSampleTypeName(sampleInfo.getSampleTypeName());

            if (sampleInfo.getSampleRemark() != null) {
                sampleInfo.setSampleRemark(StringUtils.isBlank(sampleInfo.getSampleRemark()) ? "" : sampleInfo.getSampleRemark());
            }

        }

        if (bloodFlag) {
            params.put("bloodFlag", "blood");
        } else {
            params.put("bloodFlag", null);
        }

        if (seminalFlag) {
            params.put("seminalFlag", "seminal");
        } else {
            params.put("seminalFlag", null);
        }

        params.put("sampleList", limsSampleInfoDnaList);

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("");
            tmp = cfg.getTemplate("preAccept.ftl", "UTF-8");

            String filename = "-预实验检验记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            //类型
            response.setContentType("application/x-msdownload");
            tmp.process(params, response.getWriter());

        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

}
