package favoritePhoto.blog.repository.gallery;

import favoritePhoto.blog.model.gallery.GalleryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {

    @Query("SELECT g FROM GalleryEntity g WHERE g.deleteYn = 'n'")
    List<GalleryEntity> findAll();
    @Query("SELECT g FROM GalleryEntity g WHERE g.deleteYn = 'n'")
    Page<GalleryEntity> findActiveGalleries(Pageable pageable);

    @Modifying
    @Query("UPDATE GalleryEntity g SET g.deleteYn = 'y' WHERE g.id = :id")
    void softDeleteById(@Param("id") Long id);
}
