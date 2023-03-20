package com.spring.docs.service;

import com.spring.docs.entity.Post;
import com.spring.docs.repository.PostRepository;
import com.spring.docs.dto.PostResponse;
import com.spring.docs.dto.SaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(SaveRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepository.save(post);

        return post.getId();
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
