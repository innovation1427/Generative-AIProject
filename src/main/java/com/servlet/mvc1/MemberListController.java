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


@WebServlet("/list.do")
public class MemberListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RegisterDAO rdao;  // RegisterDAO 객체를 클래스 필드로 선언

    public void init() throws ServletException {
        // 서블릿 초기화 시 RegisterDAO 객체를 생성하고 필드에 할당
        rdao = new RegisterDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        // '관리자'인 경우에만 진행
        if ("관리자".equals(role)) {
            try {
                ArrayList<RegisterDTO> mList = rdao.selectMemberList();
                session.setAttribute("vlist", mList);
                session.setAttribute("loginCheck", "ok");

                String action = request.getParameter("action");
                if (action != null) {
                    if (action.equals("delete")) {
                        performDelete(request);
                    } else if (action.equals("update")) {
                        performUpdate(request, response);
                        return; // Ajax 요청에 대한 응답을 직접 작성하므로 더 이상 진행하지 않음
                    }
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher("memberList_el.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            performUpdate(request, response);
        } else {
            String Id = request.getParameter("Id");
            String newPassword = request.getParameter("newPassword");
            String newEmail = request.getParameter("newEmail");
            String newName = request.getParameter("newName");
            String newBirthday = request.getParameter("newBirthday");
            String newGender = request.getParameter("newGender");
            String newNickname = request.getParameter("newNickname");
            String newRole = request.getParameter("newRole");

            boolean updateResult = updateUser(Id, newPassword, newEmail, newName, newBirthday, newGender, newNickname,
                    newRole);

            if (updateResult) {
                response.sendRedirect("list.do");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "업데이트 실패");
            }
        }
    }

    private void performDelete(HttpServletRequest request) {
        String Id = request.getParameter("Id");
        boolean deleteResult = deleteUser(Id);

        if (deleteResult) {
            request.setAttribute("deleteMessage", "사용자가 삭제되었습니다.");
        }
    }

    private void performUpdate(HttpServletRequest request, HttpServletResponse response) {
        String Id = request.getParameter("Id");
        String newPassword = request.getParameter("newPassword");
        String newEmail = request.getParameter("newEmail");
        String newName = request.getParameter("newName");
        String newBirthday = request.getParameter("newBirthday");
        String newGender = request.getParameter("newGender");
        String newNickname = request.getParameter("newNickname");
        String newRole = request.getParameter("newRole");

        boolean updateResult = updateUser(Id, newPassword, newEmail, newName, newBirthday, newGender, newNickname,
                newRole);

        try {
            // Ajax 응답 작성
            response.setContentType("text/plain;charset=UTF-8"); // MIME 타입 및 인코딩 수정
            response.setCharacterEncoding("UTF-8");
            if (updateResult) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure: 업데이트 실패");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteUser(String ID) {
        return rdao.deleteUser(ID);
    }

    public boolean updateUser(String Id, String newPassword, String newEmail, String newName, String newBirthday,
            String newGender, String newNickname, String newRole) {
        return rdao.updateUser(Id, newPassword, newEmail, newName, newBirthday, newGender, newNickname, newRole);
    }
}