package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 试剂盒基因座关系表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface DnaPanelLocusMapper extends BaseMapper<DnaPanelLocus> {
    /**
     * 查询试剂盒对应基因座信息
     * @param dnaPanelId
     * @return
     */
    List<DnaPanelLocus> listByPanelId(Integer dnaPanelId);

    int isSelectId(DnaPanelLocus dnaPanelLocus);

}




