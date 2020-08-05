package com.bazl.dna.mix.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bazl.dna.common.PageInfo;

/**
 * @Author: lzn
 * @Date: 2019/7/9 14:03
 * @Version: 1.0
 * 首页查询更多分页结果
 */
public class ResultPageListUtils<T>{


    /**
     * @param pageInfo
     * @param caseInfoList
     */
    public static Map<String, Object> resultPageList(PageInfo pageInfo, List<?> caseInfoList) {
        Map<String, Object> resultMap = new HashMap<>();
        int pageSize =0;
        int count =0;
        //刚开始的页面为第一页
         pageInfo.getCurPage();
        //设置每页数据为十五条
         pageSize = pageInfo.getEvePageRecordCnt();
        //每页的开始数
        pageInfo.setStar((pageInfo.getCurPage()- 1) *pageSize);
        //list的大小
         count = caseInfoList.size();
        //总条数
        pageInfo.setAllRecordCount(count);
        //设置总页数
        pageInfo.setPageCount(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        //对list进行截取
        pageInfo.setDataList(caseInfoList.subList(pageInfo.getStar(), count - pageInfo.getStar() >pageSize ? pageInfo.getStar() + pageSize : count));

        resultMap.put("caseInfoList", pageInfo);
        return resultMap;
    }


}
