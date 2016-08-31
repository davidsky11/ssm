package com.crm.common.util.net;

import java.io.File;
import java.io.IOException;

import com.crm.common.util.lang.StringUtil;

import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.att.FileAttachment;

/**
 * 工具类-》网络相关工具类-》邮件处理工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class MailUtil {

	/***
	 * 发送内容至邮箱
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @param to
	 * @param subject
	 * @param context
	 * @param htmlContent
	 * @throws IOException
	 */
	public static boolean sendMail(final String host, final String username, final String password, final String to, final String subject, final String context, final String htmlContent, final String[] attachmentPaths) {
		boolean flag = false;
		Email email = Email.create().from(username).to(to).subject(subject);
		if (!StringUtil.isNullOrBlank(context)) {
			email.addText(context);
		}
		if (!StringUtil.isNullOrBlank(htmlContent)) {
			email.addHtml(htmlContent);
		}
		if (attachmentPaths != null && attachmentPaths.length > 0) {
			for (int i = 0, length = attachmentPaths.length; i < length; i++) {
				String path = attachmentPaths[i];
				String filename = com.crm.common.util.io.FileUtil.getFileNameWithSuffixByPath(path);
				EmailAttachment attachment = new FileAttachment(new File(path), filename, filename);
				email.attach(attachment);
			}
		}
		try {
			SmtpServer smtpServer = new SmtpServer(host);
			smtpServer.authenticateWith(username, password);
			SendMailSession session = smtpServer.createSession();
			session.open();
			session.sendMail(email);
			session.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}