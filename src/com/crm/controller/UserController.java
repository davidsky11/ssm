package com.crm.controller;

import java.util.Date;
import java.util.List;

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

import com.crm.domain.Page;
import com.crm.domain.Role;
import com.crm.domain.User;
import com.crm.domain.UserRole;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.RoleService;
import com.crm.service.UserService;
import com.crm.util.Tool;
import com.crm.util.common.Const;

/**
 * @author zh
 * 2014-8-23
 */
@Controller
public class UserController {
	
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	/**
	 * @Title:			userInfo
	 * @Description:	跳转到个人信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/userInfo", method = RequestMethod.GET)
	public User userInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Json j = new Json();
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		if (user != null) {
			j.setSuccess(true);
			j.setObj(user);
		} else {
			j.setSuccess(false);
		}
		
		return user;
	}
	
	/**
	 * @Title:			getProfile
	 * @Description:	跳转到个人信息页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/profile",method=RequestMethod.GET)
	public  String getProfile(Model model,HttpSession session){
		return "user/profile";
	}
	
	/**
	 * @Title:			modifyCurrentUserPwd
	 * @Description:	修改用户信息（密码）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/modifyCurrentUserPwd", method=RequestMethod.POST)
	public Json modifyCurrentUserPwd(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		String pass = (String) request.getParameter("password");
		String resetPass = (String) request.getParameter("resetPass");
		
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (pass.equals(resetPass)) {
					j.setSuccess(false);
					j.setMsg("重置密码不能与旧密码一样");
				} else {
					User tmp = new User();
					tmp.setId(user.getId());
					tmp.setPassword(resetPass);
					
					userService.edit(tmp);
					
					j.setSuccess(true);
					j.setMsg("重置密码成功");
				}
			} else {
				j.setSuccess(false);
				j.setMsg("密码输入正确");
			}
		} else {
			
		}
		
		return j;
	}
	
	/**
	 * 跳转到用户表格页面
	 * @return
	 */
	@RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String userList(Model model) {
		List<Role> roleList = this.roleService.getRoleList(null);
		model.addAttribute("roleList", roleList);
        return "user/list";
    }
	
	/**
	 * 跳转到用户表格页面(tree)
	 * @return
	 */
	@RequestMapping(value = "/user/listtree",method = RequestMethod.GET)
    public String userListTree(Model model) {
        return "user/list_tree";
    }
	
	/**
	 * 用户表格
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page,User user,Integer sysid) {
		DataGrid dg = new DataGrid();
		dg.setTotal(userService.getDatagridTotal(user,sysid));
		List<User> userList = userService.datagridUser(page,sysid);
		dg.setRows(userList);
		return dg;
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    public Json addUser(User user) {
		Json j = new Json();
		
		try {
            userService.add(user);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(user);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
     * 修改用户
     * 
     * @param user
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/user/editUser",method = RequestMethod.POST)
    public Json editUser(User user) {
        Json j = new Json();
        log.debug("穿过来的用户ID为："+user.getId());
        try {
            userService.edit(user);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(user);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/user/deleteUser", method = RequestMethod.POST)
	public Json deleteUser(User user) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+user.getId());
        try {
			userService.deleteUser(user.getId());
			j.setSuccess(true);
	        j.setMsg("删除成功！");
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
	}
	
	@RequestMapping(value = "/user/getUserRole", method=RequestMethod.GET)
	public Json getUserRole(HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		
		String userId = request.getParameter("userId");
		List<Role> roleList = this.userService.getRoleByUserId(userId);
		Role role = null;
		if (roleList != null && roleList.size() > 0) {
			role = roleList.get(0);
			j.setSuccess(true);
			j.setMsg(role.getId() + ":" + role.getName());
			j.setObj(role);
		} else {
			j.setSuccess(false);
			j.setMsg("该用户没有指定角色");
		}
		
		return j;
	}
	
	@RequestMapping(value = "/user/editUserRole", method=RequestMethod.POST)
	public Json editUserRole(HttpServletRequest request,HttpServletResponse response, Model model) {
		Json j = new Json();
		
		String id = request.getParameter("id");
//		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		
		UserRole ur = this.userService.getById(id);
		ur.setRoleId(roleId);
		
		int result = this.userService.updateUserRole(ur);
		if (result > 0) {
			j.setSuccess(true);
			j.setMsg("用户角色设置完成");
			j.setObj(ur);
		} else {
			j.setSuccess(false);
			j.setMsg("用户角色设置失败");
		}
		
		return j;
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 跳转到厂商列表
	 * @return
	 */
	@RequestMapping(value = "/user/venderList",method = RequestMethod.GET)
    public String venderList(Model model, @RequestParam(value="pageNumber",defaultValue="1") int pageNumber) {
		List<Role> roleList = this.roleService.getRoleList(null);
		model.addAttribute("roleList", roleList);
		
		StringBuffer conditionSql = new StringBuffer();
		conditionSql.append(" and u.userType = '1'");
		
		Page<User> page = new Page<User>();
		page.setPage(pageNumber);
		page = userService.userPages(page, conditionSql.toString());
		
		model.addAttribute("page", page);
		model.addAttribute("users", page.getContent());
		
        return "user/venderList";
    }
	
	/**
	 * 跳转到前端用户管理/经销商管理   2-经销商 / 3-前端用户
	 * @return
	 */
	@RequestMapping(value = "/user/userList/{userType}",method = RequestMethod.GET)
    public String userList(Model model, @RequestParam(value="pageNumber",defaultValue="1") int pageNumber,
    		@PathVariable("userType") String userType) {
		List<Role> roleList = this.roleService.getRoleList(null);
		model.addAttribute("roleList", roleList);
		
		StringBuffer conditionSql = new StringBuffer();
		conditionSql.append(" and u.userType = '").append(userType).append("'");
		
		Page<User> page = new Page<User>();
		page.setPage(pageNumber);
		page = userService.userPages(page, conditionSql.toString());
		
		model.addAttribute("userType", userType);
		model.addAttribute("page", page);
		model.addAttribute("users", page.getContent());
		
        return "user/userList";
    }
	
	/**
	 * @Title:			prepareAddUser
	 * @Description:	跳转到厂商新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/prepareAdd",method=RequestMethod.GET)
	public String prepareAddUser(Model model, @RequestParam("userType") String userType){
		model.addAttribute("userType", userType);
		return "user/add";
	}
	
	/**
	 * @Title:			addUser
	 * @Description:	添加厂商
	 * @param model
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	public String addUser(Model model, User user, HttpSession session,
			@RequestParam("userType") String userType){
		User curUser=(User) session.getAttribute(Const.SESSION_USER);
		
		Role role = roleService.findById("1");
		try {
			user.setCreatorId(curUser.getId());
			user.setCreatorName(curUser.getUsername());
			user.setRegTime(new Date());
			user.setUserType("1");  // 厂商
			user.setRole(role);
			user.setRoleName(role.getName());
			user.setGenerateName(user.getUsername() + "_1");
			user.setLocked(Boolean.FALSE);
//			user.setPassword("123456");  // 密码默认123456
			
			userService.add(user);
			
		} catch (Exception e) {
		}
		
		Page<User> page = new Page<User>();
		Page<User> userPage = userService.userPages(page, " and u.userType = '" + userType + "'");
		
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("page", userPage);
		
		return "user/venderList";	
	}
	
	@RequestMapping(value="/user/delete",method=RequestMethod.POST)
	public String deleteUsers(Model model, @RequestParam("deleteIds[]") String[] deleteIds,
			@RequestParam("userType") String userType){
		String ids = Tool.stringArrayToString(deleteIds, true, ",");
		
		userService.deleteUser("(" + ids + ")");
		
		Page<User> page = new Page<User>();
		Page<User> userPage = userService.userPages(page, " and u.userType = '" + userType + "'");
		
		model.addAttribute("userType", userType);
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("page", userPage);
		return "user/userList";
	}
	
	/**
	 * 用户详情
	 * @Title:			detailUsers
	 * @Description:	
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/detail/{id}",method=RequestMethod.GET)
	public String detailUsers(Model model, @PathVariable("id") String id){
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		
		return "user/detail";
	}
	
	/**
	 * 跳转用户编辑页面
	 * @Title:			editUsers
	 * @Description:	
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/edit/{id}",method=RequestMethod.GET)
	public String editUsers(Model model, @PathVariable("id") String id){
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		
		return "user/edit";
	}
	
	/**
	 * 锁定或解锁用户
	 * @Title:			deleteUsers
	 * @Description:	
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/lock", method=RequestMethod.POST)
	public String lockUser(Model model, @RequestParam("id") String id){
		User user = userService.getUserById(id);
		
		if (user.getLocked())
			user.setLocked(Boolean.FALSE);
		else 
			user.setLocked(Boolean.TRUE);
		
		boolean success = userService.edit(user) > 0;
		
		Page<User> page = new Page<User>();
		Page<User> userPage = userService.userPages(page, " and u.userType ='" + user.getUserType() + "'");
		
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("page", userPage);
		return "user/userList";
	}
	
}
