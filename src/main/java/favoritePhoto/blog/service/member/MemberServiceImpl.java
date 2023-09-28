package favoritePhoto.blog.service.member;

import favoritePhoto.blog.model.member.MemberDTO;
import favoritePhoto.blog.model.member.MemberEntity;
import favoritePhoto.blog.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Override
    public void memberJoin(@ModelAttribute MemberDTO memberDTO) {
        MemberEntity memberEntity = memberDTO.toEntity();
        memberRepository.save(memberEntity);
    }
}
