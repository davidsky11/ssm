<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title" id="exampleModalLabel">编辑活动</h4>
	</div>
	<form id="updateForm" action="aty/update" method="post">
		<div class="modal-body">
			<input name="id" value="${aty.id}" hidden="true"/>
			<div class="form-group">
				<label for="title" class="control-label">活动名称:</label> 
				<input type="text" class="form-control" id="title" name="title" value="${aty.title}" >
			</div>	
			<div class="form-group">
				<label for="publicCode" class="control-label">公共编码:</label> 
				<input type="text" class="form-control" id="publicCode" name="publicCode" value="${aty.publicCode}" >
			</div>			
			<!-- <div class="form-group">
			<label for="startTime" class="control-label"><font color="red">*</font>开始时间</label>
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>datepicker
				<input type="text" class="form-control pull-right" id="reservation" name="startTime">
			</div>
		</div> -->
		<!-- <div class="form-group">
			<label for="endTime" class="control-label"><font color="red">*</font>结束时间</label>
			<div class="input-group date">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input type="text" class="form-control pull-right" id="datepicker" name="endTime">
			</div>
		</div> -->		
			<div class="form-group">
				<label for="content" class="control-label">活动内容:</label> 
				<textarea type="text" class="form-control" id="content" name="content" >${aty.content}</textarea>
			</div>
			<div class="form-group">
				<label for="description" class="control-label">活动描述:</label> 
				<textarea type="text" class="form-control" id="description" name="description" >${aty.description}</textarea>
			</div>
			<%-- <div class="form-group">
				<label for="locked" class="control-label">状态:</label> 
				<c:choose>
					<c:when test="${user.locked}">
					<input  name="locked"  type="radio" checked="checked" value="true">锁定
					<input  name="locked"  type="radio" value="false">未锁定
					</c:when>
					<c:otherwise>
					<input  name="locked"  type="radio" value="true">锁定
					<input  name="locked"  type="radio" checked="checked" value="false">未锁定
					</c:otherwise>
				</c:choose>
			</div> --%>
			
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
			 publicCode:{ //格式：domId: 规则
				 minlength:2,//无效
				 maxlength:30,
				 required:true,							 	
				 }
		 },
		 messages:{
			
		 },
		 submitHandler : function(form){			
			 $.ajax({
					async : false,
					cache : false,
					type : 'POST',
					data : $("#updateForm").serialize(),				
					url : "aty/update",//请求的action路径  
					error : function() {//请求失败处理函数  
						alert('失败');
					},
					success : function(data) { //请求成功后处理函数。    
						alert("活动修改成功");						
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