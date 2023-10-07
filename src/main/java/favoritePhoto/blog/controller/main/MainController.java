package favoritePhoto.blog.controller.main;

import favoritePhoto.blog.model.gallery.GalleryDTO;
import favoritePhoto.blog.model.gallery.GalleryEntity;
import favoritePhoto.blog.service.gallery.GalleryService;
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

    @Autowired
    public MainController(GalleryService galleryService) { this.galleryService = galleryService; }

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model,
                           @PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GalleryEntity> page = galleryService.pageList(pageable);
        model.addAttribute("page", page);

        // 페이지의 GalleryEntity 레코드들에서 이미지 URL 추출
        List<String> imageUrls = new ArrayList<>();
        // 페이지의 GalleryEntity 레코드들 확인
        List<GalleryEntity> galleryEntities = page.getContent();
        for (GalleryEntity galleryEntity : galleryEntities) {
            String htmlContent = galleryEntity.getContent();
            // 정규식 패턴
            String imgTagPattern = "<img\\s+[^>]*src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
            // 정규식 매치
            Pattern pattern = Pattern.compile(imgTagPattern);
            Matcher matcher = pattern.matcher(htmlContent);
            // 첫 번째 매치된 이미지 태그 추출
            if (matcher.find()) {
                String imgUrl = matcher.group(1);
                imageUrls.add(imgUrl);
            } else {
                System.out.println("이미지 태그 없음");
            }
        }

        // 이미지 URL 리스트를 모델에 추가
        model.addAttribute("imageUrls", imageUrls);

        return "main/main";
    }
}
