<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<script src="https://apis.google.com/js/client.js?onload=loadYouTubeClient"></script>
<title> PK LEGEND </title>
</head>
<body>
  <header>
    <section id="top">
      <a id="logo" href="index.jsp"><img src="img/PKLegend.PNG" alt="로고"></a>

       <nav id="main_menu">
        <ul>
          <li><a href="notice.do">게임소식</a>
          <ul>
                <li><a href="notice.do">공지사항</a></li>
		        <li><a href="devel.do">개발자 노트</a></li>
		        <li><a href="update.jsp">업데이트</a></li>
		        <li><a href="event.jsp">이벤트</a></li>
		        <li><a href="guide.jsp">유저 가이드</a></li>
            </ul></li>
          <li><a href="api.jsp">게시판</a>
    	<ul>
        	<li><a href="premier_league.jsp">프리미어리그</a></li>
        	<li><a href="la_liga.jsp">라리가</a></li>
        	<li><a href="bundesliga.jsp">분데스리가</a></li>
        	<li><a href="serie_a.jsp">세리에A</a></li>
        	<li><a href="ligue_1.jsp">리그앙</a></li>
        	<li><a href="k_league.jsp">K리그</a></li>
        	<li><a href="api.jsp">게임 관련 최신 API</a></li>
        	<li><a href="of_api.jsp">공식 유튜브 API</a></li>
    	</ul></li>

          <li><a href="#">웹 상점</a><ul>
                <li><a href="#">웹 상점</a></li>
		        <li><a href="#">맴버십</a></li>
		        <li><a href="#">스페셜 상점</a></li>
		        <li><a href="#">마이페이지</a></li>
            </ul></li>
          <li><a href="rank.do">데이터센터</a><ul>
          	<li><a href="rank.do">해외 축구 랭킹</a></li>
          	<li><a href="score_ranking.html">PK 게임 랭킹</a></li>
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
    <section id="cat1">

     <section id="cat_title">
    	<a href="penalty_game.do" class="game-start-button">
    	</a>
	</section>


     
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
      
     <script>
</script><!-- 로그인 버튼 -->

      
    </section> <!-- section cat1 -->

    <section id="cat2">
      <img src="img/lolling.png" >
    </section> <!-- section cat2 -->

    
    <div class="clear"></div>
    <section id="midmenu">  
    <div>
        <input type="text" id="searchBox" name="search" placeholder="검색어를 입력하세요..."> 
        <button onclick="search()">검색</button>
        <nav id="sub_menu">
        <ul>
          <li><a href="notice.do">공지사항</a></li>
          <li><a href="devel.do">개발자 노트</a></li>
          <li><a href="api.jsp">게임 관련 API</a></li>
          <li><a href="of_api.jsp">공식 유튜브 API</a></li>
          <li><a href="rank.do">해외 축구 랭킹</a></li>
        </ul>
      </nav>
    </div>
    
    
  	</section> <!-- section recommend -->
	<div class="clear"></div>
    <section id="recommend">
        <div class="item">
         <h1>공식영상</h1>
    	<div id="player"></div>
      
   </div>

   <div class="item">
         <h1>크리에이터</h1>
    	<div id="player2"></div>
         
   </div>
      <div class="item">
   <h1>해외 축구 랭킹</h1>
   <a id="rank" href="rank.do"><img src="img/rank.png" width = "300px" height = "300px"></a>
</div>

     
  </section>
  <script>
  var apiKey = 'AIzaSyAQufDnWVHWSgwhWqWBH4NZZoG9kQGzCy0';
  var done = false; // done 변수를 선언하고 초기값을 false로 설정

  // Iframe Player API를 비동기적으로 로드한다.
  var tag = document.createElement('script');
  tag.src = "https://www.youtube.com/iframe_api";
  var firstScriptTag = document.getElementsByTagName('script')[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

  var player, player2;

  function onYouTubeIframeAPIReady() {
    // 첫 번째 동영상 플레이어 생성
    player = new YT.Player('player', {
      height: '300',
      width: '300',
      videoId: 'j8rh4W3gPAI',
      playerVars: {
        'rel': 0,
        'controls': 0,
        'autoplay': 1,
        'mute': 1,
        'loop': 1,
        'playsinline': 1
      },
      events: {
        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange
      },
      key: apiKey
    });

    // 두 번째 동영상 플레이어 생성
    player2 = new YT.Player('player2', {
      height: '300',
      width: '300',
      videoId: 'IDFWxnTqw8g',
      playerVars: {
        'rel': 0,
        'controls': 0,
        'autoplay': 1,
        'mute': 1,
        'loop': 1,
        'playsinline': 1
      },
      events: {
        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange
      },
      key: apiKey
    });
  }

  // 4. API는 비디오 플레이어가 준비되면 아래의 function을 불러올 것이다.
  function onPlayerReady(event) {
    event.target.playVideo();
  }

  // 5. API는 플레이어의 상태가 변경되면 아래의 function을 불러올 것이다.
  function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.PLAYING && !done) {
      setTimeout(stopVideo, 5000);
      done = true;
    }
  }

  function stopVideo() {
    player.stopVideo();
  }
</script>

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