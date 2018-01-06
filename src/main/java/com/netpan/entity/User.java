package com.netpan.entity;

public class User {
	private long id; // 编号
	private String pwd; // 密码
	private String name; // 姓名
	private String email; // 邮箱
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
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
	
	@Override
	public String toString() {
		return "User [getId()=" + getId() + ", getPwd()=" + getPwd() + ", getName()=" + getName() + ", getEmail()="
				+ getEmail() + "]";
	}
	
}
