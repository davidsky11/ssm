package com.crm.common.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.crm.domain.SysMenu;
import com.crm.domain.User;
import com.crm.service.UserService;
import com.crm.util.common.Const;

/** 
 * @ClassName	SidebarRankTag.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月14日 下午3:31:07
 * @Version 	V1.0    
 */
public class SidebarRankTag extends SimpleTagSupport {

	private User currentUser;
	
	@Override
	public void doTag() throws JspException, IOException {
		List<SysMenu> menuResources = currentUser.getMenuList();
		
		System.out.println(menuResources.size());
		
		// 构造顶层树  目前只支持两层，去重
		Set<SysMenu> rootMenus = new TreeSet<SysMenu>();
		for (SysMenu menu : menuResources) {
			if (menu.getParentid() == Const.ROOT_TREE_ID) {
				rootMenus.add(menu);
			}
			
			List<SysMenu> childrens = new ArrayList<SysMenu>();
			for (SysMenu m : menuResources) {
				if (m.getParentid() == menu.getId()) {
					childrens.add(m);
				}
			}
			Collections.sort(childrens);
			menu.setSubMenu(childrens);
		}
		
		/*Collections.sort(rootMenus, new Comparator<SysMenu>() {
			@Override
			public int compare(SysMenu o1, SysMenu o2) {
				return o1.getOrderNum().compareTo(o2.getOrderNum());
			}
		});*/
		
		JspWriter out= getJspContext().getOut();
		StringBuffer node = new StringBuffer();
		for(SysMenu root : rootMenus){
			if(root.getUrl().equals("druid")) {
				out.println("<li><a  href='"+root.getUrl()+"' target='_blank'><i class='fa fa-laptop'></i><span>"+root.getName()+"</span></a></li>");
			} else {
				
				/**
				 * 编辑根节点html
				 */
				node.append("<li class='treeview'><a href='#'>");
				
				if (root.getIconClz() != null && !root.getIconClz().equals("")) {
					node.append("<i class='fa ").append(root.getIconClz()).append("'></i>");
				} else {
					node.append("<i class='fa fa-circle-o'></i>");
				}
				
				if (root.getName() != null && !root.getName().equals("")) {
					node.append("<span>").append(root.getName()).append("</span>");
				} else {
					node.append("<span> X X X </span>");
				}
				
				node.append("<span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a>");
				
				/**
				 * 编辑子节点html
				 */
				if (root.getSubMenu() !=null && root.getSubMenu().size()>0) {
					node.append("<ul class='treeview-menu'>");
					
					for (SysMenu res : root.getSubMenu()) {
						if (res.getUrl() != null && !res.getUrl().equals("")) {
							node.append("<li><a class='sidebarMenuHref' href='").append(res.getUrl()).append("'>");
						} else {
							node.append("<li><a class='sidebarMenuHref' href='#'>");
						}
						
						if (res.getIconClz() != null && !res.getUrl().equals("")) {
							node.append("<i class='fa ").append(res.getIconClz()).append("'></i><span>");
						} else {
							node.append("<i class='fa fa-circle-o'></i><span>");
						}
						
						if (res.getName() != null && !res.getName().equals("")) {
							node.append(res.getName()).append("</span></a></li>");
						} else {
							node.append("XXX</span></a></li>");
						}
					}
					
					node.append("</ul></li>");
				}
				
				out.println(node.toString());
				node.setLength(0); // 情况node
				
				//out.println("<li><a class='sidebarMenuHref' href='"+res.getUrl()+"'><i class='fa  fa-circle-o'></i><span>"+res.getName()+"</span></a></li>");	
			}
		}
		
		out.flush();
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
}
 