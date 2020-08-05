package com.bazl.dna.database.algorithm.params;

/**
 * 亲缘比对参数类
 * Created by lizhihua on 2020-03-10.
 */
public class StrRelativeCompareParams extends CommCompareParams {
    /**
     * 亲缘三联体比对默认比对条件
     */
    static int RELATIVE_FMZ_DEFAULT_SAME_COUNT = 15;
    static int RELATIVE_FMZ_DEFAULT_DIFF_COUNT = 0;

    /**
     * 亲缘单亲比对默认比对条件
     */
    static int RELATIVE_SINGLE_DEFAULT_SAME_COUNT = 15;
    static int RELATIVE_SINGLE_DEFAULT_DIFF_COUNT = 0;

    /**
     * 默认三联体比对条件构造
     * @return
     */
    public static StrRelativeCompareParams DEFAULT_FMZ_PARAMS(){
        return defaultRelativeFmzCompareParams();
    }

    private static StrRelativeCompareParams defaultRelativeFmzCompareParams(){
        return new StrRelativeCompareParams(RELATIVE_FMZ_DEFAULT_SAME_COUNT, RELATIVE_FMZ_DEFAULT_DIFF_COUNT);
    }

    /**
     * 默认单亲比对条件构造
     * @return
     */
    public static StrRelativeCompareParams DEFAULT_SINGLE_PARAMS(){
        return defaultRelativeSingleCompareParams();
    }

    private static StrRelativeCompareParams defaultRelativeSingleCompareParams(){
        return new StrRelativeCompareParams(RELATIVE_SINGLE_DEFAULT_SAME_COUNT, RELATIVE_SINGLE_DEFAULT_DIFF_COUNT);
    }

    public StrRelativeCompareParams(int lowestSameLimit, int mostDiffLimit){
        this.lowestSameLimit = lowestSameLimit;
        this.mostDiffLimit = mostDiffLimit;
    }
}
