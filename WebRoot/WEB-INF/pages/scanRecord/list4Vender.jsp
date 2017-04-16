<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>

<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		扫码管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>主页</a></li>
		<li class="active">扫码管理</li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="box">
			<div class="box-body">
				<div class="box box-primary">
					<!-- <div class="box-header with-border">
						<h3 class="box-title">扫码列表</h3>
					</div> -->
					<div class="form-group">
						<div class="box-body">
							<div class="row">
			                	<div class="col-xs-3">
									<select class="form-control" id="publicCode" name="publicCode">
										<option value="">  请 选 择 活 动   </option>
										<c:forEach items="${atyList}" var="aty">
											<option value="${aty.publicCode}"
												<c:if test="${publicCode eq aty.publicCode }">selected=selected</c:if>
											>${aty.title}</option>							
										</c:forEach>
									</select>
			                	</div>
				                <div class="col-xs-3">
				                	<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
				                  		<input type="text" class="form-control pull-right" id="startDate" name="startDate" value="${startDate}" placeholder="开始时间">
				                  	</div>
				                </div>
				                <div class="col-xs-3">
				                	<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
				                  		<input type="text" class="form-control pull-right" id="endDate" name="endDate" value="${endDate}" placeholder="结束时间">
				                  	</div>
				                </div>
				                
				                <div class="col-xs-3">
				                	<button id="searchBtn" type="button" class="btn btn-info pull-right">查询</button>
				                </div>
			              	</div>
			            </div>
				 </div>
					
				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<!-- <th style="width: 10px"><label> <input id="allCheck"
									type="checkbox" class="minimal" value="0">
							</label></th> -->
							<th style="width: 10px">#</th>
							<th>扫码人</th>
							<th>扫码时间</th>
							<th>活动名称</th>
							<c:if test="${userType eq '3'}">
								<th>奖品</th>
							</c:if>
							<c:if test="${userType eq '2'}">
								<th>公共编码</th>
								<th>瓶身码</th>
							</c:if>
							<c:if test="${userType eq '1'}">
								<th>商品编码</th>
								<th>扫码地址</th>
							</c:if>
							<th>操作</th>

						</tr>
						<c:forEach items="${srs}" var="sr" varStatus="status">
							<tr>
								<%-- <td><label><input type="checkbox"
										class="minimal deleteCheckbox" value="${sr.id}"></label></td> --%>
								<td>${status.count}</td>
								<td>
									<c:if test="${sr.user != null}">
										${sr.user.username}
										<c:if test="${sr.user.userAlias != null }">
										(${sr.user.userAlias})
										</c:if>
									</c:if>
									<c:if test="${sr.user == null}">
										${sr.userName}
									</c:if>
								</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sr.scanTime}" /></td>
								<td>
									<c:if test="${sr.activity != null}">
										${sr.activity.title}
									</c:if>
								</td>
								<c:if test="${userType eq '3'}">
									<td>
										<c:if test="${sr.award != null}">
											${sr.award.title}(${sr.award.description})
										</c:if>
									</td>
								</c:if>
								<c:if test="${userType eq '2'}">
									<td>${sr.publicCode}</td>
									<td>${sr.privateCode}</td>
								</c:if>
								<c:if test="${userType eq '1'}">
									<td>${sr.privateCode}</td>
									<td>${sr.province} ${sr.city} ${sr.district} ${sr.street}</td>
								</c:if>
								<td>
									<button id="detailBtn" type="button"
										class="btn  btn-xs btn-primary btn-flat" onclick="detailItem('${sr.id}');">详情</button>
							</tr>
						</c:forEach>
					</table>
					</div>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination page="${page}"
					action="scanRecord/list4Vender" contentSelector="#content-wrapper">
				</vino:pagination>
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
				<h4 class="modal-title" id="exampleModalLabel">删除活动</h4>
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
			deleteItemsUseModal("input[class*='deleteCheckbox']", "aty/delete");
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
			data : {
				publicCode: $('#publicCode').val(),
				startDate: $('#startDate').val(),
				endDate: $('#endDate').val()
			},
			url : "scanRecord/list4Vender",//请求的action路径  
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
		modalLoadAndDisplay('aty/prepareAdd');
	}
	
	function updateItem(id){	
		modalLoadAndDisplay('aty/edit/'+id);
	}
	
	function detailItem(id){
		modalLoadAndDisplay('scanRecord/detail/'+id);
	}
	
	function uploadItem(){	
		modalLoadAndDisplay('aty/prepareUpload');
	}
	function bindItem(id){
		modalLoadAndDisplay('aty/prepareBind/'+id);
		
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
	
	// Date picker
	$('#startDate').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-mm-dd"
	})
	
	$('#endDate').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-mm-dd"
	})
</script>