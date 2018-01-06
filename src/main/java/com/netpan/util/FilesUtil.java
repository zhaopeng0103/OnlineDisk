package com.netpan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class FilesUtil {
	/**
	 * 格式化文件大小
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获得文件名前缀
	 * @param fileName
	 * @return
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}
	
	/**
	 * 获得文件名后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileSufix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
	}
	
	/**
	 * 文件之间转换格式，不同格式文件之间相互拷贝
	 * @param inputFile
	 * @param outputFile
	 */
	public static void copyFile(String inputFile, String outputFile) {
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(inputFile));
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile));
			int temp = 0;
			try {
				while ((temp = fileInputStream.read()) != -1) {
					fileOutputStream.write(temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fileInputStream.close();
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
