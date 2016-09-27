package com.crm.wechat.pay.action; 

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crm.util.GsonUtils;
import com.crm.wechat.pay.dto.ResponseDto;
import com.crm.wechat.pay.util.HttpClientUtil;

/** 
 * @ClassName	CallBackAction.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月26日 下午1:09:52
 * @Version 	V1.0    
 */
public class CallBackAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory
			.getLogger(CallBackAction.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CallBackAction() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("CallBackAction begin at {}", new Date().getTime());
		String openid = request.getParameter("openid");
//		LOGGER.info("获取到的 OpenId : {} ", openid);
		String code = request.getParameter("code");
		LOGGER.info("获到的 code : {} ", code);
		String redirectUrl = acquireOpenIDUrlWithCode(code);
		LOGGER.info("转发路径  : {}", redirectUrl);
		String responseText = HttpClientUtil.doGet(redirectUrl);
		LOGGER.info("响应的文本  : {}", redirectUrl);
		LOGGER.info("回复：\n {}", responseText);
		
		ResponseDto dto = GsonUtils.fromJson(responseText, ResponseDto.class);
		if (dto != null) {
			System.out.println(dto);
			openid = dto.getOpenid();
			LOGGER.info("获取到的 OpenId : {} ", openid);
		}
		
		String userId = request.getParameter("u");
		System.out.println("userId : " + userId + " ------- openid : " + openid);

		LOGGER.info("=======================================");
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.print("响应文本：\n "  + responseText);
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url_callback = "http://2eb6cadb.ittun.com/crmnew/wechat/callBack.do";
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxba18404f8cfe70cd&redirect_uri="
				+ URLEncoder.encode(url_callback,"UTF-8") + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		System.out.println(HttpClientUtil.doGet(url));
	}

	private String acquireOpenIDUrlWithCode(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx2585787e0ad537a5&"
				+ "secret=498dfcf5890b76ea6380061d31a75baf&code="
				+ code
				+ "&grant_type=authorization_code");
		return sb.toString();
	}

}
 