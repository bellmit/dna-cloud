package com.bazl.dna.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bazl.dna.test.entity.SampleGene;

public interface ISampleGeneDao extends JpaRepository<SampleGene, String>, JpaSpecificationExecutor<SampleGene> {

}
