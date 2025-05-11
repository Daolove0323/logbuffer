package com.daol.logbuffer.hashtag;

import com.daol.logbuffer._common.api.ListResponse;
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

    @Transactional
    public HashtagResponse createHashtag(HashtagRequest hashtagReq) {
        return HashtagResponse.from(
            hashtagRepository.save(Hashtag.create(hashtagReq.name())));
    }

    @Transactional
    public void deleteHashtag(HashtagId hashtagId) {
        hashtagRepository.findById(hashtagId).orElseThrow();
        hashtagRepository.deleteById(hashtagId);
    }
}
