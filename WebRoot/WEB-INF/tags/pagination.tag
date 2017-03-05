<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.crm.domain.Page" required="true" description="page对象，从controller获取"%>
<%@ attribute name="action" type="java.lang.String" required="true" description="分页请求的action"%>
<%@ attribute name="contentSelector" type="java.lang.String" required="true" description="selector用来筛选响应所填充的目标元素" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
int step = 10;  // 步进10
int current =  page.getPage();			// 当前页，从1开始
int pageSize = page.getPageSize();		// 总共的页数

int begin = 1;
if (current%step > 0) {
	begin += current - current%step;
} else {
	begin += current - current%step - step;
}
//int begin = Math.max(1, current - pageSize/2);			// 起始页
//long end = Math.min(begin + (pageSize - 1), pageSize);	// 结束页
long end = Math.min(begin + step, pageSize);

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
request.setAttribute("action", action);
request.setAttribute("contentSelector", contentSelector);
%>

<div id="pagination" class="box-footer clearfix">
	<ul class="pagination pagination-sm no-margin pull-right">
		 <% if (page.hasPrevious()){%>
               	<li><a href="${action}?pageNumber=1&${searchParams}">首页</a></li>
               	<c:if test="${current > 1}">
                	<li><a href="${action}?pageNumber=${current-1}&${searchParams}">&laquo;</a></li>
                </c:if>
                <c:if test="${current <= 1}">
                	<li class="disabled"><a href="#">&laquo;</a></li>
                </c:if>
         <%}else{%>
                <li class="disabled"><a href="#">首页</a></li>
                <li class="disabled"><a href="#">&laquo;</a></li>
         <%} %>
 
 		<c:set var="flag" value="true" />
		<c:forEach var="i" begin="${begin}" end="${end}">
			<c:if test="${flag}">
	            <c:choose>
	                <c:when test="${i == current}">
	                    <li class="active"><a href="${action}?pageNumber=${i}&${searchParams}">${i}</a></li>
	                </c:when>
	                <c:otherwise>
	                    <li><a href="${action}?pageNumber=${i}&${searchParams}">${i}</a></li>
	                </c:otherwise>
	            </c:choose>
            </c:if>
            <c:if test="${i - begin >= step}">
		        <c:set var="flag" value="false"/>
		    </c:if>
        </c:forEach>
	  
	  	 <% if (page.hasNext()){%>
               	<li><a href="${action}?pageNumber=${current+1}&${searchParams}">&raquo;</a></li>
                <li><a href="${action}?pageNumber=${pageSize}&${searchParams}">尾页</a></li>
         <%}else{%>
                <li class="disabled"><a href="#">&raquo;</a></li>
                <li class="disabled"><a href="#">尾页</a></li>
         <%} %>

	</ul>
</div>

<script type="text/javascript">
$(document).ready(function(){
	 $("#pagination li a").click(function(){
		 if($(this).parent().hasClass("disabled")){//禁止分页控件点击disable的按钮导致发送ajax请求
			 return ;
		 }
			
		 $.ajax({
            async: false,
            cache: false,
            type: 'GET',            
            url: this.href,//请求的action路径
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success:function(data){ //请求成功后处理函数。          
            $("${contentSelector}").html(data);
                   
            }
	 });
	 return false;       		
 	});
 	});
 	
</script>	


