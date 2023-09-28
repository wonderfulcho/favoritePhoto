package favoritePhoto.blog.service.member;

import favoritePhoto.blog.model.member.MemberDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface MemberService {

    public void memberJoin(@ModelAttribute MemberDTO memberDTO);
}
