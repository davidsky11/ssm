package com.crm.util.sms;

import com.crm.util.common.Const;

public class HttpSenderTest {
	
	public static void main(String[] args) {
		String url = Const.SMS_URL;// 应用地址
		String account = Const.SMS_ACCOUNT;// 账号
		String pswd = Const.SMS_PASS;// 密码
		String mobile = "15071493575";// 手机号码，多个号码使用","分割
		String msg = Const.SMS_MSG_HEAD + "123456" + Const.SMS_MSG_TAIL;// 短信内容
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码

		try {
			String returnString = HttpSender.batchSend(url, account, pswd, mobile, msg, needstatus, extno);
			System.out.println("返回： " + returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}
	}
}
