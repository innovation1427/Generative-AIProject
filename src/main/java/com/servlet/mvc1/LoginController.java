package com.servlet.mvc1;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login.do")
public class LoginController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 파라미터의 문자 인코딩을 UTF-8로 설정
        request.setCharacterEncoding("UTF-8");
        
        // 요청에서 사용자가 입력한 ID와 Password를 가져옴
        String id = request.getParameter("id");
        String pw = request.getParameter("password");

        // MemberDAO 객체를 생성하여 로그인 체크 수행
        MemberDAO mDao = new MemberDAO();
        boolean loginCheck = mDao.loginCheck(id, pw);

        // 만약 로그인 성공하면
        if (loginCheck) {
            // 해당 사용자의 역할(role)을 가져와 출력
            String role = mDao.getUserRole(id);
            System.out.println("User ID: " + id); // Console 창에 ID를 출력하여 확인
            System.out.println("User Role: " + role); // Console 창에 Role을 출력하여 확인

            // 로그인 결과를 request에 저장
            request.setAttribute("loginResult", loginCheck);

            // 세션을 가져와서 ID와 Role을 세션에 저장
            HttpSession session = request.getSession();
            session.setAttribute("idKey", id);
            session.setAttribute("role", role);

            // 만약 Role이 '관리자'이면 list.do로 포워딩
            if ("관리자".equals(role)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("list.do");
                dispatcher.forward(request, response);
            } else {
                // '관리자'가 아니면 다른 페이지로 이동 또는 처리
                // 예: index.jsp로 이동
                response.sendRedirect("index.jsp");
            }
        } else {
            // 로그인 실패 시 LogError.jsp로 리다이렉트
            response.sendRedirect("LogError.jsp");
        }
    }
}