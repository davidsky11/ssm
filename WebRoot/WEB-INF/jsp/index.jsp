<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>兑奖网</title>

<STYLE>
.main_header{width:100%;height:64px;margin:0px;background:url("${path}/img/login_bg.jpg") repeat-x;overflow: hidden;}
.header_left{float:left;width:242px;height:100%;background:url("${path}/img/logo2.png");}
</STYLE>

<script type="text/javascript">
	window.onload = function() {
		$('#mainMenu').tree({
			url : path+'/getMenu',
			parentField : 'pid',
			onClick : function(node) {
				if (node.attributes.url) {
					var src = path + node.attributes.url;
					if (!$.startWith(node.attributes.url, '/')) {	//不是本项目的url，例如www.baidu.com
						src = node.attributes.url;
					}
					var tabs = $('#mainTabs');
					var opts = {
						title : node.text,
						closable : true,
						iconCls : node.iconCls,
						content : $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:99%;height:99%;padding-left:2px;" frameBorder="0"></iframe>', src),
						border : false,
						fit : true
					};
					if (tabs.tabs('exists', opts.title)) {
						tabs.tabs('select', opts.title);
					} else {
						tabs.tabs('add', opts);
					}
				}
			}
		});
	};
	
	function userInfo() {
		$('#dlg').dialog('open').dialog('setTitle','个人信息');
		//$('#fm').form('load', path+"/user/userInfo");
		
		//$('#fm').form('clear');
		mesTitle = '新增用户成功';
	}
	
	function saveUserInfo() {
		url = path+"/user/editUser";
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
				 	//$('#datagrid').datagrid('reload'); 
				} else {
					 mesTitle = '修改个人信息失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}
	
	function resetPass() {
		$('#dlg_pass').dialog('open').dialog('setTitle','个人信息');
		/*$('#fm').form('load', {
			url: path+"/user/userInfo"
		});*/
	}
	
	function modifyPass() {
		url = path+"/user/modifyCurrentUserPwd";
		$('#fm_pass').form('submit',{
		 	url: url,
		 	onSubmit: function(){
		 		return $(this).form('validate');
		 	},
			success: function(result){
				/* console.info(result); */
				var result = eval('('+result+')');
				if (result.success){
				 	$('#dlg').dialog('close'); 
				 	//$('#datagrid').datagrid('reload'); 
				 	mesTitle = '修改密码成功';
				} else {
					 mesTitle = '修改密码失败';
				}
				$.messager.show({ 
					 title: mesTitle,
					 msg: result.msg
				});
			}
	 	});
	}
</script>

</head>
    <body class="easyui-layout">  
        <!-- 正上方panel -->  
        <div data-options="region:'north',border:false" style="height:60px;background:#46B43B;padding:0px">
        <!-- <div class="main_header" region="north" style="height:70px;padding:5px;" > -->
			<div class="header_left"></div>
			
	        <div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
	            <strong><SPAN><%=user.getUsername() %></SPAN></strong>&nbsp;欢迎你！
	            <SPAN id=clock style="color: blue;"></SPAN>
				<SCRIPT type=text/javascript> 
				    var clock = new Clock();
				    clock.display(document.getElementById("clock"));
				</SCRIPT>
	        </div>
	        <div style="position: absolute; right: 0px; bottom: 0px; ">
	            <!-- <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>  -->
	            <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a> 
	            <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
	        </div>
				
	        <!-- <div id="layout_north_pfMenu" style="width: 120px; display: none;">
	            <div onclick="changeTheme('default');">默认</div>
	        </div> -->
	        <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	            <div onclick="userInfo();">个人信息</div>
	            <div onclick="resetPass();">设置密码</div>
	        </div>
	        <div id="layout_north_zxMenu" style="width: 100px; display: none;">
	            <div onclick="logout();">重新登录</div>
	            <div class="menu-sep"></div>
	            <div onclick="logoutClose(true);">退出系统</div>
	        </div>
	        
        </div>  
	    	
		<!-- 左侧菜单 -->
        <div data-options="region:'west',href:''" title="导航菜单" style="width: 200px; padding: 0px;">
			<ul id="mainMenu"></ul>
			
		</div>
		
        <!-- 正中间panel -->  
        <!-- <div region="center" title="功能区" >   -->
        <div region="center">  
            <div class="easyui-tabs" id="mainTabs" fit="true" border="false">  
                <div title="欢迎使用" style="padding:20px;">   
                    <div style="margin-top:20px; float:left;min-width:600px;widht: 600px; height: 90%; ">  
                        <h1 style="font-size:24px;"> 兑 奖 网 </h1>
                        <h2 style="color: darkblue;"> = = = 说 明 文 字 = = = </h2>
                    </div>  
                </div>  
            </div>  
        </div>  
        
        <!-- 修改个人信息框 -->
		<div id="dlg" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" novalidate>
				<div class="fitem" style="display: none;">
					<label>ID:</label> <input name="id" class="easyui-textbox" required="true" readonly>
				</div>
				<div class="fitem">
					<label>登录账号:</label> <input name="username" class="easyui-textbox" required="true" readonly>
				</div>
				<div class="fitem">
					<label>邮箱地址:</label> <input name="email" class="easyui-textbox">
				</div>
				<div class="fitem">
					<label>性别:</label> 
					<input type="radio" name="gender" id="gender" value="男" style="width:50px;">男</input>
					<input type="radio" name="gender" id="gender" value="女" style="width:50px;">女</input>
				</div>
				<div class="fitem">
					<label>QQ:</label> <input name="qq" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>微信:</label> <input name="weixin" class="easyui-textbox" >
				</div>
				<div class="fitem">
					<label>微信:</label> <input name="weixin" class="easyui-textbox" >
				</div>
			</form>
		</div>
		
		<!-- 修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="saveUserInfo()" style="width:90px">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
		
		<!-- 修改密码框 -->
		<div id="dlg_pass" class="easyui-dialog"
			style="width:400px;height:400px;padding:30px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm_pass" method="post" novalidate>
				<div class="fitem" style="display: none;">
					<label>ID:</label> <input name="id" class="easyui-textbox" required="true" readonly>
				</div>
				<div class="fitem">
					<label>当前密码:</label> <input name="password" class="easyui-textbox">
				</div>
				<div class="fitem">
					<label>重置密码:</label> <input id="resetPass" name="resetPass" class="easyui-textbox">
				</div>
				<div class="fitem">
					<label>重复重置密码:</label><input id="resetPass2" name="resetPass2" class="easyui-textbox">
				</div>
			</form>
		</div>
		
		<!-- 修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" onclick="modifyPass()" style="width:90px">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg_pass').dialog('close')"
				style="width:90px">取消</a>
		</div>
        
        <!-- 正下方panel -->  
        <div region="south" style="height:24px;line-height:22px;" align="center">  
            <label style="font-size:11px;">&nbsp;&nbsp;&nbsp;CRM-NEW&nbsp;&nbsp;&nbsp;</label>  
        </div>   
    </body>
</html>
