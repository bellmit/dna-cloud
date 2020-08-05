package com.bazl.dna.lims.core.service;


import com.bazl.dna.lims.core.model.po.UseInstruments;

import java.util.List;

/**
 * @author hj
 * @date 2019/9/2.
 */
public interface UseInstrumentsService {

    List<UseInstruments> findByExtractMentod(UseInstruments useInstrumentsEntity);
}
