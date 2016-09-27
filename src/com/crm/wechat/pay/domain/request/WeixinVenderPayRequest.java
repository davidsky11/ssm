package com.crm.wechat.pay.domain.request;

import java.io.Serializable;

import com.crm.wechat.pay.domain.WxConfig;
import com.crm.wechat.pay.util.WeixinUtils;

/**
 * @ClassName WeixinVenderPayRequest.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年9月27日 下午12:06:46
 * @Version V1.0
 */
public class WeixinVenderPayRequest implements Serializable {

	private static final long serialVersionUID = -6472733331144256817L;

	public String mch_appid = WxConfig.WXAPPID; // 公众账号appid
	public String mchid = WxConfig.MCH_ID; // 微信支付分配的商户号
	public String device_info;  // 设备号
	public String nonce_str = WeixinUtils.getNonceStr(); // 随机字符串，不长于32位
	public String sign; // 签名
	public String partner_trade_no = WeixinUtils.getMchBillno(); // 商户订单号，必须唯一，组成：
	public String openid; // 收款方
	public String check_name = "NO_CHECK";  // 校验用户姓名选项 NO_CHECK/FORCE_CHECK/OPTION_CHECK
	public String re_user_name = WxConfig.SEND_NAME; // 商户名称
	public int amount; // 企业付款金额，单位为分
	public String desc;  // 企业付款描述信息
	public String spbill_create_ip;  // ip地址，32位
	
	public String getMch_appid() {
		return mch_appid;
	}
	public String getMchid() {
		return mchid;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public String getOpenid() {
		return openid;
	}
	public String getCheck_name() {
		return check_name;
	}
	public String getRe_user_name() {
		return re_user_name;
	}
	public int getAmount() {
		return amount;
	}
	public String getDesc() {
		return desc;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public void setRe_user_name(String re_user_name) {
		this.re_user_name = re_user_name;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	
	@Override
	public String toString() {
		return "WeixinVenderPayRequest [mch_appid=" + mch_appid + ", mchid=" + mchid + ", device_info=" + device_info
				+ ", nonce_str=" + nonce_str + ", sign=" + sign + ", partner_trade_no=" + partner_trade_no + ", openid="
				+ openid + ", check_name=" + check_name + ", re_user_name=" + re_user_name + ", amount=" + amount
				+ ", desc=" + desc + ", spbill_create_ip=" + spbill_create_ip + "]";
	}
	
}
