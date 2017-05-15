<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<header>
	<!-- ECharts3 图表库 -->
	<!-- <script type='text/javascript' src='scripts/js/echarts/echarts.min.js'></script> -->

</header>

<section class="content">
	<div class="row">
		<div class="box">
			<div class="box-body">
				<div class="box box-primary">
					<div class="form-group">
						<div class="box-body">
							<div class="row">
								<div class="col-xs-3">
									<div class="col-xs-3"><label>活动： </label>
									</div>
									<div class="col-xs-9">
									<select class="form-control" id="activityId" name="activityId">
										<c:forEach items="${atyList}" var="aty">
											<option value="${aty.id}"
												<c:if test="${activityId eq aty.id }">selected=selected</c:if>>${aty.title}</option>
										</c:forEach>
									</select>
									</div>
								</div>
								
								<div class="col-xs-2">
									<div class="col-xs-3"><label>年份： </label>
									</div>
									<div class="col-xs-9">
									<select class="form-control" id="year" name="year">
										<option value="">请选择年份</option>
										<c:forEach items="${yearArr}" var="arr">
											<option value="${arr}"
												<c:if test="${year eq arr }">selected=selected</c:if>>${arr}</option>
										</c:forEach>
									</select>
									</div>
								</div>
								
								<div class="col-xs-2">
									<div class="col-xs-3"><label>月份： </label></div>
									<div class="col-xs-9"><select class="form-control" id="month" name="month">
										<option value="">请选择月份</option>
										<c:forEach items="${monthArr}" var="mon">
											<option value="${mon}"
												<c:if test="${month eq mon}">selected=selected</c:if>>${mon}</option>
										</c:forEach>
									</select>
									</div>
								</div>
								
								<div class="col-xs-2">
									<div class="col-xs-3"><label>级别:</label></div>
									<div class="col-xs-9">
										<select class="form-control" id="level" name="level">
											<c:forEach items="${levelArr}" var="arr">
												<option value="${arr.key}"
													<c:if test="${arr.value eq level}">selected=selected</c:if>>${arr.value}</option>
											</c:forEach>
										</select>
									</div>
									
								</div>

								<div class="col-xs-3">
									<button id="searchBtn" type="button"
										class="btn btn-info pull-right">统计</button>
								</div>
							</div>
						</div>

						<div class="table-responsive">
							<div id="saleChartArea"
								style="height: 500px; border: 1px solid #ccc; padding: 10px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<script type="text/javascript">
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('saleChartArea'));
	var xAxisDataJson = [];
	var seriesDataJson = [];
	var seriesPieJson = [];

	// 指定图表的配置项和数据
	var option = {
		title : {
			text : '销售统计分析'
		},
		tooltip : {
			trigger : 'axis'/*,
							        	formatter: "{b}:{c}({a})"*/
		},
		legend : {
			data : [ '销售量' ]
		},
		grid : {
			left : '3%',
			right : '4%',
			bootom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'category',
			data : xAxisDataJson
		},
		yAxis : {
			type : 'value'/*,
							        	boundaryGap: [0, 5]*/
		},
		series : [ {
			name : '销售量',
			type : 'bar',
			data : seriesDataJson
		} /* ,
				        	{
				        		name: '人数',
				        		type: 'pie',
				        		radius: '15%',
				        		center: ['83%','25%'],
				        		data: seriesPieJson,
				        		itemStyle: {
				        			emphasis: {
				        				shadowBlur: 10,
				        				shadowOffsetX: 0,
				        				shadowColor: 'rgba(0, 0, 0, 0.5)'
				        			}
				        		}
				        	} */
		]
	};
	
	myChart.on('click', function(params){
		var value = params.name;
		var level = $('#level').val();
		if (level == "year") {
			var idx = value.indexOf("-");
			var year = value.substring(0, idx);
			var month = value.substring(idx+1, value.length);
			$('#year').val(year);
			$('#month').val(month);
			$('#level').val("month");
		}
		if (level == "month") {
			//$('#month').val(params.name);
			$('#level').val("month");
			return false;
		}
		if (level == "" || typeof(level) == "undefined") {
			$('#level').val("month");
			
			var idx = value.indexOf("-");
			var year = value.substring(0, idx);
			var month = value.substring(idx+1, value.length);
			$('#year').val(year);
			$('#month').val(month);
			//alert(month);
		}
		getChartData();
	});
	
	function getChartData() {
		myChart.showLoading({
			text : '正在努力的加载数据中...'
		});

		//通过Ajax获取数据  
		$.ajax({
			type : "post",
			async : true, //同步执行  
			url : "ajax_sale_charts.do",
			data : {
				activityId : $('#activityId').val(),
				year: $('#year').val(),
				month: $('#month').val(),
				level: $('#level').val()
			},
			dataType : "json", //返回数据形式为json  
			success : function(result) {
				xAxisDataJson.length = 0;
				seriesDataJson.length = 0;
				seriesPieJson.length = 0;

				if (result) {
					$.each(result, function(index, obj) {
						seriesDataJson.push(obj.category);
						xAxisDataJson.push(obj.bar);

						/* seriesPieJson.push({
							name: obj.bar,
							value: obj.category
						}); */
					});
				} else {
					alert("没有获取到该活动的销售信息");
				}
				
				myChart.hideLoading();
				myChart.setOption(option, true);
			},
			error : function(errorMsg) {
				alert("不好意思，图表请求数据失败啦!");
				myChart.hideLoading();
			}
			
			//myChart.hideLoading();
			//myChart.setOption(option);
		});
	}
	
	myChart.setOption(option);
	getChartData();
	
	$("#searchBtn").click(function() {
		var level = $('#level').val();
		var year = $('#year').val();
		var month = $('#month').val();

		if (level == "year") {
			if (year == null || year == "")
			{
				alert("请选择年份再进行统计!");
				return false;
			}
			$('#month').val("");
		}
		if (level == "month") {
			//$('#month').val("");
			if (year == null || year == "" || month == null || month == "")
			{
				alert("请选择年份/月份再进行统计!");
				return false;
			}
		}
		if (level == "" || typeof(level) == "undefined") {
			alert("请先选择统计级别再进行统计!");
			return false;
		}
		
		getChartData();
	});

</script>