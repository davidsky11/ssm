package com.crm.common.bean;

/**
 * html img bean
 */
public class ImageBean {
	private String title;
	private String url;
	private Boolean newWindow;
	private String src;
	private Integer width;
	private Integer height;
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public Boolean getNewWindow() {
		return newWindow;
	}
	public String getSrc() {
		return src;
	}
	public Integer getWidth() {
		return width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setNewWindow(Boolean newWindow) {
		this.newWindow = newWindow;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
