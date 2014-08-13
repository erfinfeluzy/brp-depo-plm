package com.depobrp.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.depobrp.model.common.ContactType;

@Entity
@Table(name = "TBL_MS_VESSEL")
public class Vessel extends ContactType{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111744531354939265L;

	@Id
	@Column(name = "VESSEL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
