package com.bazl.dna.caseinfo.accept.controller.center;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.caseinfo.accept.controller.BaseController;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.po.EquipmentNameInfo;
import com.bazl.dna.lims.model.po.EquipmentRepairInfo;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.EquipmentInfoVo;
import com.bazl.dna.lims.model.vo.EquipmentRepairInfoVo;
import com.bazl.dna.lims.service.EquipmentInfoService;
import com.bazl.dna.lims.service.EquipmentNameInfoService;
import com.bazl.dna.lims.service.EquipmentRepairInfoService;
import com.bazl.dna.lims.utils.DictUtil;

/**
 * Created by sixiru on 2019/4/9.
 */
@Controller
@RequestMapping("/equipmentRepairInfo")
public class EquipmentRepairInfoController extends BaseController{

    @Autowired
    EquipmentRepairInfoService equipmentRepairInfoService;

    @Autowired
    EquipmentInfoService equipmentInfoService;

    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;
    /**
     * 设备报废
     * @param request
     * @param equipmentInfoVo
     * @param pageInfo
     * @return
     */
    @RequestMapping("equipmentUselessList")
    public ModelAndView equipmentUselessList(HttpServletRequest request, EquipmentInfoVo equipmentInfoVo, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            //设备状态
            DictItem dictItem = new DictItem();
            dictItem.setDictTypeCode("EQUIPMENT_STATUS");
            List<DictItem> equipmentStatusList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("equipmentStatusList", equipmentStatusList);
            //初始化
            equipmentInfoVo = resetRquipmentRepairInfoQuery(equipmentInfoVo);
            List<EquipmentInfoVo> equipmentInfoList = equipmentInfoService.selectEquipmentScrapInfoList(equipmentInfoVo, pageInfo);
            int equipmentInfoCount = equipmentInfoService.selectEquipmentScrapInfoCount(equipmentInfoVo);
            modelAndView.addObject("query", equipmentInfoVo);
            modelAndView.addObject("equipmentInfoList", equipmentInfoList);
            modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(equipmentInfoCount));
            modelAndView.setViewName("equipment/equipmentUselessList");
        }catch (Exception e){
            logger.info("设备报废查询失败："+e);
        }
        return modelAndView;
    }

    public static EquipmentInfoVo resetRquipmentRepairInfoQuery(EquipmentInfoVo query) {
        if (StringUtils.isBlank(query.getEntity().getEquipmentNo())) {
            query.getEntity().setEquipmentNo(null);
        } else {
            query.getEntity().setEquipmentNo(query.getEntity().getEquipmentNo());
        }
        if (StringUtils.isBlank(query.getEntity().getEquipmentStatus())) {
            query.getEntity().setEquipmentStatus(null);
        } else {
            query.getEntity().setEquipmentStatus(query.getEntity().getEquipmentStatus());
        }
        if (StringUtils.isBlank(query.getEquipmentName())) {
            query.setEquipmentName(null);
        } else {
            query.setEquipmentName(query.getEquipmentName());
        }
        return query;
    }

    /**
     * 设备维护
     * @param request
     * @param equipmentInfoVo
     * @param pageInfo
     * @return
     */
    @RequestMapping("equipmentRepairList")
    public ModelAndView equipmentRepairList(HttpServletRequest request, EquipmentInfoVo equipmentInfoVo, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            //设备状态
            DictItem dictItem = new DictItem();
            dictItem.setDictTypeCode("EQUIPMENT_STATUS");
            List<DictItem> equipmentStatusList = DictUtil.getDictList(dictItem);
            modelAndView.addObject("equipmentStatusList", equipmentStatusList);
            //初始化
            equipmentInfoVo = resetRquipmentRepairInfoQuery(equipmentInfoVo);
            List<EquipmentInfoVo> equipmentInfoList = equipmentInfoService.selectEquipmentScrapInfoList(equipmentInfoVo, pageInfo);
            int equipmentInfoCount = equipmentInfoService.selectEquipmentScrapInfoCount(equipmentInfoVo);
            modelAndView.addObject("query", equipmentInfoVo);
            modelAndView.addObject("equipmentInfoList", equipmentInfoList);
            modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(equipmentInfoCount));
            modelAndView.setViewName("equipment/equipmentRepairInfoList");
        }catch (Exception e){
            logger.info("设备维护查询失败："+e);
        }
        return modelAndView;
    }

    /**
     * 查询设备维护记录详情
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("reviewDetails")
    public ModelAndView reviewDetails(HttpServletRequest request, String id, EquipmentRepairInfoVo equipmentRepairInfoVo, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            //查询设备信息
            EquipmentInfoVo equipmentInfoVo = equipmentInfoService.selectById(id);
            modelAndView.addObject("equipmentInfoVo", equipmentInfoVo);
            List<EquipmentNameInfo> equipmentNameInfoList = equipmentNameInfoService.selectAllList();
            modelAndView.addObject("equipmentNameInfoList", equipmentNameInfoList);
            //查询维修记录
            List<EquipmentRepairInfoVo> equipmentRepairInfoList = equipmentRepairInfoService.selectEquipmentRepairList(equipmentRepairInfoVo, pageInfo);
            int equipmentRepairInfoCount = equipmentRepairInfoService.selectEquipmentRepairCount(equipmentRepairInfoVo);
            modelAndView.addObject("id", id);
            modelAndView.addObject("query", equipmentRepairInfoVo);
            modelAndView.addObject("equipmentRepairInfoList", equipmentRepairInfoList);
            modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(equipmentRepairInfoCount));
            modelAndView.setViewName("equipment/equipmentRepairInfo");
        }catch (Exception e){
            logger.info("设备维护查询详情失败："+e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/saveEquipmentRepairInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveEquipmentRepairInfo(HttpServletRequest request, @RequestBody EquipmentRepairInfo equipmentRepairInfo) {
        Map<String, Object> result = new HashMap<>();

        //获取当前登录人信息
        Subject subject = SecurityUtils.getSubject();
        LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
        if (operateUser == null) {
            result.put("date", "redirect:/login.html?timeoutFlag=1");
            return result;
        }
        try {
            equipmentRepairInfo.setId(UUID.randomUUID().toString());
            equipmentRepairInfo.setCreatePerson(operateUser.getLoginName());
            equipmentRepairInfo.setCreateDatetime(new Date());
            equipmentRepairInfoService.insert(equipmentRepairInfo);
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

}
