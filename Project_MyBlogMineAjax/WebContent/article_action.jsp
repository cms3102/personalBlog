<%@page import="kr.or.kosta.blog.freeboard.dao.JdbcBoardDao"%>
<%@page import="kr.or.kosta.blog.freeboard.dao.BoardDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.JdbcGuestBookDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.GuestBookDao"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>


<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */
String ip = request.getRemoteAddr();
%>

<jsp:useBean id="article"  class="kr.or.kosta.blog.freeboard.domain.Article" scope="request"/>
<jsp:setProperty property="*" name="article"/>
<jsp:setProperty property="ip" name="article" value="<%= ip %>"/>


<%
BoardDao boardDao = (BoardDao) DaoFactory.getInstance().getDao(JdbcBoardDao.class);
boardDao.write(article);

response.sendRedirect("free_board.jsp");
%>



</body>
</html>