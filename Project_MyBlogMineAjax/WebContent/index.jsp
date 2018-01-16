<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/login_cookie.jsp" %>

<!DOCTYPE HTML>
<%-- 전체 문서 시작 --%>
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
        <script src="js/validator.js"></script>
        <script src="js/clickevent_main.js"></script>
        <script src="js/ajax.js"></script>
		<style type="text/css">
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-xlarge.css" />
			<%@ include file="css/sign_up.css" %>
		</style>
    <script type="text/javascript">
    window.onload = function() {
		
    	document.getElementById("signup1").onclick = open1;
    	document.getElementById("login1").onclick = open2;
    	document.getElementById("close1").onclick = close1;
    	document.getElementById("close2").onclick = close1;
    	document.getElementById("close3").onclick = close2;
    	document.getElementById("close4").onclick = close2;
    	document.getElementById("open1").onclick = open1;
    	document.getElementById("open2").onclick = open2;
    	var id = document.getElementById("input_name");
    	id.onkeyup = function() {
    		check_id(this);			
		}
	}
    
    function check_id(id) {
    	var idVal = id.value;
	 	if(idVal.length >= 6){
    		ajax({
			method: "get",
			url: "check_id/check_id_action.jsp",
			param: "id=" + id,
			callback: receiveMessage
    		})
    	}			
	}

	
	function receiveMessage(request) {
		var result = request.responseText;
		/* var result = request.responseText.trim(); */ /* action페이지의 라인 바꾸는 것도 공백으로 출력되어 넘어오기 때문에 없애주는 것 */
		var resultBox = document.getElementById("check_id1")
		if(result == "0"){
			resultBox.innerHTML = "사용 불가"
			resultBox.style.color = "red";
		} else {
			resultBox.innerHTML = "사용 가능"
			resultBox.style.color = "blue";
			document.getElementById('sign_up').value = "a";
		}
	}
    </script>
	</head>
	<body id="top">
	
		<%-- Header 시작 --%>
			<jsp:include page="include/top.jsp"/>
        <%-- Header 끝 --%>

		<%-- Banner 시작 --%>
			<section id="banner">
				<div class="inner">
					<h2>Drama World</h2>
					<p>Welcome / Bienvenido / 이랏샤이마세</p>
					<ul class="actions">
                    <% if(userId == null) { %>
						<li><a id="signup1" href="#content" class="button big alt">회원가입</a></li>
						<li><a id="login1" href="#elements" class="button big alt">로그인</a></li>
                    <%} else {} %>
					</ul>
				</div>
			</section>
        <%-- Banner 끝 --%>
		
        <%-- 회원가입 페이지 시작 --%>	
			<%@ include file="include/sign_up.jsp" %>
        <%-- 회원가입 페이지 끝 --%>
        
        <%-- 로그인 페이지 시작 --%>
			<%@ include file="include/sign_in.jsp" %>
        <%-- 로그인 페이지 끝 --%>
      
		<%-- Footer 시작 --%>
			<jsp:include page="include/footer.jsp"></jsp:include>
        <%-- Footer 끝 --%>
        
	</body>
</html>