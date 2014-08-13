package com.depobrp.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ContactType 
			extends Auditable 
			implements Serializable {

	private static final long serialVersionUID = -6722075027654677292L;
	
	@Column(name = "NAME", length = 64)
	private String name;
	
	@Column(name = "EMAIL", length = 64)
	private String email;
	
	@Column(name = "ADDRESS", length = 64)
	private String address;
	
	@Column(name = "POSTAL_CODE", length = 12)
	private String postalCode;

	@Column(name = "PHONE_1", length = 32)
	private String phone1;
	
	@Column(name = "PHONE_2", length = 32)
	private String phone2;

	@Column(name = "FAX", length = 32)
	private String fax;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
}
