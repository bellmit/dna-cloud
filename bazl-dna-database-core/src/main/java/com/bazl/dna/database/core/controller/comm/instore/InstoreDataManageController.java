package com.bazl.dna.database.core.controller.comm.instore;


import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.InstoreDataType;
import com.bazl.dna.database.service.service.InstoreDataTypeService;
import com.bazl.dna.database.utils.ListUtils;

/**
 * <p>
 * 入库数据管理
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/comm/instore")
public class InstoreDataManageController extends BaseController {

    @Autowired
    InstoreDataTypeService instoreDataTypeService;

    /**
     * 查询数据库中定义的样本入库类型列表
     * @return
     */
    @PostMapping(value = "/instoreDataTypeList")
    public ResponseData getInstoreSampleTypeList(){
        try {
            List<InstoreDataType> list = instoreDataTypeService.list(new QueryWrapper<InstoreDataType>());

            if (ListUtils.isNotNullAndEmptyList(list)) {
                list.sort(new Comparator<InstoreDataType>() {
                    @Override
                    public int compare(InstoreDataType o1, InstoreDataType o2) {
                        return o1.getTypeCode().compareTo(o2.getTypeCode());
                    }
                });
            }

            return new ResponseData(list);
        } catch (Exception ex) {
            logger.error("invoke InstoreDataManageController.getInstoreSampleTypeList error.", ex);
            return new ResponseData("获取入库数据类型列表异常！" + ex.getMessage());
        }
    }

}
