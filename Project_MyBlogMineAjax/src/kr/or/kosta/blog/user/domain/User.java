package kr.or.kosta.blog.user.domain;

import java.io.Serializable;

public class User implements Serializable{
	private String id;
	private String name;
	private String passwd;
	private String email;
	private String telephone;
	private String job;
	private String message;
	private String regdate;
	
	public User() {}
	
	public User(String id, String name, String passwd, String email, String telephone, String job, String message) {
		this(id, name, passwd, email, telephone, job, message, null);
	}
	
	public User(String id, String name, String passwd, String email, String job, String message) {
		this(id, name, passwd, email, null, job, message, null);
	}
	
	public User(String id, String name, String passwd, String email, String job) {
		this(id, name, passwd, email, null, job, null, null);
	}
	
	public User(String id, String name, String passwd, String email, String telephone, String job, String message, String regdate) {
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.email = email;
		this.telephone = telephone;
		this.job = job;
		this.message = message;
		this.regdate = regdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", passwd=" + passwd + ", email=" + email + ", regdate=" + regdate
				+ "]";
	}
}
