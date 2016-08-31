package com.crm.wechat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.http.HttpEntity;

import com.crm.util.common.MD5;
import com.crm.wechat.model.RedpackBean;

/** 
 * @ClassName	SendRedpack.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:21:19
 * @Version 	V1.0    
 */
public class SendRedpack {

	/**
	* 发送现金红包
	* @throws KeyStoreException 
	* @throws IOException 
	* @throws CertificateException 
	* @throws NoSuchAlgorithmException 
	* @throws UnrecoverableKeyException 
	* @throws KeyManagementException 
	* @throws DocumentException 
	*/
	public void sendRedPack() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException, DocumentException {
	// 获取uuid作为随机字符串
	String nonceStr = uuidGenerator.generate();
	String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
	String code = createCode(10);
	String mch_id = "10000100";//商户号
	String appid = "wxd930ea5d5a258f4f";
	String opendid = "xxxxxxxxxxxxxxxxx"; //发送给指定微信用户的openid
	RedpackBean sendRedPackPo = new RedpackBean();
	String totalAmount = "1";

	sendRedPackPo.setNonce_str(nonceStr);
	sendRedPackPo.setMch_billno(mch_id + today + code);
	sendRedPackPo.setMch_id(mch_id);
	sendRedPackPo.setWxappid(appid);
	sendRedPackPo.setNick_name("xxx");
	sendRedPackPo.setSend_name("xxx");
	sendRedPackPo.setRe_openid(opendid);
	sendRedPackPo.setTotal_amount(totalAmount);
	sendRedPackPo.setMin_value(totalAmount);
	sendRedPackPo.setMax_value(totalAmount);
	sendRedPackPo.setTotal_num("1");
	sendRedPackPo.setWishing("祝您新年快乐！");
	sendRedPackPo.setClient_ip("192.168.1.1"); //IP
	sendRedPackPo.setAct_name("小游戏");
	sendRedPackPo.setRemark("快来抢红包！");


	//把请求参数打包成数组
	Map<String, String> sParaTemp = new HashMap<String, String>();
	sParaTemp.put("nonce_str", sendRedPackPo.getNonce_str());
	        sParaTemp.put("mch_billno", sendRedPackPo.getMch_billno());
	        sParaTemp.put("mch_id", sendRedPackPo.getMch_id());
	sParaTemp.put("wxappid", sendRedPackPo.getWxappid());
	sParaTemp.put("nick_name", sendRedPackPo.getNick_name());
	sParaTemp.put("send_name", sendRedPackPo.getSend_name());
	sParaTemp.put("re_openid", sendRedPackPo.getRe_openid());
	sParaTemp.put("total_amount", sendRedPackPo.getTotal_amount());
	sParaTemp.put("min_value", sendRedPackPo.getMin_value());
	sParaTemp.put("max_value", sendRedPackPo.getMax_value());
	sParaTemp.put("total_num", sendRedPackPo.getTotal_num());
	sParaTemp.put("wishing", sendRedPackPo.getWishing());
	sParaTemp.put("client_ip", sendRedPackPo.getClient_ip());
	sParaTemp.put("act_name", sendRedPackPo.getAct_name());
	sParaTemp.put("remark", sendRedPackPo.getRemark());


	        //除去数组中的空值和签名参数
	        Map<String, String> sPara = paraFilter(sParaTemp);
	String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	String key = "&key=192006250b4c09247ec02edce69f6a2d"; //商户支付密钥
	String mysign = MD5.sign(prestr, key, "utf-8").toUpperCase();
	        
	sendRedPackPo.setSign(mysign);

	        
	String respXml = MessageUtil.messageToXml(sendRedPackPo);

	//打印respXml发现，得到的xml中有“__”不对，应该替换成“_”
	respXml = respXml.replace("__", "_");


	        // 将解析结果存储在HashMap中
	Map<String, String> map = new HashMap<String, String>();


	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
	        FileInputStream instream = new FileInputStream(new File("/home/apiclient_cert.p12")); //此处为证书所放的绝对路径
	        
	        try {
	            keyStore.load(instream, mch_id.toCharArray());
	        } finally {
	            instream.close();
	        }


	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, mch_id.toCharArray())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
	        
	        try {


	            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
	            
	            StringEntity  reqEntity  = new StringEntity(respXml, "utf-8");
	            
	            // 设置类型 
	            reqEntity.setContentType("application/x-www-form-urlencoded"); 
	            
	            httpPost.setEntity(reqEntity);
	            
	            System.out.println("executing request" + httpPost.getRequestLine());


	            CloseableHttpResponse response = httpclient.execute(httpPost);
	            try {
	                HttpEntity entity = response.getEntity();
	                System.out.println(response.getStatusLine());
	                if (entity != null) {


	            // 从request中取得输入流
	            InputStream inputStream = entity.getContent();
	            // 读取输入流
	            SAXReader reader = new SAXReader();
	            Document document = reader.read(inputStream);
	            // 得到xml根元素
	            Element root = document.getRootElement();
	            // 得到根元素的所有子节点
	            List<Element> elementList = root.elements();


	            // 遍历所有子节点
	            for (Element e : elementList)
	            map.put(e.getName(), e.getText());


	            // 释放资源
	            inputStream.close();
	            inputStream = null;


	                }
	                EntityUtils.consume(entity);
	            } finally {
	                response.close();
	            }
	        } finally {
	            httpclient.close();
	        }


	// 返回状态码
	String return_code = map.get("return_code");
	// 返回信息
	String return_msg = map.get("return_msg");
	// 业务结果
	String result_code = map.get("result_code");
	// 错误代码
	String err_code = map.get("err_code");
	// 错误代码描述
	String err_code_des = map.get("err_code_des");

	/**
	* 根据以上返回码进行业务逻辑处理
	*/
	
}
 