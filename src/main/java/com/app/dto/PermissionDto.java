package com.app.dto;

public class PermissionDto {

	private Long id;
	private String actionName;
	private String description;
	private String method;
	private String path;
	private String baseUrl;

	public PermissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PermissionDto(Long id, String actionName, String description, String method, String path, String baseUrl) {
		super();
		this.id = id;
		this.actionName = actionName;
		this.description = description;
		this.method = method;
		this.path = path;
		this.baseUrl = baseUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
