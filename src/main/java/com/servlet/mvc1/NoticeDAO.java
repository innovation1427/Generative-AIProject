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

public class NoticeDAO {

    Connection conn=null;
    PreparedStatement pstmt=null;
    ResultSet rs=null;

    final String NOTICE_LIST = "select * from notice;";
    final String TOTAL_PAGES_QUERY = "SELECT CEIL(COUNT(*) / ?) FROM NOTICE ORDER BY NUM DESC";
    final String NOTICE_LIST_PAGING = "SELECT * FROM notice AS n ORDER BY n.NUM DESC LIMIT ?, ?";

    public NoticeDTO selectNoticeById(int noticeId) throws SQLException {
        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM notice WHERE NUM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, noticeId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                NoticeDTO notice = new NoticeDTO();
                notice.setNum(rs.getString("NUM"));
                notice.setTitle(rs.getString("TITLE"));
                notice.setContent(rs.getString("CONTENT"));
                notice.setNickname(rs.getString("NICKNAME"));
                notice.setPostdate(rs.getTimestamp("POSTDATE"));
                notice.setVisitcount(rs.getString("VISITCOUNT"));
                return notice;
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
        String noticeId = request.getParameter("noticeId");
        try {
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE notice SET VISITCOUNT = VISITCOUNT + 1 WHERE NUM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, noticeId);
            pstmt.executeUpdate();
        } finally {
            JDBCUtil.close(pstmt, conn);
        }
    }

    public ArrayList<NoticeDTO> selectNoticeList(int currentPage, int pageSize) throws SQLException {
        conn = JDBCUtil.getConnection();

        int startRow = (currentPage - 1) * pageSize;
        
        pstmt = conn.prepareStatement(NOTICE_LIST_PAGING);
        pstmt.setInt(1, startRow);
        pstmt.setInt(2, pageSize);

        rs = pstmt.executeQuery();
        ArrayList<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();
        while (rs.next()) {
            NoticeDTO notice = new NoticeDTO();
            notice.setNum(rs.getString("NUM"));
            notice.setTitle(rs.getString("TITLE"));
            notice.setContent(rs.getString("CONTENT"));
            notice.setNickname(rs.getString("NICKNAME"));
            notice.setPostdate(rs.getTimestamp("POSTDATE"));
            notice.setVisitcount(rs.getString("VISITCOUNT"));
            noticeList.add(notice);
            
            // 로그 추가
        }
        
        JDBCUtil.close(pstmt, conn);
        return noticeList;
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

    public boolean noticeInsert(NoticeDTO nDTO) {
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
        	conn = JDBCUtil.getConnection();
            String strQuery = "insert into notice values(?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(strQuery);
            pstmt.setString(1, null);
            pstmt.setString(2, nDTO.getTitle());
            pstmt.setString(3, nDTO.getContent());
            pstmt.setString(4, nDTO.getNickname());
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
