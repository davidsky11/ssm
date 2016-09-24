<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath()%>/scripts/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/uploadify/uploadify.css" />
	
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">新增活动</h4>
</div>
<form id="addForm" action="aty/add" method="post" enctype="multipart/form-data">
	<div class="modal-body">
		<div class="form-group">
			<label for="title" class="control-label"><font color="red">*</font>活动名称:</label> 
			<input type="text" class="form-control" id="title" name="title">
		</div>
		<div class="form-group">
			<label for="publicCode" class="control-label"><font color="red">*</font>公共编码:</label> 
			<input type="text" class="form-control" id="publicCode" name="publicCode">
		</div>
		<!-- <div class="form-group">
			<label for="startTimeAdd" class="control-label"><font color="red">*</font>开始时间</label>
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input type="text" class="form-control pull-right" id="startTimeAdd" name="startTime">
			</div>
		</div>
		<div class="form-group">
			<label for="endTimeAdd" class="control-label"><font color="red">*</font>结束时间</label>
			<div class="input-group">
				<div class="input-group-addon">
					<i class="fa fa-calendar"></i>
				</div>
				<input type="text" class="form-control pull-right" id="endTimeAdd" name="endTime">
			</div>
		</div> -->
		<div class="form-group">
			<label for="content" class="control-label"><font color="red">*</font>活动内容:</label> 
			<textarea type="text" class="form-control" id="content" name="content" />
		</div>
		<div class="form-group">
			<label for="description" class="control-label">活动描述:</label> 
			<textarea type="text" class="form-control" id=""description"" name="description" />
		</div>
		<div>
			<label for="image" class="control-label">上传图片:</label>
			<input type="file" id="uploadify" name="image">
			<p class="help-block">图片尺寸尽量保持在640x950.</p>
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
	
	//Date picker
	$('#startTimeAdd').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-MM-dd HH:mm:ss"
	})
	
	$('#endTimeAdd').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-MM-dd HH:mm:ss"
	})
	
	$(document).ready(function() {
		$("#uploadify").uploadify({
			swf:'scripts/uploadify/uploadify.swf',
			uploader : 'servlet/Upload',//后台处理的请求   
			buttonText:'上传',
			fileSizeLimit:'1024KB',
			fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
			fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
			auto:true,
			height:16,
			width:40,
			multi:false,
			onUploadSuccess:function(file, data, response){
				alert("上传完成");
				/* $('#li_photo_view').attr('src','uploads/'+data);
				$('#li_photo_url').val(data); 
				$('#photo_a').attr('href', 'uploads/'+data); */
			}
		})
	})
</script>