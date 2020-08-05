package com.bazl.dna.lims.utils;

/**
 * 判断IP是否在本区域的ip段中
 */
public class IPsegmentUtil {

    public static boolean isIpValid(String requestIp, String validIpRanges) throws Exception {

        String[] ipList = validIpRanges.split(",");
        for (int i=0; i < ipList.length; i++) {
            if (inIpSpan(requestIp, ipList[i].substring(1, ipList[i].length() - 1))) {
                return true;
            }
        }

        return false;
    }


    private static boolean inIpSpan(String requestIpStr, String validIpRange) throws Exception {
        boolean result = true;
        if (requestIpStr.equals("127.0.0.1")
                || requestIpStr.equals("0:0:0:0:0:0:0:1")) {
            return true;
        }

        String[] ipColumn = requestIpStr.split("[.]");
        if (ipColumn.length != 4) {
            throw new Exception("无效的ip地址：" + requestIpStr);
        }

        String[] ipRangeArr = validIpRange.split("-");
        String startIp = ipRangeArr[0];
        String endIp = ipRangeArr[1];

        String[] startIpColumn = startIp.split("[.]");
        String[] endIpColumn = endIp.split("[.]");
        if (startIpColumn.length != 4 || endIpColumn.length != 4) {
            throw new Exception("无效的ip地址：" + startIpColumn + ", " + endIpColumn);
        }

        try {
            for (int i = 0; i < 4; i++) {
                int start = Integer.parseInt(startIpColumn[i]);
                int end = Integer.parseInt(endIpColumn[i]);
                int test = Integer.parseInt(ipColumn[i]);
                if (!(test >= start && test <= end)) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            throw e;
        }

        return result;
    }

/*

    public static boolean inIpsegment(String ipAddress,String ipSegmentStr){
        //1.首先解析IP段
        String ipSegStr[]=ipSegmentStr.split("-");
        String startIP[]=ipSegStr[0].split("[.]");
        String endIP[]=ipSegStr[1].split("[.]");
        String testIP[]=ipAddress.split("[.]");
        for (int i = 0; i < 4; i++) {
            int start = Integer.parseInt(startIP[i]);
            int end = Integer.parseInt(endIP[i]);
            int test = Integer.parseInt(testIP[i]);
            if (test >= start && test <= end){
                return true;
            }else {
                return false;
            }
        }
        return true;
    }
*/

}
