package kr.or.kosta.blog.guestbook.dao;

import java.sql.Connection;
import java.util.List;

import kr.or.kosta.blog.guestbook.domain.GuestBook;

public interface GuestBookDao {
	
	//방명록 글 등록
	public void write(GuestBook guestBook);
	
	//전체회원 리스트
	public List<GuestBook> listAll();
	
	//페이지별 출력 리스트 
	public List<GuestBook> pageList(String request_page);
	
	//페이지 넘버 출력
	public String pageNum();
	
}
