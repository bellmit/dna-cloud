package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.qo.LocusInfoQuery;

import java.util.List;

/**
 * <p>
 * DNA基因座信息表
 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface DnaLocusInfoService extends IService<DnaLocusInfo> {

    /**
     * 根据panelId获取对应的基因座列表
     * @param panelId
     * @return
     */
    public List<DnaLocusInfo> listByPanelId(Integer panelId);

    /**
     * 根据LOCUS_TYPE查询基因座
     * @param locusType
     * @return
     */
    public List<DnaLocusInfo> listByLocusType (String locusType);

    List<DnaLocusInfo> queryAlleleInfos(int type,String name);

    /**
     * 分页查询基因座信息
     * @param query
     * @return
     */
    public List<DnaLocusInfo> locusPaginationQuery(LocusInfoQuery query);

    /**
     * 查询基因座信息总计
     * @param query
     * @return
     */
    public int locusPaginationCount(LocusInfoQuery query);

    /**
     * 获取基因座
     * @return
     */
    public List<DnaLocusInfo> queryLocusInfo();

    /**
     * 查询根据基因座名称获取基因信息
     * @param locusName
     * @return
     */
    public DnaLocusInfo selectByLocusName(String locusName);

    /**
     * 查询基因座信息根据 类型
     * @param locusType
     * @return
     */
    List<DnaLocusInfo> selectByLocusType(String locusType);

    public String selectById(DnaLocusInfo dnaLocusInfo);
}
