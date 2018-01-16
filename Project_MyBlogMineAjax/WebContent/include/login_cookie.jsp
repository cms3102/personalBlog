<%@ page import="java.net.URLDecoder" %>
<%
String userId = null;

Cookie[] cookies = request.getCookies();
if (cookies != null) {
  for (Cookie cookie : cookies) {
    if (cookie.getName().equalsIgnoreCase("user")) {
      //userId = cookie.getValue();
      userId = URLDecoder.decode(cookie.getValue(), "utf-8"); // 인코딩된 한글 디코딩
    }
  }
}
%>