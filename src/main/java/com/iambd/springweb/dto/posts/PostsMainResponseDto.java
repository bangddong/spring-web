package com.iambd.springweb.dto.posts;

import com.iambd.springweb.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class PostsMainResponseDto {
    private Long postId;
    private String postTitle;
    private String postContent;
    private String postAuthor;
    private String createTime;
    private String modifiedDate;

    public PostsMainResponseDto(Posts entity) {
        postId = entity.getPostId();
        postTitle = entity.getPostTitle();
        postContent = entity.getPostContent();
        postAuthor = entity.getPostAuthor();
        createTime = toStringDateTime(entity.getCreateTime());
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }

    // DateTime 날짜를 String으로 치환
    private String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format) // true
                .orElse(""); // false
    }
}
