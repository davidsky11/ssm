package com.crm.domain.dto;

import com.crm.domain.Sale;

/** 
 * @ClassName	SaleDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月9日 下午11:36:22
 * @Version 	V1.0    
 */
public class SaleDto extends Sale {

	private String publicCode;

	public String getPublicCode() {
		return publicCode;
	}

	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}

	@Override
	public String toString() {
		return "SaleDto [publicCode=" + publicCode + "]";
	}
	
}
 