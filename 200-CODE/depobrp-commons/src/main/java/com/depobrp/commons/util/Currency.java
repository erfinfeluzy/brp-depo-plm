package com.depobrp.commons.util;


public enum Currency {

	IDR("Rp",	"IDR", Language.id_ID, "rupiah"), 
	USD("$",	"USD", Language.en_US, "dollar"),
	JPY("´",	"JPY", Language.en_US, "yen"),
	CNY("CNY",	"CNY", Language.en_US, "yuan"),
	GBP("£",	"GBP", Language.en_US, "poundterling"),
	EUR("Û",	"EUR", Language.en_US, "euro");

	private Currency(String symbol, String code, Language language, String speech) {
		this.symbol = symbol;
		this.code = code;
		this.language = language;
		this.speech = speech;
	}
	
	private String symbol;
	private String code;
	private Language language;
	private String speech;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
}
