<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="exampleModalLabel">新增活动</h4>
</div>
<form id="addForm" action="aty/add" method="post" >
<div class="modal-body">
	<div class="modal-body">
		<div class="form-group">
			<label for="title" class="control-label"><font color="red">*</font>活动名称:</label> 
			<input type="text" class="form-control" id="title" name="title">
		</div>
		<div class="form-group">
			<label for="publicCode" class="control-label"><font color="red">*</font>活动编码:</label> 
			<input type="text" class="form-control" id="publicCode" name="publicCode">
		</div>
		<div class="form-group">
			<label for="atyCode" class="control-label"><font color="red">*</font>公共编码:</label> 
			<input type="text" class="form-control" id="atyCode" name="atyCode">
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
			<label for="description" class="control-label">活动描述:</label> 
			<textarea type="text" class="form-control" id=""description"" name="description" />
		</div>
		<div class="form-group">
			<label for="content" class="control-label"><font color="red">*</font>活动内容:</label> 
			<textarea type="text" class="form-control" id="content" name="content" />
		</div>
		<div>
			<label for="infoUrl" class="control-label">活动海报:</label>
			<input type="text" class="form-control" id="infoUrl" name="infoUrl">
			<p class="help-block">这里可以填入海报URL:</p>
			<p>1、前往<a target="_blank" href="http://h5.baidu.com/">百度H5</a>
					、<a target="_blank" href="http://maka.im/">MAKA</a>
					、<a target="_blank" href="http://www.ih5.cn/">iH5</a>等海报制作平台制作H5海报</p>
			<p>2、发布海报并获取海报对应的链接</p>
			<p>3、将海报链接填入活动海报对应的编辑框中</p>
			<p>4、保存活动信息</p>
		</div>
		<!-- <div>
			<label for="image" class="control-label">上传图片:</label>
			<input type="file" id="uploadify" name="image">
			<p class="help-block">图片尺寸尽量保持在640x950.</p>
		</div> -->
	</div>
	<div class="modal-body">
	
	</div>
</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		<button type="submit" class="btn btn-primary" id="addSubmitBtn" onclick="return validate();">提交</button>
	</div>
</form>
<script>

	function validate() {
		var str = $('#publicCode').val();
		
		var valid = /^\d{3}$/;
		
		if (valid.test(str)) {
			return true;
		} else {
			alert("活动编码必须是三位数字!");
			return false;
		}
	}

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
	$('#startTime').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-mm-dd"
	});
	
	$('#endTime').datepicker({
		autoclose: true,
		language: "zh-CN",
		format: "yyyy-mm-dd"
	});
	
</script>