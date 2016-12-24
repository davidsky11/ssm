/**
 * 
 */
package com.crm.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.SysMenu;
import com.crm.domain.User;
import com.crm.domain.easyui.Tree;
import com.crm.service.MenuService;
import com.crm.service.UserService;
import com.crm.util.RequestUtil;
import com.crm.util.UserCookieUtil;
import com.crm.util.common.Const;

/**
 * @author zh
 * 2014-7-26
 */
@Controller
public class SystemController extends BaseController {
	
	private final Logger log = LoggerFactory.getLogger(SystemController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String home() {
		log.info("返回首页！");
		return "index";
	}
	
	@RequestMapping(value = "/syslogin",method = RequestMethod.GET)
	public String syslogin() {
		log.info("返回首页！");
		return "index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginIdx() {
		return "redirect:/";
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam String username, @RequestParam String password, 
    		@RequestParam String code, @RequestParam String userType) throws Exception{
    	
    	String returnUrl = "login";
    	
    	if (username.equals("admin")) {
    		returnUrl = "syslogin";
    	}
    	
    	if (request.getSession().getAttribute("RANDOMCODE") == null || code == null) {
    		return returnUrl;
    	}
    	
		if (code.toLowerCase().equals(request.getSession().getAttribute("RANDOMCODE").toString().toLowerCase())){
			List<User> userList = userService.login(username, userType);
			if (userList != null && userList.size() > 0) {
				User user = userList.get(0);
				if (user == null) {
					log.info("登陆用户名不存在");  
		    		request.getSession().setAttribute("message", "用户名不存在，请重新登录");
		    		request.getSession().setAttribute("message", "用户名错误，请重新输入！");
		    		return returnUrl; 
				}else {
					if (!password.equals(user.getPassword())) {
						request.getSession().setAttribute("message", "密码错误，请重新输入！");
						return returnUrl;
					}
					
					/**
					 * 更新登录次数
					 */
					Integer loginFrequency = user.getLoginFrequency(); // 该用户登录的次数
					user.setLoginFrequency(loginFrequency == null ? 0 : loginFrequency++);
					user.setLastLoginTime(user.getLoginTime() == null ? new Date() : user.getLoginTime());
					user.setLoginTime(new Date());
					userService.edit(user);
					
					List<SysMenu> menuList = userService.getMenu(user.getId());
					user.setMenuList(menuList);
						
					// 保存用信息到session
					HttpSession session = request.getSession();
					session.setAttribute(Const.SESSION_USER, user); 
					session.setAttribute(Const.SESSION_USER_TYPE, userType);
					session.setAttribute("message", "");
					
					String toUrl = RequestUtil.retrieveSavedRequest();
					if (toUrl.contains("login") || toUrl.contains("logout")) {
						return "redirect:/";
					} else {
						return "redirect:" + toUrl;//跳转至访问页面
					}
						
				}
			} else {
				return returnUrl; 
			}
		}else {
			request.getSession().setAttribute("message", "验证码错误，请重新输入");
			request.getSession().setAttribute("message", "验证码错误，请重新输入！");
    		return returnUrl; 
		}
    }
    
	/**
	 * 用户注销
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session,HttpServletResponse response){
		session.removeAttribute(Const.SESSION_USER);
		UserCookieUtil.clearCookie(response);
		return "redirect:/";
	}
	
    /**
     * 测试缓存
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/get/{id}", method = RequestMethod.GET)  
    public String get(@PathVariable String id, Model model){  
        String username = userService.getUsernameById(id);  
        model.addAttribute("username", username);  
        return "getUsername";  
    } 
    
    /**
     * 获取菜单栏(easyUI Tree)
     * @param id
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenu", method = RequestMethod.POST)  
    public List<Tree> getMenu(HttpSession session){  
    	User user =  (User)session.getAttribute(Const.SESSION_USER); 
    	List<SysMenu> menuList = new ArrayList<SysMenu>();
    	
    	/**
    	 * 超级用户获取全部列表
    	 */
    	if (user.getUsername().equals("admin")) {
    		menuList = menuService.listAllMenu();
    	} else {
    		menuList = userService.getMenu(user.getId());
    	}
    	
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
