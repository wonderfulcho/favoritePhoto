package favoritePhoto.blog.controller.member;

import favoritePhoto.blog.model.member.MemberDTO;
import favoritePhoto.blog.service.member.MemberService;
import favoritePhoto.blog.service.member.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
public class MemberController {

    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("/join")
    public String joinPage() {
        return "/member/join";
    }

    @PostMapping("/joinOk")
    public String join(@Validated @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }
        memberService.memberJoin(memberDTO);
        return "redirect:/main";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "/member/login";
    }
}
