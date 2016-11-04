<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%-- <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%> --%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		商品追踪<small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href=""><i class="fa fa-dashboard"></i>主页</a></li>
		<li class="active">商品追踪</li>
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
				<div class="form-group">
						<div class="box-body">
							<div class="row">
			                	<div class="col-xs-2">
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
				                  		<input type="text" class="form-control pull-right" id="startDate" name="startDate" value="${startDate}" placeholder="出厂时间">
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
				                	<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
				                  		<input type="text" class="form-control pull-right" id="code" name="code" value="${code}" placeholder="编码">
				                  	</div>
				                </div>
				                
				                <div class="col-xs-1">
				                	<button id="searchBtn" type="button" class="btn btn-info pull-right">条件查询</button>
				                </div>
			              	</div>
			            </div>
				 </div>
				 
				<div class="table-responsive">
					<table class="table table-hover center">
						<tr>
							<th style="width: 10px">#</th>
							<th>公共编码</th>
							<th>内部编码</th>
							<th>出厂时间</th>
							<th>经销商</th>
							<th>状态</th>
						</tr>
						<c:forEach items="${list}" var="wes" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${wes.publicCode}</td>
								<td>${wes.privateCode}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${wes.createTime}" /></td>
								<td>${wes.userName}</td>
								<td>
									<c:choose>
										<c:when test="${wes.status eq '1' }">
											未中奖
										</c:when>
										<c:when test="${wes.status eq '2' }">
											已兑奖
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- /.box-body -->
				<!-- 分页 -->
				<vino:pagination page="${page}"
					action="wares/trace" contentSelector="#content-wrapper"></vino:pagination>
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<!-- /.content -->

<script>
	//Date picker
	$('#startDate').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-mm-dd"
	});
	
	$('#endDate').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-mm-dd"
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
				endDate: $('#endDate').val(),
				code : $('#code').val()
			},
			url : "wares/trace",//请求的action路径  
			error : function() {//请求失败处理函数  
				alert('失败');
			},
			success : function(data) { //请求成功后处理函数。    
				$("#content-wrapper").html(data);//刷新content页面		
			}
		});
	});
</script>