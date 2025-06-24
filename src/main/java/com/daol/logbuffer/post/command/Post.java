package com.daol.logbuffer.post.command;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @EmbeddedId
    private PostId id;

    @Column(name = "title")
    private String title;

    @Embedded
    private PostContent content;

    @Embedded
    private PostAuthorId postAuthorId;

    @Embedded
    private CategoryId categoryId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_hashtag",
        joinColumns = @JoinColumn(name = "post_id"))
    private Set<HashtagId> hashtagIds;

    @Column(name = "post_state")
    @Enumerated(EnumType.STRING)
    private PostState state;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Post() {
        this.id = PostId.generate();
    }

    private Post(String title, PostContent content, PostAuthorId postAuthorId,
        CategoryId categoryId, Set<HashtagId> hashtagIds, PostState state) {
        this.id = PostId.generate();
        this.title = title;
        this.content = content;
        this.postAuthorId = postAuthorId;
        this.categoryId = categoryId;
        this.hashtagIds = hashtagIds;
        this.state = state;
    }

    public static Post create(String title, String content, PostAuthorId authorId,
        CategoryId categoryId, List<HashtagId> hashtagIds, PostState state) {
        return new Post(
            title,
            new PostContent(content),
            authorId,
            categoryId,
            new HashSet<>(hashtagIds),
            state
        );
    }

    public void updateDetails(String title, String content, CategoryId categoryId,
        List<HashtagId> hashtagIds,
        PostState state) {
        changeTitle(title);
        changeContent(content);
        changeCategory(categoryId);
        changeHashtags(hashtagIds);
        changeState(state);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = new PostContent(content);
    }

    private void changeCategory(CategoryId categoryId) {
        this.categoryId = categoryId;
    }

    private void changeHashtags(List<HashtagId> hashtagIds) {
        this.hashtagIds = new HashSet<>(hashtagIds);
    }

    private void changeState(PostState state) {
        this.state = state;
    }

    public void publish() {
        changeState(PostState.PUBLISHED);
    }

    public void saveAsDraft() {
        changeState(PostState.DRAFT);
    }

    public void hide() {
        changeState(PostState.HIDDEN);
    }

    public void delete() {
        changeState(PostState.DELETED);
    }

    public void verifyAuthor(PostAuthorId authorId) {
        if (authorId == null) {
            throw new RuntimeException("게시글 작성자가 없습니다.");
        }
        if (!this.getPostAuthorId().equals(authorId)) {
            throw new RuntimeException("게시글 작성자가 일치하지 않습니다.");
        }
    }
}