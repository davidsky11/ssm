<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">用户详情</h4>
			</div>
			<div class="modal-body">
				<table  class="table table-striped">
				<tr><td>用户名:</td><td>${user.username}	</td></tr>
				<tr><td>别名:</td><td>${user.userAlias}</td></tr>
				<c:if test="${userType eq '1'}">
					<tr><td>商户名:</td><td>${user.merchant}</td></tr>
					<tr><td>联系电话:</td><td>${user.telephone}</td></tr>
					<tr><td>地址:</td><td>${user.address}</td></tr>
				</c:if>
				<tr><td>上次登录时间:</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.lastLoginTime}"/></td></tr>
				<tr><td>创建者:</td><td>${user.creatorName}</td></tr>
				<tr><td>注册时间:</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.regTime}"/></td></tr>
				<tr><td>状态:</td>
					<c:choose>
						<c:when test="${user.locked eq 1}">
							<td><span class="badge bg-red">锁定</span></td>
						</c:when>
						<c:otherwise>
							<td><span class="badge bg-green">未锁定</span></td>
						</c:otherwise>
					</c:choose>
				</tr>
				</table>																										
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>			
			</div>