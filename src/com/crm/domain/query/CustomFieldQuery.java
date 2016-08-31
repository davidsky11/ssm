package com.crm.domain.query;

import java.io.Serializable;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义字段Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomFieldQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // 模型字段
    private Integer type; // 输入类型
    private Integer innerType; // 内部类型(1:用户自定义字段;2:系统定义字段;3:预留大文本字段;4:预留可查询字段)
    private String fieldLabel; // 字段标签
    private String fieldName; // 字段名称
    private String prompt; // 提示信息
    private String fieldDefaultValue; // 默认值
    private Integer required; // 是否必填项
    private Integer newline; // 是否独占一行
    private String regexStr; // 正则表达式
    private Integer fieldMaxLength; // 最大长度
    private Integer fieldMinLength; // 最小长度
    private Integer fieldMaxValue; // 最大值
    private Integer fieldMinValue; // 最小值
    private String dateFormat; // 日期格式
    private String checkboxOptions; // 可选项
    private Integer imgWidth; // 图片宽度
    private Integer imgHeight; // 图片高度
    private Integer imgTranscode; // 图片转码
    private String imgFormat; // 图片转码格式
    private Integer imgCompress; // 图片压缩
    private Integer imgExtrude; // 图片拉伸
    private Integer imgWatermark; // 添加水印
    private Integer videoTranscode; // 视频转码
    private String videoFormat; // 视频转码格式
    private String videoCodec; // 视频编码
    private Integer videoRate; // 视频码率
    private String ownerType; // ownerType
    private String ownerId; // ownerId
    private String valueOwnerId; //
    private Integer enabled; // 状态
	public String getId() {
		return id;
	}
	public Integer getType() {
		return type;
	}
	public Integer getInnerType() {
		return innerType;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public String getFieldName() {
		return fieldName;
	}
	public String getPrompt() {
		return prompt;
	}
	public String getFieldDefaultValue() {
		return fieldDefaultValue;
	}
	public Integer getRequired() {
		return required;
	}
	public Integer getNewline() {
		return newline;
	}
	public String getRegexStr() {
		return regexStr;
	}
	public Integer getFieldMaxLength() {
		return fieldMaxLength;
	}
	public Integer getFieldMinLength() {
		return fieldMinLength;
	}
	public Integer getFieldMaxValue() {
		return fieldMaxValue;
	}
	public Integer getFieldMinValue() {
		return fieldMinValue;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public String getCheckboxOptions() {
		return checkboxOptions;
	}
	public Integer getImgWidth() {
		return imgWidth;
	}
	public Integer getImgHeight() {
		return imgHeight;
	}
	public Integer getImgTranscode() {
		return imgTranscode;
	}
	public String getImgFormat() {
		return imgFormat;
	}
	public Integer getImgCompress() {
		return imgCompress;
	}
	public Integer getImgExtrude() {
		return imgExtrude;
	}
	public Integer getImgWatermark() {
		return imgWatermark;
	}
	public Integer getVideoTranscode() {
		return videoTranscode;
	}
	public String getVideoFormat() {
		return videoFormat;
	}
	public String getVideoCodec() {
		return videoCodec;
	}
	public Integer getVideoRate() {
		return videoRate;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public String getValueOwnerId() {
		return valueOwnerId;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setInnerType(Integer innerType) {
		this.innerType = innerType;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public void setFieldDefaultValue(String fieldDefaultValue) {
		this.fieldDefaultValue = fieldDefaultValue;
	}
	public void setRequired(Integer required) {
		this.required = required;
	}
	public void setNewline(Integer newline) {
		this.newline = newline;
	}
	public void setRegexStr(String regexStr) {
		this.regexStr = regexStr;
	}
	public void setFieldMaxLength(Integer fieldMaxLength) {
		this.fieldMaxLength = fieldMaxLength;
	}
	public void setFieldMinLength(Integer fieldMinLength) {
		this.fieldMinLength = fieldMinLength;
	}
	public void setFieldMaxValue(Integer fieldMaxValue) {
		this.fieldMaxValue = fieldMaxValue;
	}
	public void setFieldMinValue(Integer fieldMinValue) {
		this.fieldMinValue = fieldMinValue;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public void setCheckboxOptions(String checkboxOptions) {
		this.checkboxOptions = checkboxOptions;
	}
	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}
	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}
	public void setImgTranscode(Integer imgTranscode) {
		this.imgTranscode = imgTranscode;
	}
	public void setImgFormat(String imgFormat) {
		this.imgFormat = imgFormat;
	}
	public void setImgCompress(Integer imgCompress) {
		this.imgCompress = imgCompress;
	}
	public void setImgExtrude(Integer imgExtrude) {
		this.imgExtrude = imgExtrude;
	}
	public void setImgWatermark(Integer imgWatermark) {
		this.imgWatermark = imgWatermark;
	}
	public void setVideoTranscode(Integer videoTranscode) {
		this.videoTranscode = videoTranscode;
	}
	public void setVideoFormat(String videoFormat) {
		this.videoFormat = videoFormat;
	}
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}
	public void setVideoRate(Integer videoRate) {
		this.videoRate = videoRate;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public void setValueOwnerId(String valueOwnerId) {
		this.valueOwnerId = valueOwnerId;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
    
}