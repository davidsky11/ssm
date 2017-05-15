<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<section class="content">
	<div class="row">
		<div class="box">
			<div class="box-body">
				<div class="box box-primary">
					<div class="form-group">
						<div class="box-body">
							<div class="row">
								<div class="col-xs-3">
									<div class="col-xs-4"> <label>活动:</label></div>
									<div class="col-xs-8">
										<select class="form-control" id="publicCode" name="publicCode">
											<c:forEach items="${atyList}" var="aty">
												<option value="${aty.publicCode}"
													<c:if test="${publicCode eq aty.publicCode }">selected=selected</c:if>>${aty.title}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="col-xs-2">
									<div class="col-xs-3"><label>省份:</label></div>
									<div class="col-xs-9">
										<input id="province" name="province" class="form-control" value="${province}" />
									</div>
								</div>
								
								<div class="col-xs-2">
									<div class="col-xs-3"><label>市/县:</label></div>
									<div class="col-xs-9">
										<input id="city" name="city" class="form-control" value="${city}" />
									</div>
								</div>

								<div class="col-xs-2">
									<div class="col-xs-3"><label>级别:</label></div>
									<div class="col-xs-9">
										<select class="form-control" id="level" name="level">
											<c:forEach items="${levelArr}" var="arr">
												<option value="${arr.key}"
													<c:if test="${arr.value eq level }">selected=selected</c:if>>${arr.value}</option>
											</c:forEach>
										</select>
									</div>
									
								</div>
								<div class="col-xs-3">
									<button id="searchBtn" type="button" class="btn btn-info pull-right">统计</button>
								</div>
							</div>
						</div>

						<div>
							<div id="placeChartArea" style="height: 500px; border: 1px solid #ccc; padding: 10px;">
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<script type="text/javascript">
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('placeChartArea'));
	//var myChartPie = echarts.init(document.getElementById('placeChartAreaPie'));
	
	var xAxisDataJson = [];
	var seriesDataJson = [];
	var seriesPieJson = [];

	// 指定图表的配置项和数据
	var option = {
		title : {
			text : '区域销售统计'
		},
		tooltip : {
			trigger : 'axis',
			formatter: "{b}:{c}(件)"
		},
		legend : {
			data : [ '销售量' ]
		},
		grid : {
			left : '3%',
			right : '4%',
			bootom : '3%',
			width: '75%',
			containLabel : true
		},
		xAxis : {
			type : 'category',
			data : xAxisDataJson
		},
		yAxis : {
			type : 'value',
			boundaryGap: [0, 1]
		},
		series : [ 
		    {
			name : '销售量',
			type : 'bar',
			data : seriesDataJson
			},
        	{
        		name: '销售量',
        		type: 'pie',
        		radius: '28%',
        		center: ['90%','30%'],
        		data: seriesPieJson,
        		itemStyle: {
        			emphasis: {
        				shadowBlur: 10,
        				shadowOffsetX: 0,
        				shadowColor: 'rgba(0, 0, 0, 0.5)'
        			}
        		}
        	} 
		]
	};
	
	myChart.on('click', function(params){
		var level = $('#level').val();
		if (level == "province") {
			$('#province').val(params.name);
			$('#level').val("city");
		}
		if (level == "city") {
			//$('#city').val(params.name);
			$('#level').val("district");
		}
		if (level == "district")
		{
			return false;
		}
		if (level == "" || typeof(level) == "undefined") {
			$('#level').val("province");
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
			url : "ajax_place_charts.do",
			data : {
				publicCode : $('#publicCode').val(),
				province: $('#province').val(),
				city: $('#city').val(),
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

						seriesPieJson.push({
							name: obj.bar,
							value: obj.category
						});
					});
				} else {
					alert("没有获取到该活动的销售信息");
				}
				
				myChart.hideLoading();
				myChart.setOption(option, true);
			},
			error : function(errorMsg) {
				alert("没有找到想要的数据!");
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
		var province = $('#province').val();
		var city = $('#city').val();
		if (level == "province") {
			$('#province').val("");
			$('#city').val("");
		}
		if (level == "city") {
			if (province == null || province == "") {
				alert("请填写省再进行统计！");
				return false;
			}
		}
		if (level == "district") {
			if (province == null || province == "" || city == null || city == "")
			{
				alert("请填写省/市再进行统计!");
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