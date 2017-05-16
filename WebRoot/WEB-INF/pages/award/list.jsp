<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/AdminLTE/plugins/select2/select2.min.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/pinyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/AdminLTE/plugins/select2/select2.full.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/AdminLTE/plugins/select2/i18n/zh-CN.js"></script>
	
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		奖项管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>主页</a></li>
		<li class="active">奖项管理</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="box">
			<div class="box-body">
			
				<div class="box box-primary">
					<form id="searchForm">
					<div class="form-group">
						<div class="box-body">
							<div class="row">
								<div class="col-xs-1">
									<button id="addBtn" type="button"
										class="btn btn-primary btn-flat" onclick="addItem()">
										<span class="fa fa-fw fa-plus" aria-hidden="true"></span> 新增
									</button>
								</div>
								<div class="col-xs-1">
									<button id="deleteBtn" type="button"
										class="btn  btn-danger btn-flat">
										<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
									</button>
								</div>
								<div class="col-xs-6"></div>

								<div class="col-xs-4" style="position: relative;">
									<div style="float: left;">
										<select id="atyId" name="atyId" class="select2">
											<option value="">请选择</option>
											<c:forEach items="${atyList}" var="aty">
												<option value="${aty.id}">${aty.title}</option>
											</c:forEach>
										</select> 
									</div>
									<div style="float: right;">
										<button id="searchBtn" type="button"
											class="btn btn-info btn-flat pull-right">
											<span class="fa fa-fw fa-search" aria-hidden="true"></span>查询
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					</form>
					
					<div class="table-responsive">
						<table class="table table-hover center">
							<tr>
								<th style="width: 10px"><label> <input
										id="allCheck" type="checkbox" class="minimal" value="0">
								</label></th>
								<th style="width: 10px">#</th>
								<th>活动名称</th>
								<th>奖项名称</th>
								<th>奖项金额</th>
								<th>总数</th>
								<th>剩余</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${awds}" var="awd" varStatus="status">
								<tr>
									<td><label><input type="checkbox"
											class="minimal deleteCheckbox" value="${awd.id}"></label></td>
									<td>${status.count}</td>
									<td>${awd.activity.title}</td>
									<td>${awd.title}</td>
									<td>${awd.amount}</td>
									<td>${awd.total}</td>
									<td>${awd.remain}</td>

									<td>
										<%-- <shiro:hasPermission name="user:update"> --%>
										<button id="updateBtn" type="button"
											class="btn btn-xs btn-primary btn-flat"
											onclick="updateItem('${awd.id}');">编辑</button> <%-- </shiro:hasPermission> <shiro:hasPermission name="user:view"> --%>
										<button id="detailBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"
											onclick="detailItem('${awd.id}');">详情</button> <%-- </shiro:hasPermission> <shiro:hasPermission name="user:bind"> --%>
										<%-- <button id="bindRoleBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat"  onclick='bindItem(${user.id})'>角色绑定</button> --%>
										<%-- </shiro:hasPermission></td> --%>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination page="${page}" action="awd/list"
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
				<h4 class="modal-title" id="exampleModalLabel">删除奖项</h4>
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
<script>
	$('#atyId').select2({
		width: '236px',
		placeholder: {
			id: "",
			text: '键入查询'
		},
		allowClear: true,
		language: "zh-CN",
		multiple: false
	});

	//Date range picker
	$('#reservation').daterangepicker();
	//Date range picker with time picker
	$('#reservationtime').daterangepicker({
		timePicker : true,
		timePickerIncrement : 30,
		format : 'MM/DD/YYYY h:mm A'
	});
	/* icheck 初始化 详情：https://github.com/fronteed/icheck */
	iCheckInit();
	/* iCheck事件监听 详情：https://github.com/fronteed/icheck */
	/* 全选和取消全选 */
	$(document).ready(function() {
		$('#allCheck').on('ifToggled', function(event) {
			$('input[class*="deleteCheckbox"]').iCheck('toggle');
		});
	});
	//删除确认modal事件处理
	$('#deleteConfirmModal').on(
			'shown.bs.modal',
			function(event) {
				$('#deleteConfirmBtn').click(
						function() {
							deleteItemsUseModal(
									"input[class*='deleteCheckbox']",
									"awd/delete");
						});
			});
	/* button监听事件 */
	$(document).ready(function() {
		$("#deleteBtn").click(function() {
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
			url : "awd/search",//请求的action路径  
			error : function() {//请求失败处理函数  
				alert('失败');
			},
			success : function(data) { //请求成功后处理函数。    
				$("#content-wrapper").html(data);//刷新content页面		
			}
		});
	});

	function modalLoadAndDisplay(url) {
		$('#modal .modal-content').load(url, function() {
			$("#modal").modal();
		});
	}
	function addItem() {
		modalLoadAndDisplay('awd/prepareAdd');
	}

	function updateItem(id) {
		modalLoadAndDisplay('awd/edit/' + id);
	}

	function detailItem(id) {
		modalLoadAndDisplay('awd/detail/' + id);
	}

	function uploadItem() {
		modalLoadAndDisplay('awd/prepareUpload');
	}
	function bindItem(id) {
		modalLoadAndDisplay('awd/prepareBind/' + id);

	}

	/**
	AJAX不能下载文件，用表单来实现
	 */
	function downloadItem() {
		var downloadIds = [];
		var i = 0;
		$("input[class*='deleteCheckbox']").each(function(index, item) {
			var isChecked = item.checked;
			if (isChecked == true) {
				downloadIds[i++] = item.value;
			}
		});
		$('#downloadIds').val(downloadIds)
		$('#downloadForm').submit(function() {

		});
	}
</script>