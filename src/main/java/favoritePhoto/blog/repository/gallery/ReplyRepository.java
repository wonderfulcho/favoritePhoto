package favoritePhoto.blog.repository.gallery;

import favoritePhoto.blog.model.gallery.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    @Query("SELECT r FROM ReplyEntity r WHERE r.deleteYn = 'n' AND r.galleryID = :galleryID")
    List<ReplyEntity> findByGalleryID(@Param("galleryID") Long galleryID);

    @Modifying
    @Query("UPDATE ReplyEntity r SET r.deleteYn = 'y' WHERE r.id = :id")
    void softDeleteById(@Param("id") Long id);
}
