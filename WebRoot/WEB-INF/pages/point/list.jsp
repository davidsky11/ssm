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
								<th style="width: 10px"><label> <input
										id="allCheck" type="checkbox" class="minimal" value="0">
								</label></th>
								<th style="width: 10px">#</th>
								<th>兑奖人</th>
								<th>兑换时间</th>
								<th>兑换积分数</th>
								<th>兑换地点</th>
								<th>操作</th>

							</tr>
							<c:forEach items="${users}" var="user" varStatus="status">
								<tr>
									<td><label><input type="checkbox"
											class="minimal deleteCheckbox" value="${user.id}"></label></td>
									<td>${status.count}</td>
									<td>${user.username}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
											value="${user.createTime}" /></td>
									<td>${user.creatorName}</td>
									<td></td>
									<td>
										<button id="detailBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"
											onclick='detailItem(${user.id})'>详情</button>
									</td>		
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
