package com.crm.domain;

/** 
 * @ClassName	ScanRecord.java
 * @Description 扫描记录实体
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:39:25
 * @Version 	V1.0    
 */
public class ScanRecord {

	private String id;
	private String userId;  // 前端用户ID
	private User user;
	private String userName;  // 前端用户名称
	private String userType;  // 用户类型
	private Double longitude;  // 经度
	private Double latitude;  // 纬度
	private String scanTime;  // 扫描时间
	private String country;  // 国家
	private String province;  // 省份
	private String city;  // 城市
	private String distance;  // 区
	private String street;  // 街道
	private String sematicDescription;  // 详细地址
	private String formattedAddress;  // 地址
	private String waresId;
	private Wares wares;
	private String publicCode;
	private String privateCode;
	private String insideCode;
	private String activityName;
	private Activity activity;
	
	public String getId() {
		return id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserType() {
		return userType;
	}
	public String getCountry() {
		return country;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getDistance() {
		return distance;
	}
	public String getStreet() {
		return street;
	}
	public String getSematicDescription() {
		return sematicDescription;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setSematicDescription(String sematicDescription) {
		this.sematicDescription = sematicDescription;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public String getWaresId() {
		return waresId;
	}
	public Wares getWares() {
		return wares;
	}
	public String getPublicCode() {
		return publicCode;
	}
	public String getPrivateCode() {
		return privateCode;
	}
	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}
	public void setWares(Wares wares) {
		this.wares = wares;
	}
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}
	public void setPrivateCode(String privateCode) {
		this.privateCode = privateCode;
	}
	public String getInsideCode() {
		return insideCode;
	}
	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public String toString() {
		return "ScanRecord [id=" + id + ", userId=" + userId + ", user=" + user + ", userName=" + userName
				+ ", userType=" + userType + ", longitude=" + longitude + ", latitude=" + latitude + ", scanTime="
				+ scanTime + ", country=" + country + ", province=" + province + ", city=" + city + ", distance="
				+ distance + ", street=" + street + ", sematicDescription=" + sematicDescription + ", formattedAddress="
				+ formattedAddress + ", waresId=" + waresId + ", wares=" + wares + ", publicCode=" + publicCode
				+ ", privateCode=" + privateCode + ", insideCode=" + insideCode + ", activityName=" + activityName
				+ ", activity=" + activity + "]";
	}
	
}
 