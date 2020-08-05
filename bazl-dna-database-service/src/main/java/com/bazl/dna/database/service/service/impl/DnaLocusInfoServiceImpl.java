package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DnaLocusInfoMapper;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.qo.LocusInfoQuery;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * DNA基因座信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
@Transactional
public class DnaLocusInfoServiceImpl extends ServiceImpl<DnaLocusInfoMapper, DnaLocusInfo>
		implements DnaLocusInfoService {

	private static final String CACHE_NAME = "DnaLocusInfo";

	@Autowired
	private DnaLocusInfoMapper dnaLocusInfoMapper;

	/**
	 * 根据panelId获取对应的基因座列表
	 * 
	 * @param panelId
	 * @return
	 */
	@Override
	@Cacheable(value = CACHE_NAME + ":listByPanelId", key = "#panelId")
	public List<DnaLocusInfo> listByPanelId(Integer panelId) {
		try {
			return this.getBaseMapper().listByPanelId(panelId);
		} catch (Exception ex) {
			log.error("invoke DnaLocusInfoService.listByPanelId error.", ex);
			return null;
		}
	}

	/**
	 * 根据LOCUS_TYPE查询基因座
	 * 
	 * @param locusType
	 * @return
	 */
	@Override
	@Cacheable(value = CACHE_NAME + ":listByLocusType", key = "#locusType")
	public List<DnaLocusInfo> listByLocusType(String locusType) {
		try {
			return this.getBaseMapper().listByLocusType(locusType);
		} catch (Exception ex) {
			log.error("invoke DnaLocusInfoService.listByLocusType error.", ex);
			return null;
		}
	}

	@Override
	@Cacheable(value = CACHE_NAME + ":queryAlleleInfos", key = "#type+':'+#name")
	public List<DnaLocusInfo> queryAlleleInfos(int type, String name) {
		List<DnaLocusInfo> dnaLocusInfos = dnaLocusInfoMapper.queryLocusInfos(type, name);
		return dnaLocusInfos;
	}

	@Override
	public List<DnaLocusInfo> locusPaginationQuery(LocusInfoQuery query) {
		try {
			if(StringUtils.isNotBlank(query.getLocusName())){
				query.setLocusName(query.getLocusName().trim().toUpperCase()); //基因座名称
			}
			List<DnaLocusInfo> strDnaLoucsInfoList = dnaLocusInfoMapper.locusPaginationQuery(query);
			return strDnaLoucsInfoList;
		} catch (Exception ex) {
			log.error("invoke DnaLocusInfoService.locusPaginationQuery error.", ex);
			return null;
		}
	}

	@Override
	public int locusPaginationCount(LocusInfoQuery query) {
		try {
			int count = dnaLocusInfoMapper.paginationCount(query); // 查询分页总计
			return count;
		} catch (Exception ex) {
			log.error("invoke DnaLocusInfoService.locusPaginationCount error.", ex);
			return 0;
		}
	}
	
	@Override
	@Cacheable(value = CACHE_NAME + ":queryLocusInfo")
    public List<DnaLocusInfo> queryLocusInfo() {
        try {
            return dnaLocusInfoMapper.queryLocusInfo();
        }catch(Exception ex){
            log.error("invoke LabServerInfoService.queryLocusInfo error.", ex);
            return null;
        }
    }

	@Override
	public DnaLocusInfo selectByLocusName(String locusName) {
		return dnaLocusInfoMapper.selectByLocusName(locusName);
	}

	@Override
	public String selectById(DnaLocusInfo dnaLocusInfo) {
		return dnaLocusInfoMapper.selectById(dnaLocusInfo) ;
	}

	@Override
	public List<DnaLocusInfo> selectByLocusType(String locusType) {
		return dnaLocusInfoMapper.selectByLocusType(locusType) ;
	}
}
