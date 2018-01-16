package kr.or.kosta.blog.freeboard.dao;

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
import kr.or.kosta.blog.freeboard.domain.Article;
import kr.or.kosta.blog.guestbook.domain.GuestBook;
import kr.or.kosta.blog.user.dao.UserDao;
import kr.or.kosta.blog.user.domain.User;

public class JdbcBoardDao implements BoardDao {
	
	private DataSource setDataSource;
	
	public JdbcBoardDao() {}
	
	public JdbcBoardDao(DataSource setDataSource) {
		this.setDataSource = setDataSource;
	}
	
	
	public DataSource getDataSource() {
		return setDataSource;
	}

	public void setDataSource(DataSource setDataSource) {
		this.setDataSource = setDataSource;
	}

	
//	글쓰기
	@Override
	public void write(Article article) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO article " + 
					 "            (article_id, " + 
					 "             board_id, " + 
					 "             writer, " + 
					 "             subject, " + 
					 "             content, " + 
					 "             ip, " + 
					 "             passwd, " + 
					 "             group_no, " + 
					 "             level_no, " + 
					 "             order_no) " + 
					 "VALUES      (article_id_seq.nextval, " + 
					 "             1, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             ?, " + 
					 "             article_id_seq.currval, " + 
					 "             0, " + 
					 "             0) "; 
		try {
			con = setDataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getWriter());
			pstmt.setString(2, article.getSubject());
			pstmt.setString(3, article.getContent());
			pstmt.setString(4, article.getIp());
			pstmt.setString(5, article.getPasswd());
			pstmt.executeUpdate();
			con.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {}
			throw new BlogException("JdbcBoardDao.write(Article article) 실패", e);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}

	
//	order_no �ø���
	@Override
	public void increaseOrder(Article parent_article) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "UPDATE article " + 
					 "SET    order_no = order_no + 1 " + 
					 "WHERE  board_id = 1 " + 
					 "       AND group_no = ? " + 
					 "       AND order_no > (SELECT order_no " + 
					 "                       FROM   article " + 
					 "                       WHERE  article_id = ?)";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, parent_article.getGroup_no());
			pstmt.setInt(2, parent_article.getArticle_id());
			pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.increaseOrder(Article parent_article) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}
	

//	답글 작성
	@Override
	public void reply(Article article, Article parent_article) {
		
		if (parent_article.getLevel_no() == 0) {
			newReply(article, parent_article);	
		} else {
			replyToReply(article, parent_article);
		}
	}	
	
	
//	�ű� ��� ���
	public void newReply(Article article, Article parent_article) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO article " + 
		   		 "            (article_id, " + 
		   		 "             board_id, " + 
		   		 "             writer, " + 
		   		 "             subject, " + 
		   		 "             content, " + 
		   		 "             ip, " + 
		   		 "             passwd, " + 
		   		 "             group_no, " + 
		   		 "             level_no, " + 
		   		 "             order_no) " + 
		   		 "VALUES      (article_id_seq.nextval, " + 
		   		 "             1, " + 
		   		 "             ?, " + 
		   		 "             ?, " + 
		   		 "             ?, " + 
		   		 "             ?, " + 
		   		 "             ?, " + 
		   		 "             ?, " + 
		   		 "             1, " + 
		   		 "             (SELECT MAX(order_no) + 1 " + 
		   		 "              FROM   article " + 
		   		 "              WHERE  board_id = 1 " + 
		   		 "                     AND group_no = ?)) ";
		   
			try {
				con = setDataSource.getConnection();
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, article.getWriter());
				pstmt.setString(2, article.getSubject());
				pstmt.setString(3, article.getContent());
				pstmt.setString(4, article.getIp());
				pstmt.setString(5, article.getPasswd());
				pstmt.setInt(6, parent_article.getGroup_no());
				pstmt.setInt(7, parent_article.getGroup_no());
				pstmt.executeUpdate();
				con.commit();
			}catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {}
				throw new BlogException("JdbcBoardDao.newReply(Article article, Article parent_article) 실패", e);
			}finally {
				try {
					if(pstmt != null) pstmt.close();
					if(con != null)   con.close();
				} catch (Exception e) {}
			}
	}
	
	
//	답글의 답글 등록
	public void replyToReply(Article article, Article parent_article) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO article " + 
				 "            (article_id, " + 
				 "             board_id, " + 
				 "             writer, " + 
				 "             subject, " + 
				 "             content, " + 
				 "             ip, " + 
				 "             passwd, " + 
				 "             group_no, " + 
				 "             level_no, " + 
				 "             order_no) " + 
				 "VALUES      (article_id_seq.nextval, " + 
				 "             1, " + 
				 "             ?, " + 
				 "             ?, " + 
				 "             ?, " + 
				 "             ?, " + 
				 "             ?, " + 
				 "             ?, " + 
				 "             ?, " + 
				 "             (SELECT order_no + 1 " + 
				 "              FROM   article " + 
				 "              WHERE  article_id = ?)) ";
		   
		   try {
				con = setDataSource.getConnection();
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, article.getWriter());
				pstmt.setString(2, article.getSubject());
				pstmt.setString(3, article.getContent());
				pstmt.setString(4, article.getIp());
				pstmt.setString(5, article.getPasswd());
				pstmt.setInt(6, parent_article.getGroup_no());
				pstmt.setInt(7, parent_article.getLevel_no() + 1);
				pstmt.setInt(8, parent_article.getArticle_id());
				increaseOrder(parent_article);
				pstmt.executeUpdate();
				con.commit();
			}catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {}
				throw new BlogException("JdbcBoardDao.replyToReply(Article article, Article parent_article) 실패", e);
			}finally {
				try {
					if(pstmt != null) pstmt.close();
					if(con != null)   con.close();
				} catch (Exception e) {}
			}
	}
	
	
//	�� ����
	@Override
	public void rewrite(Article article) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "UPDATE article " + 
					 "SET    subject = ?, " + 
					 "       content = ?, " + 
					 "       regdate = sysdate, " + 
					 "       ip = ? " + 
					 "WHERE  board_id = 1 " + 
					 "       AND article_id = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getSubject());
			pstmt.setString(2, article.getContent());
			pstmt.setString(3, article.getIp());
			pstmt.setInt(4, article.getArticle_id());
			rs = pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.rewrite(Article article) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}

	
//	글 삭제 처리
	@Override
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
	
	
//	��ü �� ��� ��ȸ
	@Override
	public List<Article> listAll(){
		List<Article> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT board_id, " + 
					 "       article_id, " + 
					 "       subject, " + 
					 "       writer, " + 
					 "       regdate, " + 
					 "       ip, " + 
					 "       hitcount, " + 
					 "       content, " + 
					 "       group_no, " + 
					 "       level_no, " + 
					 "       order_no " + 
					 "FROM   article " + 
					 "WHERE  board_id = 1 " + 
					 "ORDER  BY group_no DESC, " + 
					 "          order_no ASC ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<Article>();
			while(rs.next()) {
				Article article = createArticle(rs);
				list.add(article);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.listAll() 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return list;
	}
	
	
//	Article ��ü ����
	private Article createArticle(ResultSet rs) throws SQLException {
		int board_id = rs.getInt("board_id");
		int article_id = rs.getInt("article_id");
		String subject = rs.getString("subject");
		String writer = rs.getString("writer");		
		String regdate = rs.getString("regdate");		
		String ip = rs.getString("ip");
		String passwd = rs.getString("passwd");
		int hitcount = rs.getInt("hitcount");		
		String content = rs.getString("content");		
		int group_no = rs.getInt("group_no");		
		int level_no = rs.getInt("level_no");		
		int order_no = rs.getInt("order_no");		
		
		Article article = new Article();
		article.setBoard_id(board_id);
		article.setArticle_id(article_id);
		article.setSubject(subject);
		article.setWriter(writer);
		article.setRegdate(regdate);
		article.setIp(ip);
		article.setPasswd(passwd);
		article.setHitcount(hitcount);
		article.setContent(content);
		article.setGroup_no(group_no);
		article.setLevel_no(level_no);
		article.setOrder_no(order_no);
		return article;
	}
	
	
//	페이지 별 글 리스트 조회
	@Override
	public List<Article> pageList(String request_page) {
		
		List<Article> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT board_id, " + 
					 "       article_id, " + 
					 "       subject, " + 
					 "       writer, " + 
					 "       regdate, " + 
					 "       ip, " + 
					 "       passwd, " + 
					 "       hitcount," + 
					 "       content, " + 
					 "       group_no, " + 
					 "       level_no, " + 
					 "       order_no " + 
					 "FROM   (SELECT CEIL(rownum / 10) request_page, " + 
					 "               board_id, " + 
					 "               article_id, " + 
					 "               subject, " + 
					 "               writer, " + 
					 "               TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI') regdate, " + 
					 "               ip, " + 
					 "               passwd, " + 
					 "               hitcount, " + 
					 "      		 content, " + 
					 "               group_no, " + 
					 "               level_no, " + 
					 "               order_no " + 
					 "        FROM   (SELECT board_id, " + 
					 "                       article_id, " + 
					 "                       subject, " + 
					 "                       writer, " + 
					 "                       regdate, " + 
					 "                       ip, " + 
					 "                       passwd, " + 
					 "                       hitcount," + 
					 "      				 content, " + 
					 "                       group_no, " + 
					 "                       level_no, " + 
					 "                       order_no " + 
					 "                FROM   article " + 
					 "                WHERE  board_id = 1 " + 
					 "                ORDER  BY group_no DESC, " + 
					 "                          order_no ASC)) " + 
					 "WHERE  request_page = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request_page);
			rs = pstmt.executeQuery();
			list = new ArrayList<Article>();
			while(rs.next()) {
				Article article = createArticle(rs);
				list.add(article);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.pageList(String request_page) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return list;
	}
	
	
//	��ȸ �� �ø���
	@Override
	public void increaseHits(int article_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "UPDATE article " + 
					 "SET    hitcount = hitcount + 1 " + 
					 "WHERE  board_id = 1 " + 
					 "       AND article_id = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(article_id));
			pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.increaseHits(int article_id) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
	}
	
	
//	�� �� ��ȸ
	@Override
	public Article showDetail(int article_id) {
		
		Article article = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT board_id, " + 
					 "       article_id, " + 
					 "       subject, " + 
					 "       writer, " + 
					 "       To_char(regdate, 'YYYY-MM-DD HH24:MI:SS') regdate, " + 
					 "       ip, " + 
					 "       passwd, " + 
					 "       hitcount, " + 
					 "       content, " + 
					 "       group_no, " + 
					 "       level_no, " + 
					 "       order_no " + 
					 "FROM   article " + 
					 "WHERE  board_id = 1 " + 
					 "       AND article_id = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(article_id));
			increaseHits(article_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				article = createArticle(rs);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.showDetail(int article_id) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return article;
	}
	
	
//	검색 조건에 따른 글 조회
	@Override
	public List<Article> search(String request_page, String condition, String keyword) {
		
		List<Article> list = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT board_id, " + 
					 "       article_id, " + 
					 "       subject, " + 
					 "       writer, " + 
					 "       regdate, " + 
					 "       ip, " + 
					 "       passwd, " + 
					 "       hitcount, " + 
					 "       content, " + 
					 "       group_no, " + 
					 "       level_no, " + 
					 "       order_no " + 
					 "FROM   (SELECT Ceil(rownum / 10) request_page, " + 
					 "               board_id, " + 
					 "               article_id, " + 
					 "               subject, " + 
					 "               writer, " + 
					 "               To_char(regdate, 'YYYY-MM-DD HH24:MI') regdate, " + 
					 "               ip, " + 
					 "               passwd, " + 
					 "               hitcount, " + 
					 "               content, " + 
					 "               group_no, " + 
					 "               level_no, " + 
					 "               order_no " + 
					 "        FROM   (SELECT board_id, " + 
					 "                       article_id, " + 
					 "                       subject, " + 
					 "                       writer, " + 
					 "                       regdate, " + 
					 "                       ip, " + 
					 "                       passwd, " + 
					 "                       hitcount, " + 
					 "                       content, " + 
					 "                       group_no, " + 
					 "                       level_no, " + 
					 "                       order_no " + 
					 "                FROM   article " + 
					 "                WHERE  board_id = 1 " + 
					 "                       AND group_no IN (SELECT group_no " + 
					 "                                        FROM   article " + 
					 "                                        WHERE " + condition +  " LIKE '%' || ? || '%') " + 
					 "                ORDER  BY group_no DESC, " + 
					 "                          order_no ASC)) " + 
					 "WHERE  request_page = ? ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, request_page);
			rs = pstmt.executeQuery();
			list = new ArrayList<Article>();
			while(rs.next()) {
				Article article = createArticle(rs);
				list.add(article);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.search(String request_page, String condition, String keyword) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return list;
	}
	
	
//	�˻��� �� �� üũ
	public int searchedArticles(String condition, String keyword) {

		int count = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT Count(article_id) count " + 
					 "FROM   (SELECT article_id " + 
					 "        FROM   (SELECT article_id " + 
					 "                FROM   article " + 
					 "                WHERE  board_id = 1 " + 
					 "                       AND group_no IN (SELECT group_no " + 
					 "                                        FROM   article " + 
					 "                                        WHERE " + condition +  " LIKE '%' || ? || '%') " + 
					 "                ORDER  BY group_no DESC, " + 
					 "                          order_no ASC)) "; 
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.search(String condition, String keyword) 실패", e);
		}finally {
			try {
				if(rs != null)    rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null)   con.close();
			} catch (Exception e) {}
		}
		return count;
	}
	
	
//	������ ��ȣ ���
	@Override
	public String totalArticles() {
		
		String total = "0";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT Count(article_id) total " + 
					 "FROM   article " + 
					 "WHERE  board_id = 1 ";
		try {
			con = setDataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			total = rs.getString("total");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BlogException("JdbcBoardDao.totalArticles() 실패", e);
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
