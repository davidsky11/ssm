package com.crm.rest.domain;

import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.ScanRecord;

/** 
 * @ClassName	ScanQuery.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月7日 下午4:09:55
 * @Version 	V1.0    
 */
public class ScanQuery extends ScanRecord {
	
	private Activity activity;
	private Award award;
	
	public ScanQuery(ScanRecord sr) {
		this.setAccountId(sr.getAccountId());
		this.setAccountName(sr.getAccountName());
		this.setId(sr.getId());
		this.setLatitude(sr.getLatitude());
		this.setLongitude(sr.getLongitude());
		this.setScanTime(sr.getScanTime());
	}

	public Activity getActivity() {
		return activity;
	}

	public Award getAward() {
		return award;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void setAward(Award award) {
		this.award = award;
	}
	
}
 