<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../layout/header.jsp" />

<div class="container-md">
    <h1>list</h1> ${ph}
    
	<form action="/board/list" method="get">
	    <div class="input-group mb-3">
	        <select class="form-select border-end-0" name="type" id="inputGroupSelect01" style="flex: 0 0 25%; border-top-right-radius: 0; border-bottom-right-radius: 0;">
	            <option value="twc" ${ph.pgvo.type eq null ? 'selected' : ''}> 전체 </option>
	            <option value="t" ${ph.pgvo.type eq 't' ? 'selected' : ''}> 제목 </option>
	            <option value="w" ${ph.pgvo.type eq 'w' ? 'selected' : ''}> 작성자 </option>
	            <option value="c" ${ph.pgvo.type eq 'c' ? 'selected' : ''}> 내용 </option>
	            <option value="tc" ${ph.pgvo.type eq 'tc' ? 'selected' : ''}> 제목 + 내용 </option>
	            <option value="tw" ${ph.pgvo.type eq 'tw' ? 'selected' : ''}> 제목 + 작성자 </option>
	            <option value="wc" ${ph.pgvo.type eq 'wc' ? 'selected' : ''}> 작성자 + 내용 </option>
	        </select>
	        
	        <input type="text" class="form-control" name="keyword" placeholder="검색어를 입력하세요." value="${ph.pgvo.keyword}" aria-label="Search" aria-describedby="button-addon2" style="border-top-left-radius: 0; border-bottom-left-radius: 0;">
	        <input type="hidden" name="pageNo" value="1">
	        <input type="hidden" name="qty" value="${ph.pgvo.qty}">
	        
	        <!-- <button class="btn btn-outline-secondary" type="submit" id="button-addon2"> 검색 </button>  -->
	        <button class="btn btn-success position-relative" type="submit" id="button-addon2"> 검색
			    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
			        ${ph.totalCount}
			    	<span class="visually-hidden">unread messages</span>
			    </span>
			</button>

	        
	    </div>
	</form>

    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col"> # </th>
                <th scope="col"> title </th>
                <th scope="col"> writer </th>
                <th scope="col"> regDate </th>
                <th scope="col"> readCount </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="bvo">
                <tr>
                    <td> ${bvo.bno} </td>
                    <td> <a href="/board/detail?bno=${bvo.bno}"> ${bvo.title} </a> </td>
                    <td> ${bvo.writer} </td>
                    <td> ${bvo.regDate} </td>
                    <td> ${bvo.readCount} </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <nav aria-label="Page navigation example">
	  <ul class="pagination justify-content-center">
	    <li class="page-item ${ph.prev eq false ? 'disabled' : '' }">
	    	<a class="page-link" href="/board/list?pageNo=${ph.startPage-1}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Previous">
	    		<span aria-hidden="true"> &laquo; 이전 </span>
	    	</a>
	    </li>
	    
	    <c:forEach begin="${ph.startPage}" end="${ph.endPage }" var="i">
	    	<li class="page-item ${ph.pgvo.pageNo eq i ? 'active' : '' }">
	    		<a class="page-link" href="/board/list?pageNo=${i}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">
	    			<span> ${i} </span>
	    		</a>
	    	</li>
	    </c:forEach>
	    
	    <li class="page-item ${ph.next eq false ? 'disabled' : '' }">
	    	<a class="page-link" href="/board/list?pageNo=${ph.endPage+1}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" aria-label="Next">
	    		<span aria-hidden="true"> 다음 &raquo; </span>
	    	</a>
	    </li>
	  </ul>
	</nav>
</div>

<jsp:include page="../layout/footer.jsp" />