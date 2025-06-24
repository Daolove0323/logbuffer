package com.daol.logbuffer.image.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImageRepository<T extends Image> extends CrudRepository<T, ImageId> {

    Optional<T> findByFileName(String fileName);
}