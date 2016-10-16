package com.crm.domain.system;

import java.io.Serializable;

/** 
 * @ClassName	SysDictionary.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月1日 下午5:12:40
 * @Version 	V1.0    
 */
public class SysDictionary implements Serializable, Cloneable {

	private static final long serialVersionUID = -5948732206325299221L;

	private Integer id;
	private String entrycode;
	private String entryvalue;
	private String additional;
	private String additional2;
	private String description;
	private String classname;
	private String classcode;
	private String dadistatus;
	private Long dorder;
	private String isleaf;
	private Long levelno;
	private Integer parentid;
	
	public Integer getId() {
		return id;
	}
	public String getEntrycode() {
		return entrycode;
	}
	public String getEntryvalue() {
		return entryvalue;
	}
	public String getAdditional() {
		return additional;
	}
	public String getAdditional2() {
		return additional2;
	}
	public String getDescription() {
		return description;
	}
	public String getClassname() {
		return classname;
	}
	public String getClasscode() {
		return classcode;
	}
	public String getDadistatus() {
		return dadistatus;
	}
	public Long getDorder() {
		return dorder;
	}
	public String getIsleaf() {
		return isleaf;
	}
	public Long getLevelno() {
		return levelno;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setEntrycode(String entrycode) {
		this.entrycode = entrycode;
	}
	public void setEntryvalue(String entryvalue) {
		this.entryvalue = entryvalue;
	}
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}
	public void setDadistatus(String dadistatus) {
		this.dadistatus = dadistatus;
	}
	public void setDorder(Long dorder) {
		this.dorder = dorder;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
	public void setLevelno(Long levelno) {
		this.levelno = levelno;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getParentid() {
		return parentid;
	}
	
	@Override
	public String toString() {
		return "SysDictionary [id=" + id + ", entrycode=" + entrycode + ", entryvalue=" + entryvalue + ", additional="
				+ additional + ", additional2=" + additional2 + ", description=" + description + ", classname="
				+ classname + ", classcode=" + classcode + ", dadistatus=" + dadistatus
				+ ", dorder=" + dorder + ", isleaf=" + isleaf + ", levelno=" + levelno + ", parentid=" + parentid + "]";
	}
		
}
 