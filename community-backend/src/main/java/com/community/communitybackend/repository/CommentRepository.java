package com.community.communitybackend.repository;

import com.community.communitybackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 我们添加一个自定义方法，用于根据帖子ID查找所有评论，并按时间倒序排列
    List<Comment> findByPostIdOrderByCreateTimeDesc(Long postId);
}