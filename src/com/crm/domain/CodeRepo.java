package com.crm.domain; 

/** 
 * @ClassName	CodeRepo.java
 * @Description 商户扫码仓库
 * @Author		kevin 
 * @CreateTime  2016年8月6日 上午11:24:07
 * @Version 	V1.0    
 */
public class CodeRepo {
	
	private String id;  
	private String scanner;  // 扫描人ID
	private String scannerName;  // 扫描人名称
	private String scanBatch;  // 扫码批次
	private Double longitude;  // 精度
	private Double latitude;  // 纬度
	private String scanTime;  // 扫描时间
	private String waresId;  // 商品编码
	private String flagCode;  // 硬件标识码
	
	private String awardId;  // 奖项编码
	private String publicCode;  
	private String privateCode;
	private String insideCode;
	
	public String getId() {
		return id;
	}
	public String getScanner() {
		return scanner;
	}
	public String getScannerName() {
		return scannerName;
	}
	public String getScanBatch() {
		return scanBatch;
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
	public void setScanner(String scanner) {
		this.scanner = scanner;
	}
	public void setScannerName(String scannerName) {
		this.scannerName = scannerName;
	}
	public void setScanBatch(String scanBatch) {
		this.scanBatch = scanBatch;
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
	public String getFlagCode() {
		return flagCode;
	}
	public void setFlagCode(String flagCode) {
		this.flagCode = flagCode;
	}
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public String getWaresId() {
		return waresId;
	}
	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}
	public String getPublicCode() {
		return publicCode;
	}
	public String getPrivateCode() {
		return privateCode;
	}
	public String getInsideCode() {
		return insideCode;
	}
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}
	public void setPrivateCode(String privateCode) {
		this.privateCode = privateCode;
	}
	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}
	
	@Override
	public String toString() {
		return "CodeRepo [id=" + id + ", scanner=" + scanner + ", scannerName=" + scannerName + ", scanBatch="
				+ scanBatch + ", longitude=" + longitude + ", latitude=" + latitude + ", scanTime=" + scanTime
				+ ", waresId=" + waresId + ", flagCode=" + flagCode + ", awardId=" + awardId + ", publicCode="
				+ publicCode + ", privateCode=" + privateCode + ", insideCode=" + insideCode + "]";
	}
	
}
 