package com.crm.wechat.pay.domain.response;

import java.io.Serializable;


/**
 * 
 * @Description 微信发送红包返回响应
 * @author Sam 
 * @date 2016年6月25日下午5:42:59
 *
 */
public class WeixinVenderPayResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String return_code; //返回状态码
    private String return_msg; //返回信息
    private String sign; //签名

    private String mch_appid; //商户appid
    private String mchid; // 商户号
    private String device_info;  // 设备号
    private String nonce_str; // 随机字符串
    private String result_code; //业务结果
    public String err_code; //错误代号
    public String err_code_des; //错误文案
    
    private String partner_trade_no; //商户订单号
    private String payment_no; //商户号
    private String payment_time; //微信支付成功时间，格式为yyyyMMddHHmmss，如2015年02月27日9点10分10秒表示为20150227091010。
	
    public String getReturn_code() {
		return return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public String getSign() {
		return sign;
	}
	public String getMch_appid() {
		return mch_appid;
	}
	public String getMchid() {
		return mchid;
	}
	public String getDevice_info() {
		return device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public String getResult_code() {
		return result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public String getPayment_no() {
		return payment_no;
	}
	public String getPayment_time() {
		return payment_time;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}
	
	@Override
	public String toString() {
		return "WeixinVenderPayResponse [return_code=" + return_code + ", return_msg=" + return_msg + ", sign=" + sign
				+ ", mch_appid=" + mch_appid + ", mchid=" + mchid + ", device_info=" + device_info + ", nonce_str="
				+ nonce_str + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des="
				+ err_code_des + ", partner_trade_no=" + partner_trade_no + ", payment_no=" + payment_no
				+ ", payment_time=" + payment_time + "]";
	}
    
}
