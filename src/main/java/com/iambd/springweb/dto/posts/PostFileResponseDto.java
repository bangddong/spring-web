package com.iambd.springweb.dto.posts;

import com.iambd.springweb.domain.posts.PostFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PostFileResponseDto {
    private Long postId;
    private String origFileName;
    private String fileName;
    private String filePath;
    private String createTime;
    private String modifiedDate;

    public PostFileResponseDto(PostFile entity) {
        postId = entity.getPostId();
        origFileName = entity.getOrigFilename();
        fileName = entity.getFileName();
        filePath = entity.getFilePath();
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
