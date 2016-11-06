<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%-- <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%> --%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		配置管理<small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>主页</a></li>
		<!-- <li><a href="#">用户管理</a></li> -->
		<li class="active">配置管理</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- <div class="col-md-6"> -->
		<div class="box">
			<!-- /.box-header -->
			<div class="box-body">
				<!-- /.row -->
				<div class="box box-primary">
					<!-- <div class="box-header with-border">
						<h3 class="box-title">配置列表</h3>
					</div> -->
					<!-- <div class="btn-group">
						注意，为了设置正确的内补（padding），务必在图标和文本之间添加一个空格。
						
						<button id="addBtn" type="button"
							class="btn btn-primary btn-flat margin" onclick="addItem()">
							<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
						</button>
						<button id="deleteBtn" type="button"
							class="btn btn-danger btn-flat margin">
							<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除
						</button>
							
					</div> -->
				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<!-- <th style="width: 10px"><label> <input id="allCheck"
									type="checkbox" class="minimal" value="0"> -->
							</label></th>
							<th style="width: 10px">#</th>
							<th>名称</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>内容</th>
							<th>创建人</th>
							<th>公共编码</th>
							<th>商品数量</th>
							<th>预算金额</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${atyList}" var="aty" varStatus="status">
							<tr>
								<%-- <td><label><input type="checkbox"
										class="minimal deleteCheckbox" value="${awd.id}"></label></td> --%>
								<td>${status.count}</td>
								<td>${aty.title}</td>
								<%-- <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aty.startTime}" /></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aty.endTime}" /></td> --%>
								<td>${aty.startTime}</td>
								<td>${aty.endTime}</td>
								<td>${aty.content}</td>
								<td>${aty.publisherName}</td>
								<td>${aty.publicCode}</td>
								<td>${aty.count}</td>
								<td>${aty.amount}</td>

								<td>
									<button id="updateBtn" type="button"
										class="btn btn-xs btn-primary btn-flat" onclick="updateItem('${aty.id}');">配置</button>
									<%-- <button id="detailBtn" type="button"
										class="btn  btn-xs btn-primary btn-flat" onclick="generateItem('${aty.id}');">生成编码</button> --%>
									
									<button id="bindRoleBtn" type="button"
										class="btn btn-xs btn-primary btn-flat" onclick="outputItem('${aty.id}');">导出编码</button>
								</td>
							</tr>
						</c:forEach>
					</table>
					<form id="downloadForm" action="wes/downloadCfg" method="post" >
						<input id="atyId" type="hidden" name="id">
					</form>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<%-- <vino:pagination page="${page}"
					action="wes/config" contentSelector="#content-wrapper"></vino:pagination> --%>
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
			deleteItemsUseModal("input[class*='deleteCheckbox']", "wes/delete");
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
			url : "wes/search",//请求的action路径  
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
		modalLoadAndDisplay('wes/edit/'+id);
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
	function outputItem(id){
		//modalLoadAndDisplay('wes/downloadCfg/'+id);
		$('#atyId').val(id);
		$('#downloadForm').submit();
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