package favoritePhoto.blog.service.member;

import favoritePhoto.blog.model.member.MemberDTO;
import favoritePhoto.blog.model.member.MemberEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MemberService {

    public void memberDTOJoin(@ModelAttribute MemberDTO memberDTO);

    public List<MemberDTO> memberDTOList();

    public MemberDTO memberDTOByEmail(@RequestParam String email);

    public int memberDTOUpdate(@RequestBody MemberDTO memberDTO,
                               @RequestParam String newPassword,
                               @RequestParam String passwordConfirm);
}
