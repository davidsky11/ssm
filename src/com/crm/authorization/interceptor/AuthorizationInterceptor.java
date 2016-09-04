package com.crm.authorization.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.crm.authorization.annotation.Authorization;
import com.crm.authorization.model.TokenModel;
import com.crm.authorization.service.TokenService;
import com.crm.rest.domain.ApiResult;
import com.crm.util.JSONUtils;
import com.crm.util.common.Const;

/**
 * @ClassName AuthorizationInterceptor.java
 * @Description 自定义拦截器，判断此次请求是否有权限
 * @Author kevin
 * @CreateTime 2016年7月3日 上午2:39:18
 * @Version V1.0
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
//	@Qualifier("redisTokenService")
	@Qualifier("ehcacheTokenService")
	private TokenService<String, String> tokenService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 从header中得到token
		String authorization = request.getHeader(Const.AUTHORIZATION);
		// 验证token
		TokenModel model = tokenService.getToken(authorization);
		
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_AUTHORIZATION);
		if (model == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			request.setCharacterEncoding("UTF-8");  
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json;charset=UTF-8");  
			
		    result.setCode(Const.WARN_AUTHORIZATION_FAIL);
		    result.setMsg("用户授权失败，请重新登录");
		    result.setSuccess(false);
		    result.setData(authorization);
		    
		    PrintWriter out = response.getWriter();
		    out.print(JSONUtils.obj2json(result));
		    out.close();
		    	
			return false;
		}
		
		if (tokenService.checkToken(model, false)) {
			// 如果token验证成功，将token对应的用户id存在request中，便于之后注入
			tokenService.expire(model, Const.TOKEN_EXPIRES_HOUR);
			request.setAttribute(Const.CURRENT_USER_ID, model.getUserId());
//			return true;
		} else {
			if (method.getAnnotation(Authorization.class) != null) {  // 如果验证token失败，并且方法注明了Authorization，返回403错误
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				request.setCharacterEncoding("UTF-8");  
			    response.setCharacterEncoding("UTF-8");  
			    response.setContentType("application/json;charset=UTF-8");  
				
			    result.setCode(Const.WARN_AUTHORIZATION_FAIL);
			    result.setMsg("用户授权失败，请重新登录");
			    result.setSuccess(false);
			    result.setData(authorization);
			    
			    PrintWriter out = response.getWriter();
			    out.print(JSONUtils.obj2json(result));
			    out.close();
			    	
				return false;
			}
		}
		return true;
	}

}
