package com.bazl.dna.elasticsearch.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.bazl.dna.elasticsearch.entity.SampleGene;

/**
 * Elasticsearch Repository
 * @author zhaoyong
 *
 */
@Repository
public interface ISampleGeneElasticsearchService extends ElasticsearchRepository<SampleGene, String> {


}
