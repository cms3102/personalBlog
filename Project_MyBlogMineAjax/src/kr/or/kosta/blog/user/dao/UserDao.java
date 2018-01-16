package kr.or.kosta.blog.user.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.or.kosta.blog.user.domain.User;

public interface UserDao {
	
	//ȸ�� ���
	public void create(User user);
	
	// ���̵�� ȸ�� ��ȸ
	public User read(String id);
	
	//��üȸ�� ��ȯ
	public List<User> listAll();
	
	//��ü ��� ��� ��ȯ
	public List<Map<String, String>> employeeList();
	
	// ȸ�� ���� üũ
	public User isMember(String id, String passwd);	
	
	// ���̵� �ߺ� üũ
	public String checkId(String id);
	
	// �̸��� �ߺ� üũ
	public boolean checkEmail(String userEmail);
}
