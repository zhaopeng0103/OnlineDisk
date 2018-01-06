package com.netpan.service;

import com.netpan.entity.User;

public interface UserService {
	public void addUser(User user);
	
	public boolean checkEmail(String email);
	
	public boolean checkName(String name);
	
	public int login(User user);
}
