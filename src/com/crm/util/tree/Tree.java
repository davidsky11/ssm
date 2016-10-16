package com.crm.util.tree; 

/** 
 * @ClassName	Tree.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月29日 下午10:12:21
 * @Version 	V1.0    
 */
public class Tree {

	private Integer id;
	private Integer pId;
	private String name;
	private boolean checked=false;
	private boolean open=true;
	
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
 