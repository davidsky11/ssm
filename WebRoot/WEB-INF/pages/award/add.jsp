<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">新增奖项</h4>
</div>
<form id="addForm" action="awd/add" method="post">
	<div class="modal-body">
		<div class="form-group">
			<label for="title" class="control-label"><font color="red">*</font>奖项名称:</label> 
			<input type="text" class="form-control" id="title" name="title">
		</div>
		<div class="form-group">
			<label for="activityId" class="control-label"><font color="red">*</font>活动名称:</label> 
			<select class="form-control" name="activityId">
				<c:forEach items="${atyList}" var="aty" varStatus="status">
					<option value="${aty.id}">${aty.title}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label for="amount" class="control-label"><font color="red">*</font>奖项金额:</label> 
			<input type="text" class="form-control" id="amount" name="amount" />
		</div>
		<div class="form-group">
			<label for="total" class="control-label"><font color="red">*</font>奖项数目:</label> 
			<input type="text" class="form-control" id="total" name="total" />
		</div>
		<div class="form-group">
			<label for="description" class="control-label">奖项描述:</label> 
			<textarea type="text" class="form-control" id="description" name="description" />
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
	</div>
</form>
<script>
/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
$('#modal').on('shown.bs.modal', function(event) {	
			$("#title").focus();//将焦点放在活动名称输入上
			 $("#addForm").validate({
					 rules:{
						 title:{ //格式：domId: 规则
							 minlength:2,//无效
							 maxlength:30,
							 required:true,							 	
							 }
					 },
					 messages:{
						 title:{
							 required:"请输入活动名称",
							 minlength:jQuery.validator.format("至少需要{0}字符"),
							 maxlength:jQuery.validator.format("不能超过{0}字符")
						 }
					 },
				 submitHandler : function(form){
			           	$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data :  $("#addForm").serialize(),
						   // contentType : 'application/json',    //发送信息至服务器时内容编码类型
							//dataType : "json",
							url : "awd/add",//请求的action路径  
							error : function() {//请求失败处理函数  
								alert('失败');
							},
							success : function(data) { //请求成功后处理函数。    
								alert("奖项添加成功");
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