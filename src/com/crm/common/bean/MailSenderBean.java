package com.crm.common.bean;

import java.util.Properties;

/**
 * 发送邮件需要使用的基本信息
 */
public class MailSenderBean {
	private String mailServerHost; // 发送邮件的服务器的IP
	private String mailServerPort = "25"; // 发送邮件的服务器端口
	private String fromAddress; // 邮件发送者的地址
	private String toAddress; // 邮件接收者的地址
	private String userName; // 登陆邮件发送服务器的用户名
	private String password;  // 登陆邮件发送服务器的密码
	private boolean validate = false; // 是否需要身份验证
	private String subject; // 邮件主题
	private String content; // 邮件的文本内容
	private String[] attachFileNames; // 邮件附件的文件名

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isValidate() {
		return validate;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}
	
}
