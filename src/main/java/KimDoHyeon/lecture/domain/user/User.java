package KimDoHyeon.lecture.domain.user;


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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String loginId;

    @Column
    private String pw;

    @Column
    private String mbti;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
