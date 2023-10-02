package favoritePhoto.blog.service.member;

import favoritePhoto.blog.model.member.MemberDTO;
import favoritePhoto.blog.model.member.MemberEntity;
import favoritePhoto.blog.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Override
    public void memberDTOJoin(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberDTO.toEntity();
        memberRepository.save(memberEntity);
    }

    @Transactional
    @Override
    public List<MemberDTO> memberDTOList() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (MemberEntity memberEntity : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.builder()
                    .id(memberEntity.getId())
                    .email(memberEntity.getEmail())
                    .password(memberEntity.getPassword())
                    .nickname(memberEntity.getNickname())
                    .build();
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    @Transactional
    @Override
    public MemberDTO memberDTOByEmail(String email) {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        MemberDTO memberDTO = MemberDTO.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .nickname(memberEntity.getNickname())
                .password(memberEntity.getPassword())
                .build();
        return memberDTO;
    }

    @Transactional
    public String memberLogin(String email, String password) {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (MemberEntity memberEntity : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.builder()
                    .id(memberEntity.getId())
                    .email(memberEntity.getEmail())
                    .password(memberEntity.getPassword())
                    .nickname(memberEntity.getNickname())
                    .build();
            memberDTOList.add(memberDTO);
        }

        for (MemberDTO memberDTO : memberDTOList) {
            if (email.equals(memberDTO.getEmail()) && password.equals(memberDTO.getPassword())) {
                return email;
            }
        }
        return null;
    }

    @Transactional
    @Override
    public int memberDTOUpdate(MemberDTO memberDTO, String newPassword, String passwordConfirm) {

        int check = 0;

        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity m : memberEntityList) {
            MemberDTO member = MemberDTO.builder()
                    .id(m.getId())
                    .email(m.getEmail())
                    .password(m.getPassword())
                    .nickname(m.getNickname())
                    .build();
            memberDTOList.add(member);
        }

        MemberEntity memberEntity = memberRepository.findByEmail(memberDTO.getEmail());
        MemberDTO memberPasswordCheck = memberEntity.toDTO();

        if (!memberPasswordCheck.getPassword().equals(memberDTO.getPassword())) {
            System.out.println("기존 비밀번호 다름");
            check = 1;
        } else if (memberPasswordCheck.getPassword().equals(memberDTO.getPassword())) {
            if (newPassword != null && !newPassword.equals(passwordConfirm)) {
                System.out.println("새로운 비밀번호와 비밀번호 확인 값 다름");
                check = 1;
            } else if (memberDTO.getNickname() == null) {
                System.out.println("닉네임을 입력하세요.");
                check = 3;
            } else if (memberDTO.getNickname().length() < 2 || memberDTO.getNickname().length() > 15) {
                System.out.println("닉네임은 2~15자여야 합니다. ");
                check = 4;
            } else {
                System.out.println("기존 비밀번호 같음");
                check = 5;
                MemberEntity entity = memberDTO.toEntity();
                memberRepository.save(entity);
            }

            for (MemberDTO member : memberDTOList) {
                if (member.getNickname().equals(memberDTO.getNickname()) && !member.getEmail().equals(memberDTO.getEmail())) {
                    System.out.println("닉네임 중복되서 변경 실패");
                    check = 2;
                }
            }

            if (newPassword != null && newPassword.length() >= 8 && newPassword.equals(passwordConfirm)) {
                System.out.println("새로운 비밀번호로 비밀번호 변경");
                memberDTO.setPassword(newPassword);
                MemberEntity entity = memberDTO.toEntity();
                memberRepository.save(entity);
            }
        }
        return check;
    }
}
