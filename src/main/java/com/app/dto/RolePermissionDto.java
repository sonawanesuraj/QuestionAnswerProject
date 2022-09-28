package com.app.dto;

public class RolePermissionDto {
	private Long id;
	private Long roleId;
	private Long permissionId;

	public RolePermissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolePermissionDto(Long id, Long roleId, Long permissionId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

}
