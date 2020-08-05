package com.bazl.dna.test.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.test.dao.ISampleGeneDao;
import com.bazl.dna.test.entity.SampleGene;
import com.bazl.dna.test.mapper.SampleGeneMapper;
import com.bazl.dna.test.service.ISampleGeneService;

@Service
public class SampleGeneServiceImpl implements ISampleGeneService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleGeneServiceImpl.class);
	
	@Autowired
	private ISampleGeneDao sampleGeneDao;
	
	@Autowired
	private SampleGeneMapper mapper;

	@Override
	@Transactional
	public SampleGene save(SampleGene entity) {
		try {
			return sampleGeneDao.save(entity);
		} catch (Exception e) {
			LOGGER.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public SampleGene getById(String id) {
		return sampleGeneDao.getOne(id);
	}

	@Override
	@Transactional
	public void delete(String id) {
		try {
			sampleGeneDao.deleteById(id);
		} catch (Exception e) {
			LOGGER.error("Error delete: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	public List<Map<String, Object>> findGrantList(Map<String, Object> map) {
		return mapper.findGrantList(map);
	}

}
