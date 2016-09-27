package com.crm.wechat.pay.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName	OpenIDAction.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月26日 下午1:09:09
 * @Version 	V1.0    
 */
public class OpenIDAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(OpenIDAction.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenIDAction() {
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
		LOGGER.info("acquireOpenID begin at {}", new Date().getTime());
		String reqUrl = generateReqUrl();
		LOGGER.info("acquireOpenID url is {}", reqUrl);
		response.sendRedirect(reqUrl);
		return;
	}

	private String generateReqUrl() throws UnsupportedEncodingException {
		String url_callback = "http://2eb6cadb.ittun.com/crmnew/wechat/callBack.do?u=1";
		return "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid="
				+ "wx2585787e0ad537a5"
				+ "&redirect_uri="
				+ URLEncoder.encode(url_callback,"UTF-8")
				+ "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	}
	
}
 