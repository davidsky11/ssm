package com.crm.wechat.pay.domain;

import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName	MerchantsGlobalError.java
 * @Description 微信商户api全局错误
 * 		每次调用接口时，可能获得正确或错误的返回码，开发者可以根据返回码信息调试接口，排查错误。
 * @Author		kevin 
 * @CreateTime  2016年9月27日 上午11:27:37
 * @Version 	V1.0    
 */
public class MerchantsGlobalError {

	public static Map<String, String> map = new HashMap<String, String>();
    static {
        map.put("NO_AUTH", "发放失败，此请求可能存在风险，已被微信拦截");
        map.put("SENDNUM_LIMIT", "该用户今日领取红包个数超过限制");
        map.put("CA_ERROR", "请求未携带证书，或请求携带的证书出错");
        map.put("ILLEGAL_APPID", "错误传入了app的appid");
        map.put("SIGN_ERROR", "商户签名错误");
        map.put("FREQ_LIMIT", "受频率限制");
        map.put("XML_ERROR", "请求的xml格式错误，或者post的数据为空 ");
        map.put("PARAM_ERROR", "参数错误");
        map.put("OPENID_ERROR", "Openid错误");
        map.put("NOTENOUGH", "余额不足");
        map.put("FATAL_ERROR", "重复请求时，参数与原单不一致");
        map.put("SECOND_OVER_LIMITED", "SECOND_OVER_LIMITED");
        map.put("DAY_ OVER_LIMITED", "企业红包的按天日发放受限 ");
        map.put("MONEY_LIMIT", "红包金额发放限制");
        map.put("SEND_FAILED", "红包发放失败,请更换单号再重试");
        map.put("SYSTEMERROR", "系统繁忙，请再试");
        
        map.put("NOAUTH", "没有权限");
        map.put("AMOUNT_LIMIT", "付款金额不能小于最低限额");
        map.put("OPENID_ERROR", "Openid错误");
        map.put("NAME_MISMATCH", "姓名校验出错");
    }
    
}
 