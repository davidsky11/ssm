package com.crm.wechat.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.wechat.utils.PropertiesUtil;
import com.crm.wechat.utils.RedMd5;
import com.crm.wechat.utils.RoundUtil;

/**
 * @ClassName 	WxRedPacketService.java
 * @Description	微信红包服务类
 * @Author 		kevin
 * @CreateTime 	2016年7月15日 上午12:10:41
 * @Version 	V1.0
 */
//@Service
public class WxRedPacketService {

	private final Logger logger = LoggerFactory.getLogger(WxRedPacketService.class);

	@Autowired
	private PropertiesUtil propertiesUtil;

	/** 微信商户号 */
	private String mch_id = propertiesUtil.getRedpacketProperties("mch_id");
	/** 微信红包接口地址 */
	private String sendPacketUrl = propertiesUtil.getRedpacketProperties("sendPacketUrl");
	/** 服务号appid */
	private String appid = propertiesUtil.getRedpacketProperties("appid");
	/** 提供方名称 */
	private String nickName = propertiesUtil.getRedpacketProperties("nickName");
	/** 发红包者名称 */
	private String sendName = propertiesUtil.getRedpacketProperties("sendName");
	/** 接口调用方IP */
	private String clientIp = propertiesUtil.getRedpacketProperties("clientIp");
	/** 商户密钥 */
	private String partnerkey = propertiesUtil.getRedpacketProperties("partnerkey");
	/** 加密字符编码，我用的UTF-8 */
	private String charset = propertiesUtil.getRedpacketProperties("charset");
	/** 证书文件目录 */
	private String cerfile = propertiesUtil.getRedpacketProperties("cerfile");

	/**
	 * @Title: sendRedPacket @Description: 发送红包方法 @param @param openId
	 * 微信用户openid @param @param money 红包金额 @param @return 设定文件 @return int
	 * 返回类型 @throws
	 */
	public int sendRedPacket(String openId, String money, String actName, String wishing, String remark) {

		// 商户订单号
		String orderNNo = getOrderNo();
		// 红包参数
		Map<String, String> paramMap = getParamMap(orderNNo, openId, money, actName, wishing, remark);
		// 发送的报文参数
		String xml = createXML(paramMap);
		logger.info("红包发送报文:" + xml);

		try {
			String resXml = doSend(sendPacketUrl, xml);
			logger.info("红包发送返回报文:" + resXml);
			// 红包发送成功，状态判断
			if (resXml.indexOf("SUCCESS") > -1) {
				return 1;
			} else if (resXml.indexOf("NO_AUTH") > -1) {
				// 发放失败，此请求可能存在风险，已被微信拦截
				return 2;
			} else if (resXml.indexOf("SENDNUM_LIMIT") > -1) {
				// 该用户今日领取红包个数超过限制
				return 3;
			} else if (resXml.indexOf("MONEY_LIMIT") > -1) {
				// 红包金额发放限制
				return 4;
			} else if (resXml.indexOf("SEND_FAILED") > -1) {
				// 红包发放失败,请更换单号再重试
				return 5;
			} else if (resXml.indexOf("SYSTEMERROR") > -1) {
				// 请求已受理，请稍后使用原单号查询发放结果
				return 6;
			} else if (resXml.indexOf("NOTENOUGH") > -1) {
				// 帐号余额不足，请到商户平台充值后再重试
				return 7;
			} else {
				// 其它错误
				return 8;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @Title: getOrderNo @Description: 生成商户订单号 @param @return 设定文件 @return
	 * String 返回类型 @throws
	 */
	private String getOrderNo() {
		String order = mch_id + new SimpleDateFormat("yyyyMMddss").format(new Date());
		Random r = new Random();
		for (int i = 0; i < 2; i++) {
			order += r.nextInt(9000) + 1000;
		}
		return order;
	}

	/**
	 * @Title: getParamMap @Description: 红包参数拼接及签名生成 @param @param orderNNo
	 * 红包订单号 @param @param openId 微信openid @param @param money
	 * 红包金额 @param @param actName 活动名称 @param @param wishing 红包祝福语 @param @param
	 * remark 备注 @param @return 设定文件 @return Map<String,String> 返回类型 @throws
	 */
	private Map<String, String> getParamMap(String orderNNo, String openId, String money, String actName,
			String wishing, String remark) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("nonce_str", RoundUtil.getUUID());// 随机字符串
		paramMap.put("mch_billno", orderNNo);// 商户订单
		paramMap.put("mch_id", mch_id);// 商户号
		paramMap.put("wxappid", appid);// 商户appid
		paramMap.put("nick_name", nickName);// 提供方名称
		paramMap.put("send_name", sendName);// 发红包者名称
		paramMap.put("re_openid", openId);// 用户openid
		paramMap.put("total_amount", money);// 付款金额
		paramMap.put("total_num", "1");// 红包发送总人数
		paramMap.put("wishing", wishing);// 红包祝福语
		paramMap.put("client_ip", clientIp);// 接口调用机器IP地址
		paramMap.put("act_name", actName);// 活动名称
		paramMap.put("remark", remark);// 备注
		paramMap.put("sign", redSignal(paramMap));// 签名
		return paramMap;
	}

	private static String createXML(Map<String, String> map) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><xml>";
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String str = i.next();
			xml += "<" + str + ">" + "<![CDATA[" + map.get(str) + "]]>" + "</" + str + ">";
		}
		xml += "</xml>";
		return xml;
	}

	/**
	 * @Title: redSignal @Description: 发送红包签名生成 @param @param
	 * params @param @return 设定文件 @return String 返回类型 @throws
	 */
	private String redSignal(Map<String, String> params) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		for (Map.Entry<String, String> m : params.entrySet()) {
			packageParams.put(m.getKey(), m.getValue().toString());
		}
		StringBuffer sb = new StringBuffer();
		Set<?> es = packageParams.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!StringUtils.isEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + partnerkey);
		String sign = RedMd5.MD5Encode(sb.toString(), charset).toUpperCase();
		return sign;
	}

	public String doSend(String url, String data) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(cerfile));// P12文件目录
		try {
			keyStore.load(instream, mch_id.toCharArray());// 这里写密码..默认是你的MCHID
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray())// 这里也是写密码的
				.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = toStringInfo(response.getEntity(), charset);

				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	/**
	 * @Title: toStringInfo @Description: 返回对象转换XML @param @param
	 * entity @param @param defaultCharset @param @return @param @throws
	 * Exception @param @throws IOException 设定文件 @return String 返回类型 @throws
	 */
	private static String toStringInfo(HttpEntity entity, String defaultCharset) throws Exception, IOException {
		final InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		try {
			Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
					"HTTP entity too large to be buffered in memory");
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			Charset charset = null;

			if (charset == null) {
				charset = Charset.forName(defaultCharset);
			}
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			final Reader reader = new InputStreamReader(instream, charset);
			final CharArrayBuffer buffer = new CharArrayBuffer(i);
			final char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} finally {
			instream.close();
		}
	}

}
