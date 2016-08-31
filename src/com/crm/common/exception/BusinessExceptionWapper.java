package com.crm.common.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

import com.crm.common.util.io.XmlUtil;

/** 
 * @ClassName	BusinessExceptionWapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午7:09:53
 * @Version 	V1.0    
 */
public class BusinessExceptionWapper {

	private static String propFileName = "exceptions.xml";
	private Map<String, BusinessException> businessExceptionsMap;

	public static BusinessExceptionWapper getBusinessExceptionWapper() {
		return BusinessExceptionWapper.Singleton.INSTANCE;
	}

	public Map<String, BusinessException> getAllExceptionInfos() {
		if (this.businessExceptionsMap == null) {
			String filePath = BusinessExceptionWapper.class.getClassLoader().getResource("").getPath() + propFileName;
			this.businessExceptionsMap = new HashMap();
			Element exceptionsElement = XmlUtil.buildFromFile(filePath).getRootElement();
			List exceptionElements = exceptionsElement.getChildren();
			if (exceptionElements != null && exceptionElements.size() > 0) {
				Iterator arg4 = exceptionElements.iterator();

				while (arg4.hasNext()) {
					Element exceptionElement = (Element) arg4.next();
					String code = exceptionElement.getChildText("code");
					BusinessException businessException = new BusinessException();
					businessException.setCode(exceptionElement.getChildText("code"));
					businessException.setType(exceptionElement.getChildText("type"));
					businessException.setDetailMsg(exceptionElement.getChildText("detailMsg"));
					businessException.setSuggestionMsg(exceptionElement.getChildText("suggestionMsg"));
					this.businessExceptionsMap.put(code, businessException);
				}
			}
		}

		return this.businessExceptionsMap;
	}

	public static BusinessException getBusinessException(String code) {
		Map businessExceptionsMap = getBusinessExceptionWapper().getAllExceptionInfos();
		BusinessException exception = (BusinessException) businessExceptionsMap.get(code);
		if (exception == null) {
			exception = new BusinessException();
		}

		return exception;
	}

	private interface Singleton {
		BusinessExceptionWapper INSTANCE = new BusinessExceptionWapper();
	}
	
}
 