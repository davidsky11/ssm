package com.crm.domain; 

/** 
 * @ClassName	Sale.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:28:32
 * @Version 	V1.0    
 */
public class Sale {
	
	private Integer id;
	private Integer year;
	private Integer month;
	private Double amount;
	
	public Integer getId() {
		return id;
	}
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return month;
	}
	public Double getAmount() {
		return amount;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Sale [id=" + id + ", year=" + year + ", month=" + month + ", amount=" + amount + "]";
	}
	
}
 