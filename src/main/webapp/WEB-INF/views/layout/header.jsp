<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>header.jsp</title>
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
    rel="stylesheet">
<style>
.navbar {
    background-color: #343a40;
    color: #f8f9fa !important;
}

.navbar-brand, .nav-link {
    color: #f8f9fa !important;
    padding: 0 15px;
}

.nav-link:hover {
    color: #adb5bd !important;
}

.navbar-toggler-icon {
    color: #f8f9fa;
}

body {
    font-family: Arial, sans-serif;
    background-color: #f8f9fa;
    margin: 0;
    padding-top: 120px; /* 고정된 헤더 공간 확보 */
}

footer {
    padding: 1rem;
    background-color: #343a40;
    color: white;
    text-align: center;
}

.table th, .table td {
    text-align: center;
}

.project-title {
    padding: 10px 0;
    text-align: center;
    font-size: 24px;
    background-color: #ffffff; /* 배경색을 하얀색으로 설정 */
    color: #343a40; /* 글자색을 navbar와 맞추기 */
    border-bottom: 1px solid #ddd; /* 구분선을 추가하여 구분 */
}

.navbar-expand-lg {
    margin-bottom: 0;
}
</style>
</head>
<body>

    <!-- 상단 고정 헤더 (프로젝트 제목 + 네비게이션 바) -->
    <header class="fixed-top">
        <div class="project-title">My Spring Project!</div>
        <nav class="navbar navbar-expand-lg">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mx-auto mb-2 mb-lg-0">
                        <li class="nav-item"> <a class="nav-link active" aria-current="page" href="/"> index </a> </li>
                        <li class="nav-item"> <a class="nav-link" href="/board/register"> 글 작성 </a> </li>
                        <li class="nav-item"> <a class="nav-link" href="/board/list"> 게시판 보기 </a> </li>
                        <li class="nav-item"> <a class="nav-link" href="#"> Link 3 </a> </li>
                        <li class="nav-item"> <a class="nav-link" href="#"> Link 4 </a> </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
