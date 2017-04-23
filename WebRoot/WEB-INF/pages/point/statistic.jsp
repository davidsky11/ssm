<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		积分管理 <small></small>
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
								<h3 class="box-title">积分统计</h3>
							</div>
							<div class="box-body" style="display:block;">
								<table class="table">
								<tr><td>累计积分:</td><td>${user.points}</td></tr>	
								<tr><td>当前积分:</td><td>${user.noUsePoints}</td></tr>	
								<tr><td>已兑积分:</td><td>${user.usePoints}</td></tr>	
								</table>
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
