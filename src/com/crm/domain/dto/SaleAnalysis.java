package com.crm.domain.dto; 

/** 
 * @ClassName	SaleAnalysis.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:05:24
 * @Version 	V1.0    
 */
public class SaleAnalysis {
	
	private String year;
	private String month;
	private Double amount;
	
	public String getYear() {
		return year;
	}
	public String getMonth() {
		return month;
	}
	public Double getAmount() {
		return amount;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "SaleAnalysis [year=" + year + ", month=" + month + ", amount=" + amount + "]";
	}
	
}
 