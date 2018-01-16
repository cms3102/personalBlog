package kr.or.kosta.blog.freeboard.dao;

import java.sql.Connection;
import java.util.List;

import kr.or.kosta.blog.freeboard.domain.Article;
import kr.or.kosta.blog.guestbook.domain.GuestBook;

public interface BoardDao {
	
//	신규 글 등록
	public void write(Article article);
	
//	order_no 올리기
	public void increaseOrder(Article parent_article);
	
//	답글 등록
	public void reply(Article article, Article parent_article);
	
//	 글 수정
	public void rewrite(Article article);
	
//	글 삭제
	public void delete(int article_id);
	
//	전체 글 목록 조회
	public List<Article> listAll();
	
//	페이지별 글 목록 조회 
	public List<Article> pageList(String request_page);
	
//	글 상세 조회
	public Article showDetail(int article_id);
	
//	조회 수 올리기
	public void increaseHits(int article_id);
	
//	글 검색
	public List<Article> search(String request_page, String condition, String keyword);
	
//	검색된 글 수 체크
	public int searchedArticles(String condition, String keyword);
	
//	페이지 번호 출력
	public String totalArticles();
	
}
