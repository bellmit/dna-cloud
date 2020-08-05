package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.ConsignmentInfoVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 委托信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface ConsignmentInfoMapper extends BaseMapper<ConsignmentInfo> {

    int selectByPrimaryKeyCount(String No);

    //根据委托id获取委托信息
    ConsignmentInfo selectByPrimaryKey(Integer id);

    /**
     * 根据委托id获取委托信息Vo
     */
    ConsignmentInfoVo selectById(Integer id);
}
