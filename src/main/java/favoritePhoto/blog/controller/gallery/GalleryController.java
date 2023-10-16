package favoritePhoto.blog.controller.gallery;

import favoritePhoto.blog.model.gallery.GalleryDTO;
import favoritePhoto.blog.model.gallery.GalleryEntity;
import favoritePhoto.blog.model.gallery.ReplyDTO;
import favoritePhoto.blog.service.gallery.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/gallery")
@Controller
public class GalleryController {

    private final GalleryService galleryService;

    @Autowired
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    /* 갤러리 메인페이지 */
    @GetMapping("/galleryMain")
    public String galleryMainPage(HttpSession session, Model model,
                                  @PageableDefault(page = 0, size = 6, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<GalleryEntity> page = galleryService.pageList(pageable);
        model.addAttribute("page", page);
        model.addAttribute("previous", page.hasPrevious() ? page.previousPageable().getPageNumber() : -1);
        model.addAttribute("next", page.hasNext() ? page.nextPageable().getPageNumber() : page.getTotalPages());
        model.addAttribute("startPage", page.getNumber() / 10 * 10);
        model.addAttribute("endPage", Math.min(page.getNumber() / 10 * 10 + 9, page.getTotalPages() - 1));
        model.addAttribute("loginMember", session.getAttribute("loginMember"));

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
                System.out.println("이미지가 없습니다.");
            }
        }

        // 이미지 URL 리스트를 모델에 추가
        model.addAttribute("imageUrls", imageUrls);


        return "gallery/galleryMain";
    }

    /* 갤러리 글 작성페이지 */
    @GetMapping("/galleryWrite")
    public String galleryWritePage() {
        return "gallery/galleryWrite";
    }

    /* 갤러리 글 저장 */
    @PostMapping("/save")
    public String galleryWriteSave(GalleryDTO galleryDTO) {
        galleryService.saveGallery(galleryDTO);

        return "redirect:/gallery/galleryMain";
    }

    /* 갤러리 글 상세 조회 */
    @GetMapping("/galleryDetail")
    public void galleryDetail(@RequestParam Long id, Model model, HttpSession session) {
        GalleryDTO galleryDTO = galleryService.galleryDetail(id);
        List<ReplyDTO> replyDTOList = galleryService.replyDTOList(id);

        model.addAttribute("item", galleryDTO);
        model.addAttribute("replyList", replyDTOList);
        model.addAttribute("loginMember", session.getAttribute("loginMember"));

        System.out.println("session = " + session.getAttribute("loginMember"));
    }

    /* 게시글 수정 페이지 */
    @GetMapping("galleryUpdate")
    public void galleryUpdatePage(@RequestParam Long id, Model model) {
        System.out.println("id : " + id);
        GalleryDTO galleryDTO = galleryService.galleryDetail(id);
        model.addAttribute("item", galleryDTO);
    }

    /* 게시글 수정 */
    @PostMapping("galleryUpdateOK")
    public String galleryUpdateOK(GalleryDTO galleryDTO) {
        galleryService.updateGallery(galleryDTO);

        return "redirect:/gallery/galleryMain";
    }


    /* 게시글 삭제 */
    @PostMapping("galleryDelete")
    public ResponseEntity<String> galleryDelete(@RequestParam Long id) {
        try {
            galleryService.gallerySoftDeleteById(id);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }

    /* 댓글 입력 */
    @PostMapping("/galleryReply")
    public String galleryReply(ReplyDTO replyDTO, Model model) {

        galleryService.saveReply(replyDTO);
        Long galleryID = replyDTO.getGalleryID();

        return "redirect:/gallery/galleryDetail?id=" + galleryID;
    }

    /* 댓글 삭제 */
    @PostMapping("/replyDelete")
    public ResponseEntity<String> replyDelete(@RequestParam Long id, Model model) {
        try {
            galleryService.softDeleteById(id);
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }
}
