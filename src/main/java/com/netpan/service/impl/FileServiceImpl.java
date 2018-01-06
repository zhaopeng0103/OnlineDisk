package com.netpan.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netpan.dao.FileDao;
import com.netpan.entity.User;
import com.netpan.entity.File;
import com.netpan.entity.Node;
import com.netpan.service.FileService;
import com.netpan.util.Constants;
import com.netpan.util.DateUtil;

@Service("fileService")
public class FileServiceImpl implements FileService{
	@Autowired
	private FileDao fileDao;
	
	/**
	 * 获得文件列表，查看文件或目录列表
	 * @param user
	 * @param parentid
	 * @return
	 */
	@Override
	public List<File> getFileList(User user, long parentid) {
		List<File> list = new ArrayList<File>();
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getId() + "_" + parentid + "_"));
		ResultScanner resultScanner = fileDao.getResultScannerByUserFile(filter);
		Iterator<Result> iter = resultScanner.iterator();
		while(iter.hasNext()) {
			Result result = iter.next();
			if(!result.isEmpty()) {
				long id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
				if (id > 0) {
					list.add(fileDao.getById(id));
				}
			}
		}
		return list;
	}
	
	/**
	 * 上传文件
	 * @param inputStream
	 * @param file
	 * @param user
	 * @param parentid
	 */
	@Override
	public void uploadFile(InputStream inputStream, File file, User user, long parentid) {
		fileDao.upload(inputStream, file, user);
		long rowkey = fileDao.addFileInfo(file);
		fileDao.addUserFile(user, parentid, rowkey);
	}
	
	/**
	 * 创建文件夹
	 * @param file
	 * @param user
	 * @param parentid
	 */
	@Override
	public void makeFolder(File file, User user, long parentid) {
		fileDao.mkDir(file, user);
		long rowkey = fileDao.addFileInfo(file);
		fileDao.addUserFile(user, parentid, rowkey);
	}
	
	/**
	 * 获得面包屑导航
	 * @param dir
	 * @return
	 */
	@Override
	public List<File> getBreadcrumb(String dir) {
		List<File> breadcrumblist = new ArrayList<File>();
		String[] breadcrumbArray = dir.split("/");
		
		File file = new File();
		file.setId(0);
		file.setOriginalName("根目录");
		file.setPath("/");
		file.setOriginalPath("/");
		breadcrumblist.add(file);
		
		if (breadcrumbArray.length > 0) {
			for (int i = 1; i < breadcrumbArray.length; i++) {
				String path = "";
				for (int j = 1; j <= i; j++) {
					path += "/" + breadcrumbArray[j];
				}
				File filterFile = getResultByPath(path);
				if (filterFile != null) {
					breadcrumblist.add(filterFile);
				}
			}
		}
		return breadcrumblist;
	}
	
	/**
	 * 获得匹配相同路径的file信息，用于补充面包屑导航
	 * @param path
	 * @return
	 */
	private File getResultByPath(String path) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[5]), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(path)));
		((SingleColumnValueFilter) filter).setFilterIfMissing(true);
		ResultScanner resultScanner = fileDao.getResultScannerByFile(filter);
		Iterator<Result> iter = resultScanner.iterator();
		File file = null;
		while(iter.hasNext()) {
			Result result = iter.next();
			if(Bytes.toBoolean(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[3])))) {
				file = new File();
				file.setId(Bytes.toLong(result.getRow()));
				file.setOriginalName(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[0]))));
				file.setPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[5]))));
				file.setOriginalPath(Bytes.toString(result.getValue(Bytes.toBytes(Constants.FAMILY_FILE_FILE), Bytes.toBytes(Constants.COLUMN_FILE_ORIGINALNAMEANDETC[6]))));
			}
		}
		return file;
	}
	
	/**
	 * 在删除文件或目录时根据id获得文件或目录信息
	 * @param id
	 * @return
	 */
	@Override
	public File getFileInfoById(long id) {
		return fileDao.getById(id);
	}
	
	/**
	 * 递归删除file表和user_file表的文件信息，删除文件或目录时使用
	 * @param user
	 * @param file
	 * @param parentid
	 */
	@Override
	public void deleteInfoRecursion(User user, File file, long parentid) {
		if(file.getType().equals("D")) {
			Filter filter = new PrefixFilter(Bytes.toBytes(user.getId() + "_" + file.getId() + "_"));
			ResultScanner resultScanner = fileDao.getResultScannerByUserFile(filter);
			Iterator<Result> iter = resultScanner.iterator();
			while(iter.hasNext()) {
				Result result = iter.next();
				if(!result.isEmpty()) {
					long id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
					if (id > 0) {
						File subFile = fileDao.getById(id);
						if(subFile.getType().equals("D")) {
							deleteInfoRecursion(user, subFile, file.getId());
						}
						fileDao.deleteUserFile(user, subFile, file.getId());
						fileDao.deleteFileInfo(subFile);
					}
				}
			}
		}
		fileDao.deleteUserFile(user, file, parentid);
		fileDao.deleteFileInfo(file);
	}
	
	/**
	 * 删除hdfs中的文件，删除文件或目录时使用
	 * @param user
	 * @param file
	 */
	@Override
	public void deleteHdfs(User user, File file) {
		fileDao.deleteFileOrFolder(file, user);
	}
	
	/**
	 * 重命名文件或目录
	 * @param file
	 * @param newname
	 */
	@Override
	public void rename(File file, String newname) {
		fileDao.renameFileOrFolderInfo(file, newname);
	}
	
	/**
	 * 获得某一个父目录下的所有子目录，用于复制或移动时显示面包树
	 * @param user
	 * @param parentid
	 * @return
	 */
	@Override
	public List<Node> getTreeFile(User user, long parentid){
		List<Node> nodeList = new ArrayList<Node>();
		Filter filter = new PrefixFilter(Bytes.toBytes(user.getId() + "_" + parentid + "_"));
		ResultScanner resultScanner = fileDao.getResultScannerByUserFile(filter);
		Iterator<Result> iter = resultScanner.iterator();
		while(iter.hasNext()) {
			Result result = iter.next();
			if(!result.isEmpty()) {
				long id = Bytes.toLong(result.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
				if (id > 0) {
					File file = fileDao.getById(id);
					if(file!=null&&file.isDir()) {
						Node node = new Node();
						node.setId(file.getId());
						node.setText(file.getOriginalName());
						nodeList.add(node);
					}
				}
			}
		}
		return nodeList;
	}
	
	/**
	 * 下载文件
	 * @param user
	 * @param path
	 * @param local
	 * @return
	 */
	@Override
	public boolean downloadFile(User user, File file, String local) {
		return fileDao.downloadFile(user, file, local);
	}
	
	/**
	 * 递归复制file表和user_file表的文件信息，复制文件或目录时使用
	 * @param user
	 * @param sourceFile
	 * @param dstid
	 */
	@Override
	public void copyInfoRecursion(User user, File sourceFile, long destid, String destPath) {
		if(destPath.equals("/")) {
			sourceFile.setPath(destPath + sourceFile.getName());
		}else {
			sourceFile.setPath(destPath + "/" + sourceFile.getName());
		}
		sourceFile.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
		long rowkey = fileDao.addFileInfo(sourceFile);
		fileDao.addUserFile(user, destid, rowkey);
		if(sourceFile.getType().equals("D")) {
			Filter sourceFilter = new PrefixFilter(Bytes.toBytes(user.getId() + "_" + sourceFile.getId() + "_"));
			ResultScanner sourceResultScanner = fileDao.getResultScannerByUserFile(sourceFilter);
			Iterator<Result> sourceIter = sourceResultScanner.iterator();
			while(sourceIter.hasNext()) {
				Result sourceResult = sourceIter.next();
				if(!sourceResult.isEmpty()) {
					long id = Bytes.toLong(sourceResult.getValue(Bytes.toBytes(Constants.FAMILY_USERFILE_FILE), Bytes.toBytes(Constants.COLUMN_USERFILE_FILEID)));
					if (id > 0) {
						File subsourceFile = fileDao.getById(id);
						if(sourceFile.getPath().equals("/")) {
							subsourceFile.setPath(sourceFile.getPath() + subsourceFile.getName());
						}else {
							subsourceFile.setPath(sourceFile.getPath() + "/" + subsourceFile.getName());
						}
						subsourceFile.setDate(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
						long subrowkey = fileDao.addFileInfo(subsourceFile);
						fileDao.addUserFile(user, rowkey, subrowkey);
						if(subsourceFile.getType().equals("D")) {
							copyInfoRecursion(user, subsourceFile, rowkey, sourceFile.getPath());
						}
					}
				}
			}
		}
	}
	
	/**
	 * 复制或者移动hdfs中的文件，复制与移动文件或目录时使用
	 * @param sourcePath
	 * @param destPath
	 * @param flag
	 */
	@Override
	public void copyOrMoveHdfs(User user, File sourceFile, File destFile, boolean flag) {
		fileDao.copyOrMoveFile(user, sourceFile, destFile, flag);
	}
	
}
