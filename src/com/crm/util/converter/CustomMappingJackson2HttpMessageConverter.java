package com.crm.util.converter;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/** 
 * @ClassName	CustomMappingJackson2HttpMessageConverter.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:08:45
 * @Version 	V1.0    
 */
public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomMappingJackson2HttpMessageConverter.class);
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter() {
			// 重写writeInternal方法，在返回内容前首先进行加密
			@Override
			protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
				// 使用Jackson的ObjectMapper将Java对象转换成Json字符串
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(object);
				
				LOGGER.error(json);
				// 加密
				String result = json + "加密了";
				LOGGER.error(result);
				
				// 输出
				outputMessage.getBody().write(result.getBytes());
			}
		};
	}
	
	
}
 