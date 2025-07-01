package com.community.communitybackend.dto;

import java.time.LocalDateTime;

// 我们移除了 @Data 注解
public class PostResponseDto {
    private Long id;
    private String title;
    private String authorUsername;
    private LocalDateTime createTime;

    // --- 手动添加 Getters 和 Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}