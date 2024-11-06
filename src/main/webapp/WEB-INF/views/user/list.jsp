<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../layout/header.jsp" />
<div class="container-md">
	<h1>User List Page...</h1>
	<hr>
	<div class="row row-cols-1 row-cols-md-2 g-4">
		<c:forEach items="${userList }" var="uvo">
			<div class="col">
				<div class="card border-dark mb-3" style="max-width: 540px;">
					<div class="row g-0">
						<div class="col-md-4">
							<img src="/resources/image/image.jpg"
								class="img-fluid rounded-start" alt="...">
						</div>
						<div class="col-md-8">
							<div class="card-body">
								<h5 class="card-title">${uvo.nickName }</h5>
								<p class="card-text">${uvo.email }(${uvo.regDate })</p>
								<p class="card-text">
									<small class="text-body-secondary">Last login
										${uvo.lastLogin } ago</small>
								</p>
								<c:forEach items="${uvo.authList }" var="auths">
									<span class="badge rounded-pill text-bg-success">${auths.auth }</span>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>


<%-- <div class="card" style="width: 18rem;">
  <img src="/resources/image/image.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 class="card-title">${uvo.email }(${uvo.nickName })</h5>
    <p class="card-text">가입일:${uvo.regDate }  </p>
     <p class="card-text">최근로그인:${uvo.lastLogin }</p>
     <c:forEach items="${uvo.authList }" var="auths">
     <span class="badge rounded-pill text-bg-success">${auths.auth }</span>
     </c:forEach>
     <br>
    <a href="/" class="btn btn-primary">Go somewhere</a>
  </div>
</div> --%>
<jsp:include page="../layout/footer.jsp" />