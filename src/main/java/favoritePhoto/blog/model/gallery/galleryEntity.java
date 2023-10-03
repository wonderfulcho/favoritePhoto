package favoritePhoto.blog.model.gallery;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gallery")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class galleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // PK
    private String nickname;    // 작성자 넥네임
    private String title;       // 제목
    private String content;     // 내용
    private String img;         // 이미지 경로 주소
    private int hits;           // 조회수
    private String deleteYn;    // 삭제 여부
    private LocalDateTime createDate = LocalDateTime.now(); // 생성일
    private LocalDateTime modifiedDate; // 수정일
}
