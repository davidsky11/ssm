<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Content Header (Page header) -->

<section class="content-header">
	<h1>
		字典管理 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
		<li class="active">字典管理</li>
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
					<div class="btn-group">
						
						<!-- 注意，为了设置正确的内补（padding），务必在图标和文本之间添加一个空格。 -->
						<button id="addBtn" type="button"
							class="btn btn-primary btn-flat margin" data-toggle="modal"
							data-target="#addModal">
							<span class="fa fa-fw  fa-plus" aria-hidden="true"></span> 新增
						</button>	
										
						<button id="deleteBtn" type="button"
							class="btn  btn-danger btn-flat margin">
							<span class="fa fa-fw fa-remove" aria-hidden="true"></span> 删除</button>
						
						<button id="detailBtn" type="button"
							class="btn  btn-primary btn-flat margin"  onclick=''>
							<span class="fa fa-fw fa-newspaper-o" aria-hidden="true"></span> 详情</button>	
						<button id="updateBtn" type="button"
							class="btn  btn-primary btn-flat margin"  onclick=''>
							<span class="fa fa-fw fa-pencil-square-o" aria-hidden="true"></span> 编辑</button>	
							
					</div>
					<div class="zTreeDemoBackground right">
						<ul id="dicTree" class="ztree"></ul>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<!-- /.content -->

<!-- 新增页面 modal框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">新增字典</h4>
			</div>
			<form id="addForm" action="dic/add" method="post">
				<div class="modal-body">
					<label for="parentName" class="control-label">上级资源:</label> 
					<font color="red"><span id="parentName">请返回,选定上级资源再进行操作</span></font>
					
					<div class="form-group">
						<label for="entrycode" class="control-label"><font color="red">*</font>资源编码:</label> 
						<input type="text" class="form-control" id="entrycode" name="entrycode">
					</div>
					<div class="form-group">
						<label for="entryvalue" class="control-label"><font color="red">*</font>资源名:</label> 
						<input type="text" class="form-control" id="entryvalue" name="entryvalue">
					</div>
					<div class="form-group">
						<label for="type" class="control-label"><font color="red">*</font>是否叶子节点:</label> 
							
						<input name="isleaf" type="radio" value="0" >上级节点
						<input name="type" type="radio" value="1" checked="checked">叶子节点
					</div>
					<!-- <div class="form-group">
						<label for="url" class="control-label">菜单路径URL:</label> <input
							type="text" class="form-control " id="url" name="url">
					</div>
					<div class="form-group">
						<label for="priority" class="control-label"><font color="red">*</font>排序优先级:</label> <input
						placeholder="优先级数字越小，排在越上面"
							type="text" class="form-control" id="priority" name="priority" value="1">
					</div> -->	
					
					<input type="hidden" class="form-control" id="parentId" name="parentid" >
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary" id="addSubmitBtn">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- ./新增页面 modal框 -->

<!-- 编辑页面 modal框  -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content"></div>
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
				<h4 class="modal-title" id="exampleModalLabel">删除字典</h4>
				<input type="hidden" id="id" name="id" />
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
	/*   */
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
	
	function getCheckedIds(){
		var treeObj = $.fn.zTree.getZTreeObj("dicTree");
		var nodes = treeObj.getCheckedNodes(true);

		var dicIds=[];
		for(var i=0;i<nodes.length;i++){
			dicIds[i]=nodes[i].id;
		}
		return dicIds;	
		}
	
	/* button监听事件 */
	$(document).ready(function(){
		$("#deleteBtn").click(function(){
			deleteItemsById(getCheckedDicIds(),"/delete");
		});
		
	});
	
	//删除确认modal事件处理
	$('#deleteConfirmModal').on('shown.bs.modal', function(event) {
		$('#deleteConfirmBtn').click(function(){
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				data : {
					id: $("#id").val()	 
				},
				url : "dic/delete",//请求的action路径  
				error : function() {//请求失败处理函数  
					alert('失败');
				},
				success : function(data) { //请求成功后处理函数。    
					$("#content-wrapper").html(data);//刷新content页面
					$('#deleteConfirmModal').modal('hide');
				}
			});
		});
	});
	
	
 	/*modal框事件监听 详情：http://v3.bootcss.com/javascript/#modals-events */
	$('#addModal').on('shown.bs.modal', function(event) {	
		
			$("#name").focus();
			 $("#addForm").validate({
				 rules:{
					 name:{ //格式：domId: 规则
						 minlength:2,//无效
						 maxlength:30,
						 required:true,							 	
						 },
					 type:"required",					
					 permission:{
						 required:true,
						 isPermission:true
					 },//正则来校验，校验方法在additional-method.js里
					 priority:{
						 min:1,//无效
						 max:999,
						 required:true,
						 digits:true	
						 }
				 },
				 messages:{					
					 priority:{
						 max: jQuery.validator.format("请输入一个最大为{0} 的数"),
						 min: jQuery.validator.format("请输入一个最小为{0} 的数"),						
						 digits: "只能输入整数",
						 reuqired:true
					 }
				 },
				 
				 submitHandler : function(form){
			           	$.ajax({
							async : false,
							cache : false,
							type : 'POST',
							data :  $("#addForm").serialize(),
						   
							url : "dic/add",//请求的action路径  
							error : function() {//请求失败处理函数  
								alert('失败');
							},
							success : function(data) { //请求成功后处理函数。    
								alert("添加字典成功");
								$('#addModal').on('hidden.bs.modal',function(event){//当modal框完全隐藏后再刷新页面content，要不然有bug
									$("#content-wrapper").html(data);//刷新content页面
								});
								$('#addModal').modal('hide');
							}
						});
			        }    
			    });
			$("#addSubmitBtn").click(function() {
				if($('#parentId').val()==""){
					alert("请返回,选定上级资源再进行操作");
					return;
				}
				
				
			});
		});
	

	$("#searchBtn").click(function() {
		$('#pageNumber').val(1);
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			data : $("#searchForm").serialize(),		 
			url : "dic/search",//请求的action路径  
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
	
	function updateItem(id){	
		modalLoadAndDisplay('dic/'+id);
	}
	
	function detailItem(id){
		modalLoadAndDisplay('dic/detail/'+id);
	}
	
	function deleteItem(id){
		$("#id").val(id);
	}
	
	/* ztree */
	/* 节点点击的时候回调函数*/
	function onTreeNodeClick(event, treeId, treeNode, clickFlag) {
		//alert(treeNode.id);
		$("#parentId").val(treeNode.id);//设置上级资源id
		$("#parentName").text(treeNode.name);
		//$("#addBtn").attr("onclick", ""); // 设置要新增的id
		$("#detailBtn").attr("onclick","detailItem("+treeNode.id+")");//设置要显示的详情的id
		$("#updateBtn").attr("onclick","updateItem("+treeNode.id+")");//设置要显示的编辑页面的id
		$("#deleteBtn").attr("onclick","deleteItem("+trreNode.id+")");
	}	
	
    var setting = {  
    	data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pId"
			},
			
    		view: {
				showIcon: true
				}
			},
	    check: {
			enable: false,
			chkboxType: { "Y": "", "N": "" } //设置勾选行为
		},
		callback: {
			onClick: onTreeNodeClick
		}
	};  
   	 
	var zNodes;  
	$(document).ready(function(){  
		$.ajax({  
			async : false,  
			cache:false,  
			type: 'GET',  
			dataType : "json",  
			url: "dic/json/all", //请求的action路径  
			error: function () {//请求失败处理函数  
			    alert('请求失败');  
			},  
			success:function(data){ //请求成功后处理函数。  
			    zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes  
			}  
		});  
	   
	    $.fn.zTree.init($("#dicTree"), setting, zNodes);
	});
	   	     
	$(document).ready(function() {
		$(".js-example-basic-single").select2();	
	});	
	
</script>