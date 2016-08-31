<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.crm.domain.Role"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>角色管理</title>
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<script type="text/javascript">
	//请求地址
	var url;
	//提示消息
	var mesTitle;
	
	//添加角色信息
	function addRole(){
		$('#dlg').dialog('open').dialog('setTitle','新增角色');
		$('#fm').form('clear');
		url = path+"/role/addRole";
		mesTitle = '新增角色成功';
	}
	
	//编辑角色信息
 	function editRole(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg').dialog('open').dialog('setTitle','编辑角色');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/role/editRole?id="+id;
		 	mesTitle = '编辑角色成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要编辑的角色！', 'error');
	 	}
	}
 	
	//删除用信息
 	function deleteRole(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg_delete').dialog('open').dialog('setTitle','删除角色');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/role/deleteRole?id="+id;
		 	mesTitle = '删除角色成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的记录！', 'error');
	 	}
	}
 	
	//保存添加、修改内容
	function saveRole(){
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
					 mesTitle = '新增角色失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}	
	
	//提交删除内容
	function saveRole_del(){
	 	$('#fm').form('submit',{
		 	url: url,
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg_delete').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '删除角色失败';
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
 	function searchRoleQ(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	url = path+"/role/editRole?publisherName="+$('#search_publisherName').val;
	}
	
	//查询
 	function searchRole(){
		$('#dlgsearch').dialog('open').dialog('setTitle','查询');
		$('#fmsearch').form('clear');
		url = path+"/role/searchRole";
		mesTitle = '查询角色成功';
	}
	
	//刷新
	function reload(){
		$('#datagrid').datagrid('reload'); 
	}
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 用户信息列表 title="用户管理" -->
		<table id="datagrid" class="easyui-datagrid" 
		    fit="true"
			url="${path}/role/datagrid" 
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
					<th field="name" width="100">角色名称</th>
					<th field="roleDesc" width="100">角色描述</th>
					<th field="enabled" width="100">是否可用</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addRole();">新增</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editRole();">编辑</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteRole();">删除</a>
			<!-- <span>发布者:</span><input name="search_publisherName" id="search_publisherName" value="" size=10 /> 
  			<a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>  -->
			<!-- <a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-jright" plain="true" onclick="searchRole();">更多查询</a> -->
		</div>

		<!-- 添加/修改对话框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>角色名称:</label> <input name="name" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>角色描述:</label> <input name="roleDesc" class="easyui-textbox">
				</div>
				<div class="fitem">
					<label>是否启用:</label>
					<input type="radio" name="enabled" id="enabled" value="1" style="width:50px;">启用</input>
					<input type="radio" name="enabled" id="enabled" value="0" style="width:50px;">禁用</input>
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveRole()" style="width:90px">保存</a> 
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
					<label>确定删除该角色吗？</label>
			</form>
		</div>
		
		<!-- 删除对话框按钮 -->
		<div id="dlg-del-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveRole_del()" style="width:90px">删除</a> 
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
					<label>角色名:</label> <input name="name" class="easyui-textbox" >
				</div>
			</form>
		</div>
		
		<!-- 查询对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="searchRole()" style="width:90px">查询</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
	</div>
</body>
</html>
