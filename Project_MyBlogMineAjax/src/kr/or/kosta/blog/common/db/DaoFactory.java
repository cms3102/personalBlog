package kr.or.kosta.blog.common.db;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import kr.or.kosta.blog.common.exception.BlogException;

/**
 * #1. Dao �ν��Ͻ� ����
 * #2. DataSource �ν��Ͻ� ����
 * #3. Dao�� DataSource ����
 * #4. DataSource�� ������ Dao ��ȯ
 * DaoFactory�� �̱��� ���� ����
 * 
 * @author �����
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
	 * ����ȭ�� Dao ��û â��
	 */
	public Object getDao(String className) { // DAO �̸� ���� �ޱ�
		Object dao = null;
		try {
			Class cls = Class.forName(className);
			dao = cls.newInstance(); // DAO ��ü ����
			
			//JdbcXXXDao dao = (JdbcXXXDao)dao;
			//dao.setDataSource(dataSource); // �̰� �ȵǱ� ������ �Ʒ� ���� �޼ҵ� ȣ���� ��.
			
			// ���� �޼ҵ�ȣ��
			Method method =  cls.getMethod("setDataSource", DataSource.class); // 
			method.invoke(dao, dataSource);
			
		} catch (Exception e) {
			throw new BlogException("DaoFactory.getDao(String className) ���� �� ���ܹ߻�", e); // �������� ������Ʈ �����Ͽ� �߻��ϴ� ���ܴ� ��Ÿ������ ó���� �ִ� �� ����.
		}
		return dao;
	}
	
	public Object getDao(Class cls) {
		return getDao(cls.getName());
	}
}
