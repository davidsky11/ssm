<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title" id="exampleModalLabel">编辑配置</h4>
	</div>
	<form id="updateForm" action="wes/addCfg" method="post">
		<div class="modal-body">
			<input name="id" value="${aty.id}" hidden="true"/>
			<div class="form-group">
				<label for="title" class="control-label">活动名称:</label> 
				<input type="text" class="form-control" id="title" name="title" value="${aty.title}" disabled>
			</div>	
			<div class="form-group">
				<label for="publicCode" class="control-label">公共编码:</label> 
				<input type="text" class="form-control" id="publicCode" name="publicCode" value="${aty.publicCode}" disabled>
			</div>		
			<%-- <div class="form-group">
				<label for="startTime" class="control-label">开始时间:</label> 
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aty.startTime}"  />
			</div>
			<div class="form-group">
				<label for="endTime" class="control-label">结束时间:</label> 
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aty.endTime}" var="endTime" />
			</div> --%>
			<div class="form-group">
				<label for="publisherName" class="control-label">创建者:</label> 
				<input type="text" class="form-control" id="publisherName" name="publisherName" value="${aty.publisherName}" disabled>
			</div>
			<div class="form-group">
				<label for="description" class="control-label">奖项描述:</label> 
				${awd.description}
			</div>
			<div class="form-group">
				<label for="count" class="control-label">设置商品编码数目:</label> 
				<input type="text" class="form-control" id="count" name="count" value="${aty.count}">
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button type="submit" class="btn btn-primary" id="updateSubmitBtn">提交</button>
		</div>
	</form>
<script>
/* 异步提交表单及更新content */
$('#modal').on('shown.bs.modal', function(event) {
	$("#updateForm").validate({
		rules:{			 
			 
		 },
		 messages:{
			
		 },
		 submitHandler : function(form){			
			 $.ajax({
					async : false,
					cache : false,
					type : 'POST',
					data : $("#updateForm").serialize(),				
					url : "wes/addCfg",//请求的action路径  
					error : function() {//请求失败处理函数  
						alert('失败');
					},
					success : function(data) { //请求成功后处理函数。    
						alert("配置成功");						
						$('#modal').on('hidden.bs.modal',function(event){//当modal框完全隐藏后再刷新页面content，要不然有bug
							$("#content-wrapper").html(data);//刷新content页面
						});
						$('#modal').modal('hide');
					}
				});
			 }
		});		
	});

</script>