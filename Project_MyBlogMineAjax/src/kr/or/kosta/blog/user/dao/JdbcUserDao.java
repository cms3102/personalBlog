package kr.or.kosta.blog.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.or.kosta.blog.common.exception.BlogException;
import kr.or.kosta.blog.user.domain.User;

public class JdbcUserDao implements UserDao {
	
	private DataSource setDataSource;
	
	public JdbcUserDao() {}
	
	public JdbcUserDao(DataSource setDataSource) {
		this.setDataSource = setDataSource;
	}
	
	
	public DataSource getDataSource() {
		return setDataSource;
	}

	public void setDataSource(DataSource setDataSource) {
		this.setDataSource = setDataSource;
	}



	@Override
	public void create(User user){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO users " + 
					 "            (id, " + 
					 "             name, " + 
					 "             passwd, " + 
					 "             email, " + 
					 "             telephone, " + 
					 "             job, " + 
					 "             message) " + 
					 "VALUES      (?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?)"; 
		try {
			con = setDataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPasswd());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getTelephone());
			pstmt.setString(6, user.getJob());
			pstmt.setString(7, user.getMessage());
			pstmt.executeUpdate();
			con.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {}
			throw new BlogException("JdbcUserDao.crate(user) 실패", e);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}

	@Override
	public User read(String id){
		User user = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT id, " + 
				     "       name, " + 
				     "       passwd, " + 
				     "       email, " + 
				     "       TO_CHAR(regdate, 'YYYY/MM/DD') regdate " + 
				     "FROM   users " + 
				     "WHERE  id = ?";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user = createUser(rs);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcUserDao.read(id) 에러 발생", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close(); // 커넥션 종료가 아니라 반납하는것
			} catch (Exception e) {}
		}
		return user;
	}

	
	@Override
	public List<User> listAll(){
		List<User> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT id, " + 
				     "       name, " + 
				     "       passwd, " + 
				     "       email, " + 
				     "       TO_CHAR(regdate, 'YYYY/MM/DD') regdate " + 
				     "FROM   users";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<User>();
			while(rs.next()) {
				User user = createUser(rs);
				list.add(user);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcUserDao.listAll() 에러 발생", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return list;
		
		
		
	}
	
	private User createUser(ResultSet rs) throws SQLException {
		String uid = rs.getString("id");		
		String name = rs.getString("name");		
		String passwd = rs.getString("passwd");		
		String email = rs.getString("email");		
		String regdate = rs.getString("regdate");
		
		User user = new User();
		user.setId(uid);
		user.setName(name);
		user.setPasswd(passwd);
		user.setEmail(email);
		user.setRegdate(regdate);
		return user;
	}
	
	@Override
	public List<Map<String, String>> employeeList() {
		List<Map<String, String>> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT e.employee_id                 eid, " + 
				     "       e.first_name " + 
				     "       || ' ' " + 
				     "       || e.last_name                ename, " + 
			  	     "       TO_CHAR(e.salary, '$999,999') esalary, " + 
				     "       d.department_name             dname, " + 
				     "       l.city                        dcity, " + 
				     "       s.first_name " + 
				     "       || ' ' " + 
				     "       || s.last_name                sname " + 
				     "FROM   employees e " + 
				     "       LEFT OUTER JOIN departments d " + 
				     "                    ON e.department_id = d.department_id " + 
				     "       LEFT OUTER JOIN locations l " + 
				     "                    ON d.location_id = l.location_id " + 
				     "       LEFT OUTER JOIN employees s " + 
				     "                    ON e.manager_id = s.employee_id " +
				     "ORDER BY eid";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<Map<String, String>>();
			ResultSetMetaData rsd = rs.getMetaData();
			int columCount = rsd.getColumnCount();
			while(rs.next()) {
				Map<String, String> row = new HashMap<String, String>();
				for(int i=1; i<=columCount; i++) {
					String columName = rsd.getColumnLabel(i);
					String columValue = rs.getString(i);
					row.put(columName, columValue);
				}
				list.add(row);				
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcUserDao.employeeList() 에러 발생", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return list;
	}
	
	@Override
	public User isMember(String id, String passwd) {
	User user = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT id, " + 
				     "       name, " + 
				     "       passwd, " + 
				     "       email, " + 
				     "       TO_CHAR(regdate, 'YYYY/MM/DD') regdate " + 
				     "FROM   users " + 
				     "WHERE  id = ? AND passwd = ?";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user = createUser(rs);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcUserDao.read(id) 실행 에러", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close(); // 커넥션 종료가 아니라 반납하는것
			} catch (Exception e) {}
		}
		return user;
	}
	
	@Override
	public String checkId(String userId) {
		String id;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT id " + 
				     "FROM   users " + 
					 "WHERE  id = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				id = "0";
			} else {
				id = "a" + userId;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcUserDao.read(id) 실행 에러", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close(); // 커넥션 종료가 아니라 반납하는것
			} catch (Exception e) {}
		}
		return id;
	}
	
	public boolean checkEmail(String userEmail) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT email " + 
				     "FROM   users " + 
					 "WHERE  email = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcUserDao.checkEmail(String userEmail) 실행 에러", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close(); // 커넥션 종료가 아니라 반납하는것
			} catch (Exception e) {}
		}
	}
	
//	회원 삭제
	public void delete(int article_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "UPDATE article " + 
					 "SET    subject = '삭제된 글입니다.' " + 
					 "WHERE  article_id = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, article_id);
			rs = pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.delete(String article_id) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}
}
