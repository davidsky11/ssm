package com.crm.domain; 

/** 
 * @ClassName	Sale.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:28:32
 * @Version 	V1.0    
 */
public class Sale implements Comparable<Sale> {
	
	private Integer id;
	private Integer year;
	private Integer month;
	private Double amount;
	private String activityId;
	private Activity activity;
	private String publicCode;
	
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
	public String getActivityId() {
		return activityId;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
		if (activity != null)
			this.publicCode = activity.getPublicCode();
	}
	public String getPublicCode() {
		return publicCode;
	}
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}
	
	@Override
	public String toString() {
		return "Sale [id=" + id + ", year=" + year + ", month=" + month + ", amount=" + amount + ", activityId="
				+ activityId + ", activity=" + activity + ", publicCode=" + publicCode + "]";
	}
	
	@Override
	public int compareTo(Sale o) {
		if (o == null) {
			return 1;
		}
		if (this.getYear() == null) {
			return -1;
		}
		if (o.getYear() == null) {
			return 1;
		}
		if (this.getYear() > o.getYear()) {
			return 1;
		} else if (this.getYear() < o.getYear()) {
			return -1;
		}else if (this.getYear() == o.getYear()) {
			if (this.getMonth() > o.getMonth()) {
				return 1;
			} else {
				return -1;
			}
		}
		
		return 0;
	}
	
}
 