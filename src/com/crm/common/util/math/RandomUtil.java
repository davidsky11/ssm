package com.crm.common.util.math;

import jodd.util.MathUtil;
import jodd.util.RandomString;

/**
 * 工具类-》基础工具类-》 随机字符串工具
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class RandomUtil {

	private RandomUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 返回一个定长的随机字符串(只包含大小写字母、数字)
	 */
	public static String generateMixString(final int length) {
		return RandomString.getInstance().randomAlphaNumeric(length);
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 */
	public static String generateLetterString(final int length) {
		return RandomString.getInstance().randomAlpha(length);
	}

	/**
	 * 返回一个定长的随机纯数字字符串(只包含数字)
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 返回一个定长的随机纯数字字符串(只包含数字)
	 */
	public static String generateNumberString(final int length) {
		return RandomString.getInstance().randomNumeric(length);
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 返回一个定长的随机纯小写字母字符串
	 */
	public static String generateLowerString(final int length) {
		return RandomString.getInstance().randomAlpha(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 返回一个定长的随机纯大写字母字符
	 */
	public static String generateUpperString(final int length) {
		return RandomString.getInstance().randomAlpha(length).toUpperCase();
	}

	/**
	 * 生成一个0-9 a-z 的随机指定长度字符串
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 生成一个0-9 a-z 的随机指定长度字符串
	 */
	public static String getCharAndNumr(final int length) {
		return RandomString.getInstance().randomAlphaNumeric(length).toLowerCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            生成值的长度
	 * @return 定长的纯0字符串
	 */
	public static String generateZeroString(final int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0 超出将后面部分切掉
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 返回根据数字生成的随机字符串
	 */
	public static String toFixdLengthString(final long num, final int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length())).append(strNum);
		} else {
			return String.valueOf(num).substring(0, fixdlenth);
		}
		return sb.toString();
	}

	/**
	 * randomInt
	 * 
	 * @param min
	 *            生成值的最小值
	 * @param max
	 *            生成值的最大值
	 * @return 根据最小值最大值生成一个随机整形
	 */
	public static int randomInt(final int min, final int max) {
		return MathUtil.randomInt(min, max);
	}

	/**
	 * randomLong
	 * 
	 * @param min
	 *            生成值的最小值
	 * @param max
	 *            生成值的最大值
	 * @return 根据最小值最大值生成一个随机长整形
	 */
	public static long randomLong(final long min, final long max) {
		return MathUtil.randomLong(min, max);
	}

}
