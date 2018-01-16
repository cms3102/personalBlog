package kr.or.kosta.blog.freeboard.domain;

public class Board{
	private int article_id;
	private int board_id;
	private String writer;
	private String subject;
	private String content;
	private String regdate;
	private int hitcount;
	private String passwd;
	private String attach_file;
	private int group_no;
	private int level_no;
	private int order_no;
	
	public Board() {}
	
	public Board(int article_id, int board_id, String writer, String subject, String content, String passwd, int group_no, int level_no, int order_no) {
		this(article_id, board_id, writer, subject, content, null, 0, passwd, null, group_no, level_no, order_no);
	}
	
	public Board(int article_id, int board_id, String writer, String subject, String content, String passwd, String attach_file, int group_no, int level_no, int order_no) {
		this(article_id, board_id, writer, subject, content, null, 0, passwd, attach_file, group_no, level_no, order_no);
	}
	
	public Board(int article_id, int board_id, String writer, String subject, String content, int hitcount, String passwd, int group_no, int level_no, int order_no) {
		this(article_id, board_id, writer, subject, content, null, hitcount, passwd, null, group_no, level_no, order_no);
	}
	
	public Board(int article_id, int board_id, String writer, String subject, String content, int hitcount, String passwd, String attach_file, int group_no, int level_no, int order_no) {
		this(article_id, board_id, writer, subject, content, null, hitcount, passwd, attach_file, group_no, level_no, order_no);
	}
	
	public Board(int article_id, int board_id, String writer, String subject, String content, String regdate, int hitcount, String passwd, String attach_file, int group_no, int level_no, int order_no) {
		this.article_id = article_id;
		this.board_id = board_id;
		this.writer = writer;
		this.subject = subject;
		this.content = content;
		this.regdate = regdate;
		this.hitcount = hitcount;
		this.passwd = passwd;
		this.attach_file = attach_file;
		this.group_no = group_no;
		this.level_no = level_no;
		this.order_no = order_no;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getHitcount() {
		return hitcount;
	}

	public void setHitcount(int hitcount) {
		this.hitcount = hitcount;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAttach_file() {
		return attach_file;
	}

	public void setAttach_file(String attach_file) {
		this.attach_file = attach_file;
	}

	public int getGroup_no() {
		return group_no;
	}

	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}

	public int getLevel_no() {
		return level_no;
	}

	public void setLevel_no(int level_no) {
		this.level_no = level_no;
	}

	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}

	@Override
	public String toString() {
		return "FreeBoard [article_id=" + article_id + ", board_id=" + board_id + ", writer=" + writer + ", subject="
				+ subject + ", content=" + content + ", regdate=" + regdate + ", hitcount=" + hitcount + ", passwd="
				+ passwd + ", attach_file=" + attach_file + ", group_no=" + group_no + ", level_no=" + level_no
				+ ", order_no=" + order_no + "]";
	}
	
	

}
