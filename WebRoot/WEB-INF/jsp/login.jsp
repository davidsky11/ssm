<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢乐兑登录界面</title>
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/login1.css" media="all"/>

<script type="text/javascript" src="js/jquery/jQuery1.7.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.42.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.SuperSlide.js"></script>
<script type="text/javascript" src="js/jquery/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var $tab_li = $('#tab ul li');
		$tab_li.hover(function() {
			$(this).addClass('selected').siblings().removeClass('selected');
			var index = $tab_li.index(this);
			$('div.tab_box > div').eq(index).show().siblings().hide();

			var type = $(this).val();
			$("#userType").attr("value", type);
		});
	});
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
		$("#stu_username_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入用户名') {
				$(this).val('');
			}
		});

		$("#stu_username_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入用户名');
			}
		});

		$("#stu_password_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入密码') {
				$(this).val('');
			}
		});

		$("#stu_password_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入密码');
			}
		});

		$("#stu_code_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入验证码') {
				$(this).val('');
			}
		});

		$("#stu_code_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入验证码');
			}
		});

		$(".stu_login_error").Validform({
			tiptype : function(msg, o, cssctl) {
				var objtip = $(".stu_error_box");
				cssctl(objtip, o.type);
				objtip.text(msg);
			},
			ajaxPost : false
		});

		/* 商户登录 */
		$("#tea_username_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入商户账号') {
				$(this).val('');
			}
		});

		$("#tea_username_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入商户账号');
			}
		});

		$("#tea_password_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入密码') {
				$(this).val('');
			}
		});

		$("#tea_password_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入密码');
			}
		});

		$("#tea_code_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入验证码') {
				$(this).val('');
			}
		});

		$("#tea_code_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入验证码');
			}
		});

		$(".tea_login_error").Validform({
			tiptype : function(msg, o, cssctl) {
				var objtip = $(".tea_error_box");
				cssctl(objtip, o.type);
				objtip.text(msg);
			},
			ajaxPost : false
		});

		/* 系统账号登录 */
		$("#sec_username_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入账号') {
				$(this).val('');
			}
		});

		$("#sec_username_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入账号');
			}
		});

		$("#sec_password_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入密码') {
				$(this).val('');
			}
		});

		$("#sec_password_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入密码');
			}
		});

		$("#sec_code_hide").focus(function() {
			var username = $(this).val();
			if (username == '请输入验证码') {
				$(this).val('');
			}
		});

		$("#sec_code_hide").focusout(function() {
			var username = $(this).val();
			if (username == '') {
				$(this).val('请输入验证码');
			}
		});

		$(".sec_login_error").Validform({
			tiptype : function(msg, o, cssctl) {
				var objtip = $(".sec_error_box");
				cssctl(objtip, o.type);
				objtip.text(msg);
			},
			ajaxPost : false
		});
	});
</script>
<script type="text/javascript">
	$(function() {
		/*$(".screenbg ul li").each(function() {
			$(this).css("opacity", "0");
		});
		$(".screenbg ul li:first").css("opacity", "1");
		var index = 0;
		var t;
		var li = $(".screenbg ul li");
		var number = li.size();
		function change(index) {
			li.css("visibility", "visible");
			li.eq(index).siblings().animate({
				opacity : 0
			}, 3000);
			li.eq(index).animate({
				opacity : 1
			}, 3000);
		}
		function show() {
			index = index + 1;
			if (index <= number - 1) {
				change(index);
			} else {
				index = 0;
				change(index);
			}
		}
		t = setInterval(show, 8000);*/
		// 根据窗口宽度生成图片宽度
		//var width = $(window).width();
		//$(".screenbg img").css("width", width + "px");
	});
</script>

<script type="text/javascript">
	/*$(document).ready(function() {
		changeCode();
		$("img[name='codeImg'").each(function() {
			$(this).bind("click", function() {
				changeCode();
			});
		});
		
		$("input[name='remember1']").click(function() {
			if($("#autologinch1").is(":checked")){
				$("#autologinch1").val("Y");
		    } else {
		    	$("#autologinch1").val("");
		    }
		});
		
		$("input[name='remember2']").click(function() {
			if($("#autologinch2").is(":checked")){
				$("#autologinch2").val("Y");
		    } else {
		    	$("#autologinch2").val("");
		    }
		});
		
		$("input[name='remember3']").click(function() {
			if($("#autologinch3").is(":checked")){
				$("#autologinch3").val("Y");
		    } else {
		    	$("#autologinch3").val("");
		    }
		});
		
	});*/
</script>
</head>

<body>
	<div id="head">
		<span> 欢  乐  兑 </span>
	</div>
	<div id="tab">
		<ul class="tab_menu">
			<li class="selected" value="3">APP用户登录</li>
			<li value="2">商户账户登录</li>
		</ul>
		<div class="tab_box">
			<!-- APP用户登录开始 -->
			<div>
				<div class="stu_error_box"></div>
				<form id="loginForm" name="loginForm" action="login" method="post" class="stu_login_error">
					<input type="hidden" id="userType" name="userType" value="3" />
					<div id="username">
						<label> 用&nbsp;户&nbsp;名：</label> 
						<input type="text"
							id="stu_username_hide" name="username" value="请输入用户名"
							nullmsg="用户名不能为空!" datatype="s3-18" errormsg="用户名范围在3~18个字符之间!"  />
						<!--ajaxurl="demo/valid.jsp"-->
					</div>
					<div id="password">
						<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label> 
						<input type="password"
							id="stu_password_hide" name="password" 
							nullmsg="密码不能为空!" datatype="*3-16" errormsg="密码范围在3~16之间!"  />
					</div>
					<div id="code">
						<label>验证码：</label> 
						<input type="text" id="stu_code_hide" name="code" value="请输入验证码" nullmsg="验证码不能为空!" 
							datatype="*4-4" errormsg="验证码有4位数!"  /> 
						<img id="codeImg" name="codeImg" alt="验证码占位图" title="点击更换" src="" />
					</div>
					<div id="remember">
						<input type="checkbox" id="remember1" name="remember"> <label>记住密码</label>
						<input type="hidden" id="autologinch1" name="autologinch"  class="autologinch" value=""/>
					</div>
					<div id="login">
						<button type="submit">登录</button>
					</div>
				</form>
			</div>
			<!-- APP用户登录结束 -->

			<!-- 商户账号登录开始-->
			<div class="hide">
				<div class="tea_error_box"></div>
				<form action="login" method="post" class="tea_login_error">
					<input type="hidden" id="userType" name="userType" value="2" />
					<div id="username">
						<label>商户账号： </label> <input type="text" id="tea_username_hide"
							name="username" value="请输入商户账号" nullmsg="商户账号不能为空!"
							datatype="s3-18" errormsg="商户账号范围在3~18个字符之间1" />
					</div>
					<div id="password">
						<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label> <input type="password"
							id="tea_password_hide" name="password" value=""
							nullmsg="密码不能为空!" datatype="*3-16" errormsg="密码范围在3~16之间!"
							/>
					</div>
					<div id="code">
						<label>验证码：</label> <input type="text" id="tea_code_hide"
							name="code" value="请输入验证码" nullmsg="验证码不能为空!" datatype="*4-4"
							errormsg="验证码有4位数!" /> 
						<img id="codeImg" name="codeImg" alt="验证码占位图" title="点击更换" src="" />
					</div>
					<div id="remember">
						<input type="checkbox" id="remember2" name="remember"> <label>记住密码</label>
						<input type="hidden" id="autologinch2" name="autologinch"  class="autologinch" value=""/>
					</div>
					<div id="login">
						<button type="submit">登录</button>
					</div>
				</form>
			</div>
			<!-- 商户账号登录结束 -->
			<!-- 系统账户登录开始 -->
			<!-- <div class="hide">
				<div class="sec_error_box"></div>
				<form action="login" method="post" class="sec_login_error">
					<div id="username">
						<label>账&nbsp;&nbsp;&nbsp;号：</label> <input type="text"
							id="sec_username_hide" name="username" value="请输入账号"
							nullmsg="账号不能为空!" datatype="s3-18" errormsg="账号范围在3~18个字符之间!"
							sucmsg="账号验证通过" />
					</div>
					<div id="password">
						<label>密&nbsp;&nbsp;&nbsp;码：</label> <input type="password"
							id="sec_password_hide" name="password" value="请输入密码"
							nullmsg="密码不能为空!" datatype="*3-16" errormsg="密码在3~16位之间!"
							sucmsg="密码验证通过" />
					</div>
					<div id="code">
						<label>验证码：</label> <input type="text" id="sec_code_hide"
							name="code" value="请输入验证码" nullmsg="验证码不能为空!" datatype="*4-4"
							errormsg="验证码有4位数!" sucmsg="验证码验证通过" /> <img id="codeImg"
							name="codeImg" alt="验证码占位图" title="点击更换" src="" />
						<div id="remember">
							<input type="checkbox" id="remember3" name="remember"> <label>记住密码</label>
							<input type="hidden" id="autologinch3" name="autologinch"  class="autologinch" value=""/>
						</div>
						<div id="login">
							<button type="submit">登录</button>
						</div>
				</form>
			</div> -->
			<!-- 系统账号登陆结束 -->
		</div>
	</div>
	<div class="bottom">
		<!-- @2016 dest <a href="javascript:;" target="_blank"> 关于</a> <span>
			copyright </span> -->
	</div>
	<div class="screenbg">
		<img src="img/5.jpg">
		<!-- <ul>
			<li><a href="javascript:;"><img src="img/0.jpg"></a></li>
			<li><a href="javascript:;"><img src="img/1.jpg"></a></li>
			<li><a href="javascript:;"><img src="img/2.jpg"></a></li>
		</ul> -->
	</div>
</body>
</html>
