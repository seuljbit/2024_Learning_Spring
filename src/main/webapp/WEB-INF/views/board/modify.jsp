<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 페이지</title>
</head>

<jsp:include page="../layout/header.jsp" />

<div class="container">
    <h2>게시글 상세 정보</h2>
    <hr>
    <form action="/board/update" method="post">
        <div class="mb-3">
            <label for="n" class="form-label"> 게시글 번호 </label>
            <input type="text" class="form-control" id="bno" name="bno" value="${bvo.bno}" readonly>
        </div>

        <div class="mb-3">
            <label for="t" class="form-label"> 제목 </label>
            <input type="text" class="form-control" id="title" name="title" value="${bvo.title}">
        </div>

        <div class="mb-3">
            <label for="writer" class="form-label"> 작성자 </label>
            <input type="text" class="form-control" id="writer" name="writer" value="${bvo.writer}" readonly>
        </div>

        <div class="mb-3">
            <label for="content" class="form-label"> 내용 </label>
            <textarea class="form-control" id="content" name="content" rows="5">${bvo.content}</textarea>
        </div>

        <div class="mb-3">
            <label for="regDate" class="form-label"> 등록일 </label>
            <input type="text" class="form-control" id="regDate" name="regDate" value="${bvo.regDate}" readonly>
        </div>

        <div class="mb-3">
            <label for="readCount" class="form-label"> 조회수 </label>
            <input type="text" class="form-control" id="readCount" name="readCount" value="${bvo.readCount}" readonly>
        </div>

        <button type="submit"> 수정 완료 </button>
    </form>
</div>

<jsp:include page="../layout/footer.jsp" />