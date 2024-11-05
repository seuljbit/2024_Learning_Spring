<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../layout/header.jsp" />
<div class="container-md">
<h1>User login</h1>
<hr>

<form action="/user/login" method="post">
	<div class="mb-3">
	  <label for="e" class="form-label"> email </label>
	  <input type="text" class="form-control" id="e" name="email" placeholder="email..">
	</div>
	
	<div class="mb-3">
	  <label for="p" class="form-label"> password </label>
	  <input type="text" class="form-control" id="p" name="pwd" placeholder="password..">
	</div>
	
	<!-- 로그인 실패 시 에러 메세지 출력 -->
	<c:if test="${param.errorMessage ne null }">
		<div class="mb-3"> ${param.errorMessage }</div>
	</c:if>
	
	<button type="submit" class="btn btn-primary"> login </button>
</form>

</div>
<jsp:include page="../layout/footer.jsp" />