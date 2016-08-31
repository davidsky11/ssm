package com.crm.common.util.spring;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crm.common.annotation.MethodLog;


/**
 * LogHelper
 */
public class SpringAopLogHelper {
	private final Logger log = LoggerFactory.getLogger(SpringAopLogHelper.class);

	/**
	 * 无参无返回值的方法
	 */
	public void log(final JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog ann = method.getAnnotation(MethodLog.class);
		if (ann != null) {
			log.debug("--位置：{} --方法：{} --描述：{}", joinPoint.getStaticPart(), ann.name(), ann.description());
		}
	}

	/**
	 * 有参无返回值的方法
	 */
	public void logArg(final JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog ann = method.getAnnotation(MethodLog.class);
		if (ann != null) {
			log.debug("--位置：{} --方法：{} --描述：{} --参数：{}", joinPoint.getStaticPart(), ann.name(), ann.description(), args);
		}
	}

	/**
	 * 有参并有返回值的方法
	 */
	public void logArgAndReturn(final JoinPoint joinPoint, final Object returnObj) {
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog ann = method.getAnnotation(MethodLog.class);
		// if (ann != null) {
		// log.info("--位置：{} --方法：{} --描述：{} --参数：{} --返回结果：{}", joinPoint.getStaticPart(), ann.name(), ann.description(), args, returnObj);
		// }
		if (ann != null) {
			log.debug("--位置：{} --方法：{} --描述：{} --参数：{} --返回结果类型：{}", joinPoint.getStaticPart(), ann.name(), ann.description(), args, returnObj != null ? returnObj.getClass() : "null");
		}
	}
}
