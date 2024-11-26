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
    <title>YouTube Video Search</title>
    <style>
        .video-container {
            display: flex;
            flex-wrap: wrap;
        }

        .video-item {
            width: 50%; /* 한 줄에 2개씩 나열하려면 50%로 설정 */
            box-sizing: border-box;
            padding: 1px;
        }
        
        .video-title {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            cursor: pointer;
            font-weight: bold; /* 글꼴 굵기 설정 */
        }

        iframe {
            width: 348px; /* 부모 요소에 맞게 전체 폭으로 설정 */
            height: 208px; /* 원하는 높이로 설정하세요 */
        }
    </style>
    <script>
        function toggleTitle(element) {
            // 클릭된 요소의 형제 요소를 찾아 토글
            var sibling = element.nextElementSibling;
            sibling.style.display = (sibling.style.display === 'none' || sibling.style.display === '') ? 'block' : 'none';
        }
    </script>
</head>
<body>

<%
    // YouTube API 키 (본인의 키로 교체)
    String apiKey = "";

    // 검색어 (검색하고 싶은 키워드로 교체)
    String searchQuery = "FC온라인";

    // YouTube API 호출 URL 생성
    String apiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + searchQuery + "&key=" + apiKey + "&maxResults=10";

    StringBuilder responseBuilder = new StringBuilder();

    try {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");

        int read;
        char[] buffer = new char[4096];

        while ((read = reader.read(buffer)) != -1) {
            responseBuilder.append(buffer, 0, read);
        }

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
        JSONArray items = jsonResponse.getJSONArray("items");

        // 동영상 리스트 출력
        out.println("<div class=\"video-container\">"); // 영상을 감싸는 컨테이너 열기

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONObject snippet = item.getJSONObject("snippet");

            String title = snippet.getString("title");
         	// 영상 제목을 최대 40자로 자르고 "..." 추가
            if (title.length() > 40) {
                title = title.substring(0, 40) + "...";
            }
         	
         // videoId 키의 존재 여부 확인 후 안전하게 가져오기
            if (item.has("id") && item.getJSONObject("id").has("videoId")) {
                String videoId = item.getJSONObject("id").getString("videoId");

                out.println("<div class=\"video-item\">"); // 영상 아이템 열기
                out.println("<iframe src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>");
                out.println("<p class=\"video-title\" onclick=\"toggleTitle(this)\">" + title + "</p>");
                out.println("</div>"); // 영상 아이템 닫기
            } else {
                // videoId가 없는 경우 해당 영상은 건너뛰기
                out.println("<!-- Video without videoId, skipping... -->");
            }
        }

        out.println("</div>"); // 영상을 감싸는 컨테이너 닫기
    } catch (IOException e) {
        out.println("Error occurred while fetching data from YouTube API: " + e.getMessage());
    } catch (Exception e) {
        out.println("Error occurred during processing: " + e.getMessage());
    } 
%>

</body>
</html>