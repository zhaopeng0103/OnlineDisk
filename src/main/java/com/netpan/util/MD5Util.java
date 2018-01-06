package com.netpan.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	private static final String staticSalt = "ffms";

	public static void main(String[] args) {
		System.out.println("xx=" + MD5Util.encodePwd("123456"));
		System.out.println("yy=" + MD5Util.isPwdRight("123456", "cb323f94f7575f0c5cee65521507e1da"));
	}

	/**
	 * 根据用户id和用户输入的原始密码，进行MD5加密
	 * @param userId
	 * @param userPwd
	 * @return 加密后的字符串
	 */
	public static String encodePwd(String userPwd) {
		return DigestUtils.md5Hex(userPwd + staticSalt);
	}

	/**
	 * 判断用户输入的密码是否正确
	 * @param userId：用户id
	 * @param userPwd：当前输入的密码
	 * @param dbPwd：数据库中存储的密码
	 * @return true:输入正确 false：输入错误
	 */
	public static boolean isPwdRight(String userPwd, String dbPwd) {
		boolean rs = false;
		if (encodePwd(userPwd).equals(dbPwd)) {
			rs = true;
		}
		return rs;
	}

}
