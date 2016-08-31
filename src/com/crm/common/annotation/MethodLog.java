package com.crm.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:		MethodLog
 * @Description:	方法级别的日志
 * @Author:    		kevin
 * @CreateDte:		2016年7月17日 下午5:25:02
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

	/** * 方法中文名 */
	String name() default "";

	/** * 方法描述 */
	String description() default "";
	
}
