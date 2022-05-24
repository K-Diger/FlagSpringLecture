package KimDoHyeon.lecture.domain.post;


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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String postMbti;

    @Column
    private String content;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}
