package com.crm.authorization.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.crm.authorization.annotation.CurrentAccount;
import com.crm.dao.mybatis.UserMapper;
import com.crm.domain.Account;
import com.crm.util.common.Const;

/** 
 * @ClassName	CurrentUserMethodArgumentResolver.java
 * @Description 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午3:05:07
 * @Version 	V1.0    
 */
@Component
public class CurrentAccountMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//如果参数类型是Account并且有CurrentUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(Account.class) &&
                parameter.hasParameterAnnotation(CurrentAccount.class)) {
            return true;
        }
        return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		//取出鉴权时存入的登录用户Id
        String currentUserId = (String) webRequest.getAttribute(Const.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
		
		if (currentUserId != null) {
            //从数据库中查询并返回
            return userMapper.findByConditionSql(" AND ID = '" + currentUserId + "'");
        }
        throw new MissingServletRequestPartException(Const.CURRENT_USER_ID);
	}

}
 