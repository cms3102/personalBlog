package kr.or.kosta.blog.menus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ���� �ٿ�ε� ó�� ������
 */
public class FileDownloadServlet extends HttpServlet {
	
	private String fileRepository = "F:/KOSTA164/fileDirectory/"; // �ٿ�ε� �� ������ �ִ� ���丮 �ּ�
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("file"); // ���� ������ �̸��� ����
		if (fileName == null || fileName.equals(""))
			return;

		String filePath = fileRepository + fileName;
		File file = new File(filePath);

		// HTTP ������ ������ ĳ�� ��� �ʵ��� ������� ����
		String httpVersion = request.getProtocol();
		if (httpVersion.equals("HTTP/1.0")) {
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
		} else if (httpVersion.equals("HTTP/1.1")) {
			response.setHeader("Cache-Control", "no-cache");
		}
		
		// ���� �ٿ�ε� ó���� ���� ��������� ����Ÿ�� ����
		response.setContentType("application/octet-stream");
		fileName = URLEncoder.encode(fileName, "utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
		response.setHeader("Content-Length", "" + file.length()); // Content-Length : �ٿ�޴� ������ �뷮

		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		try{
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
		}finally{
			if(out != null) out.close();
			if(in != null)  in.close();
		}
	}
}