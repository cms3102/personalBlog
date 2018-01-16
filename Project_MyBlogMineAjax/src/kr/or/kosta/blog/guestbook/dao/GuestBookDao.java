package kr.or.kosta.blog.guestbook.dao;

import java.sql.Connection;
import java.util.List;

import kr.or.kosta.blog.guestbook.domain.GuestBook;

public interface GuestBookDao {
	
	//���� �� ���
	public void write(GuestBook guestBook);
	
	//��üȸ�� ����Ʈ
	public List<GuestBook> listAll();
	
	//�������� ��� ����Ʈ 
	public List<GuestBook> pageList(String request_page);
	
	//������ �ѹ� ���
	public String pageNum();
	
}
