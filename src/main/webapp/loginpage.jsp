<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/loginpage.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
</head>
<body>
<header>
    <section id="top">
      <a id="logo" href="index.jsp"><img src="img/PKLegend.PNG" alt="로고" width="230" style="height: auto;"></a>
     </section> <!-- section top -->
  </header> <!--header -->

	<section id="main">
	    <form method="get" action="login.do">
	        <h2>Login</h2>
	        <input type="text" id="username" name="id" placeholder="아이디" required>
	
	        <input type="password" id="password" name="password" placeholder="비밀번호" required>
	        
	        <button type="submit">로그인</button>
	    </form>
	<section id="sub">
	  
	
	    <!-- 링크들을 <ul>로 묶음 -->
	    <ul id="login-links">
	        <!-- 추가: 비밀번호 찾기 링크 -->
	        <li><a href="#">아이디 찾기</a></li>
	        
	        <!-- 추가: 아이디 찾기 링크 -->
	        <li><a href="#">비밀번호 찾기</a></li>
	        
	        <!-- 추가: 회원가입 링크 -->
	        <li><a href="Register.jsp">가입하기</a></li>
	    </ul>
	</section>
	</section>

 
</body>
</html>