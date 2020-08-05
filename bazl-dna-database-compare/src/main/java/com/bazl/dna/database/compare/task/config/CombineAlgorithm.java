package com.bazl.dna.database.compare.task.config;

import java.math.BigInteger;
import java.math.RoundingMode;

import com.bazl.dna.exception.DnaRuntimeException;
import com.google.common.math.BigIntegerMath;

/**
 * 按匹配个数分拆集合
 */
public class CombineAlgorithm {
	/* src数组的长度 */
	private int m;

	/* 需要获取N个数 */
	private int n;

	// 临时变量，obj的行数
	private int objLineIndex;

	/* 存放结果的二维数组 */
	private Object[][] obj;
	
	public CombineAlgorithm() {
	}

	/**
	 * 按匹配个数分拆集合
	 * @param src	数组集合
	 * @param getNum		匹配个数
	 * @throws Exception
	 */
	public CombineAlgorithm(Object[] src, int getNum) {
		if (src == null)
			throw new DnaRuntimeException("原数组为空.");
		if (src.length < getNum)
			throw new DnaRuntimeException("要取的数据比原数组个数还大.");
		m = src.length;
		n = getNum;

		/* 初始化 */
		objLineIndex = 0;
		BigInteger big = combination(m, n);
		if (big != null) {
			obj = new Object[big.intValue()][n];
		}

		Object[] tmp = new Object[n];
		combine(src, 0, 0, n, tmp);
	}

	/**
	 * <p>
	 * 计算 C(m,n)个数 = (m!)/(n!*(m-n)!)
	 * </p>
	 * 从M个数中选N个数，函数返回有多少种选法 参数 m 必须大于等于 n m = 0; n = 0; return 1;
	 * 
	 */
	public static BigInteger combination(int m, int n) {
		// 如果总数小于取出的数，直接返回
		if (m < n)
			return null;
		
		BigInteger k = BigIntegerMath.factorial(m);
		BigInteger j = BigIntegerMath.factorial(n).multiply(BigIntegerMath.factorial(m-n));
		return BigIntegerMath.divide(k, j, RoundingMode.UNNECESSARY);
	}
	
	/**
	 * <p>
	 * 递归算法，把结果写到obj二维数组对象
	 * </p>
	 * 
	 * @param src
	 * @param srcIndex
	 * @param i
	 * @param n
	 */
	private void combine(Object[] src, int srcIndex, int i, int n, Object[] tmp) {
		int j;
		for (j = srcIndex; j < src.length - (n - 1); j++) {
			tmp[i] = src[j];
			if (n == 1) {
				System.arraycopy(tmp, 0, obj[objLineIndex], 0, tmp.length);
				objLineIndex++;
			} else {
				n--;
				i++;
				combine(src, j + 1, i, n, tmp);
				n++;
				i--;
			}
		}

	}

	public Object[][] getResult() {
		return obj;
	}

}
