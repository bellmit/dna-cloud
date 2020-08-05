package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.DictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典条目信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface DictItemMapper extends BaseMapper<DictItem> {

    //根据typeCode和code获取其对应的name
    String selectNameByTypeCodeAndCode(@Param("dictTypeCode") String dictTypeCode, @Param("dictCode") String dictCode);
    //查询根据字典类型
    public List<DictItem> selectListByDictType(String dictTypeCode);
}
