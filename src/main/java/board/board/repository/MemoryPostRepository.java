package board.board.repository;


import board.board.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryPostRepository implements PostRepository {

    private static Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Post save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id)
    {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Post> findByName(String name)
    {
        return store.values().stream()
                .filter(post -> post.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Post> findByContent(String content)
    {
        return store.values().stream()
                .filter(post -> post.getContent().equals(content))
                .findAny();
    }
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear();
    }
}