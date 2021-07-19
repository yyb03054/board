package board.board.repository;


import board.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);
    Optional<Post> findByName(String name);
    Optional<Post> findByContent(String content);
    List<Post> findAll();
}

