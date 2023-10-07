package favoritePhoto.blog.service.gallery;

import favoritePhoto.blog.model.gallery.GalleryDTO;
import favoritePhoto.blog.model.gallery.GalleryEntity;
import favoritePhoto.blog.model.gallery.ReplyDTO;
import favoritePhoto.blog.model.gallery.ReplyEntity;
import favoritePhoto.blog.repository.gallery.GalleryRepository;
import favoritePhoto.blog.repository.gallery.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public GalleryService(GalleryRepository galleryRepository, ReplyRepository replyRepository) {
        this.galleryRepository = galleryRepository;
        this.replyRepository = replyRepository;
    }

    /* 갤러리 글 저장 */
    @Transactional
    public void saveGallery(GalleryDTO galleryDTO) {
        galleryDTO.setCreateDate(LocalDateTime.now());
        GalleryEntity galleryEntity = galleryDTO.toEntity();

        GalleryEntity updateEntity = GalleryEntity.builder()
                .id(galleryEntity.getId())
                .title(galleryDTO.getTitle())
                .content(galleryDTO.getContent())
                .modifiedDate(galleryDTO.getModifiedDate())
                .build();

        galleryRepository.save(updateEntity);
    }

    /* 갤러리 글 수정 */
    @Transactional
    public void updateGallery(GalleryDTO galleryDTO) {
        galleryDTO.setModifiedDate(LocalDateTime.now());

        Optional<GalleryEntity> optionalGalleryEntity = galleryRepository.findById(galleryDTO.getId());
        GalleryEntity galleryEntity = optionalGalleryEntity.get();

        GalleryEntity updateEntity = GalleryEntity.builder()
                .id(galleryEntity.getId())
                .createDate(galleryEntity.getCreateDate())
                .title(galleryDTO.getTitle())
                .nickname(galleryDTO.getNickname())
                .content(galleryDTO.getContent())
                .deleteYn(galleryDTO.getDeleteYn())
                .hits(galleryDTO.getHits())
                .modifiedDate(galleryDTO.getModifiedDate())
                .build();

        galleryRepository.save(updateEntity);
    }

    /* 갤러리 글 목록 전체 조회 */
    @Transactional
    public List<GalleryDTO> galleryDTOList() {
        List<GalleryEntity> galleryEntityList = galleryRepository.findAll();
        List<GalleryDTO> galleryDTOList = new ArrayList<>();

        for (GalleryEntity entity : galleryEntityList) {
            GalleryDTO galleryDTO = GalleryDTO.builder()
                    .id(entity.getId())
                    .nickname(entity.getNickname())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .deleteYn(entity.getDeleteYn())
                    .hits(entity.getHits())
                    .createDate(entity.getCreateDate())
                    .modifiedDate(entity.getModifiedDate())
                    .build();
            galleryDTOList.add(galleryDTO);
        }

        return galleryDTOList;
    }

    /* 페이징 처리 */
    @Transactional(readOnly = true)
    public Page<GalleryEntity> pageList(Pageable pageable) {
        return galleryRepository.findActiveGalleries(pageable);
    }


    /* 1개의 개시글 가져오기 */
    @Transactional
    public GalleryDTO galleryDetail(Long id) {
        Optional<GalleryEntity> galleryEntity = galleryRepository.findById(id);
        GalleryDTO galleryDTO = galleryEntity.get().toDTO();
        return galleryDTO;
    }

    /* 게시글 삭제 */
    @Transactional
    public void gallerySoftDeleteById(Long id) { galleryRepository.softDeleteById(id); }

    /* 댓글 저장 */
    @Transactional
    public void saveReply(ReplyDTO replyDTO) {
        replyDTO.setCreateDate(LocalDateTime.now());
        ReplyEntity replyEntity = replyDTO.toEntity();
        replyRepository.save(replyEntity);
    }

    /* 해당 게시글의 댓글 리스트 불러오기 */
    @Transactional
    public List<ReplyDTO> replyDTOList(Long galleryID) {
        List<ReplyEntity> replyEntityList = replyRepository.findByGalleryID(galleryID);
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for (ReplyEntity replyEntity : replyEntityList) {
            ReplyDTO replyDTO = ReplyDTO.builder()
                    .id(replyEntity.getId())
                    .galleryID(replyEntity.getGalleryID())
                    .nickname(replyEntity.getNickname())
                    .content(replyEntity.getContent())
                    .createDate(replyEntity.getCreateDate())
                    .modifiedDate(replyEntity.getModifiedDate())
                    .deleteYn(replyEntity.getDeleteYn())
                    .build();
            replyDTOList.add(replyDTO);
        }
        return replyDTOList;
    }

    /* 댓글 삭제 */
    @Transactional
    public void softDeleteById(Long id) {
        replyRepository.softDeleteById(id);
    }
}
