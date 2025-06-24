package com.daol.logbuffer.hashtag;

import com.daol.logbuffer._common.api.ListResponse;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional(readOnly = true)
    public ListResponse<HashtagResponse> getAllHashtags() {
        return ListResponse.of(hashtagRepository.findAll().stream()
            .map(HashtagResponse::from)
            .toList());
    }

    @Transactional(readOnly = true)
    public List<String> getHashtagNames(List<HashtagId> hashtagIds) {
        return hashtagRepository.findAllById(hashtagIds).stream()
            .map(Hashtag::getName)
            .toList();
    }

    @Transactional
    public HashtagResponse getOrCreateHashtag(HashtagRequest hashtagReq) {
        Hashtag hashtag = hashtagRepository.findByName(hashtagReq.name())
            .orElseGet(() -> hashtagRepository.save(Hashtag.create(hashtagReq.name())));
        return HashtagResponse.from(hashtag);
    }

    @Transactional
    public void deleteHashtag(HashtagId hashtagId) {
        hashtagRepository.findById(hashtagId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 해시태그를 찾을 수 없습니다."));
        hashtagRepository.deleteById(hashtagId);
    }

    @Transactional
    public void deleteHashtagsIfNotUsed(List<HashtagId> hashtags) {
        hashtagRepository.deleteHashtagsIfNotUsed(hashtags);
    }
}