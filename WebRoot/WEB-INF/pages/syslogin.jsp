<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>快乐兑登录界面</title>
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/login1.css" media="all"/>

<script type="text/javascript" src="js/jquery/jQuery1.7.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.8.2.min.js"></script>
<!-- <script type="text/javascript" src="js/jquery/jquery-1.42.min.js"></script> -->
<script type="text/javascript" src="js/jquery/jquery.SuperSlide.js"></script>

<script type="text/javascript">
	/* $(document).ready(function() {
		var $tab_li = $('#tab ul li');
		$tab_li.hover(function() {
			$(this).addClass('selected').siblings().removeClass('selected');
			var index = $tab_li.index(this);
			$('div.tab_box > div').eq(index).show().siblings().hide();

			var type = $(this).val();
			$("#userType").attr("value", type);
		});
	}); */
</script>
<script type="text/javascript">
	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}
	
	function changeCode() {
		$("img[name='codeImg'").attr("src", "code?t=" + genTimestamp());
	};

	$(function() {
		changeCode();
		$("img[name='codeImg'").each(function() {
			$(this).bind("click", function() {
				changeCode();
			});
		});
		
		/* APP 用户登录*/
		$("#sys_username_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入用户名') {
				$(this).val('');
			}
		});

		$("#sys_username_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入用户名');
			}
		});

		$("#sys_password_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入密码') {
				$(this).val('');
			}
		});

		$("#sys_password_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入密码');
			}
		});

		$("#sys_code_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入验证码') {
				$(this).val('');
			}
		});

		$("#sys_code_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入验证码');
			}
		});

	});
</script>

</head>

<body>
	<div id="head">
		<span> 快  乐  兑  </span>
	</div>
	<div id="tab">
		<!-- <ul class="tab_menu1">
			<li>系统管理员登录</li>
		</ul> -->
		<div class="tab_box">
			<!-- 系统用户登录开始 -->
			<div>
				<form id="loginForm" name="loginForm" action="login" method="post" >
					<input type="hidden" id="userType" name="userType" value="0" />
					<div id="username">
						<label>用户账户：</label> 
						<input type="text"
							id="sys_username_hide" name="username" value="请输入用户名"
							nullmsg="用户名不能为空!" datatype="s3-18" errormsg="用户名范围在3~18个字符之间!"  />
						<!--ajaxurl="demo/valid.jsp"-->
					</div>
					<div id="password">
						<label>用户密码：</label> 
						<input type="password"
							id="sys_password_hide" name="password" 
							nullmsg="密码不能为空!" datatype="*3-16" errormsg="密码范围在3~16之间!"  />
					</div>
					<div id="code">
						<label>验证码：</label> 
						<input type="text" id="sys_code_hide" name="code" value="请输入验证码" nullmsg="验证码不能为空!" 
							datatype="*4-4" errormsg="验证码有4位数!"  /> 
						<img id="codeImg" name="codeImg" alt="验证码占位图" title="点击更换" src="" />
					</div>
					<div id="remember">
						<span name="errorIfo" style="color:red;">${message}</span>
					</div>
					<!-- <div id="remember">
						<input type="checkbox" id="remember1" name="remember"> <label>记住密码</label>
					</div> -->
					<div id="login">
						<button type="submit">登录</button>
					</div>
				</form>
			</div>
			<!-- 系统用户登录结束 -->

		</div>
	</div>
	<div class="bottom">
		<!-- @2016 dest <a href="javascript:;" target="_blank"> 关于</a> <span>
			copyright </span> -->
	</div>
	<div class="screenbg">
		<img src="img/5.jpg">
	</div>
</body>
</html>
