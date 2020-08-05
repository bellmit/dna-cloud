package com.bazl.dna.mix.algorithm.params;

/**
 * 通用比对参数类
 * Created by lizhihua on 2020-03-10.
 */
public class CompareParams {

    /**
     * 匹配下限
     */
    protected int lowestSameLimit;

    /**
     * 容差上限
     */
    protected int mostDiffLimit;

    public int getLowestSameLimit() {
        return lowestSameLimit;
    }

    public void setLowestSameLimit(int lowestSameLimit) {
        this.lowestSameLimit = lowestSameLimit;
    }

    public int getMostDiffLimit() {
        return mostDiffLimit;
    }

    public void setMostDiffLimit(int mostDiffLimit) {
        this.mostDiffLimit = mostDiffLimit;
    }
}
