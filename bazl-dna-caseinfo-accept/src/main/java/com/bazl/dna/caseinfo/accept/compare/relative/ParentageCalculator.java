package com.bazl.dna.caseinfo.accept.compare.relative;

import com.bazl.dna.caseinfo.accept.compare.relative.po.Marker;
import com.bazl.dna.caseinfo.accept.compare.relative.po.ParentageMatchOptions;
import com.bazl.dna.caseinfo.accept.compare.relative.po.ParentageMatchResult;

import java.util.Map;

/**
 * Created by Sun on 2019/4/2.
 */
public interface ParentageCalculator {

    ParentageMatchResult calculate(Map<String, Marker> var1, Map<String, Marker> var2, Map<String, Marker> var3, ParentageMatchOptions var4);

}
