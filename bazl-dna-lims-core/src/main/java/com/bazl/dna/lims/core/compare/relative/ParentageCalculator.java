package com.bazl.dna.lims.core.compare.relative;

import com.bazl.dna.lims.core.compare.relative.po.Marker;
import com.bazl.dna.lims.core.compare.relative.po.ParentageMatchOptions;
import com.bazl.dna.lims.core.compare.relative.po.ParentageMatchResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Sun on 2019/4/2.
 */
public interface ParentageCalculator {

    ParentageMatchResult calculate(Map<String, Marker> var1, Map<String, Marker> var2, Map<String, Marker> var3, ParentageMatchOptions var4);

}
