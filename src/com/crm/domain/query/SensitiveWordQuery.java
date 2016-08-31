package com.crm.domain.query;

import java.io.Serializable;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 敏感词Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SensitiveWordQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 敏感词
    private String word; // 敏感词
    private String replacement; // 替换词
    private Integer enabled; // 是否可用
	public String getId() {
		return id;
	}
	public String getWord() {
		return word;
	}
	public String getReplacement() {
		return replacement;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
    
}