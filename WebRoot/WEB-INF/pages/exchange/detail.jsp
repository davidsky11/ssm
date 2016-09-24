<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">兑奖详情</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped">
				<tr><td>兑奖者:</td>
					<td>
						<c:choose>
							<c:when test="${ex.user ne null}">
								${ex.user.userAlias }
							</c:when>
							<c:otherwise>
								${currentUser.userAlias}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr><td>时间：</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ex.exchangeTime}"/></td></tr>
				<tr><td>奖品:</td>
					<td>
						<c:if test="${ex.award ne null}">
							${ex.award.title }
						</c:if>
					</td>
				</tr>
				<tr><td>兑奖类型：</td><td>${ex.exchangeType}</td></tr>
				<tr><td>受益者:</td><td>${ex.beneficiary}</td></tr>
				</table>																										
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>			
			</div>