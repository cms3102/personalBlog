package kr.or.kosta.blog.common.exception;


public class BlogException extends RuntimeException {
	
	private static final String DEFAULT_CODE = "EMS-0001";
	private String code;

	public BlogException() {
		this(DEFAULT_CODE, "Database Server Error!");				
	}
		
	public BlogException(String message) {
		super(message);
	}

	public BlogException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BlogException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public BlogException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
