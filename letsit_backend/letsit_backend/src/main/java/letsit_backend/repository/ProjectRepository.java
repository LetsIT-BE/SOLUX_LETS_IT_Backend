package letsit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Object[], Long> {

    @Query("SELECT p.id, p.title, r.regionName, p.onoff, p.stack, p.difficulty, p.userId FROM Project p JOIN Region r ON p.regionId = r.id WHERE p.userId = :userId")
    List<Object[]> findByUserId(Long userId);
}