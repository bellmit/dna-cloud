package com.bazl.dna.database.algorithm.params;

/**
 * Created by lizhihua on 2020-03-03.
 */
public class StrSameCompareParams extends CommCompareParams {

    /**
     * 同型比对默认比对条件
     */
    static int SAME_COMPARE_DEFAULT_SAME_COUNT = 15;
    static int SAME_COMPARE_DEFAULT_DIFF_COUNT = 0;

    public static StrSameCompareParams DEFAULT_PARAMS(){
        return defaultStrSameCompareParams();
    }

    private static StrSameCompareParams defaultStrSameCompareParams(){
        return new StrSameCompareParams(SAME_COMPARE_DEFAULT_SAME_COUNT, SAME_COMPARE_DEFAULT_DIFF_COUNT);
    }

    public StrSameCompareParams(int lowestSameLimit, int mostDiffLimit){
        this.lowestSameLimit = lowestSameLimit;
        this.mostDiffLimit = mostDiffLimit;
    }

}
