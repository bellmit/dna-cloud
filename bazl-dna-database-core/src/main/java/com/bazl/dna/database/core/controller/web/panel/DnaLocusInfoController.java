package com.bazl.dna.database.core.controller.web.panel;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.qo.LocusInfoQuery;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.DnaPanelInfoService;

/**
 * <p>
 * 基因座信息 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/locus")
public class DnaLocusInfoController extends BaseController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    DnaPanelInfoService dnaPanelInfoService;
    @Autowired
    DnaLocusInfoService dnaLocusInfoService;
    @Autowired
    CasePersonInfoService casePersonInfoService;


    /**
     * STR基因座列表接口-liuchang
     */
        @PostMapping(value = "/listAllStrLocus")
    public ResponseData listAllStrLocus(@RequestBody LocusInfoQuery query) {
        try {
            //默认查询STR分型
            query.setLocusType(Constants.GENE_TYPE_STR);
            //分页查询基因座列表数据
            if (query.getLocusName() != null && StringUtils.isNotBlank(query.getLocusName())) {
                query.setLocusName(query.getLocusName().trim().toUpperCase()); //基因座名称 变大写去除空格
            }
            if (query.getCoreLocusFlag() != null) {
                query.setCoreLocusFlag(query.getCoreLocusFlag());  //是否为核心基因座
            }
            List<DnaLocusInfo> strDnaLoucsInfoList = dnaLocusInfoService.locusPaginationQuery(query);
            //查询总计基因组列表数据
            int totalCount = dnaLocusInfoService.locusPaginationCount(query);
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), totalCount));
            resultObj.put("strDnaLoucsInfoList", strDnaLoucsInfoList);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke DnaLocusInfoController.listAllStrLocus error.", ex);
            return new ResponseData("STR基因座列表出现异常！" + ex.getMessage());
        }
    }

    /**
     * YSTR基因座列表接口－liuchang
     */
    @PostMapping(value = "/listAllYstrLocus")
    public ResponseData listAllYstrLocus(@RequestBody LocusInfoQuery query) {
        try {
            //默认查询YSTR分型
            query.setLocusType(Constants.GENE_TYPE_YSTR);
            //分页查询基因座列表数据
            List<DnaLocusInfo> YstrDnaLoucsInfoList = dnaLocusInfoService.locusPaginationQuery(query);
            //查询总计基因组列表数据
            int totalCount = dnaLocusInfoService.locusPaginationCount(query);
            //分页信息
            PageInfo pageInfo = new PageInfo();
            pageInfo.setEvePageRecordCnt(query.getRows());//每页显示条数
            pageInfo.setAllRecordCount(totalCount);//总计页数
            if(totalCount >0 && totalCount % query.getRows() == 0){ //总计页码
                pageInfo.setPageCount((int)totalCount/query.getRows());
            }else{
                pageInfo.setPageCount((int)totalCount/query.getRows()+1);
            }
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("pageInfo", pageInfo);
            resultObj.put("YstrDnaLoucsInfoList", YstrDnaLoucsInfoList);
            return new ResponseData(resultObj);
        } catch (Exception ex) {
            logger.error("invoke DnaLocusInfoController.listAllYstrLocus error.", ex);
            return new ResponseData("YSTR基因座列表出现异常！" + ex.getMessage());
        }
    }

    /**
     * 编辑YSTR基因座信息接口--liuchang
     */
    @PostMapping(value = "/updateYstrGeneInfo")
    @SuppressWarnings("all")
    public ResponseData updateYstrGeneInfo(HttpServletRequest request, @RequestBody List<DnaLocusInfo> ystrDnaLocusInfo) {
        try {
            if (ystrDnaLocusInfo.size() > 0 && ystrDnaLocusInfo != null) {
                for (DnaLocusInfo locusInfo : ystrDnaLocusInfo) {
                    if (locusInfo.getUpdateDatetime() == null && locusInfo.getCreateDatetime() != null) {
                        locusInfo.setUpdateDatetime(LocalDateTime.now());//基因座修改时间
                    }
                    if (locusInfo.getCreateDatetime() == null) {
                        locusInfo.setCreateDatetime(LocalDateTime.now());//基因座创建时间
                    }
                    if (StringUtils.isNotBlank(locusInfo.getLocusName())) {
                        locusInfo.setLocusName(locusInfo.getLocusName());//基因座名称
                    }
                    if (StringUtils.isNotBlank(locusInfo.getLocusAlias())) {
                        locusInfo.setLocusAlias(locusInfo.getLocusAlias());//基因座别名
                    }
                    if (StringUtils.isNotBlank(locusInfo.getValueScope())) {
                        locusInfo.setValueScope(locusInfo.getValueScope());//取值范围
                    }
                    if (locusInfo.getCoreLocusFlag() != null) {
                        locusInfo.setCoreLocusFlag(locusInfo.getCoreLocusFlag());//是否为核心基因座
                    }
                    if (StringUtils.isNotBlank(locusInfo.getRemarks())) {
                        locusInfo.setRemarks(locusInfo.getRemarks());//备注
                    }
                    if (locusInfo.getLocusOrd() != null) {
                        locusInfo.setLocusOrd(locusInfo.getLocusOrd());//基因座顺序
                    }
                    if (StringUtils.isNotBlank(locusInfo.getLocusType())) {
                        locusInfo.setLocusType(locusInfo.getLocusType());//修改基因座类型
                    }
                    dnaLocusInfoService.updateById(locusInfo);
                }
            }
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.updateYstrGeneInfo error.", ex);
            return new ResponseData("编辑YSTR基因座信息出现异常！" + ex.getMessage());
        }
    }

    //查询ystr基因座列表
    @PostMapping(value = "/ystrAlleleQuery")
    @SuppressWarnings("all")
    public ResponseData ystrAlleleQuery(String name) {
        try {
            int type = 2;
            List<DnaLocusInfo> dnaLocusInfos = dnaLocusInfoService.queryAlleleInfos(type, name);
            return new ResponseData(dnaLocusInfos);
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.ystrAlleleQuery error.", ex);
            return new ResponseData("Ystr基因管理异常！" + ex.getMessage());
        }
    }

    /**
     * 类说明：修改基因座管理--liuchang
     *
     * @param rquest
     * @param dnaLocusInfo
     * @return
     */
    @PostMapping(value = "/updateStrGeneInfo")
    @SuppressWarnings("all")
    public ResponseData updateStrGeneInfo(HttpServletRequest request, @RequestBody List<DnaLocusInfo> ystrDnaLocusInfo) {
        try {
            if (ystrDnaLocusInfo.size() > 0 && ystrDnaLocusInfo != null) {
                for (DnaLocusInfo dnaLocusInfo : ystrDnaLocusInfo) {
                    if (dnaLocusInfo.getUpdateDatetime() == null && dnaLocusInfo.getCreateDatetime() != null) {
                        dnaLocusInfo.setUpdateDatetime(LocalDateTime.now());//基因座修改时间
                    }
                    if (dnaLocusInfo.getCreateDatetime() == null) {
                        dnaLocusInfo.setCreateDatetime(LocalDateTime.now());//基因座创建时间获取当前系统时间
                    }
                    if (StringUtils.isNotBlank(dnaLocusInfo.getLocusName())) {
                        dnaLocusInfo.setLocusName(dnaLocusInfo.getLocusName());//基因座名称
                    }
                    if (StringUtils.isNotBlank(dnaLocusInfo.getLocusAlias())) {
                        dnaLocusInfo.setLocusAlias(dnaLocusInfo.getLocusAlias());//基因座别名
                    }
                    if (StringUtils.isNotBlank(dnaLocusInfo.getValueScope())) {
                        dnaLocusInfo.setValueScope(dnaLocusInfo.getValueScope());//取值范围
                    }
                    if (dnaLocusInfo.getCoreLocusFlag() != null) {
                        dnaLocusInfo.setCoreLocusFlag(dnaLocusInfo.getCoreLocusFlag());//是否为核心基因座
                    }
                    if (StringUtils.isNotBlank(dnaLocusInfo.getRemarks())) {
                        dnaLocusInfo.setRemarks(dnaLocusInfo.getRemarks());//备注
                    }
                    if (dnaLocusInfo.getLocusOrd() != null) {
                        dnaLocusInfo.setLocusOrd(dnaLocusInfo.getLocusOrd());//基因座顺序
                    }
                    dnaLocusInfoService.updateById(dnaLocusInfo);
                }
            }
                    return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.updateStrGeneInfo error.", ex);
                    return new ResponseData("修改异常 DnaPanelInfoController.updateStrGeneInfo ！" + ex.getMessage());
        }

    }
}
