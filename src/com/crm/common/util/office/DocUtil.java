package com.crm.common.util.office;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.crm.common.util.math.RandomUtil;

/**
 * 工具类-》办公文档工具类-》doc处理工具类
 */
public final class DocUtil {
	
	public static final int RANDOMNUMSIZE = 3; // 随机数字长度

	private DocUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * 导出并下载
	 */
	public static void exportAndDownload(final String templateFileName, final String destFileName, final Map<String, String> map, final HttpServletResponse response) {
		try {
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(templateFileName));
			Iterator<XWPFTable> it = document.getTablesIterator();
			while (it.hasNext()) {
				XWPFTable table = (XWPFTable) it.next();
				int rcount = table.getNumberOfRows();
				for (int i = 0; i < rcount; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						for (Entry<String, String> e : map.entrySet()) {
							if (cell.getText().equals(e.getKey())) {
								cell.removeParagraph(0);
								cell.setText(e.getValue());
							}
						}
					}
				}
			}
			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destFileName);
			document.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出并下载
	 */
	public static String exportAndDownload(final Map<String, String> map, final String templeteName, final HttpServletRequest request, final HttpServletResponse resp) {
		/*String templateFileName = request.getServletContext().getRealPath("/") + "/files/word/templates/" + templeteName + ".doc";
		String tempFileName = "/files/word/build/" + templeteName + "_" + RandomUtil.generateNumberString(RANDOMNUMSIZE) + ".doc";
		String destFileName = request.getServletContext().getRealPath("/") + tempFileName;
		exportAndDownload(templateFileName, destFileName, map, resp);
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		return baseUrl + tempFileName;*/
		
		return "";
	}

	// public static void main(String[] args) throws Exception {
	// HashMap map = new HashMap();
	// map.put("${name}", "王五");
	// map.put("${tel}", "8886666");
	// }

}
