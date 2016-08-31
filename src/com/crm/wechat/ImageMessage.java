package com.crm.wechat; 

/** 
 * @ClassName	ImageMessage.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 上午1:44:27
 * @Version 	V1.0    
 */
public class ImageMessage extends BaseMessage {

	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
	
}
 