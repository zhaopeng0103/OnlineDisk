package com.netpan.service;

import java.util.List;

import com.netpan.entity.User;

public interface FollowService {
	/**
	 * 得到所关注的用户姓名
	 * @param user
	 * @return
	 */
	public List<String> getFollowUser(User user);
	
	/**
	 * 获得搜索用户
	 * @param user
	 * @param searchName
	 * @return
	 */
	public List<String> searchUser(User user, String searchName);
	
	/**
	 * 添加关注
	 * @param user
	 * @param followName
	 */
	public void addFollowUser(User user, String followName);
	
	/**
	 * 取消关注
	 * @param user
	 * @param unfollowName
	 */
	public void unFollowUser(User user, String unfollowName);
}
