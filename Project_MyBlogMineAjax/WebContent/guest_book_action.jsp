<%@page import="kr.or.kosta.blog.guestbook.dao.JdbcGuestBookDao"%>
<%@page import="kr.or.kosta.blog.guestbook.dao.GuestBookDao"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>


<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */
%>

<jsp:useBean id="guestbook"  class="kr.or.kosta.blog.guestbook.domain.GuestBook" scope="request"/>
<jsp:setProperty property="*" name="guestbook"/>


<%
GuestBookDao guestBookDao = (GuestBookDao) DaoFactory.getInstance().getDao(JdbcGuestBookDao.class);
guestBookDao.write(guestbook);

response.sendRedirect("guest_book.jsp");
%>


</body>
</html>