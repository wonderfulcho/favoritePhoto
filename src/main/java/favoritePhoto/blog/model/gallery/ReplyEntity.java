package favoritePhoto.blog.model.gallery;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // PK
    private Long galleryID;             // 갤러리 게시글 ID
    private String nickname;            // 닉네임
    private String content;             // 댓글 내용
    private LocalDateTime createDate;   // 생성일
    private LocalDateTime modifiedDate; // 수정일
    @Builder.Default
    private String deleteYn = "n";      // 삭제 여부, 기본값 "n"

    public ReplyDTO toDTO() {
        return ReplyDTO.builder()
                .id(id)
                .galleryID(galleryID)
                .nickname(nickname)
                .content(content)
                .deleteYn(deleteYn)
                .createDate(createDate)
                .build();
    }
}
