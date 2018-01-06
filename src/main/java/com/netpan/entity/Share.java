package com.netpan.entity;

public class Share {
	private String path;
	private String originalPath;
	private String type;
	private String name;
	private String sharetime;
	private long shareedUserId;
	private String shareedUserName;
	private long shareid;
	
	private String shareUserName;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getOriginalPath() {
		return originalPath;
	}
	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSharetime() {
		return sharetime;
	}
	public void setSharetime(String sharetime) {
		this.sharetime = sharetime;
	}
	public long getShareedUserId() {
		return shareedUserId;
	}
	public void setShareedUserId(long shareedUserId) {
		this.shareedUserId = shareedUserId;
	}
	public String getShareedUserName() {
		return shareedUserName;
	}
	public void setShareedUserName(String shareedUserName) {
		this.shareedUserName = shareedUserName;
	}
	public long getShareid() {
		return shareid;
	}
	public void setShareid(long shareid) {
		this.shareid = shareid;
	}

	public String getShareUserName() {
		return shareUserName;
	}
	public void setShareUserName(String shareUserName) {
		this.shareUserName = shareUserName;
	}
	
}
