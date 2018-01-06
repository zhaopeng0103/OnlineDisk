package com.netpan.util;

public class Constants {
	public static final String currentUserSessionKey="currentUser";
	
	public static final String[] sufix = {"csv","txt","doc","docx","xls","xlsx","ppt","pptx"};
	
	public static final String TABLE_IDUSER = "id_user";
	public static final String FAMILY_IDUSER_USER = "user";
	public static final String[] COLUMN_IDUSER_PWDNAMEEMAIL = { "pwd", "name", "email" };
	
	public static final String TABLE_USERId = "user_id";
	public static final String FAMILY_USERId_ID = "id";
	public static final String COLUMN_USERId_ID = "id";
	
	public static final String TABLE_EMAILUSER = "email_user";
	public static final String FAMILY_EMAILUSER_USER = "user";
	public static final String COLUMN_EMAILUSER_USERID = "userid";
	
	public static final String TABLE_GID = "gid_disk";
	public static final String ROWKEY_GID_GID = "gid";
	public static final String FAMILY_GID_GID = "gid";
	public static final String COLUMN_GID_GID = "gid";
	public static final String ROWKEY_GID_SHAREID = "shareid";
	public static final String FAMILY_GID_SHAREID = "gid";
	public static final String COLUMN_GID_SHAREID = "shareid";
	public static final String ROWKEY_GID_FILEID = "fileid";
	public static final String FAMILY_GID_FILEID = "gid";
	public static final String COLUMN_GID_FILEID = "fileid";
	
	public static final String TABLE_FILE = "file";
	public static final String FAMILY_FILE_FILE = "file";
	public static final String[] COLUMN_FILE_ORIGINALNAMEANDETC = { "originalName", "name", "isFile", "isDir", "length", "path", "originalPath", "viewflag", "date" };
	
	public static final String TABLE_USERFILE = "user_file";
	public static final String FAMILY_USERFILE_FILE = "file";
	public static final String COLUMN_USERFILE_FILEID = "fileId"; // user_file中行健：1_0_2 => 1 => originalName,name....
	
	public static final String TABLE_FOLLOW = "follow";
	public static final String FAMILY_FOLLOW_NAME = "name";
	
	public static final String TABLE_FOLLOWED = "followed";
	public static final String FAMILY_FOLLOWED_USERID = "userid";
	public static final String COLUMN_FOLLOWED_USERID = "userid";
	
	public static final String TABLE_SHARE = "share";
	public static final String FAMILY_SHARE_CONTENT = "content";
	public static final String[] COLUMN_SHARE_DIRTYPEPATHANDETC = { "path", "originalPath", "type", "name", "sharetime", "shareedUserId", "shareedUserName", "shareid", "shareUserName"};
	
	public static final String TABLE_SHAREED = "shareed";
	public static final String FAMILY_SHAREED_SHAREID = "shareid";
	public static final String COLUMN_SHAREED_SHAREID = "shareid";
	
}