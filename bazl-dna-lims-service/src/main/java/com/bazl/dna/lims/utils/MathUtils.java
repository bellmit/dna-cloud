package com.bazl.dna.lims.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2017/1/18.
 */
public class MathUtils {

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

    /**
     * * 两个Double数相加 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }
/*
    public static void main(String[] args) {
        System.out.println(isNumeric("222.100"));
    }*/
}
