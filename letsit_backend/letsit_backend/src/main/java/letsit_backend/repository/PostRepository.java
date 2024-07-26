package letsit_backend.repository;

import letsit_backend.model.Member;
import letsit_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Member userId);
    List<Post> findByUserIdAndDeadlineFalse(Member userId);
//    List<Post> findByUserIdAndStatus(Member userId, String status);
}
