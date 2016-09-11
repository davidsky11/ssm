<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.crm.domain.Activity"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户管理</title>
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<script type="text/javascript">
	//请求地址
	var url;
	//提示消息
	var mesTitle;
	
	//添加用户信息
	function addActivity(){
		$('#dlg').dialog('open').dialog('setTitle','新增活动');
		$('#fm').form('clear');
		url = path+"/activity/addActivity";
		mesTitle = '新增活动成功';
	}
	
	//编辑用户信息
 	function editActivity(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg').dialog('open').dialog('setTitle','编辑活动');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/activity/editActivity?id="+id;
		 	mesTitle = '编辑活动成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要编辑的活动！', 'error');
	 	}
	}
 	
	//删除用信息
 	function deleteActivity(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg_delete').dialog('open').dialog('setTitle','删除活动');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/activity/deleteActivity?id="+id;
		 	mesTitle = '删除活动成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的记录！', 'error');
	 	}
	}
 	
	//保存添加、修改内容
	function saveActivity(){
	 	$('#fm').form('submit',{
		 	url: url,
		 	onSubmit: function(){
		 		return $(this).form('validate');
		 	},
			success: function(result){
				/* console.info(result); */
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '新增活动失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}	
	
	//提交删除内容
	function saveActivity_del(){
	 	$('#fm').form('submit',{
		 	url: url,
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg_delete').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '删除用户失败';
					 $('#datagrid').datagrid('reload'); 
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			},
			error: function() {
				$('#datagrid').datagrid('reload'); 
			}
	 	});
	}

	//快速查询
 	function searchActivityQ(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	url = path+"/activity/editActivity?publisherName="+$('#search_publisherName').val;
	}
	
	//查询
 	function searchActivity(){
		$('#dlgsearch').dialog('open').dialog('setTitle','查询');
		$('#fmsearch').form('clear');
		url = path+"/activity/searchActivity";
		mesTitle = '查询活动成功';
	}
	
	//刷新
	function reload(){
		$('#datagrid').datagrid('reload'); 
	}
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 用户信息列表 title="活动管理" -->
		<table id="datagrid" class="easyui-datagrid" 
		    fit="true"
			url="${path}/activity/datagrid" 
			toolbar="#toolbar" 
			pagination="true"
			fitColumns="true" 
			singleSelect="true" 
			rownumbers="true"
			striped="true"
			border="false" 
			nowrap="false">
			<thead>
				<tr>
					<th field="id" width="100">编号</th>
					<th field="title" width="100">活动名称</th>
					<th field="publicCode" width="70">公共编码</th>
					<th field="description" width="100">活动描述</th>
					<th field="content" width="100">活动内容</th>
					<th field="startTime" formatter="Common.TimestampFormatter2" width="100">开始时间</th>
					<th field="endTime" formatter="Common.TimestampFormatter2" width="100">结束时间</th>
					<th field="publisherName" width="100">发布者</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addActivity();">新增</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editActivity();">编辑</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteActivity();">删除</a>
			<!-- <span>发布者:</span><input name="search_publisherName" id="search_publisherName" value="" size=10 /> 
  			<a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>  -->
			<!-- <a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-jright" plain="true" onclick="searchActivity();">更多查询</a> -->
		</div>

		<!-- 添加/修改对话框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>活动名称:</label> <input name="title" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>公共编码:</label> <input name="publicCode" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>活动描述:</label> <input name="description" class="easyui-textbox">
				</div>
				<div class="fitem">
					<label>开始时间:</label> <input name="startTime" formatter="Common.TimestampFormatter2" type="text" class="easyui-datetimebox" required="required"/>
				</div>
				<div class="fitem">
					<label>结束时间:</label> <input name="endTime" formatter="Common.TimestampFormatter2" type="text" class="easyui-datetimebox" required="required"/>
				</div>
				<div class="fitem">
					<label>活动内容:</label> <input name="content" type="text" required="required"/>
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveActivity()" style="width:90px">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<!-- 删除对话框 -->
		<div id="dlg_delete" class="easyui-dialog"
			style="width:300px;height:200px;padding:30px 20px" closed="true"
			buttons="#dlg-del-buttons">
			<div class="ftitle">请谨慎操作</div>
			<form id="fm" method="post" novalidate>
					<label>确定删除该活动吗？</label>
			</form>
		</div>
		
		<!-- 删除对话框按钮 -->
		<div id="dlg-del-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveActivity_del()" style="width:90px">删除</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_delete').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<!-- 查询对话框 -->
		<div id="dlgsearch" class="easyui-dialog"
			style="width:400px;height:380px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fmsearch" method="post" novalidate>
				<div class="fitem">
					<label>活动名:</label> <input name="title" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>发布者:</label> <input name="publisherName" class="easyui-textbox" >
				</div>
			</form>
		</div>
		
		<!-- 查询对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="searchActivity()" style="width:90px">查询</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
	</div>
</body>
</html>
