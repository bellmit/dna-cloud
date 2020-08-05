package com.bazl.dna.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "nt_sys_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	private String id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "menu_type_id" ,nullable = true)
	private SysMenuType sysMenuType;
	
	@Column(name = "menu_name", nullable = false, length = 32)
	private String menuName;
	
	@Column(name = "another_name", nullable = true, length = 32)
	private String anotherName;
	
	@Column(name = "parent_id", nullable = false, length = 32)
	private String parentId;
	
	@Column(name = "menu_order", nullable = false)
	private Integer menuOrder;
	
	@Column(name = "menu_action", nullable = false, length = 32)
	private String menuAction;
	
	@Column(name = "menu_description", nullable = true, length = 255)
	private String menuDescription;
	
	@Column(name = "status", nullable = true, columnDefinition="INT default 0", length = 32)
	private String status;
	
	/**
	 * 创建时间 create_time
	 */
	@Column(name = "create_time" ,insertable=true, updatable=false)
	private Timestamp createTime;
	
	/**
	 * 更新时间 update_time
	 */
	@Column(name = "update_time" ,insertable=true, updatable=false)
	private Timestamp updateTime;
	
	/**
	 * 图标
	 */
	@Column(name = "icon", nullable = true, length = 255)
	private String icon;
	
	/**
	 * Constructor
	 */
	public SysMenu(){
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysMenuType getSysMenuType() {
		return sysMenuType;
	}

	public void setSysMenuType(SysMenuType sysMenuType) {
		this.sysMenuType = sysMenuType;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
