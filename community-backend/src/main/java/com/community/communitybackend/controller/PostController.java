package com.community.communitybackend.controller;

import com.community.communitybackend.dto.PostDetailDto;
import com.community.communitybackend.dto.PostDto;
import com.community.communitybackend.dto.PostResponseDto;
import com.community.communitybackend.entity.Post;
import com.community.communitybackend.entity.User;
import com.community.communitybackend.repository.PostRepository;
import com.community.communitybackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts") // 所有帖子相关的API都在 /posts 路径下
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 创建新帖子的接口 (受保护的)
     * @param postDto 包含帖子标题和内容的DTO
     * @param userDetails 由Spring Security自动注入的当前登录用户信息
     * @return 成功或失败的响应
     */
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: Author not found."));

        Post newPost = new Post();
        newPost.setTitle(postDto.getTitle());
        newPost.setContent(postDto.getContent());
        newPost.setAuthor(author);

        postRepository.save(newPost);

        return ResponseEntity.ok("Post created successfully!");
    }

    /**
     * 获取所有帖子列表的接口 (公开的)
     * @return 包含所有帖子信息的DTO列表
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));

        List<PostResponseDto> postDtos = posts.stream().map(post -> {
            PostResponseDto dto = new PostResponseDto();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            if (post.getAuthor() != null) {
                dto.setAuthorUsername(post.getAuthor().getUsername());
            }
            dto.setCreateTime(post.getCreateTime());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(postDtos);
    }

    /**
     * 根据ID获取单个帖子详情的接口 (公开的)
     * @param id 从URL路径中获取的帖子ID
     * @return 包含帖子详细信息的DTO，如果找不到则返回404
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDto> getPostById(@PathVariable Long id) {
        Optional<Post> postOptional = postRepository.findById(id);

        return postOptional.map(post -> {
            // 如果帖子存在，将其转换为DTO并返回
            PostDetailDto dto = new PostDetailDto();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            if (post.getAuthor() != null) {
                dto.setAuthorUsername(post.getAuthor().getUsername());
            }
            dto.setCreateTime(post.getCreateTime());
            dto.setUpdateTime(post.getUpdateTime());
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build()); // 如果帖子不存在，返回 404 Not Found
    }
}