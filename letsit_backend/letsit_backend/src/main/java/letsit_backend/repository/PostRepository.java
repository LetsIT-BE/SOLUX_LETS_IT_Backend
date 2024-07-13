package letsit_backend.repository;

import letsit_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 최신순으로 모든 게시글 조회
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllByOrderByCreatedAtDesc();

    // 스크랩순으로 모든 게시글 조회
    @Query("SELECT p FROM Post p ORDER BY p.scrapCount DESC")
    List<Post> findAllByOrderByScrapCountDesc();

    // 조회순으로 모든 게시글 조회
    @Query("SELECT p FROM Post p ORDER BY p.viewCount DESC")
    List<Post> findAllByOrderByViewCountDesc();
}
