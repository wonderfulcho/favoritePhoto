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
public class GalleryDTO {
    private Long id;                    // PK
    private String nickname;            // 작성자 넥네임
    private String title;               // 제목
    private String content;             // 내용
    private int hits;                   // 조회수
    private String deleteYn;            // 삭제 여부
    private LocalDateTime createDate;   // 생성일
    private LocalDateTime modifiedDate; // 수정일

    public GalleryEntity toEntity() {
        return GalleryEntity.builder()
                .id(id)
                .nickname(nickname)
                .title(title)
                .content(content)
                .hits(hits)
                .deleteYn(deleteYn)
                .createDate(createDate)
                .build();
    }
}
