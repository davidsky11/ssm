<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body class="timeBody">
<div class="timeContent">
	<article class="article">
		<c:forEach items="${srs}" var="sr" varStatus="status">
			<section style="display:block;padding: 0 0 17px; position: relative;">
				<span class="point-time point-green"></span>
				<time datetime="2016-11" style="width: 15%; display: block; position: absolute;">
					<span><fmt:formatDate pattern="yyyy-MM" value="${sr.scanTime}" /></span>
					<span><fmt:formatDate pattern="dd" value="${sr.scanTime}" /></span>
				</time>
				<aside style="color: #3a3a38; margin-left: 25%; padding-bottom: 15px;">
					<p class="things">[<strong>${sr.city}</strong>]  ${sr.user.userAlias}(${sr.user.username}) @ <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${sr.scanTime}" /></p>
					<p class="brief"><span class="text-green">${sr.formattedAddress} ${sr.sematicDescription}</span></p>
				</aside>
			</section>
		</c:forEach>
	</article>
</div>
</body>
			