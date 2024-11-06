<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../layout/header.jsp" />
<div class="container-md">
	<h1>Board List Page...</h1>
	<hr>
	<!-- search line -->
	<div class="container-fluid">
		<form action="/board/list" method="get" class="d-flex" role="search">
			<select class="form-select" name="type" id="inputGroupSelect01">
				<option ${ph.pgvo.type == null ? 'selected' : '' }>Choose...</option>
				<option value="t" ${ph.pgvo.type eq 't' ? 'selected' : '' }>title</option>
				<option value="w" ${ph.pgvo.type eq 'w' ? 'selected' : '' }>writer</option>
				<option value="c" ${ph.pgvo.type eq 'c' ? 'selected' : '' }>content</option>
				<option value="tw" ${ph.pgvo.type eq 'tw' ? 'selected' : '' }>title+writer</option>
				<option value="wc" ${ph.pgvo.type eq 'wc' ? 'selected' : '' }>writer+content</option>
				<option value="tc" ${ph.pgvo.type eq 'tc' ? 'selected' : '' }>title+content</option>
				<option value="twc" ${ph.pgvo.type eq 'twc' ? 'selected' : '' }>all</option>
			</select>
			
			<input class="form-control me-2" name="keyword" type="search" placeholder="Search" value="${ph.pgvo.keyword }" aria-label="Search">
			<input type="hidden" name="pageNo" value="1">
			<input type="hidden" name="qty" value="${ph.pgvo.qty }">
			<!--  <button class="btn btn-outline-success" type="submit">Search</button> -->
			<button type="submit" class="btn btn-success position-relative"> Search
				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
					${ph.totalCount } 
					<span class="visually-hidden">unread messages</span>
				</span>
			</button>
		</form>
	</div>
	
	<table class="table table-hover">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">title</th>
				<th scope="col">writer</th>
				<th scope="col">regDate</th>
				<th scope="col">readCount</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="bvo">
				<tr>
					<td>${bvo.bno }</td>
					<td>
						<a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a>
						<c:if test="${bvo.hasFile > 0 }">
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"fill="currentColor" class="bi bi-journal-medical"viewBox="0 0 16 16">
							  <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v.634l.549-.317a.5.5 0 1 1 .5.866L9 6l.549.317a.5.5 0 1 1-.5.866L8.5 6.866V7.5a.5.5 0 0 1-1 0v-.634l-.549.317a.5.5 0 1 1-.5-.866L7 6l-.549-.317a.5.5 0 0 1 .5-.866l.549.317V4.5A.5.5 0 0 1 8 4M5 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5m0 2a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5" />
							  <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2" />
							  <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1z" />
							</svg>
							${bvo.hasFile }
	  					</c:if>
	  					<c:if test="${bvo.cmtQty > 0 }"> <span class="fw-bold text-danger">[${bvo.cmtQty }]</span> </c:if>
					</td>
					<td>${bvo.writer }</td>
					<td>${bvo.regDate }</td>
					<td>${bvo.readCount }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!--pagination line -->
	<!-- <<  >>  : 값이 false disabled -->

	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<li class="page-item ${ph.prev eq false ? 'disabled' : '' }">
				<a class="page-link" href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>

			<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
				<li class="page-item ${ph.pgvo.pageNo eq i ? 'active' : '' }">
					<a class="page-link" href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i }</a>
				</li>
			</c:forEach>

			<li class="page-item ${ph.next eq false ? 'disabled' : '' }">
				<a class="page-link" href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</ul>
	</nav>

	<a href="/"><button type="button" class="btn btn-success">index</button></a>
</div>

<jsp:include page="../layout/footer.jsp" />