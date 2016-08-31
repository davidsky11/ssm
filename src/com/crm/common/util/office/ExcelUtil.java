package com.crm.common.util.office;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.ReaderConfig;
import net.sf.jxls.reader.XLSReadMessage;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.xml.sax.SAXException;

import com.crm.common.util.io.FileUtil;

/**
 * 工具类-》办公文档工具类-》Excel工具类
 */
public final class ExcelUtil {
	public static final int RANDOMNUMSIZE = 3; // 随机数字长度

	private ExcelUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * 导出
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws ParsePropertyException 
	 */
	public static void export(final String templateFileName, final String destFileNameWithoutSuffix, final Map<String, Object> beans) throws ParsePropertyException, InvalidFormatException, IOException {
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		transformer.transformXLS(templateFileName, beans, destFileNameWithoutSuffix + "." + FileUtil.getSuffix(templateFileName));
	}

	/**
	 * 导出并下载
	 */
	public static void exportAndDownload(final String templateFileName,
			final String targetNameWithoutSuffix, final Map<String, Object> beans,
			final HttpServletResponse response) {
		// 清空response
		response.reset();
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小
		// response.setContentLength((int) workbook.);
		try {
			String targetName2 = URLEncoder.encode(targetNameWithoutSuffix + "." + FileUtil.getSuffix(templateFileName), "UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "No-cache");
			response.setDateHeader("Expires", 0);
			response.addHeader("Content-Disposition", "attachment;filename="
					+ targetName2);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		OutputStream os = null;
		try {
			Workbook workbook = transformer.transformXLS(new FileInputStream(
					templateFileName), beans);
			os = response.getOutputStream();
			workbook.write(os);
		} catch (ParsePropertyException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 导入
	 */
	@SuppressWarnings("unchecked")
	public static List<XLSReadMessage> importExcel(final String templateFileName, final InputStream inputStream, final Map<String, Object> beans, final boolean skipErrors, final HttpServletRequest request) throws IOException, SAXException, InvalidFormatException {
		InputStream inputXML = new FileInputStream(templateFileName);
        XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
        ReaderConfig.getInstance().setSkipErrors(skipErrors);
        InputStream inputXLS = new BufferedInputStream(inputStream);
        XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
        inputXLS.close();
        inputXML.close();
        return readStatus.getReadMessages();
	}
}
