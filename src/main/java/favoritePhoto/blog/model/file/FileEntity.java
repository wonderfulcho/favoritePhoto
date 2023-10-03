package favoritePhoto.blog.model.file;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private byte[] content;
}
