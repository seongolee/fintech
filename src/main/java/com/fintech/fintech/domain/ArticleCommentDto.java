package com.fintech.fintech.domain;

import java.time.LocalDateTime;

// 댓글
public class ArticleCommentDto {
    private Long id;
    private ArticleDto article; // 게시글(ID)
    private String content; // 내용

    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자
}
