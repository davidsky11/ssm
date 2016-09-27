package com.crm.wechat.pay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.wechat.pay.util.WeixinToken;

/** 
 * @ClassName	WeixinLoginController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月26日 下午12:06:20
 * @Version 	V1.0    
 */
//@CrossOrigin
@Controller
@RequestMapping(value = "/api")
public class WeixinLoginController {

	//@Autowired
    //private EcshopUserDao userDao;
    
    /**
     * 获取openId
     * @param wxCode
     * @param req
     * @return
     */
    @RequestMapping(value = "/weixin/code/{wxCode}/wxcode", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getWeiXinOpenId(@PathVariable String wxCode){
        System.out.println("wxCode--》》》："+wxCode);
        if ("undefined".equals(wxCode)) {
            return null;
        }
        String wxOpenId = "";
        //EcshopUsers user = null;
        try {
            wxOpenId = WeixinToken.getWeChatId(wxCode);
            System.out.println("根据code获取得的wxOpenId:"+wxOpenId);
            /*user = userDao.findOneByOpenId(wxOpenId);
            if (user == null) {
                System.out.println("返回的openid:"+wxOpenId);
                Map<String, String> map = new HashMap<String, String>();
                map.put("openId", wxOpenId);
                return map;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    
    /**
     * 微信登陆（绑定）
     * @param ecshopUserDto
     * @param openId
     * @return
     */
    /*@RequestMapping(value = "/weixin/{openId}/member", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object checkMember(@RequestBody EcshopUserDto ecshopUserDto, @PathVariable String openId){
        String userName = ecshopUserDto.getUserName();
        Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
        md5.setEncodeHashAsBase64(true);
        String encryptedPassword = md5.encodePassword(ecshopUserDto.getPassword(), BoqumaConstant.SALT);
        System.out.println("微信登录用户--UserName:"+userName);
        //EcshopUsers user = userDao.findOneByUserNameAndPassword(userName,encryptedPassword);
        if (user == null) {
            System.out.println("用户名或密码错误！");
            Map<String, String> map = new HashMap<String, String>();
            map.put("hint", "用户名或密码错误！");
            return map;
        } else {
            user.setOpenId(openId);
            userDao.save(user);
            System.out.println("绑定成功！");
            return user;
        }
        
    }*/
    
}
 