package com.crm.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * @ClassName SwaggerConfig.java
 * @Description Swagger-UI的配置
 * @Author kevin
 * @CreateTime 2016年7月3日 上午2:35:35
 * @Version V1.0
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				.apiInfo(apiInfo())
				.apiVersion("0.1")
				.includePatterns("/v1.*?");
//				.includePatterns(".*?")
//				.pathProvider(new GtPaths())
//				.apiVersion("0.1")
//				.swaggerGroup("swagger")
				/*.swaggerGroup("api-docs").build()*/
				// 将Timestamp类型全部转为Long类型
//				.directModelSubstitute(Timestamp.class, Long.class)
//				.build();
	}
	
	private ApiInfo apiInfo()
    {
        ApiInfo apiInfo = new ApiInfo(
                "欢乐兑API列表",
                "提供给Android和IOS测试使用",
                "My Apps API terms of service",
                "davidsky11@126.com",
                "My Apps API Licence Type",
                "My Apps API License URL");
        return apiInfo;
    }
	
}
