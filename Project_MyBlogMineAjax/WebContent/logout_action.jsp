<%@page import="kr.or.kosta.blog.user.domain.User"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>
<%@ page import="kr.or.kosta.blog.user.dao.UserDao" %>
<%@ page import="kr.or.kosta.blog.user.dao.JdbcUserDao" %>

<%

Cookie[] cookies = request.getCookies();
if(cookies != null){
 for(Cookie cookie : cookies){
	 String cookieName = cookie.getName();
   if(cookieName.equals("user")) {
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
    break;
   }
 }
}

response.sendRedirect("index.jsp");
%>

