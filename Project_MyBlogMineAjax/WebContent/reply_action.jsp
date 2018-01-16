<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="kr.or.kosta.blog.freeboard.domain.Article"%>
<%@page import="kr.or.kosta.blog.freeboard.dao.JdbcBoardDao"%>
<%@page import="kr.or.kosta.blog.freeboard.dao.BoardDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.JdbcGuestBookDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.GuestBookDao"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>


<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */
int parent_id =  Integer.parseInt(request.getParameter("parent_id"));
String ip = request.getRemoteAddr();
%>

<jsp:useBean id="article"  class="kr.or.kosta.blog.freeboard.domain.Article" scope="request"/>
<jsp:setProperty property="*" name="article"/>
<jsp:setProperty property="ip" name="article" value="<%= ip %>"/>


<%
BoardDao boardDao = (BoardDao) DaoFactory.getInstance().getDao(JdbcBoardDao.class);
Article parent_article =  boardDao.showDetail(parent_id);
boardDao.reply(article, parent_article);

response.sendRedirect("free_board.jsp");
%>



</body>
</html>