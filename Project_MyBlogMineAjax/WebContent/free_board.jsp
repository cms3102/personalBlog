<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="include/sign_up.jsp" %>
<%@ include file="include/sign_in.jsp" %>
<%@ include file="include/login_cookie.jsp" %>
<%@ page import="kr.or.kosta.blog.common.db.DaoFactory" %>
<%@ page import="kr.or.kosta.blog.freeboard.dao.BoardDao" %>
<%@ page import="kr.or.kosta.blog.freeboard.dao.JdbcBoardDao" %>
<%@ page import="kr.or.kosta.blog.freeboard.domain.Article" %>


<%
request.setCharacterEncoding("utf-8"); /* 한글 정보가 들어올 때 인코딩 */

/* 요청 페이지값 저장 */
String request_page =  request.getParameter("request_page");
if(request_page == null) {
	request_page = "1";
}

/* 현재 페이지, 검색 조건, 검색어 저장 */
int currentPage = Integer.parseInt(request_page);
String condition = request.getParameter("condition");
String keyword = request.getParameter("keyword");
%>

<!-- 출력할 게시글 리스트 받기 -->
<% BoardDao boardDao = (BoardDao) DaoFactory.getInstance().getDao(JdbcBoardDao.class);
List<Article> list = boardDao.pageList(request_page);  
double total = Double.parseDouble(boardDao.totalArticles());

/* 검색값 존재 시 게시글 리스트 받기 */
if(condition != null) {
	list = boardDao.search(request_page, condition, keyword);
    total = boardDao.searchedArticles(condition, keyword);
}

/* 게시판명 동적 변경 */
int board_id = list.get(0).getBoard_id();
String board_name = "";
if(board_id == 1){
  board_name = "자유게시판";
}

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
			<%@ include file="css/free_board.css" %>
		</style>
    <script type="text/javascript">
    </script>
	</head>
	<body id="top">

		<!-- Header -->
			<jsp:include page="include/top.jsp"/>

		<!-- Main -->
			<section id="main" class="wrapper style1">
				<header class="major">
                  
					<h2><%= board_name %></h2>
					<!-- <p>Tempus adipiscing commodo ut aliquam blandit</p> -->
				</header>
        
				<div class="container">
                    <hr class="major" />
                    <div class="free1">
                    <label class="free2">전체 게시물 수 : <%= (int)total %></label>
                    <table class="free3">
                    <thead>
                    <tr>
                    <td>번호</td>
                    <td>제목</td>
                    <td>작성자</td>
                    <td>작성일</td>
                    <td>아이피</td>
                    <td>조회</td>
                    </tr>
                    </thead>
                      <!-- 게시물 번호 계산 -->
                      <%
                      int count = 0;
                      if(request_page.equals("1")){
                    	count = (int)total +1;
                      } else {
                    	count = ( (int)total +1) - ((currentPage -1)*10);
                      }
                      
                      /* 게시글 출력 */
                      for(int i=0; i<list.size(); i++) {
                      Article article = list.get(i);
                      int level_no = article.getLevel_no();
                      count--;
                      %>
                      <tr>
                      <td> <%= count %> </td>
                      <td id="subject2">
                      <!-- 답글 구분 및 들여쓰기 -->
                      <%
                      for(int j=0; j<level_no; j++) {
                      %>
                      <img src="images/blank.png" width="12" height="12">
                      <%                    	  
                      }
                      %>
                      <%
                      if(level_no > 0){
                      %>
                      <img src="images/reply.png" width="15" height="15"> [re]
                      <%  
                      }
                      %>
                      <!-- 삭제된 글 여부 확인 및 처리 -->
                      <%
                      if(article.getSubject().equals("삭제된 글입니다.")){ %>
                        <label class="deleted"><%= article.getSubject() %></label>
                      <%  
                      } else {
                      %>
                        <a class="subject" href="posting_content.jsp?article_id=<%= article.getArticle_id() %>"> <%= article.getSubject() %> </a>
                      <% } %>
                      </td>
                      <td> <%= article.getWriter() %> </td>
                      <td> <%= article.getRegdate() %> </td>
                      <td> <%= article.getIp() %> </td>
                      <td> <%= article.getHitcount() %> </td>
                      </tr>
                      <%     
                        }
                      %>
                    </table>
                    <form class="pgpgpg">
                    <!-- 페이징 처리를 위한 계산 -->
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
                    
                    <div class="jumpset">
                    <!-- 페이지 번호 출력 및 이동 버튼 생성  -->
                    <%
                    if(condition != null){
                    %>
                      <div>
                        <a class="jumppage" href="?request_page=1&condition=<%= condition %>&keyword=<%= keyword %>" >첫 페이지</a>
                        <a class="jumppage" href="?request_page=<%= rightBefore(currentPage) %>&condition=<%= condition %>&keyword=<%= keyword %>" >이전</a>
                      </div>
                    <%
                    } else {
                    %>                    
                      <div>
                       <a class="jumppage" href="?request_page=1" >첫 페이지</a>
                       <a class="jumppage" href="?request_page=<%= before(startPage) %>" >이전</a>
                      </div>
                    <% } %>
                    </div>
                    <%
                    for(int i=startPage; i<lastPage; i++) {
                    if(i != currentPage) {
                    %>
                    <%
                      if(condition != null){
                    %>
                       <a class="pagenum2" href="?request_page=<%= i %>&condition=<%= condition %>&keyword=<%= keyword %>" ><%= i %></a>
                       
                    <%} else {%>
                    
                       <a class="pagenum2" href="?request_page=<%= i %>" ><%= i %></a>
                    <%
                    }
                    } else if(i == currentPage) { 
                    %>
                     <%
                      if(condition != null){
                    %>
                       <a class="pagenum1" href="?request_page=<%= i %>&condition=<%= condition %>&keyword=<%= keyword %>" ><%= i %></a>
                       
                    <%} else {%>
                    
                       <a class="pagenum1" href="?request_page=<%= i %>" ><%= i %></a>
                    <%
                    }
                    }
                    }
                     %>
                    <div class="jumpset"> 
                     <%
                    if(condition != null){
                    %>
                      <div>
                        <a class="jumppage" href="?request_page=<%= rightAfter(currentPage, (int)pageNum) %>&condition=<%= condition %>&keyword=<%= keyword %>" >다음</a>
                        <a class="jumppage" href="?request_page=<%= (int)pageNum %>&condition=<%= condition %>&keyword=<%= keyword %>" >끝 페이지</a>
                      </div>
                    <%
                    } else {
                    %>                    
                      <div>
                       <a class="jumppage" href="?request_page=<%= next(startPage, (int)pageNum) %>" >다음</a>
                       <a class="jumppage" href="?request_page=<%= (int)pageNum %>" >끝 페이지</a>
                      </div>
                    <% } %>
                    </div>
                    </form>
                    </div>
                  <hr class="major" />
                    <% if(userId != null) { %>
                      <form id="com1">
                          <select name="condition">
                            <option value="subject">제목</option>
                            <option value="content">내용</option>
                            <option value="writer">작성자</option>
                          </select>
                          <input id="txt1" type="text" name="keyword">
                          <button type="submit">검색</button>
                    </form>
                          <a class="search1" href="index.jsp">홈으로</a>
                          <a class="search1" href="posting.jsp">글쓰기</a>
                     <%} else { %>
                       <label class="notice">글 작성 및 검색을 위해 로그인 해 주세요.</label>                       	 
                     <% } %>
                    <hr id="hr1" class="major" />
				</div>
			</section>

		<!-- Footer -->
			<jsp:include page="include/footer.jsp"></jsp:include>

	</body>
</html>