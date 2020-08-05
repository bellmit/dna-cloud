package com.bazl.dna.elasticsearch.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.elasticsearch.entity.SampleGene;
import com.bazl.dna.elasticsearch.service.ISampleGeneElasticsearchService;

/**
 * Elasticsearch Controller
 * @author zhaoyong
 *
 */
@RefreshScope
@RestController
@RequestMapping("/sampleGene")
public class SampleGeneController {

	@Autowired
	private ISampleGeneElasticsearchService search;
	
	/**
	 * 保存
	 * @param sampleGene
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData save(@RequestBody SampleGene sampleGene) {
		if (sampleGene != null) {
			return new ResponseData(search.save(sampleGene));
		}
		return new ResponseData();
	}
	
	/**
	 * id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/get")
	public ResponseData get(@RequestParam("id") String id) {
		if (StringUtils.trimToNull(id) != null) {
			Optional<SampleGene> entity = search.findById(id);
			return new ResponseData(entity);
		}
		return new ResponseData();
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(@RequestParam("id") String id) {
		if (StringUtils.trimToNull(id) != null) {
			search.deleteById(id);
		}
		return new ResponseData();
	}
	
	/**
	 * 列表
	 * @return
	 */
	@PostMapping("/list")
	public ResponseData list(@RequestBody JSONObject paramJson) {
		//TODO 
		// matchQuery: 模糊查询不是精确匹配  
		// termQuery: 精确匹配匹配不到
		
//		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//		queryBuilder.must(QueryBuilders.matchPhraseQuery("geneInfo.D3S1358", "16/16"));
//		queryBuilder.must(QueryBuilders.matchPhraseQuery("geneInfo.D18S51", "14/14"));
//		queryBuilder.must(QueryBuilders.matchPhraseQuery("geneInfo.vWA", "13/14"));
//		queryBuilder.must(QueryBuilders.matchPhraseQuery("geneInfo.FGA", "23/27"));
//		
//		NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("geneInfo", 
//				queryBuilder, ScoreMode.None);
//		queryBuilder.must(nestedQueryBuilder);
//		Iterable<SampleGene> list = this.search.search(queryBuilder);
		
		// 查询条件
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		if (StringUtils.trimToNull(paramJson.getString("sampleId")) != null) {
			queryBuilder.withQuery(QueryBuilders.matchQuery("sampleId", paramJson.getString("sampleId")));
		}
//		queryBuilder.withQuery(QueryBuilders.termQuery("geneInfo.D3S1358", "16/16"));
//		queryBuilder.withQuery(QueryBuilders.matchQuery("geneInfo.D18S51", "14/14"));
//		queryBuilder.withQuery(QueryBuilders.matchQuery("geneInfo.vWA", "13/14"));
//		queryBuilder.withQuery(QueryBuilders.matchQuery("geneInfo.FGA", "23/27"));
		
		Iterable<SampleGene> list = this.search.search(queryBuilder.build());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		return new ResponseData(result);
	}
}
