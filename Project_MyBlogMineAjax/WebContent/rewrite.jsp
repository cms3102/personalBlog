<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<%@ include file="include/login_cookie.jsp" %>
<%@page import="kr.or.kosta.blog.freeboard.domain.Article"%>
<%@page import="kr.or.kosta.blog.freeboard.dao.JdbcBoardDao"%>
<%@page import="kr.or.kosta.blog.freeboard.dao.BoardDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.JdbcGuestBookDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.GuestBookDao"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>

<%
int article_id = Integer.parseInt(request.getParameter("article_id"));
BoardDao boardDao = (BoardDao) DaoFactory.getInstance().getDao(JdbcBoardDao.class);
Article article =  boardDao.showDetail(article_id);
%>


<!DOCTYPE HTML>

<html>
	<head>
		<title>Sergio's 드라마 블로그</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
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
					<h2>게시글 수정</h2>
					<!-- <p>Tempus adipiscing commodo ut aliquam blandit</p> -->
				</header>
        
				<div class="container">
                    <hr class="major" />
                    <div class="free1">
                    <form action="rewrite_action.jsp">
                    <table class="free3">
                    <tr>
                      <td class="post1">제목 <input type="hidden" name="article_id" value="<%= article.getArticle_id() %>"></td>
                      <td id="title1" colspan="3"><input type="text" name="subject" value="<%= article.getSubject() %>"></td>
                    </tr>
                    <tr>
                      <td class="post1">작성자</td>
                      <td id="myid"> <%= userId %> <input type="hidden" name="writer" value="<%= userId %>"></td>
                      <td class="post1" id="psw">비밀번호</td>
                      <td><input id="repasswd" type="password" name="passwd" required> <input type="hidden" id="expasswd" name="expasswd" value="<%= article.getPasswd() %>"></td>
                    </tr>
                    <tr>
                      <td id="post2" colspan="4"> <textarea rows="7" cols="30" name="content"><%= article.getContent() %></textarea></td>
                    </tr>
                    </table>
                    <div class="search3">
                      <button id="cancel1" type="reset">취소</button>
                      <button id="rewrite" type="submit" onsubmit="return check_pw();">수정</button>
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