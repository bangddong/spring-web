package com.iambd.springweb.domain;

import com.iambd.springweb.domain.posts.Posts;
import com.iambd.springweb.domain.posts.PostsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /**
     * 각 메소드가 끝날 때 마다 데이터 초기화
     */
    @AfterEach
    void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글저장_불러오기")
    void 게시글저장_불러오기() {

        //given (테스트 기반 데이터 주입)
        Posts savePost = Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("gusehdqkd@gmail.com")
                .build();

        postsRepository.save(savePost);

        //when (테스트 행위 정의)
        List<Posts> postList = postsRepository.findAll();

        //then (테스트 검증)
        Posts testPost = postList.get(0);
        Assertions.assertEquals(testPost.getTitle(), "테스트 게시글");
        Assertions.assertEquals(testPost.getContent(), "테스트 본문");
    }

    @Test
    @DisplayName("BaseTimeEntity_등록")
    void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Posts savePost = Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("gusehdqkd@gmail.com")
                .build();

        postsRepository.save(savePost);

        // when
        List<Posts> postsList = postsRepository.findAll();
        // then
        Posts testPost = postsList.get(0);
        Assertions.assertTrue(testPost.getCreateTime().isAfter(now));
        Assertions.assertTrue(testPost.getModifiedDate().isAfter(now));
    }

}
