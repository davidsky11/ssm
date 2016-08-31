package com.crm.wechat; 

/** 
 * @ClassName	AccessToken.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 上午1:37:44
 * @Version 	V1.0    
 */
public class AccessToken {

	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }
    
}
 