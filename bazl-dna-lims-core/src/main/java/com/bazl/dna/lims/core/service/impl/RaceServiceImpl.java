package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.model.po.Race;
import com.bazl.dna.lims.core.dao.RaceMapper;
import com.bazl.dna.lims.core.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sun on 2019/4/2.
 */
@Service
public class RaceServiceImpl implements RaceService {

    @Autowired
    RaceMapper raceMapper;

    @Override
    public List<Race> selectAll() {
        return raceMapper.selectAll();
    }
}
