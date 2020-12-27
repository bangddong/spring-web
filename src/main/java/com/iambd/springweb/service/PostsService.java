package com.iambd.springweb.service;

import com.iambd.springweb.domain.posts.PostsRepository;
import com.iambd.springweb.dto.posts.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class PostsService {

    private PostsRepository postsRepository;

    @Transactional // 트랜잰션, 로직 실행 중 에러 발생시 모든 작업 롤백, 즉 커밋을 하지 않음.
    public Long save(PostsSaveRequestDto dto) {
        return postsRepository.save(dto.toEntity()).getId();
    }
}
