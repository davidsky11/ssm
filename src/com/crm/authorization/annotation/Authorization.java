package com.crm.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName	Authorization.java
 * @Description 在Controller的方法上使用此注解，该方法在映射时会检查用户是否登录，未登录返回401错误
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午2:38:00
 * @Version 	V1.0    
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

}
 