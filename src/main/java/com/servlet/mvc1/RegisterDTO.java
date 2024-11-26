package com.servlet.mvc1;

public class RegisterDTO {
	private String ID;
	private String PASSWORD;
	private String EMAIL;
	private String NAME;
	private String BIRTHDAY;
	private String GENDER;
	private String NICKNAME;
	private String ROLE;
	private int SCORE; // SCORE 필드 추가

	public String getId() {
		return ID;
	}

	public void setId(String ID) {
		this.ID = ID;
	}

	public String getPassword() {
		return PASSWORD;
	}

	public void setPassword(String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}

	public String getEmail() {
		return EMAIL;
	}

	public void setEmail(String EMAIL) {
		this.EMAIL = EMAIL;
	}

	public String getName() {
		return NAME;
	}

	public void setName(String NAME) {
		this.NAME = NAME;
	}

	public String getBirthday() {
		return BIRTHDAY;
	}

	public void setBirthday(String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}

	public String getGender() {
		return GENDER;
	}

	public void setGender(String GENDER) {
		this.GENDER = GENDER;
	}

	public String getNickname() {
		return NICKNAME;
	}

	public void setNickname(String NICKNAME) {
		this.NICKNAME = NICKNAME;
	}

	public String getRole() {
		return ROLE;
	}

	public void setRole(String ROLE) {
		this.ROLE = ROLE;
	}

	public int getScore() {
		return SCORE;
	}

	public void setScore(int SCORE) {
		this.SCORE = SCORE;
	}
}
