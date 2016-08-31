package com.crm.wechat.utils;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName GenerateSequenceUtil.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月14日 上午1:04:08
 * @Version V1.0
 */
public class GenerateSequenceUtil {

	/** .log */
	private final static Logger logger = LoggerFactory.getLogger(GenerateSequenceUtil.class);

	/** The FieldPosition. */
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

	/** This Format for format the data to special format. */
	private final static Format dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	/** This Format for format the number to special format. */
	private final static NumberFormat numberFormat = new DecimalFormat("0000");

	/** This int is the sequence number ,the default value is 0. */
	private static int seq = 0;

	private static final int MAX = 9999;

	/**
	 * 时间格式生成序列
	 * 
	 * @return String
	 */
	public static synchronized String generateSequenceNo() {
		Calendar rightNow = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
		numberFormat.format(seq, sb, HELPER_POSITION);
		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}
		logger.info("THE SQUENCE IS :" + sb.toString());
		return sb.toString();
	}
}
