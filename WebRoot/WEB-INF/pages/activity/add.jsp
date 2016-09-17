<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">新增活动</h4>
</div>
<form id="addForm" action="user/add" method="post">
	<div class="modal-body">
		<div class="form-group">
			<label for="title" class="control-label"><font color="red">*</font>活动名称:</label> 
			<input type="text" class="form-control" id="title" name="title">
		</div>
		<div class="form-group">
			<label for="publicCode" class="control-label"><font color="red">*</font>公共编码:</label> 
			<input type="text" class="form-control" id="publicCode" name="publicCode">
		</div>
		<div class="form-group">
			<label for="startTime" class="control-label"><font color="red">*</font>开始时间</label>
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input type="text" class="form-control pull-right" id="startTime" name="startTime">
			</div>
		</div>
		<div class="form-group">
			<label for="endTime" class="control-label"><font color="red">*</font>结束时间</label>
			<div class="input-group date">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input type="text" class="form-control pull-right" id="endTime" name="endTime">
			</div>
		</div>
		<div class="form-group">
			<label for="content" class="control-label"><font color="red">*</font>活动内容:</label> 
			<textarea type="text" class="form-control" id="content" name="content" />
		</div>
		<div class="form-group">
			<label for="description" class="control-label">活动描述:</label> 
			<textarea type="text" class="form-control" id=""description"" name="description" />
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
	</div>
</form>
<script>

//Date picker
$('#startTime').datepicker();
$('#endTime').datepicker();
//Date range picker with time picker
//$('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});	

/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
$('#modal').on('shown.bs.modal', function(event) {	
			$("#title").focus();//将焦点放在活动名称输入上
			 $("#addForm").validate({
					 rules:{
						 title:{ //格式：domId: 规则
							 minlength:2,//无效
							 maxlength:30,
							 required:true,							 	
							 },
						 publicCode:{ //格式：domId: 规则
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
						 },
						 publicCode:{
							 required:"请输入活动编码",
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
							url : "aty/add",//请求的action路径  
							error : function() {//请求失败处理函数  
								alert('失败');
							},
							success : function(data) { //请求成功后处理函数。    
								alert("活动添加成功");
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