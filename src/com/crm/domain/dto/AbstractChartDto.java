package com.crm.domain.dto; 

/** 
 * @ClassName	AbstractChartDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月20日 下午11:17:25
 * @Version 	V1.0    
 */
public class AbstractChartDto {

	private String category;
	private String bar;
	
	public AbstractChartDto() {}
	
	public AbstractChartDto(String bar, String category) {
		this.category = category;
		this.bar = bar;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBar() {
		return bar;
	}
	public void setBar(String bar) {
		this.bar = bar;
	}
	
	@Override
	public String toString() {
		return "AbstractChartDto [category=" + category + ", bar=" + bar + "]";
	}
	
}
 