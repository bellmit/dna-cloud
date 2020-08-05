package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.MarkerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sun on 2019/4/2.
 */
@Repository
public interface MarkerInfoService {

    public List<MarkerInfo> selectAll();

}
