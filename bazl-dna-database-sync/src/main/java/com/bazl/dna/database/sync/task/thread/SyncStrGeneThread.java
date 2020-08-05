package com.bazl.dna.database.sync.task.thread;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.constants.RedisConstants;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.CriminalSampleInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.PersonRelationInfoService;

/**
 * @author lizhihua on 2020-02-03.
 */
@Component
public class SyncStrGeneThread {

	private static final Logger LOG = LoggerFactory.getLogger(SyncStrGeneThread.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private CasePersonInfoService casePersonInfoService;

	@Autowired
	private PersonRelationInfoService personRelationInfoService;

	@Autowired
	private DnaSampleInfoService sampleInfoService;
	
	@Autowired
	private CriminalSampleInfoService criminalSampleInfoService;

	@Autowired
	private DnaStrGeneInfoService geneInfoService;

	/**
	 * 执行当前实验室的基因数据同步
	 * 
	 * @param pendingLabServerInfo
	 * @throws Exception
	 */
	@Async
	public void execute(int pageIndex, int pageSize) throws Exception {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		LOG.info("Thread: {}, limit:{},{}", Thread.currentThread().getName(), pageIndex, pageSize);

		/**
		 *
		 * 根据labServerNo读取gene_sync_queue表中的待同步数据，
		 * 加载对应的基因信息数据转换为redis数据库的存储格式，发送至redis数据库
		 * key：[基因类型]-[入库数据类型_亲属关系1_亲属关系2]-[实验室服务器编号]-[基因数据id]
		 */
		List<Map<String, Object>> geneInfoList = geneInfoService.findList(pageable.getOffset(), pageable.getPageSize());
		for (Map<String, Object> map : geneInfoList) {
			String relationship = "_";
			if (map.get("sampleId") != null) {
				DnaSampleInfo sampleInfo = sampleInfoService.getById(Integer.parseInt(map.get("sampleId").toString()));
				String instoreDataType = "";
				Integer refPersonId = null;
				
				try {
					if (sampleInfo != null) {
						instoreDataType = StringUtils.trimToEmpty(sampleInfo.getInstoreDataType());
						refPersonId = sampleInfo.getRefPersonId();
					} else {
						CriminalSampleInfo criminalSampleInfo = criminalSampleInfoService.getById(Integer.parseInt(map.get("sampleId").toString()));
						if (criminalSampleInfo != null) {
							instoreDataType = StringUtils.trimToEmpty(criminalSampleInfo.getInstoreDataType());
							refPersonId = criminalSampleInfo.getRefPersonId();
						}
					}
					// 如果包含08 09 10 则属于亲属库else属于单一
					if (instoreDataType.contains(RedisConstants.CRIMINALSUSPECT_RELATIVES)
							|| instoreDataType.contains(RedisConstants.VICTIM_RELATIVES)
							|| instoreDataType.contains(RedisConstants.MISSING_RELATIVES)) {
						CasePersonInfo casePersonInfo = new CasePersonInfo();
						casePersonInfo.setId(refPersonId);
						// 查询人员id
						List<CasePersonInfo> casePersonInfoList = casePersonInfoService.queryCasePerson(casePersonInfo);
						if (!casePersonInfoList.isEmpty()) {
							PersonRelativeInfo personRelativeInfo = new PersonRelativeInfo();
							personRelativeInfo.setRelationPersonUuid(casePersonInfoList.get(0).getId().toString());
							// 查询亲属关系
							List<PersonRelativeInfo> personRelativeInfoList = personRelationInfoService
									.queryPersonRelationList(personRelativeInfo);
							for (PersonRelativeInfo relativeInfo : personRelativeInfoList) {
								relationship = relationship + "_" + relativeInfo.getRelationTypeCode();
							}
						}
						redisTemplate.opsForValue()
								.set("1" + "-" + instoreDataType + relationship + "-"
										+ map.get("labServerNo").toString() + "-" + map.get("id").toString(), map.get("geneInfo").toString());
					} else {
						redisTemplate.opsForValue()
								.set("1" + "-" + instoreDataType + "_0_0" + "-"
										+ map.get("labServerNo").toString() + "-" + map.get("id").toString(), map.get("geneInfo").toString());
					}
				} catch (Exception e) {
					LOG.error("error: ", map.get("sampleId"));
					e.printStackTrace();
				}
			} else {
				LOG.error("Thread not found: {}", map);
			}
		}
	}

}
