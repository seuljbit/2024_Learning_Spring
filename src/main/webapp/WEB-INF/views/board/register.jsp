<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .form-container {
        max-width: 100%; /* 목록 페이지와 동일한 넓이로 설정 */
        margin: 0 auto; /* 가운데 정렬 */
        background-color: #ffffff;
        padding: 20px;
        border-radius: 8px;
    }
</style>
</head>
<body>

<jsp:include page="../layout/header.jsp" />

<div class="container mt-5">
    <div class="form-container">
        <h2 class="text-center mb-4">게시글 등록</h2>
        <form action="/board/insert" method="post" enctype="multipart/form-data">
            
            <div class="mb-3">
                <label for="t" class="form-label">제목</label>
                <input type="text" class="form-control" id="t" name="title" placeholder="제목을 입력하세요">
            </div>
            
            <div class="mb-3">
                <label for="w" class="form-label">작성자</label>
                <input type="text" class="form-control" id="w" name="writer" placeholder="작성자명을 입력하세요">
            </div>
            
            <div class="mb-3">
                <label for="file" class="form-label">첨부 파일</label>
                <input type="file" class="form-control d-none" id="file" name="files" multiple="multiple">
                <button type="button" class="btn btn-outline-primary w-100" id="trigger">파일 선택</button>
            </div>
            
            <div class="mb-3" id="fileZone"></div>
            
            <div class="mb-3">
                <label for="c" class="form-label">내용</label>
                <textarea class="form-control" id="c" name="content" rows="5" placeholder="내용을 입력하세요"></textarea>
            </div>
            
            <div class="d-grid"> <button type="submit" class="btn btn-primary" id="regBtn"> 등록 </button> </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script>

<jsp:include page="../layout/footer.jsp" />

</body>
</html>