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
    
    private String email;
    private String password;
    private String nickname;

    @Builder
    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
