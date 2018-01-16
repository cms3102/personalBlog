<%@page import="kr.or.kosta.blog.user.domain.User"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>
<%@ page import="kr.or.kosta.blog.user.dao.UserDao" %>
<%@ page import="kr.or.kosta.blog.user.dao.JdbcUserDao" %>


<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */
String id = request.getParameter("id");
%>

<%
UserDao userdao = (UserDao) DaoFactory.getInstance().getDao(JdbcUserDao.class);
String result = userdao.checkId(id);
%>

<%= result %>