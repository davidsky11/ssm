package com.crm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @ClassName JspToHtml.java
 * @Description jsp转换成html 将动态网页转换成静态网页
 * @Author kevin
 * @CreateTime 2016年9月24日 下午9:22:57
 * @Version V1.0
 */
public class HtmlUtil {

	public static String title = "标题测试";
	public static String context = "标题测试";
	public static String img = "upload/img/bj.png";

	/**
	 * 根据本地模板生成静态页面
	 * 
	 * @param JspFile
	 *            jsp路经
	 * @param HtmlFile
	 *            html路经
	 * @return
	 */
	public static boolean JspToHtmlFile(String filePath, String HtmlFile) {
		String str = "";
		long beginDate = (new Date()).getTime();
		try {
			String tempStr = "";
			FileInputStream is = new FileInputStream(filePath);// 读取模块文件
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((tempStr = br.readLine()) != null)
				str = str + tempStr;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {

			str = str.replaceAll("###title###", title);
			str = str.replaceAll("###content###", context);
			str = str.replaceAll("###img###", img);// 替换掉模块中相应的地方

			File f = new File(HtmlFile);
			BufferedWriter o = new BufferedWriter(new FileWriter(f));
			o.write(str);
			o.close();
			System.out.println("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据url生成静态页面
	 *
	 * @param u
	 *            动态文件路经 如：http://www.163.com/x.jsp
	 * 
	 * @param path
	 *            文件存放路经如：x:\\abc\bbb.html
	 * @return
	 */
	public static boolean JspToHtmlByURL(String u, String path) {
		// 从utl中读取html存为str
		String str = "";
		try {
			URL url = new URL(u);
			URLConnection uc = url.openConnection();
			InputStream is = uc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while (br.ready()) {
				str += br.readLine() + "\n";

			}
			is.close();
			// 写入文件
			File f = new File(path);
			BufferedWriter o = new BufferedWriter(new FileWriter(f));
			o.write(str);
			o.close();
			str = "";
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据url生成静态页面
	 *
	 * @param url
	 *            动态文件路经 如：http://www.163.com/x.jsp
	 * 
	 * @return d
	 */
	public static StringBuffer getHtmlTextByURL(String url) {
		// 从utl中读取html存为str
		StringBuffer sb = new StringBuffer();
		try {
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while (br.ready()) {
				sb.append(br.readLine() + "\n");
			}
			is.close();
			return sb;
		} catch (Exception e) {
			e.printStackTrace();
			return sb;
		}
	}
	
	public static String getClassesPath() {
		//file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/   
		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
		//path=path.replace('/', '\\'); // 将/换成\  
		path=path.replace("file:", ""); //去掉file:  
		path=path.substring(1);//去掉第一个\,如 \D:\JavaWeb...  
		
		return path;
				
	}
	
	public static String getWebInfoPath() {
		//file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/   
		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
		//path=path.replace('/', '\\'); // 将/换成\  
        path=path.replace("file:", ""); //去掉file: 
        
        path=path.replace("classes\\", "");
        path=path.replace("classes/", ""); //去掉class\
        path=path.substring(1);//去掉第一个\,如 \D:\JavaWeb...  
        
        return path;
	}

	/**
	 * 测试main 函数
	 *
	 * @param arg
	 */
	public static void main(String[] arg) {
		long begin = System.currentTimeMillis();
		// 循环生成20个html文件
		for (int k = 0; k < 1; k++) {
			String url = "E:/WorkSpaces\\WebSpaces\\ssm-crm-new\\WebRoot\\template\\info.jsp";// 模板文件地址
			String savepath = "E:\\WorkSpaces\\WebSpaces\\ssm-crm-new\\WebRoot\\WEB-INF\\static\\info\\" + k + ".html";// 生成文件地址
			//String url = "E:\WorkSpaces\WebSpaces\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ssm-crm-new\WEB-INF\classes\template\info.jsp";
			//String savepath = "E:\WorkSpaces\WebSpaces\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ssm-crm-new\WEB-INF\static\info\123.html";
			
//			JspToHtmlFile(Const.HTML_TEMPLATE_FILE, Const.HTML_OUTPUT_PATH + k + ".html");
			//JspToHtmlFile(getWebInfoPath() + "template/info.jsp", "D://" + k + ".html");
		}
		System.out.println("用时:" + (System.currentTimeMillis() - begin) + "ms");
		
		System.out.println(getClassesPath());
		System.out.println(getWebInfoPath());
	}
}
