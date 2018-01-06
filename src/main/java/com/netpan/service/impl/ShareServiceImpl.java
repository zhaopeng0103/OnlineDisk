package com.netpan.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netpan.dao.ShareDao;
import com.netpan.dao.UserDao;
import com.netpan.entity.Share;
import com.netpan.entity.User;
import com.netpan.service.ShareService;
import com.netpan.util.Constants;

@Service("shareService")
public class ShareServiceImpl implements ShareService {
	@Autowired
	private ShareDao shareDao;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 添加分享
	 * @param user
	 * @param share
	 * @param shareedUserId
	 */
	@Override
	public void addShare(User user, Share share, String shareedUserName) {
		long shareedUserId = userDao.getIdByName(shareedUserName);
		share.setShareedUserId(shareedUserId);
		share.setShareedUserName(shareedUserName);
		share.setShareUserName(user.getName());
		long rowKey = shareDao.addShare(user, share);
		shareDao.addShareed(user, shareedUserId, rowKey);
	}
	
	/**
	 * 获得我的分享
	 * @param user
	 * @return
	 */
	@Override
	public List<Share> getMyShareList(User user) {
		List<Share> list = new ArrayList<Share>();
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getId() + "_"));
		ResultScanner resultScanner = shareDao.getResultScannerByShare(filter);
		Iterator<Result> iter = resultScanner.iterator();
		while(iter.hasNext()) {
			Result result = iter.next();
			if(!result.isEmpty()) {
				Share share = new Share();
				share.setPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[0]))));
				share.setOriginalPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[1]))));
				share.setType(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[2]))));
				share.setName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[3]))));
				share.setSharetime(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[4]))));
				share.setShareedUserId(Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[5]))));
				share.setShareedUserName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[6]))));
				share.setShareid(Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[7]))));
				share.setShareUserName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHARE_CONTENT), Bytes.toBytes(Constants.COLUMN_SHARE_DIRTYPEPATHANDETC[8]))));
				list.add(share);
			}
		}
		return list;
	}
	
	/**
	 * 获得我收到的分享
	 * @param user
	 * @return
	 */
	@Override
	public List<Share> getShareedList(User user) {
		List<Share> list = new ArrayList<Share>();
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getId() + "_"));
		ResultScanner resultScanner = shareDao.getResultScannerByShareed(filter);
		Iterator<Result> iter = resultScanner.iterator();
		while(iter.hasNext()) {
			Result result = iter.next();
			if(!result.isEmpty()) {
				String rowkey = Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_SHAREED_SHAREID), Bytes.toBytes(Constants.COLUMN_SHAREED_SHAREID)));
				if (!rowkey.isEmpty()) {
					list.add(shareDao.getShareInfoByRowkey(rowkey));
				}
			}
		}
		return list;
	}
	
	/**
	 * 删除我的分享
	 * @param user
	 * @param shareid
	 */
	@Override
	public void deleteMyShareList(User user, long shareid) {
		Share share = shareDao.getShareInfoByRowkey(user.getId() + "_" + shareid);
		shareDao.deleteShare(user, shareid);
		shareDao.deleteShareed(user, share.getShareedUserId(), shareid);
	}
	
}
