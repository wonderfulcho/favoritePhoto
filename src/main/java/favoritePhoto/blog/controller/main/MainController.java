package favoritePhoto.blog.controller.main;

import favoritePhoto.blog.model.gallery.GalleryEntity;
import favoritePhoto.blog.model.market.MarketDTO;
import favoritePhoto.blog.service.gallery.GalleryService;
import favoritePhoto.blog.service.market.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController {

    private final GalleryService galleryService;
    private final MarketService marketService;

    @Autowired
    public MainController(GalleryService galleryService, MarketService marketService) {
        this.galleryService = galleryService;
        this.marketService = marketService;
    }

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model,
                           @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /* 갤러리 페이지 */
        Page<GalleryEntity> galleryPage = galleryService.pageList(pageable);
        model.addAttribute("gallery", galleryPage);

        List<String> imageUrls = new ArrayList<>();
        // 페이지의 GalleryEntity 레코드들 확인
        List<GalleryEntity> galleryEntities = galleryPage.getContent();
        for (GalleryEntity galleryEntity : galleryEntities) {
            String htmlContent = galleryEntity.getContent();
            String imgTagPattern = "<img\\s+[^>]*src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 정규식 패턴
            Pattern pattern = Pattern.compile(imgTagPattern);
            Matcher matcher = pattern.matcher(htmlContent);
            if (matcher.find()) { // 첫 번째 매치된 이미지 태그 추출
                String imgUrl = matcher.group(1);
                imageUrls.add(imgUrl);
            } else {
                System.out.println("이미지 태그 없음");
            }
        }

        // 이미지 URL 리스트를 모델에 추가
        model.addAttribute("imageUrls", imageUrls);


        /* 마켓 페이지 */
        Page<MarketDTO> marketPage = marketService.getCrawledDataByPage(pageable);
        model.addAttribute("market", marketPage);


        return "main/main";
    }
}
