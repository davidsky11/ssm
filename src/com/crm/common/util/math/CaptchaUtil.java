package com.crm.common.util.math;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;

/**
 * 工具类-》基础工具类-》验证码工具类
 * <p>
 * [依赖 simple-captcha.jar]
 * </p>
 */
public final class CaptchaUtil {
	// 验证码图片的宽度
	private static final int WIDTH = 160;
	// 验证码图片的高度
	private static final int HEIGHT = 40;

	/**
	 * 私有 构造函数
	 */
	private CaptchaUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * 创建验证码
	 * 
	 * @param session
	 *            客户端session
	 * @param response
	 *            客户端response
	 */
	public static void getCaptcha(final HttpSession session, final HttpServletResponse response) {
		getCaptcha(session, response, WIDTH, HEIGHT);
	}

	/**
	 * 创建验证码
	 * 
	 * @param session
	 *            客户端session
	 * @param response
	 *            客户端response
	 * @param width
	 *            验证码图片的宽度
	 * @param height
	 *            验证码图片的高度
	 * 
	 * 
	 */
	public static void getCaptcha(final HttpSession session, final HttpServletResponse response, final int width, final int height) {
		// 根设置的大小初始化验证码对象localCaptcha
		Captcha localCaptcha = new Captcha.Builder(width, height).addText().addNoise().build();
		// 将验证码的图片写到response中
		CaptchaServletUtil.writeImage(response, localCaptcha.getImage());
		// 将验证码对象localCaptcha放到Session中，待会会在LoginAction类中取出
		session.setAttribute("simpleCaptcha", localCaptcha);
	}

	/**
	 * 清空验证码
	 * 
	 * @param session
	 *            客户端session
	 */
	public static void resetCaptcha(final HttpSession session) {
		session.setAttribute("simpleCaptcha", null);
	}

	/**
	 * 校验验证码
	 */
	public static boolean checkCaptcha(final HttpSession session, final String checkCode) {
		// 从session中获取Captcha类对象localCaptcha；localCaptcha中包含了原来的一组图片，验证码对
		Captcha localCaptcha = (Captcha) session.getAttribute("simpleCaptcha");
		if (localCaptcha == null) {
			return false;
		} else {
			return localCaptcha.isCorrect(checkCode);
		}
	}
}
