package com.netpan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netpan.dao.UserDao;
import com.netpan.entity.User;
import com.netpan.service.UserService;
import com.netpan.util.MD5Util;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 添加用户或注册用户
	 */
	@Override
	public void addUser(User user) {
		user.setPwd(MD5Util.encodePwd(user.getPwd()));
		userDao.addUser(user);
	}
	
	/**
	 * 检测邮箱是否重复
	 */
	@Override
	public boolean checkEmail(String email) {
		return userDao.checkEmail(email);
	}
	
	/**
	 * 检测姓名是否重复
	 */
	@Override
	public boolean checkName(String name) {
		return userDao.checkName(name);
	}
	
	/**
	 * 登录判断用户名与密码
	 */
	@Override
	public int login(User user) {
		long id = userDao.getIdByName(user.getName());
		if (id > 0) {
			User user2 = userDao.getById(id);
			if (user2!=null && MD5Util.isPwdRight(user.getPwd(), user2.getPwd())) {
				user.setId(id);
				user.setEmail(user2.getEmail());
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}
	
}
