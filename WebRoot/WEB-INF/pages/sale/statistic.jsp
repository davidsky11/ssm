<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<header>
	<!-- ECharts3 图表库 -->
	<!-- <script type='text/javascript' src='scripts/js/echarts/echarts.min.js'></script> -->

</header>
	
<div id="saleChartArea" style="height:500px;border:1px solid #ccc;padding:10px;">

</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('saleChartArea'));
    var xAxisDataJson = [];
    var seriesDataJson = [];
    var seriesPieJson = [];
    
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '销售统计分析'
        },
        tooltip: {
        	trigger: 'axis'/*,
        	formatter: "{b}:{c}({a})"*/
        },
        legend: {
            data:['销售量']
        },
        grid: {
        	left: '3%',
        	right: '4%',
        	bootom: '3%',
        	containLabel: true
        },
        xAxis: {
        	type: 'category',
        	data: xAxisDataJson
        },
        yAxis: {
        	type: 'value'/*,
        	boundaryGap: [0, 5]*/
        },
        series: [{
	            name: '销售量',
	            type: 'bar',
	            data: seriesDataJson
        	}/* ,
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
    
    myChart.setOption(option);
    
    function getChartData() {  
    	myChart.showLoading({text: '正在努力的加载数据中...'});
    	
        //通过Ajax获取数据  
        $.ajax({  
            type : "post",  
            async : true, //同步执行  
            url : "ajax_sale_charts.do",  
            data : {},  
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
                	
                	myChart.hideLoading();  
                	myChart.setOption(option); 
                }  
            },  
            error : function(errorMsg) {  
                alert("不好意思，图表请求数据失败啦!");  
                myChart.hideLoading();  
            }  
        });  
    } 
    
    getChartData();
</script> 