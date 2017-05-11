<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		个人资料 <small></small>
	</h1>
	<ol class="breadcrumb">
		<li class="active"><a href="">主页</a></li>
	</ol>
</section>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- <div class="col-md-6"> -->
		<div class="box">
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<div class="box box-primary">
							<div class="box-header with-border">
								<h3 class="box-title"><span id="myTitle">个人资料</span></h3>
								
								<%-- <c:if test="${currentUser.userType == '2'}"> --%>
									<div class="pull-right">
				                    	<a id="btnChgPwd" href="javascript:void(0);" onclick="chgPwd();" class="btn btn-default btn-flat">修改密码</a>
				                    </div>
			                    <%-- </c:if> --%>
							</div>
							<div id="myProfile" class="box-body" style="display:block;">
								<table class="table">
								<tr><td>用户名:</td><td>${currentUser.username}</td></tr>	
								<tr><td>别名:</td><td>${currentUser.userAlias}</td></tr>	
								<tr><td>上次登录时间:</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${currentUser.lastLoginTime}"></fmt:formatDate></td></tr>	
								<tr><td>登录时间:</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${currentUser.loginTime}"></fmt:formatDate></td></tr>	
								<tr><td>注册时间:</td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${currentUser.regTime}"></fmt:formatDate></td></tr>	
							
								</table>
							</div>
							
							<div id="chgPwd" class="box-body" style="display:none;">
							<form id="formChgPwd" action="" method="post">
								<table class="table">
									<tr><td>原始密码：</td><td><input id="password" name="password" type="password" /></td></tr>
									<tr><td>新密码：</td><td><input id="newPwd" name="newPwd" type="password" /></td></tr>
									<tr><td>重复新密码：</td><td><input id="newPwdRep" name="newPwdRep" type="password" /></td></tr>
									<tr><td>
										<button type="button" id="commitChg" onclick="toChgPwd();">修改密码</button>
									</td></tr>
								</table>
							</form>
							</div>
						
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col (right) -->
				</div>
				
				
			</div>
			<!-- /.box -->
		</div>
	</div>
</section>
<!-- /.content -->
