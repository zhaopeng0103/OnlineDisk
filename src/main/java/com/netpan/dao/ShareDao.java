package com.netpan.dao;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.netpan.dao.basedao.HbaseDao;
import com.netpan.entity.Share;
import com.netpan.entity.User;
import com.netpan.util.Constants;

@Repository("shareDao")
public class ShareDao {
	@Autowired
	private HbaseDao hbaseDao;
	
	/**
	 * 添加到share表
	 * @param user
	 * @param share
	 */
	public long addShare(User user, Share share) {
		long rowKey = hbaseDao.incrCounter(Constants.TABLE_GID, Constants.ROWKEY_GID_SHAREID, Constants.FAMILY_GID_SHAREID, Constants.COLUMN_GID_SHAREID, 1);
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[0], share.getPath());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[1], share.getOriginalPath());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[2], share.getType());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[3], share.getName());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[4], share.getSharetime());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[5], share.getShareedUserId());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[6], share.getShareedUserName());
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[7], rowKey);
		hbaseDao.updateOneData(Constants.TABLE_SHARE, user.getId() + "_" + rowKey, Constants.FAMILY_SHARE_CONTENT, Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[8], share.getShareUserName());
		return rowKey;
	}
	
	/**
	 * 添加到shareed表
	 * @param user
	 * @param share
	 * @param shareedUserId
	 */
	public void addShareed(User user, long shareedUserId, long shareid) {
		hbaseDao.updateOneData(Constants.TABLE_SHAREED, shareedUserId + "_" + user.getId() + "_" + shareid, Constants.FAMILY_SHAREED_SHAREID, Constants.COLUMN_SHAREED_SHAREID, user.getId() + "_" + shareid);
	}
	
	/**
	 * 根据行健获得share信息
	 * @param rowkey
	 * @return
	 */
	public Share getShareInfoByRowkey(String rowkey) {
		Share share = null;
		Result result = hbaseDao.getResultByRow(Constants.TABLE_SHARE, rowkey);
		if(!result.isEmpty()) {
			share = new Share();
			share.setPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[0]))));
			share.setOriginalPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[1]))));
			share.setType(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[2]))));
			share.setName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[3]))));
			share.setSharetime(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[4]))));
			share.setShareedUserId(Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[5]))));
			share.setShareedUserName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[6]))));
			share.setShareid(Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[7]))));
			share.setShareUserName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[8]))));
		}
		return share;
	}
	
	/**
	 * 以一定规则扫描share表
	 * @param filter
	 * @return
	 */
	public ResultScanner getResultScannerByShare(Filter filter) {
		return hbaseDao.getResultScannerByFilter(Constants.TABLE_SHARE, filter);
	}
	
	/**
	 * 以一定规则扫描shareed表
	 * @param filter
	 * @return
	 */
	public ResultScanner getResultScannerByShareed(Filter filter) {
		return hbaseDao.getResultScannerByFilter(Constants.TABLE_SHAREED, filter);
	}
	
	/**
	 * 删除share表中的信息
	 * @param user
	 * @param share
	 */
	public void deleteShare(User user, long shareid) {
		hbaseDao.deleteDataByRow(Constants.TABLE_SHARE, user.getId() + "_" + shareid);
	}
	
	/**
	 * 删除shareed表中的信息
	 * @param user
	 * @param share
	 * @param shareedUserId
	 */
	public void deleteShareed(User user, long shareedUserId, long shareid) {
		hbaseDao.deleteDataByRow(Constants.TABLE_SHAREED, shareedUserId + "_" + user.getId() + "_" + shareid);
	}
}
