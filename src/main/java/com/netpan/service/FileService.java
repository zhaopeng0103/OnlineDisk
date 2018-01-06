package com.netpan.service;

import java.io.InputStream;
import java.util.List;
import com.netpan.entity.File;
import com.netpan.entity.User;
import com.netpan.entity.Node;

public interface FileService {
	/**
	 * 获得文件列表，查看文件或目录列表
	 * @param user
	 * @param parentid
	 * @return
	 */
	public List<File> getFileList(User user, long parentid);
	
	/**
	 * 上传文件
	 * @param inputStream
	 * @param file
	 * @param user
	 * @param parentid
	 */
	public void uploadFile(InputStream inputStream, File file, User user, long parentid);
	
	/**
	 * 创建文件夹
	 * @param file
	 * @param user
	 * @param parentid
	 */
	public void makeFolder(File file, User user, long parentid);
	
	/**
	 * 获得面包屑导航
	 * @param dir
	 * @return
	 */
	public List<File> getBreadcrumb(String dir);
	
	/**
	 * 在删除文件或目录时根据id获得文件或目录信息
	 * @param id
	 * @return
	 */
	public File getFileInfoById(long id);
	
	/**
	 * 递归删除file表和user_file表的文件信息，删除文件或目录时使用
	 * @param user
	 * @param file
	 * @param parentid
	 */
	public void deleteInfoRecursion(User user, File file, long parentid);
	
	/**
	 * 删除hdfs中的文件，删除文件或目录时使用
	 * @param user
	 * @param file
	 */
	public void deleteHdfs(User user, File file);
	
	/**
	 * 重命名文件或目录
	 * @param file
	 * @param newname
	 */
	public void rename(File file, String newname);
	
	/**
	 * 获得某一个父目录下的所有子目录，用于复制或移动时显示面包树
	 * @param user
	 * @param parentid
	 * @return
	 */
	public List<Node> getTreeFile(User user, long parentid);
	
	/**
	 * 下载文件
	 * @param user
	 * @param path
	 * @param local
	 * @return
	 */
	public boolean downloadFile(User user, File file, String local);
	
	/**
	 * 递归复制file表和user_file表的文件信息，复制文件或目录时使用
	 * @param user
	 * @param sourceFile
	 * @param dstid
	 */
	public void copyInfoRecursion(User user, File sourceFile, long destid, String destPath);
	
	/**
	 * 复制或者移动hdfs中的文件，复制与移动文件或目录时使用
	 * @param sourcePath
	 * @param destPath
	 * @param flag
	 */
	public void copyOrMoveHdfs(User user, File sourceFile, File destFile, boolean flag);
}
