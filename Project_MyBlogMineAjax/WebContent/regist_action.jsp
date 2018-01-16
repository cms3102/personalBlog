<%@page import="kr.or.kosta.blog.user.domain.User"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>
<%@ page import="kr.or.kosta.blog.user.dao.UserDao" %>
<%@ page import="kr.or.kosta.blog.user.dao.JdbcUserDao" %>


<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */

String telephone1 = request.getParameter("telephone1");
String telephone2 = request.getParameter("telephone2");
String telephone = null;
if(telephone2.length() != 0){
	telephone = telephone1 + telephone2; 
}
%>


<jsp:useBean id="user"  class="kr.or.kosta.blog.user.domain.User" scope="request">
<jsp:setProperty property="*" name="user"/>
<jsp:setProperty property="telephone" name="user" value="<%= telephone %>"/>
</jsp:useBean>

<%
UserDao userdao = (UserDao) DaoFactory.getInstance().getDao(JdbcUserDao.class);
boolean result = userdao.checkEmail(user.getEmail());
%>

<% if(result) { %>

String result = "0"
<jsp:forward page="index.jsp">
<jsp:param value="<%= result %>" name="result"/>
</jsp:forward>

<%} else { 

userdao.create(user);%>

<jsp:forward page="sign_up_result.jsp"/>

<% } %>

