package com.iambd.springweb.dto.posts;

import com.iambd.springweb.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class PostsMainResponseDto {
    private Long id;
    private String title;
    private String author;
    private String modifiedDate;

    public PostsMainResponseDto(Posts entity) {
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
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
