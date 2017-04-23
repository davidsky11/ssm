<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%-- <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%> --%>
<!-- Content Header (Page header) -->
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
				<div class="row">
					<div class="col-md-12">
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">兑换记录</h3>
							</div>
							<div class="box-body">
								<!-- form start -->
								<form id="searchForm" action="user/search" method="get">
									<div class="box-body">
										<div class="row">
											<input hidden="true" name="pageNumber" id="pageNumber">
											<div class="form-group col-md-2">
												<label for="usernameLabel">用户名:</label> <input type="text"
													class="form-control" id="usernameLabel"
													name="search_username" value="${searchParamsMap.username }">
											</div>
											<div class="form-group col-md-2">
												<label for="aliasLabel">别名:</label> <input type="text"
													class="form-control" id="aliasLabel"
													name="search_userAlias"
													value="${searchParamsMap.userAlias }">
											</div>
											<!-- Date range -->
											<div class="form-group  col-md-4">
												<label>创建时间:</label>
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="reservation" name="search_createTimeRange"
														value="${searchParamsMap.createTimeRange}">
												</div>
												<!-- /.input group -->
											</div>
											<!-- <div class="form-group col-md-2">
												<label for="isLockedLabel" >是否锁定: </label><br>
												<input id="isLockedLabel" type="checkbox" name="search_locked">
											</div> -->

											<!-- /.form group -->
										</div>
										<!-- other rows -->
									</div>
									<!-- /.box-body -->
									<div class="box-footer">
										<button id="searchBtn" type="submit"
											class="btn btn-info pull-right">查询</button>
									</div>
									<!-- /.box-footer -->
								</form>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col (right) -->
				</div>
				<!-- /.row -->
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">用户列表</h3>
					</div>

					<div class="table-responsive">
						<table class="table table-hover center">
							<tr>
								<th style="width: 10px"><label> <input
										id="allCheck" type="checkbox" class="minimal" value="0">
								</label></th>
								<th style="width: 10px">#</th>
								<th>用户名</th>
								<th>别名</th>
								<th>角色</th>
								<th>创建时间</th>
								<th>创建人</th>
								<th>状态</th>
								<th>操作</th>

							</tr>
							<c:forEach items="${users}" var="user" varStatus="status">
								<tr>
									<td><label><input type="checkbox"
											class="minimal deleteCheckbox" value="${user.id}"></label></td>
									<td>${status.count}</td>
									<td>${user.username}</td>
									<td>${user.userAlias}</td>
									<td><c:forEach var="role" items="${user.roles}">${role.name}&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
											value="${user.createTime}" /></td>
									<td>${user.creatorName}</td>
									<c:choose>
										<c:when test="${user.locked}">
											<td><span class="badge bg-red">锁定</span></td>
										</c:when>
										<c:otherwise>
											<td><span class="badge bg-green">未锁定</span></td>
										</c:otherwise>
									</c:choose>

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
				<!-- 分页 -->
				<vino:pagination paginationSize="${page.rows}" page="${page}"
					action="user/search" contentSelector="#content-wrapper"></vino:pagination>
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<!-- /.content -->
