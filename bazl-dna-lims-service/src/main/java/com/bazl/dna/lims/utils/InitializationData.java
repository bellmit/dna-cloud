package com.bazl.dna.lims.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.po.DictItem;

/**
 * @author wanghaiyang
 * @date 2020/8/4 15:18
 */
public class InitializationData {

    public static PageInfo resetPageInfo(int pageIndex) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(pageIndex);
        return pageInfo;
    }

    public static String resetOrgId(String userOrgId) {
        //判断是否是法医账户，需要进行处理
        if (StringUtils.isNotBlank(userOrgId) && userOrgId.contains(Constants.FORENSIC_CENTER_SHORT_ORG_ID)) {
            userOrgId = "110230000000";
        }

        return userOrgId;
    }
    
    /**
     * 初始化字典项
     * @return
     */
	public static ModelAndView initDictList() {
	    ModelAndView modelAndView = new ModelAndView();
	    /*  字典 */
	
	    DictItem dictItem = new DictItem();
	    //案件类型
	    dictItem.setDictTypeCode(Constants.CASE_TYPE);
	    List<DictItem> caseTypeList = DictUtil.getDictList(dictItem);
	
	    //案件性质
	    //List<DictItem> casePropertyList1 = new LinkedList<>();
	    dictItem.setDictTypeCode(Constants.CASE_PROPERTY);
	    List<DictItem> casePropertyList = DictUtil.getDictList(dictItem);
	
	    //案件级别
	    dictItem.setDictTypeCode(Constants.CASE_LEVEL);
	    List<DictItem> caseLevelList = DictUtil.getDictList(dictItem);
	
	    //人员类型
	    dictItem.setDictTypeCode(Constants.PERSON_TYPE);
	    List<DictItem> memberTypeList = DictUtil.getDictList(dictItem);
	
	    //性别
	    dictItem.setDictTypeCode(Constants.GENDER);
	    List<DictItem> genderList = DictUtil.getDictList(dictItem);
	
	    //检材类型
	    dictItem.setDictTypeCode(Constants.SAMPLE_TYPE);
	    List<DictItem> sampleTypeList = DictUtil.getDictList(dictItem);
	
	    //包装方法PACK_METHOD
	    dictItem.setDictTypeCode(Constants.PACK_METHOD);
	    List<DictItem> packMethodList = DictUtil.getDictList(dictItem);
	
	    //案件状态CASE_STATUS
	    dictItem.setDictTypeCode(Constants.CASE_STATUS);
	    List<DictItem> caseStatusList = DictUtil.getDictList(dictItem);
	
	    dictItem.setDictTypeCode(Constants.POSITION_LIST);
	    List<DictItem> positionList = DictUtil.getDictList(dictItem);
	
	    //人员类型
	    dictItem.setDictTypeCode(Constants.PERSON_TYPE);
	    List<DictItem> personTypeList = DictUtil.getDictList(dictItem);
	
	    //亲缘关系
	    dictItem.setDictTypeCode(Constants.RELATION_TYPE);
	    List<DictItem> relationTypeList = DictUtil.getDictList(dictItem);
	
	    //提取方法
	    dictItem.setDictTypeCode(Constants.EXTRACT_METHOD);
	    List<DictItem> extractMethodList = DictUtil.getDictList(dictItem);
	
	    //载体
	    dictItem.setDictTypeCode(Constants.SAMPLE_CARRIER);
	    List<DictItem> sampleCarrierList = DictUtil.getDictList(dictItem);
	
	    modelAndView.addObject("caseTypeList", caseTypeList);
	    modelAndView.addObject("casePropertyList", casePropertyList);
	    modelAndView.addObject("caseLevelList", caseLevelList);
	    modelAndView.addObject("memberTypeList", memberTypeList);
	    modelAndView.addObject("sampleTypeList", sampleTypeList);
	    modelAndView.addObject("genderList", genderList);
	    modelAndView.addObject("packMethodList", packMethodList);
	    modelAndView.addObject("caseStatusList", caseStatusList);
	    modelAndView.addObject("positionList", positionList);
	    modelAndView.addObject("personTypeList",personTypeList);
	    modelAndView.addObject("relationTypeList",relationTypeList);
	    modelAndView.addObject("extractMethodList",extractMethodList);
	    modelAndView.addObject("sampleCarrierList",sampleCarrierList);
	
	    return modelAndView;
	}

}
