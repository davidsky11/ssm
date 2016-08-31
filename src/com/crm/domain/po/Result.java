package com.crm.domain.po; 

/** 
 * @ClassName	Result.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午11:25:08
 * @Version 	V1.0    
 */
public class Result {

	private AddressComponent addressComponent;
	private String business;
	private String cityCode;
	private String formatted_address;
	private String sematic_description;
	private Location location;
	
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}
	public String getBusiness() {
		return business;
	}
	public String getCityCode() {
		return cityCode;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public String getSematic_description() {
		return sematic_description;
	}
	public Location getLocation() {
		return location;
	}
	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public void setSematic_description(String sematic_description) {
		this.sematic_description = sematic_description;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "Result [addressComponent=" + addressComponent + ", business=" + business + ", cityCode=" + cityCode
				+ ", formatted_address=" + formatted_address + ", sematic_description=" + sematic_description
				+ ", location=" + location + "]";
	}
	
}

 