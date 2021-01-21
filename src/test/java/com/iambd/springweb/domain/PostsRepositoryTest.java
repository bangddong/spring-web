package com.iambd.springweb.domain;

import com.iambd.springweb.domain.posts.Posts;
import com.iambd.springweb.domain.posts.PostsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
        Assertions.assertEquals(testPost.getPostTitle(), "테스트 게시글");
        Assertions.assertEquals(testPost.getPostContent(), "테스트 본문");
    }

    // desc 정렬이니 ID가 역순으로 조회되어야 함.
    @Test
    @DisplayName("게시글저장_불러오기_정렬")
    void 게시글저장_불러오기_정렬() {
        //given
        int testId = 2;
        //when
        Stream<Posts> postList = postsRepository.findAllDesc();
        //then
        Assertions.assertEquals(testId, postList.findFirst().get().getPostId());
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

    @Test
    void 게시글목록_불러오기() {
        //given

    }

}
