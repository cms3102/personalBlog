package kr.or.kosta.blog.guestbook.dao;

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
import kr.or.kosta.blog.guestbook.domain.GuestBook;
import kr.or.kosta.blog.user.dao.UserDao;
import kr.or.kosta.blog.user.domain.User;

public class JdbcGuestBookDao implements GuestBookDao {
	
	private DataSource setDataSource;
	
	public JdbcGuestBookDao() {}
	
	public JdbcGuestBookDao(DataSource setDataSource) {
		this.setDataSource = setDataSource;
	}
	
	
	public DataSource getDataSource() {
		return setDataSource;
	}

	public void setDataSource(DataSource setDataSource) {
		this.setDataSource = setDataSource;
	}

	@Override
	public void write(GuestBook guestBook) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO guestbook " + 
					 "            (guestbook_id, " + 
					 "             user_id, " + 
					 "             contents) " + 
					 "VALUES      (guestbook_seq.NEXTVAL, " + 
					 "             ?, " + 
					 "             ?) "; 
		try {
			con = setDataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, guestBook.getUser_id());
			pstmt.setString(2, guestBook.getContents());
			pstmt.executeUpdate();
			con.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {}
			throw new BlogException("JdbcGuestBookDao.write(GuestBook guestBook) ����", e);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}

	@Override
	public List<GuestBook> listAll(){
		List<GuestBook> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT guestbook_id, " + 
				 	 "       user_id, " + 
					 "       contents, " + 
					 "       To_char(regdate, 'YYYY/MM/DD') regdate " + 
					 "FROM   guestbook ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<GuestBook>();
			while(rs.next()) {
				GuestBook guestBook = createGuestBook(rs);
				list.add(guestBook);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcGuestBookDao.listAll() ������!!", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return list;
	}
	
	private GuestBook createGuestBook(ResultSet rs) throws SQLException {
		String guestbook_id = rs.getString("guestbook_id");
		String user_id = rs.getString("user_id");		
		String contents = rs.getString("contents");		
		String regdate = rs.getString("regdate");		
		
		GuestBook guestBook = new GuestBook();
		guestBook.setGuestbook_id(guestbook_id);
		guestBook.setUser_id(user_id);
		guestBook.setContents(contents);
		guestBook.setRegdate(regdate);
		return guestBook;
	}
	
	@Override
	public List<GuestBook> pageList(String request_page) {
		
		List<GuestBook> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT guestbook_id, " + 
					 "       user_id, " + 
					 "       contents, " + 
					 "       regdate " + 
					 "FROM   (SELECT Ceil(rownum / 10) request_page, " + 
					 "               guestbook_id, " + 
					 "               user_id, " + 
					 "               contents, " + 
					 "               regdate " + 
					 "        FROM   (SELECT guestbook_id, " + 
					 "                       user_id, " + 
					 "                       contents, " + 
					 "                       To_char(regdate, 'YYYY-MM-DD HH24:MI:SS DAY') regdate " + 
					 "                FROM   guestbook " + 
					 "                ORDER  BY regdate DESC)) " + 
					 "WHERE  request_page = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request_page);
			rs = pstmt.executeQuery();
			list = new ArrayList<GuestBook>();
			while(rs.next()) {
				
				GuestBook guestBook = createGuestBook(rs);
				list.add(guestBook);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcGuestBookDao.pageList(String request_page) 오류", e);
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
	public String pageNum() {
		
		String total = "1";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT Count(guestbook_id) total " + 
					 "FROM   guestbook ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			total = rs.getString("total");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcGuestBookDao.pageNum() 오류", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		
		return total;
	}
	
}
