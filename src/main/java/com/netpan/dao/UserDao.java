package com.netpan.dao;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.netpan.dao.basedao.HbaseDao;
import com.netpan.entity.User;
import com.netpan.util.Constants;

@Repository("userDao")
public class UserDao{
	@Autowired
	private HbaseDao hbaseDao;
	
	/**
	 * 添加用户或注册用户
	 * @param user
	 */
	public void addUser(User user) {
		String[] value = {user.getPwd(), user.getName(), user.getEmail()};
		long rowKey = hbaseDao.incrCounter(Constants.TABLE_GID, Constants.ROWKEY_GID_GID, Constants.FAMILY_GID_GID, Constants.COLUMN_GID_GID, 1);
		hbaseDao.updateMoreData(Constants.TABLE_IDUSER, rowKey, Constants.FAMILY_IDUSER_USER, Constants.COLUMN_IDUSER_PWDNAMEEMAIL, value);
		hbaseDao.updateOneData(Constants.TABLE_USERId, user.getName(), Constants.FAMILY_USERId_ID, Constants.COLUMN_USERId_ID, rowKey);
		hbaseDao.updateOneData(Constants.TABLE_EMAILUSER, user.getEmail(), Constants.FAMILY_EMAILUSER_USER, Constants.COLUMN_EMAILUSER_USERID, rowKey);
	}
	
	/**
	 * 检测邮箱是否存在
	 * @param user
	 * @return
	 */
	public boolean checkEmail(String email) {
		Result result = hbaseDao.getResultByRow(Constants.TABLE_EMAILUSER, email);
		return result.isEmpty();
	}
	
	/**
	 * 检测姓名是否存在
	 * @param user
	 * @return
	 */
	public boolean checkName(String name) {
		Result result = hbaseDao.getResultByRow(Constants.TABLE_USERId, name);
		return result.isEmpty();
	}
	
	/**
	 * 根据用户名称找到用户ID
	 * @param user
	 * @return
	 */
	public long getIdByName(String name) {
		long id = 0l;
		Result result = hbaseDao.getResultByRow(Constants.TABLE_USERId, name);
		if(!result.isEmpty()) {
			id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERId_ID), Bytes.toBytes(Constants.COLUMN_USERId_ID)));
		}
		return id;
	}
	
	/**
	 * 根据用户ID找到密码、用户名或邮箱
	 * @param id
	 * @return
	 */
	public User getById(long id) {
		User user = null;
		Result result = hbaseDao.getResultByRow(Constants.TABLE_IDUSER, id);
		if(!result.isEmpty()) {
			user = new User();
			user.setId(id);
			user.setPwd(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_IDUSER_USER), Bytes.toBytes(Constants.COLUMN_IDUSER_PWDNAMEEMAIL[0]))));
			user.setName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_IDUSER_USER), Bytes.toBytes(Constants.COLUMN_IDUSER_PWDNAMEEMAIL[1]))));
			user.setEmail(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_IDUSER_USER), Bytes.toBytes(Constants.COLUMN_IDUSER_PWDNAMEEMAIL[2]))));
		}
		return user;
	}
	
}
