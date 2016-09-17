/**
 * 
 */
package com.crm.domain;

import java.util.List;

/**
 * @author zh
 * 2014-8-1
 */
public class SysMenu implements Comparable {
	
	/**
	 * 菜单ID
	 */
	private Integer id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 父级菜单ID 0表示根节点
	 */
	private Integer parentid;
	/**
	 * 菜单顺序
	 */
	private String sequence;
	/**
	 * 菜单图标样式
	 */
	private String iconCls;	
	/**
	 * 菜单链接地址
	 */
	private String url;
	/**
	 * 可用状态
	 */
	private Integer enable;
	/**
	 * 优先级
	 */
	private Integer priority;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * AdminTLE 图标
	 */
	private String iconClz;
	/**
	 * 排序
	 */
	private String orderNum;
	/**
	 * 子节点个数
	 */
	private Integer countChildrens;
	
	private SysMenu parentMenu;
	private List<SysMenu> subMenu;
	private boolean hasMenu = false;
	
	public Integer getCountChildrens() {
		return countChildrens;
	}
	public void setCountChildrens(Integer countChildrens) {
		this.countChildrens = countChildrens;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SysMenu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(SysMenu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public List<SysMenu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<SysMenu> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	public Integer getPriority() {
		return priority;
	}
	public String getType() {
		return type;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIconClz() {
		return iconClz;
	}
	public void setIconClz(String iconClz) {
		this.iconClz = iconClz;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String toTreeNode() {
		StringBuffer node = new StringBuffer();
		if (this.getUrl() != null && !this.getUrl().equals("")) {
			node.append("<li><a class='sidebarMenuHref' href='").append(this.getUrl()).append("'>");
		} else {
			node.append("<li><a class='sidebarMenuHref' href='#'>");
		}
		
		if (this.getIconClz() != null && !this.getUrl().equals("")) {
			node.append("'<i class='fa ").append(this.getIconClz()).append("'></i><span>");
		} else {
			node.append("'<i class='fa fa-circle-o'></i><span>");
		}
		
		if (this.getName() != null && !this.getName().equals("")) {
			node.append(this.getName()).append("</span></a></li>");
		} else {
			node.append("XXX</span></a></li>");
		}
		
		return node.toString();
	}
	
	@Override
	public int compareTo(Object o) {
		SysMenu menu = (SysMenu) o;
		if (this.getOrderNum() == null) {
			return -1;
		}
		if (menu.getOrderNum() == null) {
			return 1;
		}
		return this.getOrderNum().compareTo(menu.getOrderNum());
	}
	
}
