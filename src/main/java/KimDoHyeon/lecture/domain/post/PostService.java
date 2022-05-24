package KimDoHyeon.lecture.domain.post;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public boolean editPost(Long id, String content) {
        postRepository.updatePostById(content, id);

        return true;
    }

    @Transactional
    public Post loadPostByPostIndex(Long id) {
        return postRepository.findById(id).get();
    }
}