<%-- <%@ page import="kr.or.kosta.blog.menus.FileList"%> --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.File"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
  <%@ include file="/css/file_list.css" %>
</style>
<script type="text/javascript" src="js/download.js" ></script> 
</head>
<body>

<table class="file1">

<thead>
<tr>  
<td>번호</td> 
<td>파일명</td> 
<td>파일 용량</td>
<td>다운로드</td>
</tr>
</thead>

<tbody>

<%
        String fileRepository = "C:/Users/Sergio/Desktop/KOSTA164/KOSTA164/fileDirectory/";
        /* String fileRepository = "c:/aaa/"; */

        File files = new File(fileRepository);
		File[] list = files.listFiles();
		
		response.setContentType("text/html; charset=utf-8");
		
		String name;
		Long size;
		
		for (int i = 0; i < list.length; i++) {
		      name = list[i].getName();
		      size = list[i].length();
		      out.println("<tr>");
		      out.println("<td>" + (i+1) + "</td>");
		      out.println("<td>" + name + "</td>");
		      out.println( "<td>" + (size/1024) + "</td>");
		      //out.println( "<td><a href=download.do?file=" + name + ">다운로드</a></td>");
		      //out.println("<td><input type=\"button\" value=\"다운로드\" onclick=\"window.location.href=download.do?file='" + name + "'\"></td>");
		      out.println("<td><input type=\"button\" value=\"다운로드\" name=\"" + name + "\"></td>");
		      out.println("</tr>");
		}
    
%>

</tbody>

</table>
</body>
</html>