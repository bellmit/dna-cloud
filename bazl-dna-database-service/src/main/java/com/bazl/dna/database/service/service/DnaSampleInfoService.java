package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;

import java.util.List;

/**
 * <p>
 * DNA样本信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface DnaSampleInfoService extends IService<DnaSampleInfo> {

    /**
     * 根据案件id查询所有的样本信息
     * @param caseId
     * @return
     */
    List<DnaSampleInfoVo> selectByCaseId(Integer caseId);

    /*
    *   根据主键id查询样本信息
    * */
    DnaSampleInfoVo selectById(Integer id);

    /*
    *   根据关联人员 id  查询检材信息
    * */
    List<DnaSampleInfoVo> selectByPersonId(Integer personId);

    /**
     * 根据实验室编号查询样本信息
     * @param labServerNo
     * @return
     */
    List<DnaSampleInfo> selectSampleByLabServerNo(String labServerNo);

    /**
     * 查询全部检材数
     * @return
     */
    public int selectAllSampleCount();
}
