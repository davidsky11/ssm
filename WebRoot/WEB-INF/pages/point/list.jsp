<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<section class="content-header">
	<h1>
		积分管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>主页</a></li>
		<!-- <li><a href="#">用户管理</a></li> -->
		<li class="active">用户管理</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- <div class="col-md-6"> -->
		<div class="box">
			<!-- /.box-header -->
			<div class="box-body">
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">兑奖记录</h3>
					</div>

					<div class="table-responsive">
						<table class="table table-hover center">
							<tr>
								<th style="width: 10px">#</th>
								<th>兑奖人</th>
								<th>兑换时间</th>
								<th>兑换积分数</th>
								<th>兑换地点</th>

							</tr>
							<c:forEach items="${points}" var="point" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${point.user.username}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${point.exchangeTime}" /></td>
									<td>${point.exchangeAmount * 100}</td>
									<td>${ex.country} ${ex.province} ${ex.city}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<!-- /.content -->
