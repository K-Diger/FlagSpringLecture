package KimDoHyeon.lecture.domain.comment;

import KimDoHyeon.lecture.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter

//Builder 어노테이션 등록을 위한 생성자 어노테이션 추가 등록
@Builder
@AllArgsConstructor
@NoArgsConstructor
//
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    @JsonIgnore
    private Post post;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;


}
