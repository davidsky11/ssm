package com.crm.common.bean;

import java.io.Serializable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件上传bean
 */
public class FileUploadBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// spring 文件上传bean
	private CommonsMultipartFile commonsMultipartFile;

	public final CommonsMultipartFile getFile() {
		return commonsMultipartFile;
	}

	public final void setFile(final CommonsMultipartFile file) {
		this.commonsMultipartFile = file;
	}

}