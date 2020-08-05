package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * DNA样本信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface DnaSampleInfoMapper extends BaseMapper<DnaSampleInfo> {

    /**
     * 根据caseId查询检材列表
     * @param caseId
     * @return
     */
    public List<DnaSampleInfoVo> selectVoListByCaseId(Integer caseId);

    //根据主键id查询样本信息
    public DnaSampleInfoVo selectByIdVoList(Integer id);

    /*
    *   根据关联人员 id  查询检材信息
    * */
    List<DnaSampleInfoVo> selectVoListPersonId(Integer personId);

    /**
     * 根据实验室编号查询样本信息
     * @param labServerNo
     * @return
     */
    List<DnaSampleInfo> selectSampleByLabServerNo(String labServerNo);

    /**
     * 查询全部样本数
     * @return
     */
    public  int selectAllSampleCount();
}
