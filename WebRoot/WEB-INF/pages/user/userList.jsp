<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		<c:if test="${userType eq '1'}">
			厂商管理
		</c:if>
		<c:if test="${userType eq '2'}">
			经销商管理
		</c:if>
		<c:if test="${userType eq '3'}">
			前端用户管理
		</c:if>
		<small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>系统管理</a></li>
		<li class="active"><c:if test="${userType eq '1'}">
				厂商管理
			</c:if> <c:if test="${userType eq '2'}">
				经销商管理
			</c:if> <c:if test="${userType eq '3'}">
				前端用户管理
			</c:if></li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="box">
			<div class="box-body">

				<div class="box box-primary">
					<div class="form-group">
						<div class="box-body">
							<div class="row">

							<form id="searchForm">
								<input id="userType" type="hidden" name="userType" value="${userType}">
								<c:choose>
									<c:when test="${userType eq '1'}">
										<div class="col-xs-1">
											<button id="addBtn" type="button"
												class="btn btn-primary btn-flat"
												onclick="addItem(${userType})">
												<span class="fa fa-fw  fa-plus" aria-hidden="true"></span>
												新增
											</button>
										</div>
										<div class="col-xs-1">
											<button id="deleteBtn" type="button"
												class="btn  btn-danger btn-flat">
												<span class="fa fa-fw fa-remove" aria-hidden="true"></span>
												删除
											</button>
										</div>
										<div class="col-xs-6"></div>

										<div class="col-xs-4">
											<div style="float: left;">
												<input type="text" class="form-control pull-right"
													style="margin-right: auto;" id="userInfo" name="userInfo"
													value="${userInfo}" placeholder="用户信息">
											</div>
											<div style="float: right;">
												<button id="searchBtn" type="button"
													class="btn btn-info btn-flat pull-right">
													<span class="fa fa-fw fa-search" aria-hidden="true"></span>查询
												</button>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-xs-4">
											<input type="text" class="form-control pull-right"
												style="margin-right: auto;" id="userInfo" name="userInfo"
												value="${userInfo}" placeholder="用户信息">
										</div>
										<div class="col-xs-6"></div>

										<div class="col-xs-2" style="position: relative;">
											<div style="float: right;">
												<button id="searchBtn" type="button"
													class="btn btn-info btn-flat pull-right">
													<span class="fa fa-fw fa-search" aria-hidden="true"></span>查询
												</button>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</form>
							</div>
						</div>
					</div>
				</div>

				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<c:if test="${userType eq '1'}">
								<th style="width: 10px"><label> <input
										id="allCheck" type="checkbox" class="minimal" value="0"></label>
								</th>
							</c:if>
							<th style="width: 10px">#</th>
							<th>用户名</th>
							<th>别名</th>
							<c:if test="${userType eq '1'}">
								<th>商户名</th>
								<th>联系电话</th>
							</c:if>
							<th>创建时间</th>
							<th>创建人</th>
							<c:if test="${userType eq '2' or userType eq '3'}">
								<th>最近登录</th>
							</c:if>
							<th>状态</th>
							<th>操作</th>

						</tr>
						<c:forEach items="${users}" var="user" varStatus="status">
							<tr>
								<c:if test="${userType eq '1'}">
									<td><label><input type="checkbox"
											class="minimal deleteCheckbox" value="${user.id}"></label></td>
								</c:if>
								<td>${status.count}</td>
								<td>${user.username}</td>
								<td>${user.userAlias}</td>

								<c:if test="${userType eq '1'}">
									<td>${user.merchant}</td>
									<td>${user.telephone}</td>
								</c:if>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
										value="${user.regTime}" /></td>
								<td>${user.creatorName}</td>
								<c:if test="${userType eq '2' or userType eq '3'}">
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
											value="${user.lastLoginTime}" /></td>
								</c:if>
								<c:choose>
									<c:when test="${user.locked eq 1}">
										<td><span class="badge bg-red">锁定</span></td>
									</c:when>
									<c:otherwise>
										<td><span class="badge bg-green">未锁定</span></td>
									</c:otherwise>
								</c:choose>

								<td>
									<button id="updateBtn" type="button"
										class="btn btn-xs btn-primary "
										onclick="updateItem('${user.id}')">编辑</button>
									<button id="detailBtn" type="button"
										class="btn btn-xs btn-primary "
										onclick="detailItem('${user.id}')">详情</button> <c:choose>
										<c:when test="${user.locked eq 1}">
											<button id="lockBtn" type="button"
												class="btn btn-xs btn-warning"
												onclick="lockItem('${user.id}', 1)">解锁</button>
										</c:when>
										<c:otherwise>
											<button id="lockBtn" type="button"
												class="btn btn-xs btn-primary"
												onclick="lockItem('${user.id}', 0)">锁定</button>
										</c:otherwise>
									</c:choose> <c:if test="${user.userType eq 1}">
										<button id="atyBtn" type="button"
											class="btn btn-xs btn-primary"
											onclick="atyDetail('${user.id}')">活动列表</button>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<!-- /.box-body -->
			<!-- 分页 -->
			<vino:pagination page="${page}" action="user/userList/${userType}"
				contentSelector="#content-wrapper"></vino:pagination>
		</div>
		<!-- /.box -->
	</div>
	</div>
</section>
<!-- /.content -->

<!-- 新增页面 modal框 -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content"></div>
	</div>
</div>
<!-- ./新增页面 modal框 -->
<!-- 删除确认页面 modal框 -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">删除用户</h4>
			</div>
			<div class="modal-body">
				<div>确定要删除吗？</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="deleteConfirmBtn">提交</button>
			</div>
		</div>
	</div>
</div>

<!-- ./锁定/解锁页面 modal框 -->
<!-- 锁定/解锁确认页面 modal框 -->
<div class="modal fade" id="lockConfirmModal" tabindex="-2"
	role="dialog" aria-labelledby="lockModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="lockedTitle" class="modal-title" id="lockModalLabel">
					<%-- <c:choose>
						<c:when test="${user.locked eq 1}">
							解锁用户
						</c:when>
						<c:otherwise>
							锁定用户
						</c:otherwise>
					</c:choose> --%>
				</h4>
			</div>
			<div class="modal-body">
				<div>
					<h5 id="confirmTitle"></h5>
					<%-- <c:choose>
						<c:when test="${user.locked eq 1}">
							确定要解锁用户吗？
						</c:when>
						<c:otherwise>
							确定要锁定用户吗？
						</c:otherwise>
					</c:choose> --%>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="lockConfirmBtn">提交</button>
			</div>
		</div>
	</div>
</div>
<script>

	//Date range picker
	$('#reservation').daterangepicker();
	//Date range picker with time picker
	$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});	
	/* icheck 初始化 详情：https://github.com/fronteed/icheck */
   	iCheckInit();
 	/* iCheck事件监听 详情：https://github.com/fronteed/icheck */
 	/* 全选和取消全选 */
	$(document).ready(function(){
		$('#allCheck').on('ifToggled', function(event){		
			$('input[class*="deleteCheckbox"]').iCheck('toggle');			
		});		
	});
	
	var userType = $('#userType').val();
	//删除确认modal事件处理
	$('#deleteConfirmModal').on('shown.bs.modal', function(event) {
		$('#deleteConfirmBtn').click(function(){
			deleteItemsUseModal("input[class*='deleteCheckbox']", "user/delete?userType="+userType);
		});
	});
	
	var userId;
	//锁定/解锁确认modal事件处理
	$('#lockConfirmModal').on('shown.bs.modal', function(event) {
		$('#lockConfirmBtn').click(function(){
			lockItemsUseModal("user/lock?id="+userId);
		});
	});
	/* button监听事件 */
	$(document).ready(function(){
		$("#deleteBtn").click(function(){
			$("#deleteConfirmModal").modal();	
		});
	});	
	$("#searchBtn").click(function() {
		$('#pageNumber').val(1);
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : $("#searchForm").serialize(),		 
			url : "user/userList",//请求的action路径  
			error : function() {//请求失败处理函数  
				alert('失败');
			},
			success : function(data) { //请求成功后处理函数。    
				$("#content-wrapper").html(data);//刷新content页面		
			}
		});
	});
	
	function modalLoadAndDisplay(url){	
		$('#modal .modal-content').load(url,function(){
			$("#modal").modal();
		});		
	}
	function addItem(userType){
		modalLoadAndDisplay('user/prepareAdd?userType='+userType);
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('user/edit/'+id);
	}
	
	function detailItem(id){
		modalLoadAndDisplay('user/detail/'+id);
	}
	
	function atyDetail(id){
		modalLoadAndDisplay('user/atyDetail/'+id);
	}
	
	function uploadItem(){	
		modalLoadAndDisplay('user/prepareUpload');
	}
	function bindItem(id){
		modalLoadAndDisplay('user/prepareBind/'+id);
	}
	function lockItem(id, locked){
		userId = id;
		if (locked == 1) {
			$("#confirmTitle").text("确定要解锁用户吗？");
			$("#lockedTitle").text("解锁用户");
		} else {
			$("#confirmTitle").text("确定要锁定用户吗？");
			$("#lockedTitle").text("锁定用户");
		}
		$("#lockConfirmModal").modal();	
	}
	
	/**
	AJAX不能下载文件，用表单来实现
	*/
	function downloadItem(){	
		var downloadIds = [];
		var i = 0;
		$("input[class*='deleteCheckbox']").each(function(index, item) {
			var isChecked = item.checked;
			if (isChecked == true) {
				downloadIds[i++] = item.value;
			}
		});
		$('#downloadIds').val(downloadIds)
		$('#downloadForm').submit(function(){
			
		});
	}
	
</script>