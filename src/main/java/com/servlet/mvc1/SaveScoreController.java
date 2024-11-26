package com.servlet.mvc1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SaveScoreController")
public class SaveScoreController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("idKey"); // 세션에서 사용자 ID 가져오기
        String scoreParam = request.getParameter("score"); // 클라이언트에서 점수 가져오기

        // 디버깅 로그: SaveScoreController 호출 여부 확인
        System.out.println("====================================");
        System.out.println("SaveScoreController 호출됨");
        System.out.println("사용자 ID: " + userId);
        System.out.println("전달받은 점수: " + scoreParam);
        System.out.println("====================================");

        if (userId == null || scoreParam == null || scoreParam.isEmpty()) {
            System.err.println("ERROR: 사용자 ID 또는 점수 값이 누락되었습니다.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("사용자 ID 또는 점수가 누락되었습니다.");
            return;
        }

        try {
            // 점수 변환 시도
            int score = Integer.parseInt(scoreParam);
            System.out.println("점수 변환 성공: " + score);

            // DAO 호출 및 데이터베이스 저장
            ScoreDAO scoreDAO = new ScoreDAO();
            scoreDAO.saveScore(userId, score);
            System.out.println("DB 업데이트 성공: 사용자 ID = " + userId + ", 점수 = " + score);

            // 성공 응답
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("점수 저장 성공");
        } catch (NumberFormatException e) {
            // 점수 형식 변환 실패
            System.err.println("ERROR: 점수 변환 실패 - 잘못된 형식 (" + scoreParam + ")");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("잘못된 점수 형식");
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("ERROR: 서버 오류 발생");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("서버 오류 발생: " + e.getMessage());
        }
    }
}
