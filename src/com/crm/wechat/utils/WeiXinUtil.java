﻿package com.crm.wechat.utils;  

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class WeiXinUtil {
	public static final String OPENID_KEY = "openid";	
	public static final String APPID = "***************";
	public static final String APPSECRET= "****************";

	public static final String OAUTH_BASE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri=REDIRECT_URL&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
    public static final String REDIRECT_URI = "http://xxxxxxxx/wechat/oauth.shtml";
    
    public static String getUrl() {
		return "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSECRET+"&code=CODE&grant_type=authorization_code";
	}
    public static String getOauthLinkURL(String state){
    	String oauthLink = OAUTH_BASE;
		String redirect = REDIRECT_URI;
		try {
			redirect = URLEncoder.encode(redirect, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		oauthLink = oauthLink.replace("REDIRECT_URL", redirect).replace("STATE", state);
		return oauthLink;
    }
}
  
