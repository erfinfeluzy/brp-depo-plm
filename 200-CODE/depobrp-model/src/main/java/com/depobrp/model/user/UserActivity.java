package com.depobrp.model.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.depobrp.model.common.Auditable;

@Entity
@Table(name = "TBL_MS_USER_ACTIVITY")
public class UserActivity extends Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5847292617736376849L;
	
	private static final int MAX_LENGTH = 255;
	
	public UserActivity(String description, String createdBy) {
		super();
		Date trxDate = new Date();
		int maxLength = description.length() < MAX_LENGTH ? description.length() : MAX_LENGTH; 
		this.description = description.substring(0, maxLength);
		this.createdDate = trxDate;
		this.createdBy = createdBy;
		this.updatedDate = trxDate;
		this.updatedBy = createdBy;
	}

	public UserActivity() {
		super();
	}

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DESCRIPTION", length = 255 )
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
