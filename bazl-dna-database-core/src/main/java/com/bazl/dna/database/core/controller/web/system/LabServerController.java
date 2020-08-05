package com.bazl.dna.database.core.controller.web.system;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.service.LabServerInfoService;

/**
 * <p>
 * 实验室信息表 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/lab")
public class LabServerController {

    @Autowired
    LabServerInfoService labServerInfoService;

    /**
     * 查询数据库中定义的国籍列表
     * @return
     */
    @PostMapping(value = "/labServerList")
    public ResponseData labServerList(){
        List<LabServerInfo> list = labServerInfoService.list();
        return new ResponseData(list);
    }


}
