package com.daol.logbuffer.hashtag;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HashtagRepository extends JpaRepository<Hashtag, HashtagId> {

    @Modifying
    @Query("""
        delete from Hashtag h
        where h.id in :hashtagIds
        and not exists (
        select 'x'
        from Post p 
        join p.hashtagIds ph
        where ph.value = h.id.value
        )
        """)
    void deleteHashtagsIfNotUsed(List<HashtagId> hashtagIds);

    Optional<Hashtag> findByName(String name);
}