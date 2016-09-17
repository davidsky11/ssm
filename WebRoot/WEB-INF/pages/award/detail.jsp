<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">奖项详情</h4>
			</div>
			<div class="modal-body">
				<table class="table table-striped">
				<tr><td>奖项名称:</td><td>${awd.title}</td></tr>
				<tr><td>活动名称:</td><td>${awd.activity.title}</td></tr>
				<tr><td>奖项金额:</td><td>${awd.amount} 元</td></tr>
				<tr><td>奖项数目:</td><td>${awd.total} 个</td></tr>
				<tr>
					<td>活动描述:</td>
					<td>${awd.description}</td>
				</tr>
				<%-- <tr><td>状态:</td>
				<c:choose>
					<c:when test="${user.locked}">
						<td><span class="badge bg-red">锁定</span></td>
					</c:when>
					<c:otherwise>
						<td><span class="badge bg-green">未锁定</span></td>
					</c:otherwise>
				</c:choose></tr> --%>
				</table>																										
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>			
			</div>