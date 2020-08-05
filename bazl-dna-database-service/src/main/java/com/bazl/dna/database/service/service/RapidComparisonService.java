package com.bazl.dna.database.service.service;

import java.util.ArrayList;

import com.bazl.dna.database.algorithm.result.StrRelativeCompareResult;
import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.algorithm.result.YstrCompareResult;
import com.bazl.dna.database.service.model.po.KinshipCompare;
import com.bazl.dna.database.service.model.po.StrCompare;

public interface RapidComparisonService  {
    ArrayList<StrSameCompareResult> compareStrGeneInfo(StrCompare strCompare);

    ArrayList<StrRelativeCompareResult> compareKinshipGeneInfo(KinshipCompare kinshipCompare);

    ArrayList<YstrCompareResult> compareYstrGeneInfo(StrCompare strCompare);
}
