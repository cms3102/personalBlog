<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ include file="login_cookie.jsp" %>


<header id="header" class="skel-layers-fixed">
	<h1><a href="index.jsp">Sergio's 드라마 블로그</a></h1>
	<nav id="nav">
	  <ul>
	  <% if(userId == null) { %>
	    <li><a href="index.jsp">홈</a></li>
	    <li><a href="no-sidebar.jsp">블로그 소개</a></li>
	    <li><a href="data.jsp">자료실</a></li>
	    <li><a href="guest_book.jsp">방명록</a></li>
	    <li><a href="free_board.jsp">자유게시판</a></li>
	    <li><a id="open1" href="#content" class="button alt" >회원가입</a></li>
	    <li><a id="open2" href="#elements" class="button alt" >로그인</a></li>
	  <% } else { %>
	    <li><a href="index.jsp">홈</a></li>
	    <li><a href="no-sidebar.jsp">블로그 소개</a></li>
	    <li><a href="data.jsp">자료실</a></li>
	    <li><a href="guest_book.jsp">방명록</a></li>
	    <li><a href="free_board.jsp">자유게시판</a></li>
	    <li id="welcome"><%= userId %>님 환영합니다.</li>
	    <li><a href=logout_action.jsp>로그아웃</a></li>
	  <% }; %>
	  </ul>
	</nav>
</header>