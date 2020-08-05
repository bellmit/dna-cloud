package com.bazl.dna.database.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * ystr基因信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
public interface DnaYstrGeneInfoMapper extends BaseMapper<DnaYstrGeneInfo> {

	/**
     * 根据比对目标条件获取样本信息
     * @return
     */
	public List<Map<String, Object>> findCompareList(@Param("geneId") int geneId, @Param("serverNo") List<String> serverNo,
			@Param("dataType") List<String> dataType, @Param("params") Map<String, Object> params);

	/**
	 * 根据检材主键sampleId查询基因信息
	 * @param sampleId
	 * @return
	 */
	public DnaSampleInfoVo selectBySampleId(Integer sampleId);

	/**
	 * 根据检材主键sampleId，和样本类型 查询基因信息
	 * @param sampleId
	 * @return
	 */
	public DnaSampleInfoVo selectSampleByDataSource(@Param("sampleId") Integer sampleId, @Param("dataSource") Integer dataSource);


	/**
     * 快速比对查询基因信息
     * @return
     */
	public List<Map<String, Object>> findQuickCompareList(@Param("params") Map<String, Object> params); 
	
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
	 * 查询样本信息根据样本编号
	 * @param sampleLabNo
	 * @return
     */
	public DnaSampleInfoVo selectBySampleLabNo(String sampleLabNo);

	/**
	 * 查询YSTR分型信息根据实验室编号
	 * @param labServerNo
	 * @return
     */
	List<DnaYstrGeneInfo> selectListByLabServerNo(String labServerNo);

	/**
	 * 查询YSTR分型信息数据
	 * @return
     */
	public int selectAllGeneCount();
	
	public List<Map<String, Object>> findList(@Param("offset") long offset, @Param("rows") long rows);

}
