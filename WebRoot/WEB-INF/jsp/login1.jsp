<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>兑奖网</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" media="all" />
<!-- <script type="text/javascript" src="js/jquery-1.4.2.js"></script> -->
<script src="js/easyui/jquery.min.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		$('.tabPanel ul li').click(
			function() {
				$(this).addClass('hit').siblings().removeClass('hit');
				$('.panes>div:eq(' + $(this).index() + ')').show().siblings().hide();
			}
		)
	});
	
	var errInfo = "${errInfo}";
	$(document).ready(function(){
		changeCode();
		$("#codeImg").bind("click",changeCode);
		if(errInfo!=""){
			if(errInfo.indexOf("验证码")>-1){
				$("#codeerr").show();
				$("#codeerr").html(errInfo);
				$("#code").focus();
			}else{
				$("#nameerr").show();
				$("#nameerr").html(errInfo);
			}
		}
		$("#loginname").focus();
	});

	function genTimestamp(){
		var time = new Date();
		return time.getTime();
	}

	function changeCode(){
		$("#codeImg").attr("src","code?t="+genTimestamp());
	}
	
	function login(){
			check();
	    	document.loginForm.submit();
		} 
	
	function resetErr(){
		$("#nameerr").hide();
		$("#nameerr").html("");
		$("#pwderr").hide();
		$("#pwderr").html("");
		$("#codeerr").hide();
		$("#codeerr").html("");
	}
	
	function check(){
		resetErr();
		if($("#loginname").val()==""){
			$("#nameerr").show();
			$("#nameerr").html("用户名不得为空！");
			$("#loginname").focus();
			return false;
		}
		if($("#password").val()==""){
			$("#pwderr").show();
			$("#pwderr").html("密码不得为空！");
			$("#password").focus();
			return false;
		}
		if($("#code").val()==""){
			$("#codeerr").show();
			$("#codeerr").html("验证码不得为空！");
			$("#code").focus();
			return false;
		}
		if($(".autologin").is(":checked")){
	    	$(".autologinch").val("Y");
	    }
		return true;
	}
</script>
</head>
<body>
	<form action="login" method="post" name="loginForm" id="loginForm" class="loginForm">
	<div>
		<div class="tabPanel">
			<ul>
				<li class="hit">APP账号</li>
				<li>商户账号</li>
				<!-- <li># # #</li> -->
			</ul>
			<div class="panes">
				<div class="pane" style="display: block;">

					<div align="center">
						<div id="Main">
							<div class="form_div1">
								<span class="span1">用户名：</span>
								<input type="text" name="loginname" id="loginname" class="login_input" value="${loginname }"/>&nbsp;<span id="nameerr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span class="span1">密码：</span>
								<input type="password" name="password" id="password" class="login_input" value="${password }"/>&nbsp;<span id="pwderr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span class="span1">验证码：</span>
								<input type="text" name="code" id="code" class="login_input"/>
								&nbsp;<span id="codeerr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
							</div>
							<div>
								<div class="form_div1">
									<span class="span1"> </span> <input type="submit"
										name="btnLogin" value="" class="Button"
										style="margin-top: 8px" /> <a href="#">忘记密码</a>
								</div>

							</div>
							<!-- <div class="form_div1">
								<input type="button" name="loginBtn" id="loginBtn" value="登录" class="btn" onclick="login()"/>
								<label>　　　</label>
								<input type="reset" name="cancelBtn" id="cancelBtn" value="取消" class="btn"/>
							</div>
							<div style="line-height: 32px; margin-top: 10px;">
								<img src="images/li.png" /> <a href="#">333</a> <img
									src="images/li.png" /> <a href="#">555</a>
							</div> -->
						</div>
					</div>
				</div>
				<div class="pane">
					<div align="center">
						<div id="Main">
							<div class="form_div1">
								<span class="span1">用户名：</span>
								<input type="text" name="loginname" id="loginname" class="login_input" value="${loginname }"/>&nbsp;<span id="nameerr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span class="span1">密码：</span>
								<input type="password" name="password" id="password" class="login_input" value="${password }"/>&nbsp;<span id="pwderr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span class="span1">验证码：</span>
								<input type="text" name="code" id="code" class="login_input"/>
								&nbsp;<span id="codeerr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
							</div>
							<div>
								<div class="form_div1">
									<span class="span1"> </span> <input type="submit"
										name="btnLogin" value="" class="Button"
										style="margin-top: 8px" /> <a href="#">忘记密码</a>
								</div>

							</div>
							
						</div>
					</div>
				</div>
			</div>
			
			<div>
				<div class="pane">
					<div align="center">
						<div id="Main">
							<div class="form_div1">
								<span class="span1">用户名：</span>
								<input type="text" name="loginname" id="loginname" class="login_input" value="${loginname }"/>&nbsp;<span id="nameerr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span class="span1">密码：</span>
								<input type="password" name="password" id="password" class="login_input" value="${password }"/>&nbsp;<span id="pwderr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span class="span1">验证码：</span>
								<input type="text" name="code" id="code" class="login_input"/>
								&nbsp;<span id="codeerr" class="errInfo"></span>
							</div>
							<div class="form_div1">
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
							</div>
							<div>
								<div class="form_div1">
									<span class="span1"> </span> <input type="submit"
										name="btnLogin" value="" class="Button"
										style="margin-top: 8px" /> <a href="#">忘记密码</a>
								</div>

							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
</body>
</html>
