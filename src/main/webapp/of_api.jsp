<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/notice.css">
<title> 게임 관련 최신 API </title>
</head>
<body>
  <header>
    <section id="top">
      <a id="logo" href="index.jsp"><img src="img/PKLegend.PNG" alt="로고"></a>

<nav id="main_menu">
        <ul>
          <li><a href="notice.jsp">게임소식</a>
          <ul>
                <li><a href="notice.jsp">공지사항</a></li>
		        <li><a href="developernotes.jsp">개발자 노트</a></li>
		        <li><a href="update.jsp">업데이트</a></li>
		        <li><a href="event.jsp">이벤트</a></li>
		        <li><a href="guide.jsp">유저 가이드</a></li>
            </ul></li>
          <li><a href="#">게시판</a><ul>
          		<li><a href="api.jsp">게임 관련 최신 API</a></li>
          		<li><a href="of_api.jsp">공식 유튜브 API</a></li>
                <li><a href="recommend.jsp">추천</a></li>
		        <li><a href="freedom.jsp">자유</a></li>
		        <li><a href="tip.jsp">팁</a></li>
		        <li><a href="question,jsp">질문</a></li>
            </ul></li>
          <li><a href="#">웹 상점</a><ul>
                <li><a href="#">웹 상점</a></li>
		        <li><a href="#">맴버십</a></li>
		        <li><a href="#">스페셜 상점</a></li>
		        <li><a href="#">마이페이지</a></li>
            </ul></li>
          <li><a href="rank.do">데이터센터</a><ul>
          	<li><a href="rank.do">해외 축구 랭킹</a></li>
                <li><a href="#">데일리차트</a></li>
		        <li><a href="#">선수</a></li>
		        <li><a href="#">감독/팀컬러</a></li>
		        <li><a href="#">공식경기 랭킹</a></li>
		        <li><a href="#">아레나 랭킹</a></li>
		        <li><a href="#">강화 부스트 도우미</a></li>
		        <li><a href="#">스쿼드 만들기</a></li>
            </ul></li>
          <li><a href="#">고객지원</a><ul>
                <li><a href="#">다운로드/설치</a></li>
		        <li><a href="#">테스트 구장</a></li>
		        <li><a href="#">오픈API</a></li>
            </ul></li>
        </ul>
      </nav>

      <div class="clear"></div>
      
      <% 
      String mem_id = (String)session.getAttribute("idKey");
      
      %>
     </section> <!-- section top -->
  </header> <!--header -->

  <section id="main">
   <section id="cat">
    <section id="cat1">

     <section id="cat_title"></section>
     
     <section id="login" onclick="redirectToOtherPage()">
<%
    if (mem_id == null) {
        // 로그인이 되어 있지 않은 경우
        out.println("<a href='loginpage.jsp'> <img class='button-image' src='img/login.png' width ='185px' alt='Button'></a>");
    } else {
    	out.println("<a href='logout.do'> <img class='button-image' src='img/logout.png' width ='185px' alt='Button'></a>");
}
%>    
      </section>
      </section>
       <section id="serch">
        <input type="text" id="searchBox" name="search" placeholder="검색어를 입력하세요...">
        <button onclick="search()">검색</button>
      </section>
      <section id ="sub2_manu">
      <h2>게시판</h2>
      <ul>
      			<li><a href="api.jsp">게임 관련 최신 API</a></li>
      			<li><a href="of_api.jsp">공식 유튜브 API</a></li>
                <li><a href="recommend.jsp">추천</a></li>
		        <li><a href="freedom.jsp">자유</a></li>
		        <li><a href="tip.jsp">팁</a></li>
		        <li><a href="question.jsp">질문</a></li>
            </ul>
      </section>
      
     <script>
</script><!-- 로그인 버튼 -->

      
    </section> <!-- section cat1 -->

    <section id="cat2">
  <div class="board">
    <div class="board-title">공식 유튜브 API</div>
    <jsp:include page="of_api_list.jsp" />
  </div>
</section>
    
    <div class="clear"></div>

 </section> <!-- section main -->

      <footer>
      <section id="footer_menu_box">
            <nav id="footer_menu">
            <ul>
              <li><a href="#">회사소개</a></li>
              <li><a href="#">채용안내</a></li>
              <li><a href="#">이용약관</a></li>
              <li><a href="#">개인정보취급방침</a></li>
              <li><a href="#">게임이용등급안내</a></li>
              <li><a href="#">정소년보호정책</a></li>
              <li><a href="#">운영정책</a></li>
              <!-- 관리자만 list.do에 접속가능하게 설정 -->
              <li>
    			<c:if test="${sessionScope.role eq '관리자'}">
        		<a href="list.do">관리자 페이지</a>
    			</c:if>
			  </li>
            </ul>
          </nav>
        </section>
          
        <section id="footer_address">
            <p class="text-center">(주)동양 5조 웹프로젝트, 서울시 구로구 경인로 445 ([구]고척동 62-160) 동양미래대학교</p>
    		<p class="text-center">COPYRIGHT(c) DONGYANG 5조 WebProject. ALL RIGHTS RESERVED.</p>
        </section> <!-- section footer_address -->
      </footer>
</body>
</html>