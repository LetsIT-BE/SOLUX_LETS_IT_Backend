package letsit_backend.repository;

import letsit_backend.model.Post;
import letsit_backend.model.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {
    Optional<TeamPost> findByPostId(Post post);
}
