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
}

.navbar-brand, .nav-link {
	color: #f8f9fa !important;
	font-weight: bold;
	padding: 0 15px;
}

.nav-link:hover {
	color: #adb5bd !important;
}

.navbar-toggler-icon {
	color: #f8f9fa;
}

.container-md {
	text-align: center;
}

body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
	margin: 0;
	padding: 0;
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

.navbar .navbar-brand, .navbar .nav-link {
	color: #f8f9fa !important;
}
</style>
</head>
<body>

	<div class="container-md">
		<h1 class="text-center mt-4 mb-4">My Spring Project!</h1>
	</div>

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
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="/"> index </a></li>
					<li class="nav-item"><a class="nav-link"
						href="/board/register"> 글 작성 </a></li>
					<li class="nav-item"><a class="nav-link" href="/board/list">
							게시판 보기 </a></li>
					<li class="nav-item"><a class="nav-link" href="#"> Link 3
					</a></li>
					<li class="nav-item"><a class="nav-link" href="#"> Link 4
					</a></li>
				</ul>
			</div>

		</div>
	</nav>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
