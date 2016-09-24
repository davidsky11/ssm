package com.crm.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.crm.common.util.math.RandomUtil;
import com.crm.util.common.Const;  

/** 
 * @ClassName	UploadServlet.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月24日 下午11:52:03
 * @Version 	V1.0    
 */
@WebServlet("/Upload")  
public class UploadServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;  
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");  // 日期格式化工具
	
	// 上传文件的保存路径
	private String configPath = Const.UPLOAD_PATH;
	// 临时文件路径
	private String dirTemp = "temp/";
	  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        doPost(request, response);  
    }  
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String ret_fileName = null;  // 返回前端已修改的图片名称
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 文件保存目录路径
        String savePath = configPath;
     // 临时文件目录
        String tempPath = this.getServletContext().getRealPath("/") + dirTemp;
 
        // 创建文件夹
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
 
        // 创建临时文件夹
        File dirTempFile = new File(tempPath);
        if (!dirTempFile.exists()) {
            dirTempFile.mkdirs();
        }
 
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(20 * 1024 * 1024); // 设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
        factory.setRepository(new File(tempPath));  // 设定存储临时文件的目录。
 
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        
        String time = sdf.format(new Date());
 
        try {
            List<?> items = upload.parseRequest(request);
            Iterator<?> itr = items.iterator();
 
            while (itr.hasNext()) {
                FileItem item   = (FileItem) itr.next();
                String fileName = item.getName();
                if(fileName!=null){
                    String endstr = fileName.substring(fileName.indexOf("."), fileName.length());
                    fileName      = time.concat(RandomUtil.generateMixString(3)).concat(endstr);
                    ret_fileName  = fileName;
                }
                if (!item.isFormField()) {
 
                    try {
                        File uploadedFile = new File(savePath,fileName);
 
                        OutputStream os = new FileOutputStream(uploadedFile);
                        InputStream is = item.getInputStream();
                        byte buf[] = new byte[1024];// 可以修改 1024 以提高读取速度
                        int length = 0;
                        while ((length = is.read(buf)) > 0) {
                            os.write(buf, 0, length);
                        }
                        // 关闭流
                        os.flush();
                        os.close();
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
 
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //将已修改的图片名称返回前端
        out.print(ret_fileName);
        out.flush();
        out.close();
    }  
    
}
 