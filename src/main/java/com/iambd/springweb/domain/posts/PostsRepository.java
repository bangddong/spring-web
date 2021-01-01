package com.iambd.springweb.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

/*
    Dao라 불리는 DB접근
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
