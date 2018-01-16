<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="kr.or.kosta.blog.guestbook.domain.GuestBook"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<%@ include file="include/login_cookie.jsp" %>
<%@ page import="kr.or.kosta.blog.guestbook.dao.JdbcGuestBookDao" %>
<%@ page import="kr.or.kosta.blog.guestbook.dao.GuestBookDao"%>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>

<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */

/* 파라미터에서 요청된 페이지 번호를 받아 저장 */
String request_page =  request.getParameter("request_page");

if(request_page == null) {
  request_page = "1";
}

int currentPage = Integer.parseInt(request_page);
%>

<%
/* DB에서 출력할 리스트 받기 */
GuestBookDao guestBookDao = (GuestBookDao) DaoFactory.getInstance().getDao(JdbcGuestBookDao.class);
List<GuestBook> list2;
list2 = guestBookDao.pageList(request_page); 

/* 페이징 처리를 위한 전체 글 수, 총 페이지 수 계산  */
double total = Double.parseDouble(guestBookDao.pageNum()); 
double pageNum =  Math.ceil((double)total/10);
%>


<!DOCTYPE HTML>

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
        <script src="js/clickevent2.js"></script>
		<style type="text/css">
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-xlarge.css" />
            <%@ include file="css/sign_up.css" %>
			<%@ include file="css/comment.css" %>
			<%@ include file="css/guest_book.css" %>
		</style>
	</head>
	<body id="top">

		<!-- Header -->
			<jsp:include page="include/top.jsp"/>

		<!-- Main -->
			<section id="main" class="wrapper style1">
				<header class="major">
					<h2>방명록</h2>
					<!-- <p>Tempus adipiscing commodo ut aliquam blandit</p> -->
				</header>
        
				<div class="container">
                    <hr class="major" />
                    <div>
                    <table class="guest1">
                    <thead>
                    <tr>
                    <td id="guest2">작성자</td>
                    <td id="guest3">내용</td>
                    <td id="guest4">작성일</td>
                    </tr>
                    </thead>
                    <tbody>
                    <% 
                    for (GuestBook pageList : list2) {
                    %>
                    <tr>
                    <td> <%= pageList.getUser_id() %> </td>
                    <td> <%= pageList.getContents().replace("\r\n", "<br>") %> </td>
                    <td> <%= pageList.getRegdate() %> </td>
                    </tr>
                    <% } %>
                    </tbody>
                    </table>
                    <form class="pgpgpg">
                    <!-- 페이지징 처리를 위한 계산 -->
                    <% 
                    int startPage = (((currentPage-1)/10)*10)+1;
                    int lastPage = startPage + 10;
                    if(lastPage > (int)pageNum) {
                      lastPage = (int)pageNum+1;
                    }
                    %>
                    
                     <%!
                    public int before(int startPage) {
                        int before = startPage - 10;
                        if(before < 1){
                          return before = 1;
                        }
                        return before; 
                      }
                    
                    public int next(int startPage, int endPage) {
                        int next = startPage + 10;
                        if(next > endPage ){
                          return next = endPage;
                        } 
                        return next; 
                      }
                    
                    public int rightBefore(int currentPage) {
                        int before = currentPage - 1;
                        if(before < 1){
                          return before = 1;
                        }
                        return before; 
                      }
                    
                    public int rightAfter(int currentPage, int endPage) {
                        int next = currentPage + 1;
                        if(next > endPage ){
                          return next = endPage;
                        } 
                        return next; 
                      }
                    %>
                    
                    <!-- 페이지 번호 출력 및 이동 버튼 생성 -->
                    <div class="jumpset">
                      <a class="jumppage" href="?request_page=1" >첫 페이지</a>
                      <a class="jumppage" href="?request_page=<%= before(startPage) %>" >이전</a>
                    </div>
                    
                    <%
                    for(int i=startPage; i<lastPage; i++) {
                    if(i != currentPage) {
                    %>
                       <a class="pagenum2" href="?request_page=<%= i %>" ><%= i %></a>  
                    <%
                    } else if(i == currentPage) { 
                    %>
                       <a class="pagenum1" href="?request_page=<%= i %>"><%= i %></a>
                    <% } %>
                    <% } %>
                    <div class="jumpset">
                      <a class="jumppage" href="?request_page=<%= next(startPage, (int)pageNum) %>" >다음</a>
                      <a class="jumppage" href="?request_page=<%= (int)pageNum %>" >끝 페이지</a>
                    </div>
                    </form>
                    </div>
					<hr id="hr2" class="major" />
                    <% if(userId != null) { %>
                      <form id="com1" action="guest_book_action.jsp">
                          <input type="hidden" name="user_id" value="<%= userId %>">
                          <textarea rows="2" cols="30" name="contents"></textarea>
                          <button class="regist1" type="submit">작성</button>
                      </form>
                     <%} else { %>
                       <label class="notice">방명록을 작성하시려면 로그인 해 주세요.</label>                       	 
                     <% } %>
                    <hr id="hr1" class="major" />
				</div>
			</section>

		<!-- Footer -->
			<jsp:include page="include/footer.jsp"></jsp:include>
	</body>
</html>