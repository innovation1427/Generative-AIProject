package com.servlet.mvc1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import common.JDBCUtil;

public class DeveloperDAO {

    Connection conn=null;
    PreparedStatement pstmt=null;
    ResultSet rs=null;

    final String DEVEL_LIST = "select * from developer;";
    final String TOTAL_PAGES_QUERY = "SELECT CEIL(COUNT(*) / ?) FROM developer ORDER BY NUM DESC";
    final String DEVEL_LIST_PAGING = "SELECT * FROM developer AS n ORDER BY n.NUM DESC LIMIT ?, ?";

    public DeveloperDTO selectDeveloperById(int developerId) throws SQLException {
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM developer WHERE NUM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, developerId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	DeveloperDTO developer = new DeveloperDTO();
            	developer.setNum(rs.getString("NUM"));
            	developer.setTitle(rs.getString("TITLE"));
            	developer.setContent(rs.getString("CONTENT"));
            	developer.setNickname(rs.getString("NICKNAME"));
            	developer.setPostdate(rs.getTimestamp("POSTDATE"));
            	developer.setVisitcount(rs.getString("VISITCOUNT"));
                return developer;
            }
        } finally {
            JDBCUtil.close(pstmt, conn);
        }

        return null; // 해당 ID에 해당하는 공지사항이 없을 경우 null 반환
    }

    public int getTotalPages(int pageSize) throws SQLException {
        conn = JDBCUtil.getConnection();

        pstmt = conn.prepareStatement(TOTAL_PAGES_QUERY);
        pstmt.setInt(1, pageSize);

        rs = pstmt.executeQuery();

        int totalPages = 0;

        if (rs.next()) {
            totalPages = rs.getInt(1);
        }

        JDBCUtil.close(pstmt, conn);
        return totalPages;
    }

    public void increaseVisitCount(HttpServletRequest request) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String developerId = request.getParameter("developerId");
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE developer SET VISITCOUNT = VISITCOUNT + 1 WHERE NUM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, developerId);
            pstmt.executeUpdate();
        } finally {
            JDBCUtil.close(pstmt, conn);
        }
    }

    public ArrayList<DeveloperDTO> selectDeveloperList(int currentPage, int pageSize) throws SQLException {
        conn = JDBCUtil.getConnection();

        int startRow = (currentPage - 1) * pageSize;
        
        pstmt = conn.prepareStatement(DEVEL_LIST_PAGING);
        pstmt.setInt(1, startRow);
        pstmt.setInt(2, pageSize);

        rs = pstmt.executeQuery();
        ArrayList<DeveloperDTO> developerList = new ArrayList<DeveloperDTO>();
        while (rs.next()) {
        	DeveloperDTO developer = new DeveloperDTO();
        	developer.setNum(rs.getString("NUM"));
        	developer.setTitle(rs.getString("TITLE"));
        	developer.setContent(rs.getString("CONTENT"));
        	developer.setNickname(rs.getString("NICKNAME"));
        	developer.setPostdate(rs.getTimestamp("POSTDATE"));
        	developer.setVisitcount(rs.getString("VISITCOUNT"));
        	developerList.add(developer);
            
            // 로그 추가
        }
        
        JDBCUtil.close(pstmt, conn);
        return developerList;
    }
    public String getUserNickname(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String nickname = null;

        try {
            conn = JDBCUtil.getConnection();
            String strQuery = "SELECT nickname FROM users WHERE id = ?";
            pstmt = conn.prepareStatement(strQuery);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                nickname = rs.getString("nickname");
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }

        return nickname;
    }

    public boolean developerInsert(DeveloperDTO dDTO) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
        	conn = JDBCUtil.getConnection();
            String strQuery = "insert into developer values(?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(strQuery);
            pstmt.setString(1, null);
            pstmt.setString(2, dDTO.getTitle());
            pstmt.setString(3, dDTO.getContent());
            pstmt.setString(4, dDTO.getNickname());
            Timestamp timestamp = new Timestamp(new Date().getTime());
            pstmt.setTimestamp(5, timestamp);
            pstmt.setString(6, "0");
         
            int count = pstmt.executeUpdate();

            if (count == 1) {
                flag = true;
            }

        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
        	JDBCUtil.close(rs, pstmt, conn);
        }
        return flag;
    }
    
}
