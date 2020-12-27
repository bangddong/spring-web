package com.iambd.springweb.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity 클래스가 BaseTimeEntity를 상속할 경우 아래 변수를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing(관리,추적) 기능 추가
public class BaseTimeEntity {

    // 생성일
    @CreatedDate
    private LocalDateTime createTime;
    // 수정일
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
