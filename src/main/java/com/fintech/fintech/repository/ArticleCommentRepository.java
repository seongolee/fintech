package com.fintech.fintech.repository;

import com.fintech.fintech.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository <ArticleComment, Long>{
}
