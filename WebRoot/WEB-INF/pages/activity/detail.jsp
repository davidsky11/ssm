<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">活动详情</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped">
				<tr><td>活动名称:</td><td>${aty.title}</td></tr>
				<tr><td>活动编码:</td><td>${aty.publicCode}</td></tr>
				<tr><td>公共编码:</td><td>${aty.atyCode}</td></tr>
				<%-- <tr><td>开始时间：</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aty.startTime}"/></td></tr>
				<tr><td>结束时间：</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aty.endTime}"/></td></tr> --%>
				<tr><td>开始时间：</td><td>${aty.startTime}</td></tr>
				<tr><td>结束时间：</td><td>${aty.endTime}</td></tr>
				<tr><td>活动内容:</td><td>${aty.content}</td></tr>
				<tr><td>活动描述:</td><td>${aty.description}</td></tr>
				<tr><td>活动海报:</td><td>
					<c:choose>
						<c:when test="${aty.infoUrl eq null or aty.infoUrl eq '' }">
							暂缺
						</c:when>
						<c:when test="${aty.infoUrl ne null and aty.infoUrl ne '' }">
							<a target="_blank" href="${aty.infoUrl}"> 浏 览 海 报 </a>
						</c:when>
					</c:choose>
				</tr>
				</table>																										
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>			
			</div>