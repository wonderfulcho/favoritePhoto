package favoritePhoto.blog.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import favoritePhoto.blog.model.member.MemberDTO;
import favoritePhoto.blog.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/member")
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/join")
    public String joinPage(Model model) {
        // 이메일 중복 검사하기 위해 memberList를 회원가입 페이지에 넘겨준다.
        model.addAttribute("memberList", memberService.memberDTOList());
        return "/member/join";
    }

    @PostMapping("/joinOk")
    public String join(@ModelAttribute MemberDTO memberDTO) {
        memberService.memberDTOJoin(memberDTO);
        return "redirect:/main";
    }
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("memberList", memberService.memberDTOList());
        return "/member/login";
    }

    @PostMapping("/loginOK")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String loginEmail = memberService.memberLogin(email, password);
        if (loginEmail != null) {
            MemberDTO loginMember = memberService.memberDTOByEmail(email);
            session.setAttribute("loginMember", loginMember);
            response.put("success", true);
            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "/member/myPage";
    }


    @PostMapping("/memberUpdate")
    public ResponseEntity<Map<String, Object>> memberUpdate(@RequestBody Map<String, Object> requestData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String memberDTOJson = objectMapper.writeValueAsString(requestData.get("memberDTO"));
        MemberDTO memberDTO = objectMapper.readValue(memberDTOJson, MemberDTO.class);
        String newPassword = (String) requestData.get("newPassword");
        String passwordConfirm = (String) requestData.get("passwordConfirm");

        Map<String, Object> response = new HashMap<>();

        int check = memberService.memberDTOUpdate(memberDTO, newPassword, passwordConfirm);

        response.put("check", check);

        return ResponseEntity.ok(response);
    }

}
