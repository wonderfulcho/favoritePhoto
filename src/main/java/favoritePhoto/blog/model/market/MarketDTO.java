package favoritePhoto.blog.model.market;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MarketDTO {

    private Long id;
    private String title;       // 제목
    private String homepageUrl; // 판매페이지 주소
    private String imgUrl;      // img 주소
    private String price;       // 가격

}
