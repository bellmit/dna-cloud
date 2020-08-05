package com.bazl.dna.mix.service.impl;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.mix.dao.MobileNewsMapper;
import com.bazl.dna.mix.model.po.*;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.SampleInfoVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.service.MatchResultMixSingleService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.task.redisDataBaseTask;
import com.bazl.dna.mix.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class MatchResultMixSingleServiceImpl extends MixBaseService implements MatchResultMixSingleService {

    //混合匹配下限
    @Value("${minSameMixCount}")
    private int minSameMixCount;

    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;

    @Autowired
    GeneCompareUtil geneCompareUtil;
    @Autowired
    MobileNewsMapper mobileNewsMapper;

    @Autowired
    MatchResulService matchResulService;

    @Autowired
    MixedSampleGeneService mixedSampleGeneService;

    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;

    @Autowired
    redisDataBaseTask redisDataBaseTask;


    /**
     * 自动分析混合样本基因分型和单一样本分型进行比对
     * @param mixedSampleGeneList
     * @param singleSampleGeneList
     * @param mixSameCount
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<AutoanalysisMix> autoanalysisMixedAndSingle(List<MixedSampleGeneVo>  mixedSampleGeneList, List<SingleSampleGeneVo> singleSampleGeneList,
                                                                 int mixSameCount,  CaseInfo caseInfo) {
        int x = 1;
        List<AutoanalysisMix> autoanalysisMixList = new ArrayList<>();
        for (MixedSampleGeneVo mixSampleGene: mixedSampleGeneList){ //循环混合
            AutoanalysisMix autoanalysisMixnew = new   AutoanalysisMix(); //存储混合比中单一的数据
            ArrayList<SingleSampleGeneVo> sinList = new ArrayList<>();
            for (SingleSampleGeneVo singSampleGene: singleSampleGeneList){ //循环单一
                //混合比单一
                Map<String, Object> map =geneCompareUtil.compareGeneInfo(mixSampleGene.getEntity().getGeneInfo(), singSampleGene.getEntity().getGeneInfo(), mixSameCount);
                if (map != null && (Boolean) map.get("matched")) {
                    sinList.add(singSampleGene);
                }

            }
            mixSampleGene.setNewGeneInfo(GeneSameCompareUtil.ngetGeneInfoString(mixSampleGene.getEntity().getGeneInfo()));
            mixSampleGene.setGeneImagePath("0");

            autoanalysisMixnew.setMixedSampleGeneVo(mixSampleGene); //混合的数据
            //对同型比中情况进行分组
            Map<String, String> groupMap = new HashMap<String, String>();
            Map<String, List<SingleSampleGeneVo>> groupMapList = new HashMap<String, List<SingleSampleGeneVo>>();
            int len = sinList.size();
            mixSampleGene.setRatio((short) len);
            String groupName = null;
            List<SingleSampleGeneVo> matchedSampleList = null;
            for(int i = 0; i < len; i++){
//                SingleSampleGeneVo si = sinList.get(i);
                SingleSampleGeneVo si = new SingleSampleGeneVo();
                si.setGeneInfo(sinList.get(i).getGeneInfo());
                si.getEntity().setGeneInfo(sinList.get(i).getEntity().getGeneInfo());
                si.setSampleNo(sinList.get(i).getSampleNo());
                si.setSampleName(sinList.get(i).getSampleName());
                si.setSampleFlag(sinList.get(i).getSampleFlag());
                si.setSampleType(sinList.get(i).getSampleType());//检材类型
                si.setSampleTypeName(sinList.get(i).getSampleTypeName());
                if(groupMap.containsKey(si.getSampleNo())){
                    continue;
                }else{
                    groupName = "Group-" + x;
                    groupMap.put(si.getSampleNo(), groupName);
                }
                matchedSampleList = groupMapList.get(groupName);
                if(matchedSampleList == null || matchedSampleList.size() == 0){
                    matchedSampleList = new ArrayList<>();
                }
                for(int j = i+1; j < len; j++){
//                    SingleSampleGeneVo sj = sinList.get(j);
                    SingleSampleGeneVo sj = new SingleSampleGeneVo();
                    sj.setGeneInfo(sinList.get(j).getGeneInfo());
                    sj.getEntity().setGeneInfo(sinList.get(j).getEntity().getGeneInfo());
                    sj.setSampleNo(sinList.get(j).getSampleNo());
                    sj.setSampleName(sinList.get(j).getSampleName());
                    sj.setSampleFlag(sinList.get(j).getSampleFlag());
                    sj.setSampleType(sinList.get(j).getSampleType());//检材类型
                    sj.setSampleTypeName(sinList.get(j).getSampleTypeName());
                    //获取基因型
                    String geneInfo1 = si.getEntity().getGeneInfo();
                    String geneInfo2 = sj.getEntity().getGeneInfo();
                    boolean bool = geneCompareUtil.NewrepeatingUtils(geneInfo1, geneInfo2);
                    if(bool){//si与sj同型比中
                        groupName = "Group-" + x;
                        groupMap.put(sj.getSampleNo(), groupName);
                        matchedSampleList.add(sj);
                    }
                }
                matchedSampleList.add(0, si);
                groupMapList.put(groupName, matchedSampleList);
                x++;
            }
            //遍历map 给下一步排序使用
            List<SingleSampleGeneVo> mixSingleList = new ArrayList<>();
            for (String key : groupMapList.keySet()){
                List<SingleSampleGeneVo> singleList = groupMapList.get(key);
                for (SingleSampleGeneVo single : singleList){
                    single.setGroupName(key);
                    mixSingleList.add(single);
                }
            }
            //位置排序
            Collections.sort(mixSingleList);
            for(SingleSampleGeneVo single : mixSingleList){
//                    String singGene = GeneSameCompareUtil.ngetGeneInfoStringOr(single.getEntity().getGeneInfo());  //单一基因信息
                LinkedHashMap<String, List<String>> singGeneNew = JSON.parseObject(single.getEntity().getGeneInfo(), LinkedHashMap.class);;
//                    String mixGene = GeneSameCompareUtil.ngetGeneInfoStringOr(mixSampleGene.getEntity().getGeneInfo()); // 混合基因信息
                LinkedHashMap<String, List<String>> mixGeneNew = JSON.parseObject(mixSampleGene.getEntity().getGeneInfo(), LinkedHashMap.class);;

                Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResultFlag(mixGeneNew, singGeneNew, 13);
                single.setNewGeneMap(stringObjectMap);
                int sameCount = Integer.parseInt(stringObjectMap.get("sameCount").toString());
                int diffCount = Integer.parseInt(stringObjectMap.get("diffCount").toString());
                single.setSameCount(sameCount);
                single.setSampleCount(sameCount+diffCount);
                if (StringUtils.isNotBlank(single.getSampleType())){
                    if (single.getSampleType().equals("01")){
                        single.setSampleFlag("0");//物证
                    }else {
                        single.setSampleFlag("1");//人员
                    }
                }
                single.setRatioSampleGeneImagePath("0");//无峰图
                single.setSplitdSampleGeneImagePath("0");//无峰图0
                single.setSampleType(single.getSampleTypeName());
            }
            autoanalysisMixnew.setSingleSampleGeneVos(mixSingleList);     //混合比中单一的数据
            x = 1;//还原 分组用
            autoanalysisMixList.add(autoanalysisMixnew);
        }
        return autoanalysisMixList;
    }


    /**
     * 自动分析混合样本基因分型和单一样本分型进行比对
     * @param mixedSampleGeneList
     * @param singleSampleGeneList
     * @param mixSameCount
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> autoanalysisMixedAndSingleCompare(List<MixedSampleGeneVo>  mixedSampleGeneList,
                                                                 List<SingleSampleGeneVo> singleSampleGeneList,
                                                                 int mixSameCount,  CaseInfo caseInfo) {
        List<SampleInfoVo> sampleInfoList1 = new ArrayList<>();
        //混合基因样本编号list
        List<MixedSampleGeneVo> mixedSampleGeneList1 = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String mixedSampleId = "";
        try {
            int x = 1;//记录分组
            MixedSampleGeneVo mixAuditedGene = null;
            SingleSampleGeneVo auditedGene1 = null;
            List<List<SampleInfoVo>> resuleList = new ArrayList<>();
            for (int m = 0; m < mixedSampleGeneList.size(); m++) {
                mixAuditedGene = mixedSampleGeneList.get(m);

                for (int i = 0; i < singleSampleGeneList.size(); i++) {
                    auditedGene1 = singleSampleGeneList.get(i);
                    //判断混合基因是否包含此基因
                    if (mixSameCount == 0) {
                        mixSameCount = minSameMixCount;
                    }
                    //比对
                    String geneStr1 = mixAuditedGene.getEntity().getGeneInfo();
                    String geneStr2 = auditedGene1.getEntity().getGeneInfo();

                    map =geneCompareUtil.compareGeneInfo(geneStr1, geneStr2, mixSameCount);

                    //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
                    if ((Boolean) map.get("matched")) {
                        MatchResult result = new MatchResult();
                        result.setMixedSampleGeneId(mixAuditedGene.getEntity().getId());
                        result.setSingleGeneId(auditedGene1.getEntity().getId());
                        List<MatchResult> matchResult = matchResulService.selectMatchList(result);
                        if(null != matchResult && matchResult.size() > 0){
                            result.setRatio(Short.valueOf(map.get("sameCount").toString()));
                            result.setSplitDigit(Short.valueOf(map.get("diffCount").toString()));
                            result.setSampleGeneResultType("1");
                            matchResulService.updateByPrimaryKey(result);

                        }else{
                            //添加混合单一样本比中详情信息
                            MatchResult newResult = new MatchResult();
                            newResult.setId(UUID.randomUUID().toString());
                            newResult.setMixedSampleGeneId(mixAuditedGene.getEntity().getId());
                            newResult.setSingleGeneId(auditedGene1.getEntity().getId());
                            newResult.setRatio(Short.valueOf(map.get("sameCount").toString()));
                            newResult.setSplitDigit(Short.valueOf(map.get("diffCount").toString()));
                            newResult.setComparisonTime(new Date());
                            newResult.setSampleGeneResultType("1");
                            matchResulService.insert(newResult);
                            //添加比中消息信息
                            if (null != caseInfo){
                                if (caseInfo != null){
                                    MobileNews mobileNews = new MobileNews();
                                    mobileNews.setId(UUID.randomUUID().toString());
                                    mobileNews.setTitle("比对消息提示");//标题
                                    mobileNews.setContent(caseInfo.getCaseName()+"单一比对已比中");//内容
                                    mobileNews.setState(0);//状态  未读
                                    Date date = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String createdate = sdf.format(date);
                                    mobileNews.setCreateDatetime(createdate);
                                    mobileNews.setType(11);
                                    mobileNews.setUserid(caseInfo.getCreatePerson());
                                    mobileNews.setCaseId(caseInfo.getId());//案件id
                                    mobileNews.setUserOrg(caseInfo.getOrgId());
                                    mobileNews.setMessageType(3);//消息类型
                                    mobileNewsMapper.insert(mobileNews);
                                    System.out.println("添加混合单一比对比中消息");
                                }
                            }
                        }
                    }

                    //判断混合基因型id是否重复 （去重）
                    if(StringUtils.isBlank(mixedSampleId)){
                        mixedSampleId = mixAuditedGene.getEntity().getId();
                        mixedSampleGeneList1.add(mixAuditedGene);
                    }else{
                        if(!(mixedSampleId).contains(mixAuditedGene.getEntity().getId())){
                            mixedSampleId = mixedSampleId+","+mixAuditedGene.getEntity().getId();
                            mixedSampleGeneList1.add(mixAuditedGene);
                        }
                    }
                }
            }
            //样本编号list
            SampleInfoVo sampleInfoVo = new SampleInfoVo();
            for(MixedSampleGeneVo mixedSampleGeneVo:mixedSampleGeneList1){
                sampleInfoVo = new SampleInfoVo();
             //   List<SampleInfoVo> sampleInfoList =null; //- sampleInfoDAO.selectSampleInfoList(mixedSampleGeneVo.getEntity().getId());

                sampleInfoVo.getEntity().setSampleNo(mixedSampleGeneList.get(0).getSampleNo());
                sampleInfoVo.getEntity().setId(mixedSampleGeneList.get(0).getEntity().getId());
                sampleInfoList1.add(sampleInfoVo);

                //获取基因信息
                Map<String, Object> resultGeneInfo = new HashMap<>();
                Map<String, List<String>> mixedSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(mixedSampleGeneVo.getEntity().getGeneInfo());
                //判空
                if (mixedSampleGeneInfo != null) {
                    //解析基因型信息
                    resultGeneInfo = geneSameCompareUtil.analysisGene1(mixedSampleGeneInfo);
                }
                mixedSampleGeneVo.setResultGeneInfo(resultGeneInfo);
                //混合基因信息
                sampleInfoVo.setMixedSampleGeneVo(mixedSampleGeneVo);

                MatchResult result = new MatchResult();
                result.setMixedSampleGeneId(mixedSampleGeneList.get(0).getEntity().getId());
                List<MatchResult> matchResult = matchResulService.selectMatchList(result);
                for(int i = matchResult.size()-1; i>=0 ;i--){
                    if(!(matchResult.get(0).getProportionCaseNo()).equals(caseInfo.getCaseNo())){
                        matchResult.remove(i);
                    }
                }
               //- MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(mixedSampleGeneList.get(0).getEntity().getId());
                //对同型比中情况进行分组
                Map<String, String> groupMap = new HashMap<String, String>();
                Map<String, List<MixedSampleGene>> groupMapList = new HashMap<String, List<MixedSampleGene>>();
                int len = matchResult.size();
                String groupName = null;
                List<MixedSampleGene> matchedSampleList = null;
                for(int i = 0; i < len; i++){
                    MixedSampleGene si = new MixedSampleGene();
                    si.setGeneInfo(matchResult.get(i).getGeneInfo());
                    si.setSampleNo(matchResult.get(i).getProportionSampleNo());
                    si.setSampleId(matchResult.get(i).getId());
                    si.setSampleName(matchResult.get(i).getProportionSampleName());
                    si.setSampleFlag(matchResult.get(i).getProportionPersonnel());
                    si.setRatio(matchResult.get(i).getRatio()+"");
                    si.setSumCount( matchResult.get(i).getProportionSiteNum());

                    if(groupMap.containsKey(si.getSampleNo())){
                        continue;
                    }else{
                        groupName = "Group-" + x;
                        groupMap.put(si.getSampleNo(), groupName);
                    }
                    matchedSampleList = groupMapList.get(groupName);
                    if(matchedSampleList == null || matchedSampleList.size() == 0){
                        matchedSampleList = new ArrayList<>();
                    }
                    for(int j = i+1; j < len; j++){
                        MixedSampleGene sj = new MixedSampleGene();
                        sj.setGeneInfo(matchResult.get(j).getGeneInfo());
                        sj.setSampleNo(matchResult.get(j).getProportionSampleNo());
                        sj.setSampleId(matchResult.get(j).getId());
                        sj.setSampleName(matchResult.get(j).getProportionSampleName());
                        sj.setSampleFlag(matchResult.get(j).getProportionPersonnel());
                        sj.setRatio(matchResult.get(j).getRatio()+"");
                        sj.setSumCount( matchResult.get(j).getProportionSiteNum());
                        //获取基因型
                        String geneInfo1 =  si.getGeneInfo();
                        String geneInfo2 =  sj.getGeneInfo();
                        boolean bool = geneCompareUtil.repeatingUtils(geneInfo1, geneInfo2);
                        if(bool){//si与sj同型比中
                            groupName = "Group-" + x;
                            groupMap.put(sj.getSampleNo(), groupName);
                            matchedSampleList.add(sj);
                        }
                    }
                    matchedSampleList.add(0, si);
                    groupMapList.put(groupName, matchedSampleList);
                    x++;
                }
                //遍历map
                List<MixedSampleGene> mixSingleList = new ArrayList<>();
                for (String key : groupMapList.keySet()){
                    List<MixedSampleGene> singleList = groupMapList.get(key);
                    for (MixedSampleGene single : singleList){
                        single.setGroupName(key);
                        mixSingleList.add(single);
                    }
                }
                //位置排序
                Collections.sort(mixSingleList);

                //混合单一比中信息
                sampleInfoVo.setMatchResultMixSingleList(mixSingleList);
                //混合比中单一条数
                sampleInfoVo.setSingleSampleCount(mixSingleList.size());
                x = 1;//还原
            }
            //排序
            Collections.sort(sampleInfoList1, new Comparator<SampleInfoVo>() {
                @Override
                public int compare(SampleInfoVo u1, SampleInfoVo u2) {
                    int u1size;
                    int u2size;
                    if(null != u1.getMatchResultMixSingleList() && u1.getMatchResultMixSingleList().size() > 0){
                        u1size = u1.getMatchResultMixSingleList().size();
                    }else{
                        u1size=0;
                    }
                    if(null != u2.getMatchResultMixSingleList() && u2.getMatchResultMixSingleList().size() > 0){
                        u2size = u2.getMatchResultMixSingleList().size();
                    }else {
                        u2size=0;
                    }
                    return u2size-u1size;
                }
            });
            resuleList.add(sampleInfoList1);
            map.put("mixSameCount", mixSameCount);
            map.put("caseNo", caseInfo.getCaseNo());
            map.put("resuleList", resuleList);
        } catch (Exception ex) {
            logger.error("根据混合基因id查询混合单一比中详情表失败！！！", ex.getMessage());
        }
        return map;
    }



}
