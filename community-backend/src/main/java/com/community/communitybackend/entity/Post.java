package com.community.communitybackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob // 表示这是一个大对象，通常用于存储长文本
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 定义多对一关系：多个帖子可以属于一个用户
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false) // 定义外键列
    private User author;

    @CreationTimestamp // 由Hibernate自动在创建时设置时间戳
    private LocalDateTime createTime;

    @UpdateTimestamp // 由Hibernate自动在更新时设置时间戳
    private LocalDateTime updateTime;
}