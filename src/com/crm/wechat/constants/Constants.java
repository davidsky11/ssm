package com.crm.wechat.constants;

/**
 * @ClassName Constants.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月24日 下午7:02:51
 * @Version V1.0
 */
public class Constants {

	public static final String REDIRECT_URI = "www.baidu.com";
	public static final String SCOPE_BASE = "snsapi_base";
	public static final String SCOPE_INFO = "snsapi_userinfo";
	public static final String APPID = "wx64a24dc49c06f8ab";
	public static final String APPSECRET = "498dfcf5890b76ea6380061d31a75baf";
	public static final String MCH_ID = "";  // 商户号
	public static final String KEY = "";  // API密钥
	public static final String KEY_PATH = ""; // 密钥文件的存放路径
	
	public static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	// 获取access_token接口
	public static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取关注者列表
	public static final String URL_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";

	// 企业付款
	public static final String VENDER_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
}
