package com.crm.rest.domain; 

/** 
 * @ClassName	Greeting.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月13日 下午11:56:50
 * @Version 	V1.0    
 */
public class Greeting {

	private Long id;
	private String content;
	
	public Greeting(Long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
 