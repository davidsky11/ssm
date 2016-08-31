package com.crm.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Role;
import com.crm.domain.User;
import com.crm.domain.UserRole;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.RoleService;
import com.crm.service.UserService;
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
	
	@RequestMapping(value = "user/modifyCurrentUserPwd", method=RequestMethod.POST)
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
	
	@RequestMapping(value = "user/getUserRole", method=RequestMethod.GET)
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
	
	@RequestMapping(value = "user/editUserRole", method=RequestMethod.POST)
	public Json editUserRole(HttpServletRequest request,HttpServletResponse response, Model model) {
		Json j = new Json();
		
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		
		UserRole ur = this.userService.getById(id);
		ur.setRoleId(roleId);
		
		int result = this.userService.updateUserRole(ur);
		j.setSuccess(false);
		j.setMsg("用户角色设置完成");
		j.setObj(ur);
		
		return j;
	}
}
