package com.community.communitybackend.controller;

import com.community.communitybackend.dto.CommentDto;
import com.community.communitybackend.entity.Comment;
import com.community.communitybackend.entity.Post;
import com.community.communitybackend.entity.User;
import com.community.communitybackend.repository.CommentRepository;
import com.community.communitybackend.repository.PostRepository;
import com.community.communitybackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments") // RESTful风格的URL，表示评论是帖子的子资源
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long postId,
                                        @RequestBody CommentDto commentDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        // 1. 找到当前登录的用户
        User author = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. 找到被评论的帖子
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 3. 创建并保存评论
        Comment newComment = new Comment();
        newComment.setContent(commentDto.getContent());
        newComment.setAuthor(author);
        newComment.setPost(post);

        commentRepository.save(newComment);

        return ResponseEntity.ok("Comment added successfully!");
    }
}