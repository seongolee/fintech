package com.fintech.fintech.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

// 게시글
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 은 Identity 방식으로 설정해주어야한다.
    private long id;

    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 내용

    @Setter private String hashtag; // 해시태그

    @ToString.Exclude // articleComment 에도 ToString이 존재하기 때문에 순환참조가 일어나게 되므로 ToString을 끊어준다.
    // foreign key 를 설정할때는 여러가지 제약사항이 생길 수 있으므로 설계시 고려해야 할 사항을 파악해야 함.
    @OrderBy("id") // 정렬기준 설정
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // mappedBy 를 설정해주지 않으면 default 가 두 Entity 이름을 합쳐서 생성되기 때문에 설정해준다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 다른 방법도 사용이 가능하지만 중복 허용을 하지 않기때문에 Set 으로 처리해준다.

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article that = (Article) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
