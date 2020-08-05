package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DnaPanelInfoMapper;
import com.bazl.dna.database.service.mapper.DnaPanelLocusMapper;
import com.bazl.dna.database.service.model.bo.PanelInfoModel;
import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import com.bazl.dna.database.service.service.DnaPanelLocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 试剂盒基因座关系表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class DnaPanelLocusServiceImpl extends ServiceImpl<DnaPanelLocusMapper, DnaPanelLocus> implements DnaPanelLocusService {

	private static final String CACHE_NAME = "DnaPanelLocus";
	private static final String CACHE_NAME_PANELINFO = "DnaPanelInfo";
	
    @Autowired
    private DnaPanelLocusMapper dnaPanelLocusMapper;
    @Autowired
    private DnaPanelInfoMapper  dnaPanelInfoMapper;

    @Override
    public List<DnaPanelLocus> listByPanelId(Integer dnaPanelId) {
        try {
             return dnaPanelLocusMapper.listByPanelId(dnaPanelId);
            } catch (Exception ex) {
            log.error("invoke DnaPanelLocusService.listByPanelId error", ex);
            return null;
        }
    }

    public int isSelectId(DnaPanelLocus dnaPanelLocus) {
        return dnaPanelLocusMapper.isSelectId(dnaPanelLocus);
    }

    @Override
    @Caching(evict = {
    		@CacheEvict(value = CACHE_NAME, allEntries = true),
    		@CacheEvict(value = CACHE_NAME_PANELINFO, allEntries = true)
    	})
    @SuppressWarnings("all")
    public boolean savePanelLoucsInfo(PanelInfoModel panelInfoModel) throws Exception {

        try{
			if (panelInfoModel.getPanelInfo() != null) {
                //先查询本地试剂盒信息，没有就新增
                DnaPanelInfo  dnaPanelInfo = dnaPanelInfoMapper.selectById(panelInfoModel.getPanelInfo().getId());
                //保存试剂盒信息,有更新信息，没有进行新增
                if (dnaPanelInfo!=null) {
                    //保存试剂盒信息
                    DnaPanelInfo dnaPanelLocus = panelInfoModel.getPanelInfo();
                    dnaPanelInfoMapper.updateById(dnaPanelLocus);
                    //保存试剂盒对应基因座信息
                    List<DnaPanelLocus> dnaPanelLocusList = panelInfoModel.getLocusInfoList();
					if (dnaPanelLocusList != null && !dnaPanelLocusList.isEmpty()) {
                        for (DnaPanelLocus panelLocus : dnaPanelLocusList) {
                        		panelLocus.setPanelId(dnaPanelInfo.getId());//试剂盒主键ID
                            if (panelLocus.getId() == null) {
	                        		dnaPanelLocusMapper.insert(panelLocus);
	                        } else {
	                        		dnaPanelLocusMapper.updateById(panelLocus);
	                        }
                        }
                    }
                }else if (dnaPanelInfo == null){
                    //保存试剂盒信息
                    DnaPanelInfo dnaPanelInfo1 = panelInfoModel.getPanelInfo();
                    dnaPanelInfoMapper.insert(dnaPanelInfo1);
                    //保存基因座信息
                    List<DnaPanelLocus> dnaPanelLocusList = panelInfoModel.getLocusInfoList();
                    if (dnaPanelLocusList != null && !dnaPanelLocusList.isEmpty()) {
                        for (DnaPanelLocus panelLocus : dnaPanelLocusList) {
                            panelLocus.setPanelId(dnaPanelInfo1.getId());//试剂盒主键ID
                            dnaPanelLocusMapper.insert(panelLocus);
                        }
                    }
                }
            }
        }catch (Exception ex){
            log.error("invoke DnaPanelLocusService.savePanelLoucsInfo error",ex);
            throw new Exception("保存试剂盒信息及其基因座信息异常！"+ex.getMessage());
        }
        return false;
    }

}
