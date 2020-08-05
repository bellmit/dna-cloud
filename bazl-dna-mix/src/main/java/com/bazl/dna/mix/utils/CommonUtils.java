package com.bazl.dna.mix.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author wanghaiyang
 * @date 2019/4/3.
 */
public class CommonUtils {

    /**
     * 判断一个数组是否包含另一个
     * @param a
     * @param b
     * @return
     */
    public static boolean containArray(String[] a, String[] b) {
        boolean flag = false;
        int k = 0;
        /**统计b中包含a中的元素是否与a的元素个数相同*/
        if (a.length < b.length) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    if (a[i] == b[j]) {
                        k++;
                        System.out.println(a[i] +"/"+ b[j]);
                        continue;
                    }
                }
            }
        }

        if (k == a.length) {
            flag = true;
        }

        return flag;
    }


    /**
     * 转换基因型格式
     * @param genotypeString
     * @return
     */
    public static String transfromGeneFormat(Map<String, String> genotypeString) {
        String geneInfo = null;
        Map<String, Object> result = null;
        if (genotypeString.size() > 0) {
            result = new LinkedHashMap<>();
            for(String key : genotypeString.keySet()){
                if(genotypeString.containsKey(key)){
                    if (!genotypeString.get(key).isEmpty()) {
                        String[] genotype = genotypeString.get(key).split(",");
                        if (genotype != null && genotype.length > 0) {
                            result.put(key, genotype);
                        }
                    }
                }
            }
        }

        if (result != null) {
            JSONObject json = new JSONObject(result);
            geneInfo = json.toJSONString();
        }

        return geneInfo;
    }

    /**
     * 拆分基因型大字段
     * @param string
     * @param chr
     * @return
     */
    private static String[] splitString (String string, char chr)
    {
        if(string != null && !"".equals(string))
        {
            Vector<Integer> indexs = new Vector<Integer>();
            char[] chars = string.toCharArray();
            for (int i = 0; i < string.length(); i++)
            {
                if(chars[i] == chr)
                {
                    indexs.add(Integer.valueOf(i));
                }
            }
            List<String> splitedString = new ArrayList<String>();
            for (int i = 0; i < indexs.size(); i++)
            {
                if(chr=='[' || chr == '<')
                {
                    if(i <indexs.size()-1)
                    {
                        int fromIndex = indexs.get(i);
                        int endIndex = indexs.get(i+1);
                        if(null != string.substring(fromIndex, endIndex)&& !"".equals(string.substring(fromIndex, endIndex)))
                        {
                            splitedString.add(string.substring(fromIndex, endIndex));
                        }
                    }else
                    {
                        if(null != string.substring(indexs.get(i), string.length())&& !"".equals(string.substring(indexs.get(i), string.length())))
                        {
                            splitedString.add(string.substring(indexs.get(i), string.length()));
                        }
                    }
                }
                else
                {
                    if(indexs.size() >1)
                    {
                        if(i == 0)
                        {
                            if(null != string.substring(1,indexs.get(i)) && !"".equals(string.substring(1,indexs.get(i))))
                            {
                                splitedString.add(string.substring(1,indexs.get(i)));
                            }
                        }
                        else
                        {
                            int beginIndex = indexs.get(i-1);
                            int endIndex = indexs.get(i);
                            if(null != string.substring(beginIndex+1, endIndex) && !"".equals(string.substring(beginIndex+1, endIndex)))
                            {
                                splitedString.add(string.substring(beginIndex+1, endIndex));
                            }
                        }
                        if(i == indexs.size()-1)
                        {
                            if(null != string.substring(indexs.get(indexs.size()-1)+1, string.length()-1)&&
                                    !"".equals(string.substring(indexs.get(indexs.size()-1)+1, string.length()-1)))
                            {
                                splitedString.add(string.substring(indexs.get(indexs.size()-1)+1, string.length()-1));
                            }
                        }
                    }
                    else if(indexs.size() ==1)
                    {
                        if(null != string.substring(1, indexs.get(i)) && !"".equals(string.substring(1, indexs.get(i))))
                        {
                            splitedString.add(string.substring(1, indexs.get(i)));
                        }
                        if(null != string.substring(indexs.get(i)+1, string.length()-1) &&
                                !"".equals(string.substring(indexs.get(i)+1, string.length()-1)))
                        {
                            splitedString.add(string.substring(indexs.get(i)+1, string.length()-1));
                        }
                    }
                }
            }
            return splitedString.toArray(new String[0]);
        }
        return null;
    }

    /**
     * 解析基因型大字段
     * @param genotypeString
     * @return
     */
    public static Map<String,String> convertGenoTypeString(String genotypeString)
    {
        Map<String,String> result = new LinkedHashMap<String,String>();

        if(genotypeString == null || genotypeString.equals("")) {
            return result;
        }

        String[] splitedString = CommonUtils.splitString (genotypeString, '[');

        for (int i = 0; i < splitedString.length; i++)
        {
            int index1 = splitedString[i].indexOf("[");
            int index2 = splitedString[i].indexOf("]");
            String markerName = splitedString[i].substring(index1+1, index2);
            String alleleStrings = splitedString[i].substring(index2+1, splitedString[i].length());
            String[] markerAlleles = CommonUtils.splitString(alleleStrings,'<');
            if(markerAlleles == null) {
                return null;
            }

            String temp = "";
            for (int j = 0; j < markerAlleles.length; j++)
            {
                String[] alleleInfoString = CommonUtils.splitString(markerAlleles[j],',');
                if(alleleInfoString.length > 0)
                {
                    temp += alleleInfoString[0] + ",";
                }
            }
            result.put(markerName, temp.length()>0?temp.substring(0,temp.length() -1):temp);
        }
        return result;
    }

    public static boolean isMixSample(String genotypeString)
    {
        if(genotypeString == null || genotypeString.equals("")) {
            return false;
        }

        String[] splitedString = CommonUtils.splitString (genotypeString, '[');

        for (int i = 0; i < splitedString.length; i++)
        {
            int index2 = splitedString[i].indexOf("]");
            String alleleStrings = splitedString[i].substring(index2+1, splitedString[i].length());
            String[] markerAlleles = CommonUtils.splitString(alleleStrings,'<');
            if(markerAlleles == null) {
                return false;
            }

            if(markerAlleles.length >2) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        Map<String,String> temp = convertGenoTypeString("[D8S1179]<10,131.1,234,2105><12,139.5,403,3772><14,148.7,138,1358>[D21S11]<29,204.4,114,772><30,208.3,216,1425><31,212.3,340,2241>[D7S820]<9,267.8,74,616><11,275.9,239,1927><12,280.1,59,512>[CSF1PO]<10,322.4,322,2996><11,326.6,111,977><12,330.6,165,1564>[D3S1358]<15,123.7,183,1922><16,127.7,354,3606><17,132.0,277,2924>[TH01]<9,183.1,1501,10297><9,183.1,1501,10297>[D13S317]<8,217.0,510,3456><11,229.0,269,1986><12,233.1,251,1903>[D16S539]<9,268.6,230,2061><10,272.7,265,2174><11,276.6,211,1792>[D2S1338]<17,316.4,184,1883><19,324.7,73,768><21,333.0,239,2189><23,340.7,187,1500>[D19S433]<13,117.2,102,1288><14,121.1,261,3299><16.2,131.2,147,1694>[vWA]<14,167.2,619,4462><14,167.2,619,4462>[TPOX]<8,230.1,377,3289><11,242.0,247,2156><12,246.0,94,853>[D18S51]<13,287.1,158,1354><15,295.4,144,1135><23,329.9,93,832>[Am]<X,106.6,410,2647><Y,112.2,131,904>[D5S818]<11,151.5,239,2381><12,155.9,348,3383><13,160.1,173,1668>[FGA]<20,226.8,128,1080><23,238.8,73,646><25,247.1,101,842><26,251.2,85,741>");
        String ss="[D8S1179]<11,134.8,444,3136><12,139.0,196,1468><13,143.6,534,4174><14,148.2,177,1422>[D21S11]<28,200.0,485,3298><31,211.9,163,1154><32.2,217.8,258,1830><33.2,221.8,417,2981>[D7S820]<8,262.9,85,708><10,271.0,237,1989><12,279.1,236,1919>[CSF1PO]<10,321.3,223,1970><12,329.5,236,2109><13,333.6,313,2803>[D3S1358]<15,123.2,615,4840><16,127.3,166,1289><17,131.4,127,998>[THO1]<6,170.8,674,4496><9,182.8,165,1168><10,186.8,496,3392>[D13S317]<8,216.4,731,5493><11,228.3,352,2672><12,232.3,580,4419>[D16S539]<11,276.0,134,1141><12,280.0,381,3251><13,284.0,203,1754>[D2S1338]<18,319.6,259,2353><19,323.7,78,710><23,340.0,302,2723>[D19S433]<14.2,123.1,350,2160><15.2,127.1,483,3025>[vWA]<16,174.5,591,4184><17,178.5,365,2551><18,182.4,131,923>[TPOX]<8,229.5,86,832><10,237.5,194,1756><11,241.5,159,1392><12,245.5,156,1460>[D18S51]<15,294.5,464,4176><15,294.5,464,4176>[AMEL]<X,106.1,742,5031><Y,111.7,283,2072>[D5S818]<10,146.6,372,2779><11,151.0,580,4182><13,159.4,664,4734>[FGA]<17,214.1,190,1336><23,238.2,349,2673><24,242.3,439,3310>";
        String[] ssArr = ss.split("\\[");
        for(String s1 : ssArr){
            if(StringUtils.isBlank(s1)){
                continue;
            }
            System.out.println(s1);
            System.out.println(s1.substring(0, s1.indexOf("]")));
            System.out.println(s1.substring(s1.indexOf("]") + 1));

            String[] alleleValueArr = (s1.substring(s1.indexOf("]") + 1)).split("<");
            for(String allele : alleleValueArr){
                System.out.println(allele);
            }
        }
        /*Map<String,String> temp = convertGenoTypeString(ss);
        System.out.println(transfromGeneFormat(temp));
        System.out.println(temp.size());
        System.out.println(temp.toString());*/
        /*String geneInfo = "{\"D8S1179\":[\"10\",\"12\",\"14\"],\"D21S11\":[\"29\",\"30\",\"31\"],\"D7S820\":[\"9\",\"11\",\"12\"],\"CSF1PO\":[\"10\",\"11\",\"12\"],\"D3S1358\":[\"15\",\"16\",\"17\"],\"TH01\":[\"9\",\"9\"],\"D13S317\":[\"8\",\"11\",\"12\"],\"D16S539\":[\"9\",\"10\",\"11\"],\"D2S1338\":[\"17\",\"19\",\"21\",\"23\"],\"D19S433\":[\"13\",\"14\",\"16.2\"],\"vWA\":[\"14\",\"14\"],\"TPOX\":[\"8\",\"11\",\"12\"],\"D18S51\":[\"13\",\"15\",\"23\"],\"Am\":[\"X\",\"Y\"],\"D5S818\":[\"11\",\"12\",\"13\"],\"FGA\":[\"20\",\"23\",\"25\",\"26\"]}";

        String [] a = {"1","2"};
        String [] b = {"1","3","2"};
        System.out.println(CommonUtils.containArray(a, b));

        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("a");
        l1.add("c");
        l1.add("c");

        List<String> l2 = new ArrayList<String>();
        l2.add("b");
        l2.add("b");
        l2.add("c");

        l1.removeAll(l2);//此处指的是将与l2重复的删除
//        l1.addAll(l2);//此处指加上l2

        //如果保证l1,和l2  2个各自的LIST 本身不重复，此行代码不用写。否则会出现合并后LIST重复的问题，具体看业务需要
//        l1 = new ArrayList<String>(new HashSet<>(l1));

        if (l1.contains("a")) {
            System.out.println(true);
        }
        for(String str : l1) {
            System.out.println(str);
        }

        System.out.println(isMixSample("[D8S1179]<10,131.1,234,2105><12,139.5,403,3772><14,148.7,138,1358>[D21S11]<29,204.4,114,772><30,208.3,216,1425><31,212.3,340,2241>[D7S820]<9,267.8,74,616><11,275.9,239,1927><12,280.1,59,512>[CSF1PO]<10,322.4,322,2996><11,326.6,111,977><12,330.6,165,1564>[D3S1358]<15,123.7,183,1922><16,127.7,354,3606><17,132.0,277,2924>[TH01]<9,183.1,1501,10297><9,183.1,1501,10297>[D13S317]<8,217.0,510,3456><11,229.0,269,1986><12,233.1,251,1903>[D16S539]<9,268.6,230,2061><10,272.7,265,2174><11,276.6,211,1792>[D2S1338]<17,316.4,184,1883><19,324.7,73,768><21,333.0,239,2189><23,340.7,187,1500>[D19S433]<13,117.2,102,1288><14,121.1,261,3299><16.2,131.2,147,1694>[vWA]<14,167.2,619,4462><14,167.2,619,4462>[TPOX]<8,230.1,377,3289><11,242.0,247,2156><12,246.0,94,853>[D18S51]<13,287.1,158,1354><15,295.4,144,1135><23,329.9,93,832>[Am]<X,106.6,410,2647><Y,112.2,131,904>[D5S818]<11,151.5,239,2381><12,155.9,348,3383><13,160.1,173,1668>[FGA]<20,226.8,128,1080><23,238.8,73,646><25,247.1,101,842><26,251.2,85,741>[D8S1179]<10,131.1,234,2105><12,139.5,403,3772><14,148.7,138,1358>[D21S11]<29,204.4,114,772><30,208.3,216,1425><31,212.3,340,2241>[D7S820]<9,267.8,74,616><11,275.9,239,1927><12,280.1,59,512>[CSF1PO]<10,322.4,322,2996><11,326.6,111,977><12,330.6,165,1564>[D3S1358]<15,123.7,183,1922><16,127.7,354,3606><17,132.0,277,2924>[TH01]<9,183.1,1501,10297><9,183.1,1501,10297>[D13S317]<8,217.0,510,3456><11,229.0,269,1986><12,233.1,251,1903>[D16S539]<9,268.6,230,2061><10,272.7,265,2174><11,276.6,211,1792>[D2S1338]<17,316.4,184,1883><19,324.7,73,768><21,333.0,239,2189><23,340.7,187,1500>[D19S433]<13,117.2,102,1288><14,121.1,261,3299><16.2,131.2,147,1694>[vWA]<14,167.2,619,4462><14,167.2,619,4462>[TPOX]<8,230.1,377,3289><11,242.0,247,2156><12,246.0,94,853>[D18S51]<13,287.1,158,1354><15,295.4,144,1135><23,329.9,93,832>[Am]<X,106.6,410,2647><Y,112.2,131,904>[D5S818]<11,151.5,239,2381><12,155.9,348,3383><13,160.1,173,1668>[FGA]<20,226.8,128,1080><23,238.8,73,646><25,247.1,101,842><26,251.2,85,741>"));*/
    }

}
