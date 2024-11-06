<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<jsp:include page="../layout/header.jsp" />
<div class="container-md">
	<h1>User Modify Page...</h1>
	<hr>
	<sec:authentication property="principal.uvo" var="uvo" />
	<form action="/user/modify" method="post">
		<div class="col">
			<div class="card border-dark mb-3" style="max-width: 540px;">
				<div class="row g-0">
					<div class="col-md-4">
						<img src="/resources/image/image.jpg" class="img-fluid rounded-start" alt="...">
					</div>
					
					<div class="col-md-8">
						<div class="card-body">
							<input type="hidden" name="email" value="${uvo.email }">
							<h5 class="card-title">
								<input type="text" name="nickName" class="form-control" value="${uvo.nickName }">
								<input type="text" name="pwd" class="form-control" placeholder="password...">
							</h5>
							<p class="card-text">${uvo.email }(${uvo.regDate })</p>
							<p class="card-text">
								<small class="text-body-secondary">Last login ${uvo.lastLogin } ago</small>
							</p>
							<c:forEach items="${uvo.authList }" var="auths">
								<span class="badge rounded-pill text-bg-success">${auths.auth }</span>
							</c:forEach>
							<button type="submit" class="btn btn-primary btn-sm">modify</button>
							<a href="/user/remove">
							<button type="button" class="btn btn-danger btn-sm">remove</button></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<jsp:include page="../layout/footer.jsp" />