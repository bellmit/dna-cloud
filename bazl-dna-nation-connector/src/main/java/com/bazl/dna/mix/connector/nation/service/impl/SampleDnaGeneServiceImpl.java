package com.bazl.dna.mix.connector.nation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.connector.nation.dao.SampleDnaGeneMapper;
import com.bazl.dna.mix.connector.nation.model.po.SampleDnaGene;
import com.bazl.dna.mix.connector.nation.model.po.newSampleInfo;
import com.bazl.dna.mix.connector.nation.model.vo.SampleDnaGeneVo;
import com.bazl.dna.mix.connector.nation.service.SampleDnaGeneService;
import com.google.common.collect.Lists;

@Service
public class SampleDnaGeneServiceImpl extends BaseService implements SampleDnaGeneService {

	@Autowired
	private SampleDnaGeneMapper sampleDnaGeneMapper;

	@Override
	public SampleDnaGene selectByPrimaryKey(String id) {
		return sampleDnaGeneMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SampleDnaGene> selectByCaseToSingleGeneList(String caseId) {
		try {
			return sampleDnaGeneMapper.selectByCaseToSingleGeneList(caseId);
		} catch (Exception e) {
			logger.error("Error selectByCaseToSingleGeneList: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SampleDnaGene> selectByCaseToMixSampleDnaGeneList(String caseId) {
		try {
			return sampleDnaGeneMapper.selectByCaseToMixSampleDnaGeneList(caseId);
		} catch (Exception e) {
			logger.error("Error selectByCaseToMixSampleDnaGeneList: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SampleDnaGeneVo> queryByMixDnaGene(int page, int size, SampleDnaGeneVo sampleDnaGeneVo) {
		try {
			int startRum = (page - 1) * size;
			int endRum = page * size;
			return sampleDnaGeneMapper.queryByMixDnaGene(startRum, endRum, sampleDnaGeneVo);
		} catch (Exception e) {
			logger.error("Error queryByMixDnaGene: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	@Transactional
	public int updateSampleDnaGeneDF(String sampleGeneId) {
		try {
			return sampleDnaGeneMapper.updateSampleDnaGeneDF(sampleGeneId);
		} catch (Exception e) {
			logger.error("Error queryByMixDnaGene: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public int selectMixGeneByCwsd() {
		try {
			return sampleDnaGeneMapper.selectMixGeneByCwsd();
		} catch (Exception e) {
			logger.error("Error selectMixGeneByCwsd: ", e);
		}
		return 0;
	}

	@Override
	public int selectSingleGeneByCwsd() {
		try {
			return sampleDnaGeneMapper.selectSingleGeneByCwsd();
		} catch (Exception e) {
			logger.error("Error selectSingleGeneByCwsd: ", e);
		}
		return 0;
	}

	@Override
	public List<SampleDnaGeneVo> selectMixGeneByCwsdPage(SampleDnaGeneVo query, int page) {
		int pageNo;
		int pageSize;
		try {
			pageNo = page;
			pageSize = 1000;
			query.setOffset(pageNo * pageSize);
			query.setRows(query.getOffset() + pageSize);
			return sampleDnaGeneMapper.selectMixGeneByCwsdPage(query);
		} catch (Exception e) {
			logger.error("Error selectMixGeneByCwsdPage: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SampleDnaGeneVo> selectSingleGeneByCwsdPage(SampleDnaGeneVo query,PageInfo pageInfo) {
		int pageNo;
		int pageSize;
		try {
			pageNo = pageInfo.getPage();
			pageSize = pageInfo.getEvePageRecordCnt();
			query.setOffset(pageNo * pageSize);
			query.setRows(query.getOffset() + pageSize);
			return sampleDnaGeneMapper.selectSingleGeneByCwsdPage(query);
		} catch (Exception e) {
			logger.error("Error selectSingleGeneByCwsdPage: ", e);
		}
		return Lists.newArrayList();
	}

    @Override
    public List<SampleDnaGeneVo> selectByDateSing(SampleDnaGeneVo query, PageInfo pageInfo) {
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset(pageNo * pageSize);
            query.setRows(query.getOffset() + pageSize);
            return sampleDnaGeneMapper.selectSingByDatePage(query);
        } catch (Exception e) {
            logger.error("Error selectBySingDateList: ", e);
        }
        return Lists.newArrayList();
    }

    @Override
	public int selectSingleCountByDate(Date date) {
		int i = 0;
		try {
			i = sampleDnaGeneMapper.selectSingleCountByDate(date);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询时间段内的基因条数失败！", e.getMessage());
		}
		return i;
	}

	@Override
	public newSampleInfo selectSingleGene(String sampleId,String personCodeType) {
		return sampleDnaGeneMapper.selectSingleGene(sampleId, personCodeType);
	}

	@Override
	public List<SampleDnaGeneVo> countMixDnaGene() {
		try {
			return sampleDnaGeneMapper.countMixDnaGene();
		} catch (Exception e) {
			logger.error("Error countMixDnaGene: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SampleDnaGeneVo> queryByMixDnaGenes(PageInfo pageInfo, SampleDnaGeneVo sampleDnaGeneVo) {
		int pageNo;
		int pageSize;
		try {
			pageNo = pageInfo.getPage();
			pageSize = pageInfo.getEvePageRecordCnt();
			sampleDnaGeneVo.setOffset((pageNo - 1) * pageSize);
			sampleDnaGeneVo.setRows(sampleDnaGeneVo.getOffset() + pageSize);
			return sampleDnaGeneMapper.queryByMixDnaGenes(sampleDnaGeneVo);
		} catch (Exception e) {
			logger.error("Error queryByMixDnaGenes: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public int countMixDnaGenes(SampleDnaGeneVo sampleDnaGeneVo) {
		return sampleDnaGeneMapper.countMixDnaGenes(sampleDnaGeneVo);
	}

	@Override
	public List<SampleDnaGeneVo> selectBySampleNoList(String sampleNo) {
		try {
			return sampleDnaGeneMapper.selectBySampleNoList(sampleNo);
		} catch (Exception e) {
			logger.error("Error selectBySampleNoList: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SampleDnaGeneVo> selectSingleGeneBySampleNo(SampleDnaGeneVo query) {
		try {
			return sampleDnaGeneMapper.selectSingleGeneBySampleNo(query);
		} catch (Exception e) {
			logger.error("Error selectSingleGeneBySampleNo: ", e);
		}
		return Lists.newArrayList();
	}

	@Override
	public List<SampleDnaGene> selectMixedSampleGeneList(String caseId) {
		try {
			return sampleDnaGeneMapper.selectMixedSampleGeneList(caseId);
		} catch (Exception e) {
			logger.error("Error selectMixedSampleGeneList: ", e);
		}
		return Lists.newArrayList();
	}
}
