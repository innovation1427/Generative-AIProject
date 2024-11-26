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

@WebServlet("/notice.do")
public class NoticeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // 페이지 번호 및 페이지 크기 파라미터 설정
        int currentPage = 1;
        int pageSize = 15;

        try {
            // 현재 페이지 번호를 가져오고 없으면 1로 설정
            if (request.getParameter("page") != null) {
                currentPage = Integer.parseInt(request.getParameter("page"));
            }

            // 여기서 데이터베이스 연결 등 필요한 작업을 수행합니다.
            NoticeDAO ndao = new NoticeDAO();
            ArrayList<NoticeDTO> noList = ndao.selectNoticeList(currentPage, pageSize);

            // 가져온 데이터를 request에 저장하여 JSP로 전달
            session.setAttribute("nList", noList);
            session.setAttribute("currentPage", currentPage);
            session.setAttribute("pageSize", pageSize);

            // 전체 페이지 수 계산 및 전달
            int totalPages = ndao.getTotalPages(pageSize);
            session.setAttribute("totalPages", totalPages);

            // JSP로 포워딩
            RequestDispatcher dispatcher = request.getRequestDispatcher("notice.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 로그 추가
        System.out.println("NoticeController: Fetched notice list.");
    }
}
