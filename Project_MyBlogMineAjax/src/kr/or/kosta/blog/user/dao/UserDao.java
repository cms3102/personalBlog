package kr.or.kosta.blog.user.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.or.kosta.blog.user.domain.User;

public interface UserDao {
	
	//회원 등록
	public void create(User user);
	
	// 아이디로 회원 조회
	public User read(String id);
	
	//전체회원 반환
	public List<User> listAll();
	
	//전체 사원 목록 반환
	public List<Map<String, String>> employeeList();
	
	// 회원 여부 체크
	public User isMember(String id, String passwd);	
	
	// 아이디 중복 체크
	public String checkId(String id);
	
	// 이메일 중복 체크
	public boolean checkEmail(String userEmail);
}
