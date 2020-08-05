package com.bazl.dna.database.sync.task.thread;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.sync.client.GeneListServerClient;
import com.bazl.dna.database.sync.service.vo.SampleDnaGeneVo;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class SyncMixGeneThread {

	private static final Logger LOG = LoggerFactory.getLogger(SyncMixGeneThread.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private GeneListServerClient geneListServerClient;

	/**
	 * 加载对应的基因信息数据转换为redis数据库的存储格式，发送至redis数据库
	 * key：[基因类型]-[入库数据类型_亲属关系1_亲属关系2]-[实验室服务器编号]-[基因数据id]
	 */
	@Async
	public void execute(List<Map<String, String>> listLocusName,
			int pageIndex, int pageSize) {
		try {
			LOG.info("Thread: {}, limit:{},{}", Thread.currentThread().getName(), pageIndex, pageSize);
			Gson gson = new Gson();
			LOG.info("------------查询单一样本-------------");
			ResponseData selectSingleGeneByCwsdPage = geneListServerClient.selectSingleGeneByCwsdPage(String.valueOf(pageIndex),pageSize);
			if (PublicConstants.SUCCESS_CODE == selectSingleGeneByCwsdPage.getCode()) {
				List<SampleDnaGeneVo> postList = gson.fromJson(gson.toJson(selectSingleGeneByCwsdPage.getResult()),
						new TypeToken<List<SampleDnaGeneVo>>() {}.getType());
				if (ListUtils.isNotNullAndEmptyList(postList)) {
					for (SampleDnaGeneVo geneVo : postList) {
						String gene = GeneTransFormUtils.geneFormatConversion(geneVo.getEntity().getGeneInfo(), listLocusName);
						String key = geneVo.getEntity().getGeneType() + "-" + geneVo.getPersonCode() + "_0_0" + "-"
								+ geneVo.getEntity().getInitServerNo() + "-" + geneVo.getEntity().getId();
						if (gene != null && !gene.isEmpty() && !gene.equals("{}")) {
							redisTemplate.opsForValue().set(key, gene);
						}else {
							LOG.error("单一样本基因信息为空！=====" + geneVo.getEntity().getId());
						}
					}
					LOG.info("===========插入redis成功=============");
				}else {
					LOG.error("查询单一样本为空！");
				}
			}else {
				LOG.error("查询单一样本失败！");
			}
		} catch (Exception e) {
			LOG.error("SyncMixGeneThread error:", e);
		}
	}
}
