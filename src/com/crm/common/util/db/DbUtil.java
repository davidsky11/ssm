package com.crm.common.util.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.crm.common.util.io.FileUtil;

/**
 * @ClassName:		DbUtil
 * @Description:	数据库备份还原
 * @Author:    		kevin
 * @CreateDte:		2016年7月17日 下午5:56:10
 */
public class DbUtil {

	/**
	 * 备份数据库
	 */
	public static void backup(final String dbBinPath, final String dbUsername, final String dbPassword, final String dbHost, final String dbPort, final String dbName, final String realPath) {
		String cmd = dbBinPath + "/mysqldump -u" + dbUsername + " -p" + dbPassword + "  --default-character-set=utf8 -h" + dbHost + " -P" + dbPort + " " + dbName; // 设置导出编码为utf8。这里必须是utf8
		// + " >" + "\"" + backPath + "\"" 直接写入文件备份
		try {
			FileUtil.createParentDirs(realPath);
			// 调用 mysql 的 cmd:
			Runtime rt = Runtime.getRuntime();
			Process child = rt.exec(cmd);
			String inStr;
			StringBuffer sb = new StringBuffer("");
			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			BufferedReader br = new BufferedReader(new InputStreamReader(child.getInputStream(), "UTF-8")); // 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			sb.append("/*数据库备份完成*/");
			// 要用来做导入用的sql目标文件：
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(realPath), "utf8");
			writer.write(sb.toString());
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 关闭输入输出流
			br.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 还原数据库
	 */
	public static void reback(final String dbBinPath, final String dbUsername, final String dbPassword, final String dbHost, final String dbPort, final String dbName, final String realPath) {
		String cmd = dbBinPath + "/mysql -u" + dbUsername + " -p" + dbPassword + "  --default-character-set=utf8 -h" + dbHost + " -P" + dbPort + " " + dbName; // 设置导出编码为utf8。这里必须是utf8
		try {
			Runtime rt = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			Process child = rt.exec(cmd);
			String inStr;
			StringBuffer sb = new StringBuffer("");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(realPath), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			OutputStreamWriter writer = new OutputStreamWriter(child.getOutputStream(), "utf8"); // 控制台的输入信息作为输出流
			writer.write(sb.toString());
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 关闭输入输出流
			br.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
