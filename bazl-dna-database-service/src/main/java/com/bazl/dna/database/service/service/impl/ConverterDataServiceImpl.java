package com.bazl.dna.database.service.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.model.po.AutoCompareSettings;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.po.TransferCaseQueue;
import com.bazl.dna.database.service.model.po.TransferCaseSample;
import com.bazl.dna.database.service.model.vo.ConverterDataSampleAndPersonVo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;
import com.bazl.dna.database.service.service.AutoCompareQueueService;
import com.bazl.dna.database.service.service.AutoCompareSettingsService;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.bazl.dna.database.service.service.ConverterDataService;
import com.bazl.dna.database.service.service.CriminalPersonInfoService;
import com.bazl.dna.database.service.service.CriminalSampleInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;
import com.bazl.dna.database.service.service.PersonRelationInfoService;
import com.bazl.dna.database.service.service.QcSampleInfoService;
import com.bazl.dna.database.service.service.TransferCaseQueueService;
import com.bazl.dna.database.service.service.TransferCaseSampleService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.google.common.collect.Maps;

@Service
public class ConverterDataServiceImpl implements ConverterDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterDataServiceImpl.class);
	
	@Autowired
	private CaseInfoService caseInfoService;
	
	@Autowired
	private ConsignmentInfoService consignmentInfoService;
	
	@Autowired
	private DnaSampleInfoService sampleInfoService;
	
	@Autowired
	private CasePersonInfoService casePersonInfoService;
	
	@Autowired
	private PersonRelationInfoService relativeInfoService;
	
	@Autowired
	private DnaStrGeneInfoService strGeneInfoService;
	
	@Autowired
	private DnaYstrGeneInfoService ystrGeneInfoService;
	
	@Autowired
	private CriminalSampleInfoService criminalSampleInfoService;
	
	@Autowired
	private CriminalPersonInfoService criminalPersionInfoService;
	
	@Autowired
	private QcSampleInfoService qcSampleInfoService;
	
	@Autowired
	private TransferCaseQueueService transferCaseService;
	
	@Autowired
	private TransferCaseSampleService transferCaseSampleService;
	
	@Autowired
	private AutoCompareQueueService autoCompareQueueService;
	
	@Autowired
	private AutoCompareSettingsService autoCompareSettingsService;
	
	@Override
	@Transactional
	public int saveCase(ConverterDataVo vo, boolean isTransfer) {
		try {
			// 案件信息
			CaseInfo caseInfo = vo.getCaseInfo();
			caseInfoService.save(caseInfo);
			
			// 委托案件信息
			ConsignmentInfo consignmentInfo = vo.getConsignmentInfo();
			consignmentInfo.setCaseId(caseInfo.getId());
			consignmentInfoService.save(consignmentInfo);

			//存储limsPersonUuid和主键id的映射map
			Map<String, Integer> personUuidAndDbIdMap = new HashMap<String, Integer>();
			
			// 样本和人员信息
			List<ConverterDataSampleAndPersonVo> voList = vo.getSamplePersonList();
			List<Integer> sampleIds = Lists.newArrayList();
			for (ConverterDataSampleAndPersonVo v : voList) {
				// 案件人员信息
				CasePersonInfo casePerson = v.getCasePersonInfo();
				if (casePerson != null) {
					casePerson.setCaseId(caseInfo.getId());
					casePerson.setConsignmentId(consignmentInfo.getId());
					casePersonInfoService.save(casePerson);

					personUuidAndDbIdMap.put(casePerson.getLimsPersonUuid(), casePerson.getId());
				}

				// 样本信息
				DnaSampleInfo sampleInfo = v.getDnaSampleInfo();
				sampleInfo.setCaseId(caseInfo.getId());
				sampleInfo.setConsignmentId(consignmentInfo.getId());
				if (casePerson != null) {
					sampleInfo.setRefPersonId(casePerson.getId());
				}
				sampleInfoService.save(sampleInfo);
				sampleIds.add(sampleInfo.getId());
				
				// str基因信息
				List<DnaStrGeneInfo> strGeneInfoList = v.getDnaStrGeneInfoList();
				for (DnaStrGeneInfo geneInfo : strGeneInfoList) {
					geneInfo.setSampleId(sampleInfo.getId());
					strGeneInfoService.save(geneInfo);
					
					if (isTransfer) {
						// 比对队列
						Map<String, Object> settingsMap = Maps.newHashMap();
						settingsMap.put("INSTORE_DATA_TYPE", sampleInfo.getInstoreDataType());
						List<AutoCompareSettings> settingsList = autoCompareSettingsService.listByMap(settingsMap);
						settingsList.stream().forEach(settings -> {
							AutoCompareQueue autoCompareQueue = buildAutoCompareQueue(settings,
									geneInfo.getId(), settings.getCompareMode(), 1, sampleInfo.getLabServerNo());
							autoCompareQueueService.save(autoCompareQueue);
						});
					}
				}
				
				// ystr基因信息
				List<DnaYstrGeneInfo> ystrGeneInfoList = v.getDnaYstrGeneInfoList();
				for (DnaYstrGeneInfo geneInfo : ystrGeneInfoList) {
					geneInfo.setSampleId(sampleInfo.getId());
					ystrGeneInfoService.save(geneInfo);
					
					if (isTransfer) {
						// 比对队列
						Map<String, Object> settingsMap = Maps.newHashMap();
						settingsMap.put("INSTORE_DATA_TYPE", sampleInfo.getInstoreDataType());
						List<AutoCompareSettings> settingsList = autoCompareSettingsService.listByMap(settingsMap);
						settingsList.stream().forEach(settings -> {
							AutoCompareQueue autoCompareQueue = buildAutoCompareQueue(settings,
									geneInfo.getId(), settings.getCompareMode(), 1, sampleInfo.getLabServerNo());
							autoCompareQueueService.save(autoCompareQueue);
						});
					}
				}
			}

			for(PersonRelativeInfo personRelativeInfo : vo.getPersonRelativeInfoList()){
				personRelativeInfo.setRelationPersonUuid(personUuidAndDbIdMap.get(personRelativeInfo.getRelationPersonUuid()).toString());
				relativeInfoService.save(personRelativeInfo);
			}
			
			// 队列
			if (isTransfer) {
				// 上报队列
				// 上报案件信息
				TransferCaseQueue transfer = new TransferCaseQueue();
				transfer.setCaseId(caseInfo.getId());
				transfer.setCreateDatetime(LocalDateTime.now());
				transfer.setLabServerNo(caseInfo.getLabServerNo());
				transfer.setTransferFileName(null);
				transfer.setTransferFileRootPath(null);
				transfer.setTransferErrorMsg(null);
				transfer.setTransferStatus("0");
				transfer.setUpdateDatetime(LocalDateTime.now());
				transferCaseService.saveTransferCaseQueue(transfer);
				
				// 上报样本信息
				for (Integer sampleId : sampleIds) {
					TransferCaseSample transferCaseSample = new TransferCaseSample();
					transferCaseSample.setDnaSampleId(sampleId);
					transferCaseSample.setTransferCaseQueueId(transfer.getCaseId());
					transferCaseSample.setTransferStats(0);
					transferCaseSampleService.save(transferCaseSample);
				}
			}
			
			return 1;
		} catch (Exception e) {
			LOGGER.error("saveCase error: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	private AutoCompareQueue buildAutoCompareQueue(AutoCompareSettings settings, 
			Integer geneId, Integer compareMode, Integer frequencyId, String labServerNo) {
		List<String> targetLabServerNoList = Lists.newArrayList();
		targetLabServerNoList.add(labServerNo);
		
		JSONObject compareParams = new JSONObject();
		compareParams.put("mostDiffLimit", settings.getMostDiffLimit());
		compareParams.put("targetDataType", Lists.newArrayList());
		compareParams.put("lowestSameLimit", settings.getLowestSameLimit());
		compareParams.put("targetLabServerNo", targetLabServerNoList);
		
		AutoCompareQueue autoCompare = new AutoCompareQueue();
		autoCompare.setCompareMode(compareMode);
		autoCompare.setCompareParams(compareParams.toJSONString());
		autoCompare.setCompareStatus(PublicConstants.STATUS_NO);
		autoCompare.setCompareWeight(PublicConstants.DEFAULT_VALUE);
		autoCompare.setCreateDatetime(LocalDateTime.now());
		autoCompare.setGeneId(geneId);
		autoCompare.setPopulationFrequencyId(frequencyId);
		autoCompare.setUpdateDatetime(LocalDateTime.now());
		return autoCompare;
	}

	@Override
	@Transactional
	public int saveCriminal(ConverterDataVo vo) {
		try {
			
			// 委托案件信息
			ConsignmentInfo consignmentInfo = vo.getConsignmentInfo();
			consignmentInfoService.save(consignmentInfo);
			
			// 样本和人员信息
			List<ConverterDataSampleAndPersonVo> voList = vo.getSamplePersonList();
			for (ConverterDataSampleAndPersonVo v : voList) {
				
				// 建库人员
				CriminalPersonInfo personInfo = v.getCriminalPersonInfo();
				personInfo.setConsignmentId(consignmentInfo.getId());
				criminalPersionInfoService.save(personInfo);
				
				// 建库样本
				CriminalSampleInfo sampleInfo = v.getCriminalSampleInfo();
				sampleInfo.setConsignmentId(consignmentInfo.getId());
				sampleInfo.setRefPersonId(personInfo.getId());
				criminalSampleInfoService.save(sampleInfo);
				
				// str基因信息
				List<DnaStrGeneInfo> strGeneInfoList = v.getDnaStrGeneInfoList();
				for (DnaStrGeneInfo geneInfo : strGeneInfoList) {
					geneInfo.setSampleId(sampleInfo.getId());
				}
				strGeneInfoService.saveBatch(strGeneInfoList);
				
				// ystr基因信息
				List<DnaYstrGeneInfo> ystrGeneInfoList = v.getDnaYstrGeneInfoList();
				for (DnaYstrGeneInfo geneInfo : ystrGeneInfoList) {
					geneInfo.setSampleId(sampleInfo.getId());
				}
				ystrGeneInfoService.saveBatch(ystrGeneInfoList);
			}
			return 1;
		} catch (Exception e) {
			LOGGER.error("saveCriminal error: ", e);
			throw new DnaRuntimeException();
		}
	}

	@Override
	@Transactional
	public int saveQcSample(QcSampleInfo entity) {
		try {
			qcSampleInfoService.save(entity);
			return 1;
		} catch (Exception e) {
			LOGGER.error("saveCriminal error: ", e);
			throw new DnaRuntimeException();
		}
	}

}
