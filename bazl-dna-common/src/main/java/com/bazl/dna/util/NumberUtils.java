package com.bazl.dna.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/***
 * <p>
 * Description: 数值工具类
 * </p>
 * 
 * @author lrm
 * @date 2018年8月3日
 */
public class NumberUtils {

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	/***
	 * 下取整
	 */
	public static final String ROUNDING_MODE_FLOOR = "floor";

	/***
	 * 上取整
	 */
	public static final String ROUNDING_MODE_CEIL = "ceil";

	/***
	 * 四舍五入
	 */
	public static final String ROUNDING_MODE_ROUND = "round";

	private NumberUtils() {

	}

	/***
	 * 把数据转换成两位小数精度
	 * 
	 * @param source
	 * @return
	 */
	public static String df2(double source) {
		return df2.format(source);
	}

	/***
	 * 把数据转换成两位小数精度(四舍五入)
	 * 
	 * @param source
	 * @return
	 */
	public static double format2precision(double source) {
		BigDecimal b = BigDecimal.valueOf(source);
		double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}

	/***
	 * 取数组中的中位数(奇数-取中间|偶数-取中间两个数平均值)
	 * 
	 * @param dataList
	 * @@param roundingMode floor-下取整|ceil-上取整|round-四舍五入(默认)
	 * @return 中位数值
	 */
	public static Integer getMedianData(List<Integer> dataList, String roundingMode) {
		Integer medianData = 0;
		if (!dataList.isEmpty()) {
			int count = dataList.size();
			int mid = count / 2;
			if (count % 2 == 0) {// 偶数-取中间两个数再求平均
				if (ROUNDING_MODE_FLOOR.equals(roundingMode)) {
					medianData = (int) Math.floor((dataList.get(mid) + dataList.get(mid + 1)) / 2d);
				} else if (ROUNDING_MODE_CEIL.equals(roundingMode)) {
					medianData = (int) Math.ceil((dataList.get(mid) + dataList.get(mid + 1)) / 2d);
				} else {
					medianData = Math.round((dataList.get(mid) + dataList.get(mid + 1)) / 2f);
				}

			} else {// 奇数-最中间数据
				medianData = dataList.get(mid);
			}
		}
		return medianData;
	}

	/***
	 * 求平均数
	 * 
	 * @param dataList
	 * @return
	 */
	public static double getAvgIntegerData(List<Integer> dataList) {
		double avgData = 0;
		if (!dataList.isEmpty()) {
			int count = dataList.size();
			double sum = 0;
			for (Integer data : dataList) {
				sum += data;
			}
			avgData = sum / count;
		}
		return avgData;
	}

	/***
	 * 求平均数
	 * 
	 * @param dataList
	 * @return
	 */
	public static double getAvgDoubleData(List<Double> dataList) {
		double avgData = 0;
		if (!dataList.isEmpty()) {
			int count = dataList.size();
			double sum = 0;
			for (Double data : dataList) {
				sum += data;
			}
			avgData = sum / count;
		}
		return avgData;
	}

	/***
	 * 数据列表按升序排序
	 * 
	 * @param dataList
	 */
	public static void listSortAsc(List<Integer> dataList) {
		Collections.sort(dataList, new Comparator<Integer>() {
			/*
			 * int compare(Integer i1, Integer i2) 返回一个基本类型的整型， 返回负数表示：i1 小于i2， 返回0
			 * 表示：i1和i2相等， 返回正数表示：i1大于i2
			 */
			public int compare(Integer i1, Integer i2) {
				// 按照Integer进行升序排列
				if (i1 > i2) {
					return 1;
				}
				if (i1.equals(i2)) {
					return 0;
				}
				return -1;
			}
		});
	}
	
	public static String GetPercentStr(int num1, int num2, int lenAfterDot){
        if(num1 == 0 || num2 == 0){
            return "0.00%";
        }

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(lenAfterDot);

        String result = numberFormat.format((float)num1/(float)num2*100);

        return result + "%";
    }

    public static boolean isNumeric(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
