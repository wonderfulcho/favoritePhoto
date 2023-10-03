package favoritePhoto.blog.model.file;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "FileMetadata")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String filepath;
    private long filesize;
}
