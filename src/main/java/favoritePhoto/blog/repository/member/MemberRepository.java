package favoritePhoto.blog.repository.member;

import favoritePhoto.blog.model.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    public MemberEntity findByEmail(String email);

    public MemberEntity save(MemberEntity memberEntity);
}
