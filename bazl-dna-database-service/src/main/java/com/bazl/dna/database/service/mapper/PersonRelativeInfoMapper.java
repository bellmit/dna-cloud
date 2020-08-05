package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 人员关系表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface PersonRelativeInfoMapper extends BaseMapper<PersonRelativeInfo> {

    List<PersonRelativeInfo> queryPersonRelationList(PersonRelativeInfo personRelativeInfo);
}
