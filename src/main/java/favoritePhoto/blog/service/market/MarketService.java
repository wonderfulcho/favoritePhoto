package favoritePhoto.blog.service.market;

import favoritePhoto.blog.model.market.MarketDTO;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MarketService {
    // 크롤링 할 홈페이지 주소 (3개의 사이트)
    private final String jCameraUrl1 = "https://j-camera.com/product/list.html?cate_no=334";
    private final String jCameraUrl2 = "https://j-camera.com/product/list.html?cate_no=334&page=2";
    private final String jCameraUrlMain = "https://j-camera.com/";
    private final String sazinzibbUrl = "https://sazinzibb.com/";
    private final String bandoLeicaUrl = "https://www.leica-storebando.co.kr/product/list.html?cate_no=442&page=";
    private final String bandoLeicaMain = "https://www.leica-storebando.co.kr/";

    public List<MarketDTO> crawlMarketData() {
        // 크롤링한 데이터 담아줄 리스트 생성
        List<MarketDTO> finalMarketItems = new ArrayList<>();
        List<MarketDTO> marketItems1 = new ArrayList<>();
        List<MarketDTO> marketItems2 = new ArrayList<>();
        List<MarketDTO> marketItems3 = new ArrayList<>();

        // 리스트에 저장하는 함수 호출
        crawlUrl(jCameraUrl1, marketItems1);
        crawlUrl(jCameraUrl2, marketItems1);
        crawlUrl(sazinzibbUrl, marketItems2);
        for (int i = 1; i <= 10; i++) {
            crawlUrl(bandoLeicaUrl + i, marketItems3);
        }

        // 아이디 값을 순서대로 증가시켜 저장
        long idCounter = 1;

        // 게시물에 날짜 데이터가 없기 때문에 반복문을 사용하여 배열의 첫번째 데이터부터 finalMarketItems에 저장
        // 사진집 사이트의 데이터 갯수가 항상 많기 때문에 사진집 사이트 데이터를 저장한 리스트의 사이즈로 반복문 횟수 설정
        for (int i = 0; i < marketItems2.size(); i++) {
            if (marketItems1.size() - 1 >= i ) {
                MarketDTO item = marketItems1.get(i);
                item.setId(idCounter++);
                finalMarketItems.add(item);
            }
            if (marketItems3.size() - 1 >= i) {
                MarketDTO item = marketItems3.get(i);
                item.setId(idCounter++);
                finalMarketItems.add(item);
            }
            MarketDTO item = marketItems2.get(i);
            item.setId(idCounter++);
            finalMarketItems.add(item);
        }

        return finalMarketItems;
    }
    private void crawlUrl(String url, List<MarketDTO> marketItems) {
        try {
            Connection conn = Jsoup.connect(url);
            Document doc = conn.get();

            Elements getTitleElements = doc.select("li.xans-record-");

            for (Element e : getTitleElements) {
                String pricePattern = "\\d+,\\d+,\\d+원";
                Pattern pattern = Pattern.compile(pricePattern);
                Matcher matcher = pattern.matcher(e.html());
                String price = "";
                // 정규 표현식과 일치하는 부분을 찾아서 저장
                if (matcher.find()) {
                    price = matcher.group();
                }

                String homepageUrl = "";
                String imgUrl = "";
                String title = e.select("strong.name").text().replaceAll("상품명 : ", ""); // 상품명

                String sliceUrl = url.substring(0, url.length() - 1); // 반도라이카 사이트 page 숫자 슬라이스
                if (sliceUrl.equals(bandoLeicaUrl)) { // 반도라이카 사이트 img, 매물 상세페이지 url
                    imgUrl = e.select("div.thumbnail img.thumb").attr("src");
                    homepageUrl = e.select("div.thumbnail a").attr("href").replaceAll("^/", bandoLeicaMain);
                } else { // 나머지 사이트 img, 매물 상세페이지 url
                    imgUrl = e.select("div.thumbnail img").attr("src").replaceAll("^//", "https://");
                    homepageUrl = e.select("div.thumbnail a").attr("href").replaceAll("^/", url.equals(sazinzibbUrl) ? sazinzibbUrl : jCameraUrlMain);
                }

                // 모든 필드 값이 null 또는 빈 문자열이 아닌 경우에만 DTO에 저장하고 리스트에 추가
                if (title != null && !title.isEmpty() &&
                        homepageUrl != null && !homepageUrl.isEmpty() &&
                        imgUrl != null && !imgUrl.isEmpty() &&
                        price != null && !price.isEmpty()) {

                    MarketDTO martketDTO = MarketDTO.builder()
                            .title(title)
                            .homepageUrl(homepageUrl)
                            .imgUrl(imgUrl)
                            .price(price)
                            .build();
                    marketItems.add(martketDTO);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    // 페이지네이션
    public Page<MarketDTO> getCrawledDataByPage(Pageable pageable) {
        // 전체 크롤링 데이터를 가져오는 메서드 호출
        List<MarketDTO> allCrawledData = crawlMarketData();

        // 페이지네이션을 적용하여 해당 페이지의 데이터를 추출
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        int endItem = Math.min(startItem + pageSize, allCrawledData.size());

        List<MarketDTO> paginatedData = allCrawledData.subList(startItem, endItem);

        // 페이지 번호와 페이지 크기에 맞는 페이지 객체 반환
        return new PageImpl<>(paginatedData, pageable, allCrawledData.size());
    }

}
