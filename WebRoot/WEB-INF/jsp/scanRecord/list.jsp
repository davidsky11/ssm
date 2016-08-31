<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.crm.domain.ScanRecord"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>扫描记录管理</title>
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<script type="text/javascript">
	//请求地址
	var url;
	//提示消息
	var mesTitle;
	
	//添加记录信息
	function addScanRecord(){
		$('#dlg').dialog('open').dialog('setTitle','新增扫描记录');
		$('#fm').form('clear');
		url = path+"/scanRecord/addScanRecord";
		console.info("addScanRecord - " + url);
		mesTitle = '新增记录成功';
	}
	
	//编辑用户信息
 	function editScanRecord(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg').dialog('open').dialog('setTitle','编辑用户');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/scanRecord/editScanRecord?id="+id;
		 	mesTitle = '编辑用户成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要编辑的记录！', 'error');
	 	}
	 	console.info("editScanRecord - " + url);
	}
 	
	//删除用信息
 	function deleteScanRecord(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	console.info(row);
	 	if (row){
	 		var id = row.id;
	 		url = path+"/scanRecord/deleteScanRecord?id="+id;
		 	$('#dlg_delete').dialog('open').dialog('setTitle','删除扫描记录');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	mesTitle = '删除用户成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的记录！', 'error');
	 	}
	 	console.info("deleteScanRecord - " + url);
	}
 	
	//保存添加、修改内容
	function saveScanRecord(){
		//url = path+"/scanRecord/addScanRecord";
		console.info("saveScanRecord - " + url);
		
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
					 mesTitle = '新增扫描记录失败';
					 
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}	
	
	//提交删除内容
	function saveScanRecord_del(){
		console.info("saveScanRecord_del - " + url);
	 	$('#fm_del').form('submit',{
		 	url: url,
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg_delete').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '删除记录失败';
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
 	function searchScanRecordQ(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	url = path+"/scanRecord/searchScanRecord?username="+$('#search_accountName').val();
	 	$('#datagrid').datagrid({
	 		url: url,
	 		singleSelect: true,
            pagination: true,
	 		accountName: $('#search_accountName').val()
	 	});
	 	
	 	$('#datagrid').datagrid('reload'); 
	}
	
	//查询
 	function searchScanRecord(){
		$('#dlgsearch').dialog('open').dialog('setTitle','查询');
		$('#fmsearch').form('clear');
		url = path+"/scanRecord/searchScanRecord";
		mesTitle = '查询用户成功';
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
			url="${path}/scanRecord/datagrid" 
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
					<th field="id" width="100">编码</th>
					<th field="accountName" width="100">扫码者</th>
					<th field="scanTime" formatter="Common.TimestampFormatter2" width="100">扫码时间</th>
					<th field="longitude" width="100">经度</th>
					<th field="latitude" width="100">纬度</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addScanRecord();">新增</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editScanRecord();">编辑</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteScanRecord();">删除</a>
			<span>扫码者:</span><input name="search_accountName" id="search_accountName" value="" size=10 /> 
  			<a href="javascript:void(0);" onclick="searchScanRecordQ();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<!-- <a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-jright" plain="true" onclick="searchScanRecord();">更多查询</a> -->
		</div>

		<!-- 添加/修改对话框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<!-- <div class="fitem" style="display:none">
					<label>ID:</label> <input name="id" class="easyui-textbox" required="true">
				</div> -->
				<div class="fitem">
					<label>扫码者:</label> <input name="accountName" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>经度:</label> <input name="longitude" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>纬度:</label> <input name="latitude" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>扫码时间:</label> <input name="scanTime" type="text" class="easyui-datetimebox" /><!-- formatter="myFormatter" -->
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveScanRecord()" style="width:90px">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<!-- 删除对话框 -->
		<div id="dlg_delete" class="easyui-dialog"
			style="width:300px;height:200px;padding:30px 20px" closed="true"
			buttons="#dlg-del-buttons">
			<div class="ftitle">请谨慎操作</div>
			<form id="fm_del" method="post" novalidate>
					<label>确定删除该记录吗？</label>
			</form>
		</div>
		
		<!-- 删除对话框按钮 -->
		<div id="dlg-del-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveScanRecord_del()" style="width:90px">删除</a> 
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
					<label>扫码者:</label> <input name="accountName" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>扫码时间:</label> <input name="scanTime" type="text" class="easyui-datebox" />
				</div>
			</form>
		</div>
		
		<!-- 查询对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="searchScanRecord()" style="width:90px">查询</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
	</div>
</body>
</html>
