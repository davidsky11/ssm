<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.crm.domain.dto.PlaceAnalysis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>区域统计</title>
	<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<script type="text/javascript">
	//请求地址
	var url;
	//提示消息
	var mesTitle;

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
	
	function FindData() {
		var publicCode = $('#search_title').val();
		
		/*$.ajax({
			method: 'POST',
			url: "${path}/place/datagrid",
			async: true,
			data
		});*/
		
		alert(publicCode);
		$('#datagrid').datagrid({
			url: path+'/place/datagrid',
			loadMsg: '再整加载中',
			columns: [[
				{field: 'province', title: '区域', width:100 },
				{field: 'amount', title: '数目', width:100 },
				{field: 'publicCode', title: '公共编码', width: 100}]],
			queryParams: {
				publicCode: publicCode
			},
			striped: true,
			pagination: true,
			rownumbers: true,
			singleSelect: true,
			fit: true,
			border: false,
			nowrap: false
		});
	};
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 奖项信息列表 title="奖项管理" -->
		<table id="datagrid" class="easyui-datagrid" 
		    fit="true"
			url="${path}/place/datagrid" 
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
					<th field="province" width="100">区域</th>
					<th field="amount" width="100">数目</th>
					<th field="publicCode" width="100">公共编码</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<!-- <a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a> -->
			
			<span>根据活动信息查询:  </span>
			<select id="search_title" name="publicCode" >
				<c:forEach items="${atyList}" var="aty" >
					<option value="${aty.publicCode}">${aty.title}</option>
				</c:forEach>
			</select>
  			<a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
		</div>
		
		<!-- 查询对话框按钮 -->
		<!-- <div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="searchAward()" style="width:90px">查询</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
				style="width:90px">取消</a>
		</div> -->
		
	</div>
</body>
</html>
