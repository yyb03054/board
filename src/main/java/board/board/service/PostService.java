package board.board.service;

import board.board.domain.Post;
import board.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

//@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    /**
     * 회원가입
     */
    public Long join(Post post){
        //같은 이름 중복 회원 x
        validateDuplicatePost(post);
        postRepository.save(post);
        return post.getId();

    }

    private void validateDuplicatePost(Post post) {
        postRepository.findByName(post.getName())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        postRepository.findByContent(post.getContent())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    public List<Post> findPosts(){
        return postRepository.findAll();
    }

    public Optional<Post> findOne(Long postId){
        return postRepository.findById(postId);
    }




}
