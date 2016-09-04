package com.crm.rest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.authorization.annotation.Authorization;
import com.crm.rest.domain.ApiResult;
import com.crm.rest.domain.Greeting;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * @ClassName	TestController.java
 * @Description 测试用
 * @Author		kevin 
 * @CreateTime  2016年7月13日 下午11:54:15
 * @Version 	V1.0    
 */
@RestController
@RequestMapping("/v1/test")
@Api(value = "测试", description = "测试用的API", position = 4)
public class TestController {
	
	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/greeting/{name}", method = RequestMethod.GET)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "测试", httpMethod = "GET", nickname="greeting", response = ApiResult.class, notes = "测试API")
	public ApiResult<Greeting> greeting(@ApiParam(required = true, name = "name", value = "名称") @PathVariable(value = "name") String name) {
		
		Greeting g = new Greeting(counter.getAndIncrement(), String.format(template, name));
		
		ApiResult<Greeting> result = new ApiResult<Greeting>();
		result.setCode(200);
		result.setData(g);
		
		return result;
	}

}
 