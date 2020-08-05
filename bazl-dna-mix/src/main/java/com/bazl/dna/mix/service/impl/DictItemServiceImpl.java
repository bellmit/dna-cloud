package com.bazl.dna.mix.service.impl;

import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.DictItemDAO;
import com.bazl.dna.mix.model.po.DictItem;
import com.bazl.dna.mix.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by hj on 2018/12/20.
 */
@Service
public class DictItemServiceImpl extends MixBaseService implements DictItemService {

    @Autowired
    DictItemDAO dictItemDAO;

    @Override
    public List<DictItem> selectListByDictCode(String dictCode) {

        return dictItemDAO.selectListByDictCode(dictCode);
    }

    @Override
    public List<DictItem> selectAllCode() {
        List<DictItem> listDictItem = dictItemDAO.selectAllCode();
        return listDictItem;
    }

    /**
     * 根据dictTypeCode查询出对应字典项内容
     * @param dictTypeCode
     * @return
     */
    @Override
    public List<DictItem> selectListByDictTypeCode(String dictTypeCode) {
        return dictItemDAO.selectListByDictTypeCode(dictTypeCode);
    }

    @Override
    public List<DictItem> selectListByDictType(String dictTypeCode, String createPerson) {
        return dictItemDAO.selectListByDictType(dictTypeCode,createPerson);
    }

    @Override
    @Transactional
    public int updateSampleNoVal(DictItem dictItem) {
    	try {
    		return dictItemDAO.updateByPrimaryKeySelective(dictItem);
    	} catch (Exception e) {
    		logger.error("Error updateSampleNoVal: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int insertSampleNoVal(DictItem dictItem) {
    	try {
    		return dictItemDAO.insert(dictItem);
    	} catch (Exception e) {
    		logger.error("Error insertSampleNoVal: ", e);
    		throw new DnaRuntimeException();
    	}
    }
}
