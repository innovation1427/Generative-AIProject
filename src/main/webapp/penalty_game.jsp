<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, java.util.Map, java.util.HashMap, java.sql.*" %>
<%
    String userId = (String) session.getAttribute("idKey");
    int currentScore = 0;

    if (userId != null) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String scoreQuery = "SELECT score FROM game_score WHERE user_id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/pk_legend", "root", "dongyang");

            pstmt = conn.prepareStatement(scoreQuery);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                currentScore = rs.getInt("score");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>승부차기 게임</title>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <style>
        body {
            text-align: center;
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        canvas {
            border: 2px solid #000;
            display: block;
            margin: 20px auto;
        }
        #controls {
            margin-top: 20px;
        }
        button {
            padding: 10px 20px;
            font-size: 16px;
            margin: 5px;
            cursor: pointer;
        }
        #scoreBoard {
            margin-top: 20px;
        }
        #current-score {
            font-size: 18px;
            font-weight: bold;
            color: #000;
        }
        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
        }
    </style>
    <script>
document.addEventListener("DOMContentLoaded", function () {
    const canvas = document.getElementById("gameCanvas");
    const ctx = canvas.getContext("2d");

    const canvasWidth = canvas.width;
    const canvasHeight = canvas.height;

    let ball = { x: canvasWidth / 2, y: canvasHeight - 50, radius: 20 };
    let goalkeeperDirection = "center";
    let score = 0; // 점수 초기화
    let highScore = <%= currentScore %>; // DB에서 가져온 최고 점수
    let showMessage = null; // 득점/실패 메시지 상태
    let isGameOver = false; // 게임 종료 여부

    const retryButtonImage = new Image();
    retryButtonImage.src = "<%= request.getContextPath() %>/img/retry_btn.png";

    const goal = { left: canvasWidth / 4, center: canvasWidth / 2, right: (3 * canvasWidth) / 4 };

    const goalkeeperImages = {
        left: "<%= request.getContextPath() %>/img/savegoal_L.png",
        center: "<%= request.getContextPath() %>/img/savegoal_C.png",
        right: "<%= request.getContextPath() %>/img/savegoal_R.png",
        missLeft: "<%= request.getContextPath() %>/img/missgoal_L.png",
        missRight: "<%= request.getContextPath() %>/img/missgoal_R.png",
        stand: "<%= request.getContextPath() %>/img/goalkeeper_stand.png"
    };

    const messageImages = {
        goal: "<%= request.getContextPath() %>/img/goal.png",
        fail: "<%= request.getContextPath() %>/img/fail.png"
    };

    let goalkeeperImage = new Image();
    goalkeeperImage.src = goalkeeperImages.stand;

    const fieldImage = new Image();
    fieldImage.src = "<%= request.getContextPath() %>/img/field.png";

    const ballImage = new Image();
    ballImage.src = "<%= request.getContextPath() %>/img/ball.png";

    const goalMessageImage = new Image();
    const failMessageImage = new Image();

    goalMessageImage.src = messageImages.goal;
    failMessageImage.src = messageImages.fail;

    function updateScoreDisplay() {
        ctx.clearRect(0, 0, canvasWidth, 50); // 점수 영역 초기화
        ctx.fillStyle = "black";
        ctx.font = "20px Arial";
        ctx.fillText(`점수: ${score}`, canvasWidth - 140, 30);
        ctx.fillText(`최고 점수: ${highScore}`, canvasWidth - 300, 30);
    }

    function drawRetryButton() {
        const buttonX = canvasWidth / 2 - 100;
        const buttonY = canvasHeight / 2 + 80;
        ctx.drawImage(retryButtonImage, buttonX, buttonY, 200, 110);
    }

    document.addEventListener("keydown", function (event) {
        if (isGameOver) return;
        if (event.key === "1") {
            shootBall("left");
        } else if (event.key === "2") {
            shootBall("center");
        } else if (event.key === "3") {
            shootBall("right");
        }
    });

    document.getElementById("leftBtn").addEventListener("click", () => {
        if (!isGameOver) shootBall("left");
    });
    document.getElementById("centerBtn").addEventListener("click", () => {
        if (!isGameOver) shootBall("center");
    });
    document.getElementById("rightBtn").addEventListener("click", () => {
        if (!isGameOver) shootBall("right");
    });

    function setRandomGoalkeeperDirection() {
        const directions = ["left", "center", "right"];
        goalkeeperDirection = directions[Math.floor(Math.random() * directions.length)];
    }

    function shootBall(direction) {
        setRandomGoalkeeperDirection();

        if (direction === "left") ball.x = goal.left;
        if (direction === "center") ball.x = goal.center;
        if (direction === "right") ball.x = goal.right;

        ball.y = canvasHeight - 200;

        checkCollision(direction);
    }

    function saveScoreToDatabase(finalScore) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/5teamfinalProject/SaveScoreController", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("점수 저장 성공");
            } else {
                console.error("점수 저장 실패: " + xhr.responseText);
            }
        };

        xhr.onerror = function () {
            console.error("AJAX 요청 중 오류 발생");
        };

        xhr.send("score=" + finalScore);
    }

    function checkCollision(ballDirection) {
        if (ballDirection === goalkeeperDirection) {
            goalkeeperImage.src = goalkeeperImages[goalkeeperDirection];
            showMessage = "fail";
            isGameOver = true;
            if (score > highScore) {
                highScore = score;
                saveScoreToDatabase(highScore);
            }
            setTimeout(() => {
                drawGame();
            }, 100);
        } else {
            score += 100;
            if (goalkeeperDirection === "left") {
                goalkeeperImage.src = goalkeeperImages.missLeft;
            } else if (goalkeeperDirection === "right") {
                goalkeeperImage.src = goalkeeperImages.missRight;
            } else {
                goalkeeperImage.src = goalkeeperImages.stand;
            }

            showMessage = "goal";
            setTimeout(() => {
                showMessage = null;
                resetBall();
            }, 700);
        }
        updateScoreDisplay();
        drawGame();
    }

    function resetBall() {
        ball.x = canvasWidth / 2;
        ball.y = canvasHeight - 50;
        drawGame();
    }

    function resetGame() {
        score = 0; // 점수 초기화
        showMessage = null;
        isGameOver = false;
        resetBall();
        updateScoreDisplay();
    }

    canvas.addEventListener("click", function (event) {
        if (isGameOver) {
            const rect = canvas.getBoundingClientRect();
            const clickX = event.clientX - rect.left;
            const clickY = event.clientY - rect.top;

            const buttonX = canvasWidth / 2 - 100;
            const buttonY = canvasHeight / 2 + 50;

            if (
                clickX >= buttonX &&
                clickX <= buttonX + 200 &&
                clickY >= buttonY &&
                clickY <= buttonY + 80
            ) {
                resetGame();
            }
        }
    });

    function drawGame() {
        ctx.clearRect(0, 0, canvasWidth, canvasHeight);

        ctx.drawImage(fieldImage, 0, 0, canvasWidth, canvasHeight);

        let goalkeeperX = canvasWidth / 2 - 50;
        if (goalkeeperDirection === "left") {
            goalkeeperX = canvasWidth / 4 - 50;
        } else if (goalkeeperDirection === "right") {
            goalkeeperX = (3 * canvasWidth) / 4 - 50;
        }

        ctx.drawImage(goalkeeperImage, goalkeeperX, 120, 100, 100);

        updateScoreDisplay();

        if (!isGameOver) {
            ctx.drawImage(ballImage, ball.x - ball.radius, ball.y - ball.radius, ball.radius * 2, ball.radius * 2);
        }

        if (showMessage === "goal") {
            ctx.globalAlpha = 0.7;
            ctx.drawImage(goalMessageImage, canvasWidth / 2 - 125, canvasHeight / 2 - 125, 250, 250);
            ctx.globalAlpha = 1.0;
        } else if (showMessage === "fail") {
            ctx.globalAlpha = 0.7;
            ctx.drawImage(failMessageImage, canvasWidth / 2 - 125, canvasHeight / 2 - 160, 250, 250);
            ctx.globalAlpha = 1.0;

            drawRetryButton();
        }
    }

    goalkeeperImage.onload = drawGame;
    fieldImage.onload = drawGame;
    ballImage.onload = drawGame;
    retryButtonImage.onload = drawGame;
});
</script>

    
</head>
<body>
    <%@ include file="header.jsp" %>
    <section id="main">
        <section id="cat1">
            <section id="cat_title">
                <a href="penalty_game.do" class="game-start-button"></a>
            </section>
            <section id="login">
                <% if (userId == null) { %>
                    <a href="loginpage.jsp">
                        <img class="button-image" src="img/login.png" width ='185px' alt='Button'>
                    </a>
                <% } else { %>
                    <a href="logout.do">
                        <img class="button-image" src="img/logout.png" width ='185px' alt='Button'>
                    </a>
                <% } %>
            </section>
        </section>

        <section id="game">
            <h1>승부차기 게임</h1>
            <canvas id="gameCanvas" width="600" height="400"></canvas>
            <div id="controls">
                <button id="leftBtn">왼쪽</button>
                <button id="centerBtn">가운데</button>
                <button id="rightBtn">오른쪽</button>
            </div>    
            
            <div id="score-container">
        		<p id="high-score">최고 점수: <%= currentScore %></p>
        		<p id="current-score">점수: 0</p>
    		</div>
        </section>
    </section>
    <%@ include file="footer.jsp" %>
</body>
</html>
