package com.iambd.springweb.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
    Dao라 불리는 DB접근
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
