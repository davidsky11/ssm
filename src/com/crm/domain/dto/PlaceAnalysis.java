package com.crm.domain.dto; 

/** 
 * @ClassName	PlaceAnysis.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:01:23
 * @Version 	V1.0    
 */
public class PlaceAnalysis {
	
	private String level;  // province\city\distance
	private String province;
	private String city;
	private String district;
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
	public String getLevel() {
		return level;
	}
	public String getCity() {
		return city;
	}
	public String getDistrict() {
		return district;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Override
	public String toString() {
		return "PlaceAnalysis [level=" + level + ", province=" + province + ", city=" + city + ", district=" + district
				+ ", count=" + count + "]";
	}
	
}
 