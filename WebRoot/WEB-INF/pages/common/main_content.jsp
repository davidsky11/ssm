<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script type="text/javascript">
	    var path = "${path}";
	</script>
	
	<c:if test="${userType eq '0'}">
		<!-- <section class="content" id="mainContent"> -->
			<div style="float:left; width:50%;">
				<h1>快乐兑</h1>
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;快乐兑是以二维码/条形码为媒介，利用可变二维码技术，为每个二维码赋予不同的含义。通过一物一码实现大数据营销管理。
				通过APP微信扫码，除了可以实现<span style="color:red;">查询防伪、溯源、防窜货</span>功能，还可以实现<span style="color:red;">扫码兑奖、
				送话费、送Q币、微信红包</span>等多种促销营销活动。通过快乐兑扫码系统，可帮助企业打通线上线下，实现营销闭环，实现O2O。
				</p>
			</div style="float:right; width:50%;">
			<div>
				<img src="${path}/crmnew/img/jieshao.jpg" width="450" height="450" />
			</div>
		<!-- </section> -->
	</c:if>
	<c:if test="${userType eq '1'}">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				<strong> 快乐兑</strong>操作说明
			</h1>
			<ol class="breadcrumb">
				<li><a href=""><i class="fa fa-dashboard"></i> 主页</a></li>
				<!-- <li class="active">Here</li> -->
			</ol>
		</section>

		<!-- Main content -->
		<section class="content" id="mainContent">
			
			<!-- Your Page Content Here！！！！！！ -->
			<h2>创建活动步骤</h2>
			<h3>第<span style="color:green;">1</span>阶段</h3>
				<ul>
					<li><h4>创建活动</h4></li>
						<span>请进入活动管理-活动维护功能</span>
						<p><a href="#" style="color:green">创建一个活动</a></p>
					<li><h4>填写活动内容</h4></li>
						<span>根据厂商活动内容填写活动相关信息</span>
						<p><a href="#" style="color:green;">保存活动内容</a></p>
				</ul>
		
			<h3>第<span style="color:green;">2</span>阶段</h3>
				<ul>
					<li><h4>设置奖项</h4></li>
						<span>请进入活动管理-奖项设置功能</span>
						<p><a href="#" style="color:green">选择一个对应活动项目</a></p>
					<li><h4>创建奖项详情</h4></li>
						<span>建立奖项的相关数据</span>
						<p><a href="#" style="color:green;">设置奖项级别|奖项金额|奖项数量</a></p>
				</ul>
		
			<h3>第<span style="color:green;">3</span>阶段</h3>
				<ul>
					<li><h4>商品生成</h4></li>
						<span>请进入活动管理-商品生成功能</span>
						<p><a href="#" style="color:green">选择一个对应活动项目</a></p>
					<li><h4>设置批次</h4></li>
						<span>根据奖项设置与批次设置生成商品编码</span>
						<p><a href="#" style="color:green;">生成商品编码|下载商品编码表</a></p>
					<li><h4>计算活动预算</h4></li>
					<li><h4>成功创建活动</h4></li>
				</ul>

		</section>
		<!-- /.content -->
	</c:if>
	
	<c:if test="${userType eq '2'}">
		<section class="content" id="mainContent">
			<h1>快乐兑</h1>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;快乐兑是以二维码/条形码为媒介，利用可变二维码技术，为每个二维码赋予不同的含义。通过一物一码实现大数据营销管理。
			通过APP微信扫码，除了可以实现<span style="color:red;">查询防伪、溯源、防窜货</span>功能，还可以实现<span style="color:red;">扫码兑奖、
			送话费、送Q币、微信红包</span>等多种促销营销活动。通过快乐兑扫码系统，可帮助企业打通线上线下，实现营销闭环，实现O2O。
			</p>
			<div>
				<img src="${path}/crmnew/img/jieshao.jpg" width="450" height="400" />
			</div>
		</section>
	</c:if>
	
	<c:if test="${userType eq '3'}">
		<section class="content" id="mainContent">
			<!-- <h1>欢迎进入 快乐兑</h1> -->
			
			<div>
				<img src="${path}/crmnew/img/back_app.png" width="80%" height="80%" />
			</div>
		</section>
	</c:if>
