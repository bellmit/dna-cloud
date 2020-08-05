package com.bazl.dna.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "nt_sys_lab")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysLab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "org_id", nullable = false, length = 64)
	private String orgId;
	
	/**
	 * 鉴定中心全称 identify_name
	 */
	@Column(name = "identify_name" , nullable = true, length = 64)
	private String identifyName;
	
	/**
	 * 鉴定中心电话 identify_phone
	 */
	@Column(name = "identify_phone" , nullable = true, length = 32)
	private String identifyPhone;
	
	/**
	 * 鉴定中心地址 identify_address
	 */
	@Column(name = "identify_address" , nullable = true, length = 64)
	private String identifyAddress;
	
	/**
	 * DNA实验室名称 lab_name
	 */
	@Column(name = "lab_name" , nullable = true, length = 64)
	private String labName;
	
	/**
	 * DNA实验室级别 lab_level
	 */
	@Column(name = "lab_level" , nullable = true, length = 32)
	private String labLevel;
	
	/**
	 * 实验室负责人 lab_user
	 */
	@Column(name = "lab_user" , nullable = true, length = 32)
	private String labUser;
	
	/**
	 * 实验室负责人电话 lab_phone
	 */
	@Column(name = "lab_phone" , nullable = true, length = 32)
	private String labPhone;
	
	/**
	 * 服务器编号 server_number
	 */
	@Column(name = "server_number" , nullable = true, length = 32)
	private String serverNumber;
	
	/**
	 * 服务器ip server_ip
	 */
	@Column(name = "server_ip" , nullable = true, length = 32)
	private String serverIp;
	
	/**
	 * 服务器ip server_ip_left
	 */
	@Column(name = "server_ip_left" , nullable = true, length = 32)
	private String serverIpLeft;
	
	/**
	 * 服务器ip server_ip_right
	 */
	@Column(name = "server_ip_right" , nullable = true, length = 32)
	private String serverIpRight;
	
	/**
	 * 服务器ip server_ip_addr
	 */
	@Column(name = "server_ip_addr" , nullable = true, length = 64)
	private String serverIpAddr;
	
	/**
	 * 状态
	 */
	@Column(name = "status", nullable = true, length = 2)
	private String status;
	
	/**
	 * 用户 create_user
	 */
	@Column(name = "create_user" , nullable = true, length = 32)
	private String createUser;
	
	/**
	 * 创建时间 create_time
	 */
	@Column(name = "create_time" ,insertable=true, updatable=false)
	private Timestamp createTime;
	
	/**
	 * 更新时间update_time
	 */
	@Column(name = "update_time" ,insertable=true, updatable=false)
	private Timestamp updateTime;
	
	@Transient
	private long countLabUser;
	
	@Transient
	private String orgName;
	
	@Transient
	private List<String> clientOrgList;
	
	/**
	 * 构造方法
	 */
	public SysLab() {
		super();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the identifyName
	 */
	public String getIdentifyName() {
		return identifyName;
	}

	/**
	 * @param identifyName the identifyName to set
	 */
	public void setIdentifyName(String identifyName) {
		this.identifyName = identifyName;
	}

	/**
	 * @return the identifyPhone
	 */
	public String getIdentifyPhone() {
		return identifyPhone;
	}

	/**
	 * @param identifyPhone the identifyPhone to set
	 */
	public void setIdentifyPhone(String identifyPhone) {
		this.identifyPhone = identifyPhone;
	}

	/**
	 * @return the identifyAddress
	 */
	public String getIdentifyAddress() {
		return identifyAddress;
	}

	/**
	 * @param identifyAddress the identifyAddress to set
	 */
	public void setIdentifyAddress(String identifyAddress) {
		this.identifyAddress = identifyAddress;
	}

	/**
	 * @return the labName
	 */
	public String getLabName() {
		return labName;
	}

	/**
	 * @param labName the labName to set
	 */
	public void setLabName(String labName) {
		this.labName = labName;
	}

	/**
	 * @return the labLevel
	 */
	public String getLabLevel() {
		return labLevel;
	}

	/**
	 * @param labLevel the labLevel to set
	 */
	public void setLabLevel(String labLevel) {
		this.labLevel = labLevel;
	}

	/**
	 * @return the labUser
	 */
	public String getLabUser() {
		return labUser;
	}

	/**
	 * @param labUser the labUser to set
	 */
	public void setLabUser(String labUser) {
		this.labUser = labUser;
	}

	/**
	 * @return the labPhone
	 */
	public String getLabPhone() {
		return labPhone;
	}

	/**
	 * @param labPhone the labPhone to set
	 */
	public void setLabPhone(String labPhone) {
		this.labPhone = labPhone;
	}

	/**
	 * @return the serverNumber
	 */
	public String getServerNumber() {
		return serverNumber;
	}

	/**
	 * @param serverNumber the serverNumber to set
	 */
	public void setServerNumber(String serverNumber) {
		this.serverNumber = serverNumber;
	}

	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}

	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * @return the serverIpLeft
	 */
	public String getServerIpLeft() {
		return serverIpLeft;
	}

	/**
	 * @param serverIpLeft the serverIpLeft to set
	 */
	public void setServerIpLeft(String serverIpLeft) {
		this.serverIpLeft = serverIpLeft;
	}

	/**
	 * @return the serverIpRight
	 */
	public String getServerIpRight() {
		return serverIpRight;
	}

	/**
	 * @param serverIpRight the serverIpRight to set
	 */
	public void setServerIpRight(String serverIpRight) {
		this.serverIpRight = serverIpRight;
	}

	/**
	 * @return the serverIpAddr
	 */
	public String getServerIpAddr() {
		return serverIpAddr;
	}

	/**
	 * @param serverIpAddr the serverIpAddr to set
	 */
	public void setServerIpAddr(String serverIpAddr) {
		this.serverIpAddr = serverIpAddr;
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
	 * @return the countLabUser
	 */
	public long getCountLabUser() {
		return countLabUser;
	}

	/**
	 * @param countLabUser the countLabUser to set
	 */
	public void setCountLabUser(long countLabUser) {
		this.countLabUser = countLabUser;
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
	 * @return the clientOrgList
	 */
	public List<String> getClientOrgList() {
		return clientOrgList;
	}

	/**
	 * @param clientOrgList the clientOrgList to set
	 */
	public void setClientOrgList(List<String> clientOrgList) {
		this.clientOrgList = clientOrgList;
	}
	
}
