package com.depobrp.model.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Auditable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2384045845021935904L;

	@Column(name = "CREATED_BY", length = 64)
	protected String createdBy;
	
	@Column(name = "CREATED_DATE")
	protected Date createdDate;
	
	@Column(name = "UPDATED_BY", length = 64)
	protected String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	protected Date updatedDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



}
