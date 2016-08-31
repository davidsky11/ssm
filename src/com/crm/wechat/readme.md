为了防止用户篡改openid，建议将openid放在session里面，而不是在用户提交手机号码的时候作为参数传递给后台。

参数1：随机字符串     nonce_str
	// 获取随机数,这里设定长度20,只要不成长于32位
	String nonce_str = RandomStringGenerator.getRandomStringByLength(20); 

参数2： 商户号   mch_id
	String mch_id = “xxxxxxxx”;
	
参数3：  商户订单号  mch_billno
	String mch_billno = mch_id + GenerateSequenceUtil.generateSequenceNo();

参数4：  公众账号appid  wxappid
	这个参数可以在微信开发者中心里面看到
	// 微信分配的公众账号ID
	String wxappid = "wx8888888888888888";
	
参数5：  商户名称  send_name
	随便写
	String send_name = "测试"
	
参数6：  用户openid  re_reopenid
	微信号和公众号对应的唯一的加密过的字符串
	String re_openid = "xxxxxxxxxxxxxxxxxx";
	
参数7：  付款金额 total_amount
	int total_amount = 100;  单位：分
	
参数8：  红包发送总人数 total_num
	int total_num = 1;
	
参数9：  红包祝福语 wishing
	随便写
	
参数10：  IP地址  client_ip
	String client_ip = InetAddress.getLocalHost().getHostAddress().toString();
	这里可能报异常，需要抓取一下
	
参数11：  活动名称 act_name
	随便写
	String act_name = "测试创建20150906";
	
参数12：  备注 remark
	随便写
	String remark = "测试";
	
最后一个参数sign
	// 签名
	


	Map<String,Object> map = new HashMap<String,Object>();
	map.put("nonce_str", nonce_str);
	map.put("mch_billno", mch_billno);
	map.put("mch_id", mch_id);
	map.put("wxappid", wxappid);
	map.put("send_name", send_name);
	map.put("re_openid", re_openid);
	map.put("total_amount", total_amount);
	map.put("total_num", total_num);
	map.put("wishing", wishing);
	map.put("client_ip", client_ip);
	map.put("act_name", act_name);
	map.put("remark", remark);
	String sign = Signature.getSign(map);
	




获取用户的基本信息

	接口调用请求说明
	http请求方式： GET
	https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	
	参数说明
	参数				是否必须			说明
	access_token		是				调用接口凭证
	openid			是				普通用户的标识，对当前公众号唯一
	lang				否				返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	
	返回说明
	正常情况下，微信会返回下述JSON数据包给公众号：
	{
	    "subscribe": 1, 
	    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
	    "nickname": "Band", 
	    "sex": 1, 
	    "language": "zh_CN", 
	    "city": "广州", 
	    "province": "广东", 
	    "country": "中国", 
	    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
	   "subscribe_time": 1382694957,
	   "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
	   "remark": "",
	   "groupid": 0
	}
	
	参数说明：
	参数				说明	
	subscribe		用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	openid			用户的标识，对当前公众号唯一
	nickname			用户的昵称
	sex				用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	city				用户所在城市
	country			用户所在国家
	province			用户所在省份
	language			用户的语言，简体中文为zh_CN
	headimgurl		用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	unionid			只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见： 获取用户个人信息（UnionID机制）
	remark			公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	groupid			用户所在的分组ID
	
	错误时微信会返回错误码等消息，JSON数据包示例如下（该示例为AppID无效错误）：
	{"errcode":40013,"errmsg":"invalid appid"}
	
	
获取用户OpenID

	https://open.weixin.qq.com/connect/oauth2/authorize?appid=appid&redirect_uri=url&response_type=code&scope=snsapi_userinfo&state=park#wechat_redirect
	
	参数：
	openID			公众号的唯一标识
	redirect_uri		重定向的url，就是授权后要跳转的页面
	scope			应用授权作用域
		snsapi_base		不弹出授权页面，直接跳转，只能获取用户OpenID
		snsapi_userinfo	弹出授权页面，可通过OpenID拿到呢称、性别、所在地
	state			重定向后带的参数
	