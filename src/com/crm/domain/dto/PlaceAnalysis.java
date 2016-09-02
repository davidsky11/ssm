package com.crm.domain.dto; 

/** 
 * @ClassName	PlaceAnysis.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:01:23
 * @Version 	V1.0    
 */
public class PlaceAnalysis {
	
	private String province;
	private Long count;
	
	public String getProvince() {
		return province;
	}
	public Long getCount() {
		return count;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "PlaceAnysis [province=" + province + ", count=" + count + "]";
	}
	
}
 