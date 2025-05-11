package com.daol.logbuffer.post.query;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostContent;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.image.domain.postimage.PostImage;
import com.daol.logbuffer.post.command.PostState;
import com.daol.logbuffer.image.domain.postthumbnailimage.PostThumbnailImage;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class PostData {

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_hashtag",
        joinColumns = @JoinColumn(name = "post_id"))
    private Set<HashtagId> hashtagIds;

    @Column(name = "post_state")
    @Enumerated(EnumType.STRING)
    private PostState state;

    @JoinColumn(name = "post_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages;

    @JoinColumn(name = "post_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private PostThumbnailImage postThumbnailImage;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}