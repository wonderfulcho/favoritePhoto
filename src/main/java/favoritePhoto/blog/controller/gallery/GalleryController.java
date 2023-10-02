package favoritePhoto.blog.controller.gallery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginMember")
@RequestMapping("/gallery")
@Controller
public class GalleryController {

    @GetMapping("/galleryMain")
    public String galleryMainPage(HttpSession session, Model model) {
        model.addAttribute("loginMember", session.getAttribute("loginMember"));
        return "/gallery/galleryMain";
    }

    @GetMapping("/galleryWrite")
    public String galleryWritePage() {
        return "/gallery/galleryWrite";
    }
}
