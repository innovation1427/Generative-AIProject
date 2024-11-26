package com.servlet.mvc1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import common.JDBCUtil;

public class ScoreDAO {
    public void saveScore(String userId, int score) {
        String checkScoreSql = "SELECT score FROM game_score WHERE user_id = ?";
        String updateScoreSql = "UPDATE game_score SET score = ? WHERE user_id = ?";
        String insertScoreSql = "INSERT INTO game_score (user_id, score) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            System.out.println("DB 연결 성공");

            // Step 1: Check existing score
            pstmt = conn.prepareStatement(checkScoreSql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 기존 점수가 있는 경우
                int existingScore = rs.getInt("score");
                System.out.println("기존 점수: " + existingScore);

                if (score > existingScore) {
                    // 새로운 점수가 더 클 경우 업데이트
                    pstmt.close();
                    pstmt = conn.prepareStatement(updateScoreSql);
                    pstmt.setInt(1, score);
                    pstmt.setString(2, userId);
                    pstmt.executeUpdate();
                    System.out.println("점수 업데이트 성공: " + score);
                } else {
                    System.out.println("새로운 점수가 기존 점수보다 작아 업데이트하지 않음.");
                }
            } else {
                // 기존 점수가 없는 경우 새로 삽입
                pstmt.close();
                pstmt = conn.prepareStatement(insertScoreSql);
                pstmt.setString(1, userId);
                pstmt.setInt(2, score);
                pstmt.executeUpdate();
                System.out.println("새 점수 삽입 성공: " + score);
            }
        } catch (Exception e) {
            System.err.println("DB 작업 중 오류 발생");
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }
    }
}

