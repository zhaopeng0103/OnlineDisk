package com.netpan.dao;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.netpan.dao.basedao.HbaseDao;
import com.netpan.entity.User;
import com.netpan.util.Constants;

@Repository("followDao")
public class FollowDao {
	@Autowired
	private HbaseDao hbaseDao;
	
	/**
	 * 得到某人所关注的用户
	 * @param user
	 * @return
	 */
	public Result getFollow(User user){
		return hbaseDao.getResultByRow(Constants.TABLE_FOLLOW, user.getId());
	}
	
	/**
	 * 扫描user_id表
	 * @param filter
	 * @return
	 */
	public ResultScanner getResultScannerByUserId(Filter filter) {
		return hbaseDao.getResultScannerByFilter(Constants.TABLE_USERId, filter);
	}
	
	/**
	 * 添加关注
	 * @param user
	 * @param followId
	 * @param followName
	 */
	public void addFollow(User user, long followId, String followName) {
		hbaseDao.updateOneData(Constants.TABLE_FOLLOW, user.getId(), Constants.FAMILY_FOLLOW_NAME, followId, followName);
	}
	
	/**
	 * 取消关注
	 * @param user
	 * @param unfollowId
	 */
	public void cancelFollow(User user, long unfollowId) {
		hbaseDao.deleteDataByColumn(Constants.TABLE_FOLLOW, user.getId(), Constants.FAMILY_FOLLOW_NAME, unfollowId);
	}
	
	/**
	 * 添加被关注
	 * @param user
	 * @param followId
	 */
	public void addFollowed(User user, long followedId) {
		hbaseDao.updateOneData(Constants.TABLE_FOLLOWED, followedId + "_" + user.getId(), Constants.FAMILY_FOLLOWED_USERID, Constants.COLUMN_FOLLOWED_USERID, user.getId());
	}
	
	/**
	 * 取消被关注
	 * @param user
	 * @param unfollowId
	 */
	public void cancelFollowed(User user, long unfollowedId) {
		hbaseDao.deleteDataByRow(Constants.TABLE_FOLLOWED, unfollowedId + "_" + user.getId());
	}
}
