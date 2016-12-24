<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">扫码详情</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped">
				<tr><td>扫码人:</td>
					<td>
						<c:choose>
							<c:when test="${sr.user ne null}">
								${sr.user.username}
								<c:if test="${sr.user.userAlias != null }">
								(${sr.user.userAlias})
								</c:if>
							</c:when>
							<c:otherwise>
								${currentUser.userAlias}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr><td>扫码时间：</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sr.scanTime}"/></td></tr>
				<tr><td>活动：</td><td>${sr.activity.title}</td></tr>
				
				<c:if test="${userType eq '2'}">
					<tr><td>公共编码：</td><td>${sr.publicCode}</td></tr>
					<tr><td>瓶身内码：</td><td>${sr.privateCode}</td></tr>
				</c:if>
				
				<c:if test="${userType eq '3'}">
				<tr><td>奖项：</td><td>${sr.award.title}</td></tr>
				<tr><td>登陆方式：</td>
					<td>
					<c:choose>
						<c:when test="${sr.user.thirdType eq 'WECHAT' }">
							微信
						</c:when>
						<c:when test="${sr.user.thirdType eq 'WEIBO' }">
							微博
						</c:when>
						<c:when test="${sr.user.thirdType eq 'QQ' }">
							QQ
						</c:when>
					</c:choose>
					</td>
				</tr>
				<tr><td>登陆账号：</td><td>${sr.user.userAlias}</td></tr>
				</c:if>
				
				<tr><td>地址:</td><td>${sr.formattedAddress} ${sr.sematicDescription}</td></tr>
				</table>																										
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>			
			</div>