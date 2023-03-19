package com.spring.docs;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/save")
    public Long savePost(@RequestBody SaveRequest request) {
        return postService.save(request);
    }

    @GetMapping("/post/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.get(id);
    }
}
