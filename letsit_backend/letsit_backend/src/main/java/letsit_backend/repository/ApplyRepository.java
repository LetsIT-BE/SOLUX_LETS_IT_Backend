package letsit_backend.repository;

import letsit_backend.model.Apply;
import letsit_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    List<Apply> findAllByPostId(Post post);
}
