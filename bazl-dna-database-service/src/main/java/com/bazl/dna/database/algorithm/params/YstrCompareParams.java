package com.bazl.dna.database.algorithm.params;

/**
 * YSTR比对参数
 * Created by lizhihua on 2020-03-03.
 */
public class YstrCompareParams extends CommCompareParams {

    /**
     * YSTR比对默认比对条件
     */
    static int YSTR_COMPARE_DEFAULT_SAME_COUNT = 17;
    static int YSTR_COMPARE_DEFAULT_DIFF_COUNT = 0;

    public static YstrCompareParams DEFAULT_PARAMS(){
        return defaultStrSameCompareParams();
    }

    private static YstrCompareParams defaultStrSameCompareParams(){
        return new YstrCompareParams(YSTR_COMPARE_DEFAULT_SAME_COUNT, YSTR_COMPARE_DEFAULT_DIFF_COUNT);
    }

    public YstrCompareParams(int lowestSameLimit, int mostDiffLimit){
        this.lowestSameLimit = lowestSameLimit;
        this.mostDiffLimit = mostDiffLimit;
    }

}
