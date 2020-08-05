package com.bazl.dna.database.core.controller.web.matched;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.CompareResult;
import com.bazl.dna.database.service.service.MatchResultRelativeService;
import com.bazl.dna.database.service.service.MatchResultSameGroupService;

/**
 * <p>
 * 比对队列表 前端控制器
 * </p>
 *
 * @author lizhi
 */
@Controller
@RequestMapping("/compare-result")
public class QueryCompareResultController extends BaseController {


    @Autowired
    MatchResultSameGroupService matchResultSameGroupService;
    
    @Autowired
    private MatchResultRelativeService matchResultRelativeService;

    /**
     * 查询str结果信息
     * @param
     * @return
     */
    @PostMapping(value = "/{queryStrCompareResult}")
    public ResponseData queryStrCompareResult(PageInfo pageInfo, CompareResult compareResult){
        try {
            List<CompareResult> compareResults =  matchResultSameGroupService.queryStrCompareResult(pageInfo,compareResult);
            return new ResponseData(compareResults);
        }catch(Exception ex){
            logger.error("invoke QueryCompareResultController.queryCaseById error.", ex);
            return null;
        }
    }


    /**
     * 查询亲缘结果信息
     * @param
     * @return
     */
    @PostMapping(value = "/{queryRelativeCompareResult}")
    public ResponseData queryRelativeCompareResult(PageInfo pageInfo, CompareResult compareResult){
        try {
            List<CompareResult> compareResults =  matchResultRelativeService.queryRelativeCompareResult(pageInfo,compareResult);
            return new ResponseData(compareResults);
        }catch(Exception ex){
            logger.error("invoke QueryCompareResultController.queryRelativeCompareResult error.", ex);
            return null;
        }
    }


    /**
     * 查看str比对详情
     */
    @PostMapping(value = "/{queryStrCompareinfo}")
    public ResponseData queryStrCompareinfo(PageInfo pageInfo, String id){
        try {
            CompareResult compareResults =  matchResultSameGroupService.queryStrCompareinfo(id);
            return new ResponseData(compareResults);
        }catch(Exception ex){
            logger.error("invoke QueryCompareResultController.queryRelativeCompareResult error.", ex);
            return null;
        }
    }


    /**
     * 查看亲缘比对详情
     */
    @PostMapping(value = "/{queryRelativeCompareinfo}")
    public ResponseData queryRelativeCompareinfo(PageInfo pageInfo, String id){
        try {
            CompareResult compareResults =  matchResultRelativeService.queryRelativeCompareinfo(id);
            return new ResponseData(compareResults);
        }catch(Exception ex){
            logger.error("invoke QueryCompareResultController.queryRelativeCompareResult error.", ex);
            return null;
        }
    }
}
