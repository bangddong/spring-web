package com.iambd.springweb.dto.posts;

import com.iambd.springweb.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String postTitle;
    private String postContent;
    private String postAuthor;
    private String postThumbnail;


    public Posts toEntity() {
        return Posts.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .postThumbnail(postThumbnail)
                .postAuthor(postAuthor)
                .build();
    }

    @Builder
    public PostsSaveRequestDto(String postTitle, String postContent, String postAuthor) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
    }
}
