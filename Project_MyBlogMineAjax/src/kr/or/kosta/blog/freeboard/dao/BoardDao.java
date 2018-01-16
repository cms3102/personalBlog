package kr.or.kosta.blog.freeboard.dao;

import java.sql.Connection;
import java.util.List;

import kr.or.kosta.blog.freeboard.domain.Article;
import kr.or.kosta.blog.guestbook.domain.GuestBook;

public interface BoardDao {
	
//	�ű� �� ���
	public void write(Article article);
	
//	order_no �ø���
	public void increaseOrder(Article parent_article);
	
//	��� ���
	public void reply(Article article, Article parent_article);
	
//	 �� ����
	public void rewrite(Article article);
	
//	�� ����
	public void delete(int article_id);
	
//	��ü �� ��� ��ȸ
	public List<Article> listAll();
	
//	�������� �� ��� ��ȸ 
	public List<Article> pageList(String request_page);
	
//	�� �� ��ȸ
	public Article showDetail(int article_id);
	
//	��ȸ �� �ø���
	public void increaseHits(int article_id);
	
//	�� �˻�
	public List<Article> search(String request_page, String condition, String keyword);
	
//	�˻��� �� �� üũ
	public int searchedArticles(String condition, String keyword);
	
//	������ ��ȣ ���
	public String totalArticles();
	
}
