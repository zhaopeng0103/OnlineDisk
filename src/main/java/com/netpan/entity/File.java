package com.netpan.entity;

public class File {
	private long id; // 序号
	private String originalName; // 原文件名
	private String name; // 存储在HDFS中的文件名
	private boolean isFile; // 是否是文件
	private boolean isDir; // 是否是目录
	private String size; // 经转换的文件大小字符串
	private String path; // 文件路径
	private String date;
	
	private String type;
	private String viewflag;
	
	private String originalPath;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		if (isDir) {
			type = "D";
		}
		if (isFile) {
			type = "F";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViewflag() {
		return viewflag;
	}

	public void setViewflag(String viewflag) {
		this.viewflag = viewflag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}
	
}
