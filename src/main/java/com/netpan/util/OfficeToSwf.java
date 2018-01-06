package com.netpan.util;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class OfficeToSwf {
	
	/**
	 * 利用openoffice普通文件转换为pdf文件
	 * @param generalFile
	 * @param pdfFile
	 */
	public static void convertToPdf(String generalFile, String pdfFile) {
		try {
			File sourceFile = new File(generalFile);
			File targetFile = new File(pdfFile);
			
			if(sourceFile.exists()) {
				return;
			}
			if (targetFile.exists()) {
				return;
			}
			if(FilesUtil.getFileSufix(generalFile).equals("swf")) {
				return;
			}
			
			// 启动OpenOffice的服务
			String command = SiteUtil.readUrl("openoffice") + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
			Process pro = Runtime.getRuntime().exec(command);
			// 连接到运行在8100端口的openoffice的一个实例
			OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
			connection.connect();
			// 进行文档转换
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(sourceFile, targetFile);
			System.out.println("转换成功：" + targetFile.getAbsolutePath());
			// 关闭openoffice连接
			connection.disconnect();
			// 销毁OpenOffice的服务进程
			pro.destroy();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 利用pdf2swf.exe将pdf文件转换为swf文件
	 * @param pdfFile
	 * @param swfFile
	 */
	public static void pdfConvertSwf(String pdfFile, String swfFile) {
		if (!pdfFile.endsWith(".pdf")) {
			return;
		}
		if (!new File(pdfFile).exists()) {
			return;
		}
		if (new File(swfFile).exists()) {
			return;
		}
		String command = SiteUtil.readUrl("pdf2swf") + " \"" + pdfFile + "\" -o " + swfFile + " -T 9 -f";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		convertToPdf("F:\\hadoop\\reverse.txt", "F:\\hadoop\\reverse.pdf");
		pdfConvertSwf("F:\\hadoop\\reverse.pdf", "F:\\hadoop\\reverse.swf");
	}
}
