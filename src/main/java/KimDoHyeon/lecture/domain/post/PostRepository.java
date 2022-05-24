package KimDoHyeon.lecture.domain.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostMbti(String mbti, Pageable pageable);


    @Modifying
    @Query(value = "UPDATE Post SET content = :newContent where id = :idx")
    void updatePostById(@Param("newContent") String newContent, @Param("idx") Long idx);
}
