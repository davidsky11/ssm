<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">字典详情</h4>
			</div>
			<div class="modal-body">
				<table  class="table table-striped">
				<tr><td>资源名:</td><td>${dic.entryvalue}</td></tr>
				<tr><td>资源编码:</td><td>${dic.entrycode}</td></tr>
				<tr><td>上级资源ID:</td><td>${dic.parentid}</td></tr>
				<tr><td>状态:</td><c:choose>
					<c:when test="${dic.dadistatus}">
						<td><span class="badge bg-red">可用</span></td>
					</c:when>
					<c:otherwise>
						<td><span class="badge bg-green">不可用</span></td>
					</c:otherwise>
				</c:choose></tr>
				
				</table>																										
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>			
			</div>