package com.bazl.dna.sys;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.BigIntegerMath;
import com.google.common.math.IntMath;

public class CombineAlgorithm {
	/* src数组的长度 */
	private int m;

	/* 需要获取N个数 */
	private int n;

	// 临时变量，obj的行数
	private int objLineIndex;

	/* 存放结果的二维数组 */
	public Object[][] obj;
	
	public CombineAlgorithm() {
	}

	/**
	 * 按匹配个数分拆集合
	 * @param src	数组集合
	 * @param getNum		匹配个数
	 * @throws Exception
	 */
	public CombineAlgorithm(Object[] src, int getNum) throws Exception {
		if (src == null)
			throw new Exception("原数组为空.");
		if (src.length < getNum)
			throw new Exception("要取的数据比原数组个数还大.");
		m = src.length;
		n = getNum;

		/* 初始化 */
		objLineIndex = 0;
		BigInteger big = combination(m, n);
		obj = new Object[big.intValue()][n];

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
		if (m < n)
			return null; // 如果总数小于取出的数，直接返回0

//		BigInteger k = new BigInteger("1");
//		BigInteger j = new BigInteger("1");
//		// 该种算法约掉了分母的(m-n)!,这样分子相乘的个数就是有n个了
//		for (int i = n; i >= 1; i--) {
//			k = k.multiply(new BigInteger(String.valueOf(m)));
//			j = j.multiply(new BigInteger(String.valueOf(n)));
//			m--;
//			n--;
//		}
//		return k.divide(j);
		
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
				// obj[objLineIndex] = tmp;
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

	public static void main(String[] args) throws Exception {
		String[] strs = new String[] {
				"AMELOGENIN", "TH01", "TPOX", "CSF1PO", "D18S51", "D21S11"};
		
		CombineAlgorithm ca = new CombineAlgorithm(strs, 3);

		List<Object[]> objList = Arrays.asList(ca.getResult());
		List<List<Object[]>> parts = Lists.partition(objList , IntMath.divide(objList.size(), 3, RoundingMode.CEILING));
		parts.parallelStream().forEach(list -> {
			process(list);
			System.out.println();
		});
		System.out.println("size:"+objList.size());
	}

	private static void process(List<Object[]> list) {
		for (Object[] o : list) {
			List<Object> l = Arrays.asList(o);
			Map<String, Object> paramsMap = Maps.newHashMap();
			for (Object obj : l) {
				paramsMap.put("virtual_" + obj, org.apache.commons.lang.math.RandomUtils.nextInt());
			}
			System.out.println(Thread.currentThread() + ":" + paramsMap);
		}
	}
	
}
