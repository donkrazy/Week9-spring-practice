<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="user">
				<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath}/user">
					<input type="hidden" name="a" value="modify">
					
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${userVo.getName() }">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<c:choose>
							<c:when test='${userVo.gender == "F" }'>
								<label>여</label> <input type="radio" name="gender" value="F" checked="checked">
								<label>남</label> <input type="radio" name="gender" value="M">
							</c:when>
							<c:otherwise>
								<label>여</label> <input type="radio" name="gender" value="F" >
								<label>남</label> <input type="radio" name="gender" value="M" checked="checked">
							</c:otherwise>
						</c:choose>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>