package com.depobrp.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.depobrp.model.common.ContactType;

@Entity
@Table(name = "TBL_MS_SHIPER")
public class Shiper extends ContactType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -620726766050516992L;
	
	@Id
	@Column(name = "SHIPER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
