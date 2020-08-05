package com.bazl.dna.database.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.mapper.*;
import com.bazl.dna.database.service.model.po.*;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.MatchResultSameService;
import com.bazl.dna.exception.DnaRuntimeException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 同型比对结果表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Service
public class MatchResultSameServiceImpl extends ServiceImpl<MatchResultSameMapper, MatchResultSame> implements MatchResultSameService {


    @Autowired
    AutoCompareQueueMapper autoCompareQueueMapper;

    @Autowired
    DnaGeneInfoMapper dnaGeneInfoMapper;


    @Autowired
    MatchResultSameMapper matchResultSameMapper;

    @Autowired
    DnaSampleInfoMapper dnaSampleInfoMapper;
    @Autowired
    private DictItemMapper dictItemMapper;

    @Autowired
    private DnaSampleInfoService dnaSampleInfoService;

    @Autowired
    MatchResultSameGroupMapper matchResultSameGroupMapper;

    @Autowired
    PopulationFrequencyInfoMapper populationFrequencyInfoMapper;

    @Autowired
    AlleleFrequencyInfoMapper alleleFrequencyInfoMapper;

    @Autowired
    MatchResultSameService matchResultSameService;

    @Autowired
    CaseInfoMapper caseInfoMapper;

    @Autowired
    CasePersonInfoMapper casePersonInfoMapper;

    @Autowired
    ConsignmentInfoMapper  consignmentInfoMapper;

    @Autowired
    CaseInfoService  caseInfoService;

    @Autowired
    CasePersonInfoService casePersonInfoService;

    private  static  final  String CACHE_NAME="MatchResultSame";

    @Transactional
    @Override
    public void insertMatchResult(StrSameCompareResult strSameCompareResult, String key) {
            try {
                MatchResultSame matchResultSame = new MatchResultSame();
                //判断分组类型
                String[] split = key.split("-");
                String[] strings = split[1].split("_");
                String instoreDataType  = strings[0];
                //获取案件类型

                DnaGeneInfo dnaGeneInfo = dnaGeneInfoMapper.selectOne(new QueryWrapper<DnaGeneInfo>().eq("GENE_INFO", strSameCompareResult.getStrSameCompareResultAlleleList().get(0).getSrcGeneAllele()));
                DnaSampleInfo sampleInfo = dnaSampleInfoMapper.selectOne(new  QueryWrapper<DnaSampleInfo>().eq("ID", dnaGeneInfo.getSampleId()));
                MatchResultSame resultSame = matchResultSameMapper.selectOne(new QueryWrapper<MatchResultSame>().eq("SAMPLEB_ID", dnaGeneInfo.getSampleId()));
                if (resultSame == null){
                    String dictCode = "";
                    int num = 0;
                    DictItem dictItem = dictItemMapper.selectOne(new QueryWrapper<DictItem>().eq("DICT_TYPE_NAME", "str比对分组编号"));
                    String[] split1 = dictItem.getDictCode().split("-");
                    String s = split1[0];
                    num = Integer.valueOf(split1[1]);
                    dictCode = s+"-"+num;
                    dictItem.setDictCode(dictCode);
                    dictItemMapper.updateById(dictItem);
                    MatchResultSameGroup matchResultSameGroup = new MatchResultSameGroup();
                    if (instoreDataType.equals("01") && sampleInfo.getInstoreDataType().equals("01")){
                        //物证比物证 2
                        matchResultSameGroup.setGroupNo(num);
                        matchResultSameGroup.setFirstMatchTime(LocalDateTime.now());
                        matchResultSameGroup.setGroupType("2");
                        matchResultSameGroupMapper.insert(matchResultSameGroup);
                    }else if (sampleInfo.getInstoreDataType().equals("01") && sampleInfo.getInstoreDataType().equals("02") && instoreDataType.equals("01")){
                        //人员比物证 1
                        matchResultSameGroup.setGroupNo(num);
                        matchResultSameGroup.setFirstMatchTime(LocalDateTime.now());
                        matchResultSameGroup.setGroupType("1");
                        matchResultSameGroupMapper.insert(matchResultSameGroup);
                    }else if(!sampleInfo.getInstoreDataType().equals("01") && !sampleInfo.getInstoreDataType().equals("02") && !instoreDataType.equals("01") && !instoreDataType.equals("02")){
                        //人员比人员 3
                        matchResultSameGroup.setGroupNo(num);
                        matchResultSameGroup.setFirstMatchTime(LocalDateTime.now());
                        matchResultSameGroup.setGroupType("3");
                        matchResultSameGroupMapper.insert(matchResultSameGroup);
                    }
                }else{
                    MatchResultSameGroup matchResultSameGroup1 = matchResultSameGroupMapper.selectOne(new QueryWrapper<MatchResultSameGroup>().eq("id", resultSame.getMatchGroupId()));
                    MatchResultSameGroup matchResultSameGroup = new MatchResultSameGroup();
                    BeanUtils.copyProperties(matchResultSameGroup1, matchResultSameGroup);
                    matchResultSameGroup.setLatestMatchTime(LocalDateTime.now());
                    matchResultSameGroupMapper.updateById(matchResultSameGroup);
                    matchResultSame.setMatchGroupId(resultSame.getMatchGroupId());

                }
                DnaGeneInfo dnaGeneInfo1 = dnaGeneInfoMapper.selectOne(new QueryWrapper<DnaGeneInfo>().eq("GENE_INFO", strSameCompareResult.getStrSameCompareResultAlleleList().get(0).getTargetGeneAllele()));
                matchResultSame.setCreateDatetime(LocalDateTime.now());
                matchResultSame.setMatchLocusCount(strSameCompareResult.getMatchedLocusCount());
                matchResultSame.setSampleaId(dnaGeneInfo.getSampleId());
                matchResultSame.setSamplebId(dnaGeneInfo1.getSampleId());
                matchResultSame.setMatchedGeneDetails(JSONObject.toJSONString(strSameCompareResult));
                matchResultSameMapper.insert(matchResultSame);

            }catch (Exception e){
            	log.error("Error save: ", e);
            	throw new DnaRuntimeException();
            }

        }


    @Override
    public MatterComparePerson queryCaseInfo(MatchResultSame matchResultSame) {
        if (matchResultSame != null) {
            //查询源检材
               DnaSampleInfoVo sampleInfo = dnaSampleInfoService.selectById(matchResultSame.getSampleaId());
            if (sampleInfo != null) {
                CaseInfo caseInfo = caseInfoService.selectByPrimaryKey(sampleInfo.getCaseId());
                //查询比中检材
                DnaSampleInfoVo sampleInfo2 = dnaSampleInfoService.selectById(matchResultSame.getSamplebId());
                CaseInfo caseInfo2 = caseInfoMapper.selectByPrimaryKey(sampleInfo.getCaseId());
                //查询人员信息
                CasePersonInfo casePersonInfo = casePersonInfoService.selectVoListById(sampleInfo2.getRefPersonId());
                MatterComparePerson matterComparePerson = new MatterComparePerson();
                /**
                 * 源样本信息
                 */
             if (caseInfo!=null) {
                matterComparePerson.setCaseName(caseInfo.getCaseName()); //源案件名称
                matterComparePerson.setCaseAcceptNo(caseInfo.getCaseAcceptNo());//源案件受理编号
                matterComparePerson.setId(caseInfo.getId().toString());//源案件编号
                if (caseInfo.getCaseStatus() != null) {
                    matterComparePerson.setCaseStatus(caseInfo.getCaseStatus());//源案件破获状态 1:已破案 0:未破案
                }
                matterComparePerson.setLabServerNo(caseInfo.getLabServerNo());//源案件实验室服务器编码
                 }
                matterComparePerson.setSampleName(sampleInfo.getSampleName());//源检材名称
                matterComparePerson.setSampleNo(sampleInfo.getSampleLabNo());//源物证编号
                matterComparePerson.setSampleType(sampleInfo.getSampleType());//源样本类型
                if (sampleInfo.getSampleType() != null) {
                    List<DictItem> SampleTypeDictItems = dictItemMapper.selectListByDictType(Constants.DICT_TPYE_SAMPLE_TYPE);
                    for (DictItem dictItem : SampleTypeDictItems) {
                        if (dictItem.getDictCode().equals(sampleInfo.getSampleType())) {
                            matterComparePerson.setSampleTypeName(dictItem.getDictName());//源样本类型名称
                        }
                    }
                }

                //如果样本信息中 委托ID不为空，根据委托ID,查询机构名称 查委托单位
                if (sampleInfo.getConsignmentId() != null) {
                    //查询委托信息根据样本委托单位ID
                    ConsignmentInfo consignmentInfo = consignmentInfoMapper.
                            selectOne(new QueryWrapper<ConsignmentInfo>().eq("ID", sampleInfo.getConsignmentId()));
                    if (consignmentInfo != null && StringUtils.isNotBlank(consignmentInfo.getConsignOrgName())) {
                        matterComparePerson.setConsignmentName(consignmentInfo.getConsignOrgName());  //委托单位名称
                    }
                }
                matterComparePerson.setMatchRegion(caseInfo2.getScenePlace());//比中区域
                if (casePersonInfo != null && StringUtils.isNotBlank(casePersonInfo.getPersonName())) {
                    matterComparePerson.setPersonname(casePersonInfo.getPersonName());//人员姓名
                }
                if (casePersonInfo != null && StringUtils.isNotBlank(casePersonInfo.getPersonType())) {
                    matterComparePerson.setPersionType(casePersonInfo.getPersonType());//人员类型
                    List<DictItem> CasePersonDictItems = dictItemMapper.selectListByDictType(Constants.DICT_TPYE_CASE_PERSON_TYPE);
                    for (DictItem dictItem : CasePersonDictItems) {
                        if (dictItem.getDictCode().equals(casePersonInfo.getPersonType())) ;
                        matterComparePerson.setPersonTypeName(dictItem.getDictName()); //人员类型名称
                    }
                }
                if (casePersonInfo != null && StringUtils.isNotBlank(casePersonInfo.getPersonIdcardNo())) {
                    matterComparePerson.setPersonIdcardNo(casePersonInfo.getPersonIdcardNo());//人员身份证号码
                }
                matterComparePerson.setMatchLocusCount(matchResultSame.getMatchLocusCount().toString());//比中基因座数目
                return matterComparePerson;
            }
        }
        return  null;
    }


    @Override
    public MatterComparePerson queryMatterCompareMatter(MatchResultSame matchResultSame) {
        if (matchResultSame != null) {
            //查询源检材
            DnaSampleInfoVo sampleInfo = dnaSampleInfoService.selectById(matchResultSame.getSampleaId());
            if (sampleInfo != null) {
                CaseInfo caseInfo = caseInfoMapper.selectByPrimaryKey(sampleInfo.getCaseId());
                //查询比中检材
                DnaSampleInfoVo sampleInfo2 = dnaSampleInfoService.selectById(matchResultSame.getSamplebId());
                CaseInfo caseInfo2 = caseInfoMapper.selectByPrimaryKey(sampleInfo.getCaseId());
                //查询人员信息
                CasePersonInfo casePersonInfo = casePersonInfoService.selectVoListById(sampleInfo2.getRefPersonId());
                MatterComparePerson matterComparePerson = new MatterComparePerson();
                /**
                 * 源信息展示
                 */
                matterComparePerson.setCaseName(caseInfo.getCaseName());          //源案件名称
                matterComparePerson.setCaseAcceptNo(caseInfo.getCaseAcceptNo());  //源案件受理编号
                matterComparePerson.setId(caseInfo.getId().toString());//源案件编号
                if (caseInfo.getCaseStatus() != null) {
                    matterComparePerson.setCaseStatus(caseInfo.getCaseStatus());//源案件破获状态
                }
                if (caseInfo.getLabServerNo() != null){
                    matterComparePerson.setLabServerNo(caseInfo.getLabServerNo());//源实验室系统编号
                }
                if (StringUtils.isNotBlank(sampleInfo.getSampleName())) {
                    matterComparePerson.setSampleName(sampleInfo.getSampleName());//源样本名称
                }
                if (StringUtils.isNotBlank(sampleInfo.getSampleEvidenceNo())) {
                    matterComparePerson.setSampleNo(sampleInfo.getSampleLabNo());//源样本编号
                }
                if (StringUtils.isNotBlank(sampleInfo.getSampleType())) {
                    matterComparePerson.setSampleType(sampleInfo.getSampleType());//源样本类型
                }
                if (StringUtils.isNotBlank(sampleInfo.getSampleType())) {
                    List<DictItem> sampleTypeDictItem = dictItemMapper.selectListByDictType(Constants.DICT_TPYE_SAMPLE_TYPE);
                    for (DictItem dictItem : sampleTypeDictItem) {
                        if (dictItem.getDictCode().equals(sampleInfo.getSampleType())) {
                            matterComparePerson.setSampleTypeName(dictItem.getDictName());//源样本类型名称
                        }
                    }
                }

                //如果样本信息中 委托ID不为空，根据委托ID,查询机构名称 查委托单位
                if (sampleInfo.getConsignmentId() != null) {
                    //查询委托信息根据样本委托单位ID
                    ConsignmentInfo consignmentInfo = consignmentInfoMapper.
                            selectOne(new QueryWrapper<ConsignmentInfo>().eq("ID", sampleInfo.getConsignmentId()));
                    if (consignmentInfo != null && StringUtils.isNotBlank(consignmentInfo.getConsignOrgName())) {
                        matterComparePerson.setConsignmentName(consignmentInfo.getConsignOrgName());  //委托单位名称
                    }
                }

                if (casePersonInfo != null && StringUtils.isNotBlank(casePersonInfo.getPersonName())) {
                    matterComparePerson.setPersonname(casePersonInfo.getPersonName());//源人员名称
                }
                if (casePersonInfo != null && StringUtils.isNotBlank(casePersonInfo.getPersonType())) {
                    matterComparePerson.setPersionType(casePersonInfo.getPersonType());//源人员类型
                    List<DictItem> casePersonDictItem = dictItemMapper.selectListByDictType(Constants.DICT_TPYE_CASE_PERSON_TYPE);
                    for (DictItem dictItem : casePersonDictItem) {
                        if (dictItem.getDictCode().equals(casePersonInfo.getPersonType())) {
                            matterComparePerson.setPersonTypeName(dictItem.getDictName());//源人员类型名称
                        }
                    }
                }
                if (casePersonInfo != null && StringUtils.isNotBlank(casePersonInfo.getPersonIdcardNo())) {
                    matterComparePerson.setPersonIdcardNo(casePersonInfo.getPersonIdcardNo());//源人员身份证号码
                }

                /**
                 * 比中信息展示
                 */
                matterComparePerson.setMatchRegion(caseInfo2.getScenePlace());     //比中地区
                matterComparePerson.setTargetSampleNo(sampleInfo2.getSampleLabNo());//比中检材编号
                matterComparePerson.setTargetSampleName(sampleInfo2.getSampleName()); //比中检材名称
                matterComparePerson.setTargetCaseAcceptNo(caseInfo2.getCaseAcceptNo());//比中案件受理编号
                matterComparePerson.setMatchLocusCount(matchResultSame.getMatchLocusCount().toString());//比中数
                return matterComparePerson;
            }
        }
        return null;
    }
    
    @Override
	public Integer getMatchResultGroupNo(Integer sampleAId, Integer sampleBId) {
		return matchResultSameMapper.getMatchResultGroupNo(sampleAId, sampleBId);
	}

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator="keyGenerator")
    public List<MatchResultSame> selectLatestTenList(String groupType) {
        try{
            return  matchResultSameMapper.selectLatestTenList(groupType);
        }catch (Exception ex){
              log.error("invoke matchResultSameService.selectLatestTenList ! error",ex);
            return null;
        }
    }

    @Override
    public List<MatchResultSame> selectListByGroupId(Integer matchGroupId) {
        if (matchGroupId!=null){
            try{
                return matchResultSameMapper.selectListByGroupId(matchGroupId);
            }catch (Exception ex){
                log.error("invole matchResultSameService.selectListByGroupId ! error",ex);
                return null;
            }
        }
        return null;
    }


	@Override
	@Transactional
	public void updateMatchResultSame(MatchResultSame entity) {
		try {
			matchResultSameMapper.updateById(entity);
		} catch (Exception e) {
			log.error("update error:", e);
			throw new DnaRuntimeException();
		}
	}
}
