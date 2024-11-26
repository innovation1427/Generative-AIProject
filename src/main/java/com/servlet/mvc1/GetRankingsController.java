package com.servlet.mvc1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/GetRankingsController")
public class GetRankingsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Map<String, String>> rankings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT user_id, score FROM game_score ORDER BY score DESC LIMIT 10";

        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/pk_legend", "root", "dongyang");

            // 쿼리 실행
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            // 결과를 리스트에 저장
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("user_id", rs.getString("user_id"));
                row.put("score", String.valueOf(rs.getInt("score")));
                rankings.add(row);
            }

            // JSON 변환 및 반환
            String json = new Gson().toJson(rankings);
            System.out.println("Generated JSON: " + json); // 디버깅 로그
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // HTTP 500 상태 설정
            response.getWriter().write("{\"error\": \"서버 에러 발생\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
