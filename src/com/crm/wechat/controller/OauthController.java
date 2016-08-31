package com.crm.wechat.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.wechat.constants.ShareState;
import com.crm.wechat.utils.JsonUtil;
import com.crm.wechat.utils.SessionHelper;
import com.crm.wechat.utils.WeiXinUtil;

/** 
 * @ClassName	OauthController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:35:03
 * @Version 	V1.0    
 */
//@Controller  
//@RequestMapping("/wechat/")
public class OauthController extends BaseController {

	 @RequestMapping("oauth")  
	    public String oauth(@RequestParam("code") String code,@RequestParam("state") String state) throws Exception{      
	        try {  
	            //没有code,非法链接进来的  
	            if(StringUtils.isBlank(code)){  
	                return "error";  
	            }  
	            String url = WeiXinUtil.getUrl().replace("CODE", code);  
	            Map<String, Object> map = JsonUtil.getMapByUrl(url);  
	            if(map.get("openid") != null){  
	                if(ShareState.LUCKYMONEY.getDesc().equals(state)){  
	                    SessionHelper.setAttribute(WeiXinUtil.OPENID_KEY, map.get("openid").toString());  
	                    return "redirect:/luckymoney/signup.shtml";  
	                }  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return "error";  
	    }  
	 
}
 