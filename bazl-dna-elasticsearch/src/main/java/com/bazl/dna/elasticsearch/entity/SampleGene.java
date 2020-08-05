package com.bazl.dna.elasticsearch.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.alibaba.fastjson.JSONObject;

@Document(indexName = "samplegene", type = "gene", shards = 1, replicas = 0)
public class SampleGene implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Field(type = FieldType.Keyword)
	private String sampleId;

	@Field(type = FieldType.Object, analyzer = "ik_smart")
	private JSONObject geneInfo;

	
	public SampleGene() {
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSampleId() {
		return sampleId;
	}


	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}


	public JSONObject getGeneInfo() {
		return geneInfo;
	}


	public void setGeneInfo(JSONObject geneInfo) {
		this.geneInfo = geneInfo;
	}
	
}
