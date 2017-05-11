<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">活动列表</h4>
</div>
<div class="modal-body">

	<c:choose>
		<c:when test="${fn:length(atyList) <= 0}">
			抱歉，该用户暂时还没有举办活动！
		</c:when>
		<c:otherwise>
			<div class="table-responsive">
				<table class="table table-hover center">
					<tr>
						<th>活动名称</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>活动内容</th>
						<th>创建人</th>
						<th>活动编码</th>
					</tr>

					<c:forEach items="${atyList}" var="aty" varStatus="status">
						<tr>
							<td>${aty.title}</td>
							<td>${aty.startTime}</td>
							<td>${aty.endTime}</td>
							<td>${aty.content}</td>
							<td>${aty.publisherName}</td>
							<td>${aty.publicCode}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:otherwise>
	</c:choose>

</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
</div>