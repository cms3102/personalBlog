<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html> <%-- 에러 처리 jsp --%>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
다음과 같은 에러가 발생하였습니다.<br>
<%= exception.toString() %><br>
<%= exception.getMessage() %><br>

<%
if(exception instanceof NullPointerException){
%>

너 바보냐

<%  
} else if(exception instanceof ArithmeticException){
%>

숫자는 0으로 나눌 수 없습니다.

<%	
}
%>

</body>
</html>