package com.netpan.service;

import java.util.List;

import com.netpan.entity.Share;
import com.netpan.entity.User;

public interface ShareService {
	/**
	 * 添加分享
	 * @param user
	 * @param share
	 * @param shareedUserId
	 */
	public void addShare(User user, Share share, String shareedUserName);
	
	/**
	 * 获得我的分享
	 * @param user
	 * @return
	 */
	public List<Share> getMyShareList(User user);
	
	/**
	 * 获得我收到的分享
	 * @param user
	 * @return
	 */
	public List<Share> getShareedList(User user);
	
	/**
	 * 删除我的分享
	 * @param user
	 * @param shareid
	 */
	public void deleteMyShareList(User user, long shareid);
}
