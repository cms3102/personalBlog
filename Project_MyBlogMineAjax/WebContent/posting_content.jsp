<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<%@ include file="include/login_cookie.jsp" %>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>
<%@ page import="kr.or.kosta.blog.freeboard.dao.BoardDao" %>
<%@ page import="kr.or.kosta.blog.freeboard.dao.JdbcBoardDao" %>
<%@ page import="kr.or.kosta.blog.freeboard.domain.Article" %>

<%
int article_id = Integer.parseInt(request.getParameter("article_id"));
String writer = request.getParameter("writer");

BoardDao boardDao = (BoardDao) DaoFactory.getInstance().getDao(JdbcBoardDao.class);
Article article = boardDao.showDetail(article_id);

%>

<!DOCTYPE HTML>

<html>
	<head>
        <%
        if(writer != null && writer.equals(userId)){
        boardDao.delete(article_id);
        %>
        <jsp:forward page="free_board.jsp"/>
        <%
        } else if(writer != null && !writer.equals(userId)) {
        %>
        <script type="text/javascript">
        alert("본인이 작성한 글만 삭제할 수 있습니다.");
        history.back();
        </script>
        <%  
        }
        %>
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
					<h2>게시글 상세</h2>
					<!-- <p>Tempus adipiscing commodo ut aliquam blandit</p> -->
				</header>
        
				<div class="container">
                    <hr class="major" />
                    <div class="free1">
                    <table class="free3">
                    <thead>
                    <tr>
                    <td class="post1">제목</td>
                    <td colspan="3"><%= article.getSubject() %></td>
                    </tr>
                    <tr>
                    <td class="post1">작성자</td>
                    <td><%= article.getWriter() %></td>
                    <td class="post1">작성일</td>
                    <td><%= article.getRegdate() %></td>
                    </tr>
                    <tr>
                    <td class="post1">아이피</td>
                    <td><%= article.getIp() %></td>
                    <td class="post1">조회 수</td>
                    <td><%= article.getHitcount() %></td>
                    </tr>
                    </thead>
                    <tr>
                    <td id="post2" colspan="4"><%= article.getContent().replace("\r\n", "<br>") %></td>
                    </tr>
                    </table>
                    <% if(userId != null) { %>
                    <div class="search3">
                      <a class="function" href="javascript:history.back();">목록</a>
                      <a class="function" href="posting_content.jsp?article_id=<%= article_id %>&writer=<%= article.getWriter() %>">삭제</a>
                      <a class="function" href="rewrite.jsp?article_id=<%= article_id %>">수정</a>
                      <a class="function" href="reply.jsp?article_id=<%= article_id %>">답글</a>
                    </div>
                     <%} else { %>
                       <label class="notice">글 수정 및 답글을 다시려면 로그인 해 주세요.</label>
                     <% } %>
                    </div>
					<hr id="bline" class="major" />
				</div>
			</section>

		<!-- Footer -->
			<jsp:include page="include/footer.jsp"></jsp:include>

	</body>
</html>