<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<!DOCTYPE HTML>
<!--
	Ion by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
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
		</style>
	</head>
	<body id="top">

		<!-- Header -->
			<jsp:include page="include/top.jsp"/>

		<!-- Main -->
			<section id="main" class="wrapper style1">
				<header class="major">
					<h2>블로그 소개</h2>
				</header>
				<div class="container">
					<section>
						<a href="#" class="image fit"><img src="images/banner.jpg" alt="" /></a>
						<p> 국내 및 해외의 추천할 만한 드라마를 소개하는 블로그 입니다. </p>
						<p> 점점 발전해 가는 블로그가 될 수 있도록 노력하겠습니다. </p>
					</section>
					<hr class="major" />
				</div>
			</section>

		<!-- Footer -->
		<jsp:include page="include/footer.jsp"></jsp:include>

	</body>
</html>