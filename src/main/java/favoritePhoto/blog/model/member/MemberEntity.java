package favoritePhoto.blog.model.member;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // PK
    private String email;       // 이메일
    private String password;    // 비밀번호
    private String nickname;    // 닉네임

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
