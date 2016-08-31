package com.crm.wechat.manager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/** 
 * @ClassName	MyX509TrustManager.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月24日 下午6:28:34
 * @Version 	V1.0    
 */
public class MyX509TrustManager implements X509TrustManager {

	// 检验客户端证书
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	// 检查服务器端证书
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	// 返回受信任的X509证书数组
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
 