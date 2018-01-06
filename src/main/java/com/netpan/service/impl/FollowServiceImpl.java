package com.netpan.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netpan.dao.FollowDao;
import com.netpan.dao.UserDao;
import com.netpan.entity.User;
import com.netpan.service.FollowService;

@Service("followService")
public class FollowServiceImpl implements FollowService {
	@Autowired
	private FollowDao followDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 得到所关注的用户姓名和id
	 * @param user
	 * @return
	 */
	@Override
	public List<String> getFollowUser(User user) {
		List<String> followList = new ArrayList<String>();
		Result result = followDao.getFollow(user);
		if(!result.isEmpty()) {
			for (Cell cell : result.listCells()) {
				followList.add(Bytes.toString(CellUtil.cloneValue(cell)));
	        }
		}
		return followList;
	}
	
	/**
	 * 获得搜索用户
	 * @param user
	 * @param searchName
	 * @return
	 */
	@Override
	public List<String> searchUser(User user, String searchName){
		List<String> followList = getFollowUser(user);
		List<String> userList = new ArrayList<String>();
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(searchName));
		ResultScanner resultScanner;
		if(searchName.equals("")||searchName.isEmpty()) {
			resultScanner = followDao.getResultScannerByUserId(null);
		}else {
			resultScanner = followDao.getResultScannerByUserId(filter);
		}
		Iterator<Result> iter = resultScanner.iterator();
		while(iter.hasNext()) {
			Result result = iter.next();
			if(!result.isEmpty()) {
				String name = Bytes.toString(result.getRow());
				if((!name.equals(user.getName()))&&(!followList.contains(name))) {
					userList.add(name);
				}
			}
		}
		return userList;
	}
	
	/**
	 * 添加关注用户
	 * @param user
	 * @param followName
	 */
	@Override
	public void addFollowUser(User user, String followName) {
		long followId = userDao.getIdByName(followName);
		followDao.addFollow(user, followId, followName);
		followDao.addFollowed(user, followId);
	}
	
	/**
	 * 取消关注
	 * @param user
	 * @param unfollowName
	 */
	@Override
	public void unFollowUser(User user, String unfollowName) {
		long unfollowId = userDao.getIdByName(unfollowName);
		followDao.cancelFollow(user, unfollowId);
		followDao.cancelFollowed(user, unfollowId);
	}
	
}
