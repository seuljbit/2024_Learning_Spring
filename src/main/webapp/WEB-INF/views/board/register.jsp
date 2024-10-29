<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="../layout/header.jsp" />

<form action="/board/insert" method="post">
	<div class="mb-3">
	  <label for="t" class="form-label"> 제목 </label>
	  <input type="text" class="form-control" id="t" name="title" placeholder="제목">
	</div>
	
	<div class="mb-3">
	  <label for="w" class="form-label"> 작성자 </label>
	  <input type="text" class="form-control" id="w" name="writer" placeholder="작성자">
	</div>
	
	<div class="mb-3">
	  <label for="c" class="form-label"> 내용 </label>
	  <textarea class="text" id="c" name="content" rows="3"></textarea>
	</div>
	
	<div class="col-auto">
	  	<button type="submit" class="btn btn-primary mb-3"> 등록 </button>
	</div>
</form>

<jsp:include page="../layout/footer.jsp" />

</body>
</html>