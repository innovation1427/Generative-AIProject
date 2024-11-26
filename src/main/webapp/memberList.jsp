<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.servlet.mvc1.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> 회원목록 </h1>
<%
	String lo = (String)session.getAttribute("loginCheck");
	
	if(lo == null) out.println("<a href=Login.jsp> 로그인 </a>");
	else out.println("<a href=logout.do> 로그아웃 </a>");

    ArrayList<RegisterDTO> vlist=(ArrayList<RegisterDTO>) session.getAttribute("vlist");
	for(int i=0; i<vlist.size(); i++){
		RegisterDTO regBean=vlist.get(i);
		out.println( regBean.getId() + "," );
		out.println( regBean.getPassword() + "," );
		out.println( regBean.getName() + ",");
		out.println( regBean.getEmail() + ",");
		out.println( regBean.getBirthday() + ",");
		out.println( regBean.getGender() + ",");
		out.println( regBean.getNickname() + ",");
		out.println( regBean.getRole() + "<BR>");
	}

%>


</body>
</html>