package com.bazl.dna.mix.utils;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/12/15.
 */
@RestController
public class RepetitionUtils extends BaseController {

    public int Determine(List<CompareQueue> list,String geneMap1){
        int count = 0;
        //遍历数据库的基因信息
        for (CompareQueue queue : list) {
            //对数据库基因信息转为Map对象
            Map<String, Object> geneDBMap = JSON.parseObject(queue.getGeneInfo());
            Map<String, Object> geneMap = JSON.parseObject(geneMap1);
            //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
            if (geneDBMap.keySet().size() != geneMap.keySet().size()) {
                count++;
                continue;
            } else {
                //如果相同那么循环比较各个value值
                for (String key : geneMap.keySet()) {
                    //如果前台的key在数据库的基因信息不存在则跳出循环
                    if (geneDBMap.get(key) == null) {
                        count++;
                        break;
                    } else {
                        //判断基因value值
                        if (match(geneDBMap.get(key).toString(), geneMap.get(key).toString())) {
                            continue;
                        } else {
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }

    public int DetermineNew(List<SplitedSampleGene> list, String geneInfo){
        int count = 0;
        String geneInfoMap = GeneSameCompareUtil.getGeneInfoString(geneInfo);
        //遍历数据库的基因信息
        for (SplitedSampleGene sampleGene : list) {
            //对数据库基因信息转为Map对象
            if (StringUtils.isNotBlank(sampleGene.getGeneInfo())){
                String geneInfoString = GeneSameCompareUtil.getGeneInfoString(sampleGene.getGeneInfo());
                Map<String, Object> geneDBMap = JSON.parseObject(geneInfoString);
                Map<String, Object> geneMap = JSON.parseObject(geneInfoMap);
                //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
                if (geneDBMap.keySet().size() != geneMap.keySet().size()) {
                    count++;
                    continue;
                } else {
                    //如果相同那么循环比较各个value值
                    for (String key : geneMap.keySet()) {
                        //如果前台的key在数据库的基因信息不存在则跳出循环
                        if (geneDBMap.get(key) == null) {
                            count++;
                            break;
                        } else {
                            //判断基因value值
                            if (matchNew(geneDBMap.get(key).toString(), geneMap.get(key).toString())) {
                                continue;
                            } else {
                                count++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * 不考虑字符串顺序，判断字符串是否相同  公共方法
     *
     * @param o
     * @param o1
     * @return
     */
    private static boolean match(String o, String o1) {
        boolean flag = false;
        String[] split = o.split(",");
        String[] split1 = o1.split(",");
        Arrays.sort(split);
        Arrays.sort(split1);
        if (split.length == split1.length) {
            if (Arrays.equals(split, split1)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 不考虑字符串顺序，判断字符串是否相同  公共方法
     *
     * @param o
     * @param o1
     * @return
     */
    private static boolean matchNew(String o, String o1) {
        boolean flag = false;
        String[] split = o.split("/");
        String[] split1 = o1.split("/");
        Arrays.sort(split);
        Arrays.sort(split1);
        if (split.length == split1.length) {
            if (Arrays.equals(split, split1)) {
                flag = true;
            }
        }
        return flag;
    }
}
