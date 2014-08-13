package com.depobrp.commons.util;

public enum Language {
	
	en_US("English - US"), 
	id_ID("Bahasa Indonesia");

	private Language(String description) {
		this.description = description;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
