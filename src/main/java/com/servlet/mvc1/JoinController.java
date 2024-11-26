package com.servlet.mvc1;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register.do")
public class JoinController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String nickname = request.getParameter("nickname");
		String role = request.getParameter("role");

		// DTO 객체 생성 및 값 설정
		RegisterDTO mDto = new RegisterDTO();
		mDto.setId(id);
		mDto.setPassword(pw);
		mDto.setEmail(email);
		mDto.setName(name);
		mDto.setBirthday(birthday);
		mDto.setGender(gender);
		mDto.setNickname(nickname);
		mDto.setRole(role);
		mDto.setScore(0); // 기본값 설정

		// DAO 호출
		RegisterDAO mDao = new RegisterDAO();	
		boolean insertCheck = mDao.memberInsert(mDto);

		if (insertCheck) {
			request.setAttribute("joinResult", insertCheck);
			HttpSession session = request.getSession();
			session.setAttribute("idKey", id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("joinResult", 0);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
			dispatcher.forward(request, response);
		}
	}
}
