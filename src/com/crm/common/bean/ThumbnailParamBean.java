package com.crm.common.bean;

/**
 * 缩略图参数
 */
public class ThumbnailParamBean {

	private Boolean thumbnail;
	private Integer width;
	private Integer height;
	private Float scale;

	public ThumbnailParamBean(Boolean thumbnail, Integer width, Integer height) {
		this.thumbnail = thumbnail;
		this.width = width;
		this.height = height;
	}

	public Boolean getThumbnail() {
		return (thumbnail != null && thumbnail && (width != null || height != null)) || (thumbnail != null && thumbnail && scale != null);
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Float getScale() {
		return scale;
	}

	public void setThumbnail(Boolean thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

}
