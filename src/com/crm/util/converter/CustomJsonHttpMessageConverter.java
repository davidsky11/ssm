package com.crm.util.converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/** 
 * @ClassName	CustomJsonHttpMessageConverter.java
 * @Description 实现HttpMessageConverter接口自定义一个Json转换器
 * @Author		kevin 
 * @CreateTime  2016年7月2日 下午11:50:45
 * @Version 	V1.0    
 */
public class CustomJsonHttpMessageConverter<T> implements HttpMessageConverter<T> {

	// Jackson的Json映射类
	private ObjectMapper mapper = new ObjectMapper();
	
	// 该转换器的支持类型：application/json
	private List<MediaType> supportedMediaTypes = Arrays.asList(MediaType.APPLICATION_JSON);

	/**
	 * 判断转换器是否可以将输入内容转换成Java类型
	 * <p>Title: canRead</p>
	 * <p>Description: </p>
	 * @param clazz		需要转换的Java类型
	 * @param mediaType 该请求的MediaType
	 * @return
	 * @see org.springframework.http.converter.HttpMessageConverter#canRead(java.lang.Class, org.springframework.http.MediaType)
	 */
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if (mediaType == null) {
			return true;
		}
		for (MediaType supportedMediaType : getSupportedMediaTypes()) {
			if (supportedMediaType.includes(mediaType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断转换器是否可以将Java类型转换成指定输出内容
	 * <p>Title: canWrite</p>
	 * <p>Description: </p>
	 * @param clazz		需要转化的Java类型
	 * @param mediaType	该请求的MediaType
	 * @return
	 * @see org.springframework.http.converter.HttpMessageConverter#canWrite(java.lang.Class, org.springframework.http.MediaType)
	 */
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if (mediaType == null || MediaType.ALL.equals(mediaType)) {
			return true;
		}
		for (MediaType supportedMediaType : getSupportedMediaTypes()) {
			if (supportedMediaType.includes(mediaType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得该转换器支持的MediaType
	 * <p>Title: getSupportedMediaTypes</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.http.converter.HttpMessageConverter#getSupportedMediaTypes()
	 */
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return supportedMediaTypes;
	}

	/**
	 * 读取请求内容，将其中的Json转换成Java对象
	 * <p>Title: read</p>
	 * <p>Description: </p>
	 * @param clazz			需要转换的Java类型
	 * @param inputMessage	请求
	 * @return
	 * @throws IOException
	 * @throws HttpMessageNotReadableException
	 * @see org.springframework.http.converter.HttpMessageConverter#read(java.lang.Class, org.springframework.http.HttpInputMessage)
	 */
	@Override
	public T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return mapper.readValue(inputMessage.getBody(), clazz);
	}

	/**
	 * 将Java对象转换成Json返回内容
	 * <p>Title: write</p>
	 * <p>Description: </p>
	 * @param t				需要转换的对象
	 * @param contentType	返回类型
	 * @param outMessage	回执对象
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 * @see org.springframework.http.converter.HttpMessageConverter#write(java.lang.Object, org.springframework.http.MediaType, org.springframework.http.HttpOutputMessage)
	 */
	@Override
	public void write(T t, MediaType contentType, HttpOutputMessage outMessage)
			throws IOException, HttpMessageNotWritableException {
		mapper.writeValue(outMessage.getBody(), t);
	}
}
 