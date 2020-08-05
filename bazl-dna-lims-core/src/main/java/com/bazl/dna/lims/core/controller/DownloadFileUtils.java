package com.bazl.dna.lims.core.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.core.LimsConfigure;
import com.bazl.dna.lims.core.client.IFileServiceClient;
import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.compare.GeneMixCompareUtil;
import com.bazl.dna.lims.core.compare.GeneRelativeCompareUtil;
import com.bazl.dna.lims.core.compare.GeneSameCompareUtil;
import com.bazl.dna.lims.core.compare.relative.po.ParentageMatchResult;
import com.bazl.dna.lims.core.compare.relative.po.ParentageMatchResultRecord;
import com.bazl.dna.lims.core.datamodel.CaseCompareResultGroup;
import com.bazl.dna.lims.core.datamodel.SampleGeneDataModel;
import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.po.CompareRelativeResult;
import com.bazl.dna.lims.core.model.po.CompareSameResult;
import com.bazl.dna.lims.core.model.po.DictItem;
import com.bazl.dna.lims.core.model.po.ErsFile;
import com.bazl.dna.lims.core.model.po.Examiner;
import com.bazl.dna.lims.core.model.po.ExperimentalParameter;
import com.bazl.dna.lims.core.model.po.ExtractPlate;
import com.bazl.dna.lims.core.model.po.LabExtractSample;
import com.bazl.dna.lims.core.model.po.LabPcrInfo;
import com.bazl.dna.lims.core.model.po.LabPcrSample;
import com.bazl.dna.lims.core.model.po.LimsCaseInfo;
import com.bazl.dna.lims.core.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.core.model.po.LimsPerosnRelation;
import com.bazl.dna.lims.core.model.po.LimsPersonInfo;
import com.bazl.dna.lims.core.model.po.LimsSampleGene;
import com.bazl.dna.lims.core.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.model.po.MatchAuditedGene;
import com.bazl.dna.lims.core.model.po.MatchSameResult;
import com.bazl.dna.lims.core.model.po.OrgInfo;
import com.bazl.dna.lims.core.model.po.SampleInfo;
import com.bazl.dna.lims.core.model.po.SampleInfoModel;
import com.bazl.dna.lims.core.model.po.SampleTable;
import com.bazl.dna.lims.core.model.vo.SampleInfoVo;
import com.bazl.dna.lims.core.service.AmPersonalInfoService;
import com.bazl.dna.lims.core.service.CompareRelativeResultService;
import com.bazl.dna.lims.core.service.CompareSameResultService;
import com.bazl.dna.lims.core.service.ErsFileService;
import com.bazl.dna.lims.core.service.ExaminerService;
import com.bazl.dna.lims.core.service.ExperimentalParameterService;
import com.bazl.dna.lims.core.service.FileArchivesInfoService;
import com.bazl.dna.lims.core.service.LabExtractSampleService;
import com.bazl.dna.lims.core.service.LabPcrInfoService;
import com.bazl.dna.lims.core.service.LabPcrSampleService;
import com.bazl.dna.lims.core.service.LimsCaseInfoService;
import com.bazl.dna.lims.core.service.LimsConsignmentInfoService;
import com.bazl.dna.lims.core.service.LimsPerosnRelationService;
import com.bazl.dna.lims.core.service.LimsPersonInfoService;
import com.bazl.dna.lims.core.service.LimsSampleGeneService;
import com.bazl.dna.lims.core.service.LimsSampleInfoDnaService;
import com.bazl.dna.lims.core.service.MatchAuditedGeneService;
import com.bazl.dna.lims.core.service.MatchRelativeResultService;
import com.bazl.dna.lims.core.service.MatchSameResultService;
import com.bazl.dna.lims.core.service.OrgInfoService;
import com.bazl.dna.lims.core.service.PersonDetailService;
import com.bazl.dna.lims.core.utils.BarcodeUtil;
import com.bazl.dna.lims.core.utils.CommonUtils;
import com.bazl.dna.lims.core.utils.DataFormat;
import com.bazl.dna.lims.core.utils.DateUtils;
import com.bazl.dna.lims.core.utils.DictUtil;
import com.bazl.dna.lims.core.utils.FileUtil;
import com.bazl.dna.lims.core.utils.GeneratePathUtil;
import com.bazl.dna.lims.core.utils.ListUtils;
import com.bazl.dna.lims.core.utils.SystemUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author wanghaiyang
 * @date 2019/4/18.
 */
@Component
public class DownloadFileUtils extends BaseController {

    @Autowired
    LimsConfigure limsConfigure;

    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    PersonDetailService personDetailService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleInfoDnaService limsSampleInfoDnaService;
    @Autowired
    LimsPerosnRelationService limsPerosnRelationService;
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    OrgInfoService orgInfoService;
    @Autowired
    FileArchivesInfoService fileArchivesInfoService;
    @Autowired
    LabPcrSampleService labPcrSampleService;
    @Autowired
    LabPcrInfoService labPcrInfoService;
    @Autowired
    MatchAuditedGeneService matchAuditedGeneService;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    CompareSameResultService compareSameResultService;
    @Autowired
    CompareRelativeResultService compareRelativeResultService;
    @Autowired
    ExaminerService examinerService;
    @Autowired
    MatchSameResultService matchSameResultService;
    @Autowired
    MatchRelativeResultService matchRelativeResultService;
    @Autowired
    ExperimentalParameterService experimentalParameterService;
    @Autowired
    LabExtractSampleService labExtractSampleService;
    @Autowired
    GeneRelativeCompareUtil geneRelativeCompareUtil;
    @Autowired
    IFileServiceClient fastDFSClient;
    @Autowired
    ErsFileService ersFileService;
/*

    @Autowired
    MobileNewsMapper mobileNewsMapper;

    @Autowired
    LogRecordMapper logRecordMapper;
*/


    /**
     * 下载预检验表
     *
     * @param request
     * @param response
     * @param consignmentId
     * @throws ParseException
     */
    public void preAceptDoc(HttpServletRequest request, HttpServletResponse response,
                            String consignmentId) throws ParseException {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignmentInfo consignment = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectListByConsignmentId(consignmentId);
        AmPersonalInfo acceptPersonInfo = new AmPersonalInfo();
        if (consignment.getAcceptorId() != null) {
            acceptPersonInfo = amPersonalInfoService.selectByPersonalId(consignment.getAcceptorId());
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
        if (acceptPersonInfo != null) {
            params.put("acceptName", StringUtils.isBlank(acceptPersonInfo.getFullName()) ? "" : acceptPersonInfo.getFullName());
        }
        if (consignment.getAcceptDatetime() != null) {
            params.put("acceptDate", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy-MM-dd"));
        }
        //页眉
        if (consignment != null) {
            if (StringUtils.isNotBlank(consignment.getAcceptOrgId())) {
                if (limsConfigure.getYushiyanControlNo().equals(consignment.getAcceptOrgId())) {
                    params.put("orgQNo", limsConfigure.getYushiyanControlNo());
                    params.put("orgQualification", limsConfigure.getLabName());
                } else {
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

            DictItem dictsampleType = new DictItem();
            dictsampleType.setDictTypeCode(Constants.SAMPLE_TYPE);
            List<DictItem> sampleTypeList = DictUtil.getDictList(dictsampleType);
            for (DictItem sampleType : sampleTypeList) {
                if (sampleInfo.getSampleType().equals(sampleType.getDictCode())) {
                    sampleInfo.setSampleTypeName(sampleType.getDictName());
                }
            }
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
//            if (0 == limsConfigure.getLimsEdition()) {
//                tmp = cfg.getTemplate("preAccept.ftl", "UTF-8");
//            } else {
            tmp = cfg.getTemplate("preAccept.ftl", "UTF-8");
//            }


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

    /**
     * 在检验案件管理 下载预实验表
     *
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
        AmPersonalInfo acceptPersonInfo = new AmPersonalInfo();
        if (consignment.getAcceptorId() != null) {
            acceptPersonInfo = amPersonalInfoService.selectByPersonalId(consignment.getAcceptorId());
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
        if (acceptPersonInfo != null) {
            params.put("acceptName", StringUtils.isBlank(acceptPersonInfo.getFullName()) ? "" : acceptPersonInfo.getFullName());
        }
        if (consignment.getAcceptDatetime() != null) {
            params.put("acceptDate", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy-MM-dd"));
        }
        //页眉
        if (consignment != null) {
            if (StringUtils.isNotBlank(consignment.getAcceptOrgId())) {
                if (limsConfigure.getYushiyanControlNo().equals(consignment.getAcceptOrgId())) {
                    params.put("orgQNo", limsConfigure.getYushiyanControlNo());
                    params.put("orgQualification", limsConfigure.getLabName());
                } else {
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
//            if (0 == limsEdition) {
//                tmp = cfg.getTemplate("preAccept.ftl", "UTF-8");
//            } else {
            tmp = cfg.getTemplate("preAccept.ftl", "UTF-8");
//            }


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


    /**
     * 下载样本流转记录表
     *
     * @param request
     * @param response
     * @param consignmentId
     * @throws ParseException
     */
    public void circulationRecord(HttpServletRequest request, HttpServletResponse response,
                                  String consignmentId) throws ParseException {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignmentInfo consignment = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectSampleListByConsignmentId(consignmentId);


        DictItem identificationDictItem = new DictItem();
        identificationDictItem.setDictTypeCode(Constants.IDENTIFICATION_TYPE);
        List<DictItem> identificationList = DictUtil.getDictList(identificationDictItem);

        for (DictItem identification : identificationList) {
            if (identification.getDictCode().equals(consignment.getIdentifyRequirement())) {
                consignment.setIdentifyRequirement(identification.getDictName());
            }
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
        if (caseInfo.getCaseNo() != null) {
            params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        }
        params.put("consignmentNo", StringUtils.isBlank(consignment.getConsignmentNo()) ? "" : consignment.getConsignmentNo());
        //页眉
        if (consignment != null) {
            if (StringUtils.isNotBlank(consignment.getAcceptOrgId())) {
                if (limsConfigure.getYushiyanControlNo().equals(consignment.getAcceptOrgId())) {
                    params.put("orgQNo", limsConfigure.getYushiyanControlNo());
                    params.put("orgQualification", limsConfigure.getLabName());
                } else {
                    params.put("orgQNo", Constants.selectOrgQNo(consignment.getAcceptOrgId()));
                    String orgQualification = orgInfoService.selectLabNameById(consignment.getAcceptOrgId());
                    params.put("orgQualification", StringUtils.isBlank(orgQualification) ? "" : orgQualification);
                }
                /*params.put("orgQNo", Constants.selectOrgQNo(consignment.getAcceptOrgId()));
                String orgQualification = orgInfoService.selectLabNameById(consignment.getAcceptOrgId());
                params.put("orgQualification", StringUtils.isBlank(orgQualification) ? "" : orgQualification);*/
            }
        }

        //送检样本
        for (LimsSampleInfoDna sampleInfo : limsSampleInfoDnaList) {
            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
            sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
            sampleInfo.setSampleDesc(StringUtils.isBlank(sampleInfo.getSampleDesc()) ? "" : sampleInfo.getSampleDesc());

            DictItem dictsamplePackling = new DictItem();
            dictsamplePackling.setDictTypeCode(Constants.PACK_METHOD);
            List<DictItem> samplePacklingList = DictUtil.getDictList(dictsamplePackling);
            for (DictItem samplePackling : samplePacklingList) {
                if (sampleInfo.getSamplePacking().equals(samplePackling.getDictCode())) {
                    sampleInfo.setSamplePackingName(samplePackling.getDictName());
                }
            }
            sampleInfo.setSamplePackingName(StringUtils.isBlank(sampleInfo.getSamplePackingName()) ? "" : sampleInfo.getSamplePackingName());
            DictItem dictsampleType = new DictItem();
            dictsampleType.setDictTypeCode(Constants.SAMPLE_TYPE);
            List<DictItem> sampleTypeList = DictUtil.getDictList(dictsampleType);
            for (DictItem sampleType : sampleTypeList) {
                if (sampleInfo.getSampleType().equals(sampleType.getDictCode())) {
                    sampleInfo.setSampleTypeName(sampleType.getDictName());
                }
            }

            if (sampleInfo.getExtractDatetime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String extracTime = sdf.format(sampleInfo.getExtractDatetime());
                sampleInfo.setExtractTime(extracTime);
            }

            sampleInfo.setSampleStatus("已受理");
            if (sampleInfo.getSampleRemark() != null) {
                sampleInfo.setSampleRemark(StringUtils.isBlank(sampleInfo.getSampleRemark()) ? "" : sampleInfo.getSampleRemark());
            }
        }

        params.put("sampleList", limsSampleInfoDnaList);

        params.put("currentDay", DateUtils.dateToString(new Date(), "yyyy-MM-dd"));

        if (StringUtils.isNotEmpty(consignment.getAutographPicture())) {
            params.put("image", consignment.getAutographPicture().replace("data:image/png;base64,", ""));
        }

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("circulationRecord.ftl", "UTF-8");

            String filename = "-样本检材流转表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));
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


    /**
     * 下载鉴定事项确认书
     *
     * @param request
     * @param response
     * @param consignmentId
     */
    public void generateAndDownload(HttpServletRequest request, HttpServletResponse response,
                                    String consignmentId) throws ParseException {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignmentInfo consignment = limsConsignmentInfoService.selectByConsignmentId(consignmentId);
        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectListByConsignmentId(consignmentId);
        AmPersonalInfo amPersonalInfo1 = amPersonalInfoService.selectByPersonalId(consignment.getDelegator1Id());
        AmPersonalInfo amPersonalInfo2 = amPersonalInfoService.selectByPersonalId(consignment.getDelegator2Id());

        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(consignment.getAreaOrgCode());

        AmPersonalInfo acceptPersonInfo = new AmPersonalInfo();
        if (consignment.getAcceptorId() != null) {
            acceptPersonInfo = amPersonalInfoService.selectByPersonalId(consignment.getAcceptorId());
        }
        DictItem posItionDictItem = new DictItem();
        posItionDictItem.setDictTypeCode(Constants.POSITION_LIST);
        List<DictItem> posItionList = DictUtil.getDictList(posItionDictItem);

        for (DictItem posItion : posItionList) {
            if (amPersonalInfo1 != null && posItion.getDictCode().equals(amPersonalInfo1.getPosition())) {
                consignment.setDelegator1Position(posItion.getDictName());
            }
            if (amPersonalInfo2 != null && posItion.getDictCode().equals(amPersonalInfo2.getPosition())) {
                consignment.setDelegator2Position(posItion.getDictName());
            }
        }

        DictItem dictItem = new DictItem();
        dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
        List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);

        for (DictItem caseProperty : casePropertyList) {
            if (caseProperty.getDictCode().equals(caseInfo.getCaseProperty())) {
                caseInfo.setCaseProperty(caseProperty.getDictName());
            }
        }

        String appendFlag = StringUtils.isBlank(consignment.getAppendFlag()) ? "" : consignment.getAppendFlag();

        if (Constants.IDENTIFYREQUIREMENT_01.equals(consignment.getIdentifyRequirement())) {
            consignment.setIdentifyRequirement("同一鉴定");
        } else if (Constants.IDENTIFYREQUIREMENT_02.equals(consignment.getIdentifyRequirement())) {
            consignment.setIdentifyRequirement("亲缘鉴定");
        } else if (Constants.IDENTIFYREQUIREMENT_03.equals(consignment.getIdentifyRequirement())) {
            consignment.setIdentifyRequirement("同一鉴定，亲缘鉴定");
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
        if (caseInfo.getCaseNo() != null) {
            params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        }
        if (StringUtils.isNotEmpty(consignment.getAutographPicture())) {
            params.put("image", consignment.getAutographPicture().replace("data:image/png;base64,", ""));
        } else {
            params.put("image", "");
            params.put("nullImage", "____________________________");
        }
        params.put("consignmentNo", StringUtils.isBlank(consignment.getConsignmentNo()) ? "" : consignment.getConsignmentNo());
        params.put("caseXkNo", StringUtils.isBlank(caseInfo.getCaseXkNo()) ? "" : caseInfo.getCaseXkNo());
        params.put("acceptor", StringUtils.isBlank(consignment.getRefusePerson()) ? "" : consignment.getRefusePerson());
        //委托单位
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgName()) ? "" : consignment.getDelegateOrgName());
        if (orgInfo != null) {
            params.put("areaOrgName", StringUtils.isBlank(orgInfo.getOrgName()) ? "" : orgInfo.getOrgName());

        } else {
            params.put("areaOrgName", "");
        }
//        params.put("areaOrgName", StringUtils.isBlank(orgInfo.getOrgName()) ? "" : orgInfo.getOrgName());
        //受理时间
        if (consignment.getAcceptDatetime() != null) {
            params.put("acceptDate", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy年MM月dd日"));
            params.put("acceptDate2", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy年MM月dd日 HH:mm"));
        }
        //受理人
        if (acceptPersonInfo != null) {
            params.put("acceptPerson", StringUtils.isBlank(acceptPersonInfo.getFullName()) ? "" : acceptPersonInfo.getFullName());
            params.put("acceptPersonPhone", StringUtils.isBlank(acceptPersonInfo.getPhoneNumber()) ? "" : acceptPersonInfo.getPhoneNumber());
        }
        //鉴定要求
        params.put("identifyRequirement", StringUtils.isBlank(consignment.getIdentifyRequirement()) ? "" : consignment.getIdentifyRequirement());
        //委托人1
        params.put("delegator1name", StringUtils.isBlank(consignment.getDelegator1Name()) ? "" : consignment.getDelegator1Name());
        params.put("delegator1Position", StringUtils.isBlank(consignment.getDelegator1Position()) ? "" : consignment.getDelegator1Position());
        params.put("delegator1Cno", StringUtils.isBlank(consignment.getDelegator1PaperworkNo()) ? "" : consignment.getDelegator1PaperworkNo());
        params.put("delegator1Phone", StringUtils.isBlank(consignment.getDelegator1PhoneNumber()) ? "" : consignment.getDelegator1PhoneNumber());
        //委托人2
        params.put("delegator2name", StringUtils.isBlank(consignment.getDelegator2Name()) ? "" : consignment.getDelegator2Name());
        params.put("delegator2Position", StringUtils.isBlank(consignment.getDelegator2Position()) ? "" : consignment.getDelegator2Position());
        params.put("delegator2Cno", StringUtils.isBlank(consignment.getDelegator2PaperworkNo()) ? "" : consignment.getDelegator2PaperworkNo());
        params.put("delegator2Phone", StringUtils.isBlank(consignment.getDelegator2PhoneNumber()) ? "" : consignment.getDelegator2PhoneNumber());
        //案件名称
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        //案件号（K号）
        params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        //简要案情
        params.put("caseBrief", StringUtils.isBlank(caseInfo.getCaseBrief()) ? "" : caseInfo.getCaseBrief());
        //案件性质
        params.put("caseProperty", StringUtils.isBlank(caseInfo.getCaseProperty()) ? "" : caseInfo.getCaseProperty());

        boolean bloodFlag = false;
        boolean seminalFlag = false;
        //送检样本
        if (limsSampleInfoDnaList.size() > 0) {
            for (LimsSampleInfoDna sampleInfo : limsSampleInfoDnaList) {
                if (sampleInfo != null) {
                    sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
                    sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
                    sampleInfo.setSampleDesc(StringUtils.isBlank(sampleInfo.getSampleDesc()) ? "" : sampleInfo.getSampleDesc());

                    DictItem dictsamplePackling = new DictItem();
                    dictsamplePackling.setDictTypeCode(Constants.PACK_METHOD);
                    List<DictItem> samplePacklingList = DictUtil.getDictList(dictsamplePackling);
                    for (DictItem samplePackling : samplePacklingList) {
                        if (StringUtils.isNotBlank(sampleInfo.getSamplePacking())) {
                            if (sampleInfo.getSamplePacking().equals(samplePackling.getDictCode())) {
                                sampleInfo.setSamplePackingName(samplePackling.getDictName());
                            }
                        }
                    }
                    sampleInfo.setSamplePackingName(StringUtils.isBlank(sampleInfo.getSamplePackingName()) ? "" : sampleInfo.getSamplePackingName());
                    DictItem dictsampleType = new DictItem();
                    dictsampleType.setDictTypeCode(Constants.SAMPLE_TYPE);
                    List<DictItem> sampleTypeList = DictUtil.getDictList(dictsampleType);
                    for (DictItem sampleType : sampleTypeList) {
                        if (StringUtils.isNotBlank(sampleInfo.getSampleType())) {
                            if (sampleInfo.getSampleType().equals(sampleType.getDictCode())) {
                                sampleInfo.setSampleTypeName(sampleType.getDictName());
                            }
                        }
                    }
                    sampleInfo.setSampleTypeName(StringUtils.isBlank(sampleInfo.getSampleTypeName()) ? "" : sampleInfo.getSampleTypeName());
                    if (sampleInfo.getExtractDatetime() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String extracTime = sdf.format(sampleInfo.getExtractDatetime());
                        sampleInfo.setExtractTime(extracTime);
                    }
                    sampleInfo.setSampleStatus("已受理");
                    if (sampleInfo.getSampleRemark() != null) {
                        sampleInfo.setSampleRemark(StringUtils.isBlank(sampleInfo.getSampleRemark()) ? "" : sampleInfo.getSampleRemark());
                    }
                }
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

        List<List<LimsSampleInfoDna>> limsSampleInfoDataList = splitList(limsSampleInfoDnaList, 19);
        for (List<LimsSampleInfoDna> limsSampleInfoDnas : limsSampleInfoDataList) {
            for (LimsSampleInfoDna limsSampleInfoDna : limsSampleInfoDnas) {
                if (StringUtils.isNotBlank(limsSampleInfoDna.getSampleFlag())) {
                    if (limsSampleInfoDna.getSampleFlag().equals("1")) {
                        LimsPerosnRelation limsPerosnRelation = limsPerosnRelationService.selectPersonInfo(limsSampleInfoDna.getLinkId());
                        LimsPersonInfo limsPersonInfo = limsPersonInfoService.selectPersonInfoById(limsSampleInfoDna.getLinkId());
                        if (limsPerosnRelation != null && limsPersonInfo != null) {
                            if (StringUtils.isNotBlank(limsPerosnRelation.getRelationType())) {
                                if (limsPerosnRelation.getRelationType().equals("01")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "父亲");
                                } else if (limsPerosnRelation.getRelationType().equals("02")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "母亲");
                                } else if (limsPerosnRelation.getRelationType().equals("03")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "丈夫");
                                } else if (limsPerosnRelation.getRelationType().equals("04")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "妻子");
                                } else if (limsPerosnRelation.getRelationType().equals("05")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "儿子");
                                } else if (limsPerosnRelation.getRelationType().equals("06")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "女儿");
                                } else if (limsPerosnRelation.getRelationType().equals("07")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "兄");
                                } else if (limsPerosnRelation.getRelationType().equals("08")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "弟");
                                } else if (limsPerosnRelation.getRelationType().equals("09")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "姐");
                                } else if (limsPerosnRelation.getRelationType().equals("10")) {
                                    limsSampleInfoDna.setRelationShip(limsPersonInfo.getPersonName() + "妹");
                                }
                            }

                        }

                    }
                }

                if (StringUtils.isNotBlank(limsSampleInfoDna.getCoreTakenStats())) {
                    if (limsSampleInfoDna.getCoreTakenStats().equals("0")) {
                        limsSampleInfoDna.setCoreTakenStats("否");
                    } else {
                        limsSampleInfoDna.setCoreTakenStats("是");
                    }
                }
            }
        }
        params.put("limsSampleInfoDataList", limsSampleInfoDataList);


        if (StringUtils.isBlank(caseInfo.getXkANo())) {
            params.put("xkANo", "                    ");
        } else {
            params.put("xkANo", StringUtils.isBlank(caseInfo.getXkANo()) ? "" : caseInfo.getXkANo());
        }

        if (consignment.getAcceptDatetime() != null) {
            params.put("currentDay", StringUtils.isBlank(DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy-MM-dd")) ? "" : DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy-MM-dd"));
        } else {
            params.put("currentDay", "");
        }

        if (StringUtils.isBlank(consignment.getTakePerson())) {
            params.put("takePerson", "____________________________");
        } else {
            params.put("takePerson", consignment.getTakePerson());
        }

        /**
         通用版本启用这段代码
         String orgQualification = orgInfoService.selectLabNameById(consignment.getAcceptOrgId());
         params.put("orgQualification", StringUtils.isBlank(orgQualification) ? "" : orgQualification);
         params.put("orgQNo", Constants.selectOrgQNo(consignment.getAcceptOrgId()));
         **/
        //顺义正式版启用下面这段代码
        String orgQNo = limsConfigure.getQuerenshuControlNo();
        if (StringUtils.isNotBlank(appendFlag) && appendFlag.equals("1")) {
            orgQNo = limsConfigure.getBusongQuerenshuControlNo();
        }
        OrgInfo orgInfo1 = orgInfoService.selectByPrimaryKey(consignment.getAcceptOrgId());
        params.put("orgQNo", orgQNo);
        params.put("orgQualification", orgInfo1.getOrgQualification());

//        if (this.yushiyanControlNo.equals(consignment.getAcceptOrgId())){
//            params.put("orgQNo", this.yushiyanControlNo);
//            params.put("orgQualification", this.labName);
//        }else{
//            params.put("orgQNo", Constants.selectOrgQNo(consignment.getAcceptOrgId()));
//            String orgQualification = orgInfoService.selectLabNameById(consignment.getAcceptOrgId());
//            params.put("orgQualification", StringUtils.isBlank(orgQualification) ? "" : orgQualification);
//        }


        params.put("orgCode", StringUtils.isBlank(consignment.getAcceptOrgId()) ? "" : consignment.getAcceptOrgId());
        params.put("barcode1", BarcodeUtil.generateFile(StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : StringUtils.substringBefore(caseInfo.getCaseNo(), "-"), "barcode1.png"));
        params.put("barcode2", BarcodeUtil.generateFile(StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : StringUtils.substringAfter(caseInfo.getCaseNo(), "-"), "barcode2.png"));

//        if(StringUtils.isNotBlank(caseInfo.getCaseNo())){
//            Pattern p = Pattern.compile("[\u4e00-\u9fa5]"); // 正则表达式 判断是否包含汉字
//            Matcher m = p.matcher(caseInfo.getCaseNo()); // 操作的字符串
//            boolean b = m.matches();
//            if(b){
//                params.put("barcode1","");
//            }else{
//                params.put("barcode1",BarcodeUtil.generateFile(caseInfo.getCaseNo(),"barcode1.png"));
//            }
//        }else{
//            params.put("barcode1",BarcodeUtil.generateFile(StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo(), "barcode1.png"));
//        }
//        if(StringUtils.isNotBlank(consignment.getConsignmentNo())){
//            Pattern p = Pattern.compile("[\u4e00-\u9fa5]"); // 正则表达式 判断是否包含汉字
//            Matcher m = p.matcher(consignment.getConsignmentNo()); // 操作的字符串
//            boolean b = m.matches();
//            if(b){
//                params.put("barcode2","");
//            }else{
//                params.put("barcode2",BarcodeUtil.generateFile(consignment.getConsignmentNo(),"barcode2.png"));
//            }
//        }else{
//            params.put("barcode2",BarcodeUtil.generateFile(StringUtils.isBlank(consignment.getConsignmentNo()) ? "" : consignment.getConsignmentNo(), "barcode2.png"));
//        }

        params.put("appendFlag", appendFlag);
        if (caseInfo.getFirstChecker() != null) {
            AmPersonalInfo acceptPersonInfo12 = amPersonalInfoService.selectByPersonalId(caseInfo.getFirstChecker());
            if (acceptPersonInfo12 != null) {
                params.put("firstChecker", StringUtils.isBlank(acceptPersonInfo12.getFullName()) ? "" : acceptPersonInfo12.getFullName());
            } else {
                params.put("firstChecker", "");
            }


        }
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板

            String ftlName = "acceptInfo.ftl";

            if (StringUtils.isNotBlank(appendFlag) && appendFlag.equals("1")) {
                //params.put("orgQNo", Constants.selectRepOrgQNo(consignment.getAcceptOrgId()));
                ftlName = "acceptInfoR.ftl";
            }
            Template tmp = cfg.getTemplate(ftlName, "UTF-8");

            String filename = "-鉴定事项确认书" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";
            if (StringUtils.isNotBlank(appendFlag) && appendFlag.equals("1")) {
                filename = "-补送鉴定事项确认书" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";
            }
            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            //类型
            response.setContentType("application/x-msdownload");
            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("下载错误！", ex);
        }
    }

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    public static List<List<LimsSampleInfoDna>> splitList(List<LimsSampleInfoDna> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        if (list.size() < 19) {
            int num = 19 - list.size();
            LimsSampleInfoDna tmpSample = null;
            for (int i = 0; i < num; i++) {
                tmpSample = new LimsSampleInfoDna();
                tmpSample.setSampleNo("");
                tmpSample.setSampleName("");
                tmpSample.setSampleDesc("");
                tmpSample.setSampleTypeName("");
                tmpSample.setSamplePackingName("");
                tmpSample.setExtractTime("");
                tmpSample.setSampleStatus("");
                tmpSample.setSampleRemark("");
                list.add(tmpSample);
            }

        }

        List<List<LimsSampleInfoDna>> result = new ArrayList<List<LimsSampleInfoDna>>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<LimsSampleInfoDna> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    /**
     * 生成鉴定书
     *
     * @param request
     * @param caseId
     * @return
     */
    public Map<String, Object> generateIdentifyBook(HttpServletRequest request, String caseId, String tandemStatus) {
        Map<String, Object> result = new HashMap<>();

        //根据案件id查询案件信息
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        String groupId = null;
        Map<String, Object> params = getDataParameters(limsCaseInfo, request, tandemStatus, groupId);

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("sameCompareIdentifyBook.ftl", "UTF-8");

            String filename = (StringUtils.isBlank(limsCaseInfo.getCaseNo()) ? "" : limsCaseInfo.getCaseNo())
                    + "_" + (StringUtils.isBlank(limsCaseInfo.getCaseName()) ? "" : limsCaseInfo.getCaseName()) + "_"
                    + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(GeneratePathUtil.getGeneratePath(limsCaseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            //文件路径
            String filePath = GeneratePathUtil.getGeneratePath(limsCaseInfo, filename);
            ResponseData responseData = fastDFSClient.upload(new File(filePath));
            String dfsFilePath = (String)responseData.getResult();
            //保存文件路径
            saveErsFile(caseId, null, dfsFilePath, Constants.FILE_CATEGORY_11);
            result.put("orgInfo", (OrgInfo)params.get("orgInfo"));
            result.put("parentOrgInfo", (OrgInfo)params.get("parentOrgInfo"));
            result.put("caseInfo", (LimsCaseInfo)params.get("limsCaseInfo"));
            result.put("consignmentInfo", (LimsConsignmentInfo)params.get("limsConsignmentInfo"));
            result.put("byteFile", FileUtil.getBytesFromFile(new File(filePath)));
            result.put("filePath", dfsFilePath);
            result.put("success", true);
        } catch (Exception e) {
            logger.info("生成失败:" + e);
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 生成入库单
     *
     * @param request
     * @param caseId
     * @return
     */
    public void generateInboundOrder(HttpServletRequest request, HttpServletResponse response, String caseId) {
        Map<String, Object> params = new HashMap<>();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        String userOrgId = operateUser.getOrgId();
        //将当前用户org_id设置进query
        if (StringUtils.isNotBlank(userOrgId) && userOrgId.contains("110230")) {
            userOrgId = "110230000000";
        }

        //页眉
        ExperimentalParameter exp = new ExperimentalParameter();
        exp.setOrgId(userOrgId);
        exp.setExperimentalType("IN_STORAGE");//常规提取
        List<ExperimentalParameter> experimentalParameters = experimentalParameterService.selectList(exp);
        if (ListUtils.isNotNullAndEmptyList(experimentalParameters)) {
            ExperimentalParameter experimentalParameter = experimentalParameters.get(0);
            String orgNameSp = experimentalParameter.getParameterName();
            params.put("orgNameSp", StringUtils.isBlank(orgNameSp) ? "" : orgNameSp);
        }

        params.put("warehouseReceiptNo", StringUtils.isBlank(limsConfigure.getWarehouseReceiptNo()) ? "" : limsConfigure.getWarehouseReceiptNo());


        //查询当前人员信息
        AmPersonalInfo amPersonalInfo = amPersonalInfoService.queryAmPersonalInfo(operateUser.getPersonalId());

        params.put("entryName", amPersonalInfo.getFullName());

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectByCaseId(caseId);
        params.put("limsCaseInfo", limsCaseInfo);
        if (limsCaseInfo != null && StringUtils.isNotBlank(limsCaseInfo.getGjkSYSNo())) {
            try {
                limsCaseInfo.getGjkSYSNo().equals(null);
            } catch (Exception ex) {
                logger.error("案件未入库！", ex);
                return;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String instoreDate = null;

        if (null != limsCaseInfo.getInstoredDatetime()) {
            logger.info("案件入库时间=" + limsCaseInfo.getInstoredDatetime());
            instoreDate = sdf.format(limsCaseInfo.getInstoredDatetime());
        }

        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectListByCaseIdInstored(caseId);
        List<Map<String, Object>> newSampleInfoList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(limsSampleInfoDnaList)) {
            LimsSampleInfoDna sampleInfoDna = limsSampleInfoDnaList.get(0);
            if (sampleInfoDna != null && sampleInfoDna.getInstoredDatetime() != null && instoreDate == null) {
                instoreDate = sdf.format(sampleInfoDna.getInstoredDatetime());
            }

            List<LimsSampleInfoDna> newSampleList = new ArrayList<>();
            //现场物证
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_01);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "现场物证");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //混合物证
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_02);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "混合物证");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //违法犯罪人员
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_03);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "违法犯罪人员");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //嫌疑人
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_04);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "嫌疑人");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //受害人
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_05);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "受害人");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //失踪人口
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_06);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "失踪人口");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //无名尸
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_07);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "无名尸");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //嫌疑人亲属
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_08);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "嫌疑人亲属");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //受害人亲属
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_09);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "受害人亲属");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //失踪人口亲属
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_10);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "失踪人口亲属");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //基础库
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_11);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "基础库");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
            //YSTR
            newSampleList = getListByInstoredType(limsSampleInfoDnaList, Constants.INSTORED_TYPE_12);
            if (ListUtils.isNotNullAndEmptyList(newSampleList)) {
                Map<String, Object> resultMap = new LinkedHashMap<>();
                resultMap.put("instoredTypeName", "YSTR");
                resultMap.put("newSampleList", newSampleList);
                newSampleInfoList.add(resultMap);
            }
        }

        params.put("instoreDate", instoreDate);
        params.put("newSampleInfoList", newSampleInfoList);

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("inboundOrder.ftl", "UTF-8");

            String filename = "-入库单" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + limsCaseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            //类型
            response.setContentType("application/x-msdownload");
            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("下载错误！", ex);
        }
    }

    /**
     * 根据入库类型获取list
     *
     * @param sampleInfoDnaList
     * @param instoredType
     * @return
     */
    public List<LimsSampleInfoDna> getListByInstoredType(List<LimsSampleInfoDna> sampleInfoDnaList, String instoredType) {
        List<LimsSampleInfoDna> sampleList = new ArrayList<>();

        for (LimsSampleInfoDna lsid : sampleInfoDnaList) {
            if (StringUtils.isNotBlank(lsid.getInstoredType()) && instoredType.equals(lsid.getInstoredType())) {
                sampleList.add(lsid);
            }
        }

        return sampleList;
    }

    public Map<String, Object> generateChuanBingIdentifyBook(HttpServletRequest request, String caseId, String groupId, String tandemStatus) {
        Map<String, Object> result = new HashMap<>();


        return result;
    }

    public Map<String, Object> getDataParameters(LimsCaseInfo limsCaseInfo, HttpServletRequest request, String tandemStatus, String groupId) {
        Map<String, Object> params = new HashMap<>();

        //根据案件id查询委托信息
        List<LimsConsignmentInfo> limsConsignmentInfoList = limsConsignmentInfoService.selectByCaseId(limsCaseInfo.getCaseId());
        LimsConsignmentInfo limsConsignmentInfo = null;
        //获取主案件委托信息
        for (LimsConsignmentInfo lci : limsConsignmentInfoList) {
            if (lci.getAppendFlag().equals(Constants.APPEND_FLAG_NON)) {
                limsConsignmentInfo = lci;
                break;
            }
        }

        //鉴定单位
        if (limsConsignmentInfoList.size() > 0) {
            for (int i = 0; i < limsConsignmentInfoList.size(); i++) {
                if (limsConsignmentInfoList.get(i).getAppendFlag().equals("0")) {
                    if (Strings.isNotBlank(limsConsignmentInfoList.get(i).getAcceptOrgId())) {
                        //获取分局信息
                        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(limsConsignmentInfoList.get(i).getAcceptOrgId());
                        if (Objects.nonNull(orgInfo)) {
                            if (Strings.isNotBlank(orgInfo.getOrgQualification())) {
                                //获取实验室名称
                                params.put("unitName", orgInfo.getOrgQualification());
                            }
                        }
                    }
                }
            }
        } else {
            params.put("unitName", SystemUtil.getSystemConfig().get("unitName"));
        }

        //证书编号
        params.put("certificateNo", SystemUtil.getSystemConfig().get("certificateNo"));
        //鉴定地址
        params.put("jdsAddress", SystemUtil.getSystemConfig().get("jdsAddress"));
        //鉴定书中委托单位
        params.put("certificateDelegateOrgName", SystemUtil.getSystemConfig().get("certificateDelegateOrgName"));
        //案件信息
        params.put("limsCaseInfo", limsCaseInfo);
        //处理简要案情中使用的特殊字符，里：<>
        String caseBrief = "";
        if (limsCaseInfo != null) {
            caseBrief = limsCaseInfo.getCaseBrief();
            if (StringUtils.isNotBlank(caseBrief)) {
                //替换左尖括号
                caseBrief = caseBrief.replace("<", "&lt;");
                caseBrief = caseBrief.replace(">", "&gt;");
            }
        }

        params.put("caseBrief", caseBrief);
        //获取委托分局信息
        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(limsConsignmentInfo.getDelegateOrgCode());
        if (orgInfo != null) {
            OrgInfo parentOrgInfo = orgInfoService.selectByPrimaryKey(orgInfo.getParentId());
            params.put("orgInfo", orgInfo);
            params.put("parentOrgInfo", parentOrgInfo);
        }
        //委托信息
        params.put("limsConsignmentInfo", limsConsignmentInfo);
        params.put("limsConsignmentInfoList", limsConsignmentInfoList);
        //案件编号
        String caseSn = "";
        //串并案件编号
        String CaseSnLast = "";
        if (StringUtils.isNotBlank(limsCaseInfo.getCaseNo())) {
            if (limsCaseInfo.getCaseNo().length() <= 14) {
                String[] caseSnArr = limsCaseInfo.getCaseNo().split("-");
                if (caseSnArr.length > 1) {
                    caseSn = caseSnArr[1];

                    if (caseSn.toUpperCase().substring(0, 2).toString().equals("FY")) {
                        caseSn = caseSn.substring(2);
                    }
                } else {
                    caseSn = caseSnArr[0];
                }
            } else {
                caseSn = limsCaseInfo.getCaseNo().split("-")[1];
            }

            String needIdentifyBookSn = SystemUtil.getSystemConfig().getProperty("needIdentifyBookSn");

            if ((needIdentifyBookSn != null) && (needIdentifyBookSn.equals("true"))) {
                CaseSnLast = caseSn;
            } else if ((caseSn.length() == 20) || (caseSn.length() == 21) || (caseSn.length() == 22)) {
                CaseSnLast = caseSn.split("-")[1];
            } else if (caseSn.length() == 11) {
                CaseSnLast = caseSn.split("-")[1];
            } else if ((caseSn.length() == 23) && (caseSn.substring(0, 1).equals("A"))) {
                CaseSnLast = caseSn.substring(14);
            } else if ((caseSn.startsWith("HD")) && (caseSn.length() > 8)) {
                CaseSnLast = caseSn;
            } else {
                CaseSnLast = caseSn;
            }
        }
        params.put("caseSn", caseSn);
        params.put("caseSnLast", CaseSnLast);

        //受理日期和补送受理日期
        String acceptDate = "";
        List<Map<String, Object>> sampleList = new ArrayList<>();
        int firstCount = 0;
        int secondCount = 0;
        for (LimsConsignmentInfo lci : limsConsignmentInfoList) {
            if (lci.getAcceptDatetime() != null) {
                acceptDate += DateUtils.dateToString(lci.getAcceptDatetime(), DateUtils.DATE_SING_STR) + "、";

                List<LimsSampleInfoDna> sampleInfoDnaList = limsSampleInfoDnaService.selectByConsignmentId(lci.getConsignmentId());
                Map<String, Object> resultMap = new LinkedHashMap<>();

                resultMap.put("firstCount", firstCount + 1);

                String firstSampleNo = "";
                String lastSampleNo = "";
                String secondSampleNo = "";
                if (sampleInfoDnaList.size() > 0) {
                    //检材进行排序，补送收的人员检材放在主案件之后
                    sampleInfoDnaList = orderBySampleInfoList(sampleInfoDnaList);
                    secondCount = firstCount + sampleInfoDnaList.size();
                    firstCount = secondCount;

                    if (sampleInfoDnaList.size() != 1) {
                        resultMap.put("secondCount", secondCount);
                    }
                    LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
                    for (int i = 0; i < sampleInfoDnaList.size(); i++) {
                        sampleInfoDna = sampleInfoDnaList.get(i);
                        if (sampleInfoDna != null && StringUtils.isNotBlank(sampleInfoDna.getSampleNo())) {
                            if (Constants.SAMPLE_FLAG_0.equals(sampleInfoDna.getSampleFlag())) {
                                if (StringUtils.isNotBlank(sampleInfoDna.getSampleNo()) && sampleInfoDna.getSampleNo().contains("-")) {
                                    firstSampleNo += sampleInfoDna.getSampleNo().split("-")[1] + "、";
                                }
                            } else {
                                secondSampleNo += sampleInfoDna.getSampleNo() + "、";
                            }
                        }
                    }
                }

                if (StringUtils.isNotBlank(firstSampleNo)) {
                    String[] sampleNoStr = firstSampleNo.split("、");
                    firstSampleNo = sampleNoStr[0];
                    if (sampleNoStr.length == 1) {
                        firstSampleNo = firstSampleNo + "、";
                    }
                    if (sampleNoStr.length > 1) {
                        lastSampleNo = sampleNoStr[sampleNoStr.length - 1] + "、";
                    }
                }

                if (StringUtils.isNotBlank(secondSampleNo)) {
                    secondSampleNo = secondSampleNo.substring(0, secondSampleNo.length() - 1);
                } else {
                    if (StringUtils.isNotBlank(firstSampleNo) && firstSampleNo.contains("、")) {
                        firstSampleNo = firstSampleNo.substring(0, firstSampleNo.length() - 1);
                    }
                    if (StringUtils.isNotBlank(lastSampleNo) && lastSampleNo.contains("、")) {
                        lastSampleNo = lastSampleNo.substring(0, lastSampleNo.length() - 1);
                    }
                }
                resultMap.put("firstSampleNo", firstSampleNo);
                resultMap.put("lastSampleNo", lastSampleNo);
                resultMap.put("secondSampleNo", secondSampleNo);
                resultMap.put("acceptAt", DateUtils.dateToString(lci.getAcceptDatetime(), DateUtils.DATE_SING_STR));

                sampleList.add(resultMap);
            }
        }

        params.put("sampleList", sampleList);

        if (StringUtils.isNotBlank(acceptDate)) {
            acceptDate = acceptDate.substring(0, acceptDate.length() - 1);
        }

        params.put("acceptDate", acceptDate);

        //预实验检材编号
        String yuShiYanBarcode = "";
        //人血检材编号
        String renxueBarcode = "";
        //精斑检材编号
        String jingBanBarcode = "";
        //提取检材编号
        String tiquBarcode = "";
        //扩增试剂盒
        String[] identifiler = null;
        String semenExists = "";
        //是否存在精斑
        String isSemenExists = "";
        //不存在精斑
        String noSemenExists = "";
        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaService.selectByCaseId(limsCaseInfo.getCaseId());
        List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectListByCaseId(limsCaseInfo.getCaseId());
        //无未检出检材
        String notDetected = "";
        String mixSampleNo = "";
        boolean hasResult;
        //是用欧冠试剂盒
        List<String> usedPanelList = new ArrayList();
        String isBloodTest = "";
        String isSemenTest = "";
        if (ListUtils.isNotNullAndEmptyList(limsSampleInfoDnaList)) {
            //检材进行排序，补送收的人员检材放在主案件之后
            limsSampleInfoDnaList = orderBySampleInfoList(limsSampleInfoDnaList);
            for (LimsSampleInfoDna lsid : limsSampleInfoDnaList) {
                if (StringUtils.isNotBlank(lsid.getSampleFlag())) {
                    if (Constants.SAMPLE_FLAG_0.equals(lsid.getSampleFlag())) {
                        if (StringUtils.isNotBlank(lsid.getSampleNo()) && lsid.getSampleNo().contains("-")) {
                            lsid.setSampleNo(lsid.getSampleNo().split("-")[1]);
                        }

                        if (StringUtils.isNotBlank(lsid.getSampleType())) {
                            if (lsid.getSampleType().equals(Constants.SAMPLE_TYPE_01)) {
                                //预实验
                                yuShiYanBarcode = yuShiYanBarcode + lsid.getSampleNo() + "、";
                                //人血
                                renxueBarcode = renxueBarcode + lsid.getSampleNo() + "、";
                                if (StringUtils.isNotBlank(lsid.getPreMethod3Result())) {
                                    //人血做了联苯胺预实验
                                    isBloodTest = "do";
                                }
                            } else if (lsid.getSampleType().equals(Constants.SAMPLE_TYPE_02)) {
                                jingBanBarcode = jingBanBarcode + lsid.getSampleNo() + "、";
                                if (StringUtils.isNotBlank(lsid.getPreMethod2Result())) {
                                    //精斑做了联苯胺预实验
                                    isSemenTest = "do";
                                }
                            }
                        }
                    }
                }

                List<MatchAuditedGene> matchAuditedGeneList = matchAuditedGeneService.selectBySampleId(lsid.getSampleId());
                if (ListUtils.isNotNullAndEmptyList(matchAuditedGeneList)) {
                    for (MatchAuditedGene mag : matchAuditedGeneList) {
                        if (StringUtils.isNotBlank(mag.getPanelId()) && !usedPanelList.contains(mag.getPanelId())) {
                            usedPanelList.add(mag.getPanelId());
                        } else {
                            List<LabPcrSample> labPcrSampleList = labPcrSampleService.selectBySampleId(lsid.getSampleId());
                            if (ListUtils.isNotNullAndEmptyList(labPcrSampleList)) {
                                LabPcrSample labPcrSample = labPcrSampleList.get(0);
                                if (labPcrSample != null) {
                                    LabPcrInfo labPcrInfo = labPcrInfoService.selectByPrimaryKey(labPcrSample.getPcrId());
                                    if (labPcrInfo != null && !usedPanelList.contains(labPcrInfo.getPcrReagent())) {
                                        usedPanelList.add(labPcrInfo.getPcrReagent());
                                    }
                                }
                            }
                        }
                    }
                }

                if (usedPanelList.size() > 0) {
                    identifiler = new String[usedPanelList.size()];
                    if (identifiler != null || identifiler.length > 0) {
                        usedPanelList.toArray(identifiler);
                    }
                }

                if (StringUtils.isNotBlank(lsid.getPreMethod2Result())) {
                    if ("+".equals(lsid.getPreMethod2Result())) {
                        isSemenExists = isSemenExists + lsid.getSampleNo() + "、";
                    } else if ("-".equals(lsid.getPreMethod2Result())) {
                        noSemenExists = noSemenExists + lsid.getSampleNo() + "、";
                    }
                }

                //顺义新需求未检出的检材也必须是审核通过的
                /*if (ListUtils.isNotNullAndEmptyList(limsSampleGeneList)) {
                    hasResult = false;
                    //获取未检出基因型检材
                    for (LimsSampleGene sampleGene : limsSampleGeneList) {
                        if (lsid.getSampleId().equals(sampleGene.getSampleId()) && StringUtils.isNotBlank(sampleGene.getGeneInfo())) {
                            hasResult = true;
                            break;
                        }
                    }

                    if (!hasResult) {
                        notDetected += lsid.getSampleNo() + "、";
                    }
                }*/
            }
        }

        params.put("isBloodTest", isBloodTest);
        params.put("isSemenTest", isSemenTest);

        //无基因型检材
        if (ListUtils.isNotNullAndEmptyList(limsSampleGeneList)) {
            //获取未检出基因型检材
            LimsSampleInfoDna sampleInfoDna = new LimsSampleInfoDna();
            for (LimsSampleGene sampleGene : limsSampleGeneList) {
                sampleInfoDna = limsSampleInfoDnaService.selectSampleInfoDnaById(sampleGene.getSampleId());
                if (StringUtils.isNotBlank(sampleGene.getGeneInfo())) {
                    List<String> markerList = geneSameCompareUtil.getMarker(sampleGene.getGeneInfo());
                    //审核通过的检材且基因座少于8个时，表示未检出
                    if (markerList != null && markerList.size() < 8) {
                        if (sampleInfoDna != null) {
                            notDetected += sampleInfoDna.getSampleNo() + "、";
                        }
                    }
                } else {
                    notDetected += sampleInfoDna.getSampleNo() + "、";
                }
            }
        }
        if (StringUtils.isNotBlank(notDetected)) {
            notDetected = notDetected.substring(0, notDetected.length() - 1);
        }

        params.put("notDetected", notDetected);

        params.put("limsSampleInfoDnaList", limsSampleInfoDnaList);

        String totalIdentifiler = "";
        if (identifiler != null && identifiler.length > 0) {
            for (int i = 0; i < identifiler.length; i++) {
                if (StringUtils.isNotBlank(identifiler[i])) {
                    if (identifiler[i].equals("Identifiler") || CommonUtils.contain(identifiler[i].replaceAll("\\s*", ""), "idplus")) {
                        identifiler[i] = ("Identifiler" + " Plus(AB)");
                    }
                    if (identifiler[i].equals("PowPlex16")) {
                        identifiler[i] = "PowPlex16(Promega)";
                    }
                    if (identifiler[i].contains("PowerPlex_21")) {
                        identifiler[i] = "PowerPlex_21";
                    }
                    if (i == 0) {
                        totalIdentifiler = totalIdentifiler + identifiler[i];
                    } else {
                        totalIdentifiler = totalIdentifiler + "及" + identifiler[i];
                    }
                }
            }
        }

        //扩增试剂盒
        params.put("pcrRegeant", totalIdentifiler);
        params.put("usedPanelList", usedPanelList);

        if (limsSampleInfoDnaList.size() < 10) {
            tiquBarcode = "01-0" + limsSampleInfoDnaList.size();
        } else {
            tiquBarcode = "01-" + limsSampleInfoDnaList.size();
        }

        params.put("tiquBarcode", tiquBarcode);

        String sampleFlag_0 = "";
        String sampleFlag_1 = "";
        for (LimsSampleInfoDna sampleInfoDna : limsSampleInfoDnaList) {
            if (Constants.SAMPLE_FLAG_0.equals(sampleInfoDna.getSampleFlag())) {
                sampleFlag_0 += sampleInfoDna.getSampleNo() + "、";
            } else {
                sampleFlag_1 += sampleInfoDna.getSampleNo() + "、";
            }
        }

        String firstTiquBarcode = "";
        String lastTiquBarcode = "";
        String secondTiquBarcode = "";
        if (StringUtils.isNotBlank(sampleFlag_0)) {
            String[] sampleNoStr = sampleFlag_0.split("、");
            firstTiquBarcode = sampleNoStr[0];
            if (sampleNoStr.length == 1) {
                firstTiquBarcode = firstTiquBarcode + "、";
            }
            if (sampleNoStr.length > 1) {
                lastTiquBarcode = sampleNoStr[sampleNoStr.length - 1] + "、";
            }
        }

        if (StringUtils.isNotBlank(sampleFlag_1)) {
            secondTiquBarcode += sampleFlag_1;
        }
        if (StringUtils.isNotBlank(secondTiquBarcode)) {
            secondTiquBarcode = secondTiquBarcode.substring(0, secondTiquBarcode.length() - 1);
        } else {
            if (StringUtils.isNotBlank(firstTiquBarcode) && firstTiquBarcode.contains("、")) {
                firstTiquBarcode = firstTiquBarcode.substring(0, firstTiquBarcode.length() - 1);
            }
            if (StringUtils.isNotBlank(lastTiquBarcode) && lastTiquBarcode.contains("、")) {
                lastTiquBarcode = lastTiquBarcode.substring(0, lastTiquBarcode.length() - 1);
            }
        }
        params.put("firstTiquBarcode", firstTiquBarcode);
        params.put("lastTiquBarcode", lastTiquBarcode);
        params.put("secondTiquBarcode", secondTiquBarcode);

        //预实验
        if (StringUtils.isNotBlank(yuShiYanBarcode) && yuShiYanBarcode.contains("、")) {
            yuShiYanBarcode = yuShiYanBarcode.substring(0, yuShiYanBarcode.length() - 1);
        }
        params.put("yuShiYanBarcode", yuShiYanBarcode);

        //DNA提取
        DictItem dictItem = new DictItem();
        //提取实验方法List
        dictItem.setDictTypeCode(Constants.EXTRACTION_TEST_METHOD);
        List<DictItem> extractTestMethodList = DictUtil.getDictList(dictItem);
        List<Map<String, Object>> extractList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(extractTestMethodList) && ListUtils.isNotNullAndEmptyList(limsSampleInfoDnaList)) {
            for (DictItem di : extractTestMethodList) {
                Map<String, Object> map = new HashMap<>();
                String sample = "";
                String sampleSemen = "";
                for (LimsSampleInfoDna lsid : limsSampleInfoDnaList) {
                    if (lsid.getSampleType().equals(Constants.SAMPLE_TYPE_02)) {
                        sampleSemen += lsid.getSampleNo() + "、";
                    } else {
                        List<LabExtractSample> labExtractSampleList = labExtractSampleService.selectLabExtractSampleBySampleId(lsid.getSampleId());
                        if (ListUtils.isNotNullAndEmptyList(labExtractSampleList)) {
                            LabExtractSample labExtractSample = labExtractSampleList.get(0);
                            if (labExtractSample != null) {
                                if (di.getDictCode().equals(labExtractSample.getExtractMethod())) {
                                    sample += lsid.getSampleNo() + "、";
                                }
                            }
                        }
                    }
                }
                if (StringUtils.isNotBlank(sample)) {
                    if (sample.contains("、")) {
                        sample = sample.substring(0, sample.length() - 1);
                    }
                    di.setDictName("磁珠法");
                    map.put("extractTestMethod", di.getDictName());
                    map.put("sampleNo", sample);

                    extractList.add(map);
                }
                if (StringUtils.isNotBlank(sampleSemen)) {
                    if (sampleSemen.contains("、")) {
                        sampleSemen = sampleSemen.substring(0, sampleSemen.length() - 1);
                    }
                    params.put("sampleSemen", sampleSemen);
                }
            }
        }
        params.put("extractList", extractList);

        //精斑检材
        if (StringUtils.isNotBlank(jingBanBarcode)) {
            //精斑
            if (jingBanBarcode.contains("、")) {
                jingBanBarcode = jingBanBarcode.substring(0, jingBanBarcode.length() - 1);
            }
            //检出精斑
            if (isSemenExists.contains("、")) {
                isSemenExists = jingBanBarcode.substring(0, jingBanBarcode.length());
            }
            //未检材精斑
            if (noSemenExists.contains("、")) {
                noSemenExists = jingBanBarcode.substring(0, jingBanBarcode.length());
            }
        }
        params.put("jingBanBarcode", jingBanBarcode);
        params.put("isSemenExists", isSemenExists);
        params.put("noSemenExists", noSemenExists);

        //人血检材
        if (StringUtils.isNotBlank(renxueBarcode) && renxueBarcode.contains("、")) {
            renxueBarcode = renxueBarcode.substring(0, renxueBarcode.length() - 1);
        }
        params.put("renxueBarcode", renxueBarcode);

        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            params.put("date", "redirect:/login.html?timeoutFlag=1");
            return params;
        }
        //获取当前分局下的签字人
        List<AmPersonalInfo> amPersonalInfoList = amPersonalInfoService.queryAmPersonalInfoListOrgId(operateUser.getOrgId());

        List<Examiner> examinerList = examinerService.selectListByCaseId(limsCaseInfo.getCaseId());
        Examiner examiner = new Examiner();
        if (ListUtils.isNotNullAndEmptyList(examinerList)) {
            examiner = examinerList.get(0);
            if (ListUtils.isNotNullAndEmptyList(amPersonalInfoList)) {
                for (AmPersonalInfo api : amPersonalInfoList) {
                    if (examiner.getInspector1().equals(api.getPersonalId())) {
                        examiner.setInspector1Name(api.getFullName());
                        examiner.setPhone1(api.getPhoneNumber());
                    }

                    if (examiner.getInspector2().equals(api.getPersonalId())) {
                        examiner.setInspector2Name(api.getFullName());
                        examiner.setPhone2(api.getPhoneNumber());
                    }

                    if (examiner.getInspector3().equals(api.getPersonalId())) {
                        examiner.setInspector3Name(api.getFullName());
                        examiner.setPhone3(api.getPhoneNumber());
                    }
                }
            }
        }

        params.put("examiner", examiner);

        //获取当前时间
        params.put("currentDate", new Date());


        //常染色体和混合基因型列表
        MatchAuditedGene matchAuditedGene = new MatchAuditedGene();
        //基因类型
        matchAuditedGene.setCaseId(limsCaseInfo.getCaseId());
        matchAuditedGene.setGeneTypeList(Arrays.asList(Constants.GENE_STR, Constants.GENE_MIXED));
        if (StringUtils.isNotBlank(tandemStatus) && tandemStatus.equals("1") && StringUtils.isNotBlank(groupId)) {
            matchAuditedGene.setGroupId(groupId);
        }
        List<MatchAuditedGene> matchAuditedGeneList = matchAuditedGeneService.selectByMatchAuditedGene(matchAuditedGene);

        if (ListUtils.isNotNullAndEmptyList(matchAuditedGeneList)) {
            matchAuditedGeneList = ListUtils.removeDuplicate(matchAuditedGeneList);
            //基因型进行排序，补送人员排在主案件人员之后
            matchAuditedGeneList = orderBySampleGeneList(matchAuditedGeneList);
        }

        List<SampleGeneDataModel> sampleGeneDataModelList = new ArrayList<>();
        List<String> markerList = null;
        List<List<String>> groupList = null;
        //查询审核过的常染色体基因信息
        List<MatchAuditedGene> auditedGeneList = new ArrayList<>();
        //查询审核过混合的基因信息
        List<MatchAuditedGene> mixAuditedGeneList = new ArrayList<>();

        //查询同一比中信息
        List<CompareSameResult> compareSameResultList = compareSameResultService.selectListByCaseId(limsCaseInfo.getCaseId());
        //比对进行排序
        compareSameResultList = orderBySameResultList(compareSameResultList);
        List<CompareSameResult> distinctSampleResultList = compareSameResultService.selectDistinctListByCaseId(limsCaseInfo.getCaseId());
        //参照检材进行排序
        distinctSampleResultList = orderBySameResultList(distinctSampleResultList);
        //获取亲缘比对结果
        List<CompareRelativeResult> compareRelativeResultList = compareRelativeResultService.selectListByCaseId(limsCaseInfo.getCaseId());

        if (ListUtils.isNotNullAndEmptyList(matchAuditedGeneList)) {

            for (MatchAuditedGene mag : matchAuditedGeneList) {
                if (Constants.GENE_STR.equals(mag.getGeneType())) {
                    auditedGeneList.add(mag);
                }
                if (Constants.GENE_MIXED.equals(mag.getGeneType())) {
                    mixAuditedGeneList.add(mag);
                }
            }

            for (int i = 0; i < matchAuditedGeneList.size(); i++) {
                MatchAuditedGene auditedGene = matchAuditedGeneList.get(i);
                if (auditedGene != null) {
                    String geneInfo = auditedGene.getGeneInfo();
                    if (StringUtils.isNotBlank(auditedGene.getPanelName()) && CommonUtils.contain(auditedGene.getPanelName(), "identifiler")) {
                        geneInfo = geneSameCompareUtil.getBooksGeneInfoList(Constants.IdentifilerPlusList, geneInfo);
                    }
                    markerList = geneSameCompareUtil.getNewMarker(geneInfo);
                    groupList = geneSameCompareUtil.analysisGeneInfo(geneInfo);

                    //只有基因座数量至少大于13个
                    if (markerList.size() > 13) {
                        break;
                    }
                }

            }

            if (ListUtils.isNotNullAndEmptyList(groupList)) {
                for (int i = 0; i < groupList.size(); i++) {
                    SampleGeneDataModel sampleGeneDataModel = new SampleGeneDataModel();
                    List<Map<String, List<String>>> geneDataModelList = new ArrayList<>();
                    // 按顺序对基因型排序，比对分组展示在一起，先展示人员比对分组，再展示物物比对分组，最后展示混合检材分型
                    //1、添加同一比对的检材基因分型
                    if (ListUtils.isNotNullAndEmptyList(distinctSampleResultList)) {
                        for (CompareSameResult result : distinctSampleResultList) {
                            MatchAuditedGene referenceMag = new MatchAuditedGene();
                            //添加比对源检材基因信息
                            for (MatchAuditedGene mag : matchAuditedGeneList) {
                                if (result.getReferenceId().equals(mag.getSampleNo())) {
                                    MatchAuditedGene newMag = new MatchAuditedGene();
                                    if (StringUtils.isNotBlank(mag.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(mag.getSampleFlag())) {
                                        if (StringUtils.isNotBlank(mag.getSampleNo()) && mag.getSampleNo().contains("-")) {
                                            newMag.setSampleNo(mag.getSampleNo().split("-")[1]);
                                            newMag.setGeneInfo(mag.getGeneInfo());
                                        }
                                    } else {
                                        newMag = mag;
                                    }

                                    referenceMag.setGeneInfo(mag.getGeneInfo());
                                    Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), newMag);
                                    geneDataModelList.add(mapList);
                                }
                            }
                            //获取被比中检材分型
                            String getMatchedSampleNo = "";
                            for (CompareSameResult csr : compareSameResultList) {
                                if (result.getReferenceId().equals(csr.getReferenceId()) && !result.getReferenceId().equals(csr.getSampleNo())) {
                                    if (StringUtils.isNotBlank(csr.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(csr.getSampleFlag())) {
                                        if (StringUtils.isNotBlank(csr.getSampleNo()) && csr.getSampleNo().contains("-")) {
                                            getMatchedSampleNo += csr.getSampleNo().split("-")[1] + "、";
                                        }
                                    }
                                }
                            }
                            //截掉最后一位符号
                            if (StringUtils.isNotBlank(getMatchedSampleNo)) {
                                getMatchedSampleNo = getMatchedSampleNo.substring(0, getMatchedSampleNo.length() - 1);
                                referenceMag.setSampleNo(getMatchedSampleNo);
                                //添加被比中检材的基因分型
                                Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), referenceMag);
                                geneDataModelList.add(mapList);
                            }
                        }
                    }


                    //2、添加未比中的检材分型
                    if (tandemStatus.equals("0")) {
                        for (MatchAuditedGene mag : matchAuditedGeneList) {

                            if (mag.getGeneCount() >= 8) {
                                //获取未比中的检材基因信息
                                if (Constants.GENE_STR.equals(mag.getGeneType())) {
                                    /*if (compareRelativeResultList.size() > 0) {*/
                                    mag = geneSameCompareUtil.getNotMatchGene(mag, compareSameResultList, compareRelativeResultList);
                                    if (mag != null) {
                                        if (StringUtils.isNotBlank(mag.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(mag.getSampleFlag())) {
                                            if (StringUtils.isNotBlank(mag.getSampleNo()) && mag.getSampleNo().contains("-")) {
                                                mag.setSampleNo(mag.getSampleNo().split("-")[1]);
                                            }
                                        }
                                        if (StringUtils.isNotBlank(mag.getGeneInfo())) {
                                            Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), mag);
                                            geneDataModelList.add(mapList);
                                        }

                                    }
                                }
                            }
                        }

                        /*for (MatchAuditedGene mag : matchAuditedGeneList) {

                            if (mag.getGeneCount() >= 8) {
                                //获取未比中的检材基因信息
                                if (Constants.GENE_NORMAL.equals(mag.getGeneType())) {
                                    if (compareRelativeResultList.size() > 0) {
                                        mag = geneSameCompareUtil.getNotMatchGene(mag, compareSameResultList, compareRelativeResultList);
                                        if (mag != null) {
                                            if (StringUtils.isNotBlank(mag.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(mag.getSampleFlag())) {
                                                if (StringUtils.isNotBlank(mag.getSampleNo()) && mag.getSampleNo().contains("-")) {
                                                    mag.setSampleNo(mag.getSampleNo().split("-")[1]);
                                                }
                                            }
                                            if (StringUtils.isNotBlank(mag.getGeneInfo())) {
                                                Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), mag);
                                                geneDataModelList.add(mapList);
                                            }

                                        }
                                    }


                            *//*if (compareRelativeResultList.size()>0){*//*
                         *//*mag = geneSameCompareUtil.getNotMatchGene(mag, compareSameResultList, compareRelativeResultList);
                            if (mag != null) {
                                if (StringUtils.isNotBlank(mag.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(mag.getSampleFlag())) {
                                    if (StringUtils.isNotBlank(mag.getSampleNo()) && mag.getSampleNo().contains("-")) {
                                        mag.setSampleNo(mag.getSampleNo().split("-")[1]);
                                    }
                                }
                                if(StringUtils.isNotBlank(mag.getGeneInfo())){
                                    Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), mag);
                                    geneDataModelList.add(mapList);
                                }

                            }*//*
                         *//*}*//*
                                }
                            }
                        }*/
                    } else {
                        for (MatchAuditedGene mag : matchAuditedGeneList) {
                            List<MatchSameResult> matchSameResultList = new ArrayList<>();
                            if (StringUtils.isNotBlank(groupId)) {
                                MatchSameResult matchSameResult = new MatchSameResult();
                                matchSameResult.setSampleaId(mag.getSampleId());
                                matchSameResult.setGroupId(groupId);
                                matchSameResultList = matchSameResultService.selectBySampleIdAndGroupId(matchSameResult);
                            }

                            if (matchSameResultList.size() > 0) {
                                if (mag.getGeneCount() >= 8) {
                                    //获取未比中的检材基因信息
                                    if (Constants.GENE_STR.equals(mag.getGeneType())) {
                                        mag = geneSameCompareUtil.getNotMatchGene(mag, compareSameResultList, compareRelativeResultList);
                                        if (mag != null) {
                                            if (StringUtils.isNotBlank(mag.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(mag.getSampleFlag())) {
                                                if (StringUtils.isNotBlank(mag.getSampleNo()) && mag.getSampleNo().contains("-")) {
                                                    mag.setSampleNo(mag.getSampleNo().split("-")[1]);
                                                }
                                            }
                                            if (StringUtils.isNotBlank(mag.getGeneInfo())) {
                                                Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), mag);
                                                geneDataModelList.add(mapList);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                    //3、添加混合的检材分型
                    for (MatchAuditedGene mag : matchAuditedGeneList) {
                        if (mag.getGeneCount() >= 8) {
                            //获取未比中的检材基因信息
                            if (Constants.GENE_MIXED.equals(mag.getGeneType())) {
                                mag = geneSameCompareUtil.getNotMatchGene(mag, compareSameResultList, compareRelativeResultList);
                                if (mag != null) {
                                    if (StringUtils.isNotBlank(mag.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(mag.getSampleFlag())) {
                                        if (StringUtils.isNotBlank(mag.getSampleNo()) && mag.getSampleNo().contains("-")) {
                                            mag.setSampleNo(mag.getSampleNo().split("-")[1]);
                                        }
                                    }
                                    Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), mag);
                                    geneDataModelList.add(mapList);
                                }
                            }
                        }
                    }

                    //判断是否存在基因分型
                    if (ListUtils.isNotNullAndEmptyList(geneDataModelList)) {
                        sampleGeneDataModel.setGeneDataModelList(geneDataModelList);
                        sampleGeneDataModel.setMarkerList(groupList.get(i));
                        List<String> markerList1 = sampleGeneDataModel.getMarkerList();
                        for (int a = 0; a < markerList1.size(); a++) {
                            if (CommonUtils.contain(markerList1.get(a), "amel")) {
                                markerList1.set(a, "Amel");
                            }
                        }
                        sampleGeneDataModel.setMarkerList(markerList1);
                        sampleGeneDataModelList.add(sampleGeneDataModel);
                    }
                }
            }
        }

        params.put("sampleGeneDataModelList", sampleGeneDataModelList);

        //混合基因型检材
        if (ListUtils.isNotNullAndEmptyList(mixAuditedGeneList)) {
            for (MatchAuditedGene sampleGene : mixAuditedGeneList) {
                if (StringUtils.isNotBlank(sampleGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(sampleGene.getSampleFlag())) {
                    if (StringUtils.isNotBlank(sampleGene.getSampleNo()) && sampleGene.getSampleNo().contains("-")) {
                        mixSampleNo += sampleGene.getSampleNo().split("-")[1] + "、";
                    }
                } else {
                    mixSampleNo += sampleGene.getSampleNo() + "、";
                }
            }
            //混合基因型检材
            if (StringUtils.isNotBlank(mixSampleNo)) {
                mixSampleNo = mixSampleNo.substring(0, mixSampleNo.length() - 1);
            }
        }
        params.put("mixSampleNo", mixSampleNo);

        //亲缘比对分型
        List<SampleGeneDataModel> relativeGeneDataModelList = new ArrayList<>();
        List<Map<String, Object>> relativeResultList = new ArrayList<>();
        List<CompareRelativeResult> threeRelativeResultList = new ArrayList<>();
        List<CompareRelativeResult> twoRelativeResultList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(compareRelativeResultList)) {
            String childSampleName = "";
            String fatherSampleName = "";
            String motherSampleName = "";
            String childGeneInfo = "";
            String fatherGeneInfo = "";
            String motherGeneInfo = "";
            String probability1 = "";
            String probability2 = "";
            String matchModel = "0";
            for (CompareRelativeResult crr : compareRelativeResultList) {
                if (StringUtils.isNotBlank(crr.getChildSampleNo())) {
                    Map<String, Object> relationMap = new LinkedHashMap<>();

                    List<MatchAuditedGene> childAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getChildSampleNo());
                    MatchAuditedGene childAuditedGene = null;
                    Integer markerCount = 20;
                    String fistMarker = "D3S1358";
                    if (ListUtils.isNotNullAndEmptyList(childAuditedGeneList)) {
                        childAuditedGene = childAuditedGeneList.get(0);
                        if (childAuditedGene != null) {
                            String geneInfo = childAuditedGene.getGeneInfo();
                            if (CommonUtils.contain(childAuditedGene.getPanelName(), "identifiler")) {
                                geneInfo = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, childAuditedGene.getGeneInfo());
                            }
                            markerList = geneSameCompareUtil.getNewMarker(geneInfo);
                            groupList = geneSameCompareUtil.analysisGeneInfo(geneInfo);
                            if (ListUtils.isNotNullAndEmptyList(markerList)) {
                                markerCount = markerList.size();

                                for (String marker : markerList) {
                                    fistMarker = marker;
                                    break;
                                }
                            }

                            childSampleName = childAuditedGene.getSampleName();
                        }
                    }

                    List<MatchAuditedGene> fatherAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getFatherSampleNo());
                    MatchAuditedGene fatherAuditedGene = null;
                    if (ListUtils.isNotNullAndEmptyList(fatherAuditedGeneList)) {
                        fatherAuditedGene = fatherAuditedGeneList.get(0);
                        if (fatherAuditedGene != null) {
                            fatherSampleName = fatherAuditedGene.getSampleName();
                        }
                    }

                    List<MatchAuditedGene> motherAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getMotherSampleNo());
                    MatchAuditedGene motherAuditedGene = null;
                    if (ListUtils.isNotNullAndEmptyList(motherAuditedGeneList)) {
                        motherAuditedGene = motherAuditedGeneList.get(0);
                        if (motherAuditedGene != null) {
                            motherSampleName = motherAuditedGene.getSampleName();
                        }
                    }

                    if (StringUtils.isNotBlank(crr.getCompareTotalProbability())) {
                        String[] probabilityArr = crr.getCompareTotalProbability().split("E");
                        if (probabilityArr.length > 1) {
                            probability1 = probabilityArr[0];
                            probability2 = probabilityArr[1];
                        }
                    }

                    if (StringUtils.isNotBlank(crr.getChildSampleNo()) && StringUtils.isNotBlank(crr.getFatherSampleNo()) && StringUtils.isNotBlank(crr.getMotherSampleNo())) {
                        //比中父母
                        matchModel = "1";
                    } else if (StringUtils.isNotBlank(crr.getChildSampleNo()) && StringUtils.isNotBlank(crr.getFatherSampleNo()) && StringUtils.isBlank(crr.getMotherSampleNo())) {
                        //比中父亲
                        matchModel = "2";
                    } else if (StringUtils.isNotBlank(crr.getChildSampleNo()) && StringUtils.isBlank(crr.getFatherSampleNo()) && StringUtils.isNotBlank(crr.getMotherSampleNo())) {
                        //比中母亲
                        matchModel = "3";
                    }

                    relationMap.put("markerCount", markerCount);
                    relationMap.put("fistMarker", fistMarker);
                    relationMap.put("childSampleName", childSampleName);
                    relationMap.put("fatherSampleName", fatherSampleName);
                    relationMap.put("motherSampleName", motherSampleName);
                    relationMap.put("probability1", probability1);
                    relationMap.put("probability2", probability2);
                    relationMap.put("matchModel", matchModel);

                    relativeResultList.add(relationMap);

                    //区分是三联体还是二联体
                    if (StringUtils.isNotBlank(crr.getFatherSampleNo()) && StringUtils.isNotBlank(crr.getMotherSampleNo())
                            && StringUtils.isNotBlank(crr.getChildSampleNo())) {
                        threeRelativeResultList.add(crr);
                    } else {
                        twoRelativeResultList.add(crr);
                    }
                }
            }

            //三联体基因分型
            childGeneInfo = "";
            fatherGeneInfo = "";
            motherGeneInfo = "";
            if (ListUtils.isNotNullAndEmptyList(threeRelativeResultList)) {
                for (CompareRelativeResult crr : threeRelativeResultList) {
                    if (StringUtils.isNotBlank(crr.getChildSampleNo())) {
                        List<MatchAuditedGene> childAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getChildSampleNo());
                        MatchAuditedGene childAuditedGene = null;
                        if (ListUtils.isNotNullAndEmptyList(childAuditedGeneList)) {
                            childAuditedGene = childAuditedGeneList.get(0);
                            if (childAuditedGene != null) {
                                String geneInfo = childAuditedGene.getGeneInfo();
                                if (CommonUtils.contain(childAuditedGene.getPanelName(), "identifiler")) {
                                    geneInfo = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, childAuditedGene.getGeneInfo());
                                }
                                groupList = geneSameCompareUtil.analysisGeneInfo(geneInfo);
                                childGeneInfo = childAuditedGene.getGeneInfo();
                            }
                        }

                        List<MatchAuditedGene> fatherAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getFatherSampleNo());
                        MatchAuditedGene fatherAuditedGene = null;
                        if (ListUtils.isNotNullAndEmptyList(fatherAuditedGeneList)) {
                            fatherAuditedGene = fatherAuditedGeneList.get(0);
                            if (fatherAuditedGene != null) {
                                fatherGeneInfo = fatherAuditedGene.getGeneInfo();
                            }
                        }

                        List<MatchAuditedGene> motherAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getMotherSampleNo());
                        MatchAuditedGene motherAuditedGene = null;
                        if (ListUtils.isNotNullAndEmptyList(motherAuditedGeneList)) {
                            motherAuditedGene = motherAuditedGeneList.get(0);
                            if (motherAuditedGene != null) {
                                motherGeneInfo = motherAuditedGene.getGeneInfo();
                            }
                        }

                        //三联体按照父、母、子的顺序生成基因分型，如果比中多个孩子往下累加基因分型和pi值
                        Map<String, Object> resultMap = geneRelativeCompareUtil.relationCompare(fatherGeneInfo, motherGeneInfo, childGeneInfo, crr);
                        if (resultMap != null) {
                            ParentageMatchResult parentageMatchResult = (ParentageMatchResult) resultMap.get("parentageMatchResult");
                            if (parentageMatchResult != null) {
                                ParentageMatchResultRecord totalResult = parentageMatchResult.getResult();
                                if (totalResult != null) {
                                    if (ListUtils.isNotNullAndEmptyList(groupList)) {
                                        Map<String, Double> marker = totalResult.getPiOfMarker();
                                        for (int i = 0; i < groupList.size(); i++) {
                                            SampleGeneDataModel sampleGeneDataModel = new SampleGeneDataModel();
                                            List<Map<String, List<String>>> geneDataModelList = new ArrayList<>();

                                            //添加父亲基因分型
                                            if (fatherAuditedGene != null) {
                                                if (StringUtils.isNotBlank(fatherAuditedGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(fatherAuditedGene.getSampleFlag())) {
                                                    if (StringUtils.isNotBlank(fatherAuditedGene.getSampleNo()) && fatherAuditedGene.getSampleNo().contains("-")) {
                                                        fatherAuditedGene.setSampleNo(fatherAuditedGene.getSampleNo().split("-")[1]);
                                                    }
                                                }

                                                Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), fatherAuditedGene);
                                                geneDataModelList.add(mapList);
                                            }
                                            //添加母亲基因分型
                                            if (motherAuditedGene != null) {
                                                if (StringUtils.isNotBlank(motherAuditedGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(motherAuditedGene.getSampleFlag())) {
                                                    if (StringUtils.isNotBlank(motherAuditedGene.getSampleNo()) && motherAuditedGene.getSampleNo().contains("-")) {
                                                        motherAuditedGene.setSampleNo(motherAuditedGene.getSampleNo().split("-")[1]);
                                                    }
                                                }

                                                Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), motherAuditedGene);
                                                geneDataModelList.add(mapList);
                                            }

                                            //添加孩子基因分型
                                            if (childAuditedGene != null) {
                                                if (StringUtils.isNotBlank(childAuditedGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(childAuditedGene.getSampleFlag())) {
                                                    if (StringUtils.isNotBlank(childAuditedGene.getSampleNo()) && childAuditedGene.getSampleNo().contains("-")) {
                                                        childAuditedGene.setSampleNo(childAuditedGene.getSampleNo().split("-")[1]);
                                                    }
                                                }

                                                Map<String, List<String>> mapList = geneSameCompareUtil.analysisGeneList(groupList.get(i), childAuditedGene);
                                                geneDataModelList.add(mapList);
                                            }

                                            //添加pi值
                                            Map<String, List<String>> mapList = null;
                                            if (ListUtils.isNotNullAndEmptyList(groupList.get(i))) {
                                                List<String> piList = new ArrayList<>();
                                                piList.add("PI值");
                                                for (String markerName : groupList.get(i)) {
                                                    String piStr = "";
                                                    if (marker.containsKey(markerName)) {
                                                        Double piDouble = marker.get(markerName);
                                                        if (!Double.isNaN(piDouble)) {
                                                            piStr = String.format("%.3f", piDouble);
                                                        }
                                                    }
                                                    mapList = new LinkedHashMap<>();
                                                    piList.add(piStr);
                                                }
                                                mapList.put("PI", piList);
                                                geneDataModelList.add(mapList);
                                            }

                                            //判断是否存在基因分型
                                            if (ListUtils.isNotNullAndEmptyList(geneDataModelList)) {
                                                sampleGeneDataModel.setGeneDataModelList(geneDataModelList);
                                                sampleGeneDataModel.setMarkerList(groupList.get(i));
                                                List<String> markerList1 = sampleGeneDataModel.getMarkerList();
                                                for (int a = 0; a < markerList1.size(); a++) {
                                                    if (CommonUtils.contain(markerList1.get(a), "amel")) {
                                                        markerList1.set(a, "Amel");
                                                    }
                                                }
                                                sampleGeneDataModel.setMarkerList(markerList1);
                                                relativeGeneDataModelList.add(sampleGeneDataModel);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //二联体基因分型
            childGeneInfo = "";
            fatherGeneInfo = "";
            motherGeneInfo = "";
            String childSampleNo = "";
            String fatherSampleNo = "";
            String motherSampleNo = "";
            String twoSampleNo = "";
            if (ListUtils.isNotNullAndEmptyList(twoRelativeResultList)) {
                for (int i = 0; i < twoRelativeResultList.size(); i++) {
                    CompareRelativeResult crr = twoRelativeResultList.get(i);
                    if (crr != null && StringUtils.isNotBlank(crr.getChildSampleNo())) {
                        //判断twoSampleNo是否包含此比对结果检材，如果存在表示已经跟其他比对结果合并
                        if (StringUtils.isNotBlank(twoSampleNo)) {
                            if (twoSampleNo.contains(crr.getFatherSampleNo()) && twoSampleNo.contains(crr.getChildSampleNo())) {
                                continue;
                            } else if (twoSampleNo.contains(crr.getMotherSampleNo()) && twoSampleNo.contains(crr.getChildSampleNo())) {
                                continue;
                            }
                        }
                        List<MatchAuditedGene> childAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getChildSampleNo());
                        MatchAuditedGene childAuditedGene = null;
                        if (ListUtils.isNotNullAndEmptyList(childAuditedGeneList)) {
                            childAuditedGene = childAuditedGeneList.get(0);
                            if (childAuditedGene != null) {
                                String geneInfo = childAuditedGene.getGeneInfo();
                                if (CommonUtils.contain(childAuditedGene.getPanelName(), "identifiler")) {
                                    geneInfo = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, childAuditedGene.getGeneInfo());
                                }
                                groupList = geneSameCompareUtil.analysisGeneInfo(geneInfo);

                                childGeneInfo = childAuditedGene.getGeneInfo();
                                childSampleNo = childAuditedGene.getSampleNo();
                            }
                        }

                        List<MatchAuditedGene> fatherAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getFatherSampleNo());
                        MatchAuditedGene fatherAuditedGene = null;
                        if (ListUtils.isNotNullAndEmptyList(fatherAuditedGeneList)) {
                            fatherAuditedGene = fatherAuditedGeneList.get(0);
                            if (fatherAuditedGene != null) {
                                fatherGeneInfo = fatherAuditedGene.getGeneInfo();
                                fatherSampleNo = fatherAuditedGene.getSampleNo();
                            }
                        }

                        List<MatchAuditedGene> motherAuditedGeneList = matchAuditedGeneService.selectListBySampleNo(crr.getMotherSampleNo());
                        MatchAuditedGene motherAuditedGene = null;
                        if (ListUtils.isNotNullAndEmptyList(motherAuditedGeneList)) {
                            motherAuditedGene = motherAuditedGeneList.get(0);
                            if (motherAuditedGene != null) {
                                motherGeneInfo = motherAuditedGene.getGeneInfo();
                                motherSampleNo = motherAuditedGene.getSampleNo();
                            }
                        }

                        //二联体添加pi值，以孩子为基准去找是否有参与多个比对的
                        Map<String, Object> resultMap = geneRelativeCompareUtil.relationCompare(fatherGeneInfo, motherGeneInfo, childGeneInfo, crr);
                        if (resultMap != null) {
                            ParentageMatchResult parentageMatchResult = (ParentageMatchResult) resultMap.get("parentageMatchResult");
                            if (parentageMatchResult != null) {
                                ParentageMatchResultRecord totalResult = parentageMatchResult.getResult();
                                if (totalResult != null) {
                                    Map<String, Double> marker = totalResult.getPiOfMarker();
                                    if (ListUtils.isNotNullAndEmptyList(groupList)) {
                                        for (int k = 0; k < groupList.size(); k++) {
                                            SampleGeneDataModel sampleGeneDataModel = new SampleGeneDataModel();
                                            List<Map<String, List<String>>> geneDataModelList = new ArrayList<>();
                                            Map<String, List<String>> mapList = null;
                                            //添加父亲基因分型
                                            if (fatherAuditedGene != null) {
                                                if (StringUtils.isNotBlank(fatherAuditedGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(fatherAuditedGene.getSampleFlag())) {
                                                    if (StringUtils.isNotBlank(fatherAuditedGene.getSampleNo()) && fatherAuditedGene.getSampleNo().contains("-")) {
                                                        fatherAuditedGene.setSampleNo(fatherAuditedGene.getSampleNo().split("-")[1]);
                                                    }
                                                }

                                                mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), fatherAuditedGene);
                                                geneDataModelList.add(mapList);
                                            }

                                            //添加母亲基因分型
                                            if (motherAuditedGene != null) {
                                                if (StringUtils.isNotBlank(motherAuditedGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(motherAuditedGene.getSampleFlag())) {
                                                    if (StringUtils.isNotBlank(motherAuditedGene.getSampleNo()) && motherAuditedGene.getSampleNo().contains("-")) {
                                                        motherAuditedGene.setSampleNo(motherAuditedGene.getSampleNo().split("-")[1]);
                                                    }
                                                }

                                                mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), motherAuditedGene);
                                                geneDataModelList.add(mapList);
                                            }

                                            //添加孩子基因分型
                                            if (childAuditedGene != null) {
                                                if (StringUtils.isNotBlank(childAuditedGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(childAuditedGene.getSampleFlag())) {
                                                    if (StringUtils.isNotBlank(childAuditedGene.getSampleNo()) && childAuditedGene.getSampleNo().contains("-")) {
                                                        childAuditedGene.setSampleNo(childAuditedGene.getSampleNo().split("-")[1]);
                                                    }
                                                }

                                                mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), childAuditedGene);
                                                geneDataModelList.add(mapList);
                                            }

                                            //添加pi值
                                            if (ListUtils.isNotNullAndEmptyList(groupList.get(k))) {
                                                List<String> piList = new ArrayList<>();
                                                piList.add("PI值");
                                                for (String markerName : groupList.get(k)) {
                                                    String piStr = "";
                                                    if (marker.containsKey(markerName)) {
                                                        Double piDouble = marker.get(markerName);
                                                        if (!Double.isNaN(piDouble)) {
                                                            piStr = String.format("%.3f", piDouble);
                                                        }
                                                    }
                                                    mapList = new LinkedHashMap<>();
                                                    piList.add(piStr);
                                                }
                                                mapList.put("PI", piList);
                                                geneDataModelList.add(mapList);
                                            }

                                            //查询是否有共同的一个检材进行了多个比对
                                            for (int j = i + 1; j < twoRelativeResultList.size(); j++) {
                                                CompareRelativeResult result = twoRelativeResultList.get(j);
                                                if (result != null) {

                                                    String childInfo = null;
                                                    String fatherInfo = null;
                                                    String motherInfo = null;
                                                    List<MatchAuditedGene> childList = matchAuditedGeneService.selectListBySampleNo(result.getChildSampleNo());
                                                    MatchAuditedGene childGene = null;
                                                    if (ListUtils.isNotNullAndEmptyList(childList)) {
                                                        childGene = childList.get(0);
                                                        if (childGene != null) {
                                                            childInfo = childGene.getGeneInfo();
                                                        }
                                                    }

                                                    List<MatchAuditedGene> fatherList = matchAuditedGeneService.selectListBySampleNo(result.getFatherSampleNo());
                                                    MatchAuditedGene fatherGene = null;
                                                    if (ListUtils.isNotNullAndEmptyList(fatherList)) {
                                                        fatherGene = fatherList.get(0);
                                                        if (fatherGene != null) {
                                                            fatherInfo = fatherGene.getGeneInfo();
                                                        }
                                                    }

                                                    List<MatchAuditedGene> motherList = matchAuditedGeneService.selectListBySampleNo(result.getMotherSampleNo());
                                                    MatchAuditedGene motherGene = null;
                                                    if (ListUtils.isNotNullAndEmptyList(motherList)) {
                                                        motherGene = motherList.get(0);
                                                        if (motherAuditedGene != null) {
                                                            motherInfo = motherGene.getGeneInfo();
                                                        }
                                                    }
                                                    if (StringUtils.isNotBlank(fatherSampleNo) && StringUtils.isNotBlank(childSampleNo)) {
                                                        if (fatherSampleNo.equals(result.getFatherSampleNo()) || childSampleNo.equals(result.getFatherSampleNo())) {
                                                            //此组父亲是其他组父亲时或孩子为其他组父亲时
                                                            childList = matchAuditedGeneService.selectListBySampleNo(result.getChildSampleNo());
                                                            if (ListUtils.isNotNullAndEmptyList(childList)) {
                                                                childGene = childList.get(0);
                                                                if (childGene != null) {
                                                                    if (StringUtils.isNotBlank(childGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(childGene.getSampleFlag())) {
                                                                        if (StringUtils.isNotBlank(childGene.getSampleNo()) && childGene.getSampleNo().contains("-")) {
                                                                            childGene.setSampleNo(childGene.getSampleNo().split("-")[1]);
                                                                        }
                                                                    }

                                                                    mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), childGene);
                                                                    geneDataModelList.add(mapList);
                                                                }
                                                            }

                                                            if (StringUtils.isBlank(twoSampleNo)) {
                                                                if (!twoSampleNo.contains(result.getChildSampleNo())) {
                                                                    twoSampleNo += result.getChildSampleNo() + "、";
                                                                }
                                                                if (!twoSampleNo.contains(result.getFatherSampleNo())) {
                                                                    twoSampleNo += result.getFatherSampleNo() + "、";
                                                                }
                                                            }

                                                            resultMap = geneRelativeCompareUtil.relationCompare(fatherInfo, motherInfo, childInfo, result);
                                                            if (resultMap != null) {
                                                                parentageMatchResult = (ParentageMatchResult) resultMap.get("parentageMatchResult");
                                                                if (parentageMatchResult != null) {
                                                                    totalResult = parentageMatchResult.getResult();
                                                                    if (totalResult != null) {
                                                                        Map<String, Double> markerNew = totalResult.getPiOfMarker();

                                                                        if (ListUtils.isNotNullAndEmptyList(groupList.get(k))) {
                                                                            List<String> piList = new ArrayList<>();
                                                                            piList.add("PI值");
                                                                            for (String markerName : groupList.get(k)) {
                                                                                String piStr = "";
                                                                                if (markerNew.containsKey(markerName)) {
                                                                                    Double piDouble = markerNew.get(markerName);
                                                                                    if (!Double.isNaN(piDouble)) {
                                                                                        piStr = String.format("%.3f", piDouble);
                                                                                    }
                                                                                }
                                                                                mapList = new LinkedHashMap<>();
                                                                                piList.add(piStr);
                                                                            }
                                                                            mapList.put("PI", piList);
                                                                            geneDataModelList.add(mapList);
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            //如果合并了一组，跳出循环
                                                            break;
                                                        } else if (fatherSampleNo.equals(result.getChildSampleNo()) || childSampleNo.equals(result.getChildSampleNo())) {
                                                            //此组父亲是其他组子女时或孩子为其他组孩子时
                                                            fatherList = matchAuditedGeneService.selectListBySampleNo(result.getFatherSampleNo());
                                                            if (ListUtils.isNotNullAndEmptyList(fatherList)) {
                                                                fatherGene = fatherList.get(0);
                                                                if (fatherGene != null) {
                                                                    if (StringUtils.isNotBlank(fatherGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(fatherGene.getSampleFlag())) {
                                                                        if (StringUtils.isNotBlank(fatherGene.getSampleNo()) && fatherGene.getSampleNo().contains("-")) {
                                                                            fatherGene.setSampleNo(fatherGene.getSampleNo().split("-")[1]);
                                                                        }
                                                                    }

                                                                    mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), fatherGene);
                                                                    geneDataModelList.add(mapList);
                                                                }
                                                            }

                                                            if (StringUtils.isBlank(twoSampleNo)) {
                                                                if (!twoSampleNo.contains(result.getChildSampleNo())) {
                                                                    twoSampleNo += result.getChildSampleNo() + "、";
                                                                }
                                                                if (!twoSampleNo.contains(result.getFatherSampleNo())) {
                                                                    twoSampleNo += result.getFatherSampleNo() + "、";
                                                                }
                                                            }

                                                            resultMap = geneRelativeCompareUtil.relationCompare(fatherInfo, motherInfo, childInfo, result);
                                                            if (resultMap != null) {
                                                                parentageMatchResult = (ParentageMatchResult) resultMap.get("parentageMatchResult");
                                                                if (parentageMatchResult != null) {
                                                                    totalResult = parentageMatchResult.getResult();
                                                                    if (totalResult != null) {
                                                                        Map<String, Double> markerNew = totalResult.getPiOfMarker();

                                                                        if (ListUtils.isNotNullAndEmptyList(groupList.get(k))) {
                                                                            List<String> piList = new ArrayList<>();
                                                                            piList.add("PI值");
                                                                            for (String markerName : groupList.get(k)) {
                                                                                String piStr = "";
                                                                                if (markerNew.containsKey(markerName)) {
                                                                                    Double piDouble = markerNew.get(markerName);
                                                                                    if (!Double.isNaN(piDouble)) {
                                                                                        piStr = String.format("%.3f", piDouble);
                                                                                    }
                                                                                }
                                                                                mapList = new LinkedHashMap<>();
                                                                                piList.add(piStr);
                                                                            }
                                                                            mapList.put("PI", piList);
                                                                            geneDataModelList.add(mapList);
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            //如果合并了一组，跳出循环
                                                            break;
                                                        }
                                                    } else if (StringUtils.isNotBlank(motherSampleNo) && StringUtils.isNotBlank(childSampleNo)) {
                                                        if (motherSampleNo.equals(result.getMotherSampleNo()) || childSampleNo.equals(result.getMotherSampleNo())) {
                                                            //此组母亲是其他组母亲时或孩子为其他组母亲时
                                                            childList = matchAuditedGeneService.selectListBySampleNo(result.getChildSampleNo());
                                                            if (ListUtils.isNotNullAndEmptyList(childList)) {
                                                                childGene = childList.get(0);
                                                                if (childGene != null) {
                                                                    if (StringUtils.isNotBlank(childGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(childGene.getSampleFlag())) {
                                                                        if (StringUtils.isNotBlank(childGene.getSampleNo()) && childGene.getSampleNo().contains("-")) {
                                                                            childGene.setSampleNo(childGene.getSampleNo().split("-")[1]);
                                                                        }
                                                                    }

                                                                    mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), childGene);
                                                                    geneDataModelList.add(mapList);
                                                                }
                                                            }

                                                            if (StringUtils.isBlank(twoSampleNo)) {
                                                                if (!twoSampleNo.contains(result.getChildSampleNo())) {
                                                                    twoSampleNo += result.getChildSampleNo() + "、";
                                                                }
                                                                if (!twoSampleNo.contains(result.getMotherSampleNo())) {
                                                                    twoSampleNo += result.getMotherSampleNo() + "、";
                                                                }
                                                            }

                                                            resultMap = geneRelativeCompareUtil.relationCompare(fatherInfo, motherInfo, childInfo, result);
                                                            if (resultMap != null) {
                                                                parentageMatchResult = (ParentageMatchResult) resultMap.get("parentageMatchResult");
                                                                if (parentageMatchResult != null) {
                                                                    totalResult = parentageMatchResult.getResult();
                                                                    if (totalResult != null) {
                                                                        Map<String, Double> markerNew = totalResult.getPiOfMarker();

                                                                        if (ListUtils.isNotNullAndEmptyList(groupList.get(k))) {
                                                                            List<String> piList = new ArrayList<>();
                                                                            piList.add("PI值");
                                                                            for (String markerName : groupList.get(k)) {
                                                                                String piStr = "";
                                                                                if (markerNew.containsKey(markerName)) {
                                                                                    Double piDouble = markerNew.get(markerName);
                                                                                    if (!Double.isNaN(piDouble)) {
                                                                                        piStr = String.format("%.3f", piDouble);
                                                                                    }
                                                                                }
                                                                                mapList = new LinkedHashMap<>();
                                                                                piList.add(piStr);
                                                                            }
                                                                            mapList.put("PI", piList);
                                                                            geneDataModelList.add(mapList);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            //如果合并了一组，跳出循环
                                                            break;
                                                        } else if (motherSampleNo.equals(result.getChildSampleNo()) || childSampleNo.equals(result.getChildSampleNo())) {
                                                            //此组母亲是其他组子女时或孩子为其他组孩子时
                                                            motherList = matchAuditedGeneService.selectListBySampleNo(result.getMotherSampleNo());
                                                            if (ListUtils.isNotNullAndEmptyList(motherList)) {
                                                                motherGene = motherList.get(0);
                                                                if (motherGene != null) {
                                                                    if (StringUtils.isNotBlank(motherGene.getSampleFlag()) && Constants.SAMPLE_FLAG_0.equals(motherGene.getSampleFlag())) {
                                                                        if (StringUtils.isNotBlank(motherGene.getSampleNo()) && motherGene.getSampleNo().contains("-")) {
                                                                            motherGene.setSampleNo(motherGene.getSampleNo().split("-")[1]);
                                                                        }
                                                                    }

                                                                    mapList = geneSameCompareUtil.analysisGeneList(groupList.get(k), motherGene);
                                                                    geneDataModelList.add(mapList);
                                                                }
                                                            }

                                                            if (StringUtils.isBlank(twoSampleNo)) {
                                                                if (!twoSampleNo.contains(result.getChildSampleNo())) {
                                                                    twoSampleNo += result.getChildSampleNo() + "、";
                                                                }
                                                                if (!twoSampleNo.contains(result.getMotherSampleNo())) {
                                                                    twoSampleNo += result.getMotherSampleNo() + "、";
                                                                }
                                                            }

                                                            resultMap = geneRelativeCompareUtil.relationCompare(fatherInfo, motherInfo, childInfo, result);
                                                            if (resultMap != null) {
                                                                parentageMatchResult = (ParentageMatchResult) resultMap.get("parentageMatchResult");
                                                                if (parentageMatchResult != null) {
                                                                    totalResult = parentageMatchResult.getResult();
                                                                    if (totalResult != null) {
                                                                        Map<String, Double> markerNew = totalResult.getPiOfMarker();

                                                                        if (ListUtils.isNotNullAndEmptyList(groupList.get(k))) {
                                                                            List<String> piList = new ArrayList<>();
                                                                            piList.add("PI值");
                                                                            for (String markerName : groupList.get(k)) {
                                                                                String piStr = "";
                                                                                if (markerNew.containsKey(markerName)) {
                                                                                    Double piDouble = markerNew.get(markerName);
                                                                                    if (!Double.isNaN(piDouble)) {
                                                                                        piStr = String.format("%.3f", piDouble);
                                                                                    }
                                                                                }
                                                                                mapList = new LinkedHashMap<>();
                                                                                piList.add(piStr);
                                                                            }
                                                                            mapList.put("PI", piList);
                                                                            geneDataModelList.add(mapList);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            //如果合并了一组，跳出循环
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            //判断是否存在基因分型
                                            if (ListUtils.isNotNullAndEmptyList(geneDataModelList)) {
                                                sampleGeneDataModel.setGeneDataModelList(geneDataModelList);
                                                sampleGeneDataModel.setMarkerList(groupList.get(k));
                                                List<String> markerList1 = sampleGeneDataModel.getMarkerList();
                                                for (int a = 0; a < markerList1.size(); a++) {
                                                    if (CommonUtils.contain(markerList1.get(a), "amel")) {
                                                        markerList1.set(a, "Amel");
                                                    }
                                                }
                                                sampleGeneDataModel.setMarkerList(markerList1);
                                                relativeGeneDataModelList.add(sampleGeneDataModel);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        params.put("relativeResultList", relativeResultList);
        params.put("relativeGeneDataModelList", relativeGeneDataModelList);

        params.put("markerList", markerList);

        List<Map<String, Object>> sameResultList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(distinctSampleResultList) && ListUtils.isNotNullAndEmptyList(compareSameResultList)) {
            for (CompareSameResult sameResult : distinctSampleResultList) {
                if (sameResult == null) {
                    continue;
                }

                Map<String, Object> resultMap = new LinkedHashMap<>();
                String sampleNoStr = "";
                String referenceId = "";
                String referenceName = "";
                String probability1 = "";
                String probability2 = "";
                Integer markerCount = 15;
                String fistMarker = "D3S1358";
                for (CompareSameResult csr : compareSameResultList) {
                    if (!sameResult.getReferenceId().equals(csr.getReferenceId())) {
                        continue;
                    }

                    if (StringUtils.isNotBlank(csr.getReferenceId()) && csr.getReferenceId().equals(csr.getSampleNo())) {
                        referenceId = csr.getReferenceId();
                        referenceName = csr.getSampleName();
                        /*if (Constants.SAMPLE_FLAG_1.equals(csr.getSampleFlag())) {
                            LimsPersonInfo personInfo = limsPersonInfoService.selectPersonInfoBySampleNo(referenceId);
                            if (personInfo != null) {
                                referenceName = personInfo.getPersonName();
                            }
                        }*/
                        continue;
                    }

                    if (Constants.SAMPLE_FLAG_0.equals(csr.getSampleFlag())) {
                        String[] sampleNoArr = csr.getSampleNo().split("-");
                        if (sampleNoArr.length > 1) {
                            sampleNoStr += sampleNoArr[1] + "、";
                        } else {
                            sampleNoStr += sampleNoArr[0] + "、";
                        }
                    }
                    if (StringUtils.isNotBlank(csr.getCompareTotalProbability())) {
                        String[] probabilityArr = csr.getCompareTotalProbability().split("E");
                        if (probabilityArr.length > 1) {
                            probability1 = probabilityArr[0];
                            probability2 = probabilityArr[1];
                        }
                    }
                }

                if (StringUtils.isNotBlank(sampleNoStr)) {
                    sampleNoStr = sampleNoStr.substring(0, sampleNoStr.length() - 1);
                }

                List<MatchAuditedGene> auditedGenes = matchAuditedGeneService.selectListBySampleNo(referenceId);
                if (ListUtils.isNotNullAndEmptyList(auditedGenes)) {
                    MatchAuditedGene auditedGene = auditedGenes.get(0);
                    if (auditedGene != null) {
                        String geneInfo = auditedGene.getGeneInfo();
                        if (CommonUtils.contain(auditedGene.getPanelName(), "identifiler")) {
                            geneInfo = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, auditedGene.getGeneInfo());
                        }
                        markerList = geneSameCompareUtil.getNewMarker(geneInfo);
                        if (ListUtils.isNotNullAndEmptyList(markerList)) {
                            markerCount = markerList.size();

                            for (String marker : markerList) {
                                fistMarker = marker;
                                break;
                            }
                        }
                    }
                }
                resultMap.put("fistMarker", fistMarker);
                resultMap.put("markerCount", markerCount);
                resultMap.put("referenceName", referenceName);
                resultMap.put("sampleNoStr", sampleNoStr);
                resultMap.put("probability1", probability1);
                resultMap.put("probability2", probability2);

                sameResultList.add(resultMap);
            }
        }

        params.put("sameResultList", sameResultList);

        //混合检材分组
        List<String> compareSameResults2 = new ArrayList<>();
        List<CaseCompareResultGroup> mixGroupList = new ArrayList<>();
        List<CaseCompareResultGroup> newMixGroupList2 = new ArrayList<>();
        try {
            mixGroupList = geneMixCompareUtil.mixDoCompare(mixAuditedGeneList, auditedGeneList, 0);


            for (CompareSameResult compareSameResult : compareSameResultList) {
                if (!compareSameResult.getSampleNo().equals(compareSameResult.getReferenceId())) {
                    compareSameResults2.add(compareSameResult.getSampleNo());
                }
            }
            for (CaseCompareResultGroup caseCompareResultGroup : mixGroupList) {
                List<MatchAuditedGene> mixMatchedSampleList = caseCompareResultGroup.getMixMatchedSampleList();
                int i = 0;
                for (MatchAuditedGene auditedGene : mixMatchedSampleList) {
                    if (compareSameResults2.contains(auditedGene.getSampleNo())) {
                        i = i + 1;
                    }
                }
                if (i == 0) {
                    newMixGroupList2.add(caseCompareResultGroup);
                }
            }
        } catch (Exception e) {
            logger.error("混合检材分组报错！" + e.getMessage());
            e.getStackTrace();
        }

        List<Map<String, Object>> mixResultList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(newMixGroupList2)) {
            for (CaseCompareResultGroup resultGroup : newMixGroupList2) {
                if (resultGroup != null) {
                    List<MatchAuditedGene> mixMatchedSampleList = resultGroup.getMixMatchedSampleList();
                    if (ListUtils.isNotNullAndEmptyList(mixMatchedSampleList)) {
                        Map<String, Object> mixMap = new LinkedHashMap<>();

                        String mixSampleName = "";
                        for (int i = 1; i < mixMatchedSampleList.size(); i++) {
                            MatchAuditedGene auditedGene = mixMatchedSampleList.get(i);
                            if (auditedGene != null) {
                                mixSampleName += auditedGene.getSampleName() + "、";
                            }
                        }
                        for (int i = 0; i < matchAuditedGeneList.size(); i++) {
                            MatchAuditedGene mixAuditGene = mixMatchedSampleList.get(i);
                            if (mixAuditGene != null) {
                                if (Constants.SAMPLE_FLAG_0.equals(mixAuditGene.getSampleFlag())) {
                                    if (StringUtils.isNotBlank(mixAuditGene.getSampleNo()) && mixAuditGene.getSampleNo().contains("-")) {
                                        mixAuditGene.setSampleNo(mixAuditGene.getSampleNo().split("-")[1]);
                                    }
                                }

                                String geneInfo = mixAuditGene.getGeneInfo();
                                if (CommonUtils.contain(mixAuditGene.getPanelName(), "identifiler")) {
                                    geneInfo = geneSameCompareUtil.getGeneInfoList(Constants.IdentifilerPlusList, mixAuditGene.getGeneInfo());
                                }
                                markerList = geneSameCompareUtil.getNewMarker(geneInfo);
                                mixMap.put("mixSampleNo", mixAuditGene.getSampleNo());
                                String mixCount = DataFormat.NumberToChn(mixMatchedSampleList.size() - 1);
                                if (StringUtils.isNotBlank(mixCount) && mixCount.equals("二")) {
                                    mixCount = "两";
                                }
                                mixMap.put("mixCount", mixCount);
                                Integer markerCount = 15;
                                String fistMarker = "D3S1358";
                                if (ListUtils.isNotNullAndEmptyList(markerList)) {
                                    markerCount = markerList.size();
                                    for (String marker : markerList) {
                                        fistMarker = marker;
                                        break;
                                    }
                                }
                                mixMap.put("fistMarker", fistMarker);
                                mixMap.put("markerCount", markerCount);
                                if (StringUtils.isNotBlank(mixSampleName)) {
                                    mixSampleName = mixSampleName.substring(0, mixSampleName.length() - 1);
                                }
                                mixMap.put("mixSampleName", mixSampleName);

                                mixResultList.add(mixMap);

                                //只有基因座数量至少大于13个
                                if (markerList.size() > 13) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        params.put("mixResultList", mixResultList);

        return params;
    }

    /**
     * 判断是否只有亲缘比对结果
     *
     * @param auditedGeneList
     * @param compareRelativeResultList
     * @return
     */
    public boolean isOnlyRelative(List<MatchAuditedGene> auditedGeneList, List<CompareRelativeResult> compareRelativeResultList) {
        boolean flag = true;
        if (ListUtils.isNotNullAndEmptyList(auditedGeneList) && ListUtils.isNotNullAndEmptyList(compareRelativeResultList)) {
            String relativeSample = "";
            for (CompareRelativeResult relativeResult : compareRelativeResultList) {
                if (StringUtils.isNotBlank(relativeResult.getFatherSampleNo()) && !relativeResult.getFatherSampleNo().equals(relativeSample)) {
                    relativeSample += relativeResult.getFatherSampleNo() + "、";
                }
                if (StringUtils.isNotBlank(relativeResult.getMotherSampleNo()) && !relativeResult.getMotherSampleNo().equals(relativeSample)) {
                    relativeSample += relativeResult.getMotherSampleNo() + "、";
                }
                if (StringUtils.isNotBlank(relativeResult.getChildSampleNo()) && !relativeResult.getChildSampleNo().equals(relativeSample)) {
                    relativeSample += relativeResult.getChildSampleNo() + "、";
                }
            }

            //根据所有亲缘比对检材跟审核通过检材对比，如果亲缘存在不包含的检材，表示不止有亲缘
            if (StringUtils.isNotBlank(relativeSample)) {
                for (MatchAuditedGene auditedGene : auditedGeneList) {
                    if (StringUtils.isNotBlank(auditedGene.getSampleNo()) && !relativeSample.contains(auditedGene.getSampleNo())) {
                        //存在其他检材
                        flag = false;
                        break;
                    }
                }
            }
        }

        return flag;
    }

    /**
     * 生成扩增实验记录表
     *
     * @param
     * @return
     */
    public void pcrRecordBook(HttpServletRequest request, HttpSession session, HttpServletResponse response, String pcrId) {

    }

    /**
     * 检材进行排序，补送收的人员检材放在主案件之后
     *
     * @param sampleInfoDnaList
     * @return
     */
    public List<LimsSampleInfoDna> orderBySampleInfoList(List<LimsSampleInfoDna> sampleInfoDnaList) {
        List<LimsSampleInfoDna> limsSampleInfoDnaList = new ArrayList<>();
        for (LimsSampleInfoDna sampleInfo : sampleInfoDnaList) {
            if (Constants.SAMPLE_FLAG_0.equals(sampleInfo.getSampleFlag()) && StringUtils.isNotBlank(sampleInfo.getSampleNo())) {
                limsSampleInfoDnaList.add(sampleInfo);
            }
        }
        List<LimsSampleInfoDna> sampleFlag1List = new ArrayList<>();
        for (LimsSampleInfoDna sampleInfo : sampleInfoDnaList) {
            if (Constants.SAMPLE_FLAG_1.equals(sampleInfo.getSampleFlag()) && StringUtils.isNotBlank(sampleInfo.getSampleNo())) {
                sampleFlag1List.add(sampleInfo);
            }
        }

        if (ListUtils.isNotNullAndEmptyList(sampleFlag1List)) {
            sampleFlag1List = sortListInt(sampleFlag1List);
            limsSampleInfoDnaList.addAll(sampleFlag1List);
        }

        return limsSampleInfoDnaList;
    }

    /**
     * 人员检材进行排序，补送排在主案件人员之后
     *
     * @param listMap
     * @return
     */
    private List<LimsSampleInfoDna> sortListInt(List<LimsSampleInfoDna> listMap) {
        Collections.sort(listMap, new Comparator<LimsSampleInfoDna>() {
            @Override
            public int compare(LimsSampleInfoDna o1, LimsSampleInfoDna o2) {
                String sampleNo1 = o1.getSampleNo().split("-")[1];
                String sampleNo2 = o2.getSampleNo().split("-")[1];

                return sampleNo1.toString().compareTo(sampleNo2.toString());
            }
        });
        return listMap;
    }

    /**
     * 检材进行排序，补送收的人员检材基因分型放在主案件之后
     *
     * @param sampleGeneList
     * @return
     */
    public List<MatchAuditedGene> orderBySampleGeneList(List<MatchAuditedGene> sampleGeneList) {
        List<MatchAuditedGene> matchAuditedGeneList = new ArrayList<>();
        for (MatchAuditedGene auditedGene : sampleGeneList) {
            if (Constants.SAMPLE_FLAG_0.equals(auditedGene.getSampleFlag()) && StringUtils.isNotBlank(auditedGene.getSampleNo())) {
                matchAuditedGeneList.add(auditedGene);
            }
        }
        List<MatchAuditedGene> sampleFlag1List = new ArrayList<>();
        for (MatchAuditedGene auditedGene : sampleGeneList) {
            if (Constants.SAMPLE_FLAG_1.equals(auditedGene.getSampleFlag()) && StringUtils.isNotBlank(auditedGene.getSampleNo())) {
                sampleFlag1List.add(auditedGene);
            }
        }

        if (ListUtils.isNotNullAndEmptyList(sampleFlag1List)) {
            sampleFlag1List = sortGeneListInt(sampleFlag1List);
            matchAuditedGeneList.addAll(sampleFlag1List);
        }

        return matchAuditedGeneList;
    }

    /**
     * 人员检材基因分型进行排序，补送排在主案件人员之后
     *
     * @param listMap
     * @return
     */
    private List<MatchAuditedGene> sortGeneListInt(List<MatchAuditedGene> listMap) {
        Collections.sort(listMap, new Comparator<MatchAuditedGene>() {
            @Override
            public int compare(MatchAuditedGene o1, MatchAuditedGene o2) {
                String sampleNo1 = o1.getSampleNo().split("-")[1];
                String sampleNo2 = o2.getSampleNo().split("-")[1];

                return sampleNo1.toString().compareTo(sampleNo2.toString());
            }
        });
        return listMap;
    }

    /**
     * 基因型排序，补送收的人员检材基因分型放在主案件之后
     *
     * @param sameResultList
     * @return
     */
    public List<CompareSameResult> orderBySameResultList(List<CompareSameResult> sameResultList) {
        List<CompareSameResult> compareSameResultList = new ArrayList<>();
        for (CompareSameResult sameResult : sameResultList) {
            if (Constants.SAMPLE_FLAG_0.equals(sameResult.getSampleFlag()) && StringUtils.isNotBlank(sameResult.getSampleNo())) {
                compareSameResultList.add(sameResult);
            }
        }
        List<CompareSameResult> sampleFlag1List = new ArrayList<>();
        for (CompareSameResult sameResult : sameResultList) {
            if (Constants.SAMPLE_FLAG_1.equals(sameResult.getSampleFlag()) && StringUtils.isNotBlank(sameResult.getSampleNo())) {
                sampleFlag1List.add(sameResult);
            }
        }

        if (ListUtils.isNotNullAndEmptyList(sampleFlag1List)) {
            sampleFlag1List = sortSameResultListInt(sampleFlag1List);
            compareSameResultList.addAll(sampleFlag1List);
        }

        return compareSameResultList;
    }

    /**
     * 人员检材基因分型进行排序，补送排在主案件人员之后
     *
     * @param listMap
     * @return
     */
    private List<CompareSameResult> sortSameResultListInt(List<CompareSameResult> listMap) {
        Collections.sort(listMap, new Comparator<CompareSameResult>() {
            @Override
            public int compare(CompareSameResult o1, CompareSameResult o2) {
                String sampleNo1 = o1.getSampleNo().split("-")[1];
                String sampleNo2 = o2.getSampleNo().split("-")[1];

                return sampleNo1.toString().compareTo(sampleNo2.toString());
            }
        });
        return listMap;
    }

    /**
     * 导出24孔样本表
     *
     * @param request
     * @param response
     * @param sampleInfoVoList
     * @param sampleTable
     */
    public void exportSampleTable(HttpServletRequest request, HttpServletResponse response,
                                  List<SampleInfoVo> sampleInfoVoList, SampleTable sampleTable) {

        String templateFilePath = request.getServletContext().getRealPath("templates/sampleTable.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            if (sampleTable != null) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(sampleTable.getBoardNo());

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(sampleTable.getCreatePerson());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(DateUtils.dateToString(sampleTable.getCreateDatetime(), DateUtils.MIN));
            }

            List<SampleInfoModel> sampleInfoModelList = getSampleInfoGroup(sampleInfoVoList, 6);
            createRowCell(sampleInfoModelList, row, cell, sheet, 4, 0, workbook, Constants.SAMPLE_TABLE);

            String fileName = "导出24孔样本表_" + sampleTable.getBoardNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
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
    }

    public static List<SampleInfoModel> getSampleInfoGroup(List<SampleInfoVo> sampleInfoVoList, int count) {

        List<SampleInfoModel> resultList = null;
        if (ListUtils.isNotNullAndEmptyList(sampleInfoVoList)) {
            resultList = new ArrayList<>();
            List<SampleInfoVo> strList = new ArrayList<>();

            for (int i = 0; i < sampleInfoVoList.size(); i++) {
                strList.add(sampleInfoVoList.get(i));

                if ((i + 1) % count == 0) {
                    SampleInfoModel sampleInfoModel = new SampleInfoModel();
                    sampleInfoModel.setSampleInfoVoList(strList);
                    resultList.add(sampleInfoModel);
                    strList = new ArrayList<>();
                }
            }

            if (ListUtils.isNotNullAndEmptyList(strList)) {
                SampleInfoModel sampleInfoModel = new SampleInfoModel();
                sampleInfoModel.setSampleInfoVoList(strList);
                resultList.add(sampleInfoModel);
            }
        }

        return resultList;
    }

    private void createRowCell(List<SampleInfoModel> sampleInfoModelList, HSSFRow row, HSSFCell cell,
                               HSSFSheet sheet, int startRow, int idx, HSSFWorkbook workbook, String table) {
        if (ListUtils.isNotNullAndEmptyList(sampleInfoModelList)) {
            HSSFCellStyle cellStyle = null;
            for (SampleInfoModel sampleInfoModel : sampleInfoModelList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }
                for (int i = 0; i < sampleInfoModel.getSampleInfoVoList().size(); i++) {
                    int count = i + 2;
                    cell = row.getCell(count);
                    if (cell == null) {
                        cell = row.createCell(count);
                    }
                    SampleInfoVo sample = sampleInfoModel.getSampleInfoVoList().get(i);
                    if (sample != null) {
                        if (Constants.SAMPLE_TABLE.equals(table) || Constants.SAMPLE_EXTRACT.equals(table)
                                || Constants.SAMPLE_PCR.equals(table) || Constants.SAMPLE_SY.equals(table)) {
                            if (StringUtils.isNotBlank((sample.getElutionName()))) {
                                int elution = Integer.parseInt(sample.getElutionName());
                                //大于50是常量，小于50是微量
                                if (elution > Constants.TRACE_CONSTANT_LIMIT) {
                                    cellStyle = getCellStyle(workbook, Constants.COLOR_LIGHT_BLUE);
                                } else {
                                    cellStyle = getCellStyle(workbook, Constants.COLOR_LIGHT_CORNFLOWER_BLUE);
                                }
                                cell.setCellStyle(cellStyle);
                            }
                        }
                        cell.setCellValue(sample.getEntity().getSampleNo());
                    }
                }

                idx++;
            }
        }
    }

    /**
     * 设置背景色
     *
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, String color) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (color.equals(Constants.COLOR_LIGHT_CORNFLOWER_BLUE)) {
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        }else if (color.equals(Constants.COLOR_LIGHT_BLUE)) {
            style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
        }
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);

        return style;
    }

    /**
     * 导出检材信息
     *
     * @param request
     * @param response
     * @param sampleInfoVoList
     */
    public void exportSampleInfoRecord(HttpServletRequest request, HttpServletResponse response,
                                       List<SampleInfoVo> sampleInfoVoList) {

        String templateFilePath = request.getServletContext().getRealPath("templates/sampleInfoRecord.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle cellStyle = createCellStyle(workbook);

            int startRow = 2;
            int idx = 0;
            String boardNo = "";
            if (ListUtils.isNotNullAndEmptyList(sampleInfoVoList)) {
                SampleInfo sampleInfo = sampleInfoVoList.get(0).getEntity();
                if (sampleInfo != null) {
                    boardNo = sampleInfo.getBoardNo();

                    row = sheet.getRow(startRow + idx);
                    if (row == null) {
                        row = sheet.createRow(startRow + idx);
                    }

                    cell = row.getCell(2);
                    if (cell == null) {
                        cell = row.createCell(2);
                    }
                    cell.setCellValue(sampleInfo.getCreatePerson());
                }

                startRow = 4;
                idx = 0;
                for (SampleInfoVo sampleInfoVo : sampleInfoVoList) {
                    row = sheet.getRow(startRow + idx);
                    if (row == null) {
                        row = sheet.createRow(startRow + idx);
                    }

                    cell = row.getCell(1);
                    if (cell == null) {
                        cell = row.createCell(1);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getEntity().getBoardNo());

                    cell = row.getCell(2);
                    if (cell == null) {
                        cell = row.createCell(2);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getEntity().getSampleNo());

                    cell = row.getCell(3);
                    if (cell == null) {
                        cell = row.createCell(3);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getEntity().getExtractPlateLocation());

                    cell = row.getCell(4);
                    if (cell == null) {
                        cell = row.createCell(4);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getPreExperimentalMethodName());

                    cell = row.getCell(5);
                    if (cell == null) {
                        cell = row.createCell(5);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getConfirmatoryMethodName());

                    cell = row.getCell(6);
                    if (cell == null) {
                        cell = row.createCell(6);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getSampleTransferMethodName());

                    cell = row.getCell(7);
                    if (cell == null) {
                        cell = row.createCell(7);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getElutionName());

                    cell = row.getCell(8);
                    if (cell == null) {
                        cell = row.createCell(8);
                    }
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(sampleInfoVo.getSamplePropertyName());

                    idx++;
                }
            }

            String fileName = "导出检材信息_" + boardNo + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
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
    }

    public HSSFCellStyle createCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        return cellStyle;
    }

    /**
     * 导入4孔样本表
     *
     * @param request
     * @param file
     */
    public List<SampleInfoModel> importSampleTable(HttpServletRequest request, MultipartFile file) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<SampleInfoModel> sampleInfoModelList = null;
        String cellData = null;
        try {
            wb = readExcel(file);
            if (wb != null) {
                //用来存放表中数据
                sampleInfoModelList = new ArrayList<>();
                //获取第一个sheet
                sheet = wb.getSheetAt(0);
                //获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();

                SampleInfoModel sampleInfoModel = new SampleInfoModel();

                row = sheet.getRow(2);
                SampleTable sampleTable = new SampleTable();

                //板号
                cellData = (String) getCellFormatValue(row.getCell(2));
                sampleTable.setBoardNo(cellData);

                //创建者
                cellData = (String) getCellFormatValue(row.getCell(4));
                sampleTable.setCreatePerson(cellData);

                //创建者
                cellData = (String) getCellFormatValue(row.getCell(6));
                if (StringUtils.isBlank(cellData)) {
                    sampleTable.setCreateDatetime(new Date());
                } else {
                    sampleTable.setCreateDatetime(DateUtils.stringToDate(cellData, DateUtils.MIN));
                }
                sampleInfoModel.setSampleTable(sampleTable);

                //获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                List<SampleInfo> sampleInfoList = new ArrayList<>();
                int count = 0;
                for (int i = 4; i < 8; i++) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 2; j < 8; j++) {
                            cellData = (String) getCellFormatValue(row.getCell(j));
                            if (StringUtils.isNotBlank(cellData)) {
                                SampleInfo sampleInfo = new SampleInfo();
                                sampleInfo.setSampleLocationSort(count + 1);
                                sampleInfo.setSamplePlateLocation(Constants.SYTABLE_POSTION_ARR_VER[count]);
                                sampleInfo.setSampleNo(cellData);

                                sampleInfoList.add(sampleInfo);
                            }
                            count++;
                        }
                    } else {
                        break;
                    }
                }
                sampleInfoModel.setSampleInfoList(sampleInfoList);
                sampleInfoModelList.add(sampleInfoModel);
            }
            return sampleInfoModelList;
        } catch (Exception ex) {
            logger.error("导入Excel错误！", ex);
            return null;
        }
    }

    //读取excel
    public static Workbook readExcel(MultipartFile file) {
        String filePath = file.getOriginalFilename();
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = file.getInputStream();
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    cellValue = DataFormat.convertDoubleToString(cell.getNumericCellValue());
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 导出csv文件
     *
     * @param request
     * @param response
     * @param sampleInfoVoList
     * @param extractPlate
     */
    public void exportCSVFile(HttpServletRequest request, HttpServletResponse response,
                              List<SampleInfoVo> sampleInfoVoList, ExtractPlate extractPlate) {
        List<SampleInfoModel> sampleInfoModelList = getSampleInfoGroup(sampleInfoVoList, 12);
        String fileName = "导出CSV文件-" + extractPlate.getBoardNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".csv";

        StringBuffer buffer = new StringBuffer();
        BufferedOutputStream bos = null;
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));

            bos = new BufferedOutputStream(response.getOutputStream());

            // 写入文件内容
            buffer.append("Contents of Purification plate(Sequential ID = 5),,,,,,,,,,,," + "\n");
            buffer.append(",,,,,,,,,,,," + "\n");
            buffer.append("Volume,,,,,,,,,,,," + "\n");
            buffer.append("SampleID,,,,,,,,,,,," + "\n");
            buffer.append(",,,,,,,,,,,," + "\n");
            buffer.append(",1,2,3,4,5,6,7,8,9,10,11,12" + "\n");

            if (ListUtils.isNotNullAndEmptyList(sampleInfoModelList)) {
                for (int i = 0; i < sampleInfoModelList.size(); i++) {
                    SampleInfoModel sampleInfoModel = sampleInfoModelList.get(i);
                    if (i == 0) {
                        sampleInfoModel.setParameter("A,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 1) {
                        sampleInfoModel.setParameter("B,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 2) {
                        sampleInfoModel.setParameter("C,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 3) {
                        sampleInfoModel.setParameter("D,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 4) {
                        sampleInfoModel.setParameter("E,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 5) {
                        sampleInfoModel.setParameter("F,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 6) {
                        sampleInfoModel.setParameter("G,270,270,270,270,270,270,270,270,270,270,0,0");
                    } else if (i == 7) {
                        sampleInfoModel.setParameter("H,270,270,270,270,270,270,270,270,270,270,0,0");
                    }
                }
            }

            for (SampleInfoModel sampleInfoModel : sampleInfoModelList) {
                buffer.append(sampleInfoModel.getParameter() + "\n");
                if (ListUtils.isNullOrEmptyList(sampleInfoModel.getSampleInfoVoList())) {
                    buffer.append(",,,,,,,,,,,," + "\n");
                } else {
                    String str = ",";
                    for (SampleInfoVo sampleInfoVo : sampleInfoModel.getSampleInfoVoList()) {
                        str += "\t" + sampleInfoVo.getEntity().getSampleNo() + ",";
                    }
                    if (StringUtils.isNotBlank(str)) {
                        str = str.substring(0, str.length() - 1);
                    }
                    buffer.append(str + "\n");
                }
            }
            bos.write(buffer.toString().getBytes("UTF-8"));
            bos.flush();
        } catch (Exception e) {
            logger.error("生成CSV错误！", e);
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * 导出上样文件
     *
     * @param request
     * @param response
     * @param sampleInfoVoList
     * @param extractPlate
     */
    public void exportSampleFile(HttpServletRequest request, HttpServletResponse response,
                                 List<SampleInfoVo> sampleInfoVoList, ExtractPlate extractPlate) {

        StringBuffer buffer = new StringBuffer();
        BufferedOutputStream bos = null;
        try {
            String fileName = "导出上样文件_" + extractPlate.getBoardNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".txt";

            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));

            bos = new BufferedOutputStream(response.getOutputStream());

            buffer.append("3500 Plate Layout File Version 1.0" + "\n" + "\n");
            buffer.append("Plate Name\tApplication Type\tCapillary Length (cm)\tPolymer\tNumber of Wells\tOwner Name\tBarcode Number\tComments" + "\n");
            buffer.append((StringUtils.isBlank(extractPlate.getBoardNo()) ? "" : extractPlate.getBoardNo()) + "\t" + "HID" + "\t" + "36" + "\t" + "POP4" + "\t" + "96" + "\n" + "\n");
            buffer.append("Well\tSample Name\tAssay\tFile Name Convention\tResults Group\tSample Type\t");
            buffer.append("User Defined Field 1\tUser Defined Field 2\tUser Defined Field 3\tUser Defined Field 4\tUser Defined Field 5\tComments" + "\n");

            if (ListUtils.isNotNullAndEmptyList(sampleInfoVoList)) {
                for (String positionStr : Constants.SYTABLE_POSTION_ARR_VER) {
                    for (SampleInfoVo sampleInfoVo : sampleInfoVoList) {
                        if (positionStr.equals(sampleInfoVo.getEntity().getExtractPlateLocation())) {
                            buffer.append(sampleInfoVo.getEntity().getExtractPlateLocation()
                                    + "\t" + sampleInfoVo.getEntity().getSampleNo()
                                    + "\t" + "HID" + "\t" + "HID" + "\t"
                                    + "HID" + "\t" + "Sample" + "\t" + "" + "\t" + "" + "\t"
                                    + "" + "\n");
                            break;
                        }
                    }
                }
            }

            bos.write(buffer.toString().getBytes("UTF-8"));
            bos.flush();
        } catch (Exception ex) {
            logger.error("生成上样表错误！", ex);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * 导出提取样本表
     *
     * @param request
     * @param response
     * @param sampleInfoVoList
     * @param extractPlate
     */
    public void exportExtractSampleTable(HttpServletRequest request, HttpServletResponse response,
                                         List<SampleInfoVo> sampleInfoVoList, ExtractPlate extractPlate) {

        String templateFilePath = request.getServletContext().getRealPath("templates/extractSampleTable.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            int startRow = 2;
            int idx = 0;
            if (extractPlate != null) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(extractPlate.getBoardNo());

                cell = row.getCell(9);
                if (cell == null) {
                    cell = row.createCell(9);
                }
                cell.setCellValue(extractPlate.getCreatePerson());

                idx++;
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }

                cell.setCellValue(extractPlate.getExtractMethod());
                cell = row.getCell(9);
                if (cell == null) {
                    cell = row.createCell(9);
                }
                cell.setCellValue(DateUtils.dateToString(extractPlate.getCreateDatetime(), DateUtils.MIN));
            }
            List<SampleInfoModel> sampleInfoModelList = getSampleInfoGroup(sampleInfoVoList, 12);
            createRowCell(sampleInfoModelList, row, cell, sheet, 5, 0, workbook, Constants.SAMPLE_EXTRACT);

            String fileName = "导出DNA提取样本表_" + extractPlate.getBoardNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
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
    }

    /**
     * 导出提取样本记录表
     *
     * @param request
     * @param response
     * @param sampleInfoVoList
     * @param extractPlate
     */
    public void exportExtractSampleRecord(HttpServletRequest request, HttpServletResponse response,
                                          List<SampleInfoVo> sampleInfoVoList, ExtractPlate extractPlate) {

        String templateFilePath = request.getServletContext().getRealPath("templates/extractSampleRecord.xls");
        HSSFWorkbook workbook = null;

        try {
            FileInputStream fis = new FileInputStream(templateFilePath);
            workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            HSSFCellStyle cellStyle = createCellStyle(workbook);

            int startRow = 2;
            int idx = 0;
            if (extractPlate != null) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(extractPlate.getBoardNo());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                if (StringUtils.isNotBlank(extractPlate.getOperationPerson())) {
                    cell.setCellValue(extractPlate.getOperationPerson());
                } else {
                    cell.setCellValue(extractPlate.getCreatePerson());
                }

                idx++;
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellValue(extractPlate.getExtractMethod());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellValue(DateUtils.dateToString(extractPlate.getCreateDatetime(), DateUtils.MIN));
            }
            startRow = 5;
            idx = 0;
            for (SampleInfoVo sampleInfoVo : sampleInfoVoList) {
                row = sheet.getRow(startRow + idx);
                if (row == null) {
                    row = sheet.createRow(startRow + idx);
                }

                cell = row.getCell(1);
                if (cell == null) {
                    cell = row.createCell(1);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getEntity().getSampleNo());

                cell = row.getCell(2);
                if (cell == null) {
                    cell = row.createCell(2);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getEntity().getExtractPlateLocation());

                cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getPreExperimentalMethodName());

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getConfirmatoryMethodName());

                cell = row.getCell(5);
                if (cell == null) {
                    cell = row.createCell(5);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getSampleTransferMethodName());

                cell = row.getCell(6);
                if (cell == null) {
                    cell = row.createCell(6);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getElutionName());

                cell = row.getCell(7);
                if (cell == null) {
                    cell = row.createCell(7);
                }
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sampleInfoVo.getSamplePropertyName());

                idx++;
            }

            String fileName = "导出样本提取记录表_" + extractPlate.getBoardNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
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
    }

    /**
     * 判断文件是否存在
     * @param caseId
     * @param consignmentId
     * @param fileCategory
     * @return
     */
    public Map<String, Object> judgeFileIsExist(String caseId, String consignmentId, String fileCategory) {
        Map<String, Object> result = new HashMap<>();
        try {
            //先判断是否存在此条记录
            ErsFile ersFile = new ErsFile();
            ersFile.setCaseId(caseId);
            ersFile.setConsignmentId(consignmentId);
            ersFile.setFileCategory(fileCategory);

            List<ErsFile> ersFileList = ersFileService.selectList(ersFile);
            if (ListUtils.isNotNullAndEmptyList(ersFileList)) {
                ersFile = ersFileList.get(0);
                String filePath = null;
                if (ersFile != null) {
                    filePath = ersFile.getOriginalFileDir();
                }
                result.put("filePath", filePath);
                result.put("isExist", true);
            }else {
                result.put("isExist", false);
            }
        }catch (Exception e) {
            e.getStackTrace();
            logger.debug("判断文件是否存在！" + caseId, e);
        }

        return result;
    }

    /**
     * 保存文件信息
     * @param caseId
     * @param consignmentId
     * @param filePath
     * @param fileCategory
     */
    public void saveErsFile(String caseId, String consignmentId, String filePath, String fileCategory) {
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            //先判断是否存在此条记录
            ErsFile ersFile = new ErsFile();
            ersFile.setCaseId(caseId);
            ersFile.setConsignmentId(consignmentId);
            ersFile.setFileCategory(fileCategory);

            List<ErsFile> ersFileList = ersFileService.selectList(ersFile);
            if (StringUtils.isNotBlank(filePath)) {
                ersFile.setOriginalFileDir(filePath);
                ersFile.setFileName(new File(filePath).getName());
            }
            //更新文件信息
            if (ListUtils.isNotNullAndEmptyList(ersFileList)) {
                ErsFile ers = ersFileList.get(0);
                ers.setFileName(ersFile.getFileName());
                ers.setOriginalFileDir(ersFile.getOriginalFileDir());
                ers.setUpdateDatetime(new Date());
                if (operateUser != null) {
                    ers.setUpdatePerson(operateUser.getLoginName());
                }
                ersFileService.updateByPrimaryKey(ers);
            } else {
                //插入文件信息
                ersFile.setFileId(UUID.randomUUID().toString());
                ersFile.setCreateDatetime(new Date());
                if (operateUser != null) {
                    ersFile.setCreatePerson(operateUser.getLoginName());
                }
                ersFileService.insert(ersFile);
            }
        } catch (Exception e) {
            e.getStackTrace();
            logger.debug("保存文件路径报错！" + caseId, e);
        }
    }

}
