package favoritePhoto.blog.model.gallery;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GalleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // PK
    private String nickname;            // 작성자 넥네임
    private String title;               // 제목
    private String content;             // 내용
    private LocalDateTime createDate;   // 생성일
    private LocalDateTime modifiedDate; // 수정일
    @Builder.Default
    private int hits = 0;               // 조회수, 기본값 0
    @Builder.Default
    private String deleteYn = "n";      // 삭제 여부, 기본값 "n"

    public GalleryDTO toDTO() {
        return GalleryDTO.builder()
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
