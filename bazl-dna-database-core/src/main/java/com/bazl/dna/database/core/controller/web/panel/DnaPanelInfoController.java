package com.bazl.dna.database.core.controller.web.panel;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.mapper.DnaPanelLocusMapper;
import com.bazl.dna.database.service.model.bo.PanelInfoModel;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import com.bazl.dna.database.service.model.po.PanelInfoQuery;
import com.bazl.dna.database.service.model.qo.DnaPanelInfoQuery;
import com.bazl.dna.database.service.service.DnaGeneInfoService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.DnaPanelInfoService;
import com.bazl.dna.database.service.service.DnaPanelLocusService;

/**
 * <p>
 * 试剂盒信息表 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/panel")
public class DnaPanelInfoController extends BaseController {
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    DnaPanelInfoService dnaPanelInfoService;
    @Autowired
    DnaLocusInfoService dnaLocusInfoService;
    @Autowired
    DnaGeneInfoService dnaGeneInfoService;
    @Autowired
    DnaPanelLocusService dnaPanelLocusService;
    @Autowired
    DnaPanelLocusMapper dnaPanelLocusMapper;


    /**
     * 根据panelType查询符合条件的集合
     * @param panelType
     * @return
     */
    @GetMapping(value="/listByType")
    public ResponseData queryByPanelType(String panelType){
        try {
            List<DnaPanelInfo> panelInfoList = dnaPanelInfoService.list(new QueryWrapper<DnaPanelInfo>().eq("PANEL_TYPE", panelType));
            return new ResponseData(panelInfoList);
        }catch(Exception ex){
            logger.error("invoke DnaPanelInfoController.queryByPanelType error.", ex);
            return new ResponseData("根据panel类型查询试剂盒列表出现异常！" + ex.getMessage());
        }
    }

    /**
     *
     * 根据panelId查询符合条件的基因座集合，返回以LOCUS_ORD字段排序后的集合
     *
     * @param panelId
     * @return
     */
    @GetMapping(value="/locusListByPanel")
    public ResponseData queryLocusByPanelId(Integer panelId){
        try {
            List<DnaLocusInfo> locusInfoList = dnaLocusInfoService.listByPanelId(panelId);
            return new ResponseData(locusInfoList);
        }catch(Exception ex){
            logger.error("invoke DnaPanelInfoController.queryLocusByPanelId error.", ex);
            return new ResponseData("根据panelId类型查询基因座列表出现异常！" + ex.getMessage());
        }
    }

    /**
     *
     * 保存panel信息
     *
     * @param dnaPanelInfo
     * @return
     */
    @PostMapping(value="/savePanelOnly")
    public ResponseData savePanelOnly(@RequestBody DnaPanelInfo dnaPanelInfo){
        try {
            //TODO
            // 根据主键id判断add 或 update

            return new ResponseData();
        }catch(Exception ex){
            logger.error("invoke DnaPanelInfoController.savePanelOnly error.", ex);
            return new ResponseData("保存panel信息出现异常！" + ex.getMessage());
        }
    }

    /**
     *
     * 保存panel和对应的locus集合
     *
     * @param dnaPanelInfo
     *      DnaPanelInfo对象，包含panel对应的DnaLocusInfo集合
     *
     * @return
     */
    @PostMapping(value="/savePanelAndLocus")
    public ResponseData savePanelAndLocus(HttpServletRequest rquest, @RequestBody DnaPanelInfo dnaPanelInfo){
        try {
            //TODO

            return new ResponseData();
        }catch(Exception ex){
            logger.error("invoke DnaPanelInfoController.savePanelAndLocus error.", ex);
            return new ResponseData("保存panel和对应的locus集合出现异常！" + ex.getMessage());
        }
    }

    /**
     *  编辑YSTR基因座信息接口
     */
    @PostMapping(value = "/updateYstrGeneInfo")
    public ResponseData updateYstrGeneInfo(@RequestBody DnaLocusInfo dnaLocusInfo){
        try {
            dnaLocusInfo.setUpdateDatetime(LocalDateTime.now());
            Boolean bool = dnaLocusInfoService.updateById(dnaLocusInfo);

            return new ResponseData(bool);
        }catch(Exception ex){
            logger.error("invoke DnaPanelInfoController.updateYstrGeneInfo error.", ex);
            return new ResponseData("编辑YSTR基因座信息出现异常！" + ex.getMessage());
        }
    }

    //获取试剂盒接口
    @PostMapping(value = "/getPanelList")
    public ResponseData getPanelList() {
        try {
            List<DnaPanelInfo> list = dnaPanelInfoService.list();
            return new ResponseData(list);
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.getPanelList error.", ex);
            return new ResponseData("获取试剂盒接口异常！" + ex.getMessage());
        }
    }

    /**
     * 分页查询试剂盒接口--liuchang
     * @param query
     * @return
     */
    @PostMapping(value = "/selectPanelInfoList")
    public  ResponseData selectPanelInfoList(@RequestBody DnaPanelInfoQuery query ){
        try{
            if (StringUtils.isNotBlank(query.getPanelName())&& query.getPanelName()!=null){
                query.setPanelName(query.getPanelName().trim());//试剂盒名称
            }
            if (StringUtils.isNotBlank(query.getAliasName())&&query.getAliasName()!=null){
                query.setAliasName(query.getAliasName().trim());//试剂盒别名
            }
            if (query.getPanelType()!=null ){
                query.setPanelType(query.getPanelType());//试剂盒类型
            }
            //分页查询试剂盒数据
            List<DnaPanelInfo> dnaPanelInfoList = dnaPanelInfoService.dnaPanelInfoPaginationQuery(query);
            //分页查询总计
            int totalCount =  dnaPanelInfoService.panelPaginationCount(query);
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), totalCount));
            resultObj.put("dnaPanelInfoList", dnaPanelInfoList);
            return new ResponseData(resultObj);
        }catch (Exception ex){
            logger.error("invoke DnaPanelInfoController.selectPanelInfoList error.", ex);
            return new ResponseData("获取分页查询试剂盒接口错误！" + ex.getMessage());
        }
    }

    //查询ystr基因座列表
    @PostMapping(value = "/ystrAlleleQuery")
    @SuppressWarnings("all")
    public ResponseData ystrAlleleQuery(String name) {
        try {
            int type = 2;
            List<DnaLocusInfo> dnaLocusInfos = dnaLocusInfoService.queryAlleleInfos(type,name);
            return new ResponseData(dnaLocusInfos);
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.ystrAlleleQuery error.", ex);
            return new ResponseData("Ystr基因管理异常！" + ex.getMessage());
        }
    }

    //基因座查询
    @PostMapping(value = "/panelInfoQuery")
    public ResponseData panelInfoQuery(DnaPanelInfo dnaPanelInfo) {
        try {
            List<PanelInfoQuery> panelInfoQueries = dnaPanelInfoService.panelInfoQuery(dnaPanelInfo);
            return new ResponseData(panelInfoQueries);
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.panelInfoQuery error.", ex);
            return new ResponseData("试剂盒管理异常！" + ex.getMessage());
        }
    }

    //修改基因座管理
    @PostMapping(value = "/updateStrGeneInfo")
    public ResponseData updateStrGeneInfo(DnaLocusInfo dnaLocusInfo) {
        try {
            if (dnaLocusInfo != null){
                dnaLocusInfoService.updateById(dnaLocusInfo);
            }
            return new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.updateStrGeneInfo error.", ex);
            return new ResponseData("修改异常！" + ex.getMessage());
        }
    }

    //查看试剂盒管理详情
    @GetMapping(value = "/selectById")
    public ResponseData selectById(@RequestParam("dnaPanelId") String dnaPanelId) {
        try {
            DnaPanelInfo panelInfo = dnaPanelInfoService.selectById(dnaPanelId);
            return new ResponseData(panelInfo);
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.selectById error.", ex);
            return new ResponseData("查看试剂盒管理详情异常！" + ex.getMessage());
        }
    }

    //查询试剂盒名称
    @PostMapping(value = "/panelNameQuery")
    public ResponseData panelNameQuery() {
        try {
            List<DnaPanelInfo> dnaPanelInfos = dnaPanelInfoService.list();
            return new ResponseData(dnaPanelInfos);
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.panelNameQuery error.", ex);
            return new ResponseData("查询试剂盒名称异常！" + ex.getMessage());
        }
    }

    /**
     * 查询试剂盒包括基因座信息--liuchang
     * @return
     */
    @GetMapping(value = "/panelLocusInfoQuery")
    public ResponseData panelLocusInfoQuery(@RequestParam("dnaPanelId") String dnaPanelId){
        try{
            //根据ID查询试剂盒信息
            DnaPanelInfo panelInfo = dnaPanelInfoService.selectById(dnaPanelId);
            //查询基因座信息根据 试剂盒ID
            List<DnaPanelLocus> locusInfoList = dnaPanelLocusService.listByPanelId(Integer.valueOf(dnaPanelId));
            //查询全部基因座信息
            List<DnaLocusInfo> allLocusInfoList = dnaLocusInfoService.list();
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("panelInfo", panelInfo);
            resultObj.put("locusInfoList", locusInfoList);
            resultObj.put("allLocusInfoList",allLocusInfoList);
            return new ResponseData(resultObj);
        }catch (Exception ex){
            logger.error("invoke DnaPanelInfoController.panelLocusInfoQuery error ",ex);
            return new ResponseData("查询试剂盒包括基因座信息异常！"+ex.getMessage());
        }
    }

    /**
     * 保存试剂盒包括基因座信息--liuchang
     * @return
     */
    @PostMapping(value = "/savePanelLoucsInfoQuery")
    public ResponseData savePanelLoucsInfoQuery (@RequestBody PanelInfoModel panelInfoModel) {
        try {
            //判断基因座信息和，试剂盒集合是否为空
            if (panelInfoModel.getPanelInfo()!=null && panelInfoModel.getLocusInfoList().size()>0) {
                //保存试剂盒，基因座信息
                this.dnaPanelLocusService.savePanelLoucsInfo(panelInfoModel);
            }
            return  new ResponseData();
        } catch (Exception ex) {
            logger.error("invoke DnaPanelInfoController.savePanelLoucsInfoQuery error", ex);
            return new ResponseData("保存试剂盒包括基因座信息异常！" + ex.getMessage());
        }
    }

    /**
     * 删除试剂盒中的基因座信息--liuchang
     * @param
     * @returnid
     */
    @GetMapping(value = "/deletePanelLoucsById")
    public ResponseData deletePanelLoucsById(Integer id) {
        if (id != 0) {
            try {
                dnaPanelLocusMapper.deleteById(id);
                return new ResponseData();
            } catch (Exception ex) {
                logger.error("invoke DnaPanelInfoController.deletePanelLoucsById error", ex);
                return new ResponseData("删除基因座异常！" + ex.getMessage());
            }
        }else{
            logger.error("invoke DnaPanelInfoController.deletePanelLoucsById error");
            return new ResponseData("参数为空，删除异常！");
        }
    }


    /**
     * 查询基因座信息--liuchang
     * @return
     */
    @GetMapping(value = "/selectAllStrLocusInfo")
    @SuppressWarnings("all")
    public ResponseData selectAllStrLocusInfo(){
        try {
            String strLocusType = "1";
            List<DnaLocusInfo> strLocusInfo = dnaLocusInfoService.selectByLocusType(strLocusType); //查询STR基因座信息
            String ystrLocusType = "2";
            List<DnaLocusInfo> ystrLocusInfo = dnaLocusInfoService.selectByLocusType(ystrLocusType); //查询YSTR基因座信息
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("strLocusInfo",strLocusInfo);
            resultObj.put("ystrLocusInfo",ystrLocusInfo);
            return new ResponseData(resultObj); //返回俩组基因座信息
        }catch (Exception ex){
            logger.error("invoke PopulationFrequencyInfoController.selectAllStrLocusInfo error");
            return new ResponseData("查询全部STR基因座信息异常!"+ex.getMessage());
        }
    }
}