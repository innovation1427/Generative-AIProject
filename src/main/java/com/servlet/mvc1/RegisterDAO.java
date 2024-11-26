package com.servlet.mvc1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCUtil;

public class RegisterDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 사용자 삽입 SQL
	final String USER_INSERT = "INSERT INTO users (ID, PASSWORD, EMAIL, NAME, BIRTHDAY, GENDER, NICKNAME, ROLE, SCORE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0);";

	// 사용자 목록 조회 SQL
	final String USER_LIST = "SELECT * FROM users;";

	// 사용자 삭제 SQL
	private static final String DELETE_USER = "DELETE FROM users WHERE ID = ?";

	// 사용자 정보 수정 SQL
	private static final String UPDATE_USER = "UPDATE users SET PASSWORD = ?, EMAIL = ?, NAME = ?, BIRTHDAY = ?, GENDER = ?, NICKNAME = ?, ROLE = ? WHERE ID = ?";

	// 사용자 삽입 메소드
	public boolean memberInsert(RegisterDTO dto) {
		conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(USER_INSERT);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getName());
			pstmt.setString(5, dto.getBirthday());
			pstmt.setString(6, dto.getGender());
			pstmt.setString(7, dto.getNickname());
			pstmt.setString(8, dto.getRole());

			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
		return false;
	}

	// 사용자 목록 조회 메소드
	public ArrayList<RegisterDTO> selectMemberList() throws SQLException {
		conn = JDBCUtil.getConnection();
		pstmt = conn.prepareStatement(USER_LIST);
		rs = pstmt.executeQuery();
		ArrayList<RegisterDTO> aList = new ArrayList<>();
		while (rs.next()) {
			RegisterDTO rd = new RegisterDTO();
			rd.setId(rs.getString("ID"));
			rd.setPassword(rs.getString("PASSWORD"));
			rd.setEmail(rs.getString("EMAIL"));
			rd.setName(rs.getString("NAME"));
			rd.setBirthday(rs.getString("BIRTHDAY"));
			rd.setGender(rs.getString("GENDER"));
			rd.setNickname(rs.getString("NICKNAME"));
			rd.setRole(rs.getString("ROLE"));
			rd.setScore(rs.getInt("SCORE")); // SCORE 필드 추가
			aList.add(rd);
		}
		JDBCUtil.close(pstmt, conn);
		return aList;
	}

	// 사용자 삭제 메소드
	public boolean deleteUser(String ID) {
		conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(DELETE_USER);
			pstmt.setString(1, ID);
			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
		return false;
	}

	// 사용자 정보 수정 메소드
	public boolean updateUser(String Id, String newPassword, String newEmail, String newName, String newBirthday,
			String newGender, String newNickname, String newRole) {
		conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(UPDATE_USER);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, newEmail);
			pstmt.setString(3, newName);
			pstmt.setString(4, newBirthday);
			pstmt.setString(5, newGender);
			pstmt.setString(6, newNickname);
			pstmt.setString(7, newRole);
			pstmt.setString(8, Id);

			int rowsUpdated = pstmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
		return false;
	}
}
