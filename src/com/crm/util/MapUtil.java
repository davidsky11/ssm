package com.crm.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.crm.domain.po.Address;

/** 
 * @ClassName	MapUtil.java
 * @Description 
 * @Author		kevin 
 * @CreateTime 
 * @Version 	V1.0    
 */
public class MapUtil {
	
	public static Address location2Address(double lat, double lng) throws Exception {
		String url = "http://api.map.baidu.com/geocoder/v2/";
		
		Address addr = new Address();
		String json = "";
		
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("ak", "47TS2lczgTxANcKZepTmQVrQuhMWHPVK"),
					new NameValuePair("location", lat +"," +lng),
					new NameValuePair("output", "json"), 
					new NameValuePair("pois", "0")
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				//json = URLDecoder.decode(baos.toString(), "UTF-8");
				json = baos.toString("UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} catch (UnsupportedEncodingException e) {
			
		} finally {
			method.releaseConnection();
		}
		
		System.out.println(json);
		
		//Gson gson = new Gson();
		addr = GsonUtils.fromJson(json, Address.class);
		if (addr != null)
			System.out.println(addr.toString());
		
		return addr;
	}
	
	public static void main(String[] args) {
		try {
			String url = "http://api.map.baidu.com/geocoder/v2/";
			
			Address addr = new Address();
			String json = "";
			
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			GetMethod method = new GetMethod();
			try {
				URI base = new URI(url, false);
				method.setURI(new URI(base, "", false));
				method.setQueryString(new NameValuePair[] { 
						new NameValuePair("ak", "47TS2lczgTxANcKZepTmQVrQuhMWHPVK"),
						new NameValuePair("location", 30.8 +"," +114.3),
						new NameValuePair("output", "json"), 
						new NameValuePair("pois", "0")
					});
				int result = client.executeMethod(method);
				if (result == HttpStatus.SC_OK) {
					InputStream in = method.getResponseBodyAsStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					//json = URLDecoder.decode(baos.toString("UTF-8"), "UTF-8");
					json = baos.toString("UTF-8");
				} else {
					throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
				}
			} catch (UnsupportedEncodingException e) {
				
			} finally {
				method.releaseConnection();
			}
			
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
 