package com.karros.vn.model.token;

public class LoginToken {
	private Integer id;
	private Integer userId;
	private String token;
	private String uuid;
	private Integer createdBy;
	private Integer updatedBy;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
  public Integer getUpdatedBy() {
    return updatedBy;
  }
  public void setUpdatedBy(Integer updatedBy) {
    this.updatedBy = updatedBy;
  }
	
}
