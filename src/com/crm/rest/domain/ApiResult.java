package com.crm.rest.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName	ApiResult.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午11:47:46
 * @Version 	V1.0    
 */
@ApiModel(value = "返回数据")
public class ApiResult {

	@ApiModelProperty(value = "操作")
	private String operate;  // 进行的操作
	
	@ApiModelProperty(value = "是否操作成功")
	private boolean success = false;  // 是否成功
	
	@ApiModelProperty(value = "状态码")
	private int code;  // 代码
	
	@ApiModelProperty(value = "回传信息")
	private String msg;  // 带回的消息
	
	@ApiModelProperty(value = "附加数据")
	private Object data;  // 附带数据
	
	public ApiResult() {}
	
	public ApiResult(String field) {
		this.operate = field;
	}
	
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
 