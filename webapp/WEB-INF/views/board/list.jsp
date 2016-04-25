<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
						 action="${pageContext.request.contextPath}/board"
						 method="get">
					<c:choose>
						<c:when test='${map.keyword == "" }'>
							<input type="text" id="kwd" name="kwd" value="">
						</c:when>
						<c:otherwise>
							<input type="text" id="kwd" name="kwd" value="${map.keyword }">
						</c:otherwise>
					</c:choose>	 
					<input type="submit" value="찾기">
				</form>
				<h4>
					전체 글수: <span>${map.totalCount }</span> 개 
				</h4>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="firstIndex"	value="${map.totalCount - (map.currentPage - 1)*map.sizeList }" />			
					<c:forEach items="${map.list }" var="vo" varStatus="status">
						<tr>
							<td>${firstIndex-status.index }</td>
							<c:choose>
								<c:when test="${vo.depth > 0 }">
									<td style="text-align:left; padding-left:${20*vo.depth }px">
										<img src="${pageContext.request.contextPath}/assets/images/reply.png">
										<a href="${pageContext.request.contextPath}/board/view/${vo.no }">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align:left">
										<a href="${pageContext.request.contextPath}/board/view/${vo.no }">${vo.title }</a>
									</td>
								</c:otherwise>	
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hits }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:choose>
									<c:when test="${ not empty authUser && authUser.no == vo.userNo }">
										<a href="${pageContext.request.contextPath}/board/delete/${vo.no }" class="del">삭제</a>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					
				</table>
				<div class="pager">
					<ul>
						<c:if test="${map.prevPage > 0 }">
							<li><a href="${pageContext.request.contextPath}/board?p=${map.prevPage }&kwd=${map.keyword }">◀</a></li>
						</c:if>
						<c:forEach begin="${map.firstPage }" end="${map.lastPage }" var="page" step="1">
							<c:choose>
								<c:when test="${page == map.currentPage }">
									<li class="selected">${page }</li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/board?p=${page }&kwd=${map.keyword }">${page }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${map.nextPage > 0 }">
							<li><a href="${pageContext.request.contextPath}/board?p=${map.nextPage }&kwd=${map.keyword }">▶</a></li>
						</c:if>	
					</ul>
				</div>
				<div class="bottom">
					<c:if test="${not empty authUser }">
						<a href="${pageContext.request.contextPath}/board/write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>