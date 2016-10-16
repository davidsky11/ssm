<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%-- <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%> --%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		奖项管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>主页</a></li>
		<!-- <li><a href="#">用户管理</a></li> -->
		<li class="active">奖项管理</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- <div class="col-md-6"> -->
		<div class="box">
			<!-- /.box-header -->
			<div class="box-body">
				<%-- <div class="row">
					<div class="col-md-12">
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title">数据查询</h3>
							</div>
							<div class="box-body">
								<!-- form start -->
								<form id="searchForm" action="user/search" method="get">
									<div class="box-body">
										<div class="row">
											<input hidden="true" name="pageNumber" id="pageNumber">
											<div class="form-group col-md-2">
												<label for="atyTitleLabel">活动名称:</label> 
												<select class="form-control" name="search_atyId">
													<c:forEach items="${atyList}" var="aty" >
														<option value="${aty.id}">${aty.title}</option>
													</c:forEach>
												</select>
											</div>
											<div class="form-group col-md-2">
												<label for="awardNameLabel">奖项名称:</label> 
												<input type="text" class="form-control" id="awardNameLabel"
													name="search_awardName" value="${searchParamsMap.awardName}">
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
				</div> --%>
				<!-- /.row -->
				<div class="box box-primary">
					<!-- <div class="box-header with-border">
						<h3 class="box-title">奖项列表</h3>
					</div> -->
					<div class="btn-group">
						<!-- 注意，为了设置正确的内补（padding），务必在图标和文本之间添加一个空格。 -->
						
						<%-- <shiro:hasPermission name="user:create"> --%>
							<button id="addBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="addItem()">
								<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
							</button>
						<%-- </shiro:hasPermission>
						<shiro:hasPermission name="user:delete"> --%>
							<button id="deleteBtn" type="button"
								class="btn  btn-danger btn-flat margin">
								<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
							</button>
						<%-- </shiro:hasPermission>
						<shiro:hasPermission name="user:upload"> --%>
							<!-- <button id="uploadBtn" type="button"
								class="btn  btn-primary btn-flat margin" onclick="uploadItem()">
								<span class="fa fa-fw fa-cloud-upload" aria-hidden="true"></span> 上传
							</button> -->
							<%-- </shiro:hasPermission> --%>
							<%-- <shiro:hasPermission name="user:download"> --%>
							<!-- <form id="downloadForm" action="user/download" method="get" >
							<button id="downloadBtn" type="submit"
								class="btn  btn-primary btn-flat margin2" 
								 onclick="downloadItem()">
								<span class="fa fa-fw fa-cloud-download" aria-hidden="true"></span> 下载
							</button>
							<input id="downloadIds" type="hidden" name="downloadIds[]">
							</form> -->
							<%-- </shiro:hasPermission> --%>
							
					</div>
				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<th style="width: 10px"><label> <input id="allCheck"
									type="checkbox" class="minimal" value="0">
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
											class="btn btn-xs btn-primary btn-flat" onclick="updateItem('${awd.id}');">编辑</button>
									<%-- </shiro:hasPermission> <shiro:hasPermission name="user:view"> --%>
										<button id="detailBtn" type="button"
											class="btn  btn-xs btn-primary btn-flat" onclick="detailItem('${awd.id}');">详情</button>
									<%-- </shiro:hasPermission> <shiro:hasPermission name="user:bind"> --%>
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
				<vino:pagination page="${page}"
					action="awd/list" contentSelector="#content-wrapper"></vino:pagination>
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
		<div class="modal-content">
			
		</div>
	</div>
</div>
<!-- ./新增页面 modal框 -->
<!-- 删除确认页面 modal框 -->
<div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel">
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
	//删除确认modal事件处理
	$('#deleteConfirmModal').on('shown.bs.modal', function(event) {
		$('#deleteConfirmBtn').click(function(){
			deleteItemsUseModal("input[class*='deleteCheckbox']", "awd/delete");
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
			type : 'GET',
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
	
	function modalLoadAndDisplay(url){	
		$('#modal .modal-content').load(url,function(){
			$("#modal").modal();
		});		
	}
	function addItem(){
		modalLoadAndDisplay('awd/prepareAdd');
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('awd/edit/'+id);
	}
	
	function detailItem(id){
		modalLoadAndDisplay('awd/detail/'+id);
	}
	
	function uploadItem(){	
		modalLoadAndDisplay('awd/prepareUpload');
	}
	function bindItem(id){
		modalLoadAndDisplay('awd/prepareBind/'+id);
		
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