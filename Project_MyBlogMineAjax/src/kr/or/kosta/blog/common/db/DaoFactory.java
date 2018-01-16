package kr.or.kosta.blog.common.db;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import kr.or.kosta.blog.common.exception.BlogException;

/**
 * #1. Dao 인스턴스 생성
 * #2. DataSource 인스턴스 생성
 * #3. Dao에 DataSource 설정
 * #4. DataSource가 설정된 Dao 반환
 * DaoFactory에 싱글톤 패턴 적용
 * 
 * @author 김기정
 */
public class DaoFactory {
	
	private static DaoFactory instance = new DaoFactory();
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521";
	private static final String USER = "hr";
	private static final String PASSWORD = "hr";
	private static final int INIT_SIZE = 5;
	private static final int MAX_SIZE = 10;
	
	private BasicDataSource dataSource;
	
	private DaoFactory() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DRIVER);
		dataSource.setUrl(URL);
		dataSource.setUsername(USER);
		dataSource.setPassword(PASSWORD);
		dataSource.setInitialSize(INIT_SIZE);
		dataSource.setMaxTotal(MAX_SIZE);
	}
	
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static DaoFactory getInstance() {
		return instance;
	}
	
	/*
	public UserDao getUserDao() {
		UserDao dao = new JdbcUserDao(dataSource);
		return dao;
	}
	
	public ArticleDao getArticleDao() {
		ArticleDao dao = new JdbcArticleDao(createDataSource());
		return dao;
	}
	.
	.
	.
	.
	*/
	
	/**
	 * 단일화된 Dao 요청 창구
	 */
	public Object getDao(String className) { // DAO 이름 전달 받기
		Object dao = null;
		try {
			Class cls = Class.forName(className);
			dao = cls.newInstance(); // DAO 객체 생성
			
			//JdbcXXXDao dao = (JdbcXXXDao)dao;
			//dao.setDataSource(dataSource); // 이게 안되기 때문에 아래 동적 메소드 호출을 함.
			
			// 동적 메소드호출
			Method method =  cls.getMethod("setDataSource", DataSource.class); // 
			method.invoke(dao, dataSource);
			
		} catch (Exception e) {
			throw new BlogException("DaoFactory.getDao(String className) 실행 중 예외발생", e); // 서버에서 컴포넌트 관련하여 발생하는 예외는 런타임으로 처리해 주는 게 좋다.
		}
		return dao;
	}
	
	public Object getDao(Class cls) {
		return getDao(cls.getName());
	}
}
