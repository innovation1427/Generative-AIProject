package crawling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@WebServlet("/rank.do")
public class rankcrawling extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public rankcrawling() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 크롤링 코드 작성
        List<Element> tables = performRankCrawlingTables();
        List<String> titleDataNames = performRankCrawlingTitleDataNames();

        // 각 테이블과 타이틀을 JSP 페이지로 전달
        request.setAttribute("rankTables", tables);
        request.setAttribute("titleDataNames", titleDataNames);

        // JSP 페이지로 포워드
        request.getRequestDispatcher("/rankcrawling.jsp").forward(request, response);
        
    }

    private List<Element> performRankCrawlingTables() {
        List<Element> tables = new ArrayList<>();

        try {
            // 크롤링할 대상 페이지 URL
            String url = "https://sports.news.naver.com/wfootball/index";

            // 해당 페이지에 접속하여 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // 원하는 데이터를 추출하는 CSS 선택자 사용
            tables = document.select("div.hmb_tbl._team_rank_area table");

        } catch (Exception e) {
            e.printStackTrace();
            // 크롤링 중 오류 발생 시, 빈 리스트 반환
        }

        return tables;
    }

    private List<String> performRankCrawlingTitleDataNames() {
        List<String> titleDataNames = new ArrayList<>();

        try {
            // 크롤링할 대상 페이지 URL
            String url = "https://sports.news.naver.com/wfootball/index";

            // 해당 페이지에 접속하여 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // 원하는 데이터를 추출하는 CSS 선택자 사용
            Elements titles = document.select("div.hmb_tab a");

            // 각 타이틀의 a 태그에서 data-name 속성의 값을 추출하여 리스트에 추가
            for (Element title : titles) {
                String dataName = title.attr("data-name").trim(); // trim()을 사용하여 공백 제거
                if (!dataName.isEmpty()) { // 데이터가 비어있지 않은 경우에만 추가
                    titleDataNames.add(dataName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 크롤링 중 오류 발생 시, 빈 리스트 반환
        }

        return titleDataNames;
    }
}