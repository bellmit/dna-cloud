package com.bazl.dna.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "nt_sys_dict_item_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysDictItemType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	private String id;
	
	@Column(name = "dict_item_type_name", nullable = true, length = 32)
	private String typeName;
	
	@Column(name = "dict_type_code", nullable = false, length = 32)
	private String typeCode;
	
	@Column(name = "dict_item_type_des", nullable = true, length = 32)
	private String typeDes; 
	
	@Column(name = "dict_item_type_order", nullable = true, length = 10)
	private Integer typeOrder;
	
	@Column(name = "del_status", nullable = false, columnDefinition="INT default 0 ",length = 32)
	private String delStatus;
	
	/**
	 * Constructor
	 */
	public SysDictItemType(){
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDes() {
		return typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}

	public Integer getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(Integer typeOrder) {
		this.typeOrder = typeOrder;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}


	

}
