package com.crm.authorization.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName	CurrentUser.java
 * @Description 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午2:37:27
 * @Version 	V1.0    
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentAccount {

	String value() default "account";
	
}
 