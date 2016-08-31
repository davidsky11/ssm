package com.crm.common.util.system;

/**
 * 工具类-》系统工具类-》 剪贴板工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class ClipboardUtil {

	private ClipboardUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * 粘贴到剪贴板
	 * 
	 * @param str
	 *            将内容粘贴到剪贴板
	 */
	public static void copyToClipboard(final String str) {
		jodd.util.ClipboardUtil.copyToClipboard(str);
	}

	/**
	 * 从剪贴板得到字符串
	 * 
	 * @return 从剪贴板获得的内容
	 */
	public static String getStringFromClipboard() {
		return jodd.util.ClipboardUtil.getStringFromClipboard();
	}
}
