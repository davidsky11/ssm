/**
 * 
 */
package com.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Account;
import com.crm.domain.SysMenu;
import com.crm.domain.easyui.Tree;
import com.crm.service.AccountService;
import com.crm.util.AccountCookieUtil;
import com.crm.util.RequestUtil;
import com.crm.util.common.Const;

/**
 * @author zh
 * 2014-7-26
 */
@Controller
public class SystemController2 extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(SystemController2.class);
	
	@Resource
	private AccountService accountService;
	
    @RequestMapping(value = "/accountLogin",method = RequestMethod.POST)
    public String login(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam String username, @RequestParam String password, 
    		@RequestParam String code, @RequestParam String userType,
    		@RequestParam String remember) throws Exception{
    	
		if (code.toLowerCase().equals(request.getSession().getAttribute("RANDOMCODE").toString().toLowerCase())){
			int type = Integer.parseInt(userType);
			Account account = accountService.findByUserName(username, type);
			
			if (account == null) {
				log.info("登陆用户名不存在");  
	    		request.getSession().setAttribute("message", "用户名不存在，请重新登录");
	    		return "login"; 
			}else {
				if (account.getPassword().equals(password)) {
					
					if(remember!=null && remember.equals("Y")){ // 判断是否要添加到cookie中
						// 保存用户信息到cookie
						AccountCookieUtil.saveCookie(account, response);
					}
					
					// 保存用信息到session
					request.getSession().setAttribute(Const.SESSION_ACCOUNT, account);  
	        		return "redirect:" + RequestUtil.retrieveSavedRequest();//跳转至访问页面
					
				}else {
					log.info("登陆密码错误");  
	        		request.getSession().setAttribute("message", "用户名密码错误，请重新登录");
	        		return "login"; 
				}
			}
		}else {
			request.getSession().setAttribute("message", "验证码错误，请重新输入");
    		return "login"; 
		}
    }
    
    /**
	 * 用户注销
	 * @return
	 */
	/*@RequestMapping(value="/logout")
	public String logout(HttpSession session,HttpServletResponse response){
		session.removeAttribute(Const.SESSION_USER);
		UserCookieUtil.clearCookie(response);
		return "redirect:/";
	}*/
    
    /**
     * 获取菜单栏(easyUI Tree)
     * @param id
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenu1", method = RequestMethod.POST)  
    public List<Tree> getMenu(HttpSession session){  
    	Account account =  (Account) session.getAttribute(Const.SESSION_ACCOUNT); 
    	List<SysMenu> menuList = accountService.getMenu(account.getId());
    	List<Tree> treeList = new ArrayList<Tree>();

        for (SysMenu menu : menuList) {
        	
			Tree node = new Tree();
			
			node.setId(menu.getId());
			node.setPid(menu.getParentid());
			node.setText(menu.getName());
			node.setIconCls(menu.getIconCls());
			
			if(menu.getParentid()!=0){	// 有父节点
				node.setPid(menu.getParentid());
			}
			if(menu.getCountChildrens() > 0){	//有子节点
				node.setState("closed");
			}
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("url", menu.getUrl());
			node.setAttributes(attr);
			treeList.add(node);
        }
    	return treeList;
    }
    
}
