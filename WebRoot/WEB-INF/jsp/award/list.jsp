<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.crm.domain.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>奖项管理</title>
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<script type="text/javascript">
	//请求地址
	var url;
	//提示消息
	var mesTitle;
	
	//添加用户信息
	function addAward(){
		$('#dlg').dialog('open').dialog('setTitle','新增奖项设置');
		$('#fm').form('clear');
		url = path+"/award/addAward";
		mesTitle = '新增奖项设置成功';
	}
	
	//编辑用户信息
 	function editAward(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg').dialog('open').dialog('setTitle','编辑奖项设置');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/award/editAward?id="+id;
		 	mesTitle = '编辑奖项设置成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要编辑的奖项设置！', 'error');
	 	}
	}
 	
	//删除用信息
 	function deleteAward(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg_delete').dialog('open').dialog('setTitle','删除该奖项设置');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/award/deleteAward?id="+id;
		 	mesTitle = '删除奖项设置成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的奖项设置！', 'error');
	 	}
	}
 	
	//保存添加、修改内容
	function saveAward(){
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
					 mesTitle = '新增奖项设置失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}	
	
	//提交删除内容
	function saveAward_del(){
	 	$('#fm').form('submit',{
		 	url: url,
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg_delete').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '删除奖项设置失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}

	//快速查询
 	function searchAwardQ(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	url = path+"/award/editAward?title="+$('#search_title').val;
	}
	
	//查询
 	function searchAward(){
		$('#dlgsearch').dialog('open').dialog('setTitle','查询');
		$('#fmsearch').form('clear');
		url = path+"/award/searchAward";
		mesTitle = '查询奖项设置成功';
	}
	
	//刷新
	function reload(){
		$('#datagrid').datagrid('reload'); 
	}
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 奖项信息列表 title="奖项管理" -->
		<table id="datagrid" class="easyui-datagrid" 
		    fit="true"
			url="${path}/award/datagrid" 
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
					<th field="title" width="100">标题</th>
					<th field="serialNo" width="100">SN码</th>
					<th field="description" width="100">描述</th>
					<th field="hierarchy" width="100">层级</th>
					<th field="sort" width="100">排序</th>
					<th field="amount" width="100">奖项金额</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addAward();">新增</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editAward();">编辑</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteAward();">删除</a>
			<!-- <span>奖项名:</span><input name="search_title" id="search_title" value="" size=10 /> 
  			<a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-jright" plain="true" onclick="searchAward();">更多查询</a> -->
		</div>

		<!-- 添加/修改对话框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>奖项名称:</label> <input name="title" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>奖项编码:</label> <input name="serialNo" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>奖项描述:</label> <input name="description" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>奖项级别:</label> <input name="hierarchy" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>奖项排序:</label> <input name="sort" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>奖项金额:</label> <input name="amount" class="easyui-textbox" required="true">
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveAward()" style="width:90px">保存</a> 
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
				<label>确定删除该奖项设置吗？</label>
			</form>
		</div>
		
		<!-- 删除对话框按钮 -->
		<div id="dlg-del-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveAward_del()" style="width:90px">删除</a> 
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
					<label>奖项名称:</label> <input name="title" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>SN码:</label> <input name="serialNo" class="easyui-textbox" >
				</div>
			</form>
		</div>
		
		<!-- 查询对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="searchAward()" style="width:90px">查询</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
	</div>
</body>
</html>
