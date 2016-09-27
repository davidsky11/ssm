package com.crm.wechat.pay;

import java.net.URLEncoder;

import com.crm.common.util.lang.StringUtil;
import com.crm.common.util.math.RandomUtil;
import com.crm.wechat.constants.Constants;
import com.fasterxml.classmate.members.RawMethod;

/** 
 * @ClassName	GetWechatCode.java
 * @Description 获取微信的code
 * @Author		kevin 
 * @CreateTime  2016年7月24日 下午6:59:34
 * @Version 	V1.0    
 */
public class GetWechatCode {

	public static String  GetCodeRequest = Constants.URL;
	
	public static String getCodeRequest(){
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("APPID", urlEncodeUTF8(Constants.APPID));
        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI",urlEncodeUTF8(Constants.REDIRECT_URI));
        GetCodeRequest = GetCodeRequest.replace("SCOPE", Constants.SCOPE_BASE);
        result = GetCodeRequest;
        return result;
    }

	public static String urlEncodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getCodeRequest());
		System.out.println(RandomUtil.generateMixString(32));
	}
	
}
 