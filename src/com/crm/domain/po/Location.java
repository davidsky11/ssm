package com.crm.domain.po; 

/** 
 * @ClassName	Location.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午11:26:52
 * @Version 	V1.0    
 */
public class Location {

	private double lat;
	private double lng;
	
	public double getLat() {
		return lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return "Location [lat=" + lat + ", lng=" + lng + "]";
	}

}
 