package com.bazl.dna.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 机构表 - nt_sys_organization
 *
 * @author liutao
 *
 */
@Entity
@Table(name = "nt_sys_organization")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysOrganization implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号 id
	 */
	@Id
	private String id;

	/**
	 * 机构名称 org_name
	 */
	@Column(name = "org_name" ,nullable = false, length = 255)
	private String orgName;

	/**
	 * 机构代码 org_code
	 */
	@Column(name = "org_code" ,nullable = false, length = 255)
	private String orgCode;

	/**
	 * 机构统一社会信用代码 org_credit_code
	 */
	@Column(name = "org_credit_code" ,nullable = true, length = 255)
	private String orgCreditCode;

	/**
	 * 机构行政划区代码 org_area_code
	 */
	@Column(name = "org_area_code" ,nullable = true, length = 255)
	private String orgAreaCode;

	/**
	 * 机构类型 org_type
	 */
	@Column(name = "org_type" ,nullable = true, length = 32)
	private String orgType;

	/**
	 * 机构图标 org_logo
	 */
	@Column(name = "org_logo" ,nullable = true, length = 64)
	private String orgLogo;
	
	/**
	 * 机构图标地址 org_logo_url
	 */
	@Column(name = "org_logo_url" ,nullable = true, length = 64)
	private String orgLogoUrl;
	
	/**
	 * 机构排序 org_order
	 */
	@Column(name = "org_order" ,nullable = true, length = 10)
	private Integer orgOrder;

	/**
	 * 机构父级机构编号 parent_id
	 */
	@Column(name = "parent_id" ,nullable = false, length = 32)
	private String parentId;
	
	/**
	 * 状态 status
	 */
	@Column(name = "status" ,nullable = false, columnDefinition="INT default 1", length = 32)
	private String status;
	
	/**
	 * 更新时间update_time
	 */
	@Column(name = "update_time" ,insertable=true, updatable=false)
	private Timestamp updateTime;

	/**
	 * 创建时间 create_time
	 */
	@Column(name = "create_time" ,insertable=true, updatable=false)
	private Timestamp createTime;
	
	/**
	 *  删除状态del_status
	 */
	@Column(name = "del_status", nullable = false, columnDefinition="INT default 0", length = 32)
	private String delStatus;
	
	/**
	 * 业务负责人
	 */
	@Column(name = "business_name", nullable = true, length = 32)
	private String businessName;
	
	/**
	 * 业务负责人电话
	 */
	@Column(name = "business_phone", nullable = true, length = 32)
	private String businessPhone;
	
	/**
	 * 技术负责人
	 */
	@Column(name = "technical_name", nullable = true, length = 32)
	private String technicalName;
	
	/**
	 * 技术负责人电话
	 */
	@Column(name = "technical_phone", nullable = true, length = 32)
	private String technicalPhone;
	
	/**
	 * 技术负责人电话
	 */
	@Column(name = "create_user", nullable = true, length = 64)
	private String createUser;
	
	/**
	 * 数据源
	 */
	@OneToOne
	@Cascade(value = { CascadeType.MERGE, CascadeType.SAVE_UPDATE }) 
	@JoinColumn(name = "config_id" ,nullable = true)
	private DataSourceConfig dataSourceConfig;
	
	/**
	 * Generate constructor
	 */
	public SysOrganization() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the orgCreditCode
	 */
	public String getOrgCreditCode() {
		return orgCreditCode;
	}

	/**
	 * @param orgCreditCode the orgCreditCode to set
	 */
	public void setOrgCreditCode(String orgCreditCode) {
		this.orgCreditCode = orgCreditCode;
	}

	/**
	 * @return the orgAreaCode
	 */
	public String getOrgAreaCode() {
		return orgAreaCode;
	}

	/**
	 * @param orgAreaCode the orgAreaCode to set
	 */
	public void setOrgAreaCode(String orgAreaCode) {
		this.orgAreaCode = orgAreaCode;
	}

	/**
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * @return the orgLogo
	 */
	public String getOrgLogo() {
		return orgLogo;
	}

	/**
	 * @param orgLogo the orgLogo to set
	 */
	public void setOrgLogo(String orgLogo) {
		this.orgLogo = orgLogo;
	}

	/**
	 * @return the orgLogoUrl
	 */
	public String getOrgLogoUrl() {
		return orgLogoUrl;
	}

	/**
	 * @param orgLogoUrl the orgLogoUrl to set
	 */
	public void setOrgLogoUrl(String orgLogoUrl) {
		this.orgLogoUrl = orgLogoUrl;
	}

	/**
	 * @return the orgOrder
	 */
	public Integer getOrgOrder() {
		return orgOrder;
	}

	/**
	 * @param orgOrder the orgOrder to set
	 */
	public void setOrgOrder(Integer orgOrder) {
		this.orgOrder = orgOrder;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * @return the businessPhone
	 */
	public String getBusinessPhone() {
		return businessPhone;
	}

	/**
	 * @param businessPhone the businessPhone to set
	 */
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	/**
	 * @return the technicalName
	 */
	public String getTechnicalName() {
		return technicalName;
	}

	/**
	 * @param technicalName the technicalName to set
	 */
	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}

	/**
	 * @return the technicalPhone
	 */
	public String getTechnicalPhone() {
		return technicalPhone;
	}

	/**
	 * @param technicalPhone the technicalPhone to set
	 */
	public void setTechnicalPhone(String technicalPhone) {
		this.technicalPhone = technicalPhone;
	}

	/**
	 * @return the delStatus
	 */
	public String getDelStatus() {
		return delStatus;
	}

	/**
	 * @param delStatus the delStatus to set
	 */
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	/**
	 * @return the dataSourceConfig
	 */
	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	/**
	 * @param dataSourceConfig the dataSourceConfig to set
	 */
	public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	
}