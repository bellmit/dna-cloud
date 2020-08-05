package com.bazl.dna.mix.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.MobileNewsMapper;
import com.bazl.dna.mix.model.po.MobileNews;
import com.bazl.dna.mix.service.MobileNewsService;

@Service
public class MobileNewsServiceImpl extends MixBaseService implements MobileNewsService {
	
	@Autowired
	private MobileNewsMapper mapper;

	@Override
	@Transactional
	public int deleteByPrimaryKey(String id) {
		try {
			return mapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
    		logger.error("Error deleteByPrimaryKey: ", e);
            throw new DnaRuntimeException();
    	}
	}

	@Override
	@Transactional
	public int insert(MobileNews record) {
		try {
			return mapper.insert(record);
		} catch (Exception e) {
    		logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
    	}
	}

	@Override
	public MobileNews selectByPrimaryKey(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<MobileNews> selectAll() {
		return mapper.selectAll();
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(MobileNews record) {
		try {
			return mapper.updateByPrimaryKey(record);
		} catch (Exception e) {
    		logger.error("Error updateByPrimaryKey: ", e);
            throw new DnaRuntimeException();
    	}
	}

	@Override
	public Map<String, Object> getUserMobileNumber(String id) {
		return mapper.getUserMobileNumber(id);
	}

	@Override
	public List<MobileNews> selectPageAll(MobileNews mobileNews, PageInfo pageInfo) {
		List<MobileNews> result = null;
		int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            mobileNews.setOffset((pageNo - 1) * pageSize);
            mobileNews.setRows(pageInfo.getEvePageRecordCnt());
            result = mapper.selectPageAll(mobileNews);
        } catch(Exception ex) {
            logger.info("查询报错："+ex);
        }
		return result;
	}

	@Override
	public int selectPageCount(MobileNews mobileNews) {
		return mapper.selectPageCount(mobileNews);
	}

	@Override
	public int selectCount(MobileNews mobileNews) {
		return mapper.selectCount(mobileNews);
	}

	@Override
	public List<MobileNews> selectPcList(MobileNews mobileNews) {
		return mapper.selectPcList(mobileNews);
	}

	@Override
	public List<MobileNews> getUserNewsList(MobileNews mobileNews) {
		return mapper.getUserNewsList(mobileNews);
	}

	@Override
	public int selectByMobileFlag(String userid) {
		int i = 0;
		try {
			i = mapper.selectByMobileFlag(userid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询新消息失败！" + e.getMessage());
		}
		return i;
	}

}
