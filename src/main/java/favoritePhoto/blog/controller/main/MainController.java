package favoritePhoto.blog.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginMember")
@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(HttpSession session) {
        System.out.println("loginMember = " + session.getAttribute("loginMember"));
        return "main/main";
    }
}
