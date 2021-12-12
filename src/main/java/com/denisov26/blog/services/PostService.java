package com.denisov26.blog.services;

import com.denisov26.blog.exceptions.PostException;
import com.denisov26.blog.models.Post;
import com.denisov26.blog.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public List<Post> findById(Long id) {
        List<Post> posts = new ArrayList<>();
        postRepository.findById(id).ifPresent(posts::add);
        return posts;
    }

    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    public void updatePost(Long id, String title, String anons, String text) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException("Post does not exist"));
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);
        postRepository.save(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException("Post does not exist"));
        postRepository.delete(post);
    }
}
