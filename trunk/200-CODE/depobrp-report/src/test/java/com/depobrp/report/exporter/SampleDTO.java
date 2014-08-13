package com.depobrp.report.exporter;

public class SampleDTO {

	public SampleDTO() {}
	
	public SampleDTO(String username, String supervisor) {
		super();
		this.username = username;
		this.supervisor = supervisor;
	}

	private String username;
	private String supervisor;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
}
