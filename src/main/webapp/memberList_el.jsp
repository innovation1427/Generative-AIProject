<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com. servlet.mvc1.*" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
    <h1> <a id="logo" href="index.jsp"><img src="img/PKLegend.PNG" alt="로고"></a> </h1>
    로그인 여부: ${loginCheck}
    <c:if test="${loginCheck == null }">
        <a href=loginpage.jsp> 로그인 </a>
    </c:if>
    <c:if test="${loginCheck != null }">
        <a href=logout.do> 로그아웃 </a>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Password</th>
                <th>Name</th>
                <th>Email</th>
                <th>Birthday</th>
                <th>Gender</th>
                <th>Nickname</th>
                <th>Role</th>
                <th>동작</th> <!-- 버튼을 위한 열 추가 -->
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${vlist}" var="memberList">
                <tr>
                    <td style="text-align: center;">${memberList.id}</td>
                    <td style="text-align: center;">${memberList.password}</td>
                    <td style="text-align: center;">${memberList.name}</td>
                    <td style="text-align: center;">${memberList.email}</td>
                    <td style="text-align: center;">${memberList.birthday}</td>
                    <td style="text-align: center;">${memberList.gender}</td>
                    <td style="text-align: center;">${memberList.nickname}</td>
                    <td style="text-align: center;">${memberList.role}</td>
                    <td style="text-align: center;">
                        <button onclick="deleteUser('${memberList.id}')">계정 삭제</button>
                        <button onclick="editUser('${memberList.id}', '${memberList.password}', '${memberList.email}', '${memberList.name}', '${memberList.birthday}', '${memberList.gender}', '${memberList.nickname}', '${memberList.role}')">계정 수정</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${not empty deleteMessage}">
        <p>${deleteMessage}</p>
    </c:if>

    <!-- 계정 수정 폼 -->
    <div id="editForm" style="display: none;">
    <h2>계정 수정 폼</h2>
    <form id="updateForm">
        <input type="hidden" id="editId" name="editId" value="">
        <label for="editPassword">새 비밀번호:</label>
        <input type="text" id="editPassword" name="editPassword"><br>
        <label for="editEmail">새 이메일:</label>
        <input type="text" id="editEmail" name="editEmail"><br>
        <label for="editName">새 이름:</label>
        <input type="text" id="editName" name="editName"><br>
        <label for="editBirthday">새 생년월일:</label>
        <input type="text" id="editBirthday" name="editBirthday"><br>
        <label for="editGender">새 성별:</label>
        <input type="text" id="editGender" name="editGender"><br>
        <label for="editNickname">새 닉네임:</label>
        <input type="text" id="editNickname" name="editNickname"><br>
        <label for="editRole">새 역할:</label>
        <input type="text" id="editRole" name="editRole"><br>
        <input type="submit" value="수정">
        <button type="button" onclick="hideEditForm()">취소</button>
    </form>
</div>

    <script>
        function deleteUser(Id) {
            if (confirm("이 사용자를 삭제하시겠습니까?")) {
                location.href = 'list.do?action=delete&Id=' + Id;
            }
        }

        function showEditForm(Id, password, email, name, birthday, gender, nickname, role) {
        	console.log('showEditForm 호출됨'); // 추가
            document.getElementById('editId').value = Id;
            document.getElementById('editPassword').value = password;
            document.getElementById('editEmail').value = email;
            document.getElementById('editName').value = name;
            document.getElementById('editBirthday').value = birthday;
            document.getElementById('editGender').value = gender;
            document.getElementById('editNickname').value = nickname;
            document.getElementById('editRole').value = role;
            document.getElementById('editForm').style.display = 'block';
        }

        function hideEditForm() {
            document.getElementById('editForm').style.display = 'none';
        }

        function editUser(Id, password, email, name, birthday, gender, nickname, role) {
            $.ajax({
                url: 'list.do?action=update', // action 파라미터 추가
                type: 'POST',
                data: {
                    Id: Id,
                    newPassword: password,
                    newEmail: email,
                    newName: name,
                    newBirthday: birthday,
                    newGender: gender,
                    newNickname: nickname,
                    newRole: role
                },
                success: function(response) {
                    console.log('서버 응답:', response);
                    if (response === 'success') {
                        alert('수정할 값을 입력하시오.');
                        showEditForm(Id, password, email, name, birthday, gender, nickname, role);
                    } else {
                        alert('수정 실패: 서버 응답이 실패했습니다.');
                    }
                },
                error: function(error) {
                    console.error('에러 발생:', error);
                }
            });
        }
    </script>
</body>
</html>