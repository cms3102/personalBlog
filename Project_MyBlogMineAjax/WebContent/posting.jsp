<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<%@ include file="include/login_cookie.jsp" %>

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
        <script src="js/clickevent_cancel.js"></script>
		<style type="text/css">
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-xlarge.css" />
            <%@ include file="css/sign_up.css" %>
			<%@ include file="css/free_board.css" %>
            <%@ include file="css/posting.css" %>
		</style>
	</head>
	<body id="top">

		<!-- Header -->
			<jsp:include page="include/top.jsp"/>

		<!-- Main -->
			<section id="main" class="wrapper style1">
				<header class="major">
					<h2>신규 글 등록</h2>
					<!-- <p>Tempus adipiscing commodo ut aliquam blandit</p> -->
				</header>
        
				<div class="container">
                    <hr class="major" />
                    <div class="free1">
                    <form action="article_action.jsp">
                    <table class="free3">
                    <tr>
                    <td class="post1">제목</td>
                    <td id="title1" colspan="3"><input type="text" name="subject"></input></td>
                    </tr>
                    <tr>
                    <td class="post1">작성자</td>
                    <td id="myid"> <%= userId %> <input type="hidden" name="writer" value="<%= userId %>"></td>
                    <td class="post1" id="psw">비밀번호</td>
                    <td><input type="password" name="passwd" required></td>
                    </tr>
                    <tr>
                    <td id="post2" colspan="4"> <textarea rows="7" cols="30" name="content"></textarea></td>
                    </tr>
                    </table>
                    <div class="search3">
                      <button id="cancel1" type="reset">취소</button>
                      <button type="submit" name="">등록</button>
                    </div>
                    </form>
                    </div>
					<hr class="major" />
				</div>
			</section>

		<!-- Footer -->
			<jsp:include page="include/footer.jsp"></jsp:include>

	</body>
</html>