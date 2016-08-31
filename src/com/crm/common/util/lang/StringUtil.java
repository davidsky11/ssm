package com.crm.common.util.lang;

/**
 * 工具类-》基础工具类-》字符串工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class StringUtil {
	private StringUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * 将字符串第一个字母转化为大写
	 * 
	 * @param str
	 *            需要被转换的字符串
	 * @return 转换后的字符串
	 */
	public static String capitalize(final String str) {
		return jodd.util.StringUtil.capitalize(str);
	}

	/**
	 * 压缩字符串 如：abcccd 压缩为abcd
	 * 
	 * @param str
	 *            需要被压缩的字符串
	 * @param c
	 *            需要压缩的字符
	 * @return 压缩后的字符串
	 */
	public static String compressChars(final String str, final char c) {
		return jodd.util.StringUtil.compressChars(str, c);
	}

	/**
	 * 转化字符串的字符集
	 * 
	 * @param str
	 *            需要被转换字符集的字符串
	 * @param srcCharsetName
	 *            原始的字符集
	 * @param newCharsetName
	 *            要转换为的字符集
	 * @return 转换后的字符串
	 */
	public static String convertCharset(final String str, final String srcCharsetName, final String newCharsetName) {
		return jodd.util.StringUtil.convertCharset(str, srcCharsetName, newCharsetName);
	}

	/**
	 * 统计字符串中包含的子字符串
	 * 
	 * @param str
	 *            需要被统计的字符串
	 * @param sub
	 *            需要统计的子字符串
	 * @param startIndex
	 *            开始统计的位置
	 * @param ignoreCase
	 *            是否忽略大小写
	 * @return 统计的数量
	 */
	public static int count(final String str, final String sub, final int startIndex, final boolean ignoreCase) {
		if (ignoreCase) {
			return jodd.util.StringUtil.countIgnoreCase(str.substring(startIndex), sub);
		} else {
			return jodd.util.StringUtil.count(str, sub, startIndex);
		}
	}

	/**
	 * 切除字符串
	 * 
	 * @param str
	 *            需要被切除的字符串
	 * @param prefix
	 *            需要切掉的前缀 可以为null
	 * @param suffix
	 *            需要切掉的后缀 可以为null
	 * @return 切除后的字符串
	 */
	public static String cut(final String str, final String prefix, final String suffix) {
		String source = str;
		if (jodd.util.StringUtil.isNotEmpty(prefix)) {
			source = jodd.util.StringUtil.cutPrefix(source, prefix);
		}
		if (jodd.util.StringUtil.isNotEmpty(suffix)) {
			source = jodd.util.StringUtil.cutSuffix(source, suffix);
		}
		return source;
	}

	/**
	 * cut字符串
	 */
	public static String cutFrom(final String str, final String substring) {
		return jodd.util.StringUtil.cutFromIndexOf(str, substring);
	}

	/**
	 * cut字符串
	 */
	public static String cutTo(final String str, final String substring) {
		return jodd.util.StringUtil.cutToIndexOf(str, substring);
	}

	/**
	 * join字符串
	 */
	public static String join(final Iterable<?> elements, final String separator) {
		return jodd.util.StringUtil.join(elements, separator);
	}

	/**
	 * 是否为null或者""
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return null或者""返回true
	 */
	public static boolean isNullOrEmpty(final String str) {
		return jodd.util.StringUtil.isEmpty(str);
	}

	/**
	 * 是否为null或者""或者空白字符
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return null或者""或者空白字符返回true
	 */
	public static boolean isNullOrBlank(final String str) {
		return jodd.util.StringUtil.isBlank(str);
	}
	
	/**
	 * 是否为null或者""或者空白字符
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return null或者""或者空白字符返回true
	 */
	public static boolean isNotNullOrBlank(final String str) {
		return !jodd.util.StringUtil.isBlank(str);
	}

	/**
	 * 取两个字符串前面最大的相同部分
	 * 
	 * @param str1
	 *            字符串1
	 * @param str2
	 *            字符串2
	 * @return 前面相同的部分
	 */
	public static String maxCommonPrefix(final String str1, final String str2) {
		return jodd.util.StringUtil.maxCommonPrefix(str1, str2);
	}

	/**
	 * 添加前缀，如果没有前缀添加前缀
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param prefix
	 *            前缀
	 * @return 添加前缀后的字符串
	 */
	public static String prefix(final String str, final String prefix) {
		return jodd.util.StringUtil.prefix(str, prefix);
	}

	/**
	 * 反转字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @return 反转后的字符串
	 */
	public static String reverse(final String str) {
		return jodd.util.StringUtil.reverse(str);
	}

	/**
	 * 添加后缀，如果没有后缀添加后缀
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param suffix
	 *            后缀
	 * @return 添加后缀后的字符串
	 */
	public static String suffix(final String str, final String suffix) {
		return jodd.util.StringUtil.suffix(str, suffix);
	}

	/**
	 * surround
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @return surround后的字符串
	 */
	public static String surround(final String str, final String prefix, final String suffix) {
		return jodd.util.StringUtil.surround(str, prefix, suffix);
	}

	/**
	 * 去掉字符串左侧的空白字符
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @return 去掉左侧空白字符的字符串
	 */
	public static String trimLeft(final String str) {
		return jodd.util.StringUtil.trimLeft(str);
	}

	/**
	 * 去掉字符串右侧的空白字符
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @return 去掉右侧空白字符的字符串
	 */
	public static String trimRight(final String str) {
		return jodd.util.StringUtil.trimRight(str);
	}

	/**
	 * 将字符串第一个字母转化为小写
	 * 
	 * @param str
	 *            需要被转换的字符串
	 * @return 转换后的字符串
	 */
	public static String uncapitalize(final String str) {
		return jodd.util.StringUtil.uncapitalize(str);
	}
}
