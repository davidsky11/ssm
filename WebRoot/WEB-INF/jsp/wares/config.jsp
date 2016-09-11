<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.crm.domain.dto.ConfigDto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>综合配置</title>
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<script type="text/javascript">
	//请求地址
	var url;
	//提示消息
	var mesTitle;
	
	//添加配置信息
	function addConfig(){
		$('#dlg').dialog('open').dialog('setTitle','设置新增产品信息');
		$('#fm').form('clear');
		
		var activityName = $('#search_title').find("option:selected").text();//.find("option:selected")
		var activityId = $('#search_title').val();
		
		//alert(activityName + " " + activityId);
		$('#activityNameA').val(activityName);
		$('#activityIdA').attr("value", activityId);
		url = path+"/wares/addConfig";
		mesTitle = '新增奖项设置成功';
	}
	
	function downloadConfig() {
		var activityId = $('#search_title').val();
		url = path+"/wares/downloadConfig";
		
		$.ajax({
			type: 'POST',
			url: url,
			data: {
				activityId: activityId
			},
			success: function(data) {
				alert(data.msg);
			},
			error: function() {
				
			}
		});
		
	}
	
	//编辑配置信息
 	function editConfig(){
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
 	function deleteConfig(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg_delete').dialog('open').dialog('setTitle','删除该奖项设置');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/wares/deleteWares?id="+id;
		 	mesTitle = '删除奖项设置成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的奖项设置！', 'error');
	 	}
	}
 	
	//保存添加、修改内容
	function saveConfig(){
	 	$('#fm').form('submit',{
		 	url: url,
		 	onSubmit: function(){
		 		return $(this).form('validate');
		 	},
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '为活动配置编码数目失败！';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}	
	
	//提交删除内容
	function saveConfig_del(){
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

	//刷新
	function reload(){
		$('#datagrid').datagrid('reload'); 
	}
	
	function FindData() {
		var activityId = $('#search_title').val();
		
		$('#datagrid').datagrid({
			url: path+'/wares/configList',
			loadMsg: '正在加载中',
			columns: [[
				{field: 'title', title: '活动名称', width:100 },
				{field: 'count', title: '编目数目', width: 100},
				{field: 'amount', title: '奖项经费', width: 100}
				]],
			queryParams: {
				activityId: activityId
			},
			striped: true,
			pagination: true,
			rownumbers: true,
			singleSelect: true,
			fit: true,
			border: false,
			nowrap: false
		});
		
		var queryParams = $('#datagrid').datagrid('options').queryParams;
		queryParams.activityId = activityId;
		
		$('#datagrid').datagrid('reload');
		/*$('#datagrid').datagrid('reload', {
			publicCode: publicCode
		}); */
	};
	
	$(function() {
		FindData();
		$("#search_title").get(0).selectedIndex=0;  //设置Select索引值为1的项选中
	})
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 奖项信息列表 title="奖项管理"  class="easyui-datagrid" -->
		<table id="datagrid" 
		    fit="true"
			url="${path}/wares/configList" 
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
					<th width="100">活动名称</th>
					<th width="100">编码数目</th>
					<th width="100">奖项经费</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addConfig();">新增</a> 
			<!-- <a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editConfig();">编辑</a>  -->
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteConfig();">删除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="downloadConfig();">导出活动的编码数据</a>
				
			<span>根据活动信息查询:  </span>
			<select id="search_title" name="activityId">
				<!-- <option value="">请选择活动</option> -->
				<c:forEach items="${atyList}" var="aty" >
					<option value="${aty.id}">${aty.title}</option>
				</c:forEach>
			</select>
  			<a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
  			
		</div>

		<!-- 添加/修改对话框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>活动名称：</label> <input id="activityNameA" name="activityNameA" required="true" readonly >
					<input id="activityIdA" name="activityIdA" type="hidden" >
				</div>
			
				<!-- <div class="fitem">
					<label>公共编码:</label> <input name="title" class="easyui-textbox" required="true">
				</div> -->
				<div class="fitem">
					<label>编码条数:</label> <input name="count" class="easyui-textbox" required="true">
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveConfig()" style="width:90px">保存</a> 
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
				<!-- <div class="fitem">
					<label>SN码:</label> <input name="serialNo" class="easyui-textbox" >
				</div> -->
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
