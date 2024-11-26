package com.servlet.mvc1;
public class NoticeDTO {
	private String NUM;
	private String TITLE;
	private String CONTENT;
	private String NICKNAME;
	private java.sql.Timestamp POSTDATE;
	private String VISITCOUNT;
	

	public String getNum() {
		return NUM;
	}
	public void setNum(String NUM) {
	    this.NUM = NUM;
	}
	public String getTitle() {
		return TITLE;
	}
	public void setTitle(String TITLE) {
		this.TITLE = TITLE;
	}
	public String getContent() {
		return CONTENT;
	}
	public void setContent(String CONTENT) {
		this.CONTENT = CONTENT;
	}
	public String getNickname() {
		return NICKNAME;
	}
	public void setNickname(String NICKNAME) {
		this.NICKNAME = NICKNAME;
	}
	public java.sql.Timestamp getPostdate() {
		return POSTDATE;
	}
	public void setPostdate(java.sql.Timestamp POSTDATE) {
		this.POSTDATE = POSTDATE;
	}
	public String getVisitcount() {
		return VISITCOUNT;
	}
	public void setVisitcount(String VISITCOUNT) {
		this.VISITCOUNT = VISITCOUNT;
	}
	
}
