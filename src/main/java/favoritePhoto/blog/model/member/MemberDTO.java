package favoritePhoto.blog.model.member;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;            // PK
    private String email;       // 이메일
    private String password;    // 비밀번호
    private String nickname;    // 닉네임

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
