package com.crm.wechat.controller; 

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crm.wechat.aes.AesException;
import com.crm.wechat.aes.WXBizMsgCrypt;
import com.crm.wechat.service.CoreService;

/** 
 * @ClassName	CoreController.java
 * @Description 注解方式打开链接
 * @Author		kevin 
 * @CreateTime  2016年7月15日 上午1:09:05
 * @Version 	V1.0    
 */
//@Controller
public class CoreController {

	private String token = "sunlight";
	private String encodingAESKey = "s8vFF4f6AWay3uAdJh79WD6imaam4BV6Kl4eL4UzgfM";
	private String corpId = "xxxxxxxxxx"; //你的企业号ID

	@RequestMapping(value = { "/coreJoin.do" }, method = RequestMethod.GET)
	public void coreJoinGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 微信加密签名
		String msg_signature = request.getParameter("msg_signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		System.out.println("request=" + request.getRequestURL());

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		String result = null;
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey,
					corpId);
			result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
		} catch (AesException e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = token;
		}
		out.print(result);
		out.close();
		out = null;
	}

	@RequestMapping(value = { "/coreJoin.do" }, method = RequestMethod.POST)
	public void coreJoinPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(request);
		System.out.println("respMessage=" + respMessage);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();

	}

}
 