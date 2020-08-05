package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * str基因信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
public interface DnaStrGeneInfoService extends IService<DnaStrGeneInfo> {

	/**
	 * 根据比对目标条件获取样本信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findCompareList(int geneId, List<String> serverNo, List<String> dataType,
			Map<String, Object> params);
	
	/**
	 * 根据比对目标条件获取亲缘样本信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findRelativeCompareList(long offset, long rows);

	/**
	 * 根据检材主键sampleId查询基因信息
	 * @param sampleId
	 * @return
	 */
	public DnaSampleInfoVo selectBySampleId(Integer sampleId);

	/**
	 * 根据检材主键和样本类型查询基因信息
	 * @param sampleId
	 * @param dataSource
	 * @return
     */
	public DnaSampleInfoVo selectSampleByDataSource(Integer sampleId,Integer dataSource);
	
	/**
     * 快速比对查询基因信息
     * @return
     */
	public List<Map<String, Object>> findQuickCompareList(Map<String, Object> params); 
	
	/**
     * 添加虚拟列
     * @param key
     */
    public void insertVirtualColumn(String key);
    
    /**
     * 判断列是否存在
     * @param columnName
     * @return
     */
    public int checkVirtualColumn(String columnName);
    
    /**
     * 添加虚拟索引
     * @param key
     */
    public void insertVirtualIndex(String key);
    
    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    public int checkVirtualIndex(String indexName);
    
    /**
     * 查询虚拟索引
     * @return
     */
    public List<String> findVirtualIndexList();
    
    /**
     * 删除虚拟索引
     * @param key
     */
    public void deleteVirtualIndex(String key);

	/**
	 * 根据检材编号查询混合基因信息
	 */
	public DnaSampleInfoVo selectBySampleLabNo(String sampleLabNo);

	/**
	 * 根据实验室查询STR分型信息
	 * @param labServerNo
	 * @return
     */
	List<DnaStrGeneInfo> selectListByLabServerNo(String labServerNo);

    /**
	 * 查询STR全部分型数据
	 * @return
     */
	public int selectAllGeneCount();
	
	public List<Map<String, Object>> findList(long offset, long rows);


}
