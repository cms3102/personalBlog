<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<!DOCTYPE HTML>

<html>
	<head>
		<title>Sergio's 드라마 블로그</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<!--[if lte IE 8]><script src="js/html5shiv.js"></script><![endif]-->
		<script src="js/jquery.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-layers.min.js"></script>
		<script src="js/init.js"></script>
        <script src="js/clickevent2.js"></script>
		<style type="text/css">
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-xlarge.css" />
			<%@ include file="css/sign_up.css" %>
			<%@ include file="css/comment.css" %>
		</style>
	</head>
	<body id="top">

		<!-- Header -->
			<jsp:include page="include/top.jsp"/>

		<!-- Main -->
			<section id="main" class="wrapper style1">
				<header class="major">
					<h2>자료실</h2>
					<!-- <p>Tempus adipiscing commodo ut aliquam blandit</p> -->
				</header>
				<div class="container">
                    <hr class="major" />
                    <jsp:include page="include/file_list.jsp"></jsp:include>
					<hr class="major" />
				</div>
			</section>

		<!-- Footer -->
			<jsp:include page="include/footer.jsp"></jsp:include>

	</body>
</html>