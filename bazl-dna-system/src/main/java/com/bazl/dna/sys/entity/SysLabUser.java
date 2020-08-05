package com.bazl.dna.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "nt_sys_lab_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysLabUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	private String id;
	
	@Column(name = "lab_id", nullable = false, length = 64)
	private Integer labId;
	
	/**
	 * 姓名 nick_name
	 */
	@Column(name = "nick_name", nullable = true, length = 32)
	private String nickName;
	
	/**
	 * 姓别 sex
	 */
	@Column(name = "sex", nullable = true, length = 2)
	private int sex;
	
	/**
	 * 身份证号 id_card
	 */
	@Column(name = "id_card", nullable = true, length = 32)
	private String idCard;
	
	/**
	 * 警号 police_card
	 */
	@Column(name = "police_card", nullable = true, length = 32)
	private String policeCard;
	
	/**
	 * 电话 user_phone
	 */
	@Column(name = "user_phone", nullable = true, length = 32)
	private String userPhone;
	
	/**
	 * 学历 education
	 */
	@Column(name = "education", nullable = true, length = 8)
	private String education;
	
	/**
	 * 毕业院校 user_school
	 */
	@Column(name = "user_school", nullable = true, length = 64)
	private String userSchool;
	
	/**
	 * 职务 user_post
	 */
	@Column(name = "user_post", nullable = true, length = 64)
	private String userPost;
	
	/**
	 * 职称 user_job
	 */
	@Column(name = "user_job", nullable = true, length = 64)
	private String userJob;
	
	/**
	 * 授权人签字 auth_sign
	 */
	@Column(name = "auth_sign", nullable = true, length = 64)
	private String authSign;
	
	/**
	 * 资质证书 cert_path
	 */
	@Column(name = "cert_path", nullable = true, length = 64)
	private String certPath;
	
	/**
	 * 系统登录名 system_user_name
	 */
	@Column(name = "system_user_name", nullable = true, length = 64)
	private String systemUserName;
	
	/**
	 * 系统角色 system_role_name
	 */
	@Column(name = "system_role_name", nullable = true, length = 64)
	private String systemRoleName;
	
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
	
	/**
	 * 构造方法
	 */
	public SysLabUser() {
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
	 * @return the labId
	 */
	public Integer getLabId() {
		return labId;
	}

	/**
	 * @param labId the labId to set
	 */
	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * @return the policeCard
	 */
	public String getPoliceCard() {
		return policeCard;
	}

	/**
	 * @param policeCard the policeCard to set
	 */
	public void setPoliceCard(String policeCard) {
		this.policeCard = policeCard;
	}

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}

	/**
	 * @param userPhone the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the userSchool
	 */
	public String getUserSchool() {
		return userSchool;
	}

	/**
	 * @param userSchool the userSchool to set
	 */
	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}

	/**
	 * @return the userPost
	 */
	public String getUserPost() {
		return userPost;
	}

	/**
	 * @param userPost the userPost to set
	 */
	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}

	/**
	 * @return the userJob
	 */
	public String getUserJob() {
		return userJob;
	}

	/**
	 * @param userJob the userJob to set
	 */
	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}

	/**
	 * @return the authSign
	 */
	public String getAuthSign() {
		return authSign;
	}

	/**
	 * @param authSign the authSign to set
	 */
	public void setAuthSign(String authSign) {
		this.authSign = authSign;
	}

	/**
	 * @return the certPath
	 */
	public String getCertPath() {
		return certPath;
	}

	/**
	 * @param certPath the certPath to set
	 */
	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	/**
	 * @return the systemUserName
	 */
	public String getSystemUserName() {
		return systemUserName;
	}

	/**
	 * @param systemUserName the systemUserName to set
	 */
	public void setSystemUserName(String systemUserName) {
		this.systemUserName = systemUserName;
	}

	/**
	 * @return the systemRoleName
	 */
	public String getSystemRoleName() {
		return systemRoleName;
	}

	/**
	 * @param systemRoleName the systemRoleName to set
	 */
	public void setSystemRoleName(String systemRoleName) {
		this.systemRoleName = systemRoleName;
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

}
