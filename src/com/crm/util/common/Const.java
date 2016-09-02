package com.crm.util.common;


public class Const {
	
	/**
	 * 地图相关信息
	 */
	public static final String MAP_AK = "47TS2lczgTxANcKZepTmQVrQuhMWHPVK";
	/**
	 * 根据经纬度坐标获取地址
	 * coordtype 	- bd09ll(默认)	坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标）、wgs84ll（ GPS经纬度）
	 * location 	- 38.76623,116.43213 lat<纬度>,lng<经度>
	 * pois 		- 是否显示指定位置周边的poi，0为不显示，1为显示。当值为1时，显示周边100米内的poi。	
	 */
	public static final String URL_LL2ADDRESS = "http://api.map.baidu.com/geocoder/v2/?ak=47TS2lczgTxANcKZepTmQVrQuhMWHPVK&callback=renderReverse&location=39.983424,116.322987&output=json&pois=0";
	
	/**
	 * 短信验证 信息
	 */
	public static final String SMS_URL = "http://222.73.117.169/msg/";
	public static final String SMS_ACCOUNT = "N5765669";
	public static final String SMS_PASS = "Ps0959a2";
	public static final String SMS_MSG_HEAD = "【欢乐兑】验证码";
	public static final String SMS_MSG_TAIL = "，您正在进行手机注册，切勿将验证码泄露于他人。";
	
	/**
	 * 短信验证码 状态值
	 */
	public static final String INFO_SMS_SUCCESS = "0";  // 提交成功
	public static final String INFO_SMS_NOT_EXISTED = "101";  // 账号不存在
	public static final String INFO_SMS_PASS_ERR = "102";  // 密码错误
	public static final String INFO_SMS_TOO_FAST = "103";  // 提交请求太快
	public static final String INFO_SMS_SYS_BUSY = "104";  // 系统忙
	public static final String INFO_SMS_SENSITIVE = "105";  // 含有敏感词
	public static final String INFO_SMS_MSG_LEN_ERR = "106";  // 消息长度错（>536或<=0）
	public static final String INFO_SMS_PHONE_ERR = "107";  // 包含错误的手机号码
	public static final String INFO_SMS_PHONE_NUMBER_ERR = "108";  // 手机号码个数错（群发>50000或<=0;单发>200或《=0）
	public static final String INFO_SMS_SEND_QUOTA_EXHAUST = "109";  // 无发送额度（该用户可用短信数已使用完）
	public static final String INFO_SMS_OUT_TIME = "110";  // 不在发送时间内
	public static final String INFO_SMS_SEND_QUOTA_EXHAUST_MONTH = "111";  // 超出该账户当月发送额度限制
	public static final String INFO_SMS_PRUDUCT_UNEXISTED = "112";  // 无此产品，用户没有订购该产品
	public static final String INFO_SMS_FORMAT_ERR = "113";  // extno格式错（非数字或者长度不对）
	public static final String INFO_SMS_AUDIT_REJECT = "114";  // 自动审核驳回
	public static final String INFO_SMS_SIGN_ERR = "115";  // 签名不合法，未带签名（用户必须带签名的前提下）
	public static final String INFO_SMS_AUTH_IP_ERR = "116";  // IP地址认证错，请求调用的IP地址不是系统登记的IP地址
	public static final String INFO_SMS_NO_SEND_AUTH = "117";  // 用户没有相应的发送权限
	public static final String INFO_SMS_ACCOUNT_TIMEOUT = "118";  // 用户已过期
	public static final String INFO_SMS_MSG_OUT_OF_WHITE_LIST = "119";  // 短信内容不在白名单中
	
	/**
	 * 用户类型
	 */
	public static final String USERTYPE_SYSTEM = "1";  // 系统用户
	public static final String USERTYPE_VENDER = "2";  // 商户
	public static final String USERTYPE_APPUSER = "3";  // APP用户
	
    public static final String REDIRECT_HOME = "/";
	public static final String SESSION_USER = "USER";     // 保存用户
	public static final String SESSION_USER_TYPE = "USER_TYPE";  // 保存用户类型
	public static final String SESSION_ACCOUNT = "ACCOUNT";     // 保存用户
	public static final String LAST_PAGE = "LAST_PAGE";   //
	// 保存cookie的cookieName
	public static final String COOKIEDOMAINNAME = "2016_V_5_SSM";   // 自己随便定义
	// 加密cookie时的网站自定码
	public static final String WEBKEY = "2016_V_5_SSM";   // 自己随便定义
	
	public static final String CACHE_SESSION = "sessionId";
	
	/**
	 * 默认REST前端的每页加载数量
	 */
	public static final int DEFAULT_COUNT_PER_PAGE = 20;  // 每页20条记录
	
	/**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    
	/**
	 * token有效期（小时）
	 */
	public static final long TOKEN_EXPIRES_HOUR = 72L;    // 定义Token保持的时间（单位：小时）
	/**
	 * 
	 */
	public static final int VALID_EXPIRES_SECONDS = 600;   // 定义验证的有效期是600秒
	
	/**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "token";
    
    /**
     * 用户类别代码
     */
    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_DEALER = "dealer";
    
    /**
     * 兑奖状态 【未兑奖】
     */
    public static final String EX_STATUS_UNEXCHANGE = "0";
    /**
     * 兑奖状态 【未中奖】
     */
    public static final String EX_STATUS_NO_AWARD = "1";
    /**
     * 兑奖状态 【已兑奖】
     */
    public static final String EX_STATUS_EXCHANGED = "2";
    
    /**
     * 错误代码
     */
    /**
     * 正常 200
     */
    public static final int INFO_NORMAL = 200; // 正常
    /**
     * 对象不存在 300
     */
    public static final int ERROR_NULL_POINTER = 300;  // 对象不存在
    /**
     * 对象或值不等 301
     */
    public static final int ERROR_NOT_EQUALS = 301;  // 不相等，不对称
    /**
     * 重复 302
     */
    public static final int ERROR_DUPLICATE = 302;  // 重复的对象
    /**
     * 参数有误  303
     */
    public static final int ERROR_PARAM_MISS = 303;  // 参数有误
    /**
     * 超时 400
     */
    public static final int WARN_TIMEOUT = 400; // 过期，超时
    /**
     * 操作失败 401
     */
    public static final int WARN_OPERATE_FAIL = 401;  // 操作失败
    /**
     * 没有数据 402
     */
    public static final int WARN_NO_MORE_DATA = 402;  // 没有多余的数据
    /**
     * 授权失败  403
     */
    public static final int WARN_AUTHORIZATION_FAIL = 403;  // 授权失败
    /**
     * 服务端错误 500
     */
    public static final int ERROR_SERVER = 500;  // 服务端错误
    /**
     * 请求太多频繁
     */
    public static final int ERROR_REQUEST_TO_FAST = 501;  // 请求太过频繁
    
    /**
     * 操作类型
     */
    public static final String OPERATE_VALID_CODE = "VALIDCODE";
    public static final String OPERATE_USER_REGISTER = "REGISTER";
    public static final String OPERATE_USER_LOGIN = "LOGIN";
    public static final String OPERATE_USER_LOGOUT = "LOGOUT";
    public static final String OPERATE_AUTHORIZATION = "AUTHORIZATION";
    public static final String OPERATE_VENDER_SCAN = "VENDER_SCAN";
    public static final String OPERATE_APP_SCAN = "APP_SCAN";
    public static final String OPERATE_SCAN_RECORD = "SCAN_RECORD";
    public static final String OPERATE_EXCHANGE_RECORD = "EXCHANGE_RECORD";
    public static final String OPERATE_USER_WITH_INFO = "USER_INFO";
    public static final String OPERATE_PRODUCT_INFO = "PRODUCT_INFO";
    public static final String OPERATE_PRODUCT_TRACE = "PRODUCT_TRACE";
    public static final String OPERATE_AWARD_ANALYSIS = "AWARD_ANALYSIS";
    public static final String OPERATE_PLACE_ANALYSIS = "PLACE_ANALYSIS";
    public static final String OPERATE_SALE_ANALYSIS = "SALE_ANALYSIS";
}
