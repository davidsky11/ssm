package com.crm.wechat; 

/** 
 * @ClassName	Video.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 上午1:46:00
 * @Version 	V1.0    
 */
public class Video {

	// 媒体文件id
	private String MediaId;
	// 缩略图的媒体id
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	
}
 