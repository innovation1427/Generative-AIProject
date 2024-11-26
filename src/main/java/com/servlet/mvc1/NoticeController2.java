package com.servlet.mvc1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice2.do")
public class NoticeController2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            // 공지사항 번호를 가져오고 없으면 -1로 설정
            int noticeId = -1;
            if (request.getParameter("num") != null) {
                noticeId = Integer.parseInt(request.getParameter("num"));
            }

            // 가져온 공지사항 데이터를 request에 저장하여 JSP로 전달
            if (noticeId != -1) {
                // 여기서 데이터베이스 연결 등 필요한 작업을 수행합니다.
                NoticeDAO ndao = new NoticeDAO();
                NoticeDTO notice = ndao.selectNoticeById(noticeId);

                // 가져온 데이터를 request에 저장하여 JSP로 전달
                session.setAttribute("notice", notice);

                // JSP로 포워딩
                RequestDispatcher dispatcher = request.getRequestDispatcher("notice2.jsp");
                dispatcher.forward(request, response);

                // 로그 추가
                System.out.println("NoticeController2: Fetched notice details.");
            } else {
                // NoticeId가 없는 경우는 목록 페이지로 이동
                response.sendRedirect("notice2.do?num=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
