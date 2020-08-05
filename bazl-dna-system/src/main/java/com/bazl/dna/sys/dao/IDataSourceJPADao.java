package com.bazl.dna.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bazl.dna.sys.entity.DataSourceConfig;

public interface IDataSourceJPADao
		extends JpaRepository<DataSourceConfig, String>, JpaSpecificationExecutor<DataSourceConfig> {

	@Query(value = "from DataSourceConfig where connectName=?1 ")
	public DataSourceConfig getConnectName(String connectName);

}
