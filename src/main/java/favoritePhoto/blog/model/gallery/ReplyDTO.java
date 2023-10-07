package favoritePhoto.blog.model.gallery;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReplyDTO {
    private Long id;                    // PK
    private Long galleryID;             // 갤러리 게시글 ID
    private String nickname;            // 닉네임
    private String content;             // 댓글 내용
    private LocalDateTime createDate;   // 생성일
    private LocalDateTime modifiedDate; // 수정일
    @Builder.Default
    private String deleteYn = "n";      // 삭제 여부, 기본값 "n"

    public ReplyEntity toEntity() {
        return ReplyEntity.builder()
                .id(id)
                .galleryID(galleryID)
                .nickname(nickname)
                .content(content)
                .deleteYn(deleteYn)
                .createDate(createDate)
                .build();
    }
}
