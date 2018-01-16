<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="kr.or.kosta.blog.user.domain.User" %>
<% User user = (User) request.getAttribute("user"); %>
<!DOCTYPE HTML>
<!--
	Ion by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
	<head>
		<title>Sergio's 드라마 블로그</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<!--[if lte IE 8]><script src="js/html5shiv.js"></script><![endif]-->
		<script src="js/jquery.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-layers.min.js"></script>
		<script src="js/init.js"></script>
		<!-- <noscript> -->
		<style type="text/css">
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-xlarge.css" />
			<%@ include file="css/sign_up.css" %>
		</style>
		<!-- </noscript> -->
	</head>
	<body id="top">
	
		<!-- Header -->
			<jsp:include page="include/top.jsp"/>

    <!-- SignUpResult -->
    <div id="id03" class="modala">
  
        <form id="fo01" class="modal-content animate">
          <div>
          <label class="done"><b>회원가입이 완료되었습니다.</b></label>
          <div id="id04">
          <label id="la01" class="infoname"><b>아이디 : <%= user.getId() %></b></label>
          <label id="la01" class="infoname"><b>이름 : <%= user.getName() %></b></label>
          <label id="la01" class="infoname"><b>비밀번호 : <%= user.getPasswd() %></b></label>
          <label id="la01" class="infoname"><b>이메일 : <%=  user.getEmail() %></b></label>
          <label id="la01" class="infoname"><b>전화번호 : <%=  user.getTelephone() %></b></label>
          <label id="la01" class="infoname"><b>직업 : <%=  user.getJob() %></b></label>
          <label id="la01" class="infoname"><b>가입인사 : <%=  user.getMessage() %></b></label>
          </div>
          <br>
          <div>
          <button type="button" class="confirm" onclick="location.href='index.jsp'">확인</button>
          </div>
          </div>
       </form>
       
     </div>  
			
		<!-- Footer -->
		<jsp:include page="include/footer.jsp"></jsp:include>

	</body>
</html>