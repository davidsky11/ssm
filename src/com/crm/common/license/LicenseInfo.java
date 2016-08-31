package com.crm.common.license;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName	LicenseInfo.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午6:59:36
 * @Version 	V1.0    
 */
public class LicenseInfo implements Serializable {
	
	private static final long serialVersionUID = 3572804962789134115L;
	
	private String product;
	private String version;
	private String permissionType;
	private Date expireDate;
	private String customer;
	private String macAddress;
	private String ipAddress;
	private String digitalSignature;
	private String licenseKey;
	private String licenseCode;
	
	public String getProduct() {
		return product;
	}
	public String getVersion() {
		return version;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getCustomer() {
		return customer;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public String getDigitalSignature() {
		return digitalSignature;
	}
	public String getLicenseCode() {
		return licenseCode;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}
	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}
	public String getLicenseKey() {
		return licenseKey;
	}
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	
}
 