<%@ page contentType="text/html;charset=UTF-8"%>
<html>

<head>
<link rel="stylesheet" type="text/css" href="css/Register.css">
<title>회원가입 페이지</title>

</head>
<body>
<header>
	<section id="top">
      <a id="logo" href="index.jsp"><img src="img/PKLegend.PNG" alt="로고" width="230" style="height: auto;"></a>
     </section> <!-- section top -->
  </header> <!--header -->

<section id="main">
	    <form method="get" action="register.do">
	        <h2>회원가입</h2>
	        <input type="text" id="username" name="id" placeholder="아이디" required size="20">
	
	        <input type="password" id="password" name="password" placeholder="비밀번호" required size="20">
	        <input type="text" id="email" name="email" placeholder="이메일" required size="255">

	        <input type="text" id="name" name="name" placeholder="이름" required size="20">
	        <input type="date" id="birthday" name="birthday" placeholder="생년월일" required>
	        
    		<div class="gender-radio">
        		<input type="radio" id="male" name="gender" value="남" required>
        		<label for="male">남</label>
    		</div>

    		<div class="gender-radio">
        		<input type="radio" id="female" name="gender" value="여" required>
        		<label for="female">여</label>
    		</div>
    		
	        <input type="text" id="nickname" name="nickname" placeholder="닉네임" required size="30">
	        
	        <div class="role-radio">
        		<input type="radio" id="nomal" name="role" value="일반" required>
        		<label for="male">일반</label>
    		</div>

    		<div class="role-radio">
        		<input type="radio" id="manager" name="role" value="관리자" required>
        		<label for="manager">관리자</label>
    		</div>
	        <button type="submit" value="회원가입">회원가입</button>
	    </form>
</section>
</body>
</html>