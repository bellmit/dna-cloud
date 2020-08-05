package com.bazl.dna.mix.service.impl;

import com.bazl.dna.mix.dao.QualtyPersonnelDAO;
import com.bazl.dna.mix.model.po.QualtyPersonnel;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.service.QualtyPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/10 16:31
 * @Version: 1.0
 */
@Service
public class QualtyPersonnelServiceImpl extends MixBaseService implements QualtyPersonnelService {

    @Autowired
    QualtyPersonnelDAO qualtyPersonnelDAO;

    /**
     * 查询比中的质控人员样本信息
     * @return
     */
    @Override
    public QualtyPersonnel selectMixedSampleQualityDetails(String id) {
        QualtyPersonnel qualtyPersonnel = null;
        try {
            qualtyPersonnel = qualtyPersonnelDAO.selectMixedSampleQualityDetails(id);
        } catch (Exception ex) {
            logger.error("查询比中的质控人员样本信息失败！！！", ex.getMessage());
        }
        return qualtyPersonnel;
    }

    /**
     * 根据质控人员id查询质控基因信息
     * @param ratiogeneId
     * @return
     */
    @Override
    public List<QualtyPersonnel> selectMixedSampleQuality(String ratiogeneId) {
        List<QualtyPersonnel>   qualtyPersonnelList=null;
        try {

            qualtyPersonnelList = qualtyPersonnelDAO.selectMixedSampleQuality(ratiogeneId);
        } catch (Exception ex) {
            logger.error("根据质控人员id查询质控基因信息失败！！！", ex.getMessage());
        }
        return qualtyPersonnelList;
    }

    /**
     * 查询质控人员基因信息
     * @return
     * @param page
     * @param size
     */
    @Override
    public List<QualtyPersonnel> selectAll(int page,int size) {
        List<QualtyPersonnel>   qualtyPersonnelList=null;
        int startRum = page*size;
        int endRum = (page+1)*size;
        try {

            qualtyPersonnelList = qualtyPersonnelDAO.selectAll(startRum,endRum);
        } catch (Exception ex) {
            logger.error("查询质控基因信息失败！！！", ex.getMessage());
        }
        return qualtyPersonnelList;
    }

    /**
     * 提交本案查询质控人员
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selecAllSampleQualityList(int page, int size) {
        List<SingleSampleGeneVo>   qualtyPersonnelList=null;
        int startRum = page*size;
        int endRum = (page+1)*size;
        try {
            qualtyPersonnelList = qualtyPersonnelDAO.selecAllSampleQualityList(startRum,endRum);
        } catch (Exception ex) {
            logger.error("查询质控基因信息失败！！！", ex.getMessage());
        }
        return qualtyPersonnelList;
    }

    @Override
    public List<QualtyPersonnel> selectAllList() {
        return qualtyPersonnelDAO.selectAllList();
    }
}
