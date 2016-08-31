<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	function addUser(){
		$('#dlg').dialog('open').dialog('setTitle','新增用户');
		$('#fm').form('clear');
		url = path+"/user/addUser";
		mesTitle = '新增用户成功';
	}
	
	//编辑用户信息
 	function editUser(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg').dialog('open').dialog('setTitle','编辑用户');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/user/editUser?id="+id;
		 	mesTitle = '编辑用户成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要编辑的记录！', 'error');
	 	}
	}
 	
	//删除用信息
 	function deleteUser(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
		 	$('#dlg_delete').dialog('open').dialog('setTitle','删除用户');
		 	$('#fm').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	url = path+"/user/deleteUser?id="+id;
		 	mesTitle = '删除用户成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的记录！', 'error');
	 	}
	}
 	
	//保存添加、修改内容
	function saveUser(){
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
					 mesTitle = '新增用户失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}	
	
	//提交删除内容
	function saveUser_del(){
	 	$('#fm').form('submit',{
		 	url: url,
			success: function(result){
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg_delete').dialog('close'); 
				 	$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '删除用户失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}

	//快速查询
 	function searchUserQ(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	url = path+"/user/editUser?username="+$('#search_username').val;
	}
	
	//查询
 	function searchUser(){
		$('#dlgsearch').dialog('open').dialog('setTitle','查询');
		$('#fmsearch').form('clear');
		url = path+"/user/searchUser";
		mesTitle = '查询用户成功';
	}
	
	//刷新
	function reload(){
		$('#datagrid').datagrid('reload'); 
	}
	
	// 修改用户的角色信息
 	function editUserRole(){
	 	var row = $('#datagrid').datagrid('getSelected');
	 	if (row){
	 		var id = row.id;
	 		alert(path+"/user/getUserRole?userId=" + id);
		 	$('#dlg_userrole').dialog('open').dialog('setTitle','配置用户角色信息');
		 	//$('#fm_userrole').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
		 	$('#fm_userrole').form('load', {
		 		url: path+"/user/getUserRole?userId=" + id,
		 		onBeforeLoad: function() {
		 			
		 		},
		 		onLoadSuccess: function(data) {
		 			alert(data);
		 			$("#urId");
		 			$("#userId");
		 			$("#roleSelect");
		 		}
		 	})
		 	
		 	url = path+"/user/editUserRole?id="+id;
		 	mesTitle = '删除用户成功';
	 	}else{
	 		$.messager.alert('提示', '请选择要删除的记录！', 'error');
	 	}
	}
</script>

</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 用户信息列表 title="用户管理" -->
		<table id="datagrid" class="easyui-datagrid" 
		    fit="true"
			url="${path}/user/datagrid" 
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
					<th field="username" width="100">用户名</th>
					<!-- <th field="password" width="100">密码</th> -->
					<th field="gender" width="100">性别</th>
					<th field="idcard" width="100">身份证号码</th>
					<th field="email" width="100">邮箱</th>
					<th field="qq" width="100">QQ</th>
					<th field="weixin" width="100">微信</th>
					<th field="regtime" width="100">入职时间</th>
				</tr>
			</thead>
		</table>

		<!-- 按钮 -->
		<div id="toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addUser();">新增</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editUser();">编辑</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editUserRole();">设置角色</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteUser();">删除</a>
			<span>用户名:</span><input name="search_username" id="search_username" value="" size=10 /> 
  			<a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-jright" plain="true" onclick="searchUser();">更多查询</a>
		</div>

		<!-- 添加/修改对话框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>用户名:</label> <input name="username" class="easyui-textbox" required="true">
				</div>
				<!-- <div class="fitem">
					<label>密码:</label> <input name="password" class="easyui-textbox" required="true">
				</div> -->
				<div class="fitem">
					<label>身份证:</label> <input name="idcard" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>性别:</label> 
					<input type="radio" name="gender" id="gender" value="男" style="width:50px;">男</input>
					<input type="radio" name="gender" id="gender" value="女" style="width:50px;">女</input>
				</div>
				<div class="fitem">
					<label>注册时间:</label> <input name="regtime" type="text" class="easyui-datebox" required="required"/>
				</div>
				<div class="fitem">
					<label>Email:</label> <input name="email" class="easyui-textbox" validType="email">
				</div>
				<div class="fitem">
					<label>QQ:</label> <input name="qq" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>微信:</label> <input name="weixin" class="easyui-textbox" >
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a> 
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
					<label>确定删除用户吗？</label>
			</form>
		</div>
		
		<!-- 删除对话框按钮 -->
		<div id="dlg-del-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveUser_del()" style="width:90px">删除</a> 
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
					<label>用户名:</label> <input name="username" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>身份证:</label> <input name="idcard" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>性别:</label> 
					<input type="radio" name="gender" id="gender" value="" style="width:30px;">全部</input>
					<input type="radio" name="gender" id="gender" value="男" style="width:30px;">男</input>
					<input type="radio" name="gender" id="gender" value="女" style="width:30px;">女</input>
				</div>
				<div class="fitem">
					<label>入职时间:</label> <input name="regtime" type="text" class="easyui-datebox" />
				</div>
				<div class="fitem">
					<label>至</label> <input name="regtime" type="text" class="easyui-datebox" />
				</div>
				<div class="fitem">
					<label>Email:</label> <input name="email" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>QQ:</label> <input name="qq" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>微信:</label> <input name="weixin" class="easyui-textbox" >
				</div>
			</form>
		</div>
		
		<!-- 查询对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="searchUser()" style="width:90px">查询</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<!-- 添加/修改对话框 -->
		<div id="dlg_userrole" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm_userrole" action="" method="post" novalidate>
				<div class="fitem">
					<label>编号:</label> 
					<input id="urId" name="id" class="easyui-textbox" value="" required="true">
				</div>
				<div class="fitem">
					<label>用户编号:</label>
					<input id="userId" name="userId" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>角色:</label>
					<select id="roleSelect" items="${roleList}" var="role">
						<option value="${role.id}">${role.name}</option>
					</select>
				</div>
			</form>
		</div>
		
		<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveUserRole()" style="width:90px">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_userrole').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
	</div>
</body>
</html>
