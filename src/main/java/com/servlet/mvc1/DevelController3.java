package com.servlet.mvc1;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/inputdevel.do")
public class DevelController3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 세션 객체 얻어오기
        HttpSession session = request.getSession();
        DeveloperDAO nDao = new DeveloperDAO();
        String id = (String) session.getAttribute("idKey");
        String nick = nDao.getUserNickname(id);
        String title = request.getParameter("title");
        String content = request.getParameter("text");

        DeveloperDTO nDto = new DeveloperDTO();
        nDto.setNickname(nick);
        nDto.setTitle(title);
        nDto.setContent(content);
        
        // 중복된 변수명을 피하기 위해 수정한 부분
        String DeveloperIdParam = request.getParameter("DeveloperId");

        try {
        	nDao.increaseVisitCount(request);
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리 필요
        }
        
        boolean insertCheck = nDao.developerInsert(nDto);

        RequestDispatcher dispatcher = request.getRequestDispatcher("devel.do");
        dispatcher.forward(request, response);
    }
}
