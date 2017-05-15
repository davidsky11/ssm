<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="vino" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		兑奖管理 <small></small>
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
								<h3 class="box-title"><span id="myTitle">兑奖统计</span></h3>
							</div>
							<div class="box-body">
								<table class="table">
								<tr><td>兑奖次数:</td><td>${exchangeCount}次</td></tr>	
								<tr><td>奖金总额:</td><td>￥${exchangeAmount}元</td></tr>	
								<tr><td>积分数目:</td><td>总 - ${user.points}分 / 剩 - ${user.noUsePoints}分</td></tr>	
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
