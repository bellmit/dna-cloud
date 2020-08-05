package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.ConsignmentInfoVo;

/**
 * <p>
 * 委托信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface ConsignmentInfoService extends IService<ConsignmentInfo> {

    /**
     * 根据委托id获取委托信息
     */
    ConsignmentInfo selectByPrimaryKey(Integer id);

    /**
     * 根据委托id获取委托信息Vo
     */
    ConsignmentInfoVo selectByIdVo(Integer id);

    /**
     * 根据案件id查询主委托对象
     * @param caseId
     * @return
     */
    public ConsignmentInfo selectMainConsignmentByCaseId(Integer caseId);

    int selectByPrimaryKeyCount(String id);

}
