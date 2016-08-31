package com.crm.common.util.spring;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * @ClassName:		SpringUTF8StringBeanPostProcessor
 * @Description:	解决sping mvc 返回json中文乱码
 * @Author:    		kevin
 * @CreateDte:		2016年7月17日 下午5:22:17
 */
public class SpringUTF8StringBeanPostProcessor implements BeanPostProcessor {
	@Override
	public final Object postProcessAfterInitialization(final Object bean, final String beanName) {
		if (bean instanceof StringHttpMessageConverter) {
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(new MediaType("text", "plain", Charset.forName("UTF-8")));
			((StringHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		return bean;
	}

	@Override
	public final Object postProcessBeforeInitialization(final Object bean, final String beanName) {
		return bean;
	}
	
}