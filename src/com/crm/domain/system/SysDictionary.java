package com.crm.domain.system; 

/** 
 * @ClassName	SysDictionary.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月1日 下午5:12:40
 * @Version 	V1.0    
 */
public class SysDictionary implements java.io.Serializable {

	private static final long serialVersionUID = -5948732206325299221L;

	private Long id;
	private String entrycode;
	private String entryvalue;
	private String additional;
	private String additional2;
	private String description;
	private String classname;
	private String classcode;
	private String classnameab;
	private String dadistatus;
	private Long dorder;
	private String isleaf;
	private Long levelno;
	private String pGuid;
	
	public Long getId() {
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
	public String getClassnameab() {
		return classnameab;
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
	public String getpGuid() {
		return pGuid;
	}
	public void setId(Long id) {
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
	public void setClassnameab(String classnameab) {
		this.classnameab = classnameab;
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
	public void setpGuid(String pGuid) {
		this.pGuid = pGuid;
	}
	
}
 