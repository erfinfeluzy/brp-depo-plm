package com.depobrp.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_MS_PAGE")
public class Page implements Serializable {

	public Page() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6194838084214533921L;
	
	@Id
	@Column(name = "PAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "LABEL")
	private String label;
	
	@Column(name = "ICON_URI")
	private String iconUri;
	
	@Column(name = "URI")
	private String uri;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name="MODULE_ID", nullable=false)
	private Module module;
	
	public Page(String name, String label, String iconUri, String uri) {
		super();
		this.name = name;
		this.label = label;
		this.iconUri = iconUri;
		this.uri = uri;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIconUri() {
		return iconUri;
	}
	public void setIconUri(String iconUri) {
		this.iconUri = iconUri;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
