package com.iambd.springweb.domain.posts;

import com.iambd.springweb.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    실제 DB와 매칭될 클래스 (Entitiy)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 추가
@Getter
@Entity // 테이블과 매칭될 클래스임을 명시(언더스코어 네이밍)
public class Posts extends BaseTimeEntity {

    @Id // PK값
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // columnDefinition -> 컬럼 타입 명시
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
