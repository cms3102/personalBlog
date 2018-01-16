<%@page import="kr.or.kosta.blog.user.domain.User"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>
<%@ page import="kr.or.kosta.blog.user.dao.UserDao" %>
<%@ page import="kr.or.kosta.blog.user.dao.JdbcUserDao" %>

<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */
String id = request.getParameter("id");
String passwd = request.getParameter("passwd");

if(id == null || passwd == null){
 return; 
}

UserDao userdao = (UserDao) DaoFactory.getInstance().getDao(JdbcUserDao.class);

User user = userdao.isMember(id, passwd);

String url = null;
if(user != null){
	Cookie userCookie = new Cookie("user", user.getId());
	userCookie.setPath("/");
	response.addCookie(userCookie); 
	url = "index.jsp";
} else {
	url = "index.jsp";
}

response.sendRedirect(url);

%>

