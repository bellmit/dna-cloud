package com.bazl.dna.lims.core.controller.center;


import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.controller.BaseController;
import com.bazl.dna.lims.core.model.PageInfo;
import com.bazl.dna.lims.core.model.po.DictItem;
import com.bazl.dna.lims.core.model.po.FugitivesInfo;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.model.vo.FugitivesInfoVo;
import com.bazl.dna.lims.core.service.FugitivesInfoService;
import com.bazl.dna.lims.core.utils.DictUtil;
import com.bazl.dna.lims.core.utils.DownloadFileTools;
import com.bazl.dna.lims.core.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 说明：在逃人员信息控制层
 * @author wanghaiyang
 * @EditAuthor liuchang
 * @date 2020-06-25
 * @EditDate: 2020-07-27
 */
@Controller
@RequestMapping("/fugitives")
public class FugitivesController extends BaseController {

    @Autowired
    FugitivesInfoService fugitivesInfoService;//在逃人员详情
    @Autowired
    DownloadFileTools  downloadFileTools;//下载文件工具类

    /**
     * 在逃人员列表--2020-07-27
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/fugitivesRegManage")
    public ModelAndView pending(HttpServletRequest request, FugitivesInfoVo query, PageInfo pageInfo){
        ModelAndView modelAndView = new ModelAndView();

        DictItem dictItem = new DictItem();   //人员类型
        dictItem.setDictTypeCode(Constants.PERSON_TYPE);
        List<DictItem> personTypeList = DictUtil.getDictList(dictItem);

        if (query != null) {
            query = resetQueryParams(query);
        }else {
            query = new FugitivesInfoVo();
        }
        List<FugitivesInfoVo> fugitivesInfoVoList = fugitivesInfoService.selectVOList(query, pageInfo);//查询在逃人员信息数据
        int totalCnt = fugitivesInfoService.selectVOCnt(query);//查询在逃人员数据总计

        modelAndView.addObject("query", query);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("fugitivesInfoVoList", fugitivesInfoVoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("fugitives/fugitivesRegManage");
        return modelAndView;
    }

    /**
     * 导入在逃人员--liuchang
     * @author wanghaiyang
     * @EditAuthor liuchang
     * @Date:2020-7-27
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value="/uploadFugitives",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadFugitives(HttpServletRequest request, @RequestParam(value = "upFile") MultipartFile[] file){
        Map<String,Object> result = new HashMap<>();
        try {
            if (file.length > 0) {
                List<FugitivesInfo> allList = new ArrayList<>();
                for (int i = 0; i < file.length; i++) {
                    List<FugitivesInfo> fugitivesInfoList = downloadFileTools.importFugitivesInfo(request, file[i]);
                    allList.addAll(fugitivesInfoList);
                }
                //遍历解析出来的list
                if (ListUtils.isNotNullAndEmptyList(allList)) {
                    List<FugitivesInfo> newFugitivesInfoList = new ArrayList<>();
                    List<FugitivesInfo> repeatFugitivesInfoList = new ArrayList<>();
                    //筛选出新导入和已经存在的在逃人员信息
                    for (FugitivesInfo fi : allList) {
                        List<FugitivesInfo> fugitivesInfos = fugitivesInfoService.selectInfoByPersonNameAndCard(fi.getPersonName(), fi.getPersonCard());
                        //如果为空，表示之前没有插入这条记录
                        if (ListUtils.isNullOrEmptyList(fugitivesInfos)) {
                            newFugitivesInfoList.add(fi);
                        }else {
                            //不为空，表示此人已经存在
                            repeatFugitivesInfoList.add(fi);
                        }
                    }
                    //批量插入在逃人员信息
                    if (ListUtils.isNotNullAndEmptyList(newFugitivesInfoList)) {
                        fugitivesInfoService.insertBatchFugitives(newFugitivesInfoList);
                    }
                    //重复人员在前台展示
                    if (ListUtils.isNotNullAndEmptyList(repeatFugitivesInfoList)) {
                        result.put("repeatFugitivesInfoList", repeatFugitivesInfoList);
                    }else {
                        result.put("repeatFugitivesInfoList", null);
                    }
                    result.put("success",true);
                }else {
                    result.put("success", "no");
                }
            }else {
                result.put("success",false);
            }
        }catch (Exception e){
            result.put("success",false);
            logger.error("uploadSampleTable error",e);
        }

        return result;
    }

    /**
     * 插入数据
     * @param fugitivesInfo
     */
    public void insertData (FugitivesInfo fugitivesInfo) throws Exception {
        if (fugitivesInfo != null) {
            fugitivesInfo.setId(UUID.randomUUID().toString());
            fugitivesInfo.setCreateDatetime(new Date());

            fugitivesInfoService.insert(fugitivesInfo);
        }
    }

    /**
     * 增加或修改在逃人员信息--liuchang
     * @param request
     * @param fugitivesInfo
     * @return
     */
    @RequestMapping(value = "operateFugitives", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> operateFugitives(HttpServletRequest request, @RequestBody FugitivesInfo fugitivesInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                result.put("success",false);
                result.put("","/login.html?timeoutFlag=1");
                return  result;
            }
            //判断是否已经存在此信息，不存在添加在逃人员信息，否则修改在逃人员信息
            String repeat = null;
            if (StringUtils.isBlank(fugitivesInfo.getId())) {
                fugitivesInfo.setId(UUID.randomUUID().toString());
                fugitivesInfo.setCreateDatetime(new Date());
                fugitivesInfo.setCreatePerson(operateUser.getLoginName());

                //判断此在逃人员是否已经存在
                List<FugitivesInfo> fugitivesInfoList =
                        fugitivesInfoService.selectInfoByPersonNameAndCard(fugitivesInfo.getPersonName(), fugitivesInfo.getPersonCard());
                if (ListUtils.isNullOrEmptyList(fugitivesInfoList)) {
                    //插入在逃人员信息
                    fugitivesInfoService.insert(fugitivesInfo);
                }else {
                    repeat = "repeat";
                }
            }else {
                FugitivesInfo fugitives = fugitivesInfoService.selectByPrimaryKey(fugitivesInfo.getId());
                if (fugitives!=null) {
                    fugitives.setPersonName(fugitivesInfo.getPersonName());//人员名称
                    fugitives.setPersonType(fugitivesInfo.getPersonType());//人员类型
                    fugitives.setPersonGender(fugitivesInfo.getPersonGender());//性别
                    fugitives.setPersonCard(fugitivesInfo.getPersonCard());//证件号码
                    fugitives.setPersonAge(fugitivesInfo.getPersonAge());//人员年龄
                    fugitives.setFugitiveNo(fugitivesInfo.getFugitiveNo().replaceAll("\\s*", ""));//逃犯编号
                    fugitives.setUpdateDatetime(new Date());//修改时间
                    fugitives.setUpdatePerson(operateUser.getLoginName().replaceAll("\\s*", ""));//修改人
                    fugitives.setInvolvedName(fugitivesInfo.getInvolvedName().replaceAll("\\s*", ""));//涉案名称
                    fugitivesInfoService.updateByPrimaryKey(fugitives);   //更新在逃人员信息
                }
            }
            result.put("repeat", repeat);
            result.put("success", true);
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("添加失败!", e);
            result.put("success", false);
        }
        return result;
    }

    /**
     * 删除在逃人员信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delFugitivesInfo")
    @ResponseBody
    public Map<String, Object> delFugitivesInfo(HttpServletRequest request, String id) {
        Map<String, Object> result = new HashMap<>();

        try {
            //获取当前登录人信息
            Subject subject = SecurityUtils.getSubject();
            LoaUserInfo operateUser = (LoaUserInfo) subject.getPrincipal();
            if(operateUser == null){
                result.put("success",false);
                result.put("","/login.html?timeoutFlag=1");
                return  result;
            }

            FugitivesInfo fugitivesInfo = new FugitivesInfo();
            fugitivesInfo.setId(id);
            fugitivesInfo.setDeleteFlag(Constants.DELETE_FLAG_1);
            fugitivesInfo.setDeleteDatetime(new Date());
            fugitivesInfo.setDeletePerson(operateUser.getLoginName());

            fugitivesInfoService.deleteFugitivesInfo(fugitivesInfo);
            result.put("success", true);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("删除失败！", e);
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/fugitivesSearch")
    @ResponseBody
    public Map<String, Object> fugitivesSearch(HttpServletRequest request, String searchFugitives) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<FugitivesInfoVo> fugitivesInfoVoList = fugitivesInfoService.selectFugitivesList(searchFugitives.trim());
            result.put("fugitivesInfoVoList", fugitivesInfoVoList);
            result.put("success", true);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("查询失败！", e);
            result.put("success", false);
        }

        return result;
    }

    /**
     * 初始化查询条件
     * @param query
     * @return
     */
    private FugitivesInfoVo resetQueryParams(FugitivesInfoVo query){
        FugitivesInfo entity = query.getEntity();//违法犯罪人员信息

        if (StringUtils.isNotBlank(query.getInvolvedParentCard())) {
            query.setInvolvedParentCard(query.getInvolvedParentCard().replaceAll("\\s*", ""));//亲属身份证号码
        } else {
            query.setInvolvedParentCard(null);
        }
        if (query.getCreateDateTime() != null) {
            query.setCreateDateTime(query.getCreateDateTime());//创建时间
        }
        if (StringUtils.isNotBlank(query.getInvolvedParentName())) {
            query.setInvolvedParentName(query.getInvolvedParentName().replaceAll("\\s*", ""));//亲属姓名
        } else {
            query.setInvolvedParentName(null);
        }

        if (StringUtils.isBlank(entity.getDeleteFlag())) {
            entity.setDeleteFlag(Constants.DELETE_FLAG_0);
        } else {
            entity.setDeleteFlag(entity.getDeleteFlag().trim());//删除标记
        }

        if (StringUtils.isBlank(entity.getPersonName())) {
            entity.setPersonName(null);
        } else {
            entity.setPersonName(entity.getPersonName().replaceAll("\\s*", ""));//人员姓名
        }

        if (StringUtils.isBlank(entity.getPersonCard())) {
            entity.setPersonCard(null);
        } else {
            entity.setPersonCard(entity.getPersonCard().replaceAll("\\s*", ""));//人员身份证号码
        }

        if (StringUtils.isBlank(entity.getFugitiveNo())) {
            entity.setFugitiveNo(null);
        } else {
            entity.setFugitiveNo(entity.getFugitiveNo().replaceAll("\\s*", ""));//涉案编号
        }
        if (StringUtils.isBlank(entity.getInvolvedName())) {
            entity.setInvolvedName(null);
        } else {
            entity.setInvolvedName(entity.getInvolvedName().replaceAll("\\s*", ""));//涉案名称
        }
        query.setEntity(entity);


        return query;
    }
}
