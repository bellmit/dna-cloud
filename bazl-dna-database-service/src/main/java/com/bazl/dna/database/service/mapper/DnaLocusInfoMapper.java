package com.bazl.dna.database.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.qo.LocusInfoQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * DNA基因座信息表
 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface DnaLocusInfoMapper extends BaseMapper<DnaLocusInfo> {

    List<DnaLocusInfo> queryLocusInfo();

    /**
     * 根据panelId获取对应的基因座列表
     * @param panelId
     * @return
     */
    public List<DnaLocusInfo> listByPanelId(Integer panelId);

    List<DnaLocusInfo> queryLocusInfos(int type, String name);

    /**
     * 根据LOCUS_TYPE查询基因座
     * @param locusType
     * @return
     */
    public List<DnaLocusInfo> listByLocusType(String locusType);

    /**
     * 分页查询  基因座信息
     * @param query
     * @return
     */
    public List<DnaLocusInfo> locusPaginationQuery(LocusInfoQuery query);

    /**
     * 查询基因座信息 总计
     * @param query
     * @return
     */
    public int paginationCount(LocusInfoQuery query);

    /**
     * 查询基因座信息根据类型
     * @param locusType
     * @return
     */
    List<DnaLocusInfo> selectByLocusType(String locusType);

    public String selectById(DnaLocusInfo locusType);

    /**
     * 查询基因座信息 根据基因座名称
     * @param locusName
     * @return
     */
    DnaLocusInfo selectByLocusName(String locusName);
}
