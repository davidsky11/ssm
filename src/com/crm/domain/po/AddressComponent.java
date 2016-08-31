package com.crm.domain.po; 

/** 
 * @ClassName	AddressComponent.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午11:22:41
 * @Version 	V1.0    
 */
public class AddressComponent {

	private String adcode;
	private String city;
	private String country;
	private String country_code;
	private String direction;
	private String distance;
	private String district;
	private String province;
	private String street;
	private String street_number;
	
	public String getAdcode() {
		return adcode;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getCountry_code() {
		return country_code;
	}
	public String getDirection() {
		return direction;
	}
	public String getDistance() {
		return distance;
	}
	public String getDistrict() {
		return district;
	}
	public String getProvince() {
		return province;
	}
	public String getStreet() {
		return street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	
	@Override
	public String toString() {
		return "AddressComponent [adcode=" + adcode + ", city=" + city + ", country=" + country + ", country_code="
				+ country_code + ", direction=" + direction + ", distance=" + distance + ", district=" + district
				+ ", province=" + province + ", street=" + street + ", street_number=" + street_number + "]";
	}

}
 